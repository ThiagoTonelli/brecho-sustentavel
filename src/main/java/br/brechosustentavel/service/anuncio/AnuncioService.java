package br.brechosustentavel.service.anuncio;

import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.model.EventoLinhaDoTempo;
import br.brechosustentavel.model.Peca;
import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.observer.Observavel;
import br.brechosustentavel.repository.repositoryFactory.IAnuncioRepository;
import br.brechosustentavel.repository.repositoryFactory.IComposicaoPecaRepository;
import br.brechosustentavel.repository.repositoryFactory.IComposicaoRepository;
import br.brechosustentavel.repository.repositoryFactory.IDefeitoPecaRepository;
import br.brechosustentavel.repository.repositoryFactory.IDefeitoRepository;
import br.brechosustentavel.repository.repositoryFactory.ILinhaDoTempoRepository;
import br.brechosustentavel.repository.repositoryFactory.IPecaRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import br.brechosustentavel.service.AplicarDescontosDefeitosService;
import br.brechosustentavel.service.CalculadoraDeIndicesService;
import br.brechosustentavel.service.GerenciadorLog;
import br.brechosustentavel.service.insignia.AplicaInsigniaService;
import br.brechosustentavel.service.pontuacao.PontuacaoService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.JOptionPane;

/**
 *
 * @author thiag
 */

public class AnuncioService {
    private final IPecaRepository pecaRepository;
    private final IAnuncioRepository anuncioRepository;
    private final ILinhaDoTempoRepository linhaDoTempoRepository;
    private final IDefeitoPecaRepository defeitoPecaRepository;
    private final IDefeitoRepository defeitoRepository;
    private final IComposicaoRepository composicaoRepository;
    private final IComposicaoPecaRepository composicaoPecaRepository;
    private final CalculadoraDeIndicesService calculadoraDeIndices;
    private final AplicarDescontosDefeitosService aplicarDescontos;
    private final AplicaInsigniaService aplicaInsigniaService;


    public AnuncioService(RepositoryFactory fabrica) {
        this.pecaRepository = fabrica.getPecaRepository();
        this.anuncioRepository = fabrica.getAnuncioRepository();
        this.linhaDoTempoRepository = fabrica.getLinhaDoTempoRepository();
        this.defeitoPecaRepository = fabrica.getDefeitoPecaRepository();
        this.defeitoRepository = fabrica.getDefeitoRepository();
        this.composicaoRepository = fabrica.getComposicaoRepository();
        this.composicaoPecaRepository = fabrica.getComposicaoPecaRepository();
        
        this.calculadoraDeIndices = new CalculadoraDeIndicesService();
        this.aplicarDescontos = new AplicarDescontosDefeitosService();
        this.aplicaInsigniaService = new AplicaInsigniaService();
    }

    public Anuncio criarOuAtualizarAnuncio(Peca peca, Usuario usuario) {
        String operacao = peca.getId_c() == null || pecaRepository.consultar(peca.getId_c()).isEmpty() ? "Criação de Anúncio" : "Alteração de Anúncio";
        try{
            peca.setPrecoFinal(aplicarDescontos.calcularDescontos(peca));
            double gwpAvoided = calculadoraDeIndices.calcularGwpAvoided(peca);
            double mciPeca = calculadoraDeIndices.calcularMCI(peca);

            Optional<Peca> pecaOpt = pecaRepository.consultar(peca.getId_c());

            Anuncio anuncio;
            if (pecaOpt.isEmpty()) {
                anuncio = criarNovoAnuncio(peca, usuario, gwpAvoided, mciPeca);
            } else {
                anuncio = republicarAnuncio(peca, usuario, gwpAvoided, mciPeca);
            }


            GerenciadorLog.getInstancia().registrarSucesso(operacao, peca.getId_c(), peca.getSubcategoria());

            Observavel.getInstance().notifyObservers();
            return anuncio;
        }
        catch (Exception e){
            GerenciadorLog.getInstancia().registrarFalha(operacao, peca.getId_c(), peca.getSubcategoria(), e.getMessage());
            throw e; 
        }

    }

    private Anuncio criarNovoAnuncio(Peca novaPeca, Usuario usuario, double gwpAvoided, double mciPeca) {
        pecaRepository.criar(novaPeca);
        salvarDefeitosDaPeca(novaPeca);
        salvarComposicaoDaPeca(novaPeca);
        // Cria o primeiro evento na linha do tempo
        EventoLinhaDoTempo evento = new EventoLinhaDoTempo(
            "Primeira publicação", "publicação", LocalDateTime.now(), gwpAvoided, mciPeca
        );
        evento.setCliclo(1);
        linhaDoTempoRepository.criar(novaPeca.getId_c(), evento);

        // Cria e salva o anúncio
        Anuncio novoAnuncio = new Anuncio(usuario.getVendedor().get(), novaPeca, novaPeca.getPrecoFinal(), gwpAvoided, mciPeca);
        anuncioRepository.criar(novoAnuncio);

        // Concede insígnias se aplicável
        aplicaInsigniaService.concederInsignia(usuario);
        new PontuacaoService().processarNovoAnuncio(novoAnuncio);
        return novoAnuncio;
    }

    private Anuncio republicarAnuncio(Peca pecaAtualizada, Usuario usuario, double gwpAvoided, double mciPeca) {
        Optional<EventoLinhaDoTempo> ultimoEventoOpt = linhaDoTempoRepository.ultimoEvento(pecaAtualizada.getId_c());

        if (ultimoEventoOpt.isPresent() && ( "encerrado".equals(ultimoEventoOpt.get().getTipoEvento()) || "oferta aceita".equals(ultimoEventoOpt.get().getTipoEvento()) || "avaliacao registrada".equals(ultimoEventoOpt.get().getTipoEvento()))) {

            pecaRepository.editar(pecaAtualizada);

            defeitoPecaRepository.excluirDefeitosDaPeca(pecaAtualizada.getId_c());
            salvarDefeitosDaPeca(pecaAtualizada);

            EventoLinhaDoTempo evento = new EventoLinhaDoTempo(
                "Republicação", "publicação", LocalDateTime.now(), gwpAvoided, mciPeca
            );
            evento.setCliclo(ultimoEventoOpt.get().getCiclo_n() + 1);
            linhaDoTempoRepository.criar(pecaAtualizada.getId_c(), evento);

            Anuncio anuncio = new Anuncio(usuario.getVendedor().get(), pecaAtualizada, pecaAtualizada.getPrecoFinal(), gwpAvoided, mciPeca);
            anuncioRepository.editar(anuncio);

            return anuncio;
        } else {
            throw new IllegalStateException("Não é possível republicar um anúncio que não foi encerrado.");
        }
    }
    
    public Anuncio editarAnuncio(Peca pecaEditada, Usuario usuario) {
        
        pecaRepository.consultar(pecaEditada.getId_c())
                .orElseThrow(() -> new IllegalStateException("Tentativa de editar uma peça que não existe com o ID: " + pecaEditada.getId_c()));

        pecaEditada.setPrecoFinal(aplicarDescontos.calcularDescontos(pecaEditada));
        double gwpAvoided = calculadoraDeIndices.calcularGwpAvoided(pecaEditada);
        double mciPeca = calculadoraDeIndices.calcularMCI(pecaEditada);
        pecaRepository.editar(pecaEditada);

        defeitoPecaRepository.excluirDefeitosDaPeca(pecaEditada.getId_c());
        salvarDefeitosDaPeca(pecaEditada); 


        composicaoPecaRepository.excluirComposicaoDaPeca(pecaEditada.getId_c());
        salvarComposicaoDaPeca(pecaEditada); 

        Anuncio anuncio = new Anuncio(usuario.getVendedor().get(), pecaEditada, pecaEditada.getPrecoFinal(), gwpAvoided, mciPeca);
        anuncioRepository.editar(anuncio);
        
        adicionarEventoDeEdicaoNaLinhaDoTempo(pecaEditada, gwpAvoided, mciPeca);

        Observavel.getInstance().notifyObservers();

        return anuncio;
    }
    
     private void adicionarEventoDeEdicaoNaLinhaDoTempo(Peca peca, double gwpAvoided, double mciPeca) {
        Optional<EventoLinhaDoTempo> ultimoEventoOpt = linhaDoTempoRepository.ultimoEvento(peca.getId_c());
        
        int cicloAtual = ultimoEventoOpt.map(EventoLinhaDoTempo::getCiclo_n).orElse(1);

        EventoLinhaDoTempo eventoEdicao = new EventoLinhaDoTempo(
            "Anúncio editado", 
            "publicação",           
            LocalDateTime.now(),
            gwpAvoided,
            mciPeca
        );
        eventoEdicao.setCliclo(cicloAtual); 

        linhaDoTempoRepository.criar(peca.getId_c(), eventoEdicao);
    }
     
    public void excluirAnuncio(String idPeca) {
        Peca peca = null;
        try {
            if (idPeca == null || idPeca.trim().isEmpty()) {
                throw new IllegalArgumentException("O ID da peça não pode ser nulo ou vazio para excluir um anúncio.");
            }
            Optional<EventoLinhaDoTempo> ultimoEventoOpt = linhaDoTempoRepository.ultimoEvento(idPeca);
            String status = ultimoEventoOpt.get().getTipoEvento();
            if(!status.equalsIgnoreCase("oferta aceita")){
                Peca pecaParaExcluir = pecaRepository.consultar(idPeca)
                        .orElseThrow(() -> new IllegalStateException("Peça com ID " + idPeca + " não encontrada para exclusão."));

                adicionarEventoDeEncerramento(pecaParaExcluir);

                anuncioRepository.atualizarStatus(idPeca, "encerrado");

                Observavel.getInstance().notifyObservers();
                GerenciadorLog.getInstancia().registrarSucesso("Exclusão de Anúncio", idPeca, peca != null ? peca.getSubcategoria() : "N/A");
            }
            else {
                JOptionPane.showMessageDialog(null, "Não é possível excluir um item já vendido");
            }
        } catch (Exception e){
            String nomePeca = peca != null ? peca.getSubcategoria() : "ID " + idPeca;
            GerenciadorLog.getInstancia().registrarFalha("Exclusão de Anúncio", idPeca, nomePeca, e.getMessage());
            throw e;
        }
            
    }
    
    private void adicionarEventoDeEncerramento(Peca peca) {
        Optional<EventoLinhaDoTempo> ultimoEventoOpt = linhaDoTempoRepository.ultimoEvento(peca.getId_c());
        int cicloAtual = ultimoEventoOpt.map(EventoLinhaDoTempo::getCiclo_n).orElse(1);

        EventoLinhaDoTempo eventoEncerramento = new EventoLinhaDoTempo(
            "Anúncio encerrado pelo vendedor", 
            "encerrado",                       
            LocalDateTime.now(),
            0,
            0
        );
        eventoEncerramento.setCliclo(cicloAtual);

        linhaDoTempoRepository.criar(peca.getId_c(), eventoEncerramento);
    }
    
    private void salvarDefeitosDaPeca(Peca peca) {
        if (peca.getDefeitos() != null && !peca.getDefeitos().isEmpty()) {
            List<Integer> idsDefeitos = new ArrayList<>();
            for (String nomeDefeito : peca.getDefeitos().keySet()) {
                Integer idDefeito = defeitoRepository.buscarIdPeloNomeDoDefeito(nomeDefeito);
                if (idDefeito != null) {
                    idsDefeitos.add(idDefeito);
                }
            }
            defeitoPecaRepository.adicionarVariosDefeitosAPeca(peca.getId_c(), idsDefeitos);
        }
    }
    
    private void salvarComposicaoDaPeca(Peca peca) {
        if (peca.getMaterialQuantidade() != null && !peca.getMaterialQuantidade().isEmpty()) {
            composicaoPecaRepository.adicionarComposicaoAPeca(peca, composicaoRepository);
        }
    }
}

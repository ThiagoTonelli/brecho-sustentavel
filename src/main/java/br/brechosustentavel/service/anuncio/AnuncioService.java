package br.brechosustentavel.service.anuncio;

import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.model.EventoLinhaDoTempo;
import br.brechosustentavel.model.Peca;
import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.observer.Observavel;
import br.brechosustentavel.repository.IAnuncioRepository;
import br.brechosustentavel.repository.IComposicaoPecaRepository;
import br.brechosustentavel.repository.IComposicaoRepository;
import br.brechosustentavel.repository.IDefeitoPecaRepository;
import br.brechosustentavel.repository.IDefeitoRepository;
import br.brechosustentavel.repository.ILinhaDoTempoRepository;
import br.brechosustentavel.repository.IPecaRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.service.AplicarDescontosDefeitosService;
import br.brechosustentavel.service.CalculadoraDeIndicesService;
import br.brechosustentavel.service.insignia.AplicaInsigniaService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author thiag
 */

public class AnuncioService {

    // Repositorys
    private final IPecaRepository pecaRepository;
    private final IAnuncioRepository anuncioRepository;
    private final ILinhaDoTempoRepository linhaDoTempoRepository;
    private final IDefeitoPecaRepository defeitoPecaRepository;
    private final IDefeitoRepository defeitoRepository;
    private final IComposicaoRepository composicaoRepository;
    private final IComposicaoPecaRepository composicaoPecaRepository;

    // Services
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
        this.aplicaInsigniaService = new AplicaInsigniaService(fabrica);
    }

    public Anuncio criarOuAtualizarAnuncio(Peca peca, Usuario usuario) {
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

        // Notifica os observadores que houve uma alteração
        Observavel.getInstance().notifyObservers();

        return anuncio;
    }

    private Anuncio criarNovoAnuncio(Peca novaPeca, Usuario usuario, double gwpAvoided, double mciPeca) {
        pecaRepository.criar(novaPeca);

        // Associa os defeitos
        salvarDefeitosDaPeca(novaPeca);
        
        
        salvarComposicaoDaPeca(novaPeca);
        // Cria o primeiro evento na linha do tempo
        EventoLinhaDoTempo evento = new EventoLinhaDoTempo(
            "Primeira publicação", "publicação", LocalDateTime.now(), gwpAvoided, mciPeca
        );
        evento.setCliclo(1);
        linhaDoTempoRepository.criar(novaPeca.getId_c(), evento);

        // Cria e salva o anúncio
        Anuncio novoAnuncio = new Anuncio(usuario.getId(), novaPeca, novaPeca.getPrecoFinal(), gwpAvoided, mciPeca);
        anuncioRepository.criar(novoAnuncio);

        // Concede insígnias se aplicável
        aplicaInsigniaService.concederInsignia(usuario);

        return novoAnuncio;
    }

    private Anuncio republicarAnuncio(Peca pecaAtualizada, Usuario usuario, double gwpAvoided, double mciPeca) {
        Optional<EventoLinhaDoTempo> ultimoEventoOpt = linhaDoTempoRepository.ultimoEvento(pecaAtualizada.getId_c());

        if (ultimoEventoOpt.isPresent() && "encerrado".equals(ultimoEventoOpt.get().getTipoEvento())) {

            pecaRepository.editar(pecaAtualizada);

            defeitoPecaRepository.excluirDefeitosDaPeca(pecaAtualizada.getId_c());
            salvarDefeitosDaPeca(pecaAtualizada);

            EventoLinhaDoTempo evento = new EventoLinhaDoTempo(
                "Republicação", "publicação", LocalDateTime.now(), gwpAvoided, mciPeca
            );
            evento.setCliclo(ultimoEventoOpt.get().getCiclo_n() + 1);
            linhaDoTempoRepository.criar(pecaAtualizada.getId_c(), evento);

            Anuncio anuncio = new Anuncio(usuario.getId(), pecaAtualizada, pecaAtualizada.getPrecoFinal(), gwpAvoided, mciPeca);
            anuncioRepository.editar(anuncio);

            return anuncio;
        } else {
            throw new IllegalStateException("Não é possível republicar um anúncio que não foi encerrado.");
        }
    }
    
    public Anuncio editarAnuncio(Peca pecaEditada, Usuario usuario) {
        // 1. Valida se a peça realmente existe no banco de dados
        pecaRepository.consultar(pecaEditada.getId_c())
                .orElseThrow(() -> new IllegalStateException("Tentativa de editar uma peça que não existe com o ID: " + pecaEditada.getId_c()));

        // 2. Calcula o novo preço final e os índices com base nos dados editados
        pecaEditada.setPrecoFinal(aplicarDescontos.calcularDescontos(pecaEditada));
        double gwpAvoided = calculadoraDeIndices.calcularGwpAvoided(pecaEditada);
        double mciPeca = calculadoraDeIndices.calcularMCI(pecaEditada);

        // 3. Atualiza a entidade Peca na base de dados
        pecaRepository.editar(pecaEditada);

        // 4. Limpa os defeitos antigos e salva os novos
        defeitoPecaRepository.excluirDefeitosDaPeca(pecaEditada.getId_c());
        salvarDefeitosDaPeca(pecaEditada); // Reutiliza o método auxiliar

        // 5. Limpa a composição antiga e salva a nova
        composicaoPecaRepository.excluirComposicaoDaPeca(pecaEditada.getId_c());
        salvarComposicaoDaPeca(pecaEditada); // Reutiliza o método auxiliar

        // 6. Atualiza o anúncio associado
        Anuncio anuncio = new Anuncio(usuario.getId(), pecaEditada, pecaEditada.getPrecoFinal(), gwpAvoided, mciPeca);
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
        if (idPeca == null || idPeca.trim().isEmpty()) {
            throw new IllegalArgumentException("O ID da peça não pode ser nulo ou vazio para excluir um anúncio.");
        }

        Peca pecaParaExcluir = pecaRepository.consultar(idPeca)
                .orElseThrow(() -> new IllegalStateException("Peça com ID " + idPeca + " não encontrada para exclusão."));

        adicionarEventoDeEncerramento(pecaParaExcluir);
        
        anuncioRepository.excluirPorPecaId(idPeca);

        Observavel.getInstance().notifyObservers();
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

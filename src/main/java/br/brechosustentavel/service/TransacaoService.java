package br.brechosustentavel.service;

import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.model.Comprador;
import br.brechosustentavel.model.EventoLinhaDoTempo;
import br.brechosustentavel.model.Oferta;
import br.brechosustentavel.model.Transacao;
import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.model.Vendedor;
import br.brechosustentavel.repository.repositoryFactory.IAnuncioRepository;
import br.brechosustentavel.repository.repositoryFactory.ICompradorRepository;
import br.brechosustentavel.repository.repositoryFactory.ILinhaDoTempoRepository;
import br.brechosustentavel.repository.repositoryFactory.IOfertaRepository;
import br.brechosustentavel.repository.repositoryFactory.ITransacaoRepository;
import br.brechosustentavel.repository.repositoryFactory.IUsuarioRepository;
import br.brechosustentavel.repository.repositoryFactory.IVendedorRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import br.brechosustentavel.service.insignia.AplicaInsigniaService;
import br.brechosustentavel.service.pontuacao.PontuacaoService;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Serviço responsável por orquestrar todas as etapas de uma transação bem-sucedida.
 */
public class TransacaoService {
    
    private final ITransacaoRepository transacaoRepository;
    private final IOfertaRepository ofertaRepository;
    private final IAnuncioRepository anuncioRepository;
    private final ICompradorRepository compradorRepository;
    private final IVendedorRepository vendedorRepository;
    private final IUsuarioRepository usuarioRepository;
    private final AplicaInsigniaService insigniaService;
    private final ILinhaDoTempoRepository linhaDoTempoRepository;
    private final PontuacaoService pontuacaoService; // Adicionado para centralizar a chamada

    public TransacaoService(AplicaInsigniaService insigniaService) {
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        this.usuarioRepository = fabrica.getUsuarioRepository();
        this.compradorRepository = fabrica.getCompradorRepository();
        this.vendedorRepository = fabrica.getVendedorRepository();
        this.transacaoRepository = fabrica.getTransacaoRepository();
        this.ofertaRepository = fabrica.getOfertaRepository();
        this.anuncioRepository = fabrica.getAnuncioRepository();
        this.linhaDoTempoRepository = fabrica.getLinhaDoTempoRepository();
        this.insigniaService = insigniaService;
        this.pontuacaoService = new PontuacaoService();
    }
    
    public void aceitarOferta(int idOferta) {
        try {
            Oferta oferta = ofertaRepository.buscarOfertaPorId(idOferta)
                    .orElseThrow(() -> new IllegalStateException("Oferta com ID " + idOferta + " não encontrada."));

            Vendedor vendedor = oferta.getAnuncio().getVendedor();
            Comprador comprador = oferta.getComprador();
            Anuncio anuncio = oferta.getAnuncio();

            // Atualiza contadores de compra/venda
            vendedor.setVendasConcluidas(vendedor.getVendasConcluidas() + 1);
            comprador.setComprasFinalizadas(comprador.getComprasFinalizadas() + 1);

            // Soma o GWP aos perfis
            double gwpDaVenda = anuncio.getGwpAvoided();
            vendedor.setGwpContribuido(vendedor.getGwpContribuido() + gwpDaVenda);
            comprador.setGwpEvitado(comprador.getGwpEvitado() + gwpDaVenda);
            
            oferta.setStatus("Aceita");
            oferta.setDataResposta(LocalDateTime.now());
            ofertaRepository.atualizar(oferta);
            
            Transacao transacao = new Transacao(oferta, oferta.getValor());
            transacaoRepository.salvar(transacao);


            pontuacaoService.processarConclusaoTransacao(transacao);
            pontuacaoService.processarRespostaOferta(oferta);


            ofertaRepository.rejeitarOfertasRestantes(anuncio.getId(), idOferta);
            anuncioRepository.atualizarStatus(anuncio.getPeca().getId_c(), "vendido");
            criarEventoLinhaDoTempo(anuncio);
            concederInsignias(comprador.getId(), vendedor.getId());
            
            GerenciadorLog.getInstancia().registrarSucesso("Transação concluída", anuncio.getPeca().getId_c(), anuncio.getPeca().getSubcategoria());

        } catch (Exception e) {
            GerenciadorLog.getInstancia().registrarFalha("Conclusão de Transação", "", "Oferta ID " + idOferta, e.getMessage());
            throw new RuntimeException("Falha ao aceitar a oferta: " + e.getMessage(), e);
        }
    }

    private void criarEventoLinhaDoTempo(Anuncio anuncio) {
        Optional<EventoLinhaDoTempo> ultimoEventoOpt = linhaDoTempoRepository.ultimoEvento(anuncio.getPeca().getId_c());
        int cicloAtual = ultimoEventoOpt.map(EventoLinhaDoTempo::getCiclo_n).orElse(1);
        
        EventoLinhaDoTempo evento = new EventoLinhaDoTempo("Venda finalizada", "oferta aceita", LocalDateTime.now(), anuncio.getGwpAvoided(), anuncio.getMci());
        evento.setCliclo(cicloAtual);
        
        linhaDoTempoRepository.criar(anuncio.getPeca().getId_c(), evento);
    }
    
    private void concederInsignias(int idComprador, int idVendedor) {
        Optional<Usuario> optUsuarioComprador = usuarioRepository.buscarPorId(idComprador);
        Optional<Usuario> optUsuarioVendedor = usuarioRepository.buscarPorId(idVendedor);

        if (optUsuarioComprador.isEmpty() || optUsuarioVendedor.isEmpty()) {
            throw new RuntimeException("Erro ao encontrar usuários para conceder insígnias.");
        }
        
        insigniaService.concederInsignia(optUsuarioComprador.get());
        insigniaService.concederInsignia(optUsuarioVendedor.get());
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service;

import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.model.EventoLinhaDoTempo;
import br.brechosustentavel.model.Oferta;
import br.brechosustentavel.model.Transacao;
import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.repository.IAnuncioRepository;
import br.brechosustentavel.repository.ICompradorRepository;
import br.brechosustentavel.repository.ILinhaDoTempoRepository;
import br.brechosustentavel.repository.IOfertaRepository;
import br.brechosustentavel.repository.ITransacaoRepository;
import br.brechosustentavel.repository.IUsuarioRepository;
import br.brechosustentavel.repository.IVendedorRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.service.insignia.AplicaInsigniaService;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public class TransacaoService {
    private RepositoryFactory fabrica;
    private ITransacaoRepository transacaoRepository;
    private IOfertaRepository ofertaRepository;
    private IAnuncioRepository anuncioRepository;
    private ICompradorRepository compradorRepository;
    private IVendedorRepository vendedorRepository;
    private IUsuarioRepository usuarioRepository;
    private AplicaInsigniaService insigniaService;
    private ILinhaDoTempoRepository linhaDoTempoRepository;
    private Optional<Anuncio> anuncio;
    
    public TransacaoService(AplicaInsigniaService insigniaService){
        this.fabrica = RepositoryFactory.getInstancia();
        this.usuarioRepository = fabrica.getUsuarioRepository();
        this.compradorRepository = fabrica.getCompradorRepository();
        this.vendedorRepository = fabrica.getVendedorRepository();
        this.transacaoRepository = fabrica.getTransacaoRepository();
        this.ofertaRepository = fabrica.getOfertaRepository();
        this.anuncioRepository = fabrica.getAnuncioRepository();
        this.linhaDoTempoRepository = fabrica.getLinhaDoTempoRepository();
        this.insigniaService = insigniaService;
    }
    
    public void aceitarOferta(int idOferta){
        Optional<Oferta> ofertaOpt = Optional.empty();
        try{
            ofertaOpt = ofertaRepository.buscarOfertaPorId(idOferta);
            if (ofertaOpt.isEmpty()) {
                throw new IllegalStateException("Oferta com ID " + idOferta + " não encontrada.");
            }
            Oferta oferta = ofertaOpt.get();

            oferta.setStatus("Aceita");
            oferta.setDataResposta(LocalDateTime.now());
            ofertaRepository.atualizar(oferta);

            Transacao transacao = new Transacao(oferta, oferta.getValor());
            transacaoRepository.salvar(transacao);

            ofertaRepository.rejeitarOfertasRestantes(oferta.getAnuncio().getId(), idOferta);

            //Coloca informações na linha do tempo
            anuncio = anuncioRepository.buscarAnuncioPorId(oferta.getAnuncio().getId());
            Optional<EventoLinhaDoTempo> ultimoEventoOpt = linhaDoTempoRepository.ultimoEvento(anuncio.get().getPeca().getId_c());
            int cicloAtual = ultimoEventoOpt.map(EventoLinhaDoTempo::getCiclo_n).orElse(1);
            EventoLinhaDoTempo evento = new EventoLinhaDoTempo("Venda finalizada", "oferta aceita", LocalDateTime.now(), anuncio.get().getGwpAvoided(), 
                    anuncio.get().getMci());
            evento.setCliclo(cicloAtual);
            linhaDoTempoRepository.criar(anuncio.get().getPeca().getId_c(), evento);
            
            //Escreve no log
            GerenciadorLog.getInstancia().registrarSucesso("Transação concluída", anuncio.get().getPeca().getId_c(), anuncio.get().getPeca() != null ? 
                    anuncio.get().getPeca().getSubcategoria() : "N/A");
            
            //Adiciona venda e compra ao vendedor e comprador
            compradorRepository.atualizarCompras(oferta.getComprador().getId());
            vendedorRepository.atualizarVendas(oferta.getAnuncio().getVendedor().getId());
            
            anuncioRepository.atualizarStatus(anuncio.get().getPeca().getId_c(), "vendido");

            //Concede insignias
            Optional<Usuario> optUsuarioComprador = usuarioRepository.buscarPorId(oferta.getComprador().getId());
            Optional<Usuario> optUsuarioVendedor = usuarioRepository.buscarPorId(oferta.getAnuncio().getVendedor().getId());
            if(optUsuarioComprador.isEmpty() || optUsuarioVendedor.isEmpty()){
                throw new RuntimeException("Erro ao encontrar usuarios.");
            }
            insigniaService.concederInsignia(optUsuarioComprador.get());
            insigniaService.concederInsignia(optUsuarioVendedor.get()); 
            
        } catch (Exception e){
            String idcPeca = (anuncio != null && anuncio.isPresent()) ? anuncio.get().getPeca().getId_c() : "N/A";
            String nomePeca = (anuncio != null && anuncio.isPresent()) ? anuncio.get().getPeca().getSubcategoria() : "Oferta ID " + idOferta;
            GerenciadorLog.getInstancia().registrarFalha("Conclusão de Transação", idcPeca, nomePeca, e.getMessage());

            throw new RuntimeException("Falha ao aceitar a oferta: " + e.getMessage(), e);
        }
    }
}

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
import br.brechosustentavel.repository.ILinhaDoTempoRepository;
import br.brechosustentavel.repository.IOfertaRepository;
import br.brechosustentavel.repository.ITransacaoRepository;
import br.brechosustentavel.repository.IUsuarioRepository;
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
    private IUsuarioRepository usuarioRepository;
    private AplicaInsigniaService insigniaService;
    private ILinhaDoTempoRepository linhaDoTempoRepository;
    private Optional<Anuncio> anuncio;
    
    public TransacaoService(AplicaInsigniaService insigniaService){
        this.fabrica = RepositoryFactory.getInstancia();
        this.usuarioRepository = fabrica.getUsuarioRepository();
        this.transacaoRepository = fabrica.getTransacaoRepository();
        this.ofertaRepository = fabrica.getOfertaRepository();
        this.anuncioRepository = fabrica.getAnuncioRepository();
        this.linhaDoTempoRepository = fabrica.getLinhaDoTempoRepository();
        this.insigniaService = insigniaService;
    }
    
    public void aceitarOferta(int idOferta){
        try{
            Optional<Oferta> oferta = ofertaRepository.buscarOfertaPorId(idOferta);
            Transacao transacao = new Transacao(oferta.get(), oferta.get().getValor());
            transacaoRepository.salvar(transacao);

            //Coloca informações na linha do tempo
            anuncio = anuncioRepository.buscarAnuncioPorId(oferta.get().getAnuncio().getId());
            Optional<EventoLinhaDoTempo> ultimoEventoOpt = linhaDoTempoRepository.ultimoEvento(anuncio.get().getPeca().getId_c());
            int cicloAtual = ultimoEventoOpt.map(EventoLinhaDoTempo::getCiclo_n).orElse(1);
            EventoLinhaDoTempo evento = new EventoLinhaDoTempo("Venda finalizada", "oferta aceita", LocalDateTime.now(), anuncio.get().getGwpAvoided(), 
                    anuncio.get().getMci());
            evento.setCliclo(cicloAtual);
            linhaDoTempoRepository.criar(anuncio.get().getPeca().getId_c(), evento);

            GerenciadorLog.getInstancia().registrarSucesso("Transação concluída", anuncio.get().getPeca().getId_c(), anuncio.get().getPeca() != null ? 
                    anuncio.get().getPeca().getSubcategoria() : "N/A");

            anuncioRepository.excluirPorId(oferta.get().getAnuncio().getId());

            //Concede insignias
            Optional<Usuario> optUsuarioComprador = usuarioRepository.buscarPorId(oferta.get().getComprador().getId());
            Optional<Usuario> optUsuarioVendedor = usuarioRepository.buscarPorId(oferta.get().getAnuncio().getVendedor().getId());
            if(optUsuarioComprador.isEmpty() || optUsuarioVendedor.isEmpty()){
                throw new RuntimeException("Erro ao encontrar usuarios.");
            }
            insigniaService.concederInsignia(optUsuarioComprador.get());
            insigniaService.concederInsignia(optUsuarioVendedor.get());   
        } catch (Exception e){
            String nomePeca = anuncio.get().getPeca() != null ? anuncio.get().getPeca().getSubcategoria() : "ID " + anuncio.get().getPeca().getId_c();
            GerenciadorLog.getInstancia().registrarFalha("Exclusão de Anúncio", anuncio.get().getPeca().getId_c(), nomePeca, e.getMessage());
        }
    }
}

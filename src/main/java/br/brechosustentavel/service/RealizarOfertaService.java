/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service;

import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.model.EventoLinhaDoTempo;
import br.brechosustentavel.model.Oferta;
import br.brechosustentavel.repository.IAnuncioRepository;
import br.brechosustentavel.repository.ILinhaDoTempoRepository;
import br.brechosustentavel.repository.IOfertaRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.service.insignia.AplicaInsigniaService;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public class RealizarOfertaService {
    private SessaoUsuarioService sessao;
    private IOfertaRepository ofertaRepository;
    private IAnuncioRepository anuncioRepository;
    private RepositoryFactory repositoryFactory;
    private ILinhaDoTempoRepository linhaDoTempoRepository;
    
    public RealizarOfertaService(){       
        repositoryFactory = RepositoryFactory.getInstancia();
        this.ofertaRepository = repositoryFactory.getOfertaRepository();
        this.anuncioRepository = repositoryFactory.getAnuncioRepository();
        this.linhaDoTempoRepository = repositoryFactory.getLinhaDoTempoRepository();
        this.sessao = SessaoUsuarioService.getInstancia();
    }
    
    public void realizarOferta(String idPeca, double valorOferta){
        Optional<Anuncio> optAnuncio = anuncioRepository.buscarPorIdPeca(idPeca);
        
        if(optAnuncio.isEmpty()){
            throw new RuntimeException("Não foi encontrado um anúncio com a peça " + idPeca);
        }
     
        
        Oferta oferta = new Oferta(optAnuncio.get(), sessao.getUsuarioAutenticado().getComprador().get(), valorOferta);
        
        
        ofertaRepository.adicionarOferta(oferta);
        
        //Coloca informações na linha do tempo
        Optional<Anuncio> anuncio = anuncioRepository.buscarAnuncioPorId(oferta.getAnuncio().getId());
        Optional<EventoLinhaDoTempo> ultimoEventoOpt = linhaDoTempoRepository.ultimoEvento(anuncio.get().getPeca().getId_c());
        int cicloAtual = ultimoEventoOpt.map(EventoLinhaDoTempo::getCiclo_n).orElse(1);
        EventoLinhaDoTempo evento = new EventoLinhaDoTempo("Oferta enviada", "oferta enviada", LocalDateTime.now(), anuncio.get().getGwpAvoided(), 
                anuncio.get().getMci());
        evento.setCliclo(cicloAtual);
        linhaDoTempoRepository.criar(anuncio.get().getPeca().getId_c(), evento);
        
        //Aplica insignias
        new AplicaInsigniaService().concederInsignia(sessao.getUsuarioAutenticado());
    }
}

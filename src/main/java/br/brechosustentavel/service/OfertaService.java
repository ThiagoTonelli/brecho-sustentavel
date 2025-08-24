/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service;

import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.model.Oferta;
import br.brechosustentavel.repository.IAnuncioRepository;
import br.brechosustentavel.repository.IOfertaRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.service.insignia.AplicaInsigniaService;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public class OfertaService {
    private SessaoUsuarioService sessao;
    private IOfertaRepository ofertaRepository;
    private IAnuncioRepository anuncioRepository;
    private RepositoryFactory repositoryFactory;
    
    public OfertaService(){       
        repositoryFactory = RepositoryFactory.getInstancia();
        this.ofertaRepository = repositoryFactory.getOfertaRepository();
        this.anuncioRepository = repositoryFactory.getAnuncioRepository();
        this.sessao = SessaoUsuarioService.getInstancia();
    }
    
    public void realizarOferta(String idPeca, double valorOferta){
        Optional<Anuncio> optAnuncio = anuncioRepository.buscarPorIdPeca(idPeca);
        
        if(optAnuncio.isEmpty()){
            throw new RuntimeException("Não foi encontrado um anúncio com a peça " + idPeca);
        }
        
        Oferta oferta = new Oferta(
              optAnuncio.get(),
              sessao.getUsuarioAutenticado().getComprador().get(),
              valorOferta
        );
        
        ofertaRepository.adicionarOferta(oferta);
        new AplicaInsigniaService(repositoryFactory).concederInsignia(sessao.getUsuarioAutenticado());
    }
}

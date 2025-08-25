/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.insignia;

import br.brechosustentavel.model.Comprador;
import br.brechosustentavel.model.Insignia;
import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.repository.repositoryFactory.ICompradorInsigniaRepository;
import br.brechosustentavel.repository.repositoryFactory.ICompradorRepository;
import br.brechosustentavel.repository.repositoryFactory.IInsigniaRepository;
import br.brechosustentavel.repository.repositoryFactory.IOfertaRepository;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public class PrimeiraOfertaHandler implements ITipoInsigniaHandler{
    private IOfertaRepository ofertaRepository;
    private IInsigniaRepository insigniaRepository;
    private ICompradorInsigniaRepository compradorInsigniaRepository;
    private ICompradorRepository compradorRepository;

    public PrimeiraOfertaHandler(IOfertaRepository ofertaRepository, IInsigniaRepository insigniaRepository, ICompradorInsigniaRepository compradorInsigniaRepository, 
            ICompradorRepository compradorRepository) {
        this.ofertaRepository = ofertaRepository;
        this.insigniaRepository = insigniaRepository;
        this.compradorInsigniaRepository = compradorInsigniaRepository;
        this.compradorRepository = compradorRepository;
    }

    @Override
    public boolean seAplica(Usuario usuario) {
        if(usuario.getComprador().isPresent()){
            return true;
        }
        return false;
    }

    @Override
    public void concederInsignia(Usuario usuario) {
        String nomeInsignia = "Primeira Oferta";
        Optional<Insignia> optInsignia = insigniaRepository.buscarInsigniaPorNome(nomeInsignia);

        if(optInsignia.isEmpty()){
            throw new RuntimeException("A insignia" + nomeInsignia +  "não foi encontrada.");
        }

        Insignia insignia = optInsignia.get();
        Comprador comprador = usuario.getComprador().get();
        if(!compradorInsigniaRepository.compradorPossuiInsignia(insignia.getId(), usuario.getId())){
            if(ofertaRepository.qtdOfertaPorComprador(usuario.getId()) >= 1){
                compradorInsigniaRepository.inserirInsigniaAComprador(insignia.getId(), usuario.getId());
                
                double qtdEstrelas = comprador.getEstrelas() + insignia.getValorEstrelas();              
                compradorRepository.atualizarEstrelas(comprador.getId(), qtdEstrelas);
                comprador.setEstrelas(qtdEstrelas);
            }
        }
    }
    
}

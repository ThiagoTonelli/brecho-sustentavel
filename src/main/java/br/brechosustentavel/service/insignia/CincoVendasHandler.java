/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.insignia;

import br.brechosustentavel.model.Insignia;
import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.repository.IAnuncioRepository;
import br.brechosustentavel.repository.IInsigniaRepository;
import br.brechosustentavel.repository.IVendedorInsigniaRepository;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public class CincoVendasHandler implements ITipoInsigniaHandler{
    private IInsigniaRepository insigniaRepository;
    private IVendedorInsigniaRepository vendedorInsigniaRepository;
    
    public CincoVendasHandler(IInsigniaRepository insigniaRepository, IVendedorInsigniaRepository vendedorInsigniaRepository){
        this.insigniaRepository = insigniaRepository;
        this.vendedorInsigniaRepository = vendedorInsigniaRepository;
    }
    
    @Override
    public boolean seAplica(Usuario usuario) {
        if(usuario.getVendedor().isPresent()){
            return true;
        }
        return false;
    }

    @Override
    public void concederInsignia(Usuario usuario) {
        String nomeInsignia = "Cinco Vendas";
        Optional<Insignia> optInsignia = insigniaRepository.buscarInsigniaPorNome(nomeInsignia);
        
        if(optInsignia.isEmpty()){
            throw new RuntimeException("A insignia" + nomeInsignia +  "não foi encontrada.");
        }
        
        Insignia insignia = optInsignia.get();
        if(!vendedorInsigniaRepository.vendedorPossuiInsignia(insignia.getId(), usuario.getId())){
            if(usuario.getVendedor().get().getVendasConcluidas() >= 5){
                vendedorInsigniaRepository.inserirInsigniaAVendedor(insignia.getId(), usuario.getId());
            }
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.insignia;

import br.brechosustentavel.model.Insignia;
import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.model.Vendedor;
import br.brechosustentavel.repository.IInsigniaRepository;
import br.brechosustentavel.repository.IVendedorInsigniaRepository;
import br.brechosustentavel.repository.IVendedorRepository;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public class CincoVendasHandler implements ITipoInsigniaHandler{
    private IInsigniaRepository insigniaRepository;
    private IVendedorInsigniaRepository vendedorInsigniaRepository;
    private IVendedorRepository vendedorRepository;
    
    public CincoVendasHandler(IInsigniaRepository insigniaRepository, IVendedorInsigniaRepository vendedorInsigniaRepository, IVendedorRepository vendedorRepository){
        this.insigniaRepository = insigniaRepository;
        this.vendedorInsigniaRepository = vendedorInsigniaRepository;
        this.vendedorRepository = vendedorRepository;
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
        Vendedor vendedor = usuario.getVendedor().get();
        if(!vendedorInsigniaRepository.vendedorPossuiInsignia(insignia.getId(), usuario.getId())){
            if(usuario.getVendedor().get().getVendasConcluidas() >= 5){
                vendedorInsigniaRepository.inserirInsigniaAVendedor(insignia.getId(), usuario.getId());
                
                double qtdEstrelas = vendedor.getEstrelas() + insignia.getValorEstrelas();              
                vendedorRepository.atualizarEstrelas(vendedor.getId(), qtdEstrelas);
                vendedor.setEstrelas(qtdEstrelas);
            }
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.insignia;

import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.repository.IAnuncioRepository;

/**
 *
 * @author kaila
 */
public class PrimeiroAnuncioHandler implements ITipoInsigniaHandler{
    private IAnuncioRepository anuncioRepository;
    
    public PrimeiroAnuncioHandler(IAnuncioRepository anuncioRepository){
        this.anuncioRepository = anuncioRepository;
    }
    
    
    @Override
    public void verificar(Usuario usuario) {
        if(usuario.getVendedor().isPresent()){
            if(usuario.getVendedor().get().getVendasConcluidas())
        }
    }

    @Override
    public void aplicar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

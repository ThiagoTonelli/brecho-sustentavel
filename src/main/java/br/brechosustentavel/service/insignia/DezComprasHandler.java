/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.insignia;

import br.brechosustentavel.model.Insignia;
import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.repository.ICompradorInsigniaRepository;
import br.brechosustentavel.repository.IInsigniaRepository;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public class DezComprasHandler implements ITipoInsigniaHandler{
    private IInsigniaRepository insigniaRepository;
    private ICompradorInsigniaRepository compradorInsigniaRepository;

    public DezComprasHandler(IInsigniaRepository insigniaRepository, ICompradorInsigniaRepository compradorInsigniaRepository) {
        this.insigniaRepository = insigniaRepository;
        this.compradorInsigniaRepository = compradorInsigniaRepository;
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
        String nomeInsignia = "Dez Compras";
        
        Optional<Insignia> optInsignia = insigniaRepository.buscarInsigniaPorNome(nomeInsignia);
        if(optInsignia.isEmpty()){
            throw new RuntimeException("A insignia" + nomeInsignia +  "não foi encontrada.");
        }

        Insignia insignia = optInsignia.get();
        if(!compradorInsigniaRepository.compradorPossuiInsignia(insignia.getId(), usuario.getId())){
            if(usuario.getComprador().get().getComprasFinalizadas() >= 10){
                compradorInsigniaRepository.inserirInsigniaAComprador(insignia.getId(), usuario.getId());
            }
        }
    }   
}

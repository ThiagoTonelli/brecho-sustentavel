/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.insignia;

import br.brechosustentavel.model.Insignia;
import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.repository.ICompradorInsigniaRepository;
import br.brechosustentavel.repository.IDenunciaRepository;
import br.brechosustentavel.repository.IInsigniaRepository;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public class GuardiaoQualidadeHandler implements ITipoInsigniaHandler{
    private IInsigniaRepository insigniaRepository;
    private IDenunciaRepository denunciaRepository;
    private ICompradorInsigniaRepository compradorInsigniaRepository;

    public GuardiaoQualidadeHandler(IInsigniaRepository insigniaRepository, IDenunciaRepository denunciaRepository, ICompradorInsigniaRepository compradorInsigniaRepository) {
        this.insigniaRepository = insigniaRepository;
        this.denunciaRepository = denunciaRepository;
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
        String nomeInsignia = "Guardião da Qualidade";
        
        Optional<Insignia> optInsignia = insigniaRepository.buscarInsigniaPorNome(nomeInsignia); 
        if(optInsignia.isEmpty()){
            throw new RuntimeException("A insignia" + nomeInsignia +  "não foi encontrada.");
        }
        
        Insignia insignia = optInsignia.get();
        if(!compradorInsigniaRepository.compradorPossuiInsignia(insignia.getId(), usuario.getId())){
            if(denunciaRepository.qtdDenunciasProcedentesPorComprador(usuario.getId()) >= 3){
                compradorInsigniaRepository.inserirInsigniaAComprador(insignia.getId(), usuario.getId());
            }
        }

    }
}

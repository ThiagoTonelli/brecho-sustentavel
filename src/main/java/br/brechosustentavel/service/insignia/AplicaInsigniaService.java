/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.insignia;

import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.repository.RepositoryFactory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kaila
 */
public class AplicaInsigniaService {
    private List<ITipoInsigniaHandler> tiposInsignias;

    public AplicaInsigniaService(RepositoryFactory repositoryFactory) {
        tiposInsignias = new ArrayList<>();
        
        //Insignias de vendedores
        tiposInsignias.add(new PrimeiroAnuncioHandler(repositoryFactory.getAnuncioRepository(), repositoryFactory.getInsigniaRepository(), 
                repositoryFactory.getVendedorInsigniaRepository(), repositoryFactory.getVendedorRepository())
        );      
        tiposInsignias.add(new CincoVendasHandler(repositoryFactory.getInsigniaRepository(), repositoryFactory.getVendedorInsigniaRepository(), 
                repositoryFactory.getVendedorRepository())
        );
        
        //Insignias de compradores
        tiposInsignias.add(new PrimeiraOfertaHandler(repositoryFactory.getOfertaRepository(), repositoryFactory.getInsigniaRepository(), 
                repositoryFactory.getCompradorInsigniaRepository(), repositoryFactory.getCompradorRepository())
        );
        tiposInsignias.add(new DezComprasHandler(repositoryFactory.getInsigniaRepository(), repositoryFactory.getCompradorInsigniaRepository(),
                repositoryFactory.getCompradorRepository())
        );
        tiposInsignias.add(new GuardiaoQualidadeHandler(repositoryFactory.getInsigniaRepository(), repositoryFactory.getDenunciaRepository(), 
                repositoryFactory.getCompradorInsigniaRepository(), repositoryFactory.getCompradorRepository())
        );
    }
    
    public void concederInsignia(Usuario usuario){
        for(ITipoInsigniaHandler tipoInsignia: tiposInsignias){
            try{
                if(tipoInsignia.seAplica(usuario)){
                    tipoInsignia.concederInsignia(usuario);
                }
            } catch (Exception e){
                throw new RuntimeException("Erro ao processar insignia: " + e.getMessage());
            }
        }
    }
}

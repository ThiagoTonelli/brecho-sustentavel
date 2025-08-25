/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service;

import br.brechosustentavel.model.Denuncia;
import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.repository.IDenunciaRepository;
import br.brechosustentavel.repository.IUsuarioRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.service.insignia.AplicaInsigniaService;
import br.brechosustentavel.service.pontuacao.PontuacaoService;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public class DenunciaService {
    private IDenunciaRepository denunciaRepository;
    private IUsuarioRepository usuarioRepository;
    
    public DenunciaService(){
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        denunciaRepository = fabrica.getDenunciaRepository();   
        usuarioRepository = fabrica.getUsuarioRepository();
    }
    
    public void processarDenunciaProcedente(int idDenuncia){
        Optional<Denuncia> denunciaOpt = denunciaRepository.buscarDenunciaPorId(idDenuncia);
        
        if(denunciaOpt.isEmpty()){
            throw new RuntimeException("Erro ao buscar denúncia.");
        }
        denunciaRepository.atualizarStatusDenuncia(denunciaOpt.get(), "Procedente");
        
        Optional <Usuario> usuarioOpt = usuarioRepository.buscarPorId(denunciaOpt.get().getComprador().getId());
        if(usuarioOpt.isEmpty()){
            throw new RuntimeException("Erro ao buscar autor da denúncia.");
        }
        new AplicaInsigniaService().concederInsignia(usuarioOpt.get());
        new PontuacaoService().processarDenunciaValidada(denunciaOpt.get());
    } 
    
    public void recusarDenuncia(int idDenuncia){
        Optional<Denuncia> denunciaOpt = denunciaRepository.buscarDenunciaPorId(idDenuncia);
        
        if(denunciaOpt.isEmpty()){
            throw new RuntimeException("Erro ao buscar denúncia.");
        }
        denunciaRepository.atualizarStatusDenuncia(denunciaOpt.get(), "Inválida");
    }
}

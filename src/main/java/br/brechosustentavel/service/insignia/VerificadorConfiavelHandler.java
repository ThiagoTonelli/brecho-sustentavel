/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.insignia;

import br.brechosustentavel.model.Comprador;
import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.repository.ICompradorRepository;
import br.brechosustentavel.repository.IDenunciaRepository;

/**
 *
 * @author kaila
 */
public class VerificadorConfiavelHandler implements ITipoInsigniaHandler{
    private IDenunciaRepository denunciaRepository;
    private ICompradorRepository compradorRepository;

    public VerificadorConfiavelHandler(IDenunciaRepository denunciaRepository, ICompradorRepository compradorRepository) {
        this.denunciaRepository = denunciaRepository;
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
        Comprador comprador = usuario.getComprador().get();
        int totalDenuncias = denunciaRepository.qtdDenunciasPorComprador(usuario.getId());
        int qtdDenunciasProcedentes = denunciaRepository.qtdDenunciasProcedentesPorComprador(usuario.getId());
        
        if(totalDenuncias > 0 && !comprador.isSelo()){
            double percentualProcedentes = (double) qtdDenunciasProcedentes / totalDenuncias;           
            if(percentualProcedentes >= 0.8){
                compradorRepository.atualizarSelo(comprador.getId());
                comprador.setSelo(true);
            }
        }
    }   
}

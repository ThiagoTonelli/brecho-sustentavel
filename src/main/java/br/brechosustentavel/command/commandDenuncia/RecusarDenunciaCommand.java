/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandDenuncia;

import br.brechosustentavel.service.DenunciaService;

/**
 *
 * @author kaila
 */
public class RecusarDenunciaCommand implements ICommandDenuncia{
    private final DenunciaService denunciaService;
    private int idDenuncia;

    public RecusarDenunciaCommand(DenunciaService denunciaService, int idDenuncia) {
        this.denunciaService = denunciaService;
        this.idDenuncia = idDenuncia;
    }
    
    @Override
    public void executar() {
        denunciaService.recusarDenuncia(idDenuncia);
    }
    
}
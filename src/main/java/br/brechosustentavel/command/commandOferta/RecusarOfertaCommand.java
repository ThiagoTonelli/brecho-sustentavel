/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandOferta;

import br.brechosustentavel.presenter.JanelaVisualizarOfertasPresenter;
import br.brechosustentavel.service.TransacaoService;

/**
 *
 * @author kaila
 */
public class RecusarOfertaCommand implements ICommandOferta{
    private final TransacaoService transacaoService;
    private final int idOferta;

    public RecusarOfertaCommand(TransacaoService transacaoService, int idOferta) {
        this.transacaoService = transacaoService;
        this.idOferta = idOferta;
    }
    
    @Override
    public void executar(JanelaVisualizarOfertasPresenter presenter, String idPeca) {
            transacaoService.recusarOferta(presenter, idOferta);
    }   
}

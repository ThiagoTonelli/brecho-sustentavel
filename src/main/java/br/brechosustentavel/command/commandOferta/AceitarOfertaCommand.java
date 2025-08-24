/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandOferta;

import br.brechosustentavel.model.Oferta;
import br.brechosustentavel.presenter.JanelaVisualizarOfertasPresenter;
import br.brechosustentavel.service.TransacaoService;

/**
 *
 * @author kaila
 */
public class AceitarOfertaCommand implements ICommandOferta{
    private final TransacaoService transacaoService;
    private final int idOferta;

    public AceitarOfertaCommand(TransacaoService transacaoService, int idOferta) {
        this.transacaoService = transacaoService;
        this.idOferta = idOferta;
    }
    
    @Override
    public void executar(JanelaVisualizarOfertasPresenter presenter, String idPeca) {
            transacaoService.aceitarOferta(idOferta);
    }   
}

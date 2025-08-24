/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.command.commandOferta;

import br.brechosustentavel.presenter.JanelaVisualizarOfertasPresenter;

/**
 *
 * @author kaila
 */
public interface ICommandOferta {
    public void executar(JanelaVisualizarOfertasPresenter presenter, String idPeca);
}

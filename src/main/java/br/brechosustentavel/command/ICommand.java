/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.command;

import br.brechosustentavel.presenter.ManterAnuncioPresenter;


/*
 *
 * @author thiag
 */
public interface ICommand {
    public void executar(ManterAnuncioPresenter presenter);
}

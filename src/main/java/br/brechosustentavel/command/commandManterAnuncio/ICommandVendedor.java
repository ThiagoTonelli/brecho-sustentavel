/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.command.commandManterAnuncio;

import br.brechosustentavel.presenter.manterAnuncioPresenter.ManterAnuncioPresenter;


/*
 *
 * @author thiag
 */
public interface ICommandVendedor {
    public Object executar(ManterAnuncioPresenter presenter);
}

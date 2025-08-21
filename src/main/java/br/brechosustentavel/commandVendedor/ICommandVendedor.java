/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.commandVendedor;

import br.brechosustentavel.presenter.ManterAnuncioPresenter.ManterAnuncioPresenter;


/*
 *
 * @author thiag
 */
public interface ICommandVendedor {
    public Object executar(ManterAnuncioPresenter presenter);
}

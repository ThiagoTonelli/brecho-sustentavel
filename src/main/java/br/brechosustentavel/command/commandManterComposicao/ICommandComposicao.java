/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandManterComposicao;

import br.brechosustentavel.presenter.manterComposicaoPresenter.ManterComposicaoPresenter;

/**
 *
 * @author thiag
 */
public interface ICommandComposicao {
    public void executar(ManterComposicaoPresenter presenter);
}

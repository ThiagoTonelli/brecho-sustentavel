/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.command.commandPrincipal;

import br.brechosustentavel.presenter.janelaPrincipalPresenter.JanelaPrincipalPresenter;
import br.brechosustentavel.service.SessaoUsuarioService;


/**
 *
 * @author thiag
 */
public interface ICommandPrincipal {
    public void executar(JanelaPrincipalPresenter presenter);
}

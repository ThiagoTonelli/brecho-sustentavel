/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.commandPrincipal;

import br.brechosustentavel.presenter.JanelaPrincipalPresenter.JanelaPrincipalPresenter;
import br.brechosustentavel.service.SessaoUsuarioService;


/**
 *
 * @author thiag
 */
public interface ICommandPrincipal {
    public void executar(JanelaPrincipalPresenter presenter, SessaoUsuarioService usuarioAutenticado);
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.command.commandPerfil;

import br.brechosustentavel.presenter.JanelaVisualizarPerfilPresenter;
import br.brechosustentavel.service.SessaoUsuarioService;

/**
 *
 * @author kaila
 */
public interface ICommandPerfil {
    public void executar(JanelaVisualizarPerfilPresenter presenter, SessaoUsuarioService sessao);
}

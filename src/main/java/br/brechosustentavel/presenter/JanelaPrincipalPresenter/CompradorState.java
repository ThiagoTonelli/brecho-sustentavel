/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.JanelaPrincipalPresenter;

import br.brechosustentavel.service.SessaoUsuarioService;

/**
 *
 * @author thiag
 */
public class CompradorState extends JanelaPrincipalState{

    public CompradorState(JanelaPrincipalPresenter presenter, SessaoUsuarioService usuarioAutenticado) {
        super(presenter, usuarioAutenticado);
    }
    
}

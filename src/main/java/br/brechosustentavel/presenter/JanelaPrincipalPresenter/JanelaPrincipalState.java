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
public abstract class JanelaPrincipalState {
    protected JanelaPrincipalPresenter presenter;
    protected SessaoUsuarioService usuarioAutenticado;
    
    public JanelaPrincipalState(JanelaPrincipalPresenter presenter, SessaoUsuarioService usuarioAutenticado){
        this.presenter = presenter;
        this.usuarioAutenticado = usuarioAutenticado;
    }
    
    public void criar() {
        throw new RuntimeException("Não é possível criar estando neste estado");
    }

    public void editar() {
         throw new RuntimeException("Não é possível editar estando neste estado");
    }

    public void ofertar() {
         throw new RuntimeException("Não é possível ofertar estando neste estado");
    }

    public void perfil() {
         throw new RuntimeException("Não é possível cancelar estando neste estado");
    }
    
    public void carregar(){
        throw new RuntimeException("Não é possível cancelar estando neste estado");
    }
}

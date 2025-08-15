/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.JanelaPrincipalPresenter;

import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.presenter.ManterAnuncioPresenter.EdicaoAnuncioState;
import br.brechosustentavel.presenter.ManterAnuncioPresenter.InclusaoAnuncioState;
import br.brechosustentavel.presenter.ManterAnuncioPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.view.JanelaPrincipalView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import br.brechosustentavel.view.IJanelaPrincipalView;

/**
 *
 * @author thiag
 */
public class JanelaPrincipalPresenter{
    private IJanelaPrincipalView view;
    private SessaoUsuarioService usuario;
    private JanelaPrincipalState estado;
    
    public JanelaPrincipalPresenter(SessaoUsuarioService usuario) throws PropertyVetoException {
        this.view = new JanelaPrincipalView();
        this.usuario = usuario;
        if(usuario.getTipoPerfil().equals("comprador")){
            setEstado(new CompradorState(this));
        }
        else if (usuario.getTipoPerfil().equals("vendedor")){
            setEstado(new VendedorState(this));
        }
        
    }
    
    public void setEstado(JanelaPrincipalState estado){
        this.estado = estado;
    }
    
    public IJanelaPrincipalView getView(){
        return view;
    }

}

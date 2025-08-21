/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.JanelaPrincipalPresenter;

import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.presenter.ManterAnuncioPresenter.EdicaoAnuncioState;
import br.brechosustentavel.presenter.ManterAnuncioPresenter.InclusaoAnuncioState;
import br.brechosustentavel.presenter.ManterAnuncioPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.presenter.TelaPrincipalPresenter;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.view.JanelaPrincipalView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import br.brechosustentavel.view.IJanelaPrincipalView;
import java.awt.Component;
import javax.swing.JInternalFrame;

/**
 *
 * @author thiag
 */
public class JanelaPrincipalPresenter{
    private JanelaPrincipalView view;
    private SessaoUsuarioService usuario;
    private JanelaPrincipalState estado;
    private TelaPrincipalPresenter telaPrincipal;
    
    public JanelaPrincipalPresenter(SessaoUsuarioService usuario, TelaPrincipalPresenter telaPrincipal) throws PropertyVetoException {
        this.view = new JanelaPrincipalView();
        this.usuario = usuario;
        this.telaPrincipal = telaPrincipal;
        telaPrincipal.getView().setLocationRelativeTo(null);
        telaPrincipal.getView().setSize(1150, 800);
        telaPrincipal.getView().setVisible(true);
        setFrame((JInternalFrame) view);
        
        if(usuario.getTipoPerfil().equalsIgnoreCase("Comprador")){
            setEstado(new CompradorState(this));
        }
        else if (usuario.getTipoPerfil().equalsIgnoreCase("Vendedor")){
            setEstado(new VendedorState(this, usuario));
        }
    }
    
    public void setEstado(JanelaPrincipalState estado){
        this.estado = estado;
    }
    
    public JanelaPrincipalView getView(){
        return view;
    }
    
    public void setFrame(JInternalFrame frame){
        telaPrincipal.getView().getjDesktopPane1().add(frame);
    }

}

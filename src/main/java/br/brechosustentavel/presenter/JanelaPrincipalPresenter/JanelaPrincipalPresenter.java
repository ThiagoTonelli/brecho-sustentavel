/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.janelaPrincipalPresenter;


import br.brechosustentavel.command.commandMenu.TrocarPerfilCommand;
import br.brechosustentavel.observer.Observador;
import br.brechosustentavel.observer.Observavel;
import br.brechosustentavel.presenter.TelaPrincipalPresenter;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.view.JanelaPrincipalView;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;

/**
 *
 * @author thiag
 */
public class JanelaPrincipalPresenter implements Observador{
    private JanelaPrincipalView view;
    private SessaoUsuarioService usuario;
    private JanelaPrincipalState estado;
    private TelaPrincipalPresenter telaPrincipal;
    
    public JanelaPrincipalPresenter(SessaoUsuarioService usuario, TelaPrincipalPresenter telaPrincipal) throws PropertyVetoException {
        this.view = new JanelaPrincipalView();
        this.usuario = usuario;
        this.telaPrincipal = telaPrincipal;
        
        Observavel.getInstance().addObserver(this);
        telaPrincipal.getView().setLocationRelativeTo(null);
        telaPrincipal.getView().setSize(1150, 800);
        telaPrincipal.getView().setVisible(true);
        setFrame((JInternalFrame) view);
        
        if(usuario.getTipoPerfil().equalsIgnoreCase("Comprador")){
            setEstado(new CompradorState(this, usuario));
        }
        else if (usuario.getTipoPerfil().equalsIgnoreCase("Vendedor")){
            setEstado(new VendedorState(this, usuario));
        }
        else {
            setEstado(new AdminState(this, usuario));
        }
        
        view.getOpcTrocarPerfil().addActionListener((ActionEvent e) -> {
            try {
                new TrocarPerfilCommand().executar(this);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(JanelaPrincipalPresenter.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
   
    }
    
    public SessaoUsuarioService getUsuario() {
        return usuario;
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

    @Override
    public void atualizar() {
        if (estado instanceof VendedorState) {
            ((VendedorState) estado).carregar();
        }
    }

}

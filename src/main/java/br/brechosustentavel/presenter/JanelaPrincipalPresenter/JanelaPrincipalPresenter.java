/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.janelaPrincipalPresenter;


import br.brechosustentavel.command.commandMenu.TrocarPerfilCommand;
import br.brechosustentavel.observer.Observador;
import br.brechosustentavel.observer.Observavel;
import br.brechosustentavel.presenter.LoginPresenter;
import br.brechosustentavel.presenter.TelaPrincipalPresenter;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import br.brechosustentavel.service.AutenticacaoService;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.service.hash.BCryptAdapter;
import br.brechosustentavel.service.verificador_telefone.LibPhoneNumberAdapter;
import br.brechosustentavel.view.JanelaPrincipalView;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

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
        view.getTextNomeLogado().setText(usuario.getUsuarioAutenticado().getNome());
        view.getTextNomeLogado().setEnabled(false);
        setFrame((JInternalFrame) view);
        telaPrincipal.getView().getjDesktopPane1().moveToBack(this.view);
        if(usuario.getTipoPerfil().equalsIgnoreCase("Comprador")){
            setEstado(new CompradorState(this, usuario));
        }
        else if (usuario.getTipoPerfil().equalsIgnoreCase("Vendedor")){
            setEstado(new VendedorState(this, usuario));
        }
        else if (usuario.getTipoPerfil().equalsIgnoreCase("Admin")){
            setEstado(new AdminState(this, usuario));
        }
        
        view.getOpcTrocarPerfil().addActionListener((ActionEvent e) -> {
            try {
                new TrocarPerfilCommand().executar(this);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(JanelaPrincipalPresenter.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        view.getOpcSair().addActionListener((ActionEvent e) -> {
            fazerLogout();
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
        telaPrincipal.abrirJanelaUnica(frame);
    }
    
    private void fazerLogout() {
        int confirmacao = JOptionPane.showConfirmDialog(
            view,
            "Tem certeza que deseja sair?",
            "Confirmação de Logout",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {

            usuario.logout();

            view.dispose();
            telaPrincipal.getView().dispose();

            try {

                RepositoryFactory fabrica = RepositoryFactory.getInstancia();
                new LoginPresenter(
                    fabrica,
                    new BCryptAdapter(),
                    new LibPhoneNumberAdapter(),
                    SessaoUsuarioService.getInstancia(),
                    new AutenticacaoService(fabrica.getUsuarioRepository(), new BCryptAdapter())
                );
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao reiniciar a aplicação após o logout: " + e.getMessage());
                System.exit(0); 
            }
        }
    }

    @Override
    public void atualizar() {
        if (estado instanceof VendedorState) {
            ((VendedorState) estado).carregar();
        }
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.presenter.JanelaPrincipalPresenter.JanelaPrincipalPresenter;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.view.JanelaEscolhaPerfilView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import javax.swing.JOptionPane;

/**
 *
 * @author kaila
 */
public class JanelaEscolhaPerfilPresenter {
    private JanelaEscolhaPerfilView view;
    private TelaPrincipalPresenter telaPrincipal;
    private SessaoUsuarioService sessao;
    
    public JanelaEscolhaPerfilPresenter(SessaoUsuarioService sessao, TelaPrincipalPresenter telaPrincipal) throws PropertyVetoException{
        this.telaPrincipal = telaPrincipal;
        this.sessao = sessao;
        
        view = new JanelaEscolhaPerfilView();
        telaPrincipal.getView().getjDesktopPane1().add(view);
        telaPrincipal.getView().setLocationRelativeTo(null);
        telaPrincipal.getView().setSize(1150, 800);
        telaPrincipal.getView().setVisible(true);
        view.setMaximum(true);
        view.setVisible(false);
        
        view.getBtnComprador().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    telaComprador();
                    view.dispose();
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        
        view.getBtnVendedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    telaVendedor();
                    view.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        view.setVisible(true);
    }
    
    public void telaVendedor() throws PropertyVetoException{
        sessao.setTipoPerfil("Vendedor");
        new JanelaPrincipalPresenter(sessao, telaPrincipal);
    }
    
    
    public void telaComprador() throws PropertyVetoException{
        sessao.setTipoPerfil("Comprador");
        new JanelaPrincipalPresenter(sessao, telaPrincipal);
    }
}

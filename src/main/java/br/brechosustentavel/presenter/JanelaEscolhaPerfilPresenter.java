/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.presenter.ManterAnuncioPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.view.JanelaEscolhaPerfilView;
import br.brechosustentavel.view.JanelaPrincipalView;
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
    private TelaPrincipalPresenter telaPresenter;
    
    public JanelaEscolhaPerfilPresenter(){
        this.telaPresenter = new TelaPrincipalPresenter();
        
        view = new JanelaEscolhaPerfilView();
        view.setVisible(false);
        view.getBtnComprador().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    telaComprador();
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
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        
        view.setVisible(true);
    }
    
    public void telaVendedor() throws PropertyVetoException{
        
        JanelaPrincipalView janelaVendedor = new JanelaPrincipalView();
        telaPresenter.getView().getjDesktopPane1().add(janelaVendedor);
        janelaVendedor.setMaximum(true);
        janelaVendedor.setVisible(true);
        //new ManterAnuncioPresenter();
    }
    
    
    public void telaComprador(){
        //inicia tela comprador
        //telaPresenter.getView().getjDesktopPane1().add(janelaEscolhaPerfil);
        //janelaEscolhaPerfil.setMaximum(true);
        //janelaEscolhaPerfil.setVisible(true);
    }
}

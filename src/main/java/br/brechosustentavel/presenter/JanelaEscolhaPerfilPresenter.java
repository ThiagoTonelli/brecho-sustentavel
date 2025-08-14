/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.presenter.VendedorPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.view.JanelaEscolhaPerfilView;
import br.brechosustentavel.view.JanelaVendedorView;
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
    private TelaPrincipalPresenter telaPresenter = new TelaPrincipalPresenter();
    
    public JanelaEscolhaPerfilPresenter(){
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
        JanelaVendedorView janelaVendedor = new JanelaVendedorView();
        telaPresenter.getView().getjDesktopPane1().add(janelaVendedor);
        janelaVendedor.setMaximum(true);
        janelaVendedor.setVisible(true);
        new ManterAnuncioPresenter();
    }
    
    
    public void telaComprador(){
        //inicia tela comprador
        //telaPresenter.getView().getjDesktopPane1().add(janelaEscolhaPerfil);
        //janelaEscolhaPerfil.setMaximum(true);
        //janelaEscolhaPerfil.setVisible(true);
    }
}

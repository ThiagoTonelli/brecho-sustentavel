/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.VendedorPresenter;

import br.brechosustentavel.view.IJanelaVendedorView;
import br.brechosustentavel.view.JanelaManterAnuncioView;
import br.brechosustentavel.view.JanelaVendedorView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thiag
 */
public class JanelaVendedorPresenter{
    private IJanelaVendedorView view;
    
    public JanelaVendedorPresenter() throws PropertyVetoException {
        this.view = new JanelaVendedorView();
        
        view.getButtonAdicionar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    ManterAnuncioPresenter presenter = new ManterAnuncioPresenter();
                    presenter.setEstadoVendedor(new InclusaoAnuncioState(presenter));
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(JanelaVendedorPresenter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        view.getButtonVisualizar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    ManterAnuncioPresenter presenter = new ManterAnuncioPresenter();
                    presenter.setEstadoVendedor(new EdicaoAnuncioState(presenter));
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(JanelaVendedorPresenter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

}

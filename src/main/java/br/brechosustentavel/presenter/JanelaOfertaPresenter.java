/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.view.JanelaOfertaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author kaila
 */
public class JanelaOfertaPresenter {
    private JanelaOfertaView view;
    
    public JanelaOfertaPresenter(java.awt.Frame janelaPai){   
        view = new JanelaOfertaView(janelaPai, true);
        configurarTela();
        
        view.getBtnEnviar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    enviar();
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        
        view.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cancelar();
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
    }
    
    private void configurarTela(){
        view.setLocationRelativeTo(null);
        view.setSize(800, 550);
        view.setVisible(false);
        
        //Configura txtField com inforomações
        view.getTxtPeca().setText("");
        
    }
    
    private void enviar(){
    }
    
    private void cancelar(){}
}

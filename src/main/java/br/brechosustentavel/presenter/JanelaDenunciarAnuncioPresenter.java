/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.command.commandDenuncia.RealizarDenunciaCommand;
import br.brechosustentavel.observer.Observavel;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.view.JanelaDenunciarAnuncioView;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author kaila
 */
public class JanelaDenunciarAnuncioPresenter {
    private JanelaDenunciarAnuncioView view;
    private SessaoUsuarioService sessao;
    private String idPeca;
    
    public JanelaDenunciarAnuncioPresenter(java.awt.Frame janelaPai, String idPeca, SessaoUsuarioService sessao){
        this.sessao = sessao;
        this.idPeca = idPeca;
        
        view = new JanelaDenunciarAnuncioView(janelaPai, true);      
        configurarTela();
        
        view.getBtnDenunciar().addActionListener((ActionEvent e) -> {
            try {
                denunciar();
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(view, "Falha ao denunciar: " + ex.getMessage());
            }
        });
        
        view.getBtnCancelar().addActionListener((ActionEvent e) -> {
            try {
                cancelar();
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
            }
        });
        view.setVisible(true);
    }
    
    private void configurarTela(){
        view.setLocationRelativeTo(null);
        view.setSize(800, 550);
        view.setVisible(false);
        
        //Configura enabled do txtField
        view.getTxtIDPeca().setEnabled(false);
        view.getTxtIDPeca().setText(idPeca);
        
        //Acrescenta motivos
        view.getcBoxMotivo().removeAllItems();
        view.getcBoxMotivo().addItem("Descrição enganosa");
        view.getcBoxMotivo().addItem("Omissão de defeitos");
        view.getcBoxMotivo().addItem("Incosistência descritiva");
        view.getcBoxMotivo().addItem("Outro");
    }
    
    private void denunciar(){
        new RealizarDenunciaCommand(this, sessao).executar();
        JOptionPane.showMessageDialog(view, "Denuncia enviada para análise com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        Observavel.getInstance().notifyObservers();
        view.dispose();
    }
    
    private void cancelar(){
        view.dispose();
    }
    
    public JanelaDenunciarAnuncioView getView(){
        return view;
    }
}

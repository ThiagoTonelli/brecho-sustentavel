/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.command.commandDenuncia.AceitarDenunciaCommand;
import br.brechosustentavel.command.commandDenuncia.RecusarDenunciaCommand;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.service.DenunciaService;
import br.brechosustentavel.view.JanelaVisualizarDetalhesDenunciaView;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import javax.swing.JOptionPane;

/**
 *
 * @author kaila
 */
public class JanelaVisualizarDetalhesDenunciaPresenter {
    private JanelaVisualizarDetalhesDenunciaView view;
    private SessaoUsuarioService sessao;
    private int idDenuncia;
    private String idPeca;
    private String motivo;
    private String descricao;
    
    public JanelaVisualizarDetalhesDenunciaPresenter(java.awt.Frame janelaPai, String idPeca, String motivo, String descricao, int idDenuncia, SessaoUsuarioService sessao){
        this.sessao = sessao;
        this.idPeca = idPeca;
        this.motivo = motivo;
        this.descricao = descricao;
        this.idDenuncia = idDenuncia;
        
        view = new JanelaVisualizarDetalhesDenunciaView(janelaPai, true);      
        configurarTela();
        
        view.getBtnAceitar().addActionListener((ActionEvent e) -> {
            try {
                aceitar();
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(view, "Falha ao aceitar denuncia: " + ex.getMessage());
            }
        });
        
        view.getBtnInvalidar().addActionListener((ActionEvent e) -> {
            try {
                recusar();
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(view, "Falha ao recusar denuncia: " + ex.getMessage());
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
        view.getTxtDescricao().setEnabled(false); 
        view.getTxtDescricao().setText(descricao);
        view.getTxtMotivo().setEnabled(false);
        view.getTxtMotivo().setText(motivo);
    }
    
    private void aceitar() throws PropertyVetoException{
        new AceitarDenunciaCommand(new DenunciaService(), idDenuncia).executar();
        JOptionPane.showMessageDialog(view, "Denúncia validada com sucesso! Por favor, edite seu anúncio.", "Sucesso",  JOptionPane.INFORMATION_MESSAGE);        
        view.dispose();
    }
    
    private void recusar() throws PropertyVetoException{
        new RecusarDenunciaCommand(new DenunciaService(), idDenuncia).executar();
        JOptionPane.showMessageDialog(view, "Denúncia recusada com sucesso!", "Sucesso",  JOptionPane.INFORMATION_MESSAGE);        
        view.dispose();
    }
    
    private void cancelar(){
        view.dispose();
    }
}

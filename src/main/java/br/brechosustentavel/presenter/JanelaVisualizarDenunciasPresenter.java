/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.command.commandDenuncia.CarregarDenunciasCommand;
import br.brechosustentavel.observer.Observavel;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.view.JanelaVisualizarDenunciasView;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/**
 *
 * @author kaila
 */
public class JanelaVisualizarDenunciasPresenter {
    private JanelaVisualizarDenunciasView view;
    private final String idPeca;
    private final SessaoUsuarioService sessao;

    
    public JanelaVisualizarDenunciasPresenter(java.awt.Frame janelaPai, String idPeca, SessaoUsuarioService sessao){
        this.idPeca = idPeca;
        this.sessao = sessao;

        view = new JanelaVisualizarDenunciasView(janelaPai, true);
        view.setVisible(false);       
        configurarTela(janelaPai);
        
        view.getBtnVerificar().addActionListener((ActionEvent e) -> {
            try {
                verificar();
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
            }
        });
        
        view.getBtnCancelar().addActionListener((ActionEvent e) -> {
            try {
                cancelar();
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(view, "Falha ao sair: " + ex.getMessage());
            }
        });    
        view.setVisible(true);
    }
    
    private void verificar(){
        try { 
            JTable tabela = view.getTableDenuncias();
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(view, "Selecione uma denuncia para visualizar detalhes.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idDenuncia = (int) tabela.getValueAt(linhaSelecionada, 0);
            if (idDenuncia != 0) {
                String motivo = (String) tabela.getValueAt(linhaSelecionada, 3);
                String descricao = (String) tabela.getValueAt(linhaSelecionada, 4);
                Frame janelaPai = (Frame) SwingUtilities.getWindowAncestor(view);
                new JanelaVisualizarDetalhesDenunciaPresenter(janelaPai, idPeca, motivo, descricao, idDenuncia, sessao);
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Oferta não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException e) {
            throw new RuntimeException("Falha ao aceitar oferta: " + e.getMessage());
        }
    }
    
    private void cancelar(){
        view.dispose();
    }

    private void configurarTela(java.awt.Frame janelaPai){
        view.setLocationRelativeTo(janelaPai);
        view.getTxtIDPeca().setEnabled(false);
        view.getTxtIDPeca().setText(idPeca);
        
        new CarregarDenunciasCommand(this, idPeca).executar();
    }
    
    public JanelaVisualizarDenunciasView getView(){
        return this.view;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.command.commandOferta.AceitarOfertaCommand;
import br.brechosustentavel.command.commandOferta.CarregarOfertasCommand;
import br.brechosustentavel.observer.Observavel;
import br.brechosustentavel.service.TransacaoService;
import br.brechosustentavel.service.insignia.AplicaInsigniaService;
import br.brechosustentavel.view.JanelaVisualizarOfertasView;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author kaila
 */
public class JanelaVisualizarOfertasPresenter {
    private JanelaVisualizarOfertasView view;
    private String idPeca;
    private String subcategoria;
    private String valorFinal;
    
    public JanelaVisualizarOfertasPresenter(java.awt.Frame janelaPai, String idPeca, String subcategoria, String valorFinal){
        this.idPeca = idPeca;
        this.subcategoria = subcategoria;
        this.valorFinal = valorFinal;
        
        view = new JanelaVisualizarOfertasView(janelaPai, true);
        view.setVisible(false);       
        configurarTela(janelaPai);
        
        view.getBtnAceitar().addActionListener((ActionEvent e) -> {
            try {
                aceitar();
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
            }
        });
        
        view.getBtnCancelar().addActionListener((ActionEvent e) -> {
            try {
                cancelar();
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(view, "Falha ao aceitar oferta: " + ex.getMessage());
            }
        });
        
        
        view.setVisible(true);
    }
    
    private void aceitar(){
        try { 
            JTable tabela = view.getTableOfertas();
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(view, "Selecione uma oferta para aceitar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idOferta = (int) tabela.getValueAt(linhaSelecionada, 0);
            if (idOferta != 0) {
                new AceitarOfertaCommand(new TransacaoService(new AplicaInsigniaService()), idOferta).executar(this, idPeca);
                view.dispose();
                JOptionPane.showMessageDialog(view, "Venda realizada com sucesso!", "Sucesso", JOptionPane.WARNING_MESSAGE);
                Observavel.getInstance().notifyObservers();
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
        
        view.getTxtSubcategoria().setEnabled(false);
        view.getTxtSubcategoria().setText(subcategoria);  
        
        view.getTxtValor().setEnabled(false);
        view.getTxtValor().setText("R$ " + valorFinal);  
        
        new CarregarOfertasCommand().executar(this, idPeca);
    }
    
    public JanelaVisualizarOfertasView getView(){
        return this.view;
    }
    
}

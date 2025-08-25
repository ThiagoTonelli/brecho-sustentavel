/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.command.commandHistorico.CarregarHistoricoCommand;
import br.brechosustentavel.model.Transacao;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.view.JanelaHistoricoView;
import java.awt.Frame;
import java.beans.PropertyVetoException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author thiag
 */
public class JanelaHistoricoPresenter {
    
    private JanelaHistoricoView view;
    private SessaoUsuarioService sessao;

    public JanelaHistoricoPresenter(JanelaHistoricoView view) throws PropertyVetoException{
        this.view = view;
        this.sessao = SessaoUsuarioService.getInstancia();
        view.setVisible(true);
        view.setMaximum(true);
        new CarregarHistoricoCommand().executar(this);
        
        view.getBtnAvaliar().addActionListener(e -> avaliarTransacaoSelecionada());
    }
    
    private void avaliarTransacaoSelecionada() {
        int linha = view.getTableHistorico().getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(view, "Por favor, selecione uma transação para avaliar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        DefaultTableModel model = (DefaultTableModel) view.getTableHistorico().getModel();
        Transacao transacao = (Transacao) model.getValueAt(linha, 0); 
        Frame janelaPai = (Frame) SwingUtilities.getWindowAncestor(view);
        JanelaAvaliacaoPresenter avaliacaoPresenter = new JanelaAvaliacaoPresenter(janelaPai, transacao, sessao.getUsuarioAutenticado());
        avaliacaoPresenter.getView().setVisible(true);
    }

    public JanelaHistoricoView getView() {
        return view;
    }

    public SessaoUsuarioService getSessao() {
        return sessao;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.manterTipoPecaPresenter;

import br.brechosustentavel.command.commandManterTipoPeca.SalvarTipoPecaCommand;
import br.brechosustentavel.command.commandManterTipoPeca.CarregarTiposPecaCommand;
import br.brechosustentavel.command.commandManterTipoPeca.ExcluirTipoPecaCommand;
import br.brechosustentavel.view.JanelaManterTipoPecaView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author thiag
 */
public class ManterTipoPecaPresenter {

    private JanelaManterTipoPecaView view;
    private Integer idTipoPecaSelecionado = null;

    public ManterTipoPecaPresenter() throws PropertyVetoException {
        this.view = new JanelaManterTipoPecaView();
        this.view.setMaximum(true);
        configurarTabela();
        vincularEventos();

        new CarregarTiposPecaCommand().executar(this);
    }

    private void configurarTabela() {
        view.getTableTipoPeca().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void vincularEventos() {
        view.getBtnLimpar().addActionListener(e -> limparCampos());

        view.getBtnSalvar().addActionListener(e -> {
            try {
                new SalvarTipoPecaCommand().executar(this);
                JOptionPane.showMessageDialog(view, "Tipo de peça salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                new CarregarTiposPecaCommand().executar(this); // Recarrega a tabela
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        view.getBtnExcluir().addActionListener(e -> {
            try {
                new ExcluirTipoPecaCommand().executar(this);
                limparCampos();
                new CarregarTiposPecaCommand().executar(this); // Recarrega a tabela
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erro ao excluir: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        view.getTableTipoPeca().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                preencherFormularioComLinhaSelecionada();
            }
        });
    }

    private void limparCampos() {
        this.idTipoPecaSelecionado = null;
        view.getTxtNomeTipoPeca().setText("");
        view.getTableTipoPeca().clearSelection();
        view.getBtnExcluir().setEnabled(false);
        view.getTxtNomeTipoPeca().requestFocus();
    }

    private void preencherFormularioComLinhaSelecionada() {
        int linhaSelecionada = view.getTableTipoPeca().getSelectedRow();
        if (linhaSelecionada == -1) return;

        DefaultTableModel model = (DefaultTableModel) view.getTableTipoPeca().getModel();
        
        this.idTipoPecaSelecionado = (Integer) model.getValueAt(linhaSelecionada, 0);
        String nome = (String) model.getValueAt(linhaSelecionada, 1);
        
        view.getTxtNomeTipoPeca().setText(nome);
        view.getBtnExcluir().setEnabled(true);
    }

    public Integer getIdTipoPecaSelecionado() {
        return idTipoPecaSelecionado;
    }

    public JanelaManterTipoPecaView getView() {
        return view;
    }
}

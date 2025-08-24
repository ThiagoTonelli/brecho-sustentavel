/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.manterComposicaoPresenter;

import br.brechosustentavel.command.commandManterComposicao.CarregarComposicoesCommand;
import br.brechosustentavel.command.commandManterComposicao.ExcluirComposicaoCommand;
import br.brechosustentavel.command.commandManterComposicao.SalvarComposicaoCommand;
import br.brechosustentavel.view.JanelaManterComposicaoView;
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
public class ManterComposicaoPresenter {

    private JanelaManterComposicaoView view;
    private Integer idComposicaoSelecionada = null;

    public ManterComposicaoPresenter() throws PropertyVetoException {
        this.view = new JanelaManterComposicaoView();
        this.view.setMaximum(true);
        configurarTabela();
        vincularEventos();

        new CarregarComposicoesCommand().executar(this);
    }

    private void configurarTabela() {
        view.getTableComposicao().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void vincularEventos() {
        view.getBtnLimpar().addActionListener(e -> limparCampos());

        view.getBtnSalvar().addActionListener(e -> {
            try {
                new SalvarComposicaoCommand().executar(this);
                JOptionPane.showMessageDialog(view, "Composição salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                new CarregarComposicoesCommand().executar(this);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        view.getBtnExcluir().addActionListener(e -> {
            try {
                new ExcluirComposicaoCommand().executar(this);
                limparCampos();
                new CarregarComposicoesCommand().executar(this);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erro ao excluir: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        view.getTableComposicao().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                preencherFormularioComLinhaSelecionada();
            }
        });
    }

    private void limparCampos() {
        this.idComposicaoSelecionada = null;
        view.getTxtNomeMaterial().setText("");
        view.getTxtFatorEmissao().setText("");
        view.getTableComposicao().clearSelection();
        view.getBtnExcluir().setEnabled(false);
        view.getTxtNomeMaterial().requestFocus();
    }

    private void preencherFormularioComLinhaSelecionada() {
        int linhaSelecionada = view.getTableComposicao().getSelectedRow();
        if (linhaSelecionada == -1) return;

        DefaultTableModel model = (DefaultTableModel) view.getTableComposicao().getModel();
        
        this.idComposicaoSelecionada = (Integer) model.getValueAt(linhaSelecionada, 0);
        String nome = (String) model.getValueAt(linhaSelecionada, 1);
        Double fatorEmissao = (Double) model.getValueAt(linhaSelecionada, 2);
        
        view.getTxtNomeMaterial().setText(nome);
        view.getTxtFatorEmissao().setText(String.valueOf(fatorEmissao));
        view.getBtnExcluir().setEnabled(true);
    }

    public Integer getIdComposicaoSelecionada() {
        return idComposicaoSelecionada;
    }

    public JanelaManterComposicaoView getView() {
        return view;
    }
}

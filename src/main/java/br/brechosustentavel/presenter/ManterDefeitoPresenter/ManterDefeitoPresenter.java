package br.brechosustentavel.presenter.ManterDefeitoPresenter; // Ajuste o pacote se necessário

import br.brechosustentavel.command.commandManterDefeito.CarregarDefeitosCommand;
import br.brechosustentavel.command.commandManterDefeito.ExcluirDefeitoCommand;
import br.brechosustentavel.command.commandManterDefeito.SalvarDefeitoCommand;
import br.brechosustentavel.view.JanelaManterDefeitoView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ManterDefeitoPresenter {
    private JanelaManterDefeitoView view;
    private Integer idDefeitoSelecionado = null;

    public ManterDefeitoPresenter() throws PropertyVetoException {
        this.view = new JanelaManterDefeitoView();
        this.view.setMaximum(true);
        configurarTabela();
        vincularEventos();

        new CarregarDefeitosCommand().executar(this);
    }

    private void configurarTabela() {
        view.getTableDefeitos().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void vincularEventos() {
        view.getBtnLimpar().addActionListener(e -> limparCampos());

        view.getBtnSalvar().addActionListener(e -> {
            try {
                new SalvarDefeitoCommand().executar(this);
                JOptionPane.showMessageDialog(view, "Defeito salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                new CarregarDefeitosCommand().executar(this);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        view.getBtnExcluir().addActionListener(e -> {
            try {
                new ExcluirDefeitoCommand().executar(this);
                limparCampos();
                new CarregarDefeitosCommand().executar(this);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erro ao excluir: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        view.getTableDefeitos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                preencherFormularioComLinhaSelecionada();
            }
        });
    }

    private void limparCampos() {
        this.idDefeitoSelecionado = null;
        view.getTxtNomeDefeito().setText("");
        view.getTxtAbatimento().setText(""); 
        if (view.getSelectTipoPeca().getItemCount() > 0) {
            view.getSelectTipoPeca().setSelectedIndex(0);
        }
        view.getTableDefeitos().clearSelection();
        view.getBtnExcluir().setEnabled(false);
        view.getTxtNomeDefeito().requestFocus();
    }

    private void preencherFormularioComLinhaSelecionada() {
        int linhaSelecionada = view.getTableDefeitos().getSelectedRow();
        if (linhaSelecionada == -1) return;

        DefaultTableModel model = (DefaultTableModel) view.getTableDefeitos().getModel();
        
        this.idDefeitoSelecionado = (Integer) model.getValueAt(linhaSelecionada, 0);
        String nome = (String) model.getValueAt(linhaSelecionada, 1);
        view.getTxtNomeDefeito().setText(nome);

        Number desconto = (Number) model.getValueAt(linhaSelecionada, 2);
        String tipoPeca = (String) model.getValueAt(linhaSelecionada, 3);
        
        String abatimentoPercentualStr = String.valueOf(desconto.doubleValue() * 100);
        
        view.getTxtAbatimento().setText(abatimentoPercentualStr); 
        
        view.getSelectTipoPeca().setSelectedItem(tipoPeca);
        view.getBtnExcluir().setEnabled(true);
    }

    public Integer getIdDefeitoSelecionado() {
        return idDefeitoSelecionado;
    }

    public JanelaManterDefeitoView getView() {
        return view;
    }
}
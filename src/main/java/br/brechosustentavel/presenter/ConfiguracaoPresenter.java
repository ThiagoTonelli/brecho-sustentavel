/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.configuracao.ConfiguracaoAdapter;
import br.brechosustentavel.service.GerenciadorLog;
import br.brechosustentavel.view.JanelaConfiguracaoView;
import br.brechosustentavel.view.JanelaPrincipalView;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.beans.PropertyVetoException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author thiag
 */
public class ConfiguracaoPresenter {
    private JanelaConfiguracaoView view;
    private ConfiguracaoAdapter configAdapter;

    public ConfiguracaoPresenter() throws PropertyVetoException {
        Frame janelaPai = (Frame) SwingUtilities.getWindowAncestor(new JanelaPrincipalView());
        this.view = new JanelaConfiguracaoView(janelaPai, true);
        this.configAdapter = new ConfiguracaoAdapter();
 
        carregarConfiguracaoAtual();
        view.getBtnSalvar().addActionListener(e -> salvarConfiguracao());
    }

    private void carregarConfiguracaoAtual() {
        String tipoLog = configAdapter.getValor("LOG_TIPO");
        if ("json".equalsIgnoreCase(tipoLog)) {
            view.getRadioJson().setSelected(true);
        } else {
            view.getRadioCsv().setSelected(true);
        }
    }

    private void salvarConfiguracao() {
        try {
            String novoTipo = view.getRadioCsv().isSelected() ? "csv" : "json";

            configAdapter.setValor("LOG_TIPO", novoTipo);
            configAdapter.setValor("LOG_CAMINHO", "log." + novoTipo);

            GerenciadorLog.getInstancia().carregarConfiguracao();
            JOptionPane.showMessageDialog(view, "Configuração salva! O log agora será gerado em " + novoTipo.toUpperCase());
            
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(view, "Erro ao salvar configuração: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JanelaConfiguracaoView getView() {
        return view;
    }
}
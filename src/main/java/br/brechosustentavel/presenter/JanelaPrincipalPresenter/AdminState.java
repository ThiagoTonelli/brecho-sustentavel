/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.JanelaPrincipalPresenter;

import br.brechosustentavel.presenter.dashboard.DashboardPresenter;
import br.brechosustentavel.service.SessaoUsuarioService;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author thiag
 */
public class AdminState extends JanelaPrincipalState {

    public AdminState(JanelaPrincipalPresenter presenter, SessaoUsuarioService usuarioAutenticado) {
        super(presenter, usuarioAutenticado);
        configurarTelaAdmin();
    }

    private void configurarTelaAdmin() {
        presenter.getView().setTitle("Painel do Administrador");
        
        JButton dashboardButton = presenter.getView().getButtonAdicionar();
        dashboardButton.setText("Consultar Dashboard");
        dashboardButton.setVisible(true);

        presenter.getView().getButtonVisualizar().setVisible(false);

        for (ActionListener al : dashboardButton.getActionListeners()) {
            dashboardButton.removeActionListener(al);
        }
        
        dashboardButton.addActionListener(e -> abrirDashboard());
    }

    private void abrirDashboard() {
        try {
            DashboardPresenter dashboardPresenter = new DashboardPresenter();
            presenter.setFrame(dashboardPresenter.getView());
            dashboardPresenter.getView().setVisible(true);
            dashboardPresenter.getView().setMaximum(true);
        } catch (PropertyVetoException ex) {
            JOptionPane.showMessageDialog(presenter.getView(), "Erro ao abrir o dashboard: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void carregar() {
        presenter.getView().getjTable1().setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Dados do Administrador"}
        ));
    }
}

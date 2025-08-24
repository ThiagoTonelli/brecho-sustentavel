/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.janelaPrincipalPresenter;

import br.brechosustentavel.presenter.ManterDefeitoPresenter.ManterDefeitoPresenter;
import br.brechosustentavel.presenter.dashboard.DashboardPresenter;
import br.brechosustentavel.presenter.manterTipoPecaPresenter.ManterTipoPecaPresenter;
import br.brechosustentavel.service.SessaoUsuarioService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author thiag
 */
public class AdminState extends JanelaPrincipalState {

    public AdminState(JanelaPrincipalPresenter presenter, SessaoUsuarioService usuarioAutenticado) throws PropertyVetoException {
        super(presenter, usuarioAutenticado);
        configurarTelaAdmin();
        
        
        presenter.getView().getButtonAdicionar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                abrirDashboard();
            }
        });
        presenter.getView().getButtonVisualizar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                manterDefeito();
            }
        });
        presenter.getView().getButtonManterTipo().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                manterTipo();
            }
        });
    }

    private void configurarTelaAdmin() throws PropertyVetoException {
        presenter.getView().setVisible(false);
        JButton dashboardButton = presenter.getView().getButtonAdicionar();
        JButton manterDefeitosButton = presenter.getView().getButtonVisualizar();
        for (ActionListener al : dashboardButton.getActionListeners()) {
            dashboardButton.removeActionListener(al);
        }
        
        manterDefeitosButton.setText("Manter Tabela de Defeitos");
        manterDefeitosButton.setVisible(true);
        presenter.getView().setTitle("Painel do Administrador");
        presenter.getView().setMaximum(true);

        dashboardButton.setText("Consultar Dashboard");
        dashboardButton.setVisible(true);
        presenter.getView().getButtonVisualizar().setVisible(true);
        
        presenter.getView().setVisible(true);
 
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
    
    private void manterDefeito(){
            try {
                ManterDefeitoPresenter presenterDefeitos = new ManterDefeitoPresenter();
                presenter.setFrame(presenterDefeitos.getView());
                presenterDefeitos.getView().setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(presenter.getView(), "Erro ao abrir manter defeitos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
    }
    
    private void manterTipo(){
            try {
                ManterTipoPecaPresenter presenterTipos = new ManterTipoPecaPresenter();
                presenter.setFrame(presenterTipos.getView());
                presenterTipos.getView().setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(presenter.getView(), "Erro ao abrir manter defeitos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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

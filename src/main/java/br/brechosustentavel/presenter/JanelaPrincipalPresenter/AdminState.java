/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.janelaPrincipalPresenter;

import br.brechosustentavel.command.commandMenu.ExportarLogCommand;
import br.brechosustentavel.command.commandMenu.ExportarVendasCommand;
import br.brechosustentavel.presenter.ConfiguracaoPresenter;
import br.brechosustentavel.presenter.ManterDefeitoPresenter.ManterDefeitoPresenter;
import br.brechosustentavel.presenter.dashboard.DashboardPresenter;
import br.brechosustentavel.presenter.manterComposicaoPresenter.ManterComposicaoPresenter;
import br.brechosustentavel.presenter.manterTipoPecaPresenter.ManterTipoPecaPresenter;
import br.brechosustentavel.service.LimparListenerService;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.view.JanelaPrincipalView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 *
 * @author thiag
 */
public class AdminState extends JanelaPrincipalState {

    public AdminState(JanelaPrincipalPresenter presenter, SessaoUsuarioService usuarioAutenticado) throws PropertyVetoException {
        super(presenter, usuarioAutenticado);
        presenter.getView().setVisible(false);
        configurarTelaAdmin();
        
        presenter.getView().getOpcDashboard().addActionListener((ActionEvent e) -> {
            abrirDashboard();
        });
        
        presenter.getView().getButtonVisualizar().addActionListener((ActionEvent e) -> {
            manterDefeito();
        });
        
        presenter.getView().getButtonManterTipo().addActionListener((ActionEvent e) -> {
            manterTipo();
        });
        
        presenter.getView().getButtonManterComposicao().addActionListener((ActionEvent e) -> {
            manterComposicao();
        });
        
        presenter.getView().getOpcExportarVendas().addActionListener((ActionEvent e) -> {
            exportarVendas();
        });
        
        presenter.getView().getMenuConfiguracao().addMenuListener(new MenuListener(){
            @Override
            public void menuSelected(MenuEvent e) {
                abrirTelaConfiguracao();
            }
            @Override
            public void menuDeselected(MenuEvent e) {}

            @Override
            public void menuCanceled(MenuEvent e) {}
        });
        
        presenter.getView().getOpcExportarLogs().addActionListener(e -> {
            new ExportarLogCommand().executar(presenter.getView());
        });
        
        presenter.getView().setVisible(true);
    }

    private void configurarTelaAdmin() throws PropertyVetoException {
        JanelaPrincipalView view = presenter.getView();
        view.setTitle("Painel do Administrador");
        view.setMaximum(true);
        
        //Limpa listeners
        LimparListenerService limpar = new LimparListenerService();
        limpar.limparListener(view.getButtonAdicionar());
        limpar.limparListener(view.getButtonVisualizar());
        limpar.limparListener(view.getButtonManterComposicao());
        limpar.limparListener(view.getButtonManterTipo());

        //Configura botoes
        view.getButtonAdicionar().setVisible(false);
        view.getButtonAdicionar().setEnabled(false);
        view.getButtonVisualizar().setText("Gerenciar Defeitos");
        view.getButtonVisualizar().setVisible(true);
        view.getButtonVisualizar().setEnabled(true);
        view.getButtonManterComposicao().setText("Gerenciar Composições");
        view.getButtonManterComposicao().setVisible(true);
        view.getButtonManterComposicao().setEnabled(true);
        view.getButtonManterTipo().setText("Gerenciar Tipos de Peças");
        view.getButtonManterTipo().setVisible(true);
        view.getButtonManterTipo().setEnabled(true);
        view.getPnlFiltros().setVisible(false);

        //Configura menus
        view.getMenuConfiguracao().setVisible(true);
        view.getMenuPerfil().setVisible(false);
        view.getMenuRelatorios().setVisible(true);
        view.getMenuTransacao().setVisible(false);
        view.getOpcVisualizarPerfil().setVisible(false);
        
        //Configura label
        view.getLblTitulo().setText("Bem-vindo ao Painel Administrativo");
        view.getLblInformacao().setText("Veja todos os anúncios dos vendedores.");
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
            } catch (PropertyVetoException ex) {
                JOptionPane.showMessageDialog(presenter.getView(), "Erro ao abrir manter defeitos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
    }
    
    private void manterTipo(){
        try {
            ManterTipoPecaPresenter presenterTipos = new ManterTipoPecaPresenter();
            presenter.setFrame(presenterTipos.getView());
            presenterTipos.getView().setVisible(true);
        } catch (PropertyVetoException ex) {
            JOptionPane.showMessageDialog(presenter.getView(), "Erro ao abrir manter defeitos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void manterComposicao(){
        try {
            ManterComposicaoPresenter presenterComposicao = new ManterComposicaoPresenter();
            presenter.setFrame(presenterComposicao.getView());
            presenterComposicao.getView().setVisible(true);
        } catch (PropertyVetoException ex) {
            JOptionPane.showMessageDialog(presenter.getView(), "Erro ao abrir manter defeitos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void abrirTelaConfiguracao() {
        try {
            ConfiguracaoPresenter configPresenter = new ConfiguracaoPresenter();
            configPresenter.getView().setLocationRelativeTo(presenter.getView());
            configPresenter.getView().setVisible(true);
        } catch (PropertyVetoException ex) {
            JOptionPane.showMessageDialog(presenter.getView(), 
                "Erro ao abrir a tela de configuração: " + ex.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void exportarVendas(){
        new ExportarVendasCommand().executar(presenter.getView());
    }
    
    @Override
    public void carregar() {
        presenter.getView().getjTable1().setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Dados do Administrador"}
        ));
    }
}

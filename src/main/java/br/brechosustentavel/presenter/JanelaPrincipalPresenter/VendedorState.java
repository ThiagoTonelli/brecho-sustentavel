/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.janelaPrincipalPresenter;

import br.brechosustentavel.command.commandPrincipal.AdicionarPerfilCommand;
import br.brechosustentavel.command.commandPrincipal.CarregarAnunciosVendedorCommand;
import br.brechosustentavel.presenter.JanelaHistoricoPresenter;
import br.brechosustentavel.presenter.JanelaVisualizarDenunciasPresenter;
import br.brechosustentavel.presenter.JanelaVisualizarOfertasPresenter;
import br.brechosustentavel.presenter.JanelaVisualizarPerfilPresenter;
import br.brechosustentavel.presenter.dashboard.DashboardPresenter;
import br.brechosustentavel.presenter.manterAnuncioPresenter.InclusaoAnuncioState;
import br.brechosustentavel.presenter.manterAnuncioPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.presenter.manterAnuncioPresenter.VisualizacaoAnuncioState;
import br.brechosustentavel.service.LimparListenerService;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.view.JanelaHistoricoView;
import br.brechosustentavel.view.JanelaPrincipalView;
import br.brechosustentavel.view.JanelaVisualizarPerfilView;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 *
 * @author thiag
 */
public class VendedorState extends JanelaPrincipalState{
    
    public VendedorState(JanelaPrincipalPresenter presenter, SessaoUsuarioService usuarioAutenticado) throws PropertyVetoException{
        super(presenter, usuarioAutenticado);
        
        JanelaPrincipalView view = presenter.getView();
        view.setVisible(false);
        configurarTela(view);

        view.getButtonAdicionar().addActionListener((ActionEvent e) -> {
            criar();
        });
        
        view.getButtonVisualizar().addActionListener((ActionEvent e) -> {
            visualizar();
        });
        
        view.getButtonManterTipo().addActionListener((ActionEvent e) -> {
            visualizarOfertas();
        });
        
        view.getButtonManterComposicao().addActionListener((ActionEvent e) -> {
            visualizarDenuncias();
        });
        
        view.getOpcAvaliar().addActionListener(e -> {
            visualizarTransacoes();
        });
        
        view.getOpcDashboard().addActionListener((ActionEvent e) -> {
            abrirDashboard();
        });
        
        view.getMenuVisualizarPerfil().addMenuListener(new MenuListener(){
            @Override
            public void menuSelected(MenuEvent e) {
                JanelaVisualizarPerfilView visualizar = new JanelaVisualizarPerfilPresenter().getView();
                presenter.setFrame(visualizar);
                try {
                    visualizar.setVisible(true);
                    visualizar.setSelected(true);
                    visualizar.setMaximum(true);
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(VendedorState.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            @Override
            public void menuDeselected(MenuEvent e) {}

            @Override
            public void menuCanceled(MenuEvent e) {}
        });
        
        presenter.getView().getOpcAddPerfil().addActionListener(e -> {
            new AdicionarPerfilCommand().executar(presenter);
        });
        
        view.setVisible(true);
    }

    @Override
    public void visualizar() {
        try{
            JTable tabela = presenter.getView().getjTable1();
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(presenter.getView(), "Selecione um anúncio para visualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String idPeca = (String) tabela.getValueAt(linhaSelecionada, 0);
            if (idPeca != null) {
                ManterAnuncioPresenter anuncioPresenter = new ManterAnuncioPresenter(usuarioAutenticado);
                presenter.setFrame(anuncioPresenter.getView());
                anuncioPresenter.setIdPeca(idPeca);
                anuncioPresenter.setEstadoVendedor(new VisualizacaoAnuncioState(anuncioPresenter));
            } else {
                JOptionPane.showMessageDialog(presenter.getView(), "Anúncio não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (PropertyVetoException ex) {
            throw new RuntimeException("Erro ao visualizar anuncio", ex);
        }
    }
    
    @Override
    public void criar(){
        try {
            ManterAnuncioPresenter anuncioPresenter = new ManterAnuncioPresenter(usuarioAutenticado);
            presenter.setFrame(anuncioPresenter.getView());
            anuncioPresenter.setEstadoVendedor(new InclusaoAnuncioState(anuncioPresenter));
        } catch (PropertyVetoException ex) {
            throw new RuntimeException("Erro ao criar novo anuncio", ex);
        }
    }
    
    @Override
    public void visualizarOfertas(){
        JTable tabela = presenter.getView().getjTable1();
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(presenter.getView(), "Selecione um anúncio para visualizar as ofertas.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String idPeca = (String) tabela.getValueAt(linhaSelecionada, 0);
        if (idPeca != null) {
            String subcategoria = (String) tabela.getValueAt(linhaSelecionada, 1);
            String valorFinal = (String) tabela.getValueAt(linhaSelecionada, 8);
            Frame janelaPai = (Frame) SwingUtilities.getWindowAncestor(presenter.getView());
            new JanelaVisualizarOfertasPresenter(janelaPai, idPeca, subcategoria, valorFinal);
        } else {
            JOptionPane.showMessageDialog(presenter.getView(), "Ofertas não encontradas.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void visualizarDenuncias(){
        JTable tabela = presenter.getView().getjTable1();
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(presenter.getView(), "Selecione um anúncio para visualizar as denuncias.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String idPeca = (String) tabela.getValueAt(linhaSelecionada, 0);
        if (idPeca != null) {
            Frame janelaPai = (Frame) SwingUtilities.getWindowAncestor(presenter.getView());
            new JanelaVisualizarDenunciasPresenter(janelaPai, idPeca, usuarioAutenticado);
        } else {
            JOptionPane.showMessageDialog(presenter.getView(), "Denuncias não encontradas.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void visualizarTransacoes(){
        try {
            JanelaHistoricoView view = new JanelaHistoricoView();
            presenter.setFrame(view);
            new JanelaHistoricoPresenter(view);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(VendedorState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public void carregar(){
        try {
            new CarregarAnunciosVendedorCommand().executar(presenter);
        } catch (Exception e) {
            System.err.println("Erro ao recarregar anúncios: " + e.getMessage());
        }
    }
    
    private void configurarTela(JanelaPrincipalView view) throws PropertyVetoException{
        view.setMaximum(true);
        carregar();
        
        //Limpa listeners
        LimparListenerService limpar = new LimparListenerService();
        limpar.limparListener(view.getButtonAdicionar());
        limpar.limparListener(view.getButtonVisualizar());
        limpar.limparListener(view.getButtonManterComposicao());
        limpar.limparListener(view.getButtonManterTipo());
        limpar.limparListener(view.getBtnFiltrar());
        
        //Configura panel de filtros
        view.getPnlFiltros().setEnabled(false);
        view.getPnlFiltros().setVisible(false);
        
        //Configura botoes
        view.getButtonAdicionar().setVisible(true);
        view.getButtonVisualizar().setVisible(true);
        view.getButtonManterComposicao().setVisible(true);
        view.getButtonManterTipo().setVisible(true);        
        view.getButtonAdicionar().setText("Adicionar Anúncio");
        view.getButtonVisualizar().setText("Visualizar Anúncio");
        view.getButtonManterComposicao().setText("Visualizar Denuncias");
        view.getButtonManterTipo().setText("Visualizar Ofertas");
        view.getButtonAdicionar().setEnabled(true);
        view.getButtonVisualizar().setEnabled(true);
        view.getButtonManterComposicao().setEnabled(true);
        view.getButtonManterTipo().setEnabled(true);
        
        //Configura menus
        view.getMenuConfiguracao().setVisible(false);
        view.getMenuPerfil().setVisible(true);
        view.getMenuVisualizarPerfil().setVisible(true);
        view.getMenuRelatorios().setVisible(false);
        view.getMenuTransacao().setVisible(true);
        view.getMenuTransacao().setText("Minhas vendas");
        view.getOpcAvaliar().setText("Avaliar vendas");
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
}

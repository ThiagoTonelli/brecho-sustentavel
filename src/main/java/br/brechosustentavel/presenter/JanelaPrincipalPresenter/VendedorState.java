/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.janelaPrincipalPresenter;

import br.brechosustentavel.command.commandPrincipal.AdicionarPerfilCommand;
import br.brechosustentavel.command.commandPrincipal.CarregarAnunciosVendedorCommand;
import br.brechosustentavel.presenter.JanelaVisualizarOfertasPresenter;
import br.brechosustentavel.presenter.JanelaVisualizarPerfilPresenter;
import br.brechosustentavel.presenter.manterAnuncioPresenter.InclusaoAnuncioState;
import br.brechosustentavel.presenter.manterAnuncioPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.presenter.manterAnuncioPresenter.VisualizacaoAnuncioState;
import br.brechosustentavel.service.SessaoUsuarioService;
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
            JanelaVisualizarOfertasPresenter visualizarOfertasPresenter = new JanelaVisualizarOfertasPresenter(janelaPai, idPeca, subcategoria, valorFinal);
        } else {
            JOptionPane.showMessageDialog(presenter.getView(), "Ofertas não encontradas.", "Erro", JOptionPane.ERROR_MESSAGE);
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
     
        //Configura botoes
        view.getButtonManterComposicao().setVisible(false);
        view.getButtonManterTipo().setText("Visualizar Ofertas");
    }
}

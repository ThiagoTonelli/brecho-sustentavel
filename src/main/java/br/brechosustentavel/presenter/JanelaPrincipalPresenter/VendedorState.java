/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.JanelaPrincipalPresenter;

import br.brechosustentavel.commandPrincipal.CarregarAnunciosVendedorCommand;
import br.brechosustentavel.presenter.JanelaVisualizarPerfilPresenter;
import br.brechosustentavel.presenter.ManterAnuncioPresenter.EdicaoAnuncioState;
import br.brechosustentavel.presenter.ManterAnuncioPresenter.InclusaoAnuncioState;
import br.brechosustentavel.presenter.ManterAnuncioPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.view.IJanelaPrincipalView;
import br.brechosustentavel.view.JanelaPrincipalView;
import br.brechosustentavel.view.JanelaVisualizarPerfilView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 *
 * @author thiag
 */
public class VendedorState extends JanelaPrincipalState{
    
    public VendedorState(JanelaPrincipalPresenter presenter, SessaoUsuarioService usuarioAutenticado) throws PropertyVetoException{
        super(presenter);
        carregar();
        JanelaPrincipalView view = presenter.getView();
        view.setMaximum(true);
        view.setVisible(true);
        view.getButtonAdicionar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                criar();
            }
        });
        view.getButtonVisualizar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                editar();
            }
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
    }
    
    @Override
    public void criar(){
        try {
            ManterAnuncioPresenter anuncioPresenter = new ManterAnuncioPresenter();
            presenter.setFrame(anuncioPresenter.getView());
            anuncioPresenter.setEstadoVendedor(new InclusaoAnuncioState(anuncioPresenter));
        } catch (PropertyVetoException ex) {
            throw new RuntimeException("erro ao criar novo anuncio", ex);
        }
    }
    
    @Override
    public void editar(){
        try {
            ManterAnuncioPresenter presenter = new ManterAnuncioPresenter();
            presenter.setEstadoVendedor(new EdicaoAnuncioState(presenter));
        } catch (PropertyVetoException ex) {
            throw new RuntimeException("erro ao editar anuncio", ex);
        }
    }
    
    @Override
    public void carregar(){
        try {
            new CarregarAnunciosVendedorCommand().executar(presenter, SessaoUsuarioService.getInstancia());
        } catch (Exception e) {
            System.err.println("Erro ao recarregar anúncios: " + e.getMessage());
        }
    }
}

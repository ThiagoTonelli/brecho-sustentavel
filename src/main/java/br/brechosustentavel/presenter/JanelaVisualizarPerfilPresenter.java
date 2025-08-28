/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.command.commandPerfil.CarregarDadosPerfilCommand;
import br.brechosustentavel.observer.Observador;
import br.brechosustentavel.observer.Observavel;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.view.JanelaVisualizarPerfilView;

/**
 *
 * @author kaila
 */
public class JanelaVisualizarPerfilPresenter implements Observador{
    private JanelaVisualizarPerfilView view;
    private SessaoUsuarioService sessao;

    public JanelaVisualizarPerfilPresenter(){
        sessao = SessaoUsuarioService.getInstancia();
        Observavel.getInstance().addObserver(this);
        view = new JanelaVisualizarPerfilView();
        view.setVisible(false);
        
        try{
            new CarregarDadosPerfilCommand().executar(this, sessao);
        } catch(Exception e){
            throw new RuntimeException("Não foi possível carregar os dados do usuário: " + e.getMessage());
        }     
        view.setVisible(true);
    }
    
    public JanelaVisualizarPerfilView getView(){
        return view;
    }

    @Override
    public void atualizar() {
        new CarregarDadosPerfilCommand().executar(this, sessao);
        view.repaint();
    }
}

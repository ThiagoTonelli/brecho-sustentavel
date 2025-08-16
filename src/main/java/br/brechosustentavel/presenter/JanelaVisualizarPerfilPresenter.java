/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.model.Vendedor;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.view.JanelaVisualizarPerfilView;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public class JanelaVisualizarPerfilPresenter {
    private JanelaVisualizarPerfilView view;
    private SessaoUsuarioService sessao;
    
    public JanelaVisualizarPerfilPresenter(){
        new TelaPrincipalPresenter();
        sessao = SessaoUsuarioService.getInstancia();
        
        view = new JanelaVisualizarPerfilView();
        view.setVisible(false);
        
        try{
            Usuario usuario = sessao.getUsuarioAutenticado();
            view.getLblNome().setText(usuario.getNome());
            view.getLblTelefone().setText(usuario.getTelefone());
            
            RepositoryFactory fabrica = RepositoryFactory.getInstancia();
            if(sessao.getTipoPerfil().equalsIgnoreCase("Vendedor")){
                dadosVendedor(fabrica, usuario);
            }
        view.setVisible(true);
        } catch(Exception e){
            throw new RuntimeException("Não foi possível encontrador os dados do usuário: " + e.getMessage());
        }
    }
    
    private void dadosComprador(RepositoryFactory fabrica, Usuario usuario){}
    
    private void dadosVendedor(RepositoryFactory fabrica, Usuario usuario){
        Optional<Vendedor> vendedorOpt = fabrica.getVendedorRepository().buscarPorId(usuario.getId());
                vendedorOpt.ifPresent(vendedor -> {
                    view.getLblNivel().setText(String.valueOf(vendedor.getNivel()));
                    view.getLblEstrelas().setText(String.valueOf(vendedor.getEstrelas()));
                    view.getLblGWP().setText(String.valueOf(vendedor.getGwpContribuido()));
        });
    }
    
    public JanelaVisualizarPerfilView getView(){
        return view;
    }
}

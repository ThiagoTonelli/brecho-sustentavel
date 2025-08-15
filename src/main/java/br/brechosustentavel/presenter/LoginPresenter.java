/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.repository.ICompradorRepository;
import br.brechosustentavel.repository.IVendedorRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.service.AutenticacaoService;
import br.brechosustentavel.service.CadastroService;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.view.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author kaila
 */
public class LoginPresenter {
    
    private LoginView view;
    private AutenticacaoService autenticacaoService;
    private SessaoUsuarioService sessaoUsuarioService;
    private Usuario usuario;
    private IVendedorRepository vendedorRepository;
    private ICompradorRepository compradorRepository;
    
    public LoginPresenter() {
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        vendedorRepository = fabrica.getVendedorRepository();
        compradorRepository = fabrica.getCompradorRepository();
        
        view = new LoginView();
        view.setVisible(false);
        view.getBtnEntrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    autenticar();
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        
        view.getBtnCadastrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cadastrar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        
        view.setVisible(true);
    }
    
    private void autenticar() {
        String email = view.getTxtEmail().getText();
        String senha = view.getTxtSenha().getText();
        
        if(autenticacaoService == null){
            throw new RuntimeException("Passe uma instancia de AutenticacaoService válida.");
        }

        try {
            Usuario usuarioAutenticado = autenticacaoService.autenticar(email, senha);
            
            boolean isComprador = compradorRepository.buscarPorId(usuarioAutenticado.getId()).isPresent();
            boolean isVendedor = vendedorRepository.buscarPorId(usuarioAutenticado.getId()).isPresent();
            
            sessaoUsuarioService.getInstancia().setUsuarioAutenticado(usuarioAutenticado);
            sessaoUsuarioService.setAutenticado(true);
            
             if(isVendedor && isComprador){
                new JanelaEscolhaPerfilPresenter();
             } else if(isVendedor) {
                sessaoUsuarioService.getInstancia().setTipoPerfil("Vendedor");
                new JanelaEscolhaPerfilPresenter();
             } else if(isComprador) {
                sessaoUsuarioService.getInstancia().setTipoPerfil("Comprador");
                new JanelaEscolhaPerfilPresenter();
             } else {
                 new JanelaEscolhaPerfilPresenter();
             }
       
            view.dispose();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(view, "Falha na autenticação: " + e.getMessage());
        }
        
    }
    
    private void cadastrar(){
        new CadastroPresenter(new CadastroService());
        view.dispose();
    }
    
    public void setAutenticacaoService(AutenticacaoService autenticacaoService){
        this.autenticacaoService = autenticacaoService;
    }
    
    public void setSessaoUsuarioService(SessaoUsuarioService sessaoUsuarioService){
        this.sessaoUsuarioService = sessaoUsuarioService;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.presenter.janelaPrincipalPresenter.JanelaPrincipalPresenter;
import br.brechosustentavel.repository.ICompradorRepository;
import br.brechosustentavel.repository.IVendedorRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.service.AutenticacaoService;
import br.brechosustentavel.service.CadastroService;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.service.hash.HashService;
import br.brechosustentavel.service.verificador_telefone.VerificadorTelefoneService;
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
    private RepositoryFactory repositoryFactory;
    private HashService hashService;
    private VerificadorTelefoneService verificadorTelefoneService;
    private SessaoUsuarioService sessao;
    private AutenticacaoService autenticacaoService;
    
    public LoginPresenter(RepositoryFactory repositoryFactory, HashService hashService, VerificadorTelefoneService verificadorTelefoneService, 
            SessaoUsuarioService sessao, AutenticacaoService autenticacaoService) {
        this.repositoryFactory = repositoryFactory;
        this.hashService = hashService;
        this.verificadorTelefoneService = verificadorTelefoneService;
        this.sessao = sessao;
        this.autenticacaoService = autenticacaoService;
        
        view = new LoginView();
        view.setLocationRelativeTo(null);
        view.setSize(1150, 800);
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
        IVendedorRepository vendedorRepository = repositoryFactory.getVendedorRepository();
        ICompradorRepository compradorRepository = repositoryFactory.getCompradorRepository();
        
        String email = view.getTxtEmail().getText();
        String senha = view.getTxtSenha().getText();
        
        try {
            Usuario usuarioAutenticado = autenticacaoService.autenticar(email, senha);
            usuarioAutenticado.setVendedor(vendedorRepository.buscarPorId(usuarioAutenticado.getId()).orElse(null));
            usuarioAutenticado.setComprador(compradorRepository.buscarPorId(usuarioAutenticado.getId()).orElse(null));
            
            boolean isComprador = usuarioAutenticado.getComprador().isPresent() ;
            boolean isVendedor = usuarioAutenticado.getVendedor().isPresent();
            boolean isAdmin = usuarioAutenticado.isAdmin();
            
            sessao.setUsuarioAutenticado(usuarioAutenticado);
            sessao.setAutenticado(true);

            if(isVendedor && isComprador){
                sessao.setTipoPerfil(null);
                new JanelaEscolhaPerfilPresenter(sessao, new TelaPrincipalPresenter());
            }else{ 
                if(isVendedor){
                    sessao.setTipoPerfil("Vendedor");   
                } else if(isComprador){
                    sessao.setTipoPerfil("Comprador");
                } else if (isAdmin){
                    sessao.setTipoPerfil("Admin");
                }
                new JanelaPrincipalPresenter(sessao, new TelaPrincipalPresenter()); 
            }
            view.dispose();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(view, "Falha na autenticação: " + e.getMessage());
        }   
    }
    
    private void cadastrar(){
        new CadastroPresenter(new CadastroService(hashService, verificadorTelefoneService, repositoryFactory), view);
    }    
}

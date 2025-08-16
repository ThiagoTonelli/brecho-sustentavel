/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.presenter.JanelaPrincipalPresenter.CompradorState;
import br.brechosustentavel.presenter.JanelaPrincipalPresenter.JanelaPrincipalPresenter;
import br.brechosustentavel.presenter.JanelaPrincipalPresenter.VendedorState;
import br.brechosustentavel.repository.ICompradorRepository;
import br.brechosustentavel.repository.IUsuarioRepository;
import br.brechosustentavel.repository.IVendedorRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.service.AutenticacaoService;
import br.brechosustentavel.service.CadastroService;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.service.hash.BCryptAdapter;
import br.brechosustentavel.view.LoginView;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author kaila
 */
public class LoginPresenter {
    
    private LoginView view;
    private SessaoUsuarioService sessao = SessaoUsuarioService.getInstancia();
    private Usuario usuario;
    private IVendedorRepository vendedorRepository;
    private ICompradorRepository compradorRepository;
    private IUsuarioRepository usuarioRepository;
    
    public LoginPresenter() {
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        vendedorRepository = fabrica.getVendedorRepository();
        compradorRepository = fabrica.getCompradorRepository();
        usuarioRepository = fabrica.getUsuarioRepository();
        
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
        AutenticacaoService autenticacaoService = new AutenticacaoService(usuarioRepository, new BCryptAdapter());
        TelaPrincipalPresenter telaPrincipal = new TelaPrincipalPresenter();
        String email = view.getTxtEmail().getText();
        String senha = view.getTxtSenha().getText();
        
        try {
            Usuario usuarioAutenticado = autenticacaoService.autenticar(email, senha);
            
            boolean isComprador = compradorRepository.buscarPorId(usuarioAutenticado.getId()).isPresent();
            boolean isVendedor = vendedorRepository.buscarPorId(usuarioAutenticado.getId()).isPresent();
            
            sessao.setUsuarioAutenticado(usuarioAutenticado);
            sessao.setAutenticado(true);

            if(isVendedor && isComprador){
                sessao.setTipoPerfil(null);
                new JanelaEscolhaPerfilPresenter();
            } else if(isVendedor){
                sessao.setTipoPerfil("Vendedor");   
            } else if(isComprador){
                sessao.setTipoPerfil("Comprador");
            } else {
                sessao.setTipoPerfil("Admin");
            }
            JanelaPrincipalPresenter janelaPrincipal = new JanelaPrincipalPresenter(sessao, telaPrincipal);
            view.dispose();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(view, "Falha na autenticação: " + e.getMessage());
        }   
    }
    
    private void cadastrar(){
        new CadastroPresenter(new CadastroService());
        view.dispose();
    }    
}

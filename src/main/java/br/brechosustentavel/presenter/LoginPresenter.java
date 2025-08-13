/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.service.AutenticacaoService;
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
    
    public LoginPresenter(AutenticacaoService autenticacaoService, SessaoUsuarioService sessaoUsuarioService) {
        this.autenticacaoService = autenticacaoService;
        this.sessaoUsuarioService = sessaoUsuarioService;
        
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
        
        view.getTxtEmail().setText("");
        view.getTxtSenha().setText("");
        view.setVisible(true);
    }
    
    private void autenticar() {
        String email = view.getTxtEmail().getText();
        String senha = view.getTxtSenha().getText();
        
        Usuario usuarioAutenticado = autenticacaoService.autenticar(email, senha);
        
        try {
            sessaoUsuarioService.getInstancia().setUsuarioAutenticado(usuarioAutenticado);
            sessaoUsuarioService.setAutenticado(true);
            
            new JanelaEscolhaPerfilPresenter();
            view.dispose();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(view, "Falha na autenticação: " + e.getMessage());
        }
        
    }
    
    private void cadastrar(){
    
    }
    
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.service.CadastroService;
import br.brechosustentavel.view.CadastroView;
import br.brechosustentavel.view.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author kaila
 */
public class CadastroPresenter {
    private CadastroView view;
    private CadastroService cadastroService;
    
    public CadastroPresenter(CadastroService cadastroService){
        this.cadastroService = cadastroService;

        view = new CadastroView();
        view.setVisible(false);
        
        //Caso não tenha usuario cadastrado, não aparece opção de escolha do tipo de perfil
        if(cadastroService.isVazio()){
            view.getRadioComprador().setVisible(false);
            view.getRadioVendedor().setVisible(false);
            view.getRadioCompradorVendedor().setVisible(false);
            view.getLblPerfil().setVisible(false);
        }
        
        view.getBtnCadastrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cadastrar();
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        
        view.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cancelar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
        
        view.setVisible(true);
    }
    
    private void cadastrar(){
        String nome = view.getTxtNome().getText();
        String telefone = view.getTxtTelefone().getText();
        String email = view.getTxtEmail().getText();
        String senha = view.getTxtSenha().getText();
        String confirmacaoSenha = view.getTxtConfirmacaoSenha().getText();
        
        try {
            if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
                throw new RuntimeException("Nome, email e senha são campos obrigatórios.");
            }
            if (!email.contains("@") || !email.contains(".")){
                throw new RuntimeException("Email inválido.");
            }
            if (!senha.equals(confirmacaoSenha)) {
                throw new RuntimeException("As senhas não conferem.");
            }
            
            boolean isComprador = false;
            boolean isVendedor = false;
            if(!cadastroService.isVazio()){                
                if(view.getRadioComprador().isSelected()) {
                    isComprador = true;
                } else if(view.getRadioVendedor().isSelected()) {
                    isVendedor = true;
                } else if(view.getRadioCompradorVendedor().isSelected()) {
                    isComprador = true;
                    isVendedor = true;
                }
                else{
                    throw new RuntimeException("Selecione um perfil.");
                }
            }

            cadastroService.cadastrar(new Usuario(nome, telefone, email, senha), isComprador, isVendedor);
            JOptionPane.showMessageDialog(view, "Cadastro realizado com sucesso!");
            view.dispose();
            
           // new JanelaEscolhaPerfilPresenter(new TelaPrincipalPresenter()); 
            
        } catch(Exception e) { 
            JOptionPane.showMessageDialog(view, "" + e.getMessage());
        }
    }
    
    private void cancelar(){
        new LoginView().setVisible(true);
        view.dispose();
    }
    
}

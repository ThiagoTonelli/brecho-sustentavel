/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.service.CadastroService;
import br.brechosustentavel.view.CadastroView;
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
    
    public CadastroPresenter(CadastroService cadastroService, java.awt.Frame janelaPai){
        this.cadastroService = cadastroService;
        
        view = new CadastroView(janelaPai, true);
        view.setLocationRelativeTo(janelaPai);
        view.setSize(1150, 800);
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
                    JOptionPane.showMessageDialog(view, "Falha ao cadastrar: " + ex.getMessage());
                }
            }
        });
        
        view.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cancelar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Falha ao cancelar: " + ex.getMessage());
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
                   
        String opcao = "";
        if(!cadastroService.isVazio()){      
            if(view.getRadioComprador().isSelected()) {
               opcao = "Comprador";
            } else if(view.getRadioVendedor().isSelected()) {
                opcao = "Vendedor";
            } else if(view.getRadioCompradorVendedor().isSelected()) {
                opcao = "Ambos";
            }
        }

        cadastroService.cadastrar(new Usuario(nome, telefone, email, senha), confirmacaoSenha, opcao);
        JOptionPane.showMessageDialog(view, "Cadastro realizado com sucesso!");
        view.dispose();            
    }
    
    private void cancelar(){
        view.dispose();
    }  
}

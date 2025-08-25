/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.command.commandAvaliacao.SalvarAvaliacaoCommand;
import br.brechosustentavel.model.Transacao;
import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.view.JanelaAvaliacaoView;
import javax.swing.JOptionPane;

/**
 *
 * @author thiag
 */
public class JanelaAvaliacaoPresenter {
    private JanelaAvaliacaoView view;
    private Transacao transacao; 
    private Usuario autor;      

    public JanelaAvaliacaoPresenter(java.awt.Frame janelaPai, Transacao transacao, Usuario autor) {
        this.view = new JanelaAvaliacaoView(janelaPai, true);
        view.setLocationRelativeTo(janelaPai);
        this.transacao = transacao;
        this.autor = autor;
        
        configurarTela();
        vincularEventos();
    }

    private void configurarTela() {
        view.setTitle("Avaliar Transação #" + transacao.getId());
    }
    
    private void vincularEventos() {
        view.getBtnEnviar().addActionListener(e -> {
            try {
                new SalvarAvaliacaoCommand().executar(this);
                
                JOptionPane.showMessageDialog(view, "Avaliação enviada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                view.dispose(); 
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erro ao enviar avaliação: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        view.getBtnCancelar().addActionListener(e -> {
            try {
                view.dispose(); 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Erro ao cancelar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Getters para que o Command possa aceder ao contexto
    public Transacao getTransacao() {
        return transacao;
    }

    public Usuario getAutor() {
        return autor;
    }
    
    public JanelaAvaliacaoView getView() {
        return view;
    }
}

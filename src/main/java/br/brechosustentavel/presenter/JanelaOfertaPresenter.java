/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.view.JanelaOfertaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.swing.JOptionPane;

/**
 *
 * @author kaila
 */
public class JanelaOfertaPresenter {
    private JanelaOfertaView view;
    private String tipo;
    private String subcategoria;
    private String valorFinal;
    
    public JanelaOfertaPresenter(java.awt.Frame janelaPai, String tipo, String subcategoria, String valorFinal) throws ParseException{   
        view = new JanelaOfertaView(janelaPai, true);
        this.tipo = tipo;
        this.subcategoria = subcategoria;
        this.valorFinal = valorFinal;
        
        configurarTela();
        
        view.getBtnEnviar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    enviar();
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
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });
    }
    
    private void configurarTela() throws ParseException{
        view.setLocationRelativeTo(null);
        view.setSize(800, 550);
        view.setVisible(false);
        
        //Configura enabled dos txtField
        view.getTxtPeca().setEnabled(false);
        view.getTxtPrecoFinal().setEnabled(false);
        view.getTxtValorMinimo().setEnabled(false);
        view.getTxtValorMaximo().setEnabled(false);
        
        //Configura txtField com informações
        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
        double valorFinalConvertido = nf.parse(valorFinal).doubleValue();
        double valorMinimo = valorFinalConvertido * 0.8;
        double valorMaximo = valorFinalConvertido * 0.99;
        view.getTxtPeca().setText(tipo + " – " + subcategoria);
        view.getTxtPrecoFinal().setText("R$ " + valorFinal);
        view.getTxtValorMinimo().setText(String.format("R$ %.2f", valorMinimo));
        view.getTxtValorMaximo().setText(String.format("R$ %.2f", valorMaximo));
        
        view.setVisible(true);
    }
    
    private void enviar(){
    }
    
    private void cancelar(){}
}

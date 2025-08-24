/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.service.OfertaService;
import br.brechosustentavel.view.JanelaOfertaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private String idPeca;
    private OfertaService ofertaService;
    
    public JanelaOfertaPresenter(java.awt.Frame janelaPai, String tipo, String subcategoria, String valorFinal, String idPeca, OfertaService ofertaService){   
        this.tipo = tipo;
        this.subcategoria = subcategoria;
        this.valorFinal = valorFinal;
        this.idPeca = idPeca;
        this.ofertaService = ofertaService;
        
        view = new JanelaOfertaView(janelaPai, true);        
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
        view.setVisible(true);
    }
    
    private void configurarTela(){
        view.setLocationRelativeTo(null);
        view.setSize(800, 550);
        view.setVisible(false);
        
        //Configura enabled dos txtField
        view.getTxtPeca().setEnabled(false);
        view.getTxtPrecoFinal().setEnabled(false);
        view.getTxtValorMinimo().setEnabled(false);
        view.getTxtValorMaximo().setEnabled(false);
        
        //Converte a string do valor final para um double com padrao do BR
        double valorFinalConvertido = Double.parseDouble(valorFinal.trim().replace(",", "."));
        double valorMinimo = valorFinalConvertido * 0.8;
        double valorMaximo = valorFinalConvertido * 0.99;
        
        //Configura txtField com informações
        view.getTxtPeca().setText(tipo + " – " + subcategoria);
        view.getTxtPrecoFinal().setText("R$ " + valorFinal);
        view.getTxtValorMinimo().setText(String.format("R$ %.2f", valorMinimo));
        view.getTxtValorMaximo().setText(String.format("R$ %.2f", valorMaximo));
    }
    
    private void enviar(){
        String valorOfertado = view.getTxtValorOferta().getText();
            
        if(valorOfertado.isEmpty()){
           JOptionPane.showMessageDialog(view, "Por favor, insira um valor para oferta.");
        }
        
        try {   
            double valorOfertadoConvertido = Double.parseDouble(valorOfertado.trim().replace(",", "."));
            
            if(valorOfertadoConvertido < 0){
                JOptionPane.showMessageDialog(view, "Por favor, insira um valor válido para a oferta.");
            }
            
            ofertaService.realizarOferta(idPeca, valorOfertadoConvertido);
            JOptionPane.showMessageDialog(view, "Oferta enviada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            view.dispose();
        } catch(Exception e) { 
            JOptionPane.showConfirmDialog(view, "Erro ao enviar oferta: " + e.getMessage());
        }
    }
    
    private void cancelar(){
        view.dispose();
    }
}

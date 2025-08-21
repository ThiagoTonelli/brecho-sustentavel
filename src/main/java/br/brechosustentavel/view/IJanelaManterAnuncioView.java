/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 *
 * @author thiag
 */
public interface IJanelaManterAnuncioView {
    public JComboBox<String> getSelectComposicao();
    public JTextField getTxtSubcategoria();
    public JPanel getPainelScrollDefeitos();
    public JScrollPane getScrollDefeitos();
    public JComboBox<String> getSelectComposicao1();
    public JComboBox<String> getSelectComposicao2();
    public JSpinner getSpinnerComposicao();
    public JSpinner getSpinnerComposicao1();
    public JSpinner getSpinnerComposicao2();
    public JTextField getTxtCor();
    public JTextField getTxtEstadoConservacao();
    public JTextField getTxtId_c();
    public JTextField getTxtMassa();   
    public JTextField getTxtTamanho();
    public JComboBox<String> getSelectTipoDePeca();
    public JTextField getTxtPrecoBase();
    public JButton getBtnEnviar();
    public JButton getBtnCancelar();
    public JButton getBtnGerarId();
    public JButton getBtnExcluir();
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.view;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 *
 * @author thiag
 */
public interface IJanelaInclusaoAnuncioView {
    public JComboBox<String> getSelectComposicao();
    public JComboBox<String> getSelectSubcategoria();
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
    public JComboBox<String> getSelectTamanho();
    public JComboBox<String> getSelectTipoDePeca();
    public JTextField getTxtPrecoBase();
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.view;

import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author thiag
 */
public interface IAnuncioView {
    public JComboBox<String> getSelectComposicao();
    public JComboBox<String> getSelectDefeitos();
    public JComboBox<String> getSelectSubcategoria();
    public JTextField getTxtCor();
    public JTextField getTxtEstadoConservacao();
    public JTextField getTxtId_c();
    public JTextField getTxtMassa();   
    public JTextField getTxtTamanho();
    public JComboBox<String> getSelectTipoDePeca();
    public JTextField getTxtPrecoBase();
}

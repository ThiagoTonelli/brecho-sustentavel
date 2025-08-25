/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service;

import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author kaila
 */
public class LimparListenerService {
       
    public void limparListener(JButton botao){
        for (ActionListener al : botao.getActionListeners()) {
            botao.removeActionListener(al);
        }
    }
}

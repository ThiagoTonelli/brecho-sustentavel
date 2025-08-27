/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.view;

import java.awt.Component;
import javax.swing.JDesktopPane;

/**
 *
 * @author thiag
 */
public class CamadaDesktopPane extends JDesktopPane {

    public static final Integer BACKGROUND_LAYER = 1; 
    public static final Integer DEFAULT_LAYER = 2;   

    @Override
    protected void addImpl(Component comp, Object constraints, int index) {
        if (comp instanceof JanelaPrincipalView) {
            super.addImpl(comp, BACKGROUND_LAYER, index);
        } else {
            super.addImpl(comp, DEFAULT_LAYER, index);
        }
    }
}

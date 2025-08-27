/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;
import br.brechosustentavel.view.TelaPrincipalView;
import javax.swing.JInternalFrame;


/**
 *
 * @author thiag
 */
public final class TelaPrincipalPresenter {
    private final TelaPrincipalView view;
   
    public TelaPrincipalPresenter(){
        this.view = new TelaPrincipalView();
    }
    
    public TelaPrincipalView getView(){
        return view;
    }
    
    public void abrirJanelaUnica(JInternalFrame frame) {
        for (JInternalFrame openFrame : view.getjDesktopPane1().getAllFrames()) {
            if (openFrame.getClass().equals(frame.getClass())) {
                try {
                    openFrame.moveToFront(); 
                    openFrame.setSelected(true);
                } catch (java.beans.PropertyVetoException e) {
                    System.err.println("Erro ao focar na janela existente: " + e.getMessage());
                }
                return; 
            }
        }

        view.getjDesktopPane1().add(frame);
        frame.setVisible(true);
        frame.moveToFront();
        try {
            frame.setSelected(true);
            //frame.setMaximum(true); 
        } catch (java.beans.PropertyVetoException e) {
            System.err.println("Erro ao abrir nova janela: " + e.getMessage());
        }
    }
    
}

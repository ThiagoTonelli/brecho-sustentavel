/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;
import br.brechosustentavel.view.TelaPrincipalView;
import java.beans.PropertyVetoException;


/**
 *
 * @author thiag
 */
public final class TelaPrincipalPresenter {
    private final TelaPrincipalView view;
   
    public TelaPrincipalPresenter(TelaPrincipalView view) throws PropertyVetoException{
        this.view = view; 
        new JanelaPrincipalState(this);
        this.view.setVisible(true);
    }
    
    public TelaPrincipalView getView(){
        return view;
    }
}

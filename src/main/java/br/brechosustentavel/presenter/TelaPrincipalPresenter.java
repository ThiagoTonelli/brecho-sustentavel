/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;
import br.brechosustentavel.view.TelaPrincipalView;


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
}

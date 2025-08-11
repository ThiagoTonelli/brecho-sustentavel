/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

/**
 *
 * @author thiag
 */
public abstract class PresenterAnuncioState {
    protected ManterAnuncioPresenter presenter;
    
    public PresenterAnuncioState(ManterAnuncioPresenter presenter){
        this.presenter = presenter;
    }
    
    public void salvar(){
        
    }
    
    public void cancelar(){
        
    }
    
    public void editar(){
        
    }

}

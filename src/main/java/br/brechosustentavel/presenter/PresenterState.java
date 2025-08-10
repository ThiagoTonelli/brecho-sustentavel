/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

/**
 *
 * @author thiag
 */
public abstract class PresenterState {
    protected TelaPrincipalPresenter janelaPrincipal;
    
    public PresenterState(TelaPrincipalPresenter telaPrincipal){
        this.janelaPrincipal = telaPrincipal;
    }
    
}

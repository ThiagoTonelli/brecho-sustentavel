/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.view.IJanelaInclusaoAnuncioView;
import br.brechosustentavel.view.JanelaInclusaoAnuncioView;

/**
 *
 * @author thiag
 */
public class ManterAnuncioPresenter {
    private final IJanelaInclusaoAnuncioView view;
    private PresenterAnuncioState estado;

    public ManterAnuncioPresenter(IJanelaInclusaoAnuncioView view) {
        this.view = view;
    }
    
    public void setEstado(PresenterAnuncioState novoEstado){
        this.estado = novoEstado;
    }
    
    public JanelaInclusaoAnuncioView getView(){
        return (JanelaInclusaoAnuncioView) view;
    }
    
}

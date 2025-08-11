/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.repository.IItemRepository;
import br.brechosustentavel.view.IJanelaInclusaoAnuncioView;

/**
 *
 * @author thiag
 */
public class ManterAnuncioPresenter {
    private final IJanelaInclusaoAnuncioView view;
    //private final IItemRepository itemRepository;
    private PresenterAnuncioState estado;

    public ManterAnuncioPresenter(IJanelaInclusaoAnuncioView view) {
        this.view = view;
        //this.itemRepository = itemRepository;
    }
    
    public void setEstado(PresenterAnuncioState novoEstado){
        this.estado = novoEstado;
    }
    
    public IJanelaInclusaoAnuncioView getView(){
        return view;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.ManterAnuncioPresenter;

import br.brechosustentavel.presenter.ManterAnuncioPresenter.ManterAnuncioPresenter;

/**
 *
 * @author thiag
 */
public abstract class ManterAnuncioState {
    protected ManterAnuncioPresenter presenter;
    
    public ManterAnuncioState(ManterAnuncioPresenter presenter){
        this.presenter = presenter;
    }
    
    public abstract void salvar();
    
    public abstract void cancelar();
    
    public abstract void editar();
    
    public abstract void visualizar();
    
    public abstract void excluir();

}

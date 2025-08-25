/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.manterAnuncioPresenter;

/**
 *
 * @author thiag
 */
public abstract class ManterAnuncioState {
    protected ManterAnuncioPresenter presenter;
    
    public ManterAnuncioState(ManterAnuncioPresenter presenter){
        this.presenter = presenter;
    }
    
    public void salvar(){
        throw new RuntimeException("Não é possível salvar estando neste estado");
    }
    
    public void cancelar(){
        throw new RuntimeException("Não é possível cancelar estando neste estado");
    }
    
    public void editar(){
        throw new RuntimeException("Não é possível editar estando neste estado");
    }
    
    public void visualizar(){
        throw new RuntimeException("Não é possível visualizar estando neste estado");
    }
    
    public void excluir(){
        throw new RuntimeException("Não é possível excluir estando neste estado");
    }
    
    public void ofertar(){
        throw new RuntimeException("Não é possível ofertar estando neste estado");
    }
    
    public void denunciar(){
        throw new RuntimeException("Não é possível denunciar estando neste estado");
    }
}

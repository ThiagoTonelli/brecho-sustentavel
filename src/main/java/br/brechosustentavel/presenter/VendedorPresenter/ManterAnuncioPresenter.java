/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.VendedorPresenter;

import br.brechosustentavel.view.JanelaManterAnuncioView;
import java.beans.PropertyVetoException;
import br.brechosustentavel.view.IJanelaManterAnuncioView;

/**
 *
 * @author thiag
 */
public class ManterAnuncioPresenter {
    private ManterAnuncioState estado;
    private JanelaManterAnuncioView view;

    public ManterAnuncioPresenter() throws PropertyVetoException{
        IJanelaManterAnuncioView view = new JanelaManterAnuncioView();
    }
    
    public void setEstadoVendedor(ManterAnuncioState novoEstado){
        this.estado = novoEstado;
    }
    
    public JanelaManterAnuncioView getView(){
        return view;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.ManterAnuncioPresenter;

import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.view.JanelaManterAnuncioView;
import java.beans.PropertyVetoException;

/**
 *
 * @author thiag
 */
public class ManterAnuncioPresenter {
    private ManterAnuncioState estado;
    private JanelaManterAnuncioView view;
    private String idPeca;
    private SessaoUsuarioService usuarioAutenticado;

    public ManterAnuncioPresenter(SessaoUsuarioService usuarioAutenticado) throws PropertyVetoException{
        view = new JanelaManterAnuncioView();
        this.usuarioAutenticado = usuarioAutenticado;
    }
    
    public void setEstadoVendedor(ManterAnuncioState novoEstado){
        this.estado = novoEstado;
    }
    
    public JanelaManterAnuncioView getView(){
        return view;
    }

    public SessaoUsuarioService getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public String getIdPeca() {
        return idPeca;
    }

    public void setIdPeca(String id) {
        this.idPeca = id;
    }

    

    
}

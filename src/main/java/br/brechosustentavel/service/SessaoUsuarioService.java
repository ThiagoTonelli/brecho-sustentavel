/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service;

import br.brechosustentavel.model.Usuario;

/**
 *
 * @author kaila
 */
public class SessaoUsuarioService {
    private static SessaoUsuarioService instancia = null;
    private String tipoPerfil;
    private boolean autenticado;
    private Usuario usuario;
    
    
    private SessaoUsuarioService(){  
    }
    
    public static SessaoUsuarioService getInstancia(){
        if(instancia == null){
            instancia = new SessaoUsuarioService();
        }
        return instancia;
    }
        
    public void logout(){
    }
    
    public boolean isAutenticado(){
        return autenticado;
    }
    
    public void setAutenticado(boolean autenticado){
        this.autenticado = autenticado;
    }
    
    public void setUsuarioAutenticado(Usuario usuario){
        this.usuario = usuario;
    }
    
    public Usuario getUsuarioAutenticado(){
        return usuario;
    }

    public String getTipoPerfil(){
        return tipoPerfil;
    }
    
    public void setTipoPerfil(String tipoPerfil){
        this.tipoPerfil = tipoPerfil;
    }
    
}

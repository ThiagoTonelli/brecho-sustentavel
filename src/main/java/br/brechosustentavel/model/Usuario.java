/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.model;

/**
 *
 * @author kaila
 */
public class Usuario {
    private String nome;
    private String email;
    private String senha;
    private boolean admin;
    
    public Usuario(String nome, String email, String senha){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.admin = false;
    }
     
    public String getEmail(){
        return email;
    }
    
    public String getSenha(){
        return senha;
    }
    
    public void setAdmin(boolean admin){
        this.admin = admin;
    }
    
    public boolean isAdmin(){
        return admin;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.model;

import java.util.Optional;

/**
 *
 * @author kaila
 */
public class Usuario {
    private int id;
    private String nome;
    private String telefone;
    private String email;
    private String senha;
    private boolean admin;
    private Comprador comprador;
    private Vendedor vendedor;
    
    public Usuario(String nome, String telefone, String email, String senha){
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.admin = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public void setAdmin(boolean admin){
        this.admin = admin;
    }
    
    public boolean isAdmin(){
        return admin;
    }

    public Optional<Comprador> getComprador() {
        return Optional.ofNullable(comprador);
    }

    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }

    public Optional<Vendedor> getVendedor() {
        return Optional.ofNullable(vendedor);
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }
    
    
}

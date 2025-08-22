/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.model;

/**
 *
 * @author kaila
 */
public class Insignia {
    private int id;
    private String nome;
    private double valorEstrelas;
    private String tipoPerfil;
    
    public Insignia(String nome, double valorEstrelas, String tipoPerfil){
        this.nome = nome;
        this.valorEstrelas = valorEstrelas;
        this.tipoPerfil = tipoPerfil;
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

    public double getValorEstrelas() {
        return valorEstrelas;
    }

    public void setValorEstrelas(double valorEstrelas) {
        this.valorEstrelas = valorEstrelas;
    }

    public String getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(String tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }   
}

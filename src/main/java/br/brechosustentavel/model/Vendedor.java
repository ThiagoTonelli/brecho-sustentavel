/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.model;

/**
 *
 * @author thiag
 */
public class Vendedor {
    private int id;
    private String nivel;
    private double estrelas;
    private int vendasConcluidas;
    private double gwpContribuido;

    public Vendedor(String nivel, double estrelas, int vendasConcluidas, double gwpContribuido){
        this.nivel = nivel;
        this.estrelas = estrelas;
        this.vendasConcluidas = vendasConcluidas;
        this.gwpContribuido = gwpContribuido;
    }

    public Vendedor() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public double getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(double estrelas) {
        this.estrelas = estrelas;
    }

    public int getVendasConcluidas() {
        return vendasConcluidas;
    }

    public void setVendasConcluidas(int vendasConcluidas) {
        this.vendasConcluidas = vendasConcluidas;
    }

    public double getGwpContribuido() {
        return gwpContribuido;
    }

    public void setGwpContribuido(double gwpContribuido) {
        this.gwpContribuido = gwpContribuido;
    }    
}

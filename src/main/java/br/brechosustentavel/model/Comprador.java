/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.model;

/**
 *
 * @author thiag
 */
public class Comprador {
    private int id;
    private String nivel;
    private double estrelas;
    private int comprasFinalizadas;
    private boolean selo;
    private double gwpEvitado;
    
    public Comprador(String nivel, double estrelas, int comprasFinalizadas, double gwpEvitado, boolean selo){
        this.nivel = nivel;
        this.estrelas = estrelas;
        this.comprasFinalizadas = comprasFinalizadas;
        this.gwpEvitado = gwpEvitado;
        this.selo = selo;
    }
    
    public Comprador(){
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

    public int getComprasFinalizadas() {
        return comprasFinalizadas;
    }

    public void setComprasFinalizadas(int comprasFinalizadas) {
        this.comprasFinalizadas = comprasFinalizadas;
    }

    public boolean isSelo() {
        return selo;
    }

    public void setSelo(boolean selo) {
        this.selo = selo;
    }

    public double getGwpEvitado() {
        return gwpEvitado;
    }

    public void setGwpEvitado(double gwpEvitado) {
        this.gwpEvitado = gwpEvitado;
    }
    
    
}

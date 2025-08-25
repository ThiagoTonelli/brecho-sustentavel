/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.model;

/**
 *
 * @author thiag
 */
public class Anuncio {
    private int id;
    private Vendedor vendedor;
    private String status;
    private Peca peca;
    private double valorFinal;
    private double gwpAvoided;
    private double mci;

    public Anuncio(Vendedor vendedor, Peca peca, double valorFinal, double gwpAvoided, double mci) {
        this.vendedor = vendedor;
        this.peca = peca;
        this.valorFinal = valorFinal;
        this.gwpAvoided = gwpAvoided;
        this.mci = mci;
    }    

    public Vendedor getVendedor() {
        return vendedor;
    }
    
    public Peca getPeca() {
        return peca;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    public double getGwpAvoided() {
        return gwpAvoided;
    }

    public double getMci() {
        return mci;
    }
    
    public void setId(int idAnuncio){
        this.id = idAnuncio;
    }
    
    public int getId(){
        return id;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public void setGwpAvoided(double gwpAvoided) {
        this.gwpAvoided = gwpAvoided;
    }

    public void setMci(double mci) {
        this.mci = mci;
    }
}

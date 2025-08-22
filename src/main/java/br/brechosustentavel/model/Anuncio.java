/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.model;

import java.util.List;

/**
 *
 * @author thiag
 */
public class Anuncio {
    private int idAnuncio;
    private int idVendedor;
    private Peca peca;
    private double valorFinal;
    private double gwpAvoided;
    private double mci;

    public Anuncio(int idVendedor, Peca peca, double valorFinal, double gwpAvoided, double mci) {
        this.idVendedor = idVendedor;
        this.peca = peca;
        this.valorFinal = valorFinal;
        this.gwpAvoided = gwpAvoided;
        this.mci = mci;
    }    

    public int getIdVendedor() {
        return idVendedor;
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
    
    public void setIdAnuncio(int idAnuncio){
        this.idAnuncio = idAnuncio;
    }
    
    public int getIdAnuncio(){
        return idAnuncio;
    }

    @Override
    public String toString() {
        return "Anuncio{" + "idAnuncio=" + idAnuncio + ", idVendedor=" + idVendedor + ", peca=" + peca + ", valorFinal=" + valorFinal + ", gwpAvoided=" + gwpAvoided + ", mci=" + mci + '}';
    }
    
    
    
}

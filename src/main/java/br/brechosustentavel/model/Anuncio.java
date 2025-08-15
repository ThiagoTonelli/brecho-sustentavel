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
    private double valor_final;
    private double gwp_avoided;
    private double mci;

    public Anuncio(int idVendedor, Peca peca, double valor_final, double gwp_avoided, double mci) {
        this.idVendedor = idVendedor;
        this.peca = peca;
        this.valor_final = valor_final;
        this.gwp_avoided = gwp_avoided;
        this.mci = mci;
    }    

    public int getIdVendedor() {
        return idVendedor;
    }

    public Peca getPeca() {
        return peca;
    }

    public double getValor_final() {
        return valor_final;
    }

    public double getGwp_avoided() {
        return gwp_avoided;
    }

    public double getMci() {
        return mci;
    }
    
    public void setIdAnuncio(int idAnuncio){
        this.idAnuncio = idAnuncio;
    }
    
}

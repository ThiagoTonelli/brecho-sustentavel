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
    private int idVendedor;
    private List<String> defeitoPeca;
    private double valor_final;
    private double gwp_avoided;
    private double mci;

    public Anuncio(int idVendedor, List<String> defeitoPeca, double valor_final, double gwp_avoided, double mci) {
        this.idVendedor = idVendedor;
        this.defeitoPeca = defeitoPeca;
        this.valor_final = valor_final;
        this.gwp_avoided = gwp_avoided;
        this.mci = mci;
    }    
    
}

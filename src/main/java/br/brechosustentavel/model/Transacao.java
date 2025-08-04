/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.model;

/**
 *
 * @author thiag
 */
public class Transacao {
    private Comprador comprador;
    private Vendedor vendedor;
    private double precoFinal;

    public Transacao(Comprador comprador, Vendedor vendedor, double precoFinal) {
        this.comprador = comprador;
        this.vendedor = vendedor;
        this.precoFinal = precoFinal;
    }
    
    
    
}

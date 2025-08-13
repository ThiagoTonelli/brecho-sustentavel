/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service;

import br.brechosustentavel.model.Peca;

/**
 *
 * @author thiag
 */
public class AplicarDescontosDefeitosService {
    public double calcularDescontos(Peca peca){
        double precoFinal = peca.getPrecoBase();
        double menorPreco = precoFinal * 0.05;
        for (double desconto : peca.getDefeitos().values()) {
            precoFinal -= precoFinal * desconto; 
        }
        if (precoFinal < menorPreco){
            return menorPreco;
        }
        else{
            return precoFinal;
        }
    }
}

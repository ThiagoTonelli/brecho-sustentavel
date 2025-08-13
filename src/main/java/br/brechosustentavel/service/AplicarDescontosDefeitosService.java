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
        for (double desconto : peca.getDefeitos().values()) {
            precoFinal -= precoFinal * desconto; 
        }
        return precoFinal;
    }
}

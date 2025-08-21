/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service;

import br.brechosustentavel.model.Peca;
import java.util.Map;

/**
 *
 * @author thiag
 */
public class CalculadoraDeIndicesService {
    public double calcularGwpBase(Peca peca){ 
        Map<String, Double> materialQuantidade = peca.getMaterialQuantidade();
        Map<String, Double> materialDesconto = peca.getMaterialDesconto();
        double massa = peca.getMassaEstimada();
        double gwpBase = 0;
        for (String chave : materialQuantidade.keySet()) {
            double quantidade = (materialQuantidade.get(chave)/100);
            double desconto = materialDesconto.get(chave);
            gwpBase += (desconto * quantidade * massa);
        }
        return gwpBase;
    }
    
    public double calcularMCI(Peca peca){
        double d_j = 0;
        for (String chave : peca.getDefeitos().keySet()) {
            d_j += peca.getDefeitos().get(chave);
        }
        double q = 1 - d_j;
        return q;
    }
    
    public double calcularGwpAvoided(Peca peca){
        double gwpBase = calcularGwpBase(peca);
        double gwpAvoided = gwpBase - (0.05 * gwpBase);
        return gwpAvoided;
    }
}

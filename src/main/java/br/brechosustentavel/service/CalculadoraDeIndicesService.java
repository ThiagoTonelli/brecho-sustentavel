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
        Map<String, Integer> materialQuantidade = peca.getMaterialQuantidade();
        Map<String, Double> materialDesconto = peca.getMaterialDesconto();
        double gwpBase = 0;
        
    }
    
    public double calcularMCI(Peca peca){
        
    }
    
    public double calcularGwpAvoided(Peca peca){
        
    }
}

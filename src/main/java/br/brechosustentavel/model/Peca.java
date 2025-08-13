/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.model;

import java.util.List;
import java.util.Map;

/**
 *
 * @author thiag
 */
public class Peca {
    private String id_c;
    private String tipoDePeca;
    private String subcategoria;
    private String tamanho;
    private String cor;
    private double massaEstimada;
    private String estadoDeConservacao;
    private double precoBase;
    private double precoFinal;
    private Map<String, Double> defeitos;
    private Map<String, Double> materialDesconto;
    private Map<String, Integer> materialQuantidade;
    private List<EventoLinhaDoTempo> linhaDoTempo;

    public Peca(String id_c, String tipoDePeca, String subcategoria, String tamanho, String cor, double massaEstimada, String estadoDeConservacao, double precoBase, Map<String, Double> defeitos, Map<String, Double> materialDesconto, Map<String, Integer> materialQuantidade) {
        this.id_c = id_c;
        this.tipoDePeca = tipoDePeca;
        this.subcategoria = subcategoria;
        this.tamanho = tamanho;
        this.cor = cor;
        this.massaEstimada = massaEstimada;
        this.estadoDeConservacao = estadoDeConservacao;
        this.precoBase = precoBase;
        this.defeitos = defeitos;
        this.materialDesconto = materialDesconto;
        this.materialQuantidade = materialQuantidade;

    }
    
    
    public void setPrecoFinal(double precoFinal){
        this.precoFinal = precoFinal;
    }

    public String getId_c() {
        return id_c;
    }

    public String getTipoDePeca() {
        return tipoDePeca;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public String getTamanho() {
        return tamanho;
    }

    public String getCor() {
        return cor;
    }

    public double getMassaEstimada() {
        return massaEstimada;
    }

    public String getEstadoDeConservacao() {
        return estadoDeConservacao;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public double getPrecoFinal() {
        return precoFinal;
    }

    public Map<String, Double> getDefeitos() {
        return defeitos;
    }

    public Map<String, Double> getMaterialDesconto() {
        return materialDesconto;
    }
    
    public Map<String, Integer> getMaterialQuantidade() {
        return materialQuantidade;
    }

    public List<EventoLinhaDoTempo> getLinhaDoTempo() {
        return linhaDoTempo;
    }
    
    
    
}

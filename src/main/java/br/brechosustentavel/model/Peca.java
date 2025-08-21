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
    private Map<String, Double> materialQuantidade;
    private List<EventoLinhaDoTempo> linhaDoTempo;

    public Peca(String id_c, String subcategoria, String tamanho, String cor, double massaEstimada, String estadoDeConservacao, double precoBase) {
        this.id_c = id_c;
        this.subcategoria = subcategoria;
        this.tamanho = tamanho;
        this.cor = cor;
        this.massaEstimada = massaEstimada;
        this.estadoDeConservacao = estadoDeConservacao;
        this.precoBase = precoBase;

    }

    public void setMaterialDesconto(Map<String, Double> materialDesconto) {
        this.materialDesconto = materialDesconto;
    }

    public void setMaterialQuantidade(Map<String, Double> materialQuantidade) {
        this.materialQuantidade = materialQuantidade;
    }
    
    public void setLinhaDoTempo(List<EventoLinhaDoTempo> linhaDoTempo){
        this.linhaDoTempo = linhaDoTempo;
    }

    public void setTipoDePeca(String tipoDePeca) {
        this.tipoDePeca = tipoDePeca;
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
    public Map<String, Double> getMaterialDesconto() {
        return materialDesconto;
    }
    
    public Map<String, Double> getMaterialQuantidade() {
        return materialQuantidade;
    }

    public List<EventoLinhaDoTempo> getLinhaDoTempo() {
        return linhaDoTempo;
    }

    public void setId_c(String id_c) {
        this.id_c = id_c;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setMassaEstimada(double massaEstimada) {
        this.massaEstimada = massaEstimada;
    }

    public void setEstadoDeConservacao(String estadoDeConservacao) {
        this.estadoDeConservacao = estadoDeConservacao;
    }

    public void setPrecoBase(double precoBase) {
        this.precoBase = precoBase;
    }

    public Map<String, Double> getDefeitos() {
        return defeitos;
    }

    public void setDefeitos(Map<String, Double> defeitosSelecionados) {
        this.defeitos = defeitosSelecionados;
    }
    
    
    
    
    
    
}

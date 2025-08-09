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
public class Item {
    private String id_c;
    private String tipoDePeca;
    private String subcategoria;
    private String tamanho;
    private double massaEstimada;
    private String estadoDeConservacao;
    private double precoBase;
    private List<Defeito> defeitos;
    private Material material;
    private List<EventoLinhaDoTempo> linhaDoTempo;

    public Item(String tipoDePeca, String subcategoria, String tamanho, double massaEstimada, String estadoDeConservacao, double precoBase, List<Defeito> defeitos, Material material) {
        this.tipoDePeca = tipoDePeca;
        this.subcategoria = subcategoria;
        this.tamanho = tamanho;
        this.massaEstimada = massaEstimada;
        this.estadoDeConservacao = estadoDeConservacao;
        this.precoBase = precoBase;
        this.defeitos = defeitos;
        this.material = material;
    }
    
    
}

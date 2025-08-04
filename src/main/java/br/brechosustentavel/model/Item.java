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
    
}

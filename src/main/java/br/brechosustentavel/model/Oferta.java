/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.model;

import java.time.LocalDateTime;

/**
 *
 * @author kaila
 */
public class Oferta {
    private int id;
    private Anuncio anuncio;
    private Comprador comprador;
    private double valor;
    private LocalDateTime data;
    private LocalDateTime dataResposta; 
    private String status;

    

    public Oferta(Anuncio anuncio, Comprador comprador, double valor) {
        this.anuncio = anuncio;
        this.comprador = comprador;
        this.valor = valor;
        this.status = "Pendente";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    public Comprador getComprador() {
        return comprador;
    }

    public void setComprador(Comprador comprador) {
        this.comprador = comprador;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
    
    public LocalDateTime getDataResposta() { 
        return dataResposta; 
    }
    
    public void setDataResposta(LocalDateTime dataResposta) { 
        this.dataResposta = dataResposta; 
    }
    
    public String getStatus() {
        return status; 
    }
    
    public void setStatus(String status) {
        this.status = status; 
    }
}

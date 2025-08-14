/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.model;

import java.time.LocalDateTime;
/**
 *
 * @author thiag
 */
public class EventoLinhaDoTempo {
    private String descricao;
    private String tipoEvento;
    private LocalDateTime dataHora;
    private double gwpEvitado;
    private double mciPeca;
    private int ciclo_n;

    public EventoLinhaDoTempo(String descricao, String tipoEvento, LocalDateTime dataHora, double gwpEvitado, double mciPeca) {
        this.descricao = descricao;
        this.tipoEvento = tipoEvento;
        this.dataHora = dataHora;
        this.gwpEvitado = gwpEvitado;
        this.mciPeca = mciPeca;
    }
    
    public void setCliclo(int ciclo_n){
        this.ciclo_n = ciclo_n;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public double getGwpEvitado() {
        return gwpEvitado;
    }

    public double getMciPeca() {
        return mciPeca;
    }

    public int getCiclo_n() {
        return ciclo_n;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    
}

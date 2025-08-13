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
    private String tipoEvento;
    private LocalDateTime dataHora;
    private double gwpEvitado;
    private double mciItem;
    private Peca peca;

    public EventoLinhaDoTempo(Peca peca, String tipoEvento, LocalDateTime dataHora, double gwpEvitado, double mciItem) {
        this.peca = peca;
        this.tipoEvento = tipoEvento;
        this.dataHora = dataHora;
        this.gwpEvitado = gwpEvitado;
        this.mciItem = mciItem;
    }
    
}

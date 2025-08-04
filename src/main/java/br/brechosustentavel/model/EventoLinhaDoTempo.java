/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.model;

import java.util.Date;

/**
 *
 * @author thiag
 */
public class EventoLinhaDoTempo {
    private String tipoEvento;
    private Date dataHora;
    private double gwpEvitado;
    private double mciItem;
    private String descricaoAdional;

    public EventoLinhaDoTempo(String tipoEvento, Date dataHora, double gwpEvitado, double mciItem, String descricaoAdional) {
        this.tipoEvento = tipoEvento;
        this.dataHora = dataHora;
        this.gwpEvitado = gwpEvitado;
        this.mciItem = mciItem;
        this.descricaoAdional = descricaoAdional;
    }
    
}

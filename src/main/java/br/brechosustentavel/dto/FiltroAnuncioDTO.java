/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.dto;

/**
 *
 * @author kaila
 */
public class FiltroAnuncioDTO {
    private String tipoCriterio;
    private String valorFiltro;
    private String busca;

    public String getTipoCriterio() {
        return tipoCriterio;
    }

    public void setTipoCriterio(String tipoCriterio) {
        this.tipoCriterio = tipoCriterio;
    }

    public String getValorFiltro() {
        return valorFiltro;
    }

    public void setValorFiltro(String valorFiltro) {
        this.valorFiltro = valorFiltro;
    }

    public String getBusca() {
        return busca;
    }

    public void setBusca(String busca) {
        this.busca = busca;
    }   
}

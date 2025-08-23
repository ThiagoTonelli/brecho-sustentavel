/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository;

import br.brechosustentavel.model.EventoLinhaDoTempo;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author thiag
 */
public interface ILinhaDoTempoRepository {
    public Optional<EventoLinhaDoTempo> ultimoEvento(String id_c);
    public void criar(String id_c, EventoLinhaDoTempo evento);
    public Map<String, Double> getGWPEvitadoPorSemana();
}

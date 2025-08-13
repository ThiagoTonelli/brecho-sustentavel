/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository;

import br.brechosustentavel.model.Defeito;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author thiag
 */
public interface IDefeitoRepository {
    public void criar();
    public void excluir();
    public void editar();
    public Map<String, Double>  buscarDefeitos(String tipoPeca);
    public Optional<Defeito> consultar();
}

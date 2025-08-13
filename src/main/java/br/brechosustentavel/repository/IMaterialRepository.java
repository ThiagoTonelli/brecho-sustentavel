/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.repository;

import br.brechosustentavel.model.Material;
import java.util.List;

import java.util.Map;
import java.util.Optional;

/**
 *
 * @author thiag
 */
public interface IMaterialRepository {
    public void criar();
    public void excluir();
    public void editar();
    public Optional<Material> consultar();
    public Map<String, Double> buscarMateriais();
    public Map<String, Double> buscarMateriaisNome(List<String> materiais);
}

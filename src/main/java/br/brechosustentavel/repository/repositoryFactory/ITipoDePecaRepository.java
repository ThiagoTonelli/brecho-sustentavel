/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.repository.repositoryFactory;

import java.util.List;
import java.util.Map;

/**
 *
 * @author thiag
 */
public interface ITipoDePecaRepository {
    public List<String> buscarTiposDePeca();
    public int buscarIdTipo(String tipo);
    public String buscarNomeTipo(int idTipo);
    public List<Map<String, Object>> buscarTodosParaManutencao();
    public void salvar(Integer id, String nome);
    public void excluir(int idTipoPeca);
    
}

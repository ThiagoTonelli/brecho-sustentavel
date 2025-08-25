/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.repositoryFactory;

import java.util.List;
import java.util.Map;

/**
 *
 * @author thiag
 */
public interface IDefeitoRepository {
    public void editar();
    public Map<String, Double> buscarDefeitosPorTipo(String tipoPeca);
    public Integer buscarIdPeloNomeDoDefeito(String nomeDefeito);
    List<Map<String, Object>> buscarTodosParaManutencao();
    void salvar(Integer id, String nome, double desconto, int idTipoPeca);
    void excluir(int idDefeito);
}

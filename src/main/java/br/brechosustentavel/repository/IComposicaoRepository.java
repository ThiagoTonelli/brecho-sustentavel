/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.repository;

import java.util.List;
import java.util.Map;

/**
 *
 * @author thiag
 */
public interface IComposicaoRepository {
    public Integer buscarIdComposicaoPorNome(String nome);
    public Map<String, Double> buscarMateriais();
    public Map<String, Double> buscarMateriaisNome(List<String> materiais);
    public List<Map<String, Object>> buscarTodosParaManutencao();
    void salvar(Integer id, String nome, double fatorEmissao);
    void excluir(int idComposicao);
}

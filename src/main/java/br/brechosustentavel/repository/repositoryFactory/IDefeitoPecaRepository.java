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
public interface IDefeitoPecaRepository {
    public void adicionarDefeitosAPeca(String id_c, int id_defeito);
    public void adicionarVariosDefeitosAPeca(String idPeca, List<Integer> idsDefeitos);
    public Map<String, Integer> buscarIdDefeitosDaPeca(String idPeca);
    public Map<String, Double> buscarNomeDefeitosDaPeca(String idPeca);
    public void excluirDefeitosDaPeca(String idPeca);
}

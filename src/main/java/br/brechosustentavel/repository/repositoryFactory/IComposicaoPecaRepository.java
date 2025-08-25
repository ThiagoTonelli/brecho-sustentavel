/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.repository.repositoryFactory;

import br.brechosustentavel.model.Peca;
import java.util.Map;

/**
 *
 * @author thiag
 */
public interface IComposicaoPecaRepository {
    public void adicionarComposicaoAPeca(Peca peca, IComposicaoRepository composicaoRepository);
    public void excluirComposicaoDaPeca(String idPeca);
    public Map<String, Integer> buscarComposicaoPorPeca(String idPeca);
    public Map<String, Double> getMassaTotalPorMaterial();
}

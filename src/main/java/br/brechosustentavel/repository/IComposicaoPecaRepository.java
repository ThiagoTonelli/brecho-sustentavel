/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.repository;

import br.brechosustentavel.model.Peca;
import java.util.List;

/**
 *
 * @author thiag
 */
public interface IComposicaoPecaRepository {
    public void adicionarComposicaoAPeca(Peca peca, List<Integer> idMateriais);
}

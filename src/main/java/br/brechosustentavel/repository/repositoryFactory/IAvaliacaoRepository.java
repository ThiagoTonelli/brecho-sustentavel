/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.repositoryFactory;

import br.brechosustentavel.model.Avaliacao;
import java.util.List;

/**
 *
 * @author thiag
 */
public interface IAvaliacaoRepository {
    void salvar(Avaliacao avaliacao);
    List<Avaliacao> buscarPorTransacao(int idTransacao);
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.service.pontuacao;

import br.brechosustentavel.repository.RepositoryFactory;


/**
 *
 * @author thiag
 */
public interface IPontuacaoStrategy {
    void calcularEAtualizar(Object contexto, RepositoryFactory fabrica);
}

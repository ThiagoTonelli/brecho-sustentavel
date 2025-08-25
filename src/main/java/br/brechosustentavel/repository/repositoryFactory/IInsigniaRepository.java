/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.repositoryFactory;

import br.brechosustentavel.model.Insignia;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public interface IInsigniaRepository {
    public void inserirInsignia(Insignia insignia);
    public Optional<Insignia> buscarInsigniaPorNome(String nome);
}

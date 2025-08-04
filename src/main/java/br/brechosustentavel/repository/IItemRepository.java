/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.repository;

import br.brechosustentavel.model.Item;
import java.util.Optional;

/**
 *
 * @author thiag
 */
public interface IItemRepository {
    public void criar();
    public void excluir();
    public void editar();
    public Optional<Item> consultar();
}

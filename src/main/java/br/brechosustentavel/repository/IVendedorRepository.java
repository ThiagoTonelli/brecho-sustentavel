/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository;

import br.brechosustentavel.model.Vendedor;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public interface IVendedorRepository {
    public Optional<Vendedor> buscarPorId(int id);
    public Vendedor cadastrarVendedor(Vendedor vendedor);
}

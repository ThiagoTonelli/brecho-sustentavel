/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository;

import br.brechosustentavel.model.Comprador;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public interface ICompradorRepository {
    public Optional<Comprador> buscarPorId(int id);
    public Comprador cadastrarComprador(Comprador comprador);
}

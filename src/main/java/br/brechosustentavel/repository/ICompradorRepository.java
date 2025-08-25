/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository;

import br.brechosustentavel.model.Comprador;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public interface ICompradorRepository {
    public Optional<Comprador> buscarPorId(int id);
    public void salvar(Comprador comprador);
    public void atualizarEstrelas(int id, double qtdEstrelas);
    public void atualizarCompras(int id);
    public Map<String, Integer> contarPorNivelReputacao();
    public void editar(Comprador comprador);
}

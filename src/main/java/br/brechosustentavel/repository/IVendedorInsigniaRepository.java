/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository;

import br.brechosustentavel.model.Insignia;
import java.util.List;

/**
 *
 * @author kaila
 */
public interface IVendedorInsigniaRepository {
    public void inserirInsigniaAVendedor(int idInsignia, int idVendedor);
    public List<Insignia> buscarInsigniaPorVendedor(int idVendedor);
    public boolean vendedorPossuiInsignia(int idInsignia, int idVendedor);
}

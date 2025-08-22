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
public interface ICompradorInsigniaRepository {
    public void inserirInsigniaAComprador(int idInsignia, int idComprador);
    public List<Insignia> buscarInsigniaPorComprador(int idComprador);
    public boolean compradorPossuiInsignia(int idInsignia, int idComprador);
}

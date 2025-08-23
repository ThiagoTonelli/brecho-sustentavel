/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.repository;

import br.brechosustentavel.model.Peca;
import java.util.Optional;

/**
 *
 * @author thiag
 */
public interface IPecaRepository {
    public void criar(Peca peca);
    public void editar(Peca peca);
    public Optional<Peca> consultar(String idC);
    public boolean ExisteId_c(String id_c);
}

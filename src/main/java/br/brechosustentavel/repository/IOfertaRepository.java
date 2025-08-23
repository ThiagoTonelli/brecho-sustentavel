/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository;

import br.brechosustentavel.model.Oferta;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public interface IOfertaRepository {
    public void adicionarOferta(Oferta oferta);
    public Optional<Oferta> buscarOfertaPorId(int id);
    public int qtdOfertaPorComprador(int idComprador);
    public List<Oferta> buscarOfertaPorAnuncio(int idAnuncio);
}

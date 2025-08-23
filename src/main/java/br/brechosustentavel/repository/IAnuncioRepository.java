/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.repository;

import br.brechosustentavel.model.Anuncio;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author thiag
 */
public interface IAnuncioRepository {
    public void criar(Anuncio anuncio);
    public List<Anuncio> buscarAnuncios(int idVendedor);
    public void editar(Anuncio anuncio);
    public int qtdAnuncioPorVendedor(int idVendedor);
    public Optional<Anuncio> buscarAnuncioPorId(int id);
    void excluirPorPecaId(String idPeca);
    public List<Anuncio> buscarTodos();
}

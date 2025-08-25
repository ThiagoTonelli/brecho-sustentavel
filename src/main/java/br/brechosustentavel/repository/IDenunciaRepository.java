/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository;

import br.brechosustentavel.model.Denuncia;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public interface IDenunciaRepository {
    public void inserirDenuncia(Denuncia denuncia);
    public void atualizarStatusDenuncia(Denuncia denuncia, String novoStatus);
    public Optional<Denuncia> buscarDenunciaPorId(int id);
    public List<Denuncia> buscarDenunciaPorStatus(String status, int id);
    public int qtdDenunciasProcedentesPorComprador(int idComprador);
}

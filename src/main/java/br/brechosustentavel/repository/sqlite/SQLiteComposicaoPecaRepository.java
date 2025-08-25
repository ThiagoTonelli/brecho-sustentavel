/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.Peca;
import br.brechosustentavel.repository.repositoryFactory.ConexaoFactory;
import br.brechosustentavel.repository.repositoryFactory.IComposicaoPecaRepository;
import br.brechosustentavel.repository.repositoryFactory.IComposicaoRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author thiag
 */
public class SQLiteComposicaoPecaRepository implements IComposicaoPecaRepository{
    private ConexaoFactory conexaoFactory;
    
    public SQLiteComposicaoPecaRepository(ConexaoFactory conexaoFactory){
        this.conexaoFactory = conexaoFactory;
    }
    
    @Override
    public void adicionarComposicaoAPeca(Peca peca, IComposicaoRepository composicaoRepository) {
        String sql = "INSERT INTO composicao_peca (id_peca, id_composicao, quantidade) VALUES (?, ?, ?)";
        String idPeca = peca.getId_c();
        Map<String, Double> materiais = peca.getMaterialQuantidade();

        if (materiais == null || materiais.isEmpty()) {
            return;
        }

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            
            for (Map.Entry<String, Double> entry : materiais.entrySet()) {
                String nomeMaterial = entry.getKey();
                int quantidade = entry.getValue().intValue();

                Integer idComposicao = composicaoRepository.buscarIdComposicaoPorNome(nomeMaterial);

                if (idComposicao != null) {
                    pstmt.setString(1, idPeca);
                    pstmt.setInt(2, idComposicao);
                    pstmt.setInt(3, quantidade);
                    pstmt.addBatch();
                }
            }
            pstmt.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir a composição para a peça " + idPeca, e);
        }
    }
    
    @Override
    public void excluirComposicaoDaPeca(String idPeca) {
        String sql = "DELETE FROM composicao_peca WHERE id_peca = ?";

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, idPeca);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir a composição da peça com ID " + idPeca, e);
        }
    }
    
    @Override
    public Map<String, Integer> buscarComposicaoPorPeca(String idPeca) {
        Map<String, Integer> composicao = new LinkedHashMap<>();
        
        String sql = """
                     SELECT
                         c.nome,
                         cp.quantidade
                     FROM
                         composicao_peca cp
                     JOIN
                         composicao c ON cp.id_composicao = c.id
                     WHERE
                         cp.id_peca = ?
                     """;

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            
            pstmt.setString(1, idPeca);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String nomeMaterial = rs.getString("nome");
                int quantidade = rs.getInt("quantidade");
                composicao.put(nomeMaterial, quantidade);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar a composição da peça com ID " + idPeca, e);
        }

        return composicao;
    }
    
    @Override
    public Map<String, Double> getMassaTotalPorMaterial() {
        Map<String, Double> massaPorMaterial = new HashMap<>();
        String sql = """
                     SELECT
                         c.nome as nome_material,
                         SUM(p.massa * (cp.quantidade / 100.0)) as massa_total
                     FROM
                         composicao_peca cp
                     INNER JOIN
                         peca p ON cp.id_peca = p.id_c
                     INNER JOIN
                         composicao c ON cp.id_composicao = c.id
                     GROUP BY
                         c.nome
                     """;

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                massaPorMaterial.put(rs.getString("nome_material"), rs.getDouble("massa_total"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar massa total por material: " + e.getMessage(), e);
        }
        return massaPorMaterial;
    }
    
}

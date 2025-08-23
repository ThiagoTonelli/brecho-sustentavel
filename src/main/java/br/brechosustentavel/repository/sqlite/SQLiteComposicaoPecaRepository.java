/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.Peca;
import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IComposicaoPecaRepository;
import br.brechosustentavel.repository.IComposicaoRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
}

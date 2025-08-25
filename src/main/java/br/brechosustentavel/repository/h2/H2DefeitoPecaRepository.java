/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.h2;

import br.brechosustentavel.repository.repositoryFactory.ConexaoFactory;
import br.brechosustentavel.repository.repositoryFactory.IDefeitoPecaRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thiag
 */
public class H2DefeitoPecaRepository implements IDefeitoPecaRepository {
    private final ConexaoFactory conexaoFactory;
    
    public H2DefeitoPecaRepository(ConexaoFactory conexaoFactory){
        this.conexaoFactory = conexaoFactory;
    }
    
    @Override
    public void adicionarDefeitosAPeca(String id_c, int id_defeito) {
        String sql = "INSERT INTO defeito_peca (id_defeito, id_peca) VALUES(?, ?)";

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setInt(1, id_defeito);
            pstmt.setString(2, id_c);
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir defeitos da peça no H2", e);
        }
    }
    
    @Override
    public void adicionarVariosDefeitosAPeca(String idPeca, List<Integer> idsDefeitos) {
        for (Integer idDefeito : idsDefeitos) {
            try {
                adicionarDefeitosAPeca(idPeca, idDefeito);
            } catch (RuntimeException e) {
                throw new RuntimeException("Erro ao adicionar vários defeitos da peça no H2", e);
            }
        }
    }
    
    @Override
    public Map<String, Double> buscarNomeDefeitosDaPeca(String idPeca){
        Map<String, Double> defeitos = new HashMap<>();
        String sql = """
                     SELECT d.nome, d.desconto
                     FROM defeito_peca dp
                     JOIN defeito d ON dp.id_defeito = d.id
                     WHERE dp.id_peca = ?
                     """;
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)){
            pstmt.setString(1, idPeca);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                defeitos.put(rs.getString("nome"), rs.getDouble("desconto"));
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar nomes de defeitos da peça no H2: " + ex.getMessage(), ex);
        }
        return defeitos;
    }
    
    @Override
    public Map<String, Integer> buscarIdDefeitosDaPeca(String idPeca){
        Map<String, Integer> defeitos = new HashMap<>();
        String sql = """
                     SELECT d.id, d.nome
                     FROM defeito_peca dp
                     JOIN defeito d ON dp.id_defeito = d.id
                     WHERE dp.id_peca = ?
                     """;
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)){
            pstmt.setString(1, idPeca);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                defeitos.put(rs.getString("nome"), rs.getInt("id"));
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar ids de defeitos da peça no H2: " + ex.getMessage(), ex);
        }
        return defeitos;
    }
    
    @Override
    public void excluirDefeitosDaPeca(String idPeca) {
        String sql = "DELETE FROM defeito_peca WHERE id_peca = ?";

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, idPeca);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir os defeitos da peça no H2: " + e.getMessage(), e);
        }
    }
}

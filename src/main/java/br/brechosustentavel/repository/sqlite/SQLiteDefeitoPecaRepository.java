/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IDefeitoPecaRepository;
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
public class SQLiteDefeitoPecaRepository implements IDefeitoPecaRepository{
    private ConexaoFactory conexaoFactory;
    
    public SQLiteDefeitoPecaRepository(ConexaoFactory conexaoFactory){
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
            throw new RuntimeException("erro ao inserir defeitos da peça", e);
        }
    }
    
    @Override
    public void adicionarVariosDefeitosAPeca(String idPeca, List<Integer> idsDefeitos) {
        for (Integer idDefeito : idsDefeitos) {
            try {
                adicionarDefeitosAPeca(idPeca, idDefeito);
            } catch (RuntimeException e) {
                throw new RuntimeException("erro ao adicionar varios defeitos da peça", e);
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
            throw new RuntimeException("Erro ao buscar peça por id_c no banco de dados (na tabela buscar nomes de defeitos da peca): " + ex.getMessage());
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
            throw new RuntimeException("Erro ao buscar peça por id_c no banco de dados: " + ex.getMessage());
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
            throw new RuntimeException("Erro ao excluir os defeitos da peça: " + e.getMessage(), e);
        }
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IDefeitoRepository;
import br.brechosustentavel.repository.ITipoDePecaRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author thiag
 */
public class SQLiteDefeitoRepository implements IDefeitoRepository{
    private final ConexaoFactory conexaoFactory;
    private final ITipoDePecaRepository tipoPecaRepository;

    public SQLiteDefeitoRepository(ConexaoFactory conexaoFactory, ITipoDePecaRepository tipoPecaRepository) {
        this.conexaoFactory = conexaoFactory;
        this.tipoPecaRepository = tipoPecaRepository;
    }

    @Override
    public void criar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void excluir() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void editar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Map<String, Double> buscarDefeitosPorTipo(String tipoPeca) {
        Map<String, Double> defeitos = new HashMap<>();
        int tipoId = this.tipoPecaRepository.buscarIdTipo(tipoPeca);
        String sql = "SELECT nome, desconto FROM defeito WHERE id_tipo = ?";
        
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            
            pstmt.setInt(1, tipoId);
            ResultSet rs = pstmt.executeQuery();
                
            while (rs.next()) {
                defeitos.put(rs.getString("nome"), rs.getDouble("desconto"));
            }
            
            return defeitos;         
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar id do tipo de peça no banco de dados: " + e.getMessage());
        }
    }

    @Override
    public Integer buscarIdPeloNomeDoDefeito(String nomeDefeito){
        String sql = "SELECT id FROM defeito WHERE nome = ?";

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, nomeDefeito);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar id do tipo de peça no banco de dados: " + e.getMessage());
        }
        return null; // Retorna null se não encontrar o defeito
    }
}

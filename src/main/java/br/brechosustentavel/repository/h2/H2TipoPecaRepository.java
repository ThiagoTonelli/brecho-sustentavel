/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.h2;

import br.brechosustentavel.repository.repositoryFactory.ConexaoFactory;
import br.brechosustentavel.repository.repositoryFactory.ITipoDePecaRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thiag
 */
public class H2TipoPecaRepository implements ITipoDePecaRepository {
    private final ConexaoFactory conexaoFactory;

    public H2TipoPecaRepository(ConexaoFactory conexaoFactory) {
        this.conexaoFactory = conexaoFactory;
    }

    @Override
    public List<String> buscarTiposDePeca() {
        List<String> tipos = new ArrayList<>();
        try (Connection conexao = this.conexaoFactory.getConexao();
             Statement stmt = conexao.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT nome FROM tipo_peca");
            while (rs.next()) {
                tipos.add(rs.getString("nome"));
            }
            return tipos;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tipos de peça no banco de dados", e);
        }
    }

    @Override
    public int buscarIdTipo(String tipo) {
        String sql = "SELECT id FROM tipo_peca WHERE nome = ?";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, tipo);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? rs.getInt("id") : -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar id do tipo de peça no banco de dados", e);
        }
    }

    @Override
    public String buscarNomeTipo(int idTipo) {
        String sql = "SELECT nome FROM tipo_peca WHERE id = ?";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, idTipo);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? rs.getString("nome") : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar nome do tipo de peça no banco de dados", e);
        }
    }

    @Override
    public List<Map<String, Object>> buscarTodosParaManutencao() {
        List<Map<String, Object>> tipos = new ArrayList<>();
        String sql = "SELECT id, nome FROM tipo_peca ORDER BY nome";

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> tipoPeca = new HashMap<>();
                tipoPeca.put("id", rs.getInt("id"));
                tipoPeca.put("nome", rs.getString("nome"));
                tipos.add(tipoPeca);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os tipos de peça: " + e.getMessage(), e);
        }
        return tipos;
    }

    @Override
    public void salvar(Integer id, String nome) {
        String sql = (id == null) 
                ? "INSERT INTO tipo_peca (nome) VALUES (?)" 
                : "UPDATE tipo_peca SET nome = ? WHERE id = ?";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            if (id != null) pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(id == null 
                ? "Erro ao inserir novo tipo de peça: " + e.getMessage() 
                : "Erro ao atualizar tipo de peça: " + e.getMessage(), e);
        }
    }

    @Override
    public void excluir(int idTipoPeca) {
        String sql = "DELETE FROM tipo_peca WHERE id = ?";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, idTipoPeca);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir tipo de peça. Verifique se ele não está em uso por defeitos ou peças.", e);
        }
    }
}

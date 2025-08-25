/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.h2;

import br.brechosustentavel.repository.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import br.brechosustentavel.repository.IComposicaoRepository;
import java.util.ArrayList;

/**
 *
 * @author thiag
 */

public class H2ComposicaoRepository implements IComposicaoRepository {
    private final ConexaoFactory conexaoFactory;

    public H2ComposicaoRepository(ConexaoFactory conexaoFactory) {
        this.conexaoFactory = conexaoFactory;
    }
    
    @Override
    public Map<String, Double> buscarMateriais() {
        Map<String, Double> composicao_valor = new HashMap<>();
        String sql = "SELECT nome, fator_emissao FROM composicao";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                composicao_valor.put(rs.getString("nome"), rs.getDouble("fator_emissao"));
            }
            return composicao_valor;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar materiais no H2", e);
        }
    }

    @Override
    public Integer buscarIdComposicaoPorNome(String nome) {
        String sql = "SELECT id FROM composicao WHERE nome = ?";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar id da composição no H2: " + e.getMessage());
        }
        return null; 
    }
    
    @Override
    public Map<String, Double> buscarMateriaisNome(List<String> nomes) {
        Map<String, Double> materiais = new HashMap<>();
        String sql = "SELECT nome, fator_emissao FROM composicao WHERE nome = ?";
        
        for (String material : nomes) {
            try (Connection conexao = this.conexaoFactory.getConexao();
                 PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                
                pstmt.setString(1, material);            
                ResultSet rs = pstmt.executeQuery();
                
                while (rs.next()) {
                    materiais.put(rs.getString("nome"), rs.getDouble("fator_emissao"));
                }
                
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao buscar material no H2", e);
            }
        }
        return materiais;
    }
    
    @Override
    public List<Map<String, Object>> buscarTodosParaManutencao() {
        List<Map<String, Object>> composicoes = new ArrayList<>();
        String sql = "SELECT id, nome, fator_emissao FROM composicao ORDER BY nome";

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> composicao = new HashMap<>();
                composicao.put("id", rs.getInt("id"));
                composicao.put("nome", rs.getString("nome"));
                composicao.put("fator_emissao", rs.getDouble("fator_emissao"));
                composicoes.add(composicao);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todas as composições no H2: " + e.getMessage(), e);
        }
        return composicoes;
    }

    @Override
    public void salvar(Integer id, String nome, double fatorEmissao) {
        if (id == null) {
            String sql = "INSERT INTO composicao (nome, fator_emissao) VALUES (?, ?)";
            try (Connection conexao = this.conexaoFactory.getConexao();
                 PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                pstmt.setString(1, nome);
                pstmt.setDouble(2, fatorEmissao);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao inserir nova composição no H2: " + e.getMessage(), e);
            }
        } else {
            String sql = "UPDATE composicao SET nome = ?, fator_emissao = ? WHERE id = ?";
            try (Connection conexao = this.conexaoFactory.getConexao();
                 PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                pstmt.setString(1, nome);
                pstmt.setDouble(2, fatorEmissao);
                pstmt.setInt(3, id);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao atualizar composição no H2: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public void excluir(int idComposicao) {
        String sql = "DELETE FROM composicao WHERE id = ?";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, idComposicao);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir composição no H2. Verifique se ela não está em uso por alguma peça.", e);
        }
    }
}

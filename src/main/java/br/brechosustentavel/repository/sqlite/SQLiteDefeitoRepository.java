/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.repository.repositoryFactory.ConexaoFactory;
import br.brechosustentavel.repository.repositoryFactory.IDefeitoRepository;
import br.brechosustentavel.repository.repositoryFactory.ITipoDePecaRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        return null;
    }
    
    public List<Map<String, Object>> buscarTodosParaManutencao() {
        List<Map<String, Object>> defeitos = new ArrayList<>();
        String sql = """
                     SELECT
                         d.id,
                         d.nome,
                         d.desconto,
                         tp.nome as tipo_peca_nome
                     FROM
                         defeito d
                     JOIN
                         tipo_peca tp ON d.id_tipo = tp.id
                     ORDER BY
                         tp.nome, d.nome
                     """;

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> defeito = new HashMap<>();
                defeito.put("id", rs.getInt("id"));
                defeito.put("nome", rs.getString("nome"));
                defeito.put("desconto", rs.getDouble("desconto"));
                defeito.put("tipo_peca_nome", rs.getString("tipo_peca_nome"));
                defeitos.add(defeito);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os defeitos: " + e.getMessage(), e);
        }
        return defeitos;
    }

    @Override
    public void salvar(Integer id, String nome, double desconto, int idTipoPeca) {
        if (id == null) {
            String sql = "INSERT INTO defeito (nome, desconto, id_tipo) VALUES (?, ?, ?)";
            try (Connection conexao = this.conexaoFactory.getConexao();
                 PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                pstmt.setString(1, nome);
                pstmt.setDouble(2, desconto);
                pstmt.setInt(3, idTipoPeca);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao inserir novo defeito: " + e.getMessage(), e);
            }
        } else {
            String sql = "UPDATE defeito SET nome = ?, desconto = ?, id_tipo = ? WHERE id = ?";
            try (Connection conexao = this.conexaoFactory.getConexao();
                 PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                pstmt.setString(1, nome);
                pstmt.setDouble(2, desconto);
                pstmt.setInt(3, idTipoPeca);
                pstmt.setInt(4, id);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao atualizar defeito com ID " + id + ": " + e.getMessage(), e);
            }
        }
    }

    @Override
    public void excluir(int idDefeito) {
        String sql = "DELETE FROM defeito WHERE id = ?";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, idDefeito);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir defeito com ID " + idDefeito + ". Verifique se ele não está em uso.", e);
        }
    }
    
}

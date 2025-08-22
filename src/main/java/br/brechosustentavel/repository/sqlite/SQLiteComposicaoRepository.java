/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;


import br.brechosustentavel.model.Material;
import br.brechosustentavel.repository.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import br.brechosustentavel.repository.IComposicaoRepository;

/**
 *
 * @author thiag
 */

public class SQLiteComposicaoRepository implements IComposicaoRepository{
    private final ConexaoFactory conexaoFactory;

    public SQLiteComposicaoRepository(ConexaoFactory conexaoFactory) {
        this.conexaoFactory = conexaoFactory;
    }
    
    @Override
    public Map<String, Double> buscarMateriais(){
        Map<String, Double> composicao_valor = new HashMap<>();
        String sql = "SELECT nome, fator_emissao FROM composicao;";
        try (Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                composicao_valor.put(rs.getString("nome"), rs.getDouble("fator_emissao"));
            }
            return composicao_valor;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar materiais no banco de dados", e);
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
        }
        catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar id da composicao da peca no banco de dados: " + e.getMessage());
        }
        return null; // Retorna null se não encontrar o defeito
    }

    @Override
    public Optional<Material> consultar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public Map<String, Double> buscarMateriaisNome(List<String> nomes) {
        Map<String, Double> materiais = new HashMap<>();
        String sql = "SELECT nome, fator_emissao FROM composicao WHERE nome = ?";
        
        for(String material : nomes){

            try (Connection conexao = this.conexaoFactory.getConexao();
                 PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                
                pstmt.setString(1, material);            
                ResultSet rs = pstmt.executeQuery();
                
                while (rs.next()) {
                    materiais.put(rs.getString("nome"), rs.getDouble("fator_emissao"));
                }
                
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao buscar id do tipo de peça no banco de dados", e);
            }
        }
        return materiais;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;


import br.brechosustentavel.model.Material;
import br.brechosustentavel.repository.IMaterialRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author thiag
 */

public class SQLiteMaterialRepository implements IMaterialRepository{
    private Connection conexao;

    public SQLiteMaterialRepository(Connection conexao) {
        this.conexao = conexao;
    }
    
    @Override
    public Map<String, Double> buscarMateriais(){
        Map<String, Double> composicao_valor = new HashMap<>();
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nome, fator_emissao FROM composicao");
            while(rs.next()){
                composicao_valor.put(rs.getString("nome"), rs.getDouble("fator_emissao"));
            
            }
            return composicao_valor;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar materiais no banco de dados", e);
        }
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
    public Optional<Material> consultar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public Map<String, Double> buscarMateriaisNome(List<String> nomes) {
        Map<String, Double> materiais = new HashMap<>();
        for(String material : nomes){
            String sql = "SELECT nome, fator_emissao FROM composicao WHERE nome = ?";
            try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                pstmt.setString(1, material);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        materiais.put(rs.getString("nome"), rs.getDouble("fator_emissao"));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao buscar id do tipo de peça no banco de dados", e);
            }
        }
        return materiais;
    }
}

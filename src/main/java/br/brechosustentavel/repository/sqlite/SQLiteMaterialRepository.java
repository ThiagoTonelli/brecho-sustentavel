/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author thiag
 */
public class SQLiteMaterialRepository {
    private Connection conexao;

    public SQLiteMaterialRepository(Connection conexao) {
        this.conexao = conexao;
    }
    
    public Map<String, Double> buscarMateriais(){
        Map<String, Double> composicao_valor = new HashMap<>();
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nome, fato_emissao FROM composicao");
            while(rs.next()){
                composicao_valor.put(rs.getString("nome"), rs.getDouble("fator_emissao"));
            
            }
            return composicao_valor;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tipos de peça no banco de dados", e);
        }
    }
}

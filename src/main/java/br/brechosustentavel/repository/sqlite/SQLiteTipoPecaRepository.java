/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.repository.ITipoDePecaRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;


/**
 *
 * @author thiag
 */
public class SQLiteTipoPecaRepository implements ITipoDePecaRepository{
    private Connection conexao;

    public SQLiteTipoPecaRepository(Connection conexao) {
        this.conexao = conexao;
    }
    
    @Override
    public List<String> buscarTiposDePeca() {
        List<String> tipos = new ArrayList<>();
        try {
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nome FROM tipo_peca");
            while(rs.next()){
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
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, tipo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    return -1; // ou algum valor que indique "não encontrado"
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar id do tipo de peça no banco de dados", e);
        }
    }
 
}

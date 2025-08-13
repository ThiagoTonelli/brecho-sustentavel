/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.Defeito;
import br.brechosustentavel.repository.IDefeitoRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author thiag
 */
public class SQLiteDefeitoRepository implements IDefeitoRepository{
    private Connection conexao;

    public SQLiteDefeitoRepository(Connection conexao) {
        this.conexao = conexao;
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
    public Map<String, Double> buscarDefeitos(String tipoPeca) {
        Map<String, Double> defeitos = new HashMap<>();
        int tipo = new SQLiteTipoPecaRepository(conexao).buscarIdTipo(tipoPeca);
        String sql = "SELECT nome, desconto FROM defeito WHERE id_tipo = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, tipo);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    defeitos.put(rs.getString("nome"), rs.getDouble("desconto"));
                }
                return defeitos;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar id do tipo de peça no banco de dados", e);
        }
    }

    @Override
    public Optional<Defeito> consultar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

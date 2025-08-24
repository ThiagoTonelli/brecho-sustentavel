/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.Transacao;
import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.ITransacaoRepository;
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
public class SQLiteTransacaoRepository implements ITransacaoRepository{
    private final ConexaoFactory conexaoFactory;

    public SQLiteTransacaoRepository(ConexaoFactory conexaoFactory) {
        this.conexaoFactory = conexaoFactory;
    }
    
    @Override
    public List<Map<String, Object>> buscarDadosParaExportacao() {
        List<Map<String, Object>> dados = new ArrayList<>();
        String sql = """
            SELECT
                t.id AS "ID Transação",
                t.data AS "Data",
                p.massa AS "Massa",
                (SELECT elt.gwp_evitado FROM evento_linha_tempo elt WHERE elt.id_peca = p.id_c ORDER BY elt.data ASC LIMIT 1) AS "GWP Base",
                a.gwp AS "GWP Evitado",
                a.mci AS "MCI"
            FROM
                transacao t
            INNER JOIN
                oferta o ON t.id_oferta = o.id
            INNER JOIN
                anuncio a ON o.id_anuncio = a.id
            INNER JOIN
                peca p ON a.id_peca = p.id_c
            ORDER BY
                t.data;
            """;

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> linha = new HashMap<>();
                linha.put("ID", rs.getInt("ID Transação"));
                linha.put("Data", rs.getString("Data"));
                linha.put("Massa", rs.getDouble("Massa"));
                linha.put("GWP_base", rs.getDouble("GWP Base"));
                linha.put("GWP_avoided", rs.getDouble("GWP Evitado"));
                linha.put("MCI_item", rs.getDouble("MCI"));
                dados.add(linha);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar dados de transações para exportação: " + e.getMessage(), e);
        }
        return dados;
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
    public Optional<Transacao> consultar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.Oferta;
import br.brechosustentavel.model.Transacao;
import br.brechosustentavel.repository.repositoryFactory.ConexaoFactory;
import br.brechosustentavel.repository.repositoryFactory.IOfertaRepository;
import br.brechosustentavel.repository.repositoryFactory.ITransacaoRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public void salvar(Transacao transacao) {
        String sql = "INSERT INTO transacao (id_oferta, valor_total) VALUES (?, ?);";
        
        try(Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setInt(1, transacao.getOferta().getId());
            pstmt.setDouble(2, transacao.getValorTotal());
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                transacao.setId(rs.getInt(1));
            }
       } catch(SQLException e) {
           throw new RuntimeException("Erro ao salvar transação: " + e.getMessage());
       }   
    }
    
    @Override
    public Optional<Transacao> buscarPorId(int id) {

        String sql = "SELECT * FROM transacao WHERE id = ?;";

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int idOferta = rs.getInt("id_oferta");

                IOfertaRepository ofertaRepo = RepositoryFactory.getInstancia().getOfertaRepository();
                Oferta ofertaCompleta = ofertaRepo.buscarOfertaPorId(idOferta)
                        .orElseThrow(() -> new SQLException("Oferta com ID " + idOferta + " associada à transação não foi encontrada."));

                Transacao transacao = new Transacao(
                        ofertaCompleta,
                        rs.getDouble("valor_total")
                );
                transacao.setId(rs.getInt("id"));


                String dataStr = rs.getString("data");
                if (dataStr != null) {
                    transacao.setData(LocalDateTime.parse(dataStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                }

                return Optional.of(transacao);
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar transação por ID: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Transacao> buscarPorComprador(int idComprador) {
        List<Transacao> transacoes = new ArrayList<>();
        // A query busca todas as transações onde o ID do comprador da oferta corresponde.
        String sql = "SELECT id FROM transacao WHERE id_oferta IN (SELECT id FROM oferta WHERE id_comprador = ?)";

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setInt(1, idComprador);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // Reutiliza o método buscarPorId que já monta o objeto Transacao completo
                buscarPorId(rs.getInt("id")).ifPresent(transacoes::add);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar transações por comprador: " + e.getMessage(), e);
        }
        return transacoes;
    }

    @Override
    public List<Transacao> buscarPorVendedor(int idVendedor) {
        List<Transacao> transacoes = new ArrayList<>();
        // A query busca todas as transações onde o ID do vendedor do anúncio corresponde.
        String sql = "SELECT t.id FROM transacao t JOIN oferta o ON t.id_oferta = o.id JOIN anuncio a ON o.id_anuncio = a.id WHERE a.id_vendedor = ?";

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setInt(1, idVendedor);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                buscarPorId(rs.getInt("id")).ifPresent(transacoes::add);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar transações por vendedor: " + e.getMessage(), e);
        }
        return transacoes;
    }

    
    
}

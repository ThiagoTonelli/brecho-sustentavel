package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.model.Peca;
import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IAnuncioRepository;
import br.brechosustentavel.repository.IDefeitoRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author thiag
 */
public class SQLiteAnuncioRepository implements IAnuncioRepository{
    private ConexaoFactory conexaoFactory;
    
    public SQLiteAnuncioRepository(ConexaoFactory conexaoFactory){
        this.conexaoFactory = conexaoFactory;
    }
    
    @Override
    public void criar(Anuncio anuncio){
        String sql = "INSERT INTO anuncio(id_vendedor, id_peca, valor_final, gwp, mci) VALUES(?, ?, ?, ?, ?)";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            
            pstmt.setInt(1, anuncio.getIdVendedor());
            pstmt.setString(2, anuncio.getPeca().getId_c());
            pstmt.setDouble(3, anuncio.getValorFinal());
            pstmt.setDouble(4, anuncio.getGwpAvoided());
            pstmt.setDouble(5, anuncio.getMci());
            
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir anuncio no banco de dados: " + e.getMessage());
        }
    }
    
    @Override 
    public List<Anuncio> buscarAnuncios(int id_vendedor, IDefeitoRepository repositoryDefeitoPeca) {
        // Mapa para evitar duplicatas de anúncios se uma peça tiver múltiplos defeitos
        Map<String, Anuncio> anuncioPorIdPeca = new HashMap<>();
        
        // 1. NOVA CONSULTA SQL COM JOINS PARA BUSCAR O NOME DO TIPO DA PEÇA
        // Usamos LEFT JOIN para garantir que anúncios de peças sem defeitos também apareçam.
        // Usamos DISTINCT para pegar apenas uma linha por anúncio.
        String sqlAnuncios = """
            SELECT DISTINCT
                a.id, a.id_vendedor, a.id_peca, a.valor_final, a.gwp, a.mci,
                p.subcategoria, p.tamanho, p.cor, p.massa,
                p.estado_conservacao, p.preco_base,
                tp.nome AS nome_tipo 
            FROM
                anuncio a
            JOIN
                peca p ON a.id_peca = p.id_c
            LEFT JOIN
                defeito_peca dp ON p.id_c = dp.id_peca
            LEFT JOIN
                defeito d ON dp.id_defeito = d.id
            LEFT JOIN
                tipo_peca tp ON d.id_tipo = tp.id
            WHERE
                a.id_vendedor = ?;
            """;

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sqlAnuncios)) {

            pstmt.setInt(1, id_vendedor);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String pecaId = rs.getString("id_peca");

                if (anuncioPorIdPeca.containsKey(pecaId)) {
                    continue;
                }

                Peca peca = new Peca(
                    pecaId,
                    rs.getString("subcategoria"),
                    rs.getString("tamanho"),
                    rs.getString("cor"),
                    rs.getDouble("massa"),
                    rs.getString("estado_conservacao"),
                    rs.getDouble("preco_base")
                );
                peca.setTipoDePeca(rs.getString("nome_tipo"));

                Anuncio anuncio = new Anuncio(
                    rs.getInt("id_vendedor"),
                    peca,
                    rs.getDouble("valor_final"),
                    rs.getDouble("gwp"),
                    rs.getDouble("mci")
                );
                anuncio.setIdAnuncio(rs.getInt("id"));

                anuncioPorIdPeca.put(pecaId, anuncio);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar anúncios no banco de dados: " + e.getMessage());
        }

        if (anuncioPorIdPeca.isEmpty()) {
            return new ArrayList<>();
        }

        for (Anuncio anuncio : anuncioPorIdPeca.values()) {
            String pecaId = anuncio.getPeca().getId_c();
            Map<String, Double> defeitos = repositoryDefeitoPeca.buscarDefeitosPorTipo(pecaId);
            anuncio.getPeca().setDefeitos(defeitos);
        }

        return new ArrayList<>(anuncioPorIdPeca.values());
    }
}

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
import java.util.Optional;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author thiag
 */
public class SQLiteAnuncioRepository implements IAnuncioRepository{
    private final ConexaoFactory conexaoFactory;
    
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
    public void editar(Anuncio anuncio) {
        String sql = "UPDATE anuncio SET valor_final = ?, gwp = ?, mci = ? WHERE id_peca = ?";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setDouble(1, anuncio.getValorFinal());
            pstmt.setDouble(2, anuncio.getGwpAvoided());
            pstmt.setDouble(3, anuncio.getMci());
            pstmt.setString(4, anuncio.getPeca().getId_c());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao editar o anúncio: " + e.getMessage(), e);
        }
    }
    
    @Override 
    public List<Anuncio> buscarAnuncios(int idVendedor, IDefeitoRepository repositoryDefeitoPeca) {
        Map<String, Anuncio> anuncioPorIdPeca = new HashMap<>();
        
        String sqlAnuncios = """
            SELECT
                a.id, a.id_vendedor, a.id_peca, a.valor_final, a.gwp, a.mci,
                p.subcategoria, p.tamanho, p.cor, p.massa,
                p.estado_conservacao, p.preco_base,
                tp.nome AS nome_tipo
            FROM
                anuncio a
            JOIN
                peca p ON a.id_peca = p.id_c
            LEFT JOIN
                tipo_peca tp ON p.id_tipo = tp.id
            WHERE
                a.id_vendedor = ?;
            """;

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sqlAnuncios)) {

            pstmt.setInt(1, idVendedor);
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
                anuncio.setId(rs.getInt("id"));

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

    @Override
    public int qtdAnuncioPorVendedor(int idVendedor) {
        String sql = "SELECT COUNT(*) FROM anuncio WHERE id_vendedor = ?;";
        
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, idVendedor);
            
            ResultSet rs = pstmt.executeQuery(); 
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar os anúncios do vendedor: " + e.getMessage());
        }
    }

    @Override
    public Optional<Anuncio> buscarAnuncioPorId(int idAnuncio) {
        String sql = """
                     SELECT
                        a.id as anuncio_id, a.valor_final, a.gwp, a.mci,
                        p.id_c as peca_id_c, p.subcategoria, p.tamanho, p.cor, p.massa, p.estado_conservacao, p.preco_base,
                        a.id_vendedor
                    FROM anuncio a
                    JOIN peca p ON a.id_peca = p.id_c
                    WHERE a.id = ?;
                     """;

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            
            pstmt.setInt(1, idAnuncio);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Peca peca = new Peca(rs.getString("id_c"), rs.getString("subcategoria"), rs.getString("tamanho"), rs.getString("cor"), 
                        rs.getDouble("massa"), rs.getString("estado_conservacao"), rs.getDouble("preco_base"));

                Anuncio anuncio = new Anuncio(
                    rs.getInt("id_vendedor"),
                    peca,
                    rs.getDouble("valor_final"),
                    rs.getDouble("gwp"),
                    rs.getDouble("mci")
                );
                anuncio.setId(rs.getInt("anuncio_id"));   
                
                return Optional.of(anuncio);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar anuncio por id: " + e.getMessage());
        }  
    }
}
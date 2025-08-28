/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.Peca;
import br.brechosustentavel.repository.repositoryFactory.ConexaoFactory;
import br.brechosustentavel.repository.repositoryFactory.IPecaRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author thiag
 */
public class SQLitePecaRepository implements IPecaRepository{
    private final ConexaoFactory conexaoFactory;

    public SQLitePecaRepository(ConexaoFactory conexaoFactory) {
        this.conexaoFactory = conexaoFactory;
    }

    @Override
    public void criar(Peca peca) {
        String sql = "INSERT INTO peca(id_c, subcategoria, tamanho, cor, massa, estado_conservacao, preco_base, id_tipo) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, peca.getId_c());
            pstmt.setString(2, peca.getSubcategoria());
            pstmt.setString(3, peca.getTamanho());
            pstmt.setString(4, peca.getCor());
            pstmt.setDouble(5, peca.getMassaEstimada());
            pstmt.setString(6, peca.getEstadoDeConservacao());
            pstmt.setDouble(7, peca.getPrecoBase());
            pstmt.setInt(8, peca.getIdTipoDePeca());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("erro ao inserir peça", e);
        }
    }

    @Override
    public void editar(Peca peca) {
        String sql = """
                     UPDATE peca SET
                         subcategoria = ?,
                         tamanho = ?,
                         cor = ?,
                         massa = ?,
                         estado_conservacao = ?,
                         preco_base = ?,
                         id_tipo = ?
                     WHERE id_c = ?
                     """;

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, peca.getSubcategoria());
            pstmt.setString(2, peca.getTamanho());
            pstmt.setString(3, peca.getCor());
            pstmt.setDouble(4, peca.getMassaEstimada());
            pstmt.setString(5, peca.getEstadoDeConservacao());
            pstmt.setDouble(6, peca.getPrecoBase());
            pstmt.setInt(7, peca.getIdTipoDePeca());
            pstmt.setString(8, peca.getId_c()); 

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao editar a peça: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean ExisteId_c(String id_c) {
        String sql = "SELECT * FROM peca WHERE id_c = ?";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, id_c);
            try (ResultSet rs = pstmt.executeQuery()) {
                    return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar id de peça no banco de dados", e);
        }
    }
    
    @Override
    public Optional<Peca> consultar(String idC) {
        String sql = """
                     SELECT 
                        p.*,
                        tp.nome AS nome_peca
                     FROM peca p 
                     JOIN tipo_peca tp ON p.id_tipo = tp.id
                     WHERE p.id_c = ?
                     """;

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, idC);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    
                    String id_c = rs.getString("id_c");
                    String subcategoria = rs.getString("subcategoria");
                    String tamanho = rs.getString("tamanho");
                    String cor = rs.getString("cor");
                    double massa = rs.getDouble("massa");
                    String estado = rs.getString("estado_conservacao");
                    double preco = rs.getDouble("preco_base");
                    int idTipo = rs.getInt("id_tipo");
                    Peca peca = new Peca(id_c, subcategoria, tamanho, cor, massa, estado, preco);
                    peca.setIdTipoDePeca(idTipo);
                    peca.setTipoDePeca(rs.getString("nome_peca"));
                    
                    return Optional.of(peca);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar peças no banco de dados", e);
        }
        return Optional.empty();
    }
    
}

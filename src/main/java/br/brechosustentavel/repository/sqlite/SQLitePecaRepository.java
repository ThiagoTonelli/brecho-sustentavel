/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.Peca;
import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IPecaRepository;
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
    public boolean ExisteId_c(String id_c) {
        String sql = "SELECT * FROM peca WHERE id_c = ?";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, id_c);
            try (ResultSet rs = pstmt.executeQuery()) {
                    return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar id do tipo de peça no banco de dados", e);
        }
    }
    
    @Override
    public Optional<Peca> consultar(String idC) {
        // Define a consulta SQL para buscar a peça pelo id_c.
        String sql = "SELECT * FROM peca WHERE id_c = ?";

        // O try-with-resources garante que a conexão e o statement serão fechados automaticamente.
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            // Define o valor do parâmetro da consulta para evitar SQL Injection.
            pstmt.setString(1, idC);

            // Executa a consulta e obtém o resultado.
            try (ResultSet rs = pstmt.executeQuery()) {
                // Verifica se o ResultSet retornou algum registro.
                if (rs.next()) {
                    // Se encontrou, cria e preenche um objeto Peca com os dados do registro.
                    
                    String id_c = rs.getString("id_c");
                    String subcategoria = rs.getString("subcategoria");
                    String tamanho = rs.getString("tamanho");
                    String cor = rs.getString("cor");
                    double massa = rs.getDouble("massa");
                    String estado = rs.getString("estado_conservacao");
                    double preco = rs.getDouble("preco_base");
                    Peca peca = new Peca(id_c, subcategoria, tamanho, cor, massa, estado, preco);
                    // Retorna um Optional contendo o objeto Peca preenchido.
                    return Optional.of(peca);
                }
            }
        } catch (SQLException e) {
            // Em um projeto real, trate a exceção de forma adequada (log, etc.).
            System.err.println("Erro ao buscar peça por ID: " + e.getMessage());
            // Opcional: Lançar uma exceção customizada de acesso a dados.
            // throw new DataAccessException("Falha ao consultar o banco de dados", e);
        }

        // Se nenhum registro foi encontrado ou se ocorreu uma exceção, retorna um Optional vazio.
        return Optional.empty();
    }
    
    
    
    
}

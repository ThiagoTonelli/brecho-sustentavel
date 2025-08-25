/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.h2;

import br.brechosustentavel.model.Insignia;
import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IInsigniaRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/**
 *
 * @author thiag
 */
public class H2InsigniaRepository implements IInsigniaRepository {
    private final ConexaoFactory conexaoFactory;

    public H2InsigniaRepository(ConexaoFactory conexaoFactory) {
        this.conexaoFactory = conexaoFactory;
    }

    @Override
    public void inserirInsignia(Insignia insignia) {
        String sql = "INSERT INTO insignia (nome, valor_estrelas, tipo_perfil) VALUES (?, ?, ?);";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, insignia.getNome());
            pstmt.setDouble(2, insignia.getValorEstrelas());
            pstmt.setString(3, insignia.getTipoPerfil());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                insignia.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar insignia: " + e.getMessage());
        }
    }

    @Override
    public Optional<Insignia> buscarInsigniaPorNome(String nome) {
        String sql = "SELECT * FROM insignia WHERE nome = ?;";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Insignia insignia = new Insignia(
                        rs.getString("nome"),
                        rs.getDouble("valor_estrelas"),
                        rs.getString("tipo_perfil")
                );
                insignia.setId(rs.getInt("id"));
                return Optional.of(insignia);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar insignia por nome: " + e.getMessage());
        }
    }
}

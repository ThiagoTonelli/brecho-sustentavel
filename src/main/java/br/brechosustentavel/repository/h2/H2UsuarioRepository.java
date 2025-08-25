/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.h2;

import br.brechosustentavel.model.Comprador;
import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.model.Vendedor;
import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IUsuarioRepository;
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
public class H2UsuarioRepository implements IUsuarioRepository {
    private final ConexaoFactory conexaoFactory;

    public H2UsuarioRepository(ConexaoFactory conexaoFactory) {
        this.conexaoFactory = conexaoFactory;
    }

    @Override
    public void cadastrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, telefone, email, senha, admin) VALUES (?, ?, ?, ?, ?);";

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getTelefone());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getSenha());
            pstmt.setBoolean(5, usuario.isAdmin());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar usuário: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        String sql = """
                     SELECT
                         u.id, u.nome, u.telefone, u.email, u.senha, u.admin,
                         v.id_vendedor, v.nivel_reputacao as vendedor_reputacao, v.estrelas as vendedor_estrelas, v.vendas_concluidas, v.gwp_contribuido,
                         c.id_comprador, c.nivel_reputacao as comprador_reputacao, c.estrelas as comprador_estrelas, c.compras_finalizadas, c.gwp_evitado, c.selo_verificador
                     FROM usuario u
                     LEFT JOIN vendedor v ON u.id = v.id_vendedor
                     LEFT JOIN comprador c ON u.id = c.id_comprador
                     WHERE u.email = ?;
                     """;

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = criarUsuarioComPerfis(rs);
                    return Optional.of(usuario);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por email: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Usuario> buscarPorId(int id) {
        String sql = """
                     SELECT
                         u.id, u.nome, u.telefone, u.email, u.senha, u.admin,
                         v.id_vendedor, v.nivel_reputacao as vendedor_reputacao, v.estrelas as vendedor_estrelas, v.vendas_concluidas, v.gwp_contribuido,
                         c.id_comprador, c.nivel_reputacao as comprador_reputacao, c.estrelas as comprador_estrelas, c.compras_finalizadas, c.gwp_evitado, c.selo_verificador
                     FROM usuario u
                     LEFT JOIN vendedor v ON u.id = v.id_vendedor
                     LEFT JOIN comprador c ON u.id = c.id_comprador
                     WHERE u.id = ?;
                     """;

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = criarUsuarioComPerfis(rs);
                    return Optional.of(usuario);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por id: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    @Override
    public boolean isVazio() {
        String sql = "SELECT COUNT(*) AS qtd_usuario FROM usuario;";

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            return rs.next() && rs.getInt("qtd_usuario") == 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar usuários: " + e.getMessage(), e);
        }
    }

    private Usuario criarUsuarioComPerfis(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario(
                rs.getString("nome"),
                rs.getString("telefone"),
                rs.getString("email"),
                rs.getString("senha")
        );
        usuario.setId(rs.getInt("id"));
        usuario.setAdmin(rs.getBoolean("admin"));

        if (rs.getObject("id_vendedor") != null) {
            Vendedor vendedor = new Vendedor(
                    rs.getString("vendedor_reputacao"),
                    rs.getDouble("vendedor_estrelas"),
                    rs.getInt("vendas_concluidas"),
                    rs.getDouble("gwp_contribuido")
            );
            vendedor.setId(rs.getInt("id_vendedor"));
            usuario.setVendedor(vendedor);
        }

        if (rs.getObject("id_comprador") != null) {
            Comprador comprador = new Comprador(
                    rs.getString("comprador_reputacao"),
                    rs.getDouble("comprador_estrelas"),
                    rs.getInt("compras_finalizadas"),
                    rs.getDouble("gwp_evitado"),
                    rs.getBoolean("selo_verificador")
            );
            comprador.setId(rs.getInt("id_comprador"));
            usuario.setComprador(comprador);
        }

        return usuario;
    }
}

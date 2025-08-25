/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.h2;

import br.brechosustentavel.model.Comprador;
import br.brechosustentavel.repository.repositoryFactory.ConexaoFactory;
import br.brechosustentavel.repository.repositoryFactory.ICompradorRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public class H2CompradorRepository implements ICompradorRepository {
    private final ConexaoFactory conexaoFactory;
    
    public H2CompradorRepository(ConexaoFactory conexaoFactory){
        this.conexaoFactory = conexaoFactory;
    }
    
    @Override
    public Optional<Comprador> buscarPorId(int id){
        String sql = "SELECT * FROM comprador WHERE id_comprador = ?";
        
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Comprador comprador = new Comprador(
                        rs.getString("nivel_reputacao"),
                        rs.getDouble("estrelas"),
                        rs.getInt("compras_finalizadas"),
                        rs.getDouble("gwp_evitado"),
                        rs.getBoolean("selo_verificador")
                );
                comprador.setId(rs.getInt("id_comprador"));
                return Optional.of(comprador);
            }
            return Optional.empty();
       } catch (SQLException e) {
           throw new RuntimeException("Erro ao buscar comprador por ID no H2: " + e.getMessage(), e);
       }   
    }

    @Override
    public void salvar(Comprador comprador) {
        String sql = "INSERT INTO comprador (id_comprador) VALUES (?)";
        
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, comprador.getId());
            pstmt.executeUpdate();
       } catch (SQLException e) {
           throw new RuntimeException("Erro ao cadastrar comprador no H2: " + e.getMessage(), e);
       }   
    }

    @Override
    public void atualizarEstrelas(int id, double qtdEstrelas) {
        String sql = "UPDATE comprador SET estrelas = ? WHERE id_comprador = ?";
        
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setDouble(1, qtdEstrelas);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar estrelas do comprador no H2: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Map<String, Integer> contarPorNivelReputacao() {
        Map<String, Integer> contagem = new HashMap<>();
        String sql = "SELECT nivel_reputacao, COUNT(*) as total FROM comprador GROUP BY nivel_reputacao";

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                contagem.put(rs.getString("nivel_reputacao"), rs.getInt("total"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar compradores por reputação no H2: " + e.getMessage(), e);
        }
        return contagem;
    }

    @Override
    public void atualizarCompras(int id) {
        String sql = "UPDATE comprador SET compras_finalizadas = compras_finalizadas + 1 WHERE id_comprador = ?";
        
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar quantidade de compras do comprador no H2: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void editar(Comprador comprador) {
        String sql = "UPDATE comprador SET nivel_reputacao = ?, estrelas = ?, compras_finalizadas = ?, gwp_evitado = ?, selo_verificador = ? WHERE id_comprador = ?";

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, comprador.getNivel());
            pstmt.setDouble(2, comprador.getEstrelas());
            pstmt.setInt(3, comprador.getComprasFinalizadas());
            pstmt.setDouble(4, comprador.getGwpEvitado());
            pstmt.setBoolean(5, comprador.isSelo());
            pstmt.setInt(6, comprador.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar/atualizar o perfil do comprador no H2: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void somarGwpEvitado(int idComprador, double gwpParaAdicionar) {
        String sql = "UPDATE comprador SET gwp_evitado = gwp_evitado + ? WHERE id_comprador = ?";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setDouble(1, gwpParaAdicionar);
            pstmt.setInt(2, idComprador);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao somar GWP evitado do comprador no H2: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizarSelo(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

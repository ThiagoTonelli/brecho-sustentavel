/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.Vendedor;
import br.brechosustentavel.repository.repositoryFactory.ConexaoFactory;
import br.brechosustentavel.repository.repositoryFactory.IVendedorRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public class SQLiteVendedorRepository implements IVendedorRepository{
    private ConexaoFactory conexaoFactory;
    
    public SQLiteVendedorRepository(ConexaoFactory conexaoFactory){
        this.conexaoFactory = conexaoFactory;
    }
    
    @Override
    public Optional<Vendedor> buscarPorId(int id){
        String sql = "SELECT * FROM vendedor WHERE id_vendedor = ?;";
        
        try(Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)){
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                Vendedor vendedor = new Vendedor(
                        rs.getString("nivel_reputacao"),
                        rs.getDouble("estrelas"),
                        rs.getInt("vendas_concluidas"),
                        rs.getDouble("gwp_contribuido")
                );
                vendedor.setId(rs.getInt("id_vendedor"));
                return Optional.of(vendedor);
            }
            return Optional.empty();
       } catch(SQLException e) {
           throw new RuntimeException("Erro ao buscar vendedor por ID: " + e.getMessage());
       }   
    }

    @Override
    public void salvar(Vendedor vendedor) {
        String sql = "INSERT INTO vendedor (id_vendedor) VALUES (?);";
        
        try(Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)){
            pstmt.setInt(1, vendedor.getId());
            pstmt.executeUpdate();
       } catch(SQLException e) {
           throw new RuntimeException("Erro ao cadastrar usuario: " + e.getMessage());
       }   
    }

    @Override
    public void atualizarEstrelas(int id, double qtdEstrelas) {
        String sql = "UPDATE vendedor SET estrelas = ? WHERE id_vendedor = ?;";
        
        try(Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)){
            pstmt.setDouble(1, qtdEstrelas);
            pstmt.setInt(2, id);
            
            pstmt.executeUpdate();
        } catch(SQLException e){
            throw new RuntimeException("Erro ao atualizar estrelas do vendedor: " + e.getMessage());
        }
    }
    
    @Override
    public Map<String, Double> getTopVendedoresPorGWP(int limite) {
        Map<String, Double> ranking = new LinkedHashMap<>();

        String sql = """
                     SELECT
                         u.nome,
                         v.gwp_contribuido
                     FROM
                         vendedor v
                     INNER JOIN
                         usuario u ON v.id_vendedor = u.id
                     ORDER BY
                         v.gwp_contribuido DESC
                     LIMIT ?
                     """;

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setInt(1, limite);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ranking.put(rs.getString("nome"), rs.getDouble("gwp_contribuido"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ranking de vendedores: " + e.getMessage(), e);
        }
        return ranking;
    }
    
    @Override
    public Map<String, Integer> contarPorNivelReputacao() {
        Map<String, Integer> contagem = new HashMap<>();
        String sql = "SELECT nivel_reputacao, COUNT(*) as total FROM vendedor GROUP BY nivel_reputacao";

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                contagem.put(rs.getString("nivel_reputacao"), rs.getInt("total"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar vendedores por reputação: " + e.getMessage(), e);
        }
        return contagem;
    }

    @Override
    public void atualizarVendas(int id) {
        String sql = "UPDATE vendedor SET vendas_concluidas = vendas_concluidas + 1 WHERE id_vendedor = ?;";
        
        try(Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)){
            pstmt.setInt(1, id);
            
            pstmt.executeUpdate();
        } catch(SQLException e){
            throw new RuntimeException("Erro ao atualizar quantidade de vendas do vendedor: " + e.getMessage());
        }
    }

    @Override
    public void editar(Vendedor vendedor) {
        String sql = "UPDATE vendedor SET nivel_reputacao = ?, estrelas = ?, vendas_concluidas = ?, gwp_contribuido = ? WHERE id_vendedor = ?";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, vendedor.getNivel());
            pstmt.setDouble(2, vendedor.getEstrelas());
            pstmt.setInt(3, vendedor.getVendasConcluidas());
            pstmt.setDouble(4, vendedor.getGwpContribuido());
            pstmt.setInt(5, vendedor.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar vendedor: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void somarGwpContribuido(int idVendedor, double gwpParaAdicionar) {
        String sql = "UPDATE vendedor SET gwp_contribuido = gwp_contribuido + ? WHERE id_vendedor = ?";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setDouble(1, gwpParaAdicionar);
            pstmt.setInt(2, idVendedor);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao somar GWP contribuído do vendedor: " + e.getMessage(), e);
        }
    }
}

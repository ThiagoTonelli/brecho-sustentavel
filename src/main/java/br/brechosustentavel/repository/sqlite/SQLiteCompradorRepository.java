/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.Comprador;
import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.ICompradorRepository;
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
public class SQLiteCompradorRepository implements ICompradorRepository{
    private ConexaoFactory conexaoFactory;
    
    public SQLiteCompradorRepository(ConexaoFactory conexaoFactory){
        this.conexaoFactory = conexaoFactory;
    }
    
    @Override
    public Optional<Comprador> buscarPorId(int id){
        String sql = "SELECT * FROM comprador WHERE id_comprador = ?;";
        
        try(Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)){
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
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
       } catch(SQLException e) {
           throw new RuntimeException("Erro ao buscar vendedor por ID: " + e.getMessage());
       }   
    }

    @Override
    public void salvar(Comprador comprador) {
       String sql = "INSERT INTO comprador (id_comprador) VALUES (?);";
        
        try(Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)){
            pstmt.setInt(1, comprador.getId());
            pstmt.executeUpdate();
       } catch(SQLException e) {
           throw new RuntimeException("Erro ao cadastrar usuario: " + e.getMessage());
       }   
    }

    @Override
    public void atualizarEstrelas(int id, double qtdEstrelas) {
        String sql = "UPDATE comprador SET estrelas = ? WHERE id_comprador = ?;";
        
        try(Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)){
            pstmt.setDouble(1, qtdEstrelas);
            pstmt.setInt(2, id);
            
            pstmt.executeUpdate();
        } catch(SQLException e){
            throw new RuntimeException("Erro ao atualizar estrelas do comprador: " + e.getMessage());
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
            throw new RuntimeException("Erro ao contar compradores por reputação: " + e.getMessage(), e);
        }
        return contagem;
    }
    
    
}

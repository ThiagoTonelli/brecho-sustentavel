/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.Denuncia;
import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IDenunciaRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public class SQLiteDenunciaRepository implements IDenunciaRepository{
    private final ConexaoFactory conexaoFactory;
    
    public SQLiteDenunciaRepository(ConexaoFactory conexaoFactory) {
        this.conexaoFactory = conexaoFactory;
    }
    
    @Override
    public void inserirDenuncia(Denuncia denuncia) {
        String sql = "INSERT INTO denuncia (id_anuncio, id_comprador, motivo, descricao, status) VALUES (?, ?, ?, ?, ?);";
        
        try(Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setInt(1, denuncia.getAnuncio().getId());
            pstmt.setInt(2, denuncia.getComprador().getId());
            pstmt.setString(3, denuncia.getMotivo());
            pstmt.setString(4, denuncia.getDescricao());
            pstmt.setString(5, denuncia.getStatus());
            
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                denuncia.setId(rs.getInt(1));
            }
       } catch(SQLException e) {
           throw new RuntimeException("Erro ao inserir denuncia: " + e.getMessage());
       }   
    }

    @Override
    public void atualizarStatusDenuncia(Denuncia denuncia, String novoStatus) {
        String sql = "UPDATE denuncia SET status = ? WHERE id = ?;";
        
        try(Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)){
            pstmt.setString(1, novoStatus);
            pstmt.setInt(2, denuncia.getId());
                      
            if(pstmt.executeUpdate() > 0){
                denuncia.setStatus(novoStatus);
            }
            else{
                throw new RuntimeException("Nenhuma denuncia encontrada com o ID " + denuncia.getId());
            }
       } catch(SQLException e) {
           throw new RuntimeException("Erro ao atualizar status da denuncia: " + e.getMessage());
       }   
    }

    @Override
    public Optional<Denuncia> buscarDenunciaPorId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Denuncia> buscarDenunciaPorStatus(String status) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int qtdDenunciasProcedentesPorComprador(int idComprador) {
        String sql = "SELECT COUNT(*) FROM denuncia WHERE id_comprador = ? AND status = 'Procedente';";
        
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {           
            pstmt.setInt(1, idComprador);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar as denúncias do comprador: " + e.getMessage());
        }     
    }
    
}

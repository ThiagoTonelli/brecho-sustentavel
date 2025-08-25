/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.h2;

import br.brechosustentavel.model.Insignia;
import br.brechosustentavel.repository.repositoryFactory.ConexaoFactory;
import br.brechosustentavel.repository.repositoryFactory.ICompradorInsigniaRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kaila
 */
public class H2CompradorInsigniaRepository implements ICompradorInsigniaRepository {
    private final ConexaoFactory conexaoFactory;
    
    public H2CompradorInsigniaRepository(ConexaoFactory conexaoFactory) {
        this.conexaoFactory = conexaoFactory;
    }
    
    @Override
    public void inserirInsigniaAComprador(int idInsignia, int idComprador) {
        String sql = "INSERT INTO comprador_insignia (id_comprador, id_insignia) VALUES (?, ?)";
        
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, idComprador);
            pstmt.setInt(2, idInsignia);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar insígnia no H2: " + e.getMessage(), e);
        }   
    }

    @Override
    public List<Insignia> buscarInsigniaPorComprador(int idComprador) {
        List<Insignia> insignias = new ArrayList<>();
        
        String sql = """
                     SELECT i.id, i.nome, i.valor_estrelas, i.tipo_perfil 
                     FROM insignia i
                     JOIN comprador_insignia ci ON i.id = ci.id_insignia
                     WHERE ci.id_comprador = ?
                     """;
        
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, idComprador);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Insignia insignia = new Insignia(
                        rs.getString("nome"),
                        rs.getDouble("valor_estrelas"),
                        rs.getString("tipo_perfil")
                );
                insignia.setId(rs.getInt("id"));
                
                insignias.add(insignia);
            }
            return insignias;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar insígnias do comprador no H2: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean compradorPossuiInsignia(int idInsignia, int idComprador) {
        String sql = "SELECT COUNT(*) FROM comprador_insignia WHERE id_comprador = ? AND id_insignia = ?";
        
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, idComprador);
            pstmt.setInt(2, idInsignia);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) { 
            throw new RuntimeException("Erro ao verificar existência da insígnia no H2: " + e.getMessage(), e);
        }
    }
}

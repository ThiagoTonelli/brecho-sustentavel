/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.Insignia;
import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IVendedorInsigniaRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kaila
 */
public class SQLiteVendedorInsigniaRepository implements IVendedorInsigniaRepository{
    private final ConexaoFactory conexaoFactory;
    
    public SQLiteVendedorInsigniaRepository(ConexaoFactory conexaoFactory) {
        this.conexaoFactory = conexaoFactory;
    }
    
    @Override
    public void inserirInsigniaAVendedor(int idInsignia, int idVendedor) {
        String sql = "INSERT INTO vendedor_insignia (id_vendedor, id_insignia) VALUES (?, ?);";
        
        try(Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)){
            pstmt.setInt(1, idVendedor);
            pstmt.setInt(2, idInsignia);
            pstmt.executeUpdate();
       } catch(SQLException e) {
           throw new RuntimeException("Erro ao cadastrar insignia: " + e.getMessage());
       }   
    }

    @Override
    public List<Insignia> buscarInsigniaPorVendedor(int idVendedor) {
        List<Insignia> insignias = new ArrayList<>();
        
        String sql = """
                     SELECT i.id, i.nome, i.valor_estrelas, i.tipo_perfil 
                     FROM insignia i
                     JOIN vendedor_insignia vi ON i.id = vi.id_insignia
                     WHERE vi.id_vendedor = ?;
                     """;
        
        try(Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)){
            pstmt.setInt(1, idVendedor);
            
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
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
            throw new RuntimeException("Erro ao buscar insignias do vendedor: " + e.getMessage());
        }
    }

    @Override
    public boolean vendedorPossuiInsignia(int idInsignia, int idVendedor) {
        String sql = "SELECT COUNT(*) FROM vendedor_insignia WHERE id_vendedor = ? AND id_insignia = ?;";
        
        try(Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, idVendedor);
            pstmt.setInt(2, idInsignia);
            
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1) > 0;
            }
            return false;
        } catch(SQLException e) { 
            throw new RuntimeException("Erro ao verificar existência da insígnia para o vendedor: " + e.getMessage());
        }
    }  
}

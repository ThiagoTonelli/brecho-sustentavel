/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.Comprador;
import br.brechosustentavel.model.Vendedor;
import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.ICompradorRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                return Optional.of(comprador);
            }
            return Optional.empty();
       } catch(SQLException e) {
           throw new RuntimeException("Erro ao buscar vendedor por ID: " + e.getMessage());
       }   
    }
}

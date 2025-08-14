/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.Vendedor;
import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IVendedorRepository;
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
                return Optional.of(vendedor);
            }
            return Optional.empty();
       } catch(SQLException e) {
           throw new RuntimeException("Erro ao buscar vendedor por ID: " + e.getMessage());
       }   
    }

    @Override
    public Vendedor cadastrarVendedor(Vendedor vendedor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

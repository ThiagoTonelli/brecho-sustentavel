/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.repository.IUsuarioRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


/**
 *
 * @author kaila
 */

public class SQLiteUsuarioRepository implements IUsuarioRepository{
    private final Connection conexao;
    
    public SQLiteUsuarioRepository(Connection conexao) {
        this.conexao = conexao;
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("Email inválido");
        }
        
        String sql = "SELECT * FROM usuario WHERE email = ?;";

        try(PreparedStatement pstmt = conexao.prepareStatement(sql)){
           pstmt.setString(1, email);
           ResultSet rs = pstmt.executeQuery();

           if(rs.next()){
               Usuario usuario = new Usuario(
                       rs.getString("nome"),
                       rs.getString("telefone"),
                       rs.getString("email"),
                       rs.getString("senha")
               ); 

               usuario.setId(rs.getInt("id"));
               usuario.setAdmin(rs.getBoolean("admin"));
               
               return Optional.of(usuario);
           }
           return Optional.empty();
           
       }catch(SQLException e){
           throw new RuntimeException("Erro ao buscar usuario por email: " + e.getMessage());
       }
    }    
} 
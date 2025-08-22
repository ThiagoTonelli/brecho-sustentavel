/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.Usuario;
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
 * @author kaila
 */

public class SQLiteUsuarioRepository implements IUsuarioRepository{
    private final ConexaoFactory conexaoFactory;
    
    public SQLiteUsuarioRepository(ConexaoFactory conexaoFactory) {
        this.conexaoFactory = conexaoFactory;
    }

    @Override
    public void cadastrarUsuario(Usuario usuario){       
        String sql = "INSERT INTO usuario (nome, telefone, email, senha, admin) VALUES (?, ?, ?, ?, ?);";
        
        try(Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getTelefone());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getSenha());
            pstmt.setBoolean(5, usuario.isAdmin());
            
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                usuario.setId(rs.getInt(1));
            }
       } catch(SQLException e) {
           throw new RuntimeException("Erro ao cadastrar usuario: " + e.getMessage());
       }   
    }
    
    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = ?;";

        try(Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)) {
           pstmt.setString(1, email);
           ResultSet rs = pstmt.executeQuery();

           if(rs.next()) {
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
        } catch(SQLException e) {
           throw new RuntimeException("Erro ao buscar usuario por email: " + e.getMessage());
        }
    }
    
    @Override
    public boolean isVazio(){
        String sql = "SELECT COUNT(*) AS qtd_usuario FROM usuario;";
        
        try(Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)) {
           ResultSet rs = pstmt.executeQuery();
           
           return rs.next() && rs.getInt(1) == 0;
        } catch(SQLException e) {
           throw new RuntimeException("Erro ao contar usuários: " + e.getMessage());
        }
    }
}
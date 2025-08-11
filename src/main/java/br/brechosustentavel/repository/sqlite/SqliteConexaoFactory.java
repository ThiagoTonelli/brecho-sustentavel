/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.repository.ConexaoFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author kaila
 */
public class SQLiteConexaoFactory extends ConexaoFactory{
    private Connection conexao;
    
    @Override
    public Connection getConexao() {
        try {
            this.conexao = DriverManager.getConnection("jdbc:sqlite:brechosustavel.db");
            return conexao;
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao conectar com SQLite " + e.getMessage());
        } 
    }
    
}

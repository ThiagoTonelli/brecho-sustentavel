/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author kaila
 */
public class SQLiteConexaoFactory extends ConexaoFactory{
    private Connection connection;

    public SQLiteConexaoFactory() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:c:/sqlite/db/chinook.db");
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao conectar com SQLite " + e.getMessage());
        }
    }
    
    @Override
    public Connection getConexao() {
        return this.connection;
    }
}

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
public class ConexaoSQLite {
    private Connection connection;

    public ConexaoSQLite() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:c:/sqlite/db/chinook.db");
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao conectar com SQLite " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
}

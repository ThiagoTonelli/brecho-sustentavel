/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.h2;

import br.brechosustentavel.repository.ConexaoFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author kaila
 */
public final class H2ConexaoFactory extends ConexaoFactory{
    private Connection connection;
    
    public H2ConexaoFactory() {
        try {
            this.connection = DriverManager.getConnection("jdbc:h2:./meu_brecho_h2", "sa", "");
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao conectar com H2 " + e.getMessage());
        }
    }

    @Override
    public Connection getConexao() {
        return this.connection;
    }
}


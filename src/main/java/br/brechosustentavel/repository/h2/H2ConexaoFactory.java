package br.brechosustentavel.repository.h2;

import br.brechosustentavel.repository.repositoryFactory.ConexaoFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2ConexaoFactory extends ConexaoFactory {

     @Override
    public Connection getConexao() {
        try {
            Class.forName("org.h2.Driver");
            String url = "jdbc:h2:./brechosustentavel_h2;AUTO_SERVER=TRUE"; 
            String usuario = "sa"; // utilizador padrão
            String senha = "";     // senha padrão
            
            return DriverManager.getConnection(url, usuario, senha);
            
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Falha ao conectar com H2: " + e.getMessage(), e);
        }
    }
}
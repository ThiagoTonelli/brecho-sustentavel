/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.repositoryFactory;

import br.brechosustentavel.repository.h2.H2ConexaoFactory;
import br.brechosustentavel.repository.sqlite.SQLiteConexaoFactory;
import java.sql.Connection;

/**
 *
 * @author thiag
 */
public abstract class ConexaoFactory {
    
    public static ConexaoFactory getConexaoFactory(String sgdb){
        if(sgdb.equalsIgnoreCase("sqlite")){
            return new SQLiteConexaoFactory();
        }
        else if(sgdb.equalsIgnoreCase("h2")){
            return new H2ConexaoFactory();
        }
        else {
            throw new IllegalArgumentException("O banco de dados selecionado não existe!");
        }
        
    }
    public abstract Connection getConexao();
}

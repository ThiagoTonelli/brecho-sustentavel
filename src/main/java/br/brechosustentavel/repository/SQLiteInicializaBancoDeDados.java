/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author kaila
 */
public class SQLiteInicializaBancoDeDados {
    private Connection conexao;
    
    public SQLiteInicializaBancoDeDados(Connection conexao) {
        this.conexao = conexao;
    }
    
    public void inicializarTabelas(){
        
    }
}

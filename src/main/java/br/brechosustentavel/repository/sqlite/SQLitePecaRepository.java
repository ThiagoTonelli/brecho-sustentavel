/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import java.sql.Connection;

/**
 *
 * @author thiag
 */
public class SQLitePecaRepository {
    private Connection conexao;

    public SQLitePecaRepository(Connection conexao) {
        this.conexao = conexao;
    }
    
}

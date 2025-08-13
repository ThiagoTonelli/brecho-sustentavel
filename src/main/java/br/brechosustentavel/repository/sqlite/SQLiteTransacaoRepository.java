/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.repository.ConexaoFactory;
import java.sql.Connection;

/**
 *
 * @author thiag
 */
public class SQLiteTransacaoRepository {
    private final ConexaoFactory conexaoFactory;

    public SQLiteTransacaoRepository(ConexaoFactory conexaoFactory) {
        this.conexaoFactory = conexaoFactory;
    }
    
    
}

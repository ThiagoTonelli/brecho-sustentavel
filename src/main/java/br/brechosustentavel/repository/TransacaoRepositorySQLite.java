/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository;

import java.sql.Connection;

/**
 *
 * @author thiag
 */
public class TransacaoRepositorySQLite {
    private Connection conexao;

    public TransacaoRepositorySQLite(Connection conexao) {
        this.conexao = conexao;
    }
    
    
}

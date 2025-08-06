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
public class ItemRepositorySQLite {
    private Connection conexao;

    public ItemRepositorySQLite(Connection conexao) {
        this.conexao = conexao;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.inicializador;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author thiag
 */
public abstract class AbstractInicializaBancoDeDados {
    
    protected Connection conexao;

    public AbstractInicializaBancoDeDados(Connection conexao) {
        this.conexao = conexao;
    }

    public void executarConfiguracaoInicial() throws SQLException {
        System.out.println("Verificando a estrutura do banco de dados...");
        inicializar();
        System.out.println("Estrutura do banco de dados verificada.");

        long totalUsuarios = 0;
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM usuario")) {
            if (rs.next()) {
                totalUsuarios = rs.getLong(1);
            }
        }

        if (totalUsuarios == 0) {
            System.out.println("Banco de dados vazio. Populando com dados iniciais...");
            popularDadosIniciais();
            System.out.println("Banco de dados populado com sucesso!");
        } else {
            System.out.println("O banco de dados já está populado.");
        }
    }

    protected abstract void inicializar() throws SQLException;


    protected abstract void popularDadosIniciais() throws SQLException;
}

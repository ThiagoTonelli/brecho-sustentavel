/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository;

import br.brechosustentavel.repository.h2.H2RepositoryFactory;
import br.brechosustentavel.repository.sqlite.SQLiteRepositoryFactory;
import br.brechosustentavel.configuracao.ConfiguracaoAdapter;

/**
 *
 * @author kaila
 */


public abstract class RepositoryFactory {
    private static String sgbd;
    
    public static RepositoryFactory getRepositoryFactory(){
        ConfiguracaoAdapter configuracao = new ConfiguracaoAdapter();
        sgbd = configuracao.getValor("SGBD");
        ConexaoFactory conexao = ConexaoFactory.getConexaoFactory(sgbd);
        if (sgbd.equalsIgnoreCase("sqlite")){
            return new SQLiteRepositoryFactory(conexao.getConexao());
        }
        else if (sgbd.equalsIgnoreCase("h2")){
            return new H2RepositoryFactory(conexao.getConexao());
        }
        throw new IllegalArgumentException("Banco de dados passado por parâmetro não existe!");
    }
    public abstract IUsuarioRepository getUsuarioRepository();
    
    public abstract IMaterialRepository getMaterialRepository();
    
    public abstract ITransacaoRepository getTransacaoRepository();
    
    public abstract IDefeitoRepository getDefeitoRepository();
    
    public abstract IPecaRepository getPecaRepository();
    
    public abstract ITipoDePecaRepository getTipoDePecaRepository();
}

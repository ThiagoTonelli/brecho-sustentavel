/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository;

import br.brechosustentavel.configuracao.ConfiguracaoAdapter;
import java.sql.Connection;

/**
 *
 * @author kaila
 */
public abstract class RepositoryFactory {
    private String sgbd;
    
    public static RepositoryFactory getRepositoryFactory(String sgbd){
        ConfiguracaoAdapter configuracao = new ConfiguracaoAdapter();
        sgbd = configuracao.getValor("SGBD");
        ConexaoFactory conexao = ConexaoFactory.getConexao(sgbd);
        if (sgbd.equalsIgnoreCase("sqlite")){
            return new SQLiteRepositoryFactory((Connection) conexao);
        }
        else if (sgbd.equalsIgnoreCase("h2")){
            return new H2RepositoryFactory((Connection) conexao);
        }
        throw new IllegalArgumentException("Banco de dados passado por parâmetro não existe!");
    }
    public abstract IUsuarioRepository getUsuarioRepository();
    
    public abstract IMaterialRepository getMaterialRepository();
    
    public abstract ITransacaoRepository getTransacaoRepository();
    
    public abstract IDefeitoRepository getDefeitoRepository();
    
    public abstract IItemRepository getItemRepository();
}

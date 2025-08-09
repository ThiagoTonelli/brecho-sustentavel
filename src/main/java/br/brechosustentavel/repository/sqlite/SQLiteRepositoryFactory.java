/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.repository.IDefeitoRepository;
import br.brechosustentavel.repository.IItemRepository;
import br.brechosustentavel.repository.IMaterialRepository;
import br.brechosustentavel.repository.ITransacaoRepository;
import br.brechosustentavel.repository.IUsuarioRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import java.sql.Connection;

/**
 *
 * @author thiag
 */
public class SQLiteRepositoryFactory extends RepositoryFactory{
    private static Connection conexao;
    
    public SQLiteRepositoryFactory(Connection conexao){
        this.conexao = conexao;
    }
    @Override
    public IUsuarioRepository getUsuarioRepository() {
        return new SQLiteUsuarioRepository(conexao);
    }

    @Override
    public IMaterialRepository getMaterialRepository() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ITransacaoRepository getTransacaoRepository() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public IDefeitoRepository getDefeitoRepository() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public IItemRepository getItemRepository() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

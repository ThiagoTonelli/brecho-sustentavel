/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.Insignia;
import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IInsigniaRepository;

/**
 *
 * @author kaila
 */
public class SQLiteInsigniaRepository implements IInsigniaRepository{
    private final ConexaoFactory conexaoFactory;
    
    public SQLiteInsigniaRepository(ConexaoFactory conexaoFactory) {
        this.conexaoFactory = conexaoFactory;
    }

    @Override
    public void inserirInsignia(Insignia insignia) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IAnuncioRepository;
import br.brechosustentavel.repository.ICompradorInsigniaRepository;
import br.brechosustentavel.repository.ICompradorRepository;
import br.brechosustentavel.repository.IDefeitoPecaRepository;
import br.brechosustentavel.repository.IDefeitoRepository;
import br.brechosustentavel.repository.IInsigniaRepository;
import br.brechosustentavel.repository.ILinhaDoTempoRepository;
import br.brechosustentavel.repository.IMaterialRepository;
import br.brechosustentavel.repository.ITipoDePecaRepository;
import br.brechosustentavel.repository.ITransacaoRepository;
import br.brechosustentavel.repository.IUsuarioRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.repository.IPecaRepository;
import br.brechosustentavel.repository.IVendedorInsigniaRepository;
import br.brechosustentavel.repository.IVendedorRepository;

/**
 *
 * @author thiag
 */
public class SQLiteRepositoryFactory extends RepositoryFactory{
    private final ConexaoFactory conexaoFactory;
    
    public SQLiteRepositoryFactory(ConexaoFactory conexaoFactory){
        this.conexaoFactory = conexaoFactory;
    }
    
    @Override
    public IDefeitoRepository getDefeitoRepository() {
        return new SQLiteDefeitoRepository(conexaoFactory, getTipoDePecaRepository());
    }
    
    @Override
    public IUsuarioRepository getUsuarioRepository(){
        return new SQLiteUsuarioRepository(conexaoFactory, getVendedorRepository(), getCompradorRepository());
    }

    @Override
    public IMaterialRepository getMaterialRepository() {
        return new SQLiteMaterialRepository(conexaoFactory);
    }

    @Override
    public ITransacaoRepository getTransacaoRepository() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public IPecaRepository getPecaRepository() {
        return new SQLitePecaRepository(conexaoFactory);
    }

    @Override
    public ITipoDePecaRepository getTipoDePecaRepository() {
        return new SQLiteTipoPecaRepository(conexaoFactory);
    }
    
    @Override
    public ILinhaDoTempoRepository getLinhaDoTempoRepository(){
        return new SQLiteLinhaDoTempoRepository(conexaoFactory);
    }

    @Override
    public IVendedorRepository getVendedorRepository() {
        return new SQLiteVendedorRepository(conexaoFactory);
    }

    @Override
    public ICompradorRepository getCompradorRepository() {
        return new SQLiteCompradorRepository(conexaoFactory);
    }

    @Override
    public IAnuncioRepository getAnuncioRepository() {
        return new SQLiteAnuncioRepository(conexaoFactory);
    }
    
    @Override
    public IDefeitoPecaRepository getDefeitoPecaRepository() {
        return new SQLiteDefeitoPecaRepository(conexaoFactory);
    }

    @Override
    public IInsigniaRepository getInsigniaRepository() {
        return new SQLiteInsigniaRepository(conexaoFactory);
    }

    @Override
    public IVendedorInsigniaRepository getVendedorInsigniaRepository() {
        return new SQLiteVendedorInsigniaRepository(conexaoFactory);
    }

    @Override
    public ICompradorInsigniaRepository getCompradorInsigniaRepository() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

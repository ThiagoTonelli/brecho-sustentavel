/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IAnuncioRepository;
import br.brechosustentavel.repository.IAvaliacaoRepository;
import br.brechosustentavel.repository.IComposicaoPecaRepository;
import br.brechosustentavel.repository.IComposicaoRepository;
import br.brechosustentavel.repository.ICompradorInsigniaRepository;
import br.brechosustentavel.repository.ICompradorRepository;
import br.brechosustentavel.repository.IDefeitoPecaRepository;
import br.brechosustentavel.repository.IDefeitoRepository;
import br.brechosustentavel.repository.IDenunciaRepository;
import br.brechosustentavel.repository.IInsigniaRepository;
import br.brechosustentavel.repository.ILinhaDoTempoRepository;
import br.brechosustentavel.repository.IOfertaRepository;
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
        return new SQLiteUsuarioRepository(conexaoFactory);
    }

    @Override
    public IComposicaoRepository getComposicaoRepository() {
        return new SQLiteComposicaoRepository(conexaoFactory);
    }
    
    @Override
    public IComposicaoPecaRepository getComposicaoPecaRepository() {
        return new SQLiteComposicaoPecaRepository(conexaoFactory);
    }

    @Override
    public ITransacaoRepository getTransacaoRepository() {
        return new SQLiteTransacaoRepository(conexaoFactory);
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
        return new SQLiteCompradorInsigniaRepository(conexaoFactory);
    }

    @Override
    public IOfertaRepository getOfertaRepository() {
        return new SQLiteOfertaRepository(conexaoFactory);
    }

    @Override
    public IDenunciaRepository getDenunciaRepository() {
        return new SQLiteDenunciaRepository(conexaoFactory);
    }
    
    @Override
    public IAvaliacaoRepository getAvaliacaoRepository() {
        return new SQLiteAvaliacaoRepository(conexaoFactory);
    }

}

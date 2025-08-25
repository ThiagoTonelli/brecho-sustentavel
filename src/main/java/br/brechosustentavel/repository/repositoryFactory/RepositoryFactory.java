/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.repositoryFactory;

import br.brechosustentavel.repository.repositoryFactory.IVendedorRepository;
import br.brechosustentavel.repository.repositoryFactory.IVendedorInsigniaRepository;
import br.brechosustentavel.repository.repositoryFactory.IUsuarioRepository;
import br.brechosustentavel.repository.repositoryFactory.ITransacaoRepository;
import br.brechosustentavel.repository.repositoryFactory.ITipoDePecaRepository;
import br.brechosustentavel.repository.repositoryFactory.IPecaRepository;
import br.brechosustentavel.repository.repositoryFactory.IOfertaRepository;
import br.brechosustentavel.repository.repositoryFactory.ILinhaDoTempoRepository;
import br.brechosustentavel.repository.repositoryFactory.IInsigniaRepository;
import br.brechosustentavel.repository.repositoryFactory.IDenunciaRepository;
import br.brechosustentavel.repository.repositoryFactory.IDefeitoRepository;
import br.brechosustentavel.repository.repositoryFactory.IDefeitoPecaRepository;
import br.brechosustentavel.repository.repositoryFactory.ICompradorRepository;
import br.brechosustentavel.repository.repositoryFactory.ICompradorInsigniaRepository;
import br.brechosustentavel.repository.repositoryFactory.IComposicaoRepository;
import br.brechosustentavel.repository.repositoryFactory.IComposicaoPecaRepository;
import br.brechosustentavel.repository.repositoryFactory.IAvaliacaoRepository;
import br.brechosustentavel.repository.repositoryFactory.IAnuncioRepository;
import br.brechosustentavel.repository.repositoryFactory.ConexaoFactory;
import br.brechosustentavel.repository.h2.H2RepositoryFactory;
import br.brechosustentavel.repository.sqlite.SQLiteRepositoryFactory;
import br.brechosustentavel.configuracao.ConfiguracaoAdapter;
import br.brechosustentavel.repository.sqlite.SQLiteDefeitoPecaRepository;

/**
 *
 * @author kaila
 */


public abstract class RepositoryFactory {
    private static RepositoryFactory instancia;
    
    public static RepositoryFactory getInstancia(){
        if(instancia == null){
            ConfiguracaoAdapter configuracao = new ConfiguracaoAdapter();
            String sgbd = configuracao.getValor("SGBD");
            ConexaoFactory conexaoFactory = ConexaoFactory.getConexaoFactory(sgbd);
            
            if (sgbd.equalsIgnoreCase("sqlite")){
                instancia = new SQLiteRepositoryFactory(conexaoFactory);
            } else if (sgbd.equalsIgnoreCase("h2")){
                instancia = new H2RepositoryFactory(conexaoFactory);
            } else {
                throw new IllegalArgumentException("Banco de dados passado por parâmetro não existe!");
            }
        }
        return instancia;
    }
    
    public abstract IUsuarioRepository getUsuarioRepository();
    
    public abstract IComposicaoRepository getComposicaoRepository();
    
    public abstract ITransacaoRepository getTransacaoRepository();
    
    public abstract IDefeitoRepository getDefeitoRepository();
    
    public abstract IPecaRepository getPecaRepository();
    
    public abstract ITipoDePecaRepository getTipoDePecaRepository();
    
    public abstract ILinhaDoTempoRepository getLinhaDoTempoRepository();
    
    public abstract IVendedorRepository getVendedorRepository();
    
    public abstract ICompradorRepository getCompradorRepository();
    
    public abstract IAnuncioRepository getAnuncioRepository();
    
    public abstract IDefeitoPecaRepository getDefeitoPecaRepository();
    
    public abstract IInsigniaRepository getInsigniaRepository();
    
    public abstract IVendedorInsigniaRepository getVendedorInsigniaRepository();
    
    public abstract ICompradorInsigniaRepository getCompradorInsigniaRepository();
    
    public abstract IComposicaoPecaRepository getComposicaoPecaRepository();
    
    public abstract IOfertaRepository getOfertaRepository();
    
    public abstract IDenunciaRepository getDenunciaRepository();
    
    public abstract IAvaliacaoRepository getAvaliacaoRepository();
}

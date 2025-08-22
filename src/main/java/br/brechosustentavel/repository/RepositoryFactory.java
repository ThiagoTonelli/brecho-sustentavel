/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository;

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
}

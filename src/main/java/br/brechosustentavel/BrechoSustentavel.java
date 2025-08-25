/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.brechosustentavel;

import br.brechosustentavel.configuracao.ConfiguracaoAdapter;
import br.brechosustentavel.presenter.LoginPresenter;
import br.brechosustentavel.repository.repositoryFactory.ConexaoFactory;
import br.brechosustentavel.repository.repositoryFactory.IUsuarioRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import br.brechosustentavel.repository.inicializador.H2InicializaBancoDeDados;
import br.brechosustentavel.repository.sqlite.SQLiteConexaoFactory;
import br.brechosustentavel.repository.inicializador.SQLiteInicializaBancoDeDados;
import br.brechosustentavel.seeder.H2Seeder;
import br.brechosustentavel.seeder.SQLiteSeeder;
import br.brechosustentavel.service.AutenticacaoService;

import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.service.hash.BCryptAdapter;
import br.brechosustentavel.service.hash.HashService;
import br.brechosustentavel.service.verificador_telefone.LibPhoneNumberAdapter;
import br.brechosustentavel.service.verificador_telefone.VerificadorTelefoneService;

import br.brechosustentavel.view.LoginView;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.TimeZone;


/**
 *
 * @author thiag
 */
public class BrechoSustentavel {

    public static void main(String[] args) throws PropertyVetoException {
        /*try{
            ConexaoFactory conexaoFactory = new SQLiteConexaoFactory();
            try(Connection conexao = conexaoFactory.getConexao()){
                SQLiteInicializaBancoDeDados inicializador = new SQLiteInicializaBancoDeDados(conexao);
                HashService hashBCrypt = new BCryptAdapter();
                inicializador.inicializar();
                
                SQLiteSeeder seeder = new SQLiteSeeder(conexao, hashBCrypt);
                seeder.inserir();
            }
                System.out.println("BD inicializado com sucesso");
                
                RepositoryFactory fabrica = RepositoryFactory.getInstancia();
                TelaPrincipalView telaPrincipalView = new TelaPrincipalView();
                TelaPrincipalPresenter telaPresenter = new TelaPrincipalPresenter();   
        } catch(SQLException e){
            System.out.println("Falha ao inicializar BD. " +  e.getMessage());
        }*/
        
        try{

            TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
            HashService hash = new BCryptAdapter();
            VerificadorTelefoneService verificadorTelefone = new LibPhoneNumberAdapter();
            ConfiguracaoAdapter configuracao = new ConfiguracaoAdapter();
            String sgdb = configuracao.getValor("SGBD");
        
            ConexaoFactory conexaoFactory = ConexaoFactory.getConexaoFactory(sgdb);
            Connection conexao = conexaoFactory.getConexao();

            if ("sqlite".equalsIgnoreCase(sgdb)) {
                new SQLiteInicializaBancoDeDados(conexao).executarConfiguracaoInicial();
            } else if ("h2".equalsIgnoreCase(sgdb)) {
                new H2InicializaBancoDeDados(conexao).executarConfiguracaoInicial();
            }
             
            RepositoryFactory fabrica = RepositoryFactory.getInstancia();
            IUsuarioRepository usuarioRepository = fabrica.getUsuarioRepository();
            AutenticacaoService autenticacaoService = new AutenticacaoService(usuarioRepository, hash);
            SessaoUsuarioService sessao = SessaoUsuarioService.getInstancia();
            LoginView loginView = new LoginView();
            LoginPresenter loginPresenter = new LoginPresenter(fabrica, hash, verificadorTelefone, sessao, autenticacaoService);
                 
        } catch(Exception e){
            System.out.println("Falha: " +  e.getMessage());
        }  
        
    }
}

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
import br.brechosustentavel.repository.inicializador.SQLiteInicializaBancoDeDados;
import br.brechosustentavel.service.AutenticacaoService;

import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.service.hash.BCryptAdapter;
import br.brechosustentavel.service.hash.HashService;
import br.brechosustentavel.service.verificador_telefone.LibPhoneNumberAdapter;
import br.brechosustentavel.service.verificador_telefone.VerificadorTelefoneService;

import br.brechosustentavel.view.LoginView;
import java.beans.PropertyVetoException;
import java.sql.Connection;


/**
 *
 * @author thiag
 */
public class BrechoSustentavel {

    public static void main(String[] args) throws PropertyVetoException {        
        try{
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

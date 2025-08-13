/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.brechosustentavel;

import br.brechosustentavel.presenter.CadastroPresenter;
import br.brechosustentavel.presenter.LoginPresenter;
import br.brechosustentavel.presenter.TelaPrincipalPresenter;
import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IUsuarioRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.repository.sqlite.SQLiteConexaoFactory;
import br.brechosustentavel.repository.sqlite.SQLiteInicializaBancoDeDados;
import br.brechosustentavel.seeder.Seeder;
import br.brechosustentavel.service.AutenticacaoService;
import br.brechosustentavel.service.CadastroService;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.service.hash.BCryptAdapter;
import br.brechosustentavel.service.hash.HashService;
import br.brechosustentavel.service.verificador_telefone.LibPhoneNumberAdapter;
import br.brechosustentavel.service.verificador_telefone.VerificadorTelefoneService;
import br.brechosustentavel.view.LoginView;
import br.brechosustentavel.view.TelaPrincipalView;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;


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
                
                Seeder seeder = new Seeder(conexao, hashBCrypt);
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
            RepositoryFactory fabrica = RepositoryFactory.getInstancia();
            IUsuarioRepository usuarioRepository = fabrica.getUsuarioRepository();
            HashService hash = new BCryptAdapter();
            AutenticacaoService autenticacaoService = new AutenticacaoService(usuarioRepository, hash);
            SessaoUsuarioService sessao = SessaoUsuarioService.getInstancia();
            LoginView loginView = new LoginView();
            LoginPresenter loginPresenter = new LoginPresenter();
            loginPresenter.setAutenticacaoService(autenticacaoService);
 
            
            
            
            
        } catch(Exception e){
            System.out.println("Falha: " +  e.getMessage());
        }
        
        
        
        
        
        
        
        
        
        
    }
}

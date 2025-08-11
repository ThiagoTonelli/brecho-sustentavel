/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.brechosustentavel;

import br.brechosustentavel.presenter.TelaPrincipalPresenter;
import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IUsuarioRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import static br.brechosustentavel.repository.RepositoryFactory.getRepositoryFactory;
import br.brechosustentavel.repository.sqlite.SQLiteConexaoFactory;
import br.brechosustentavel.repository.sqlite.SQLiteInicializaBancoDeDados;
import br.brechosustentavel.seeder.Seeder;
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
        try{
            ConexaoFactory conexaoFactory = new SQLiteConexaoFactory();
            try(Connection conexao = conexaoFactory.getConexao()){
                SQLiteInicializaBancoDeDados inicializador = new SQLiteInicializaBancoDeDados(conexao);
                inicializador.inicializar();
                
                //Seeder seeder = new Seeder(conexao);
                //seeder.inserir();
            }
                System.out.println("BD inicializado com sucesso");
                
                RepositoryFactory fabrica = getRepositoryFactory();
                TelaPrincipalView telaPrincipalView = new TelaPrincipalView();
                TelaPrincipalPresenter telaPresenter = new TelaPrincipalPresenter(telaPrincipalView);   
        } catch(SQLException e){
            System.out.println("Falha ao inicializar BD. " +  e.getMessage());
        }
        
        
        
        
        
        
        
        
        
        
    }
}

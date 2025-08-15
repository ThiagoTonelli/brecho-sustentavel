/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.commandPrincipal;

import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.presenter.JanelaPrincipalPresenter.JanelaPrincipalPresenter;
import br.brechosustentavel.repository.IAnuncioRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.service.SessaoUsuarioService;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author thiag
 */
public class CarregarAnunciosVendedorCommand implements ICommandPrincipal{

    @Override
    public void executar(JanelaPrincipalPresenter presenter, SessaoUsuarioService usuarioAutenticado) {
        String[] colunas = {
            "ID Peça", "Tipo de peça", "Tamanho", 
            "Cor", "Massa", "GWP", "MCI", "Preço Final"
        };

        // Cria o modelo vazio com as colunas
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        IAnuncioRepository repository = fabrica.getAnuncioRepository();
        
        JTable tabela = presenter.getView().getjTable1();
        tabela.setModel(modelo);
        modelo.setRowCount(0);
        List<Anuncio> anuncios = repository.buscarAnuncios(usuarioAutenticado.getUsuarioAutenticado().getId());

        for(Anuncio a : anuncios){
            modelo.addRow(new Object[]{
                a.getPeca().getId_c(),
                a.getPeca().getTipoDePeca(),
                a.getPeca().getTamanho(),
                a.getPeca().getCor(),
                a.getPeca().getMassaEstimada(),
                a.getGwp_avoided(),
                a.getMci(),
                a.getPeca().getPrecoFinal()  
            });
        }
    }
    
    
}

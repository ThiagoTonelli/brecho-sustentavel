/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandManterAnuncio;

import br.brechosustentavel.presenter.manterAnuncioPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.repository.repositoryFactory.ITipoDePecaRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import java.util.List;
import javax.swing.JComboBox;
import br.brechosustentavel.view.IJanelaManterAnuncioView;
/**
 *
 * @author thiag
 */
public class CarregarTiposDePecaCommand implements ICommandVendedor{

    @Override
    public Object executar(ManterAnuncioPresenter presenter) {
        IJanelaManterAnuncioView view = presenter.getView();
        JComboBox jcomboTipoPeca = view.getSelectTipoDePeca();
        jcomboTipoPeca.removeAllItems();
        
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        ITipoDePecaRepository repository = fabrica.getTipoDePecaRepository();
        List<String> tipoDePeca = repository.buscarTiposDePeca();
       
        for(String peca : tipoDePeca){
            jcomboTipoPeca.addItem(peca);
        }
        
        jcomboTipoPeca.revalidate();
        jcomboTipoPeca.repaint();
        return null;
    }
    
}

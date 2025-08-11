/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command;

import br.brechosustentavel.presenter.ManterAnuncioPresenter;
import br.brechosustentavel.repository.ITipoDePecaRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import static br.brechosustentavel.repository.RepositoryFactory.getRepositoryFactory;
import br.brechosustentavel.view.IJanelaInclusaoAnuncioView;
import java.util.List;
import javax.swing.JComboBox;
/**
 *
 * @author thiag
 */
public class CarregarTiposDePecaCommand implements ICommand{

    @Override
    public void executar(ManterAnuncioPresenter presenter) {
        IJanelaInclusaoAnuncioView view = presenter.getView();
        JComboBox jcomboTipoPeca = view.getSelectTipoDePeca();
        jcomboTipoPeca.removeAll();
        
        RepositoryFactory fabrica = getRepositoryFactory();
        ITipoDePecaRepository repository = fabrica.getTipoDePecaRepository();
        List<String> tipoDePeca = repository.buscarTiposDePeca();
       
        for(String peca : tipoDePeca){
            jcomboTipoPeca.addItem(peca);
        }
        
        jcomboTipoPeca.revalidate();
        jcomboTipoPeca.repaint();
    }
    
}

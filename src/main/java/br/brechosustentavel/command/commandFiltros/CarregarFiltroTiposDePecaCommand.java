/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandFiltros;

import br.brechosustentavel.presenter.janelaPrincipalPresenter.JanelaPrincipalPresenter;
import br.brechosustentavel.repository.ITipoDePecaRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.view.JanelaPrincipalView;
import java.util.List;
import javax.swing.JComboBox;

/**
 *
 * @author kaila
 */
public class CarregarFiltroTiposDePecaCommand implements ICommandFiltros{

    @Override
    public void executar(JanelaPrincipalPresenter presenter) {
        JanelaPrincipalView view = presenter.getView();
        JComboBox cBoxTipoPeca = view.getcBoxValorFiltro();
        cBoxTipoPeca.removeAllItems();
               
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        ITipoDePecaRepository repository = fabrica.getTipoDePecaRepository();
        List<String> tipoDePeca = repository.buscarTiposDePeca();
       
        for(String peca : tipoDePeca){
            cBoxTipoPeca.addItem(peca);
        }
        
        cBoxTipoPeca.revalidate();
        cBoxTipoPeca.repaint();
    }
}

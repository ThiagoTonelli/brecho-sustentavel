/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandFiltros;

import br.brechosustentavel.presenter.janelaPrincipalPresenter.JanelaPrincipalPresenter;
import br.brechosustentavel.repository.repositoryFactory.IDefeitoRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import br.brechosustentavel.view.JanelaPrincipalView;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;

/**
 *
 * @author kaila
 */
public class CarregarFiltroDefeitosCommand implements ICommandFiltros {
    @Override
    public void executar(JanelaPrincipalPresenter presenter) {
        JanelaPrincipalView view = presenter.getView();
        JComboBox cBoxDefeito = view.getcBoxValorFiltro();
        cBoxDefeito.removeAllItems();
               
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        IDefeitoRepository defeitoRepository = fabrica.getDefeitoRepository();
        List<Map<String, Object>> defeitos = defeitoRepository.buscarTodosParaManutencao();
       
        for(Map<String, Object> defeito : defeitos){
            cBoxDefeito.addItem((String) defeito.get("nome"));
        }
        
        cBoxDefeito.revalidate();
        cBoxDefeito.repaint();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command;
import br.brechosustentavel.presenter.ManterAnuncioPresenter;
import br.brechosustentavel.repository.IDefeitoRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import static br.brechosustentavel.repository.RepositoryFactory.getRepositoryFactory;
import br.brechosustentavel.view.IJanelaInclusaoAnuncioView;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 *
 * @author thiag
 */
public class CarregarDefeitosPorTipoCommand implements ICommand{

    @Override
    public void executar(ManterAnuncioPresenter presenter) {
        IJanelaInclusaoAnuncioView view = presenter.getView();
        String tipoPeca = (String ) view.getSelectTipoDePeca().getSelectedItem();
        JPanel painelDefeitos = view.getPainelScrollDefeitos();
        painelDefeitos.removeAll();
        
        if (tipoPeca == null || tipoPeca.trim().isEmpty()){
            view.getPainelScrollDefeitos().revalidate();
            view.getPainelScrollDefeitos().repaint();
        }
        
        RepositoryFactory fabrica = getRepositoryFactory();
        IDefeitoRepository repository = fabrica.getDefeitoRepository();
        List<String> defeitos = repository.buscarDefeitos(tipoPeca);
        
        System.out.println("Número de defeitos encontrados para '" + tipoPeca + "': " + defeitos.size());

        painelDefeitos.setLayout(new BoxLayout(painelDefeitos, BoxLayout.Y_AXIS));
        for(String defeito : defeitos){
            JCheckBox checkBox = new JCheckBox(defeito);
            view.getPainelScrollDefeitos().add(checkBox);
        }
        
        view.getPainelScrollDefeitos().revalidate();
        view.getPainelScrollDefeitos().repaint();
    }  
}

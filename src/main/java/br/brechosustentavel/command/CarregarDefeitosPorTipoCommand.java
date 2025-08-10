/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command;
import br.brechosustentavel.repository.IDefeitoRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import static br.brechosustentavel.repository.RepositoryFactory.getRepositoryFactory;
import java.util.List;
import javax.swing.JCheckBox;

/**
 *
 * @author thiag
 */
/*public class CarregarDefeitosPorTipoCommand implements IAnuncioCommand{

    @Override
    public void executar(AnuncioPresenter anuncioPresenter) {
        RepositoryFactory fabrica = getRepositoryFactory();
        IDefeitoRepository repository = fabrica.getDefeitoRepository();
        String tipoPeca = (String ) anuncioPresenter.getView().getSelectTipoDePeca().getSelectedItem();
        List<String> defeitos = repository.buscarDefeitos(tipoPeca);
        int contador = 0;
        for(String defeito : defeitos){
            JCheckBox checkBox = new JCheckBox(defeito + contador++);
            anuncioPresenter.getView().getPainelScrollDefeitos().add(checkBox);
        }
        new NovoAnuncioCommand().executar(anuncioPresenter);
        new NovoAnuncioCommand().executar(anuncioPresenter);
    }
    
}
*/
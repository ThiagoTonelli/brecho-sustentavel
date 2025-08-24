/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandManterAnuncio;
import br.brechosustentavel.presenter.manterAnuncioPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.repository.IDefeitoRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import br.brechosustentavel.view.IJanelaManterAnuncioView;

/**
 *
 * @author thiag
 */
public class CarregarDefeitosPorTipoCommand implements ICommandVendedor{

    @Override
    public Object executar(ManterAnuncioPresenter presenter) {
        IJanelaManterAnuncioView view = presenter.getView();
        String tipoPeca = (String ) view.getSelectTipoDePeca().getSelectedItem();
        JPanel painelDefeitos = view.getPainelScrollDefeitos();
        painelDefeitos.removeAll();
        
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        IDefeitoRepository repository = fabrica.getDefeitoRepository();
        Map<String, Double> defeitos = repository.buscarDefeitosPorTipo(tipoPeca);
        
        System.out.println("Número de defeitos encontrados para '" + tipoPeca + "': " + defeitos.size());

        painelDefeitos.setLayout(new BoxLayout(painelDefeitos, BoxLayout.Y_AXIS));
        
        defeitos.forEach((chave, valor) -> {
            JCheckBox checkBox = new JCheckBox(chave);
            checkBox.putClientProperty("desconto", valor);
            view.getPainelScrollDefeitos().add(checkBox);
        });
        
        view.getPainelScrollDefeitos().revalidate();
        view.getPainelScrollDefeitos().repaint();
        return null;
    }  
}

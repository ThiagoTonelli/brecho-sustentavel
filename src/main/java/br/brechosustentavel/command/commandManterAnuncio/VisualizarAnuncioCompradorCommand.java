/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandManterAnuncio;

import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.presenter.manterAnuncioPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.repository.repositoryFactory.IAnuncioRepository;
import br.brechosustentavel.repository.repositoryFactory.IComposicaoPecaRepository;
import br.brechosustentavel.repository.repositoryFactory.IDefeitoPecaRepository;
import br.brechosustentavel.repository.repositoryFactory.ITipoDePecaRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import br.brechosustentavel.view.IJanelaManterAnuncioView;
import java.awt.Component;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSpinner;

/**
 *
 * @author kaila
 */
public class VisualizarAnuncioCompradorCommand implements ICommandVendedor{

    @Override
    public Object executar(ManterAnuncioPresenter presenter) {
        IJanelaManterAnuncioView view = presenter.getView();
        String id_c = presenter.getIdPeca();

        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        IDefeitoPecaRepository defeitoPecaRepository = fabrica.getDefeitoPecaRepository();
        ITipoDePecaRepository tipoPecaRepository = fabrica.getTipoDePecaRepository();
        IComposicaoPecaRepository composicaoPecaRepository = fabrica.getComposicaoPecaRepository();
        IAnuncioRepository anuncioRepository = fabrica.getAnuncioRepository();
        
        Optional<Anuncio> optAnuncio = anuncioRepository.buscarPorIdPeca(id_c);
        if (optAnuncio.isPresent()) {
            Anuncio anuncio = optAnuncio.get();

            view.getTxtId_c().setText(id_c);
            view.getTxtSubcategoria().setText(anuncio.getPeca().getSubcategoria());
            view.getTxtTamanho().setText(anuncio.getPeca().getTamanho());
            view.getTxtCor().setText(anuncio.getPeca().getCor());
            view.getTxtMassa().setText(String.valueOf(anuncio.getPeca().getMassaEstimada()));
            view.getTxtEstadoConservacao().setText(anuncio.getPeca().getEstadoDeConservacao());
            view.getTxtPrecoBase().setText(String.format("%.2f", anuncio.getValorFinal()));
            view.getSelectTipoDePeca().setSelectedItem(tipoPecaRepository.buscarNomeTipo(anuncio.getPeca().getIdTipoDePeca()));

            new CarregarDefeitosPorTipoCommand().executar(presenter);

            Map<String, Double> defeitosDaPeca = defeitoPecaRepository.buscarNomeDefeitosDaPeca(id_c);
            if (defeitosDaPeca != null) {
                for (Component comp : view.getPainelScrollDefeitos().getComponents()) {
                    if (comp instanceof JCheckBox) {
                        JCheckBox checkBox = (JCheckBox) comp;
                        if (defeitosDaPeca.containsKey(checkBox.getText())) {
                            checkBox.setSelected(true);
                        }
                    }
                }
            }

            Map<String, Integer> composicaoDaPeca = composicaoPecaRepository.buscarComposicaoPorPeca(id_c);
            List<JComboBox<String>> comboBoxes = List.of(
                view.getSelectComposicao(), 
                view.getSelectComposicao1(), 
                view.getSelectComposicao2()
            );
            List<JSpinner> spinners = List.of(
                view.getSpinnerComposicao(), 
                view.getSpinnerComposicao1(), 
                view.getSpinnerComposicao2()
            );

            int index = 0;
            if (composicaoDaPeca != null) {
                for (Map.Entry<String, Integer> entry : composicaoDaPeca.entrySet()) {
                    if (index < comboBoxes.size()) {
                        comboBoxes.get(index).setSelectedItem(entry.getKey());
                        spinners.get(index).setValue(entry.getValue());
                        index++;
                    }
                }
            }
            return anuncio.getPeca();
        } else {
            throw new RuntimeException("Não foi possível encontrar um anúncio com o ID da peça: " + id_c);
        }
    }
}

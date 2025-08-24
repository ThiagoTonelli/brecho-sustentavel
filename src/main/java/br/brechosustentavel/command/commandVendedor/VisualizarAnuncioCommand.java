/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandVendedor;

import br.brechosustentavel.model.Peca;
import br.brechosustentavel.presenter.manterAnuncioPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.repository.IComposicaoPecaRepository;
import br.brechosustentavel.repository.IDefeitoPecaRepository;
import br.brechosustentavel.repository.IPecaRepository;
import br.brechosustentavel.repository.ITipoDePecaRepository;
import br.brechosustentavel.repository.RepositoryFactory;
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
 * @author thiag
 */
public class VisualizarAnuncioCommand implements ICommandVendedor{

    @Override
    public Object executar(ManterAnuncioPresenter presenter) {
        IJanelaManterAnuncioView view = presenter.getView();
        // Corrigido para usar o método correto do presenter
        String id_c = presenter.getIdPeca();

        // Inicializa todos os repositórios necessários através da Factory
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        IPecaRepository pecaRepository = fabrica.getPecaRepository();
        IDefeitoPecaRepository defeitoPecaRepository = fabrica.getDefeitoPecaRepository();
        ITipoDePecaRepository tipoPecaRepository = fabrica.getTipoDePecaRepository();
        // Adicionado o repositório para buscar a composição da peça
        IComposicaoPecaRepository composicaoPecaRepository = fabrica.getComposicaoPecaRepository();

        Optional<Peca> pecaOptional = pecaRepository.consultar(id_c);

        if (pecaOptional.isPresent()) {
            Peca peca = pecaOptional.get();

            // Preenche os campos de texto com os dados da peça
            view.getTxtId_c().setText(id_c);
            view.getTxtSubcategoria().setText(peca.getSubcategoria());
            view.getTxtTamanho().setText(peca.getTamanho());
            view.getTxtCor().setText(peca.getCor());
            view.getTxtMassa().setText(String.valueOf(peca.getMassaEstimada()));
            view.getTxtEstadoConservacao().setText(peca.getEstadoDeConservacao());
            view.getTxtPrecoBase().setText(String.format("%.2f", peca.getPrecoBase()));
            view.getSelectTipoDePeca().setSelectedItem(tipoPecaRepository.buscarNomeTipo(peca.getIdTipoDePeca()));

            // Carrega a lista de defeitos possíveis para o tipo de peça
            new CarregarDefeitosPorTipoCommand().executar(presenter);

            // Marca os checkboxes dos defeitos que a peça realmente possui
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

            // 2. Cria uma lista de JComboBoxes e JSpinners para facilitar a iteração
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

            return peca;
        } else {
            throw new RuntimeException("Não foi possível encontrar um anúncio com o ID da peça: " + id_c);
        }
    }
}
    


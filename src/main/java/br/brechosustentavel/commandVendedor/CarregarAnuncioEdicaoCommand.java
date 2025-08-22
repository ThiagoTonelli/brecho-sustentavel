/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.commandVendedor;

import br.brechosustentavel.model.Peca;
import br.brechosustentavel.presenter.ManterAnuncioPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.repository.IDefeitoPecaRepository;
import br.brechosustentavel.repository.IPecaRepository;
import br.brechosustentavel.repository.ITipoDePecaRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.view.IJanelaManterAnuncioView;
import java.awt.Component;
import java.util.Map;
import java.util.Optional;
import javax.swing.JCheckBox;

/**
 *
 * @author thiag
 */
public class CarregarAnuncioEdicaoCommand implements ICommandVendedor{

    @Override
    public Object executar(ManterAnuncioPresenter presenter) {
        IJanelaManterAnuncioView view = presenter.getView();
        String id_c = presenter.getIdAnuncio();


        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        IPecaRepository pecaRepository = fabrica.getPecaRepository();
        IDefeitoPecaRepository defeitoPecaRepository = fabrica.getDefeitoPecaRepository();
        ITipoDePecaRepository tipoPecaRepository = fabrica.getTipoDePecaRepository();
        Optional<Peca> pecaOptional = pecaRepository.consultar(id_c);

        if (pecaOptional.isPresent()) {
            Peca peca = pecaOptional.get();
            view.getTxtId_c().setText(id_c);
            view.getTxtSubcategoria().setText(peca.getSubcategoria());
            view.getTxtTamanho().setText(peca.getTamanho());
            view.getTxtCor().setText(peca.getCor());
            view.getTxtMassa().setText(String.valueOf(peca.getMassaEstimada()));
            view.getTxtEstadoConservacao().setText(peca.getEstadoDeConservacao());
            view.getTxtPrecoBase().setText(String.format("%.2f", peca.getPrecoBase()));
            view.getSelectTipoDePeca().setSelectedItem(tipoPecaRepository.buscarNomeTipo(peca.getIdTipoDePeca()));
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
            
            // Lógica para preencher a composição (materiais)
            // Esta parte pode precisar de um método adicional para buscar a composição da peça
            // Ex: Map<String, Double> composicao = pecaRepository.buscarComposicao(id_c);
            // E então preencher os JComboBox e JSpinner
            // view.getSelectComposicao().setSelectedItem(...);
            // view.getSpinnerComposicao().setValue(...);


            return peca; // Retorna o objeto Peca carregado
        } else {
            throw new RuntimeException("Não foi possível encontrar um anúncio com o ID da peça: " + id_c);
        }
    }
}
    


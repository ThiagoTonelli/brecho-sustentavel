/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandManterAnuncio;

import br.brechosustentavel.model.Peca;
import br.brechosustentavel.presenter.manterAnuncioPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.repository.repositoryFactory.IComposicaoPecaRepository;
import br.brechosustentavel.repository.repositoryFactory.IDefeitoPecaRepository;
import br.brechosustentavel.repository.repositoryFactory.IPecaRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import java.util.Map;
import java.util.Optional;
import javax.swing.JCheckBox;

/**
 *
 * @author kaila
 */
public class CarregarDadosPecaExistenteCommand implements ICommandVendedor{

    @Override
    public Object executar(ManterAnuncioPresenter presenter) {
        String idPeca = presenter.getView().getTxtId_c().getText().trim();
        
        if(idPeca.isEmpty()){
            return null;
        }
        
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        IPecaRepository pecaRepository = fabrica.getPecaRepository();
        IDefeitoPecaRepository defeitoPecaRepository = fabrica.getDefeitoPecaRepository();
        IComposicaoPecaRepository composicaoPecaRepository = fabrica.getComposicaoPecaRepository();
        Optional<Peca> optPeca = pecaRepository.consultar(idPeca);

        if (optPeca.isPresent()) {
            Peca peca = optPeca.get();
           
            presenter.getView().getTxtId_c().setEnabled(false);
            presenter.getView().getBtnGerarId().setEnabled(false);
            presenter.getView().getTxtSubcategoria().setText(peca.getSubcategoria());
            presenter.getView().getTxtCor().setText(peca.getCor());
            presenter.getView().getTxtTamanho().setText(peca.getTamanho());
            presenter.getView().getTxtEstadoConservacao().setText(peca.getEstadoDeConservacao());
            presenter.getView().getTxtMassa().setText(String.valueOf(peca.getMassaEstimada()));
            presenter.getView().getTxtPrecoBase().setText(String.valueOf(peca.getPrecoBase()));
            presenter.getView().getSelectTipoDePeca().setSelectedItem(peca.getTipoDePeca());
            
            Map<String, Integer> composicoes = composicaoPecaRepository.buscarComposicaoPorPeca(idPeca);
            Map<String, Double> defeitos = defeitoPecaRepository.buscarNomeDefeitosDaPeca(idPeca);
                        
            int i = 0;
            for (Map.Entry<String, Integer> entry : composicoes.entrySet()) {
                String nomeMaterial = entry.getKey();
                int quantidade = entry.getValue();
                switch (i) {
                    case 0:
                        presenter.getView().getSelectComposicao().setSelectedItem(nomeMaterial);
                        presenter.getView().getSpinnerComposicao().setValue(quantidade);
                        break;
                    case 1:
                        presenter.getView().getSelectComposicao1().setSelectedItem(nomeMaterial);
                        presenter.getView().getSpinnerComposicao1().setValue(quantidade);
                        break;
                    case 2:
                        presenter.getView().getSelectComposicao2().setSelectedItem(nomeMaterial);
                        presenter.getView().getSpinnerComposicao2().setValue(quantidade);
                        break;
                }
                i++;
            }

            for (java.awt.Component comp : presenter.getView().getPainelScrollDefeitos().getComponents()) {
                if (comp instanceof JCheckBox) {
                    JCheckBox checkBox = (JCheckBox) comp;
                    if (defeitos.containsKey(checkBox.getText())) {
                        checkBox.setSelected(true);
                    }
                }
            }
        }     
        return null;
    }   
}

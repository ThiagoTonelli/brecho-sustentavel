/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command;
import br.brechosustentavel.model.Peca;
import br.brechosustentavel.presenter.ManterAnuncioPresenter;
import br.brechosustentavel.presenter.TelaPrincipalPresenter;
import java.util.Optional;


/**
 *
 * @author thiag
 */
public class NovoAnuncioCommand implements ICommand{

    @Override
    public void executar(ManterAnuncioPresenter presenter) {
        try {
            String id_c = presenter.getView().getTxtId_c().getText();
            String tipoPeca = (String ) presenter.getView().getSelectTipoDePeca().getSelectedItem();
            String subcategoria = (String ) presenter.getView().getSelectSubcategoria().getSelectedItem();
            double tamanho = Double.parseDouble(presenter.getView().getTxtTamanho().getText());
            String cor = presenter.getView().getTxtCor().getText();
            String composicao = (String ) presenter.getView().getSelectComposicao().getSelectedItem();
            double massa = Double.parseDouble(presenter.getView().getTxtMassa().getText());
            String estadoConservacao = presenter.getView().getTxtEstadoConservacao().getText();
            String defeitos = (String ) presenter.getView().getSelectComposicao().getSelectedItem(); /// ANALISAR
            double precoBase = Double.parseDouble(presenter.getView().getTxtPrecoBase().getText());
            //Item item = new Peca(tipoPeca, subcategoria, tamanho, cor, composicao, massa, estadoConservacao, defeitos, precoBase);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }


}

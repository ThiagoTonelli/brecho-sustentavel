/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandAvaliacao;

import br.brechosustentavel.model.Avaliacao;
import br.brechosustentavel.presenter.JanelaAvaliacaoPresenter;
import br.brechosustentavel.service.avaliacao.AvaliacaoService;

/**
 *
 * @author thiag
 */
public class SalvarAvaliacaoCommand implements ICommandAvaliacao {

    @Override
    public void executar(JanelaAvaliacaoPresenter presenter) {
        String texto = presenter.getView().getTxtDescricao().getText();
        
        Avaliacao novaAvaliacao = new Avaliacao(
                presenter.getTransacao(),
                presenter.getAutor(),
                texto
        );
        
        new AvaliacaoService().salvarAvaliacao(novaAvaliacao);
    }

}


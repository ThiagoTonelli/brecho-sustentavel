/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandManterTipoPeca;

import br.brechosustentavel.presenter.manterTipoPecaPresenter.ManterTipoPecaPresenter;
import br.brechosustentavel.repository.repositoryFactory.ITipoDePecaRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;

/**
 *
 * @author thiag
 */
public class SalvarTipoPecaCommand implements ICommandTipoPeca {
    @Override
    public void executar(ManterTipoPecaPresenter presenter) {
        Integer id = presenter.getIdTipoPecaSelecionado();
        String nome = presenter.getView().getTxtNomeTipoPeca().getText();

        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do tipo de peça não pode ser vazio.");
        }

        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        ITipoDePecaRepository tipoPecaRepo = fabrica.getTipoDePecaRepository();
        tipoPecaRepo.salvar(id, nome);
    }
}

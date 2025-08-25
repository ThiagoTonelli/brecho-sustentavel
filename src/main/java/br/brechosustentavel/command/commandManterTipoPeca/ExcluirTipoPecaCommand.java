/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandManterTipoPeca;

import br.brechosustentavel.presenter.manterTipoPecaPresenter.ManterTipoPecaPresenter;
import br.brechosustentavel.repository.repositoryFactory.ITipoDePecaRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author thiag
 */
public class ExcluirTipoPecaCommand implements ICommandTipoPeca {
    @Override
    public void executar(ManterTipoPecaPresenter presenter) {
        Integer idParaExcluir = presenter.getIdTipoPecaSelecionado();
        if (idParaExcluir == null) {
            throw new IllegalStateException("Nenhum tipo de peça selecionado para exclusão.");
        }

        int confirmacao = JOptionPane.showConfirmDialog(
            presenter.getView(),
            "Tem certeza que deseja excluir o tipo de peça selecionado?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            RepositoryFactory fabrica = RepositoryFactory.getInstancia();
            ITipoDePecaRepository tipoPecaRepo = fabrica.getTipoDePecaRepository();
            tipoPecaRepo.excluir(idParaExcluir);
            JOptionPane.showMessageDialog(presenter.getView(), "Tipo de peça excluído com sucesso!");
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandManterDefeito;

import br.brechosustentavel.presenter.ManterDefeitoPresenter.ManterDefeitoPresenter;
import br.brechosustentavel.repository.IDefeitoRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author thiag
 */
public class ExcluirDefeitoCommand implements ICommandDefeito {
    @Override
    public void executar(ManterDefeitoPresenter presenter) {
        Integer idParaExcluir = presenter.getIdDefeitoSelecionado();

        if (idParaExcluir == null) {
            throw new IllegalStateException("Nenhum defeito selecionado para exclusão.");
        }

        int confirmacao = JOptionPane.showConfirmDialog(
            presenter.getView(),
            "Tem certeza que deseja excluir o defeito selecionado?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            RepositoryFactory fabrica = RepositoryFactory.getInstancia();
            IDefeitoRepository defeitoRepo = fabrica.getDefeitoRepository();
            defeitoRepo.excluir(idParaExcluir);
            JOptionPane.showMessageDialog(presenter.getView(), "Defeito excluído com sucesso!");
        }
    }
}

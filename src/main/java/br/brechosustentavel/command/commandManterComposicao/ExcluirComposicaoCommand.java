/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandManterComposicao;

import br.brechosustentavel.presenter.manterComposicaoPresenter.ManterComposicaoPresenter;
import br.brechosustentavel.repository.IComposicaoRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author thiag
 */
public class ExcluirComposicaoCommand implements ICommandComposicao {
    @Override
    public void executar(ManterComposicaoPresenter presenter) {
        Integer idParaExcluir = presenter.getIdComposicaoSelecionada();
        if (idParaExcluir == null) {
            throw new IllegalStateException("Nenhuma composição selecionada para exclusão.");
        }

        int confirmacao = JOptionPane.showConfirmDialog(
            presenter.getView(),
            "Tem certeza que deseja excluir o material selecionado?",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            RepositoryFactory fabrica = RepositoryFactory.getInstancia();
            IComposicaoRepository composicaoRepo = fabrica.getComposicaoRepository();
            composicaoRepo.excluir(idParaExcluir);
            JOptionPane.showMessageDialog(presenter.getView(), "Material excluído com sucesso!");
        }
    }
}

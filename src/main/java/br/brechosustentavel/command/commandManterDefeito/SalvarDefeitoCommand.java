/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandManterDefeito;

import br.brechosustentavel.presenter.ManterDefeitoPresenter.ManterDefeitoPresenter;
import br.brechosustentavel.repository.repositoryFactory.IDefeitoRepository;
import br.brechosustentavel.repository.repositoryFactory.ITipoDePecaRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;

/**
 *
 * @author thiag
 */
public class SalvarDefeitoCommand implements ICommandDefeito {
    @Override
    public void executar(ManterDefeitoPresenter presenter) {
        Integer id = presenter.getIdDefeitoSelecionado();
        String nome = presenter.getView().getTxtNomeDefeito().getText();
        
        String abatimentoStr = presenter.getView().getTxtAbatimento().getText().replace(",", ".");
        
        String tipoPecaNome = (String) presenter.getView().getSelectTipoPeca().getSelectedItem();

        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do defeito não pode ser vazio.");
        }
        if (tipoPecaNome == null) {
            throw new IllegalArgumentException("Selecione um tipo de peça.");
        }
        
        double abatimentoPercentual;
        try {
            abatimentoPercentual = Double.parseDouble(abatimentoStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O valor do abatimento deve ser um número válido.");
        }
        
        if (abatimentoPercentual < 0 || abatimentoPercentual > 100) {
            throw new IllegalArgumentException("O valor do abatimento deve estar entre 0 e 100.");
        }
        
        double descontoDecimal = abatimentoPercentual / 100.0;
        
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        ITipoDePecaRepository tipoPecaRepo = fabrica.getTipoDePecaRepository();
        int idTipoPeca = tipoPecaRepo.buscarIdTipo(tipoPecaNome);
        IDefeitoRepository defeitoRepo = fabrica.getDefeitoRepository();
        defeitoRepo.salvar(id, nome, descontoDecimal, idTipoPeca);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandManterComposicao;

import br.brechosustentavel.presenter.manterComposicaoPresenter.ManterComposicaoPresenter;
import br.brechosustentavel.repository.IComposicaoRepository;
import br.brechosustentavel.repository.RepositoryFactory;

/**
 *
 * @author thiag
 */
public class SalvarComposicaoCommand implements ICommandComposicao {
    @Override
    public void executar(ManterComposicaoPresenter presenter) {
        Integer id = presenter.getIdComposicaoSelecionada();
        String nome = presenter.getView().getTxtNomeMaterial().getText();
        String fatorEmissaoStr = presenter.getView().getTxtFatorEmissao().getText().replace(",", ".");

        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do material não pode ser vazio.");
        }
        
        double fatorEmissao;
        try {
            fatorEmissao = Double.parseDouble(fatorEmissaoStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O fator de emissão deve ser um número válido.");
        }

        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        IComposicaoRepository composicaoRepo = fabrica.getComposicaoRepository();
        composicaoRepo.salvar(id, nome, fatorEmissao);
    }
}

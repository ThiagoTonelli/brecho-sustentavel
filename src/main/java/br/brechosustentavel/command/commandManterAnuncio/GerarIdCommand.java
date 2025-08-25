/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandManterAnuncio;

import br.brechosustentavel.presenter.manterAnuncioPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.repository.repositoryFactory.IPecaRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import br.brechosustentavel.service.gerador_id_c.GeradorIdService;

/**
 *
 * @author thiag
 */
public class GerarIdCommand implements ICommandVendedor{

    @Override
    public Object executar(ManterAnuncioPresenter presenter) {
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        IPecaRepository repository = fabrica.getPecaRepository();
        GeradorIdService gerador = new GeradorIdService();
        String id = gerador.Gerar();
        while(repository.ExisteId_c(id)){
            id = gerador.Gerar();
        }
        presenter.getView().getTxtId_c().setText(id);
        return null;
    }
    
}

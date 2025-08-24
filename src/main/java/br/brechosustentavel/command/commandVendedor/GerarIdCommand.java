/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandVendedor;

import br.brechosustentavel.presenter.manterAnuncioPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.repository.IPecaRepository;
import br.brechosustentavel.repository.RepositoryFactory;
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
        //chama o service para id_c
        GeradorIdService gerador = new GeradorIdService();
        String id = gerador.Gerar();
        //verifica se existe no bd se sim cria outro id_c
        while(repository.ExisteId_c(id)){
            id = gerador.Gerar();
        }
        presenter.getView().getTxtId_c().setText(id);
        return null;
    }
    
}

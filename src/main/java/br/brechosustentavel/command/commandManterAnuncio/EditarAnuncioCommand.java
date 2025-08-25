/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandManterAnuncio;

import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.model.Peca;
import br.brechosustentavel.presenter.manterAnuncioPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import br.brechosustentavel.service.anuncio.AnuncioFormMapper;
import br.brechosustentavel.service.anuncio.AnuncioService;


/**
 *
 * @author thiag
 */
public class EditarAnuncioCommand implements ICommandVendedor {

    @Override
    public Object executar(ManterAnuncioPresenter presenter) {
        try {
            Peca pecaEditada = AnuncioFormMapper.extrairDadosDaView(presenter.getView());

            RepositoryFactory fabrica = RepositoryFactory.getInstancia();
            AnuncioService anuncioService = new AnuncioService(fabrica);

            Anuncio anuncioAtualizado = anuncioService.editarAnuncio(
                pecaEditada,
                presenter.getUsuarioAutenticado().getUsuarioAutenticado()
            );

            return anuncioAtualizado;

        } catch (Exception ex) {
            throw new RuntimeException("Ocorreu um erro ao atualizar o anúncio: " + ex.getMessage(), ex);
        }
    }
}

package br.brechosustentavel.command.commandVendedor;

import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.model.Peca;
import br.brechosustentavel.presenter.manterAnuncioPresenter.ManterAnuncioPresenter;

import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.service.anuncio.AnuncioFormMapper;
import br.brechosustentavel.service.anuncio.AnuncioService;


/**
 *
 * @author thiag
 */
public class NovoAnuncioCommand implements ICommandVendedor {
    
    @Override
    public Object executar(ManterAnuncioPresenter presenter) {
        try {
            Peca peca = AnuncioFormMapper.extrairDadosDaView(presenter.getView());

            RepositoryFactory fabrica = RepositoryFactory.getInstancia();

            AnuncioService anuncioService = new AnuncioService(fabrica);

            Anuncio anuncio = anuncioService.criarOuAtualizarAnuncio(
                peca, 
                presenter.getUsuarioAutenticado().getUsuarioAutenticado()
            );
            
            return anuncio;

        } catch (NumberFormatException e) {
            throw new RuntimeException("Erro de formato numérico. Verifique se os campos de preço e massa estão preenchidos corretamente.", e);
        } catch (Exception ex) {
            throw new RuntimeException("Ocorreu um erro ao processar a criação do anúncio: " + ex.getMessage(), ex);
        }
    }
}
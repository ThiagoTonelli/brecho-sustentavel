/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.brechosustentavel;

import br.brechosustentavel.presenter.TelaPrincipalPresenter;
import br.brechosustentavel.repository.IUsuarioRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import static br.brechosustentavel.repository.RepositoryFactory.getRepositoryFactory;
import br.brechosustentavel.view.TelaPrincipalView;
import java.beans.PropertyVetoException;


/**
 *
 * @author thiag
 */
public class BrechoSustentavel {

    public static void main(String[] args) throws PropertyVetoException {
        RepositoryFactory fabrica = getRepositoryFactory();
        IUsuarioRepository usuarioRepository = fabrica.getUsuarioRepository();
        TelaPrincipalView telaPrincipalView = new TelaPrincipalView();
        TelaPrincipalPresenter telaPresenter = new TelaPrincipalPresenter(usuarioRepository, telaPrincipalView);
    }
}

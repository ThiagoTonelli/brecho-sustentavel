/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.commandVendedor;

import br.brechosustentavel.presenter.ManterAnuncioPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.service.anuncio.AnuncioService;
import javax.swing.JOptionPane;

/**
 *
 * @author thiag
 */
public class ExcluirAnuncioCommand implements ICommandVendedor {

    @Override
    public Object executar(ManterAnuncioPresenter presenter) {
        int confirmacao = JOptionPane.showConfirmDialog(
            presenter.getView(), 
            "Tem a certeza de que deseja excluir este anúncio?\nEsta ação não pode ser desfeita.",
            "Confirmar Exclusão",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                String idPeca = presenter.getView().getTxtId_c().getText();

                RepositoryFactory fabrica = RepositoryFactory.getInstancia();
                AnuncioService anuncioService = new AnuncioService(fabrica);

                anuncioService.excluirAnuncio(idPeca);
                
                return true; 

            } catch (Exception ex) {
                throw new RuntimeException("Ocorreu um erro ao excluir o anúncio: " + ex.getMessage(), ex);
            }
        }
        
        return false; 
    }
}

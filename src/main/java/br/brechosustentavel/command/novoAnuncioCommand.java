/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command;

import com.thiago.brechosustentavel.presenter.AnuncioPresenter;
import java.util.Optional;

/**
 *
 * @author thiag
 */
public class novoAnuncioCommand implements IAnuncioCommand {

    @Override
    public void executar(AnuncioPresenter anuncioPresenter) {
        try {
            Optional <String> id_c = anuncioPresenter.getView().getTxtId_c();

        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    
}

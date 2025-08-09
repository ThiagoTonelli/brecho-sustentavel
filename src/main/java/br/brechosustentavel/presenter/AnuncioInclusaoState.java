/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.command.NovoAnuncioCommand;


/**
 *
 * @author thiag
 */
public class AnuncioInclusaoState extends AnuncioPresenterState {
    
    public AnuncioInclusaoState(AnuncioPresenter anuncioPresenter) {
        super(anuncioPresenter);
    }
    
    @Override
    public void salvar() {
        new NovoAnuncioCommand().executar(anuncioPresenter);

        //presenter.setEstado(new VisualizacaoState(anuncioPresenter));
    }


}

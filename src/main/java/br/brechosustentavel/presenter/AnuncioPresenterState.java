package br.brechosustentavel.presenter;


import br.brechosustentavel.command.novoAnuncioCommand;
import com.thiago.brechosustentavel.presenter.AnuncioPresenter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author thiag
 */
public abstract class AnuncioPresenterState {
    protected AnuncioPresenter anuncioPresenter;

    public AnuncioPresenterState(AnuncioPresenter anuncioPresenter) {
        this.anuncioPresenter = anuncioPresenter;
    }

    public void salvar() {
        new novoAnuncioCommand().executar(anuncioPresenter);
    }

    public void excluir() {
         throw new RuntimeException("Não é possível excluir estando neste estado");
    }

    public void editar() {
         throw new RuntimeException("Não é possível editar estando neste estado");
    }

    public void cancelar() {
         throw new RuntimeException("Não é possível cancelar estando neste estado");
    }

    public void fechar() {
         throw new RuntimeException("Não é possível fechar estando neste estado");
    }
}


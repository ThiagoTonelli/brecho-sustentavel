/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.command;

import com.thiago.brechosustentavel.presenter.AnuncioPresenter;

/**
 *
 * @author thiag
 */
public interface IAnuncioCommand {
    public void executar(AnuncioPresenter anuncioPresenter);
}

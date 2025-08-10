/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.view.JanelaAnuncioView;
import java.beans.PropertyVetoException;

/**
 *
 * @author thiag
 */
public class InclusaoAnuncioState extends PresenterState{
    
    public InclusaoAnuncioState(TelaPrincipalPresenter telaPresenter) throws PropertyVetoException {
        super(telaPresenter);
        JanelaAnuncioView janelaAnuncio = new JanelaAnuncioView();
        telaPresenter.getView().getjDesktopPane1().add(janelaAnuncio);
        janelaAnuncio.setVisible(true);
        telaPresenter.getView().setVisible(true);
    }
    
}

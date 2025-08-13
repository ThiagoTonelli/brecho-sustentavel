package br.brechosustentavel.presenter;

import br.brechosustentavel.presenter.TelaPrincipalPresenter;
import br.brechosustentavel.presenter.VendedorPresenter.InclusaoAnuncioState;
import br.brechosustentavel.presenter.VendedorPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.presenter.VendedorPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.view.JanelaVendedorView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import br.brechosustentavel.view.JanelaManterAnuncioView;
import br.brechosustentavel.view.IJanelaVendedorView;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author thiag
 */
public class InicioState{
    private TelaPrincipalPresenter telaPresenter;
    private String estado;
    
    public InicioState(TelaPrincipalPresenter telaPresenter) throws PropertyVetoException {
        this.telaPresenter = telaPresenter;;
    }
    
    public void telaVendedor() throws PropertyVetoException{
        JanelaVendedorView janelaVendedor = new JanelaVendedorView();
        telaPresenter.getView().getjDesktopPane1().add(janelaVendedor);
        janelaVendedor.setMaximum(true);
        janelaVendedor.setVisible(true);
        ManterAnuncioPresenter manterAnuncio = new ManterAnuncioPresenter();
    }
    /*
    
    public void telaComprador(){
        //inicia tela comprador
        telaPresenter.getView().getjDesktopPane1().add(janelaEscolhaPerfil);
        janelaEscolhaPerfil.setMaximum(true);
        janelaEscolhaPerfil.setVisible(true);
    }*/

}


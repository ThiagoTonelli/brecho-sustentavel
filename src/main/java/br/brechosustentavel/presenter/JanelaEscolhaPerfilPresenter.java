/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.presenter.VendedorPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.view.JanelaVendedorView;

/**
 *
 * @author kaila
 */
public class JanelaEscolhaPerfilPresenter {
    private TelaPrincipalPresenter telaPresenter = new TelaPrincipalPresenter();
    
    public void telaVendedor(){
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

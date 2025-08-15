/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.JanelaPrincipalPresenter;

import br.brechosustentavel.presenter.ManterAnuncioPresenter.EdicaoAnuncioState;
import br.brechosustentavel.presenter.ManterAnuncioPresenter.InclusaoAnuncioState;
import br.brechosustentavel.presenter.ManterAnuncioPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.view.IJanelaPrincipalView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

/**
 *
 * @author thiag
 */
public class VendedorState extends JanelaPrincipalState{
    
    public VendedorState(JanelaPrincipalPresenter presenter){
        super(presenter);
        
        IJanelaPrincipalView view = presenter.getView();
        view.getButtonAdicionar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                criar();
            }
        });
        view.getButtonVisualizar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                editar();
            }
        }); 
    }
    
    @Override
    public void criar(){
        try {
            ManterAnuncioPresenter presenter = new ManterAnuncioPresenter();
            presenter.setEstadoVendedor(new InclusaoAnuncioState(presenter));
        } catch (PropertyVetoException ex) {
            throw new RuntimeException("erro ao criar novo anuncio", ex);
        }
    }
    
    @Override
    public void editar(){
        try {
            ManterAnuncioPresenter presenter = new ManterAnuncioPresenter();
            presenter.setEstadoVendedor(new EdicaoAnuncioState(presenter));
        } catch (PropertyVetoException ex) {
            throw new RuntimeException("erro ao editar anuncio", ex);
        }
    }
    
    @Override
    public void carregar(){
        
    }
}

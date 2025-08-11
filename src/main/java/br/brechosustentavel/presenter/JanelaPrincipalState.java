package br.brechosustentavel.presenter;

import br.brechosustentavel.view.JanelaPrincipalView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import br.brechosustentavel.view.IJanelaPrincipalView;
import br.brechosustentavel.view.JanelaInclusaoAnuncioView;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author thiag
 */
public class JanelaPrincipalState{
    TelaPrincipalPresenter telaPresenter;
    
    public JanelaPrincipalState(TelaPrincipalPresenter telaPresenter) throws PropertyVetoException {
        this.telaPresenter = telaPresenter;
        JanelaPrincipalView janelaPrincipal = new JanelaPrincipalView();
        telaPresenter.getView().getjDesktopPane1().add(janelaPrincipal);
        janelaPrincipal.setMaximum(true);
        janelaPrincipal.setVisible(true);
        
        janelaPrincipal.getButtonAdicionar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                incluir();
            }
        });
    }

    public void incluir() {
        try {
             JanelaInclusaoAnuncioView janelaDeInclusao = new JanelaInclusaoAnuncioView();
                    
            telaPresenter.getView().getjDesktopPane1().add(janelaDeInclusao);
            janelaDeInclusao.setMaximum(true);
            janelaDeInclusao.setVisible(true);
                    
            ManterAnuncioPresenter presenterDoAnuncio = new ManterAnuncioPresenter(janelaDeInclusao);
            presenterDoAnuncio.setEstado(new JanelaInclusaoAnuncioState(presenterDoAnuncio));
            } catch (Exception ex){
                    //janelaPresenter.setEstado(new VisualizarAnuncioState(janelaPresenter));
            }
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


package br.brechosustentavel.presenter;

import br.brechosustentavel.view.IJanelaPrincipalView;
import br.brechosustentavel.view.JanelaPrincipalView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author thiag
 */
public class JanelaPrincipalState extends PresenterState {

    public JanelaPrincipalState(TelaPrincipalPresenter telaPresenter) throws PropertyVetoException {
        super(telaPresenter);
        JanelaPrincipalView janelaPrincipal = new JanelaPrincipalView();
        telaPresenter.getView().getjDesktopPane1().add(janelaPrincipal);
        janelaPrincipal.setVisible(true);
        telaPresenter.getView().setVisible(true);

        janelaPrincipal.getButtonAdicionar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    telaPresenter.setEstado(new InclusaoAnuncioState(telaPresenter));
                } catch (Exception ex){
                    //janelaPresenter.setEstado(new VisualizarAnuncioState(janelaPresenter));
                }
            }
        });
        /*janelaPresenter.getView().getButtonVisualizar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    new CarregarDefeitosPorTipoCommand().executar(anuncioPresenter);
                } catch (Exception ex){
                    
                }
            }
        });*/
    }

    public void incluir() {
        throw new RuntimeException("Não é possível excluir estando neste estado");
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


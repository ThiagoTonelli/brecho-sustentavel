/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.command.CarregarComposicaoCommand;
import br.brechosustentavel.command.CarregarDefeitosPorTipoCommand;
import br.brechosustentavel.command.CarregarTiposDePecaCommand;
import br.brechosustentavel.command.ICommand;
import br.brechosustentavel.command.NovoAnuncioCommand;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author thiag
 */
public class JanelaInclusaoAnuncioState extends PresenterAnuncioState{
    
    public JanelaInclusaoAnuncioState(ManterAnuncioPresenter presenter) throws PropertyVetoException {
        super(presenter);
        configurarTela();
        
        JComboBox<String> selectTipoDePeca = presenter.getView().getSelectTipoDePeca();
        selectTipoDePeca.addActionListener(new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    new CarregarDefeitosPorTipoCommand().executar(presenter);
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Erro ao carregar defeitos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        salvar();
        cancelar();
    }
    
    private void configurarTela(){
        try{
            new CarregarTiposDePecaCommand().executar(presenter);
            new CarregarDefeitosPorTipoCommand().executar(presenter);
            new CarregarComposicaoCommand().executar(presenter);
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Erro ao carregar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void salvar(){
        try {
            presenter.getView().getBtnEnviar().addActionListener(new ActionListener (){
                @Override
                public void actionPerformed(ActionEvent e){
                    try{
                        ICommand command = new NovoAnuncioCommand();
                        command.executar(presenter);
                        JOptionPane.showMessageDialog(null, "anuncio salvo");
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            //this.cancelar();
        }catch(Exception e){
            //JOptionPane.shoMessagemDi
        }
    }
    
    @Override
    public void cancelar(){
        presenter.getView().getBtnCancelar().addActionListener(new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    presenter.getView().dispose();
                }catch (Exception ex){
                        JOptionPane.showMessageDialog(null, "Erro ao cancelar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });  
    }
    
}

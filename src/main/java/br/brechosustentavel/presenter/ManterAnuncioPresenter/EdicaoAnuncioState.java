/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.manterAnuncioPresenter;
import br.brechosustentavel.command.commandVendedor.EditarAnuncioCommand;
import br.brechosustentavel.command.commandVendedor.ExcluirAnuncioCommand;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.JOptionPane;

/**
 *
 * @author thiag
 */
public class EdicaoAnuncioState extends ManterAnuncioState{
    
    public EdicaoAnuncioState(ManterAnuncioPresenter presenter) throws PropertyVetoException {
        super(presenter);
        configurarTela(true);
        presenter.getView().getBtnEnviar().addActionListener(new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    editar();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Erro ao ir para editar: " + ex.getMessage());
                }
            }
        });
        
        presenter.getView().getBtnCancelar().addActionListener(new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    cancelar();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Erro ao ir para editar: " + ex.getMessage());
                }
            }
        });
    }

    private void configurarTela(boolean estado) throws PropertyVetoException{
        
        for (ActionListener al : presenter.getView().getBtnEnviar().getActionListeners()) {
            presenter.getView().getBtnEnviar().removeActionListener(al);
        }
        
        presenter.getView().setVisible(false);
        presenter.getView().setMaximum(true);
        presenter.getView().setSelected(true);
        presenter.getView().getBtnExcluir().setVisible(false);
        presenter.getView().getBtnExcluir().setEnabled(false);
        presenter.getView().getBtnEnviar().setText("Salvar");
        
        presenter.getView().getTxtId_c().setEnabled(false);
        presenter.getView().getTxtSubcategoria().setEnabled(estado);
        presenter.getView().getTxtCor().setEnabled(estado);
        presenter.getView().getTxtEstadoConservacao().setEnabled(estado);
        presenter.getView().getTxtMassa().setEnabled(estado);
        presenter.getView().getTxtPrecoBase().setEnabled(estado);
        presenter.getView().getTxtTamanho().setEnabled(estado);
        
        presenter.getView().getSelectComposicao().setEnabled(estado);
        presenter.getView().getSelectComposicao1().setEnabled(estado);
        presenter.getView().getSelectComposicao2().setEnabled(estado);
        presenter.getView().getSelectTipoDePeca().setEnabled(estado);
        presenter.getView().getScrollDefeitos().setEnabled(estado);
        presenter.getView().getSpinnerComposicao().setEnabled(estado);
        presenter.getView().getSpinnerComposicao1().setEnabled(estado);
        presenter.getView().getSpinnerComposicao2().setEnabled(estado);
        
        presenter.getView().getBtnGerarId().setEnabled(false);
        for (java.awt.Component comp :  presenter.getView().getPainelScrollDefeitos().getComponents()){
            comp.setEnabled(estado);
        }
        presenter.getView().setVisible(true);
    }
    
    @Override
    public void salvar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void cancelar() {
        presenter.getView().dispose();
    }

    @Override
    public void editar() {
        try {
            new EditarAnuncioCommand().executar(presenter);
            presenter.setEstadoVendedor(new VisualizacaoAnuncioState(presenter));
        } catch (Exception ex) {
            throw new RuntimeException("erro ao editar anuncio: " + ex.getMessage());
        }
    }   

    @Override
    public void visualizar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void excluir() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

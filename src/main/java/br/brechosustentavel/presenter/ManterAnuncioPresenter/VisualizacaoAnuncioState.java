/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.manterAnuncioPresenter;

import br.brechosustentavel.command.commandManterAnuncio.CarregarComposicaoCommand;
import br.brechosustentavel.command.commandManterAnuncio.CarregarTiposDePecaCommand;
import br.brechosustentavel.command.commandManterAnuncio.ExcluirAnuncioCommand;
import br.brechosustentavel.command.commandManterAnuncio.VisualizarAnuncioCommand;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author thiag
 */
public class VisualizacaoAnuncioState extends ManterAnuncioState{

    public VisualizacaoAnuncioState(ManterAnuncioPresenter presenter) {
        super(presenter);
        visualizar();
        
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
                    JOptionPane.showMessageDialog(null, "Erro ao cancelar: " + ex.getMessage());
                }
            }
        });
        
        presenter.getView().getBtnExcluir().addActionListener(new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    excluir();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex.getMessage());
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
        presenter.getView().getBtnExcluir().setVisible(true);
        presenter.getView().getBtnExcluir().setEnabled(true);
        presenter.getView().getBtnEnviar().setText("Editar");
        
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
            presenter.setEstadoVendedor(new EdicaoAnuncioState(presenter));
        } catch (PropertyVetoException ex) {
            throw new RuntimeException("erro ao ir parar edicao" + ex);
        }
    }

    @Override
    public void visualizar() {
        try {
            new CarregarTiposDePecaCommand().executar(presenter);
            new CarregarComposicaoCommand().executar(presenter);
            new VisualizarAnuncioCommand().executar(presenter);
            configurarTela(false);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(EdicaoAnuncioState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void excluir() {
        try {
            ExcluirAnuncioCommand command = new ExcluirAnuncioCommand();
            boolean sucesso = (boolean) command.executar(presenter);

            if (sucesso) {
                JOptionPane.showMessageDialog(
                    presenter.getView(), 
                    "Anúncio encerrado com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE
                );

                presenter.getView().dispose();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                presenter.getView(), 
                "Ocorreu um erro ao encerrar o anúncio:\n" + ex.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
}

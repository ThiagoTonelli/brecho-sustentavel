/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.ManterAnuncioPresenter;
import br.brechosustentavel.commandVendedor.CarregarAnuncioEdicaoCommand;
import br.brechosustentavel.commandVendedor.CarregarComposicaoCommand;
import br.brechosustentavel.commandVendedor.CarregarDefeitosPorTipoCommand;
import br.brechosustentavel.commandVendedor.CarregarTiposDePecaCommand;
import br.brechosustentavel.commandVendedor.ICommandVendedor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author thiag
 */
public class EdicaoAnuncioState extends ManterAnuncioState{
    
    public EdicaoAnuncioState(ManterAnuncioPresenter presenter) throws PropertyVetoException {
        super(presenter);
        visualizar();
        editar();
    }

    private void configurarTela(boolean estado) throws PropertyVetoException{
        presenter.getView().setVisible(true);
        presenter.getView().setMaximum(true);
        //presenter.getView().setSelected(true);
        presenter.getView().getBtnExcluir().setVisible(estado);
        presenter.getView().getBtnExcluir().setEnabled(estado);
        if(estado == false){
            presenter.getView().getBtnEnviar().setText("Editar");
        }
        
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
    }
    
    @Override
    public void salvar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void cancelar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void editar() {
        presenter.getView().getBtnEnviar().addActionListener(new ActionListener (){
                @Override
                public void actionPerformed(ActionEvent e){
                    try{
                        //ICommandVendedor command = new EditarAnuncioCommand();
                        configurarTela(true);
                        //command.executar(presenter);
                        //presenter.setEstadoVendedor(new EdicaoAnuncioState(presenter));
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
    }

    @Override
    public void visualizar() {
        try {
            new CarregarTiposDePecaCommand().executar(presenter);
            new CarregarComposicaoCommand().executar(presenter);
            new CarregarAnuncioEdicaoCommand().executar(presenter);
            configurarTela(false);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(EdicaoAnuncioState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

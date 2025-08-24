/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.manterAnuncioPresenter;

import br.brechosustentavel.command.commandVendedor.CarregarComposicaoCommand;
import br.brechosustentavel.command.commandVendedor.CarregarTiposDePecaCommand;
import br.brechosustentavel.command.commandVendedor.VisualizarAnuncioCompradorCommand;
import br.brechosustentavel.presenter.JanelaOfertaPresenter;
import br.brechosustentavel.service.OfertaService;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author kaila
 */
public class RealizarOfertaAnuncioState extends ManterAnuncioState{
    
    public RealizarOfertaAnuncioState(ManterAnuncioPresenter presenter) {
        super(presenter);
        
        visualizar();
        
        presenter.getView().getBtnEnviar().addActionListener(new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    ofertar();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Erro ao ir ofertar: " + ex.getMessage());
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
    }
    
    @Override
    public void ofertar() {
        Frame janelaPai = (Frame) SwingUtilities.getWindowAncestor(presenter.getView());
        String tipo = presenter.getView().getSelectTipoDePeca().getSelectedItem().toString();
        String subcategoria = presenter.getView().getTxtSubcategoria().getText();
        String valorFinal = presenter.getView().getTxtPrecoBase().getText();
        String idPeca = presenter.getView().getTxtId_c().getText();
        new JanelaOfertaPresenter(janelaPai, tipo, subcategoria, valorFinal, idPeca, new OfertaService());
    }
    
    
    @Override
    public void cancelar() {
        presenter.getView().dispose();
    }
    
    @Override
    public void visualizar() {
        try {
            new CarregarTiposDePecaCommand().executar(presenter);
            new CarregarComposicaoCommand().executar(presenter);
            new VisualizarAnuncioCompradorCommand().executar(presenter);
            configurarTela(false);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(EdicaoAnuncioState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void configurarTela(boolean estado) throws PropertyVetoException{
        presenter.getView().setVisible(false);
        presenter.getView().setMaximum(true);
        presenter.getView().setSelected(true);
        
        //Configura botões [estado = false]
        presenter.getView().getBtnExcluir().setVisible(estado);
        presenter.getView().getBtnGerarId().setVisible(estado);
        presenter.getView().getBtnCancelar().setText("Voltar");
        presenter.getView().getBtnEnviar().setText("Enviar Oferta");
        
        //Configura labels
        presenter.getView().getLabelTitulo().setText("Detalhes do Anúncio");
        presenter.getView().getLabelId_c().setText("ID da Peça");
        presenter.getView().getLabelPrecoBase().setText("Preço Final (R$)");
        
        //Configura campos
        presenter.getView().getTxtId_c().setEnabled(estado);
        presenter.getView().getTxtSubcategoria().setEnabled(estado);
        presenter.getView().getTxtTamanho().setEnabled(estado);
        presenter.getView().getTxtCor().setEnabled(estado);
        presenter.getView().getTxtMassa().setEnabled(estado);        
        presenter.getView().getTxtEstadoConservacao().setEnabled(estado);
        presenter.getView().getTxtPrecoBase().setEnabled(estado);
        
        presenter.getView().getSelectComposicao().setEnabled(estado);
        presenter.getView().getSelectComposicao1().setEnabled(estado);
        presenter.getView().getSelectComposicao2().setEnabled(estado);
        presenter.getView().getSelectTipoDePeca().setEnabled(estado);
        presenter.getView().getScrollDefeitos().setEnabled(estado);
        presenter.getView().getSpinnerComposicao().setEnabled(estado);
        presenter.getView().getSpinnerComposicao1().setEnabled(estado);
        presenter.getView().getSpinnerComposicao2().setEnabled(estado);
        
        for (java.awt.Component comp :  presenter.getView().getPainelScrollDefeitos().getComponents()){
            comp.setEnabled(estado);
        }
        presenter.getView().setVisible(true);
    }
}

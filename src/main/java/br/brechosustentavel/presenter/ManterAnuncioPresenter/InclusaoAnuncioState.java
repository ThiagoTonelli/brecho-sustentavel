/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.manterAnuncioPresenter;

import br.brechosustentavel.command.commandManterAnuncio.CarregarComposicaoCommand;
import br.brechosustentavel.command.commandManterAnuncio.CarregarDefeitosPorTipoCommand;
import br.brechosustentavel.command.commandManterAnuncio.CarregarTiposDePecaCommand;
import br.brechosustentavel.command.commandManterAnuncio.GerarIdCommand;
import br.brechosustentavel.command.commandManterAnuncio.NovoAnuncioCommand;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import br.brechosustentavel.command.commandManterAnuncio.ICommandVendedor;
import br.brechosustentavel.model.Anuncio;

/**
 *
 * @author thiag
 */
public class InclusaoAnuncioState extends ManterAnuncioState{
    
    public InclusaoAnuncioState(ManterAnuncioPresenter presenter) throws PropertyVetoException {
        super(presenter);
        configurarTela(true);

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
        
        presenter.getView().getBtnGerarId().addActionListener(new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    new GerarIdCommand().executar(presenter);
                }catch (Exception ex){
                        JOptionPane.showMessageDialog(null, "Erro ao carregar id_c: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        presenter.getView().getBtnEnviar().addActionListener(new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    salvar();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Erro ao carregar salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        presenter.getView().getBtnCancelar().addActionListener(new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    cancelar();
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Erro ao carregar cancelar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
        if(estado == false){
             presenter.getView().getBtnEnviar().setText("Salvar");
        }
        presenter.getView().getTxtId_c().setEnabled(estado);
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

        presenter.getView().getBtnGerarId().setEnabled(estado);
        for (java.awt.Component comp :  presenter.getView().getPainelScrollDefeitos().getComponents()){
            comp.setEnabled(estado);
        }

        new CarregarTiposDePecaCommand().executar(presenter);
        new CarregarDefeitosPorTipoCommand().executar(presenter);
        new CarregarComposicaoCommand().executar(presenter);
        presenter.getView().setVisible(true);  
    }
    
    @Override
    public void salvar(){
        try {
            if(!todosCamposPreenchidos()){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos e verifique se a soma dos materiais atinge os 100%", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
            ICommandVendedor command = new NovoAnuncioCommand();
            Anuncio anuncio = (Anuncio) command.executar(presenter);
            JOptionPane.showMessageDialog(null, "anuncio salvo");
            presenter.setIdPeca(anuncio.getPeca().getId_c());
            presenter.setEstadoVendedor(new VisualizacaoAnuncioState(presenter));
            
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void cancelar(){
        presenter.getView().dispose(); 
    }
    
    private boolean todosCamposPreenchidos() {

        // campos de texto obrigatórios
        if (presenter.getView().getTxtId_c().getText().trim().isEmpty()) return false;
        if (presenter.getView().getTxtSubcategoria().getText().trim().isEmpty()) return false;
        if (presenter.getView().getTxtMassa().getText().trim().isEmpty()) return false;
        if (presenter.getView().getTxtPrecoBase().getText().trim().isEmpty()) return false;
        // combobox obrigatórios
        if (presenter.getView().getSelectTipoDePeca().getSelectedIndex() == -1) return false;
        if (presenter.getView().getSelectComposicao().getSelectedIndex() == -1) return false;
        if (presenter.getView().getSelectComposicao1().getSelectedIndex() == -1) return false;
        if (presenter.getView().getSelectComposicao2().getSelectedIndex() == -1) return false;

        // pelo menos um material com quantidade > 0 ou != de 100
        int somaMateriais = 0;
        somaMateriais += (int) presenter.getView().getSpinnerComposicao().getValue();
        somaMateriais += (int) presenter.getView().getSpinnerComposicao1().getValue();
        somaMateriais += (int) presenter.getView().getSpinnerComposicao2().getValue();
        if (somaMateriais <= 0 && somaMateriais != 100) return false;

        return true;
    }
  
}

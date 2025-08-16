/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.ManterAnuncioPresenter;

import br.brechosustentavel.commandVendedor.CarregarComposicaoCommand;
import br.brechosustentavel.commandVendedor.CarregarDefeitosPorTipoCommand;
import br.brechosustentavel.commandVendedor.CarregarTiposDePecaCommand;
import br.brechosustentavel.commandVendedor.GerarIdCommand;
import br.brechosustentavel.commandVendedor.NovoAnuncioCommand;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import br.brechosustentavel.commandVendedor.ICommandVendedor;

/**
 *
 * @author thiag
 */
public class InclusaoAnuncioState extends ManterAnuncioState{
    
    public InclusaoAnuncioState(ManterAnuncioPresenter presenter) throws PropertyVetoException {
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
            presenter.getView().setMaximum(true);
            presenter.getView().setVisible(true);
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
                        if(!todosCamposPreenchidos()){
                            JOptionPane.showMessageDialog(null, "Preencha todos os campos e verifique se a soma dos materiais atinge os 100%", "Aviso", JOptionPane.WARNING_MESSAGE);
                        }
                        ICommandVendedor command = new NovoAnuncioCommand();
                        command.executar(presenter);
                        JOptionPane.showMessageDialog(null, "anuncio salvo");
                        presenter.setEstadoVendedor(new EdicaoAnuncioState(presenter));
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

    @Override
    public void editar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void visualizar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private boolean todosCamposPreenchidos() {

        // campos de texto obrigatórios
        if (presenter.getView().getTxtId_c().getText().trim().isEmpty()) return false;
        if (presenter.getView().getTxtSubcategoria().getText().trim().isEmpty()) return false;
        if (presenter.getView().getTxtCor().getText().trim().isEmpty()) return false;
        if (presenter.getView().getTxtMassa().getText().trim().isEmpty()) return false;
        if (presenter.getView().getTxtEstadoConservacao().getText().trim().isEmpty()) return false;
        if (presenter.getView().getTxtPrecoBase().getText().trim().isEmpty()) return false;

        // combobox obrigatórios
        if (presenter.getView().getSelectTipoDePeca().getSelectedIndex() == -1) return false;
        if (presenter.getView().getSelectTamanho().getSelectedIndex() == -1) return false;
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

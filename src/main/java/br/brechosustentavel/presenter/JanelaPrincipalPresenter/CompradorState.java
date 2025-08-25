/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.janelaPrincipalPresenter;

import br.brechosustentavel.command.commandFiltros.CarregarAnunciosFiltradosCommand;
import br.brechosustentavel.command.commandFiltros.CarregarFiltroTiposDePecaCommand;
import br.brechosustentavel.command.commandPrincipal.AdicionarPerfilCommand;
import br.brechosustentavel.command.commandPrincipal.CarregarAnunciosCommand;
import br.brechosustentavel.presenter.JanelaVisualizarPerfilPresenter;
import br.brechosustentavel.presenter.manterAnuncioPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.presenter.manterAnuncioPresenter.RealizarOfertaAnuncioState;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.view.JanelaPrincipalView;
import br.brechosustentavel.view.JanelaVisualizarPerfilView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 *
 * @author thiag
 */
public class CompradorState extends JanelaPrincipalState{
    private JanelaPrincipalView view;
    
    public CompradorState(JanelaPrincipalPresenter presenter, SessaoUsuarioService usuarioAutenticado) throws PropertyVetoException{
        super(presenter, usuarioAutenticado);

        view = presenter.getView();
        view.setVisible(false);
        configurarTela();
        
        configurarFiltros();
        carregar();
                
        view.getButtonVisualizar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                visualizar();
            }
        });
        
        view.getBtnFiltrar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                buscarAnuncios();
            }
        });
                
        view.getMenuVisualizarPerfil().addMenuListener(new MenuListener(){
            @Override
            public void menuSelected(MenuEvent e) {
                JanelaVisualizarPerfilView visualizar = new JanelaVisualizarPerfilPresenter().getView();
                presenter.setFrame(visualizar);
                try {
                    visualizar.setVisible(true);
                    visualizar.setSelected(true);
                    visualizar.setMaximum(true);
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(CompradorState.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            @Override
            public void menuDeselected(MenuEvent e) {}

            @Override
            public void menuCanceled(MenuEvent e) {}
        });
        
        presenter.getView().getOpcAddPerfil().addActionListener(e -> {
            new AdicionarPerfilCommand().executar(presenter);
        });
        
        view.setVisible(true);
    }

    @Override
    public void visualizar() {
        try{
            JTable tabela = presenter.getView().getjTable1();
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(presenter.getView(), "Selecione um anúncio para visualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String idPeca = (String) tabela.getValueAt(linhaSelecionada, 1);
            if (idPeca != null) {            
                ManterAnuncioPresenter anuncioPresenter = new ManterAnuncioPresenter(usuarioAutenticado);
                presenter.setFrame(anuncioPresenter.getView());
                anuncioPresenter.setIdPeca(idPeca);
                anuncioPresenter.setEstadoVendedor(new RealizarOfertaAnuncioState(anuncioPresenter));
            } else {
                JOptionPane.showMessageDialog(presenter.getView(), "Anúncio não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (PropertyVetoException ex) {
            throw new RuntimeException("Erro ao visuaizar anuncio", ex);
        }
    }
    
    @Override
    public void carregar(){
        try {
            new CarregarAnunciosCommand().executar(presenter);
        } catch (Exception e) {
            System.err.println("Erro ao recarregar anúncios: " + e.getMessage());
        }
    }
    
    private void configurarFiltros(){
        JComboBox<String> cboxCriterio = view.getcBoxCriterioFiltro();
        
        cboxCriterio.addItem("Todos");
        cboxCriterio.addItem("Tipo de Peça");
        cboxCriterio.addItem("Faixa de Preço");
        
        cboxCriterio.addActionListener(e -> atualizarOpcoesDeFiltro()); 
        atualizarOpcoesDeFiltro();
    }
    
    private void atualizarOpcoesDeFiltro(){
        String criterio = (String) view.getcBoxCriterioFiltro().getSelectedItem();
        JComboBox<String> valor = view.getcBoxValorFiltro();
        view.getTxtAtributos().setEnabled(false);
        valor.removeAllItems();
        valor.setEnabled(!"Todos".equals(criterio));
        
        if ("Tipo de Peça".equals(criterio)) {
            new CarregarFiltroTiposDePecaCommand().executar(presenter);
        } else if ("Faixa de Preço".equals(criterio)) {
            valor.addItem("Até R$ 50,00");
            valor.addItem("R$ 50,01 a R$ 100,00");
            valor.addItem("R$ 100,01 a R$ 500,00");
            valor.addItem("Acima de R$ 500,00"); 
        }
    }
    
    private void buscarAnuncios(){
        try{
            new CarregarAnunciosFiltradosCommand(usuarioAutenticado).executar(presenter);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao buscar anuncios " + e.getMessage());
        }
    }
    
    private void configurarTela() throws PropertyVetoException{
        view.setMaximum(true);
        view.setVisible(false);
        view.setTitle("Bem-vindo Comprador!");
        view.getButtonAdicionar().setVisible(false);
        view.getButtonManterTipo().setVisible(false);
        view.getButtonManterComposicao().setVisible(false);
        view.getButtonVisualizar().setText("Ver detalhes do anuncio");
        
    }
}

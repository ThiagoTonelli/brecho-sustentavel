/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.commandPrincipal;

import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.presenter.JanelaPrincipalPresenter.JanelaPrincipalPresenter;
import br.brechosustentavel.repository.IAnuncioRepository;
import br.brechosustentavel.repository.IVendedorRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.service.SessaoUsuarioService;
import java.util.List;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kaila
 */
public class CarregarAnunciosCommand implements ICommandPrincipal{

    @Override
    public void executar(JanelaPrincipalPresenter presenter, SessaoUsuarioService usuarioAutenticado) {
        String[] colunas = {
            "Vendedor", "ID Peça", "Tipo de Peça", "Subcategoria", "Estado de Conservação", "GWP Evitado (kg)", "MCI", "Preço Final (R$)"
        };

        DefaultTableModel modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable tabela = presenter.getView().getjTable1();
        tabela.setModel(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
        tabela.getTableHeader().setReorderingAllowed(false); 

        try {
            RepositoryFactory fabrica = RepositoryFactory.getInstancia();
            IAnuncioRepository repositoryAnuncio = fabrica.getAnuncioRepository();
            IVendedorRepository repositoryVendedor = fabrica.getVendedorRepository();
            List<Anuncio> anuncios = repositoryAnuncio.buscarTodos();

            modelo.setRowCount(0);

            if (anuncios.isEmpty()) {
                modelo.addRow(new Object[]{"Nenhum anúncio encontrado."});
            } else {
                for (Anuncio a : anuncios) {
                    modelo.addRow(new Object[]{
                        a.getIdVendedor,
                        a.getPeca().getId_c(),
                        a.getPeca().getTipoDePeca(),
                        a.getPeca().getSubcategoria(),
                        a.getPeca().getEstadoDeConservacao(),
                        String.format("%.2f", a.getGwpAvoided()), 
                        String.format("%.2f", a.getMci()),       
                        String.format("%.2f", a.getValorFinal()) 
                    });
                }
            }
        } catch (Exception e) {

            modelo.setRowCount(0);
            modelo.addRow(new Object[]{"Erro ao carregar anúncios."});
            throw new RuntimeException("Erro ao inicializar auncios" + e.getMessage());
        }
    }
    
}

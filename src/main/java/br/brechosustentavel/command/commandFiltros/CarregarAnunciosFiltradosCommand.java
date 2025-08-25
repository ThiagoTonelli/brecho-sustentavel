/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandFiltros;

import br.brechosustentavel.dto.FiltroAnuncioDTO;
import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.presenter.janelaPrincipalPresenter.JanelaPrincipalPresenter;
import br.brechosustentavel.repository.repositoryFactory.IAnuncioRepository;
import br.brechosustentavel.repository.repositoryFactory.IUsuarioRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import br.brechosustentavel.service.SessaoUsuarioService;
import java.util.List;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kaila
 */
public class CarregarAnunciosFiltradosCommand implements ICommandFiltros{
    private SessaoUsuarioService sessao;
    
    public CarregarAnunciosFiltradosCommand(SessaoUsuarioService sessao) {
        this.sessao = sessao;
    }

    @Override
    public void executar(JanelaPrincipalPresenter presenter) {
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
            IUsuarioRepository usuarioRepository = fabrica.getUsuarioRepository();
            IAnuncioRepository anuncioRepository = fabrica.getAnuncioRepository();
            FiltroAnuncioDTO filtro = new FiltroAnuncioDTO();

            filtro.setTipoCriterio((String) presenter.getView().getcBoxCriterioFiltro().getSelectedItem());
            filtro.setValorFiltro((String) presenter.getView().getcBoxValorFiltro().getSelectedItem());
            List<Anuncio> anuncios = anuncioRepository.buscarComFiltros(filtro, sessao.getUsuarioAutenticado().getId());
            
            modelo.setRowCount(0);

            if (anuncios.isEmpty()) {
                modelo.addRow(new Object[]{"Nenhum anúncio encontrado."});
            } else {
                for (Anuncio a : anuncios) {
                    modelo.addRow(new Object[]{
                        usuarioRepository.buscarPorId(a.getVendedor().getId()).get().getNome(),
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
            modelo.addRow(new Object[]{"Erro ao carregar anúncios filtrados."});
            throw new RuntimeException("Erro ao inicializar anuncios" + e.getMessage());
        }
    }
}
    
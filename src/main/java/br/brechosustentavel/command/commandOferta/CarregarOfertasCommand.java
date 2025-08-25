/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandOferta;

import br.brechosustentavel.model.Oferta;
import br.brechosustentavel.presenter.JanelaVisualizarOfertasPresenter;
import br.brechosustentavel.repository.repositoryFactory.IAnuncioRepository;
import br.brechosustentavel.repository.repositoryFactory.IOfertaRepository;
import br.brechosustentavel.repository.repositoryFactory.IUsuarioRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import br.brechosustentavel.view.JanelaVisualizarOfertasView;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kaila
 */
public class CarregarOfertasCommand implements ICommandOferta{

    @Override
    public void executar(JanelaVisualizarOfertasPresenter presenter, String idPeca) {
        try{
            JanelaVisualizarOfertasView view = presenter.getView();
            RepositoryFactory fabrica = RepositoryFactory.getInstancia();
            IOfertaRepository ofertaRepository = fabrica.getOfertaRepository();
            IAnuncioRepository anuncioRepository = fabrica.getAnuncioRepository();
            IUsuarioRepository usuarioRepository = fabrica.getUsuarioRepository();

            List<Oferta> ofertas = ofertaRepository.buscarOfertaPorAnuncio(anuncioRepository.buscarPorIdPeca(idPeca).get().getId());

            String[] colunas = {"ID da Oferta", "Nome do Comprador", "Valor ofertado", "Data da Oferta"};
            DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

            JTable tabela = view.getTableOfertas();
            tabela.setModel(modelo);
            tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
            tabela.getTableHeader().setReorderingAllowed(false); 
            modelo.setRowCount(0);
            
            if (ofertas.isEmpty()) {
                modelo.addRow(new Object[]{"Nenhuma oferta encontrada."});
            } else {
                for (Oferta oferta : ofertas) {
                     Object[] rowData = {
                         oferta.getId(),
                         usuarioRepository.buscarPorId(oferta.getComprador().getId()).get().getNome(),
                         "R$ " + String.format("%.2f", oferta.getValor()),
                         oferta.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                     };
                     modelo.addRow(rowData);
                 } 
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar as ofertas: " + e.getMessage());
        }
    }   
}

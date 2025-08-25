/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandDenuncia;

import br.brechosustentavel.model.Denuncia;
import br.brechosustentavel.presenter.JanelaVisualizarDenunciasPresenter;
import br.brechosustentavel.repository.IAnuncioRepository;
import br.brechosustentavel.repository.IDenunciaRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.view.JanelaVisualizarDenunciasView;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kaila
 */
public class CarregarDenunciasCommand implements ICommandDenuncia{
    private final JanelaVisualizarDenunciasPresenter presenter;
    private final String idPeca;
    
    public CarregarDenunciasCommand(JanelaVisualizarDenunciasPresenter presenter, String idPeca){
        this.presenter = presenter;
        this.idPeca = idPeca;
    }

    @Override
    public void executar() {
        try{
            JanelaVisualizarDenunciasView view = presenter.getView();
            RepositoryFactory fabrica = RepositoryFactory.getInstancia();
            IDenunciaRepository denunciaRepository = fabrica.getDenunciaRepository();
            IAnuncioRepository anuncioRepository = fabrica.getAnuncioRepository();
            
            List<Denuncia> denuncias = denunciaRepository.buscarDenunciaPorStatus("Pendente", anuncioRepository.buscarPorIdPeca(idPeca).get().getId());

            String[] colunas = {"ID da Denuncia", "ID do Anuncio", "Peça", "Motivo", "Descriçao", "Data da Denuncia"};
            DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

            JTable tabela = view.getTableDenuncias();
            tabela.setModel(modelo);
            tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
            tabela.getTableHeader().setReorderingAllowed(false); 
            modelo.setRowCount(0);
            
            if (denuncias.isEmpty()) {
                modelo.addRow(new Object[]{"Nenhuma denúncia encontrada."});
            } else {
                for (Denuncia denuncia : denuncias) {
                     Object[] rowData = {
                         denuncia.getId(),
                         denuncia.getAnuncio().getId(),
                         denuncia.getAnuncio().getPeca().getSubcategoria(),
                         denuncia.getMotivo(),
                         denuncia.getDescricao(),
                         denuncia.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                     };
                     modelo.addRow(rowData);
                 } 
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar as denuncias: " + e.getMessage());
        }
    }   
}

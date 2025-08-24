/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandManterTipoPeca;

import br.brechosustentavel.presenter.manterTipoPecaPresenter.ManterTipoPecaPresenter;
import br.brechosustentavel.repository.ITipoDePecaRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author thiag
 */
public class CarregarTiposPecaCommand implements ICommandTipoPeca {
    
    @Override
    public void executar(ManterTipoPecaPresenter presenter) {
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        ITipoDePecaRepository tipoPecaRepo = fabrica.getTipoDePecaRepository();

        String[] colunas = {"ID", "Nome do Tipo de Peça"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        List<Map<String, Object>> tiposPeca = tipoPecaRepo.buscarTodosParaManutencao();
        for (Map<String, Object> tipo : tiposPeca) {
            model.addRow(new Object[]{ tipo.get("id"), tipo.get("nome") });
        }
        
        presenter.getView().getTableTipoPeca().setModel(model);
    }
}

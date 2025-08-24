/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandManterDefeito;

import br.brechosustentavel.presenter.ManterDefeitoPresenter.ManterDefeitoPresenter;
import br.brechosustentavel.repository.IDefeitoRepository;
import br.brechosustentavel.repository.ITipoDePecaRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author thiag
 */
public class CarregarDefeitosCommand implements ICommandDefeito {
    @Override
    public void executar(ManterDefeitoPresenter presenter) {
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        IDefeitoRepository defeitoRepo = fabrica.getDefeitoRepository();
        ITipoDePecaRepository tipoPecaRepo = fabrica.getTipoDePecaRepository();

        String[] colunas = {"ID", "Nome do Defeito", "Abatimento (%)", "Tipo de Peça"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        List<Map<String, Object>> defeitos = defeitoRepo.buscarTodosParaManutencao();
        for (Map<String, Object> defeito : defeitos) {
            Object[] rowData = {
                defeito.get("id"),
                defeito.get("nome"),
                defeito.get("desconto"), 
                defeito.get("tipo_peca_nome")
            };
            model.addRow(rowData);
        }
        presenter.getView().getTableDefeitos().setModel(model);

        presenter.getView().getSelectTipoPeca().removeAllItems();
        List<String> tiposPeca = tipoPecaRepo.buscarTiposDePeca();
        for (String tipo : tiposPeca) {
            presenter.getView().getSelectTipoPeca().addItem(tipo);
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandManterComposicao;

import br.brechosustentavel.presenter.manterComposicaoPresenter.ManterComposicaoPresenter;
import br.brechosustentavel.repository.repositoryFactory.IComposicaoRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author thiag
 */
public class CarregarComposicoesCommand implements ICommandComposicao {
    
    @Override
    public void executar(ManterComposicaoPresenter presenter) {
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        IComposicaoRepository composicaoRepo = fabrica.getComposicaoRepository();

        String[] colunas = {"ID", "Nome do Material", "Fator de Emissão"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        List<Map<String, Object>> composicoes = composicaoRepo.buscarTodosParaManutencao();
        for (Map<String, Object> composicao : composicoes) {
            model.addRow(new Object[]{ 
                composicao.get("id"), 
                composicao.get("nome"), 
                composicao.get("fator_emissao") 
            });
        }
        
        presenter.getView().getTableComposicao().setModel(model);
    }
}

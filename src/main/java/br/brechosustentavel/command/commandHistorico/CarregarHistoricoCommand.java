/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandHistorico;

import br.brechosustentavel.model.Transacao;
import br.brechosustentavel.presenter.JanelaHistoricoPresenter;
import br.brechosustentavel.repository.repositoryFactory.ITransacaoRepository;
import br.brechosustentavel.repository.repositoryFactory.IUsuarioRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author thiag
 */

public class CarregarHistoricoCommand implements ICommandHistorico {

    @Override
    public void executar(JanelaHistoricoPresenter presenter) {
        String perfil = presenter.getSessao().getTipoPerfil();
        int idUsuario = presenter.getSessao().getUsuarioAutenticado().getId();
        
        ITransacaoRepository transacaoRepo = RepositoryFactory.getInstancia().getTransacaoRepository();
        IUsuarioRepository usuarioRepo = RepositoryFactory.getInstancia().getUsuarioRepository();
        List<Transacao> transacoes;

        String[] colunas;
        
        if ("Comprador".equalsIgnoreCase(perfil)) {
            transacoes = transacaoRepo.buscarPorComprador(idUsuario);
            colunas = new String[]{"Transação", "Data", "Item", "Valor", "Vendedor"};
        } else { 
            transacoes = transacaoRepo.buscarPorVendedor(idUsuario);
            colunas = new String[]{"Transação", "Data", "Item", "Valor", "Comprador"};
        }
        
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        for (Transacao t : transacoes) {
            String outraParte = "Comprador".equalsIgnoreCase(perfil)
                    ? usuarioRepo.buscarPorId(t.getOferta().getAnuncio().getVendedor().getId()).get().getNome()
                    : (usuarioRepo.buscarPorId(t.getOferta().getComprador().getId()).get().getNome());
            
            model.addRow(new Object[]{
                t, 
                t.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                t.getOferta().getAnuncio().getPeca().getSubcategoria(),
                String.format("R$ %.2f", t.getValorTotal()),
                outraParte
            });
        }
        
        presenter.getView().getTableHistorico().setModel(model);
        
        presenter.getView().getTableHistorico().getColumnModel().getColumn(0).setMinWidth(0);
        presenter.getView().getTableHistorico().getColumnModel().getColumn(0).setMaxWidth(0);
        presenter.getView().getTableHistorico().getColumnModel().getColumn(0).setWidth(0);
    }
}


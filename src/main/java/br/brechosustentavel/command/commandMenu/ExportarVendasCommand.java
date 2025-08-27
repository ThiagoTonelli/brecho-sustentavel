/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandMenu;

import br.brechosustentavel.service.ExportacaoVendasService;
import br.brechosustentavel.service.GerenciadorLog;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author thiag
 */
public class ExportarVendasCommand implements ICommandMenu{
    
    @Override
    public void executar(java.awt.Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Relatório de Vendas");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Ficheiro CSV", "csv"));
        
        fileChooser.setSelectedFile(new File("relatorio_vendas.csv"));

        int userSelection = fileChooser.showSaveDialog(parent);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File ficheiroParaSalvar = fileChooser.getSelectedFile();
            
            if (!ficheiroParaSalvar.getAbsolutePath().endsWith(".csv")) {
                ficheiroParaSalvar = new File(ficheiroParaSalvar + ".csv");
            }

            try {
                ExportacaoVendasService exportacaoService = new ExportacaoVendasService();
                exportacaoService.exportarVendasParaCsv(ficheiroParaSalvar);
                JOptionPane.showMessageDialog(parent,
                        "Relatório exportado com sucesso para:\n" + ficheiroParaSalvar.getAbsolutePath(),
                        "Exportação Concluída",
                        JOptionPane.INFORMATION_MESSAGE);
                GerenciadorLog.getInstancia().registrarSucesso("Exportação de Dados", "N/A", "N/A", ficheiroParaSalvar.getName());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent,
                        "Erro ao exportar o ficheiro CSV:\n" + e.getMessage(),
                        "Erro de Exportação",
                        JOptionPane.ERROR_MESSAGE);
                GerenciadorLog.getInstancia().registrarFalha("Exportação de Dados", "N/A", ficheiroParaSalvar.getName(), e.getMessage());
            }
        }
    }
}

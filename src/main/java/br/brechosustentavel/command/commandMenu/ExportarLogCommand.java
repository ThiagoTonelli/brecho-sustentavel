/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandMenu;

import br.brechosustentavel.configuracao.ConfiguracaoAdapter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author thiag
 */
public class ExportarLogCommand implements ICommandMenu{

    @Override
    public void executar(java.awt.Component parent) {
        ConfiguracaoAdapter config = new ConfiguracaoAdapter();
        String caminhoLogAtual = config.getValor("LOG_CAMINHO");

        if (caminhoLogAtual == null || caminhoLogAtual.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "O caminho do arquivo de log não está configurado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File ficheiroLogOriginal = new File(caminhoLogAtual);
        if (!ficheiroLogOriginal.exists()) {
            JOptionPane.showMessageDialog(parent, "O arquivo de log '" + caminhoLogAtual + "' ainda não foi criado. Pois ainda não há logs no sistema", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Exportar Ficheiro de Log Como...");
        fileChooser.setSelectedFile(new File(ficheiroLogOriginal.getName())); 

        int userSelection = fileChooser.showSaveDialog(parent);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File destinoCopia = fileChooser.getSelectedFile();

            try {
                Files.copy(ficheiroLogOriginal.toPath(), destinoCopia.toPath(), StandardCopyOption.REPLACE_EXISTING);
                
                JOptionPane.showMessageDialog(parent,
                        "Ficheiro de log exportado com sucesso para:\n" + destinoCopia.getAbsolutePath(),
                        "Exportação Concluída",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(parent,
                        "Erro ao exportar o ficheiro de log:\n" + e.getMessage(),
                        "Erro de Exportação",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

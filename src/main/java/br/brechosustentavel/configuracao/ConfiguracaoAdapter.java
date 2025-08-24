package br.brechosustentavel.configuracao;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author thiag
 */
public class ConfiguracaoAdapter {

    private Dotenv dotenv;
    private final String diretorio = "src/main/java/br/brechosustentavel/configuracao";
    private final String nomeFicheiro = "configuracaoSGBD.env";
    private final Path caminhoFicheiro = Paths.get(diretorio, nomeFicheiro);

    public ConfiguracaoAdapter() {
        carregarConfiguracoes();
    }

    private void carregarConfiguracoes() {
        this.dotenv = Dotenv.configure()
                            .directory(diretorio)
                            .filename(nomeFicheiro)
                            .load();
    }

    public String getValor(String chave) {
        return dotenv.get(chave);
    }

    public void setValor(String chave, String valor) {
        try {
            
            List<String> linhas = Files.lines(caminhoFicheiro).collect(Collectors.toList());
            boolean chaveEncontrada = false;

            for (int i = 0; i < linhas.size(); i++) {
                String linha = linhas.get(i);
                if (linha.trim().startsWith(chave + "=")) {
                    linhas.set(i, chave + "=" + valor); 
                    chaveEncontrada = true;
                    break;
                }
            }

            if (!chaveEncontrada) {
                linhas.add(chave + "=" + valor);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoFicheiro.toFile(), false))) {
                for (String linha : linhas) {
                    writer.write(linha);
                    writer.newLine();
                }
            }
            
            carregarConfiguracoes();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar a configuração no ficheiro .env", e);
        }
    }
}

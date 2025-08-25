package br.brechosustentavel.configuracao;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.BufferedWriter;
import java.io.File;
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
    private final String nomeFicheiro = "configuracaoSGBD.env";
    private final Path caminhoFicheiroExterno = Paths.get(nomeFicheiro);

    public ConfiguracaoAdapter() {
        carregarConfiguracoes();
    }

    private void carregarConfiguracoes() {
        this.dotenv = Dotenv.configure()
                            .directory("./")
                            .filename(nomeFicheiro)
                            .ignoreIfMissing()
                            .load();
        
        if (dotenv.get("SGBD") == null) {
            this.dotenv = Dotenv.configure()
                                .filename(nomeFicheiro) 
                                .ignoreIfMissing()
                                .load();
        }

        if (dotenv.get("SGBD") == null) {
             throw new RuntimeException("Não foi possível carregar o arquivo de configuração '" + nomeFicheiro + "' nem de um local externo, nem de dentro do JAR.");
        }
    }

    public String getValor(String chave) {
        return dotenv.get(chave);
    }

    public void setValor(String chave, String valor) {
        File ficheiro = caminhoFicheiroExterno.toFile();
        

        try {
            List<String> linhas;
            if (ficheiro.exists()) {
                linhas = Files.lines(caminhoFicheiroExterno).collect(Collectors.toList());
            } else {
                linhas = new java.util.ArrayList<>();
            }
            
            boolean chaveEncontrada = false;
            for (int i = 0; i < linhas.size(); i++) {
                if (linhas.get(i).trim().startsWith(chave + "=")) {
                    linhas.set(i, chave + "=" + valor);
                    chaveEncontrada = true;
                    break;
                }
            }

            if (!chaveEncontrada) {
                linhas.add(chave + "=" + valor);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ficheiro, false))) {
                for (String linha : linhas) {
                    writer.write(linha);
                    writer.newLine();
                }
            }
            
            carregarConfiguracoes();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar a configuração no ficheiro .env externo.", e);
        }
    }
}

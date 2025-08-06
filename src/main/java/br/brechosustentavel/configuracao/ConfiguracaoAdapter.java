package br.brechosustentavel.configuracao;

import io.github.cdimascio.dotenv.Dotenv;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author thiag
 */
public class ConfiguracaoAdapter {
    private final Dotenv dotenv;
   
    public ConfiguracaoAdapter() {
        dotenv = Dotenv.configure().filename("configuracaoSGBD.env").load();
    }

    public String getValor(String chave) {
        return dotenv.get(chave);
    }
}

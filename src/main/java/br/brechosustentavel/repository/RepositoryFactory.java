/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository;

/**
 *
 * @author kaila
 */
public abstract class RepositoryFactory {
    public static RepositoryFactory getRepositoryFactory(String sgbd){
        ConfiguracaoAdapter configuracao = new ConfiguracaoAdapter();
        String sgbd = configuracao.getValor("SGBD");
        
    }
}

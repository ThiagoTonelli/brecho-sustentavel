/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.h2;

import org.h2.tools.Server;

/**
 *
 * @author thiag
 */
public class IniciarH2Console {
    public static void main(String[] args) {
        try {
            // Inicia o servidor web do console H2 na porta 8082
            Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
            System.out.println("Console do H2 iniciado em http://localhost:8082");
            System.out.println("Pressione [Enter] para parar.");
            System.in.read(); // Mantém o programa rodando
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

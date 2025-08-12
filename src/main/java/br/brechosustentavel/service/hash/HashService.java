/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.service.hash;

/**
 *
 * @author kaila
 */
public interface HashService {
    public String gerarHash(String senha);
    
    public boolean verificarHash(String senha, String senhaComHash);
}

package br.brechosustentavel.service.hash;

import org.mindrot.jbcrypt.BCrypt;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kaila
 */
public class BCryptAdapter implements HashService {    
    @Override
    public String gerarHash(String senha){
        String salt = BCrypt.gensalt(12);
        
        return BCrypt.hashpw(senha, salt);
    }
    
    @Override
    public boolean verificarHash(String senha, String senhaComHash){
        return BCrypt.checkpw(senha, senhaComHash);
    }
}

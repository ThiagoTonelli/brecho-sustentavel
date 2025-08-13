/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.gerador_id_c;
import org.apache.commons.lang3.RandomStringUtils;


/**
 *
 * @author thiag
 */
public class AlfanumericoDozeDigitos implements IMetodoGeracao{
    
    @Override
    public String gerar() {
        String id = RandomStringUtils.randomAlphanumeric(12);
        return id;
    }
    
}

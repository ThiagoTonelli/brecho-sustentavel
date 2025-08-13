/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.gerador_id_c;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thiag
 */
public class GeradorIdService {
    private IMetodoGeracao metodo;
    
    public GeradorIdService(){
        metodo = new AlfanumericoDozeDigitos();
        setGerador(metodo);
    }
    
    public String Gerar(){
        return metodo.gerar();
    }
    
    public void setGerador(IMetodoGeracao metodo){
        this.metodo = metodo;
    }
}

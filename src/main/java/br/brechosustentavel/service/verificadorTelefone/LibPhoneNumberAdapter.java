/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.verificador_telefone;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

/**
 *
 * @author kaila
 */
public class LibPhoneNumberAdapter implements VerificadorTelefoneService {
   
    
    @Override
    public boolean verificarTelefone(String telefone) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        
        try {
            PhoneNumber numero = phoneUtil.parse(telefone, "BR");
            
            return phoneUtil.isValidNumber(numero);
        } catch(NumberParseException e) { 
            throw new RuntimeException("Não foi possível validar o número de telefone: " + e.getMessage());
        }
    }
    
}

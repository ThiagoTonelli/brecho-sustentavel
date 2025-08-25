/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.pontuacao;

import br.brechosustentavel.model.Comprador;
import br.brechosustentavel.model.Vendedor;

/**
 *
 * @author thiag
 */
public class ReputacaoService {

    // Define os limiares para promoção de nível
    private static final double LIMIAR_PRATA = 2.0;
    private static final double LIMIAR_OURO = 5.0;

    public void atualizarNivel(Vendedor vendedor) {
        double estrelas = vendedor.getEstrelas();
        String nivelAtual = vendedor.getNivel();

        if (estrelas >= LIMIAR_OURO && !"Ouro".equals(nivelAtual)) {
            vendedor.setNivel("Ouro");
        } else if (estrelas >= LIMIAR_PRATA && "Bronze".equals(nivelAtual)) {
            vendedor.setNivel("Prata");
        }
    }

    public void atualizarNivel(Comprador comprador) {
        double estrelas = comprador.getEstrelas();
        String nivelAtual = comprador.getNivel();

        if (estrelas >= LIMIAR_OURO && !"Ouro".equals(nivelAtual)) {
            comprador.setNivel("Ouro");
        } else if (estrelas >= LIMIAR_PRATA && "Bronze".equals(nivelAtual)) {
            comprador.setNivel("Prata");
        }
    }
}

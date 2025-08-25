/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.pontuacao;

import br.brechosustentavel.model.Comprador;
import br.brechosustentavel.model.Transacao;
import br.brechosustentavel.model.Vendedor;
import br.brechosustentavel.repository.RepositoryFactory;

/**
 *
 * @author thiag
 */
public class PontuacaoTransacaoConcluidaStrategy implements IPontuacaoStrategy {

    private static final double PONTOS = 0.5;

    @Override
    public void calcularEAtualizar(Object contexto, RepositoryFactory fabrica) {
        if (!(contexto instanceof Transacao)) return;

        Transacao transacao = (Transacao) contexto;
        Vendedor vendedor = transacao.getOferta().getAnuncio().getVendedor();
        Comprador comprador = transacao.getOferta().getComprador();

        vendedor.setEstrelas(vendedor.getEstrelas() + PONTOS);
        comprador.setEstrelas(comprador.getEstrelas() + PONTOS);

        ReputacaoService reputacaoService = new ReputacaoService();
        reputacaoService.atualizarNivel(vendedor);
        reputacaoService.atualizarNivel(comprador);

        fabrica.getVendedorRepository().editar(vendedor);
        fabrica.getCompradorRepository().editar(comprador);
    }
}

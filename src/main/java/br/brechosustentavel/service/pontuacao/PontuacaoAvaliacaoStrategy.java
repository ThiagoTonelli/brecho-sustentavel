/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.pontuacao;

import br.brechosustentavel.model.Avaliacao;
import br.brechosustentavel.model.Comprador;
import br.brechosustentavel.model.Vendedor;
import br.brechosustentavel.repository.repositoryFactory.ICompradorRepository;
import br.brechosustentavel.repository.repositoryFactory.IVendedorRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;

/**
 *
 * @author thiag
 */
public class PontuacaoAvaliacaoStrategy implements IPontuacaoStrategy {

    private static final double PONTOS = 0.05;

    @Override
    public void calcularEAtualizar(Object contexto, RepositoryFactory fabrica) {
        if (!(contexto instanceof Avaliacao)) return;

        Avaliacao avaliacao = (Avaliacao) contexto;
        ICompradorRepository compradorRepo = fabrica.getCompradorRepository();
        IVendedorRepository vendedorRepo = fabrica.getVendedorRepository();
        ReputacaoService reputacaoService = new ReputacaoService();
        // Verifica quem é o autor e aplica a pontuação
        if (avaliacao.isAutorComprador()) {
            Comprador comprador = avaliacao.getTransacao().getOferta().getComprador();
            comprador.setEstrelas(comprador.getEstrelas() + PONTOS);
            compradorRepo.editar(comprador);
            reputacaoService.atualizarNivel(comprador);
        } else {
            Vendedor vendedor = avaliacao.getTransacao().getOferta().getAnuncio().getVendedor();
            vendedor.setEstrelas(vendedor.getEstrelas() + PONTOS);
            vendedorRepo.editar(vendedor);
            reputacaoService.atualizarNivel(vendedor);
        }
    }
}

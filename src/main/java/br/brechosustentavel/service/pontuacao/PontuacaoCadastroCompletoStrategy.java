/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.pontuacao;

import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.model.Vendedor;
import br.brechosustentavel.repository.repositoryFactory.IVendedorRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;

/**
 *
 * @author thiag
 */
public class PontuacaoCadastroCompletoStrategy implements IPontuacaoStrategy {

    private static final double PONTOS = 0.05;

    @Override
    public void calcularEAtualizar(Object contexto, RepositoryFactory fabrica) {
        
        if (!(contexto instanceof Anuncio)) return;

        Anuncio anuncio = (Anuncio) contexto;
        Vendedor vendedor = anuncio.getVendedor();
        
        if (isCadastroCompleto(anuncio.getPeca())) {
            vendedor.setEstrelas(vendedor.getEstrelas() + PONTOS);
            new ReputacaoService().atualizarNivel(vendedor);
            IVendedorRepository vendedorRepo = fabrica.getVendedorRepository();
            vendedorRepo.editar(vendedor);
            ReputacaoService reputacaoService = new ReputacaoService();
            reputacaoService.atualizarNivel(vendedor);
        }
    }

    private boolean isCadastroCompleto(br.brechosustentavel.model.Peca peca) {
        return peca.getTipoDePeca() != null && !peca.getTipoDePeca().isEmpty() &&
               peca.getSubcategoria() != null && !peca.getSubcategoria().isEmpty() &&
               peca.getTamanho() != null && !peca.getTamanho().isEmpty() &&
               peca.getMaterialQuantidade() != null && !peca.getMaterialQuantidade().isEmpty() &&
               peca.getMassaEstimada() > 0 &&
               peca.getEstadoDeConservacao() != null && !peca.getEstadoDeConservacao().isEmpty();
    }

}

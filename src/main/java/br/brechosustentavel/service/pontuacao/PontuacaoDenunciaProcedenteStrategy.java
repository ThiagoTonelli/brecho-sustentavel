/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.pontuacao;

import br.brechosustentavel.model.Comprador;
import br.brechosustentavel.model.Denuncia;
import br.brechosustentavel.repository.repositoryFactory.ICompradorRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;

/**
 *
 * @author thiag
 */
public class PontuacaoDenunciaProcedenteStrategy implements IPontuacaoStrategy {

    private static final double PONTOS = 0.1;

    @Override
    public void calcularEAtualizar(Object contexto, RepositoryFactory fabrica) {
        if (!(contexto instanceof Denuncia)) return;

        Denuncia denuncia = (Denuncia) contexto;

        // A pontuação só é aplicada se o status for "Procedente"
        if ("Procedente".equalsIgnoreCase(denuncia.getStatus())) {
            Comprador denunciante = denuncia.getComprador();
            denunciante.setEstrelas(denunciante.getEstrelas() + PONTOS);
            
            ICompradorRepository compradorRepo = fabrica.getCompradorRepository();
            compradorRepo.editar(denunciante);
            ReputacaoService reputacaoService = new ReputacaoService();
            reputacaoService.atualizarNivel(denunciante);
        }
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.pontuacao;

import br.brechosustentavel.model.Oferta;
import br.brechosustentavel.model.Vendedor;
import br.brechosustentavel.repository.repositoryFactory.IVendedorRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import java.time.Duration;

/**
 *
 * @author thiag
 */
public class PontuacaoRespostaRapidaStrategy implements IPontuacaoStrategy {

    private static final double PONTOS = 0.05;
    private static final long LIMITE_HORAS = 24;

    @Override
    public void calcularEAtualizar(Object contexto, RepositoryFactory fabrica) {
        if (!(contexto instanceof Oferta)) return;

        Oferta oferta = (Oferta) contexto;
        
        if (oferta.getData() != null && oferta.getDataResposta() != null) {
            
            long horasParaResponder = Duration.between(oferta.getData(), oferta.getDataResposta()).toHours();

            if (horasParaResponder <= LIMITE_HORAS) {
                Vendedor vendedor = oferta.getAnuncio().getVendedor();
                vendedor.setEstrelas(vendedor.getEstrelas() + PONTOS);

                IVendedorRepository vendedorRepo = fabrica.getVendedorRepository();
                vendedorRepo.editar(vendedor);
                ReputacaoService reputacaoService = new ReputacaoService();
                reputacaoService.atualizarNivel(vendedor);
                System.out.println("entrou aqui oferta");
            }
        }
    }
}

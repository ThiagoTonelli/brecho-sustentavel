/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.dashboard;

import br.brechosustentavel.repository.ICompradorRepository;
import br.brechosustentavel.repository.IVendedorRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author thiag
 */
public class DashboardService {

    private final IVendedorRepository vendedorRepository;
    private final ICompradorRepository compradorRepository;

    public DashboardService(RepositoryFactory fabrica) {
        this.vendedorRepository = fabrica.getVendedorRepository();
        this.compradorRepository = fabrica.getCompradorRepository();
    }

    public Map<String, Integer> getDadosReputacao() {
        Map<String, Integer> dadosVendedores = vendedorRepository.contarPorNivelReputacao();
        Map<String, Integer> dadosCompradores = compradorRepository.contarPorNivelReputacao();

        Map<String, Integer> dadosConsolidados = new HashMap<>(dadosVendedores);
        
        dadosCompradores.forEach((nivel, contagem) -> 
            dadosConsolidados.merge(nivel, contagem, (v1, v2) -> v1 + v2)
        );

        return dadosConsolidados;
    }
    
    public Map<String, Double> getDadosEvolucaoGWP() {
    // Presume que um novo método foi criado no ILinhaDoTempoRepository
    return RepositoryFactory.getInstancia().getLinhaDoTempoRepository().getGWPEvitadoPorSemana();
    }

    public Map<String, Double> getDadosParticipacaoMateriais() {
        // Presume que um novo método foi criado no IComposicaoPecaRepository
        return RepositoryFactory.getInstancia().getComposicaoPecaRepository().getMassaTotalPorMaterial();
    }

    public Map<String, Double> getDadosRankingVendedores() {
        // Presume que um novo método foi criado no IVendedorRepository
        return RepositoryFactory.getInstancia().getVendedorRepository().getTopVendedoresPorGWP(5); // Top 5
    }
}
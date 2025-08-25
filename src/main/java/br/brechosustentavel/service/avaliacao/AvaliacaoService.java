/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.avaliacao;

import br.brechosustentavel.model.Avaliacao;
import br.brechosustentavel.repository.repositoryFactory.IAvaliacaoRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import br.brechosustentavel.service.pontuacao.PontuacaoService;

/**
 *
 * @author thiag
 */
public class AvaliacaoService {
    
    private final IAvaliacaoRepository avaliacaoRepository;
    private final PontuacaoService pontuacaoService;

    public AvaliacaoService() {
        this.avaliacaoRepository = RepositoryFactory.getInstancia().getAvaliacaoRepository();
        this.pontuacaoService = new PontuacaoService();
    }
    
    public void salvarAvaliacao(Avaliacao avaliacao) {
        if (avaliacao.getTexto() == null || avaliacao.getTexto().trim().isEmpty()) {
            throw new IllegalArgumentException("O texto da avaliação não pode ser vazio.");
        }

        avaliacaoRepository.salvar(avaliacao);

        pontuacaoService.processarAvaliacaoSubmetida(avaliacao);

    }
}

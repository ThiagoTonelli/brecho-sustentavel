/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.avaliacao;

import br.brechosustentavel.model.Avaliacao;
import br.brechosustentavel.model.EventoLinhaDoTempo;
import br.brechosustentavel.repository.repositoryFactory.IAvaliacaoRepository;
import br.brechosustentavel.repository.repositoryFactory.ILinhaDoTempoRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import br.brechosustentavel.service.pontuacao.PontuacaoService;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 *
 * @author thiag
 */
public class AvaliacaoService {
    
    private final IAvaliacaoRepository avaliacaoRepository;
    private final ILinhaDoTempoRepository linhaDoTempoRepository;
    private final PontuacaoService pontuacaoService;

    public AvaliacaoService() {
        this.avaliacaoRepository = RepositoryFactory.getInstancia().getAvaliacaoRepository();
        this.linhaDoTempoRepository = RepositoryFactory.getInstancia().getLinhaDoTempoRepository();
        this.pontuacaoService = new PontuacaoService();
    }
    
    public void salvarAvaliacao(Avaliacao avaliacao) {
        if (avaliacao.getTexto() == null || avaliacao.getTexto().trim().isEmpty()) {
            throw new IllegalArgumentException("O texto da avaliação não pode ser vazio.");
        }
        
        Optional<EventoLinhaDoTempo> ultimoEventoOpt = linhaDoTempoRepository.ultimoEvento(avaliacao.getTransacao().getOferta().getAnuncio().getPeca().getId_c());
        
        avaliacaoRepository.salvar(avaliacao);
        
        EventoLinhaDoTempo evento = new EventoLinhaDoTempo(
            "Avaliação registrada",
            "Avaliação registrada",
            LocalDateTime.now(), 
            avaliacao.getTransacao().getOferta().getAnuncio().getGwpAvoided(),
            avaliacao.getTransacao().getOferta().getAnuncio().getMci()
        );
        
        evento.setCliclo(ultimoEventoOpt.get().getCiclo_n());
        
        linhaDoTempoRepository.criar(avaliacao.getTransacao().getOferta().getAnuncio().getPeca().getId_c(), evento);

        pontuacaoService.processarAvaliacaoSubmetida(avaliacao);

    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.pontuacao;

import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.model.Avaliacao;
import br.brechosustentavel.model.Denuncia;
import br.brechosustentavel.model.Oferta;
import br.brechosustentavel.model.Transacao;
import br.brechosustentavel.repository.RepositoryFactory;

/**
 *
 * @author thiag
 */
public class PontuacaoService {

    private final RepositoryFactory fabrica;

    public PontuacaoService() {
        this.fabrica = RepositoryFactory.getInstancia();
    }

    public void processarNovoAnuncio(Anuncio anuncio) {
        new PontuacaoCadastroCompletoStrategy().calcularEAtualizar(anuncio, fabrica);
    }

    public void processarConclusaoTransacao(Transacao transacao) {
        new PontuacaoTransacaoConcluidaStrategy().calcularEAtualizar(transacao, fabrica);
    }
    
    public void processarNovaOferta(Oferta oferta) {
        new PontuacaoOfertaValidaStrategy().calcularEAtualizar(oferta, fabrica);
    }
    
    public void processarRespostaOferta(Oferta oferta) {
        new PontuacaoRespostaRapidaStrategy().calcularEAtualizar(oferta, fabrica);
    }
    
    public void processarAvaliacaoSubmetida(Avaliacao avaliacao) {
        new PontuacaoAvaliacaoStrategy().calcularEAtualizar(avaliacao, fabrica);
    }

    public void processarDenunciaValidada(Denuncia denuncia) {
        new PontuacaoDenunciaProcedenteStrategy().calcularEAtualizar(denuncia, fabrica);
    }
}

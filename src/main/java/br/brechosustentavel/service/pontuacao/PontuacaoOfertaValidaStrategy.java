/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.pontuacao;

import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.model.Comprador;
import br.brechosustentavel.model.Oferta;
import br.brechosustentavel.repository.IAnuncioRepository;
import br.brechosustentavel.repository.ICompradorRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import java.util.Optional;

/**
 *
 * @author thiag
 */
public class PontuacaoOfertaValidaStrategy implements IPontuacaoStrategy {

    private static final double PONTOS = 0.05;

    @Override
    public void calcularEAtualizar(Object contexto, RepositoryFactory fabrica) {
        if (!(contexto instanceof Oferta)) return;

        Oferta oferta = (Oferta) contexto;
        
        IAnuncioRepository anuncioRepo = fabrica.getAnuncioRepository();
        Optional<Anuncio> anuncioOpt = anuncioRepo.buscarAnuncioPorId(oferta.getAnuncio().getId());

        if (anuncioOpt.isPresent()) {
            Anuncio anuncio = anuncioOpt.get();
            double precoFinal = anuncio.getValorFinal();
            
            double limiteInferior = precoFinal * 0.80;
            double limiteSuperior = precoFinal * 0.99;

            if (oferta.getValor() >= limiteInferior && oferta.getValor() <= limiteSuperior) {
                Comprador comprador = oferta.getComprador();
                comprador.setEstrelas(comprador.getEstrelas() + PONTOS);

                ICompradorRepository compradorRepo = fabrica.getCompradorRepository();
                compradorRepo.salvar(comprador);
            }
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.h2;

import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IAnuncioRepository;
import br.brechosustentavel.repository.IAvaliacaoRepository;
import br.brechosustentavel.repository.IComposicaoPecaRepository;
import br.brechosustentavel.repository.ICompradorRepository;
import br.brechosustentavel.repository.IDefeitoPecaRepository;
import br.brechosustentavel.repository.IDefeitoRepository;
import br.brechosustentavel.repository.ILinhaDoTempoRepository;
import br.brechosustentavel.repository.ITipoDePecaRepository;
import br.brechosustentavel.repository.ITransacaoRepository;
import br.brechosustentavel.repository.IUsuarioRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.repository.IPecaRepository;
import br.brechosustentavel.repository.IVendedorRepository;
import br.brechosustentavel.repository.IComposicaoRepository;
import br.brechosustentavel.repository.ICompradorInsigniaRepository;
import br.brechosustentavel.repository.IDenunciaRepository;
import br.brechosustentavel.repository.IInsigniaRepository;
import br.brechosustentavel.repository.IOfertaRepository;
import br.brechosustentavel.repository.IVendedorInsigniaRepository;

/**
 *
 * @author thiag
 */
public class H2RepositoryFactory extends RepositoryFactory {
    private final ConexaoFactory conexaoFactory;

    public H2RepositoryFactory(ConexaoFactory conexaoFactory) {
        this.conexaoFactory = conexaoFactory;
    }

    @Override
    public IDefeitoRepository getDefeitoRepository() {
        return new H2DefeitoRepository(conexaoFactory, getTipoDePecaRepository());
    }

    @Override
    public IUsuarioRepository getUsuarioRepository() {
        return new H2UsuarioRepository(conexaoFactory);
    }

    @Override
    public IComposicaoRepository getComposicaoRepository() {
        return new H2ComposicaoRepository(conexaoFactory);
    }

    @Override
    public IComposicaoPecaRepository getComposicaoPecaRepository() {
        return new H2ComposicaoPecaRepository(conexaoFactory);
    }

    @Override
    public ITransacaoRepository getTransacaoRepository() {
        return new H2TransacaoRepository(conexaoFactory);
    }

    @Override
    public IPecaRepository getPecaRepository() {
        return new H2PecaRepository(conexaoFactory);
    }

    @Override
    public ITipoDePecaRepository getTipoDePecaRepository() {
        return new H2TipoPecaRepository(conexaoFactory);
    }

    @Override
    public ILinhaDoTempoRepository getLinhaDoTempoRepository() {
        return new H2LinhaDoTempoRepository(conexaoFactory);
    }

    @Override
    public IVendedorRepository getVendedorRepository() {
        return new H2VendedorRepository(conexaoFactory);
    }

    @Override
    public ICompradorRepository getCompradorRepository() {
        return new H2CompradorRepository(conexaoFactory);
    }

    @Override
    public IAnuncioRepository getAnuncioRepository() {
        return new H2AnuncioRepository(conexaoFactory);
    }

    @Override
    public IDefeitoPecaRepository getDefeitoPecaRepository() {
        return new H2DefeitoPecaRepository(conexaoFactory);
    }

    @Override
    public IInsigniaRepository getInsigniaRepository() {
        return new H2InsigniaRepository(conexaoFactory);
    }

    @Override
    public IVendedorInsigniaRepository getVendedorInsigniaRepository() {
        return new H2VendedorInsigniaRepository(conexaoFactory);
    }

    @Override
    public ICompradorInsigniaRepository getCompradorInsigniaRepository() {
        return new H2CompradorInsigniaRepository(conexaoFactory);
    }

    @Override
    public IOfertaRepository getOfertaRepository() {
        return new H2OfertaRepository(conexaoFactory);
    }

    @Override
    public IDenunciaRepository getDenunciaRepository() {
        return new H2DenunciaRepository(conexaoFactory);
    }

    @Override
    public IAvaliacaoRepository getAvaliacaoRepository() {
        return new H2AvaliacaoRepository(conexaoFactory);
    }
}

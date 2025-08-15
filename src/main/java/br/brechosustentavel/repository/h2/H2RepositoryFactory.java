/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.h2;

import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IAnuncioRepository;
import br.brechosustentavel.repository.ICompradorRepository;
import br.brechosustentavel.repository.IDefeitoRepository;
import br.brechosustentavel.repository.ILinhaDoTempoRepository;
import br.brechosustentavel.repository.IMaterialRepository;
import br.brechosustentavel.repository.ITipoDePecaRepository;
import br.brechosustentavel.repository.ITransacaoRepository;
import br.brechosustentavel.repository.IUsuarioRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.repository.IPecaRepository;
import br.brechosustentavel.repository.IVendedorRepository;

/**
 *
 * @author thiag
 */
public class H2RepositoryFactory extends RepositoryFactory{
    private final ConexaoFactory conexaoFactory;
    
    public H2RepositoryFactory(ConexaoFactory conexaoFactory){
        this.conexaoFactory = conexaoFactory;
    }

    @Override
    public IUsuarioRepository getUsuarioRepository() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public IMaterialRepository getMaterialRepository() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ITransacaoRepository getTransacaoRepository() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public IDefeitoRepository getDefeitoRepository() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ITipoDePecaRepository getTipoDePecaRepository() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public IPecaRepository getPecaRepository() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ILinhaDoTempoRepository getLinhaDoTempoRepository() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public IVendedorRepository getVendedorRepository() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ICompradorRepository getCompradorRepository() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public IAnuncioRepository getAnuncioRepository() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

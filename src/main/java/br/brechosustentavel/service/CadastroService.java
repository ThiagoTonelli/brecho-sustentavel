/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service;

import br.brechosustentavel.model.Comprador;
import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.model.Vendedor;
import br.brechosustentavel.repository.ICompradorRepository;
import br.brechosustentavel.repository.IUsuarioRepository;
import br.brechosustentavel.repository.IVendedorRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.service.hash.HashService;
import br.brechosustentavel.service.verificador_telefone.VerificadorTelefoneService;

/**
 *
 * @author kaila
 */
public class CadastroService {
    private HashService hashService;
    private RepositoryFactory repositoryFactory;
    private VerificadorTelefoneService verificadorTelefoneService;
    
    public CadastroService(HashService hashService, VerificadorTelefoneService verificadorTelefoneService, RepositoryFactory repositoryFactory){
        this.hashService = hashService;
        this.verificadorTelefoneService = verificadorTelefoneService;
        this.repositoryFactory = repositoryFactory;
    }
    
    public void cadastrar(Usuario usuario, String confirmacaoSenha, String opcao){
        try {
            IUsuarioRepository usuarioRepository = repositoryFactory.getUsuarioRepository();
            ICompradorRepository compradorRepository = repositoryFactory.getCompradorRepository();
            IVendedorRepository vendedorRepository = repositoryFactory.getVendedorRepository();
            
            if (usuario.getNome().isBlank() || usuario.getEmail().isBlank() || usuario.getSenha().isBlank()) {
                throw new RuntimeException("Nome, email e senha são campos obrigatórios.");
            }
            if (!usuario.getEmail().contains("@") || !usuario.getEmail().contains(".")){
                throw new RuntimeException("Email inválido.");
            }
            if (!usuario.getSenha().equals(confirmacaoSenha)) {
                throw new RuntimeException("As senhas não conferem.");
            }
            if(opcao.equals("")){
                throw new RuntimeException("Selecione um perfil.");
            }
            if(usuarioRepository.buscarPorEmail(usuario.getEmail()).isPresent()){
                throw new RuntimeException("O email informado já está em uso");
            }   
            if(!usuario.getTelefone().trim().isEmpty() && !verificadorTelefoneService.verificarTelefone(usuario.getTelefone())) {
                throw new RuntimeException("O número de telefone é inválido!");
            }
            if(isVazio()){
                usuario.setAdmin(true);
            }

            usuario.setSenha(hashService.gerarHash(usuario.getSenha()));
            usuarioRepository.cadastrarUsuario(usuario);
            if(opcao.equalsIgnoreCase("Comprador")){
                Comprador comprador = new Comprador("Bronze", 0.0, 0, 0.0, false);
                comprador.setId(usuario.getId());
                usuario.setComprador(comprador);
                usuario.setVendedor(null);
                compradorRepository.salvar(usuario.getComprador().get());
            } else if(opcao.equalsIgnoreCase("Vendedor")){
                Vendedor vendedor = new Vendedor("Bronze", 0.0, 0, 0.0);
                vendedor.setId(usuario.getId());
                usuario.setVendedor(vendedor);
                usuario.setComprador(null);
                vendedorRepository.salvar(usuario.getVendedor().get());
            } else if(opcao.equalsIgnoreCase("Ambos")) {
                Comprador comprador = new Comprador("Bronze", 0.0, 0, 0.0, false);
                comprador.setId(usuario.getId());
                usuario.setComprador(comprador);
                compradorRepository.salvar(usuario.getComprador().get());
                Vendedor vendedor = new Vendedor("Bronze", 0.0, 0, 0.0);
                vendedor.setId(usuario.getId());
                usuario.setVendedor(vendedor);
                vendedorRepository.salvar(usuario.getVendedor().get());
            } 
        } catch(Exception e){
            throw new RuntimeException("Erro ao cadastrar: " + e.getMessage());
        }
    }

    public boolean isVazio(){
        return repositoryFactory.getUsuarioRepository().isVazio();
    }
}

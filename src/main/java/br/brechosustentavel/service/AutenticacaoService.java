/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service;

import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.repository.IUsuarioRepository;
import br.brechosustentavel.service.hash.HashService;
import java.util.Optional;


/**
 *
 * @author kaila
 */
public class AutenticacaoService {
    private IUsuarioRepository usuarioRepository;
    private HashService hashService;

    public AutenticacaoService(IUsuarioRepository usuarioRepository, HashService hashService) {
        this.usuarioRepository = usuarioRepository;
        this.hashService = hashService;
    }
    
    public void cadastrar(Usuario usuario){
        if(usuarioRepository.buscarPorEmail(usuario.getEmail()).isPresent()){
            throw new RuntimeException("O email informado já está em uso");
        }
        
        
    
    }

    public Usuario autenticar(String email, String senha) {
        if(email == null || email.isBlank()){
            throw new IllegalArgumentException("Email inválido");
        }
        
        Optional<Usuario> optUsuario = usuarioRepository.buscarPorEmail(email);
        
        if(optUsuario.isPresent()) {
            Usuario usuarioEncontrado = optUsuario.get();
            
            if(hashService.verificarHash(senha, usuarioEncontrado.getSenha())) {
                return usuarioEncontrado;
            }
        }
       throw new RuntimeException("Credenciais inválidas!");
    }

}
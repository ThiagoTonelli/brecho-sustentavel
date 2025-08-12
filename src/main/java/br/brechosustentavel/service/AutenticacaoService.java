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

    public Usuario autenticar(Usuario usuario) {
        Optional<Usuario> optUsuario = usuarioRepository.buscarPorEmail(usuario.getEmail());
        
        if(optUsuario.isPresent()) {
            Usuario usuarioEncontrado = optUsuario.get();
            
            if(hashService.verificarHash(usuario.getSenha(), usuarioEncontrado.getSenha())) {
                return usuarioEncontrado;
            }else {
                throw new RuntimeException("Usuário " + usuario.getEmail() + " não autenticado");
            }
        }
       throw new RuntimeException("Usuário não existe");
    }

}
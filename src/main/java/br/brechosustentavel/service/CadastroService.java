/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service;

import br.brechosustentavel.model.Comprador;
import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.model.Vendedor;
import br.brechosustentavel.repository.IUsuarioRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.service.hash.BCryptAdapter;
import br.brechosustentavel.service.hash.HashService;
import br.brechosustentavel.service.verificador_telefone.LibPhoneNumberAdapter;
import br.brechosustentavel.service.verificador_telefone.VerificadorTelefoneService;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public class CadastroService {
    private HashService hashService = new BCryptAdapter();
    private VerificadorTelefoneService verificadorTelefoneService = new LibPhoneNumberAdapter();
    private RepositoryFactory fabrica = RepositoryFactory.getInstancia();
    private IUsuarioRepository usuarioRepository = fabrica.getUsuarioRepository();
    
    public void cadastrar(Usuario usuario, boolean isComprador, boolean isVendedor){
        if(usuarioRepository.buscarPorEmail(usuario.getEmail()).isPresent()){
            throw new RuntimeException("O email informado já está em uso");
        }   
        if(!usuario.getTelefone().trim().isEmpty()){
            if(!verificadorTelefoneService.verificarTelefone(usuario.getTelefone())){
                throw new RuntimeException("O número de telefone é inválido!");
            }
        }

        if(isVazio()){
            usuario.setAdmin(true);
        }
        
        usuario.setSenha(hashService.gerarHash(usuario.getSenha()));
        Usuario usuarioCadastrado = usuarioRepository.cadastrarUsuario(usuario);
        
        if(!isVazio()){
            if(isComprador){
                new Comprador().setId(usuarioCadastrado.getId());
            }
            else{
                new Vendedor().setId(usuarioCadastrado.getId());
            }
        }
    }
    
    public boolean isVazio(){
        return usuarioRepository.isVazio();
    }
}

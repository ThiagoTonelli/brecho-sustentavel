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
import br.brechosustentavel.service.hash.BCryptAdapter;
import br.brechosustentavel.service.hash.HashService;
import br.brechosustentavel.service.verificador_telefone.LibPhoneNumberAdapter;
import br.brechosustentavel.service.verificador_telefone.VerificadorTelefoneService;

/**
 *
 * @author kaila
 */
public class CadastroService {
    private HashService hashService = new BCryptAdapter();
    private VerificadorTelefoneService verificadorTelefoneService = new LibPhoneNumberAdapter();
    private RepositoryFactory fabrica = RepositoryFactory.getInstancia();
    private IUsuarioRepository usuarioRepository = fabrica.getUsuarioRepository();
    private ICompradorRepository compradorRepository = fabrica.getCompradorRepository();
    private IVendedorRepository vendedorRepository = fabrica.getVendedorRepository();
    
    public void cadastrar(Usuario usuario, boolean isComprador, boolean isVendedor){
        try{
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
                    Comprador comprador = new Comprador();
                    comprador.setId(usuarioCadastrado.getId());
                    compradorRepository.cadastrarComprador(comprador);

                }
                else{
                    Vendedor vendedor = new Vendedor("Bronze", 0.0, 0, 0.0);
                    vendedor.setId(usuarioCadastrado.getId());
                    vendedorRepository.cadastrarVendedor(vendedor);
                }
            }
        } catch(Exception e){
            throw new RuntimeException("Erro ao cadastrar: " + e.getMessage());
        }
    }
    
    public boolean isVazio(){
        return usuarioRepository.isVazio();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.repository.repositoryFactory;

import br.brechosustentavel.model.Usuario;
import java.util.Optional;

/**
 *
 * @author thiag
 */
public interface IUsuarioRepository {
    public void cadastrarUsuario(Usuario usuario);
    public Optional<Usuario> buscarPorEmail(String email);
    public Optional<Usuario> buscarPorId(int id);
    public boolean isVazio();
}

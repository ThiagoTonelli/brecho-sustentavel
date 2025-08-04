/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.repository.IItemRepository;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.view.IAnuncioView;

/**
 *
 * @author thiag
 */
public class AnuncioPresenter {
    private IAnuncioView view;
    private SessaoUsuarioService usuarioAutenticado;
    
    public AnuncioPresenter(SessaoUsuarioService usuarioAutenticado, IAnuncioView view, IItemRepository repositorioItem){
        this.view = view;
        this.usuarioAutenticado = usuarioAutenticado;
        
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.projeto.view.IAnuncioView;

/**
 *
 * @author thiag
 */
public class AnuncioPresenter {
    private IAnuncioView view;
    private SessaoUsuario usuarioAutenticado;
    
    public AnuncioPresenter(SessaoUsuario usuarioAutenticado, IAnuncioView view, IItemRepository repositorioItem){
        this.view = view;
        this.usuarioAutenticado = usuarioAutenticado;
        
    }
    
}

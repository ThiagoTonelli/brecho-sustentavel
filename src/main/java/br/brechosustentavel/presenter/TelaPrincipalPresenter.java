/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;
import br.brechosustentavel.repository.IUsuarioRepository;
import br.brechosustentavel.view.TelaPrincipalView;
import java.beans.PropertyVetoException;


/**
 *
 * @author thiag
 */
public final class TelaPrincipalPresenter {
    private final TelaPrincipalView view;
    private final IUsuarioRepository usuarioRepository;
    private PresenterState estado;
   
    public TelaPrincipalPresenter(IUsuarioRepository usuarioRepository, TelaPrincipalView view) throws PropertyVetoException{
        this.view = view; 
        this.usuarioRepository = usuarioRepository;
        setEstado(new JanelaPrincipalState(this));
    }
    
    public void setEstado(PresenterState estado){
        this.estado = estado;
    }
    
    public TelaPrincipalView getView(){
        return view;
    }
    
    public void setEstado(JanelaPrincipalState estado){
        this.estado = estado;
    }
}

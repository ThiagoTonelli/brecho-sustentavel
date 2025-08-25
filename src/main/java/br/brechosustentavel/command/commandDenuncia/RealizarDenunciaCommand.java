/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandDenuncia;

import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.model.Comprador;
import br.brechosustentavel.model.Denuncia;
import br.brechosustentavel.presenter.JanelaDenunciarAnuncioPresenter;
import br.brechosustentavel.repository.IAnuncioRepository;
import br.brechosustentavel.repository.ICompradorRepository;
import br.brechosustentavel.repository.IDenunciaRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.view.JanelaDenunciarAnuncioView;
import java.util.Optional;
import javax.swing.JOptionPane;

/**
 *
 * @author kaila
 */
public class RealizarDenunciaCommand implements ICommandDenuncia {
    private SessaoUsuarioService sessao;
    
    public RealizarDenunciaCommand(SessaoUsuarioService sessao){
        this.sessao = sessao;
    }
    
    @Override
    public void executar(JanelaDenunciarAnuncioPresenter presenter) {
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        IAnuncioRepository anuncioRepository = fabrica.getAnuncioRepository();
        ICompradorRepository compradorRepository = fabrica.getCompradorRepository();
        IDenunciaRepository denunciaRepository = fabrica.getDenunciaRepository();
        JanelaDenunciarAnuncioView view = presenter.getView();
        
        //Pega as informações da view
        String idPeca = view.getTxtIDPeca().getText();
        String motivo = (String) view.getcBoxMotivo().getSelectedItem();
        String descricao = view.getTxtDescricao().getText();
            
        if(motivo.equals("Outro") && (descricao.isEmpty() || descricao.isBlank())){
           throw new RuntimeException("Por favor, descreva o outro motivo da denuncia.");
        }

        Optional<Anuncio> optAnuncio = anuncioRepository.buscarPorIdPeca(idPeca);
        Optional<Comprador> optComprador = compradorRepository.buscarPorId(sessao.getUsuarioAutenticado().getComprador().get().getId());
        
        if(optAnuncio.isEmpty()){
            throw new RuntimeException("Não foi encontrado um anúncio com a peça " + idPeca);
        }
        if(optComprador.isEmpty()){
            throw new RuntimeException("Não foi encontrado um comprador com o id " + optComprador.get().getId());
        }
        
        Denuncia denuncia = new Denuncia(optAnuncio.get(), optComprador.get(), motivo, descricao, "Pendente");
        denunciaRepository.inserirDenuncia(denuncia);
    }  
}

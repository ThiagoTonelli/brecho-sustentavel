/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandMenu;

import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.presenter.janelaPrincipalPresenter.CompradorState;
import br.brechosustentavel.presenter.janelaPrincipalPresenter.JanelaPrincipalPresenter;
import br.brechosustentavel.presenter.janelaPrincipalPresenter.VendedorState;
import br.brechosustentavel.repository.repositoryFactory.IUsuarioRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import br.brechosustentavel.service.SessaoUsuarioService;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

/**
 *
 * @author thiag
 */
public class TrocarPerfilCommand {

    public Object executar(JanelaPrincipalPresenter presenter) throws PropertyVetoException {
        SessaoUsuarioService sessao = presenter.getUsuario();

        IUsuarioRepository usuarioRepo = RepositoryFactory.getInstancia().getUsuarioRepository();
        int idUsuarioAtual = sessao.getUsuarioAutenticado().getId();
        Usuario usuarioCompleto = usuarioRepo.buscarPorId(idUsuarioAtual).orElse(null);

        
        List<String> perfisDisponiveis = new ArrayList<>();
        if (usuarioCompleto.getComprador().isPresent()) {
            perfisDisponiveis.add("Comprador");
        }
        if (usuarioCompleto.getVendedor().isPresent()) {
            perfisDisponiveis.add("Vendedor");
        }
        
        String perfilAtual = sessao.getTipoPerfil();
        List<String> opcoesDeTroca = perfisDisponiveis.stream()
                                                     .filter(p -> !p.equalsIgnoreCase(perfilAtual))
                                                     .collect(Collectors.toList());

        if (opcoesDeTroca.isEmpty()) {
            JOptionPane.showMessageDialog(presenter.getView(), "Você não possui outros perfis para trocar.", "Troca de Perfil", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        String novoPerfilSelecionado = (String) JOptionPane.showInputDialog(
                presenter.getView(),
                "Escolha o perfil para o qual deseja trocar:",
                "Trocar Perfil",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opcoesDeTroca.toArray(),
                opcoesDeTroca.get(0)
        );

        if (novoPerfilSelecionado != null && !novoPerfilSelecionado.isEmpty()) {
            sessao.setTipoPerfil(novoPerfilSelecionado);
            
            if (novoPerfilSelecionado.equalsIgnoreCase("Comprador")) {
                presenter.setEstado(new CompradorState(presenter, sessao));
            } else if (novoPerfilSelecionado.equalsIgnoreCase("Vendedor")) {
                presenter.setEstado(new VendedorState(presenter, sessao));
            }
            
            JOptionPane.showMessageDialog(presenter.getView(), "Perfil alterado para " + novoPerfilSelecionado + " com sucesso!");
        }
        
        return null; 
    }
}

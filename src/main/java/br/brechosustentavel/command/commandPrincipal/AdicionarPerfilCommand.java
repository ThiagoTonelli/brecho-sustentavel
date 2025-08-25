/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandPrincipal;

import br.brechosustentavel.model.Comprador;
import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.model.Vendedor;
import br.brechosustentavel.presenter.janelaPrincipalPresenter.JanelaPrincipalPresenter;
import br.brechosustentavel.repository.ICompradorRepository;
import br.brechosustentavel.repository.IUsuarioRepository;
import br.brechosustentavel.repository.IVendedorRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.service.GerenciadorLog;
import br.brechosustentavel.service.SessaoUsuarioService;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author thiag
 */
public class AdicionarPerfilCommand implements ICommandPrincipal{

    @Override
    public void executar(JanelaPrincipalPresenter presenter) {
        SessaoUsuarioService sessao = presenter.getUsuario();
        int idUsuarioAtual = sessao.getUsuarioAutenticado().getId();

        IUsuarioRepository usuarioRepo = RepositoryFactory.getInstancia().getUsuarioRepository();
        Usuario usuarioCompleto = usuarioRepo.buscarPorId(idUsuarioAtual).orElse(null);

        if (usuarioCompleto == null) {
            JOptionPane.showMessageDialog(presenter.getView(), "Erro ao carregar dados do utilizador.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> perfisParaAdicionar = new ArrayList<>();
        if (!usuarioCompleto.getVendedor().isPresent()) {
            perfisParaAdicionar.add("Vendedor");
        }
        if (!usuarioCompleto.getComprador().isPresent()) {
            perfisParaAdicionar.add("Comprador");
        }

        if (perfisParaAdicionar.isEmpty()) {
            JOptionPane.showMessageDialog(presenter.getView(), "Você já possui todos os perfis disponíveis!", "Adicionar Perfil", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String perfilEscolhido = (String) JOptionPane.showInputDialog(
                presenter.getView(),
                "Escolha o novo perfil que deseja adicionar:",
                "Adicionar Novo Perfil",
                JOptionPane.PLAIN_MESSAGE,
                null,
                perfisParaAdicionar.toArray(),
                perfisParaAdicionar.get(0)
        );

        // 5. Se o utilizador escolheu um perfil, cria-o
        if (perfilEscolhido != null && !perfilEscolhido.isEmpty()) {
            try {
                if ("Vendedor".equalsIgnoreCase(perfilEscolhido)) {
                    // Cria um Vendedor com valores padrão
                    Vendedor novoVendedor = new Vendedor("Bronze", 0.0, 0, 0.0);
                    novoVendedor.setId(idUsuarioAtual);
                    
                    IVendedorRepository vendedorRepository = RepositoryFactory.getInstancia().getVendedorRepository();
                    vendedorRepository.salvar(novoVendedor);
                    
                } else if ("Comprador".equalsIgnoreCase(perfilEscolhido)) {
                    // Cria um Comprador com valores padrão
                    Comprador novoComprador = new Comprador("Bronze", 0.0, 0, 0.0, false);
                    novoComprador.setId(idUsuarioAtual);

                    ICompradorRepository compradorRepository = RepositoryFactory.getInstancia().getCompradorRepository();
                    compradorRepository.salvar(novoComprador);
                }
                
                JOptionPane.showMessageDialog(presenter.getView(), "Perfil de " + perfilEscolhido + " criado com sucesso!");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(presenter.getView(), "Ocorreu um erro ao criar o perfil: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}


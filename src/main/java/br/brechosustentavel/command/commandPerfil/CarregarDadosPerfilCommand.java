/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandPerfil;

import br.brechosustentavel.model.Comprador;
import br.brechosustentavel.model.Insignia;
import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.model.Vendedor;
import br.brechosustentavel.presenter.JanelaVisualizarPerfilPresenter;
import br.brechosustentavel.repository.repositoryFactory.ICompradorInsigniaRepository;
import br.brechosustentavel.repository.repositoryFactory.IUsuarioRepository;
import br.brechosustentavel.repository.repositoryFactory.IVendedorInsigniaRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import br.brechosustentavel.service.SessaoUsuarioService;
import br.brechosustentavel.view.JanelaVisualizarPerfilView;
import java.awt.Dimension;
import java.util.List;
import java.util.Optional;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author kaila
 */
public class CarregarDadosPerfilCommand implements ICommandPerfil{
    
    @Override
    public void executar(JanelaVisualizarPerfilPresenter presenter, SessaoUsuarioService sessao) {
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        IUsuarioRepository usuarioRepo = fabrica.getUsuarioRepository();
        Optional<Usuario> usuarioOpt = usuarioRepo.buscarPorId(sessao.getUsuarioAutenticado().getId());
        Usuario usuario = usuarioOpt.get();
        if(usuario == null){
            throw new RuntimeException("Nenhum usuário autenticado na sessão.");
        }
        
        presenter.getView().getLblNome().setText(usuario.getNome());
        presenter.getView().getLblTelefone().setText(usuario.getTelefone());
        
        String perfil = sessao.getTipoPerfil();
        
        if(perfil.equalsIgnoreCase("Vendedor")){
            dadosVendedor(presenter.getView(), usuario.getVendedor().get(), fabrica);
        }
        else if(perfil.equalsIgnoreCase("Comprador")){
            dadosComprador(presenter.getView(), usuario.getComprador().get(), fabrica);
        }
        else {
            throw new RuntimeException("Não foi possível carregar dados para esse perfil.");
        }    
    }
    
    private void dadosComprador(JanelaVisualizarPerfilView view, Comprador comprador, RepositoryFactory fabrica){
        int totalDenuncias = fabrica.getDenunciaRepository().qtdDenunciasPorComprador(comprador.getId());
        int qtdDenunciasProcedentes = fabrica.getDenunciaRepository().qtdDenunciasProcedentesPorComprador(comprador.getId());
        double percentualProcedentes = (double) qtdDenunciasProcedentes / totalDenuncias; 
        
        view.getLblNivel().setText(String.valueOf(comprador.getNivel()));
        view.getLblEstrelas().setText(String.valueOf(comprador.getEstrelas()));
        view.getLblGWP().setText(String.valueOf(comprador.getGwpEvitado()));
        view.getTxtQtdTransacao().setVisible(true);
        view.getTxtQtdTransacao().setText(String.valueOf(comprador.getComprasFinalizadas()));

        if(percentualProcedentes >= 0){
            view.getLblDenuncias().setText(String.valueOf(percentualProcedentes));
        }
        else{
            view.getLblDenuncias().setText("0");
        }
        if(comprador.isSelo()){
            view.getLblSelo1().setText("Veficador Confiável");
        }

        view.getPanel().setPreferredSize(new Dimension(358, 407));
        JPanel painelInsignias = view.getPnlInsignias();
        painelInsignias.setPreferredSize(new Dimension(250, 340));
        painelInsignias.removeAll();

        ICompradorInsigniaRepository compradorInsigniaRepository = fabrica.getCompradorInsigniaRepository();
        List<Insignia> insignias = compradorInsigniaRepository.buscarInsigniaPorComprador(comprador.getId());

        painelInsignias.setLayout(new BoxLayout(painelInsignias, BoxLayout.Y_AXIS));

        for(Insignia insignia: insignias){
            JLabel label = new JLabel("• " + insignia.getNome()); 
            label.setFont(label.getFont().deriveFont(16f));
            label.setPreferredSize(new Dimension(150, 30));
            view.getPnlInsignias().add(label);
        }

        view.getPnlInsignias().revalidate();
        view.getPnlInsignias().repaint();
    }
    
     private void dadosVendedor(JanelaVisualizarPerfilView view, Vendedor vendedor, RepositoryFactory fabrica){
        view.getLblNivel().setText(String.valueOf(vendedor.getNivel()));
        view.getLblEstrelas().setText(String.valueOf(vendedor.getEstrelas()));
        view.getLblGWP().setText(String.valueOf(vendedor.getGwpContribuido()));
        view.getLblDenuncias().setVisible(false);
        view.getLblEstatisticasDenuncia().setVisible(false);
        view.getLblSelo1().setVisible(false);
        view.getLblTituloSelos().setVisible(false);
        view.getTxtQtdTransacao().setVisible(true);
        view.getTxtQtdTransacao().setText(String.valueOf(vendedor.getVendasConcluidas()));

        view.getPanel().setPreferredSize(new Dimension(358, 407));
        JPanel painelInsignias = view.getPnlInsignias();
        painelInsignias.setPreferredSize(new Dimension(250, 340));
        painelInsignias.removeAll();

        IVendedorInsigniaRepository vendedorInsigniaRepository = fabrica.getVendedorInsigniaRepository();
        List<Insignia> insignias = vendedorInsigniaRepository.buscarInsigniaPorVendedor(vendedor.getId());

        painelInsignias.setLayout(new BoxLayout(painelInsignias, BoxLayout.Y_AXIS));

        for(Insignia insignia: insignias){
            JLabel label = new JLabel("• " + insignia.getNome()); 
            label.setFont(label.getFont().deriveFont(16f));
            label.setPreferredSize(new Dimension(150, 30));
            view.getPnlInsignias().add(label);
        }

        view.getPnlInsignias().revalidate();
        view.getPnlInsignias().repaint();
    }  
}

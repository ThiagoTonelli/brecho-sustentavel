/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter;

import br.brechosustentavel.model.Comprador;
import br.brechosustentavel.model.Insignia;
import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.model.Vendedor;
import br.brechosustentavel.observer.Observador;
import br.brechosustentavel.repository.repositoryFactory.ICompradorInsigniaRepository;
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
public class JanelaVisualizarPerfilPresenter implements Observador{
    private TelaPrincipalPresenter presenter;
    private JanelaVisualizarPerfilView view;
    private SessaoUsuarioService sessao;
    
    public JanelaVisualizarPerfilPresenter(){
        presenter = new TelaPrincipalPresenter();
        sessao = SessaoUsuarioService.getInstancia();
        
        view = new JanelaVisualizarPerfilView();
        view.setVisible(false);
        
        try{
            Usuario usuario = sessao.getUsuarioAutenticado();
            view.getLblNome().setText(usuario.getNome());
            view.getLblTelefone().setText(usuario.getTelefone());
            
            RepositoryFactory fabrica = RepositoryFactory.getInstancia();
            if(sessao.getTipoPerfil().equalsIgnoreCase("Vendedor")){
                dadosVendedor(fabrica, usuario);
            }
            if(sessao.getTipoPerfil().equalsIgnoreCase("Comprador")){
                dadosComprador(fabrica, usuario);
            }            
        view.setVisible(true);
        } catch(Exception e){
            throw new RuntimeException("Não foi possível encontrador os dados do usuário: " + e.getMessage());
        }
    }
    
    private void dadosComprador(RepositoryFactory fabrica, Usuario usuario){
        Optional<Comprador> compradorOpt = fabrica.getCompradorRepository().buscarPorId(usuario.getId());
        int totalDenuncias = fabrica.getDenunciaRepository().qtdDenunciasPorComprador(usuario.getId());
        int qtdDenunciasProcedentes = fabrica.getDenunciaRepository().qtdDenunciasProcedentesPorComprador(usuario.getId());
        double percentualProcedentes = (double) qtdDenunciasProcedentes / totalDenuncias; 
        
        compradorOpt.ifPresent(comprador -> {
            view.getLblNivel().setText(String.valueOf(comprador.getNivel()));
            view.getLblEstrelas().setText(String.valueOf(comprador.getEstrelas()));
            view.getLblGWP().setText(String.valueOf(comprador.getGwpEvitado()));
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
            List<Insignia> insignias = compradorInsigniaRepository.buscarInsigniaPorComprador(usuario.getId());

            painelInsignias.setLayout(new BoxLayout(painelInsignias, BoxLayout.Y_AXIS));

            for(Insignia insignia: insignias){
                JLabel label = new JLabel("• " + insignia.getNome()); 
                label.setFont(label.getFont().deriveFont(16f));
                label.setPreferredSize(new Dimension(150, 30));
                view.getPnlInsignias().add(label);
            }

            view.getPnlInsignias().revalidate();
            view.getPnlInsignias().repaint();
        });
    }
    
    private void dadosVendedor(RepositoryFactory fabrica, Usuario usuario){
        Optional<Vendedor> vendedorOpt = fabrica.getVendedorRepository().buscarPorId(usuario.getId());
        vendedorOpt.ifPresent(vendedor -> {
            view.getLblNivel().setText(String.valueOf(vendedor.getNivel()));
            view.getLblEstrelas().setText(String.valueOf(vendedor.getEstrelas()));
            view.getLblGWP().setText(String.valueOf(vendedor.getGwpContribuido()));
            view.getLblDenuncias().setVisible(false);
            view.getLblEstatisticasDenuncia().setVisible(false);
            view.getLblSelo1().setVisible(false);
            view.getLblTituloSelos().setVisible(false);
            
            view.getPanel().setPreferredSize(new Dimension(358, 407));
            JPanel painelInsignias = view.getPnlInsignias();
            painelInsignias.setPreferredSize(new Dimension(250, 340));
            painelInsignias.removeAll();

            IVendedorInsigniaRepository vendedorInsigniaRepository = fabrica.getVendedorInsigniaRepository();
            List<Insignia> insignias = vendedorInsigniaRepository.buscarInsigniaPorVendedor(usuario.getId());

            painelInsignias.setLayout(new BoxLayout(painelInsignias, BoxLayout.Y_AXIS));

            for(Insignia insignia: insignias){
                JLabel label = new JLabel("• " + insignia.getNome()); 
                label.setFont(label.getFont().deriveFont(16f));
                label.setPreferredSize(new Dimension(150, 30));
                view.getPnlInsignias().add(label);
            }

            view.getPnlInsignias().revalidate();
            view.getPnlInsignias().repaint();       
        });
    }
    
    public JanelaVisualizarPerfilView getView(){
        return view;
    }

    @Override
    public void atualizar() {
        
    }
}

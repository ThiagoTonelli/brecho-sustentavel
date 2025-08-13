package br.brechosustentavel.command;

import br.brechosustentavel.model.Peca;
import br.brechosustentavel.presenter.ManterAnuncioPresenter;
import br.brechosustentavel.repository.IMaterialRepository;
import br.brechosustentavel.repository.IPecaRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.service.gerador_id_c.GeradorIdService;
import br.brechosustentavel.view.IJanelaInclusaoAnuncioView;
import java.awt.Component;
import javax.swing.JCheckBox;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author thiag
 */
public class NovoAnuncioCommand implements ICommand {
    
    @Override
    public void executar(ManterAnuncioPresenter presenter) {
        try {
            RepositoryFactory fabrica = RepositoryFactory.getInstancia();
            IJanelaInclusaoAnuncioView view = presenter.getView();

            String id_c = view.getTxtId_c().getText();
            String tipoPeca = (String) view.getSelectTipoDePeca().getSelectedItem();
            String subcategoria = (String) view.getSelectSubcategoria().getSelectedItem();
            String tamanho = (String) view.getSelectTamanho().getSelectedItem(); // Tamanho é geralmente texto (ex: "M", "42")
            String cor = view.getTxtCor().getText();

            Map<String, Integer> materiaisQuantidade = new HashMap<>();

            materiaisQuantidade.put((String) view.getSelectComposicao().getSelectedItem(), (int) view.getSpinnerComposicao().getValue());
            materiaisQuantidade.put((String) view.getSelectComposicao1().getSelectedItem() , (int) view.getSpinnerComposicao1().getValue());
            materiaisQuantidade.put((String) view.getSelectComposicao2().getSelectedItem(), (int) view.getSpinnerComposicao2().getValue());
            
            double massaEstimada = Double.parseDouble(view.getTxtMassa().getText());
            String estadoDeConservacao = view.getTxtEstadoConservacao().getText();
            double precoBase = Double.parseDouble(view.getTxtPrecoBase().getText());
            
            
            //cria repositorio dos materiais
            IMaterialRepository repositoryMaterial = fabrica.getMaterialRepository();   
            //limpa materiais com quantidade na composicao = 0
            materiaisQuantidade.entrySet().removeIf(entry -> entry.getValue() <= 0);
            Set<String> chaves = materiaisQuantidade.keySet();
            List<String> listaChaves = new ArrayList<>(chaves);
            //pega apenas os materiais que foram realmente selecionados
            Map<String, Double> materiaisDesconto = repositoryMaterial.buscarMateriaisNome(listaChaves);
            
            
            Map<String, Double> defeitosSelecionados = new HashMap<>();
            for (Component comp : view.getPainelScrollDefeitos().getComponents()) {
                if (comp instanceof JCheckBox) {
                    JCheckBox checkBox = (JCheckBox) comp;
                    if (checkBox.isSelected()) {
                        double desconto = (double) checkBox.getClientProperty("desconto");
                        defeitosSelecionados.put(checkBox.getText(), desconto);
                    }
                }
            }
            System.out.println(defeitosSelecionados);
            
            


            
            IPecaRepository repository = fabrica.getPecaRepository();
            
            if(id_c == null || id_c.trim().isEmpty()){
                //chama o service para id_c
                GeradorIdService gerador = new GeradorIdService();
                String id = gerador.Gerar();
                while(repository.consultarId_c(id)){
                    id = gerador.Gerar();
                }
                id_c = id;
            }
            
            Peca peca = new Peca(id_c, tipoPeca , subcategoria, tamanho, cor, massaEstimada, estadoDeConservacao, precoBase,  defeitosSelecionados, materiaisDesconto, materiaisQuantidade);
            
            
            System.out.println("--- ANÚNCIO A SER SALVO ---");
            System.out.println("ID-C: " + id_c);
            System.out.println("Tipo: " + tipoPeca);
            System.out.println("Preço Base: " + precoBase);
            System.out.println("Defeitos Selecionados: " + defeitosSelecionados.size());


            // --- 4. Persistência dos Dados ---
            // RepositoryFactory fabrica = RepositoryFactory.getRepositoryFactory();
            // IPecaRepository itemRepository = fabrica.getItemRepository();
            // itemRepository.criar(novaPeca); // Você precisará implementar este método no seu repositório

        } catch (NumberFormatException e) {
            throw new RuntimeException("Erro de formato numérico. Verifique se os campos de preço, massa, etc., estão preenchidos corretamente.", e);
        } catch (Exception ex) {
            // Lança a exceção para que a camada de apresentação (State) a possa capturar e mostrar ao utilizador
            throw new RuntimeException("Ocorreu um erro ao criar o anúncio: " + ex.getMessage(), ex);
        }
    }
}
package br.brechosustentavel.command;

import br.brechosustentavel.model.Defeito;
import br.brechosustentavel.model.Peca;
import br.brechosustentavel.presenter.ManterAnuncioPresenter;
import br.brechosustentavel.repository.IItemRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.view.IJanelaInclusaoAnuncioView;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.JCheckBox;

/**
 *
 * @author thiag
 */
public class NovoAnuncioCommand implements ICommand {

    @Override
    public void executar(ManterAnuncioPresenter presenter) {
        try {
            IJanelaInclusaoAnuncioView view = presenter.getView();

            String id_c = view.getTxtId_c().getText();
            String tipoPeca = (String) view.getSelectTipoDePeca().getSelectedItem();
            String subcategoria = (String) view.getSelectSubcategoria().getSelectedItem();
            String tamanho = (String) view.getSelectTamanho().getSelectedItem(); // Tamanho é geralmente texto (ex: "M", "42")
            String cor = view.getTxtCor().getText();
            String composicao = (String) view.getSelectComposicao().getSelectedItem();
            int composicaoPorcentagem = (int) view.getSpinnerComposicao().getValue();
            String composicao1 = (String) view.getSelectComposicao1().getSelectedItem();
            int composicao1Porcentagem = (int) view.getSpinnerComposicao1().getValue();
            String composicao2 = (String) view.getSelectComposicao2().getSelectedItem();
            int composicao2Porcentagem = (int) view.getSpinnerComposicao2().getValue();
            
            double massa = Double.parseDouble(view.getTxtMassa().getText());
            String estadoConservacao = view.getTxtEstadoConservacao().getText();
            double precoBase = Double.parseDouble(view.getTxtPrecoBase().getText());

            // obter defeitos selecionados
            List<String> defeitosSelecionados = new ArrayList<>();
            for (Component comp : view.getPainelScrollDefeitos().getComponents()) {
                if (comp instanceof JCheckBox) {
                    JCheckBox checkBox = (JCheckBox) comp;
                    if (checkBox.isSelected()) {
                        // Aqui você pode criar um objeto Defeito simples apenas com o nome
                        // O ideal seria buscar o objeto completo no banco de dados se precisar do percentual
                        defeitosSelecionados.add(checkBox.getText());
                    }
                }
            }
            System.out.println(defeitosSelecionados);

            // --- 3. Criação do Objeto do Modelo ---
            // Nota: O construtor do seu modelo Peca pode precisar de ajustes para aceitar os tipos corretos.
            // Por enquanto, vou assumir um construtor que aceita os dados que temos.
            // O ideal seria passar uma lista de objetos Material em vez de uma String de composição.
            
            // Peca novaPeca = new Peca(tipoPeca, subcategoria, tamanho, massa, estadoConservacao, precoBase, defeitosSelecionados, null);
            if(id_c == null || id_c.trim().isEmpty()){
                //chama o service para id_c
                id_c = "teste da silva";
            }

            System.out.println("--- ANÚNCIO A SER SALVO ---");
            System.out.println("ID-C: " + id_c);
            System.out.println("Tipo: " + tipoPeca);
            System.out.println("Preço Base: " + precoBase);
            System.out.println("Defeitos Selecionados: " + defeitosSelecionados.size());


            // --- 4. Persistência dos Dados ---
            // RepositoryFactory fabrica = RepositoryFactory.getRepositoryFactory();
            // IItemRepository itemRepository = fabrica.getItemRepository();
            // itemRepository.criar(novaPeca); // Você precisará implementar este método no seu repositório

        } catch (NumberFormatException e) {
            throw new RuntimeException("Erro de formato numérico. Verifique se os campos de preço, massa, etc., estão preenchidos corretamente.", e);
        } catch (Exception ex) {
            // Lança a exceção para que a camada de apresentação (State) a possa capturar e mostrar ao utilizador
            throw new RuntimeException("Ocorreu um erro ao criar o anúncio: " + ex.getMessage(), ex);
        }
    }
}
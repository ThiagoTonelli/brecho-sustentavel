package br.brechosustentavel.command;

import br.brechosustentavel.model.EventoLinhaDoTempo;
import br.brechosustentavel.model.Peca;
import br.brechosustentavel.presenter.VendedorPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.repository.IMaterialRepository;
import br.brechosustentavel.repository.IPecaRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.service.AplicarDescontosDefeitosService;
import br.brechosustentavel.service.CalculadoraDeIndicesService;
import java.awt.Component;
import javax.swing.JCheckBox;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import br.brechosustentavel.view.IJanelaManterAnuncioView;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 *
 * @author thiag
 */
public class NovoAnuncioCommand implements ICommand {
    
    @Override
    public void executar(ManterAnuncioPresenter presenter) {
        try {
            RepositoryFactory fabrica = RepositoryFactory.getInstancia();
            IPecaRepository repository = fabrica.getPecaRepository();
            IJanelaManterAnuncioView view = presenter.getView();
            
            //captura valores da presenter
            String id_c = view.getTxtId_c().getText();
            String tipoPeca = (String) view.getSelectTipoDePeca().getSelectedItem();
            String subcategoria = view.getTxtSubcategoria().getText();
            String tamanho = (String) view.getSelectTamanho().getSelectedItem();
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
            
            //captura quais defeitos foram selecionados para a peca
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
            Optional<Peca> peca = repository.consultar(id_c); 
            if(peca.isEmpty()){
                Peca novaPeca = new Peca(id_c, subcategoria, tamanho, cor, massaEstimada, estadoDeConservacao, precoBase);
                novaPeca.setTipoDePeca(tipoPeca);
                novaPeca.setDefeitos(defeitosSelecionados);
                novaPeca.setMaterialDesconto(materiaisDesconto);
                novaPeca.setMaterialQuantidade(materiaisQuantidade);
                //calcula metricas e preco final
                AplicarDescontosDefeitosService aplicarDescontos = new AplicarDescontosDefeitosService();
                novaPeca.setPrecoFinal(aplicarDescontos.calcularDescontos(novaPeca));
                CalculadoraDeIndicesService calcularIndices = new CalculadoraDeIndicesService();
                double gwpAvoided = calcularIndices.calcularGwpAvoided(novaPeca);
                double gwpBase = calcularIndices.calcularGwpBase(novaPeca);
                double mciPeca = calcularIndices.calcularMCI(novaPeca);
                LocalDateTime data = LocalDateTime.now();
                EventoLinhaDoTempo evento = new EventoLinhaDoTempo(novaPeca, "publicação", data, gwpAvoided, mciPeca);
                
                
                /*
                System.out.println("--- ANÚNCIO A SER SALVO ---");
                System.out.println("ID-C: " + id_c);
                System.out.println("Tipo: " + tipoPeca);
                System.out.println("Preço Base: " + precoBase);
                System.out.println("Defeitos Selecionados: " + defeitosSelecionados.size());*/
            }

        } catch (NumberFormatException e) {
            throw new RuntimeException("Erro de formato numérico. Verifique se os campos de preço, massa, etc., estão preenchidos corretamente.", e);
        } catch (Exception ex) {
            throw new RuntimeException("Ocorreu um erro ao criar o anúncio: " + ex.getMessage(), ex);
        }
    }
}
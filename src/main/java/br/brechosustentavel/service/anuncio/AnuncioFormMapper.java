/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service.anuncio;

import br.brechosustentavel.model.Peca;
import br.brechosustentavel.repository.IComposicaoRepository;
import br.brechosustentavel.repository.ITipoDePecaRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import br.brechosustentavel.view.IJanelaManterAnuncioView;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JCheckBox;

/**
 *
 * @author thiag
 */
public class AnuncioFormMapper {

    public static Peca extrairDadosDaView(IJanelaManterAnuncioView view) {

        String id_c = view.getTxtId_c().getText();
        String subcategoria = view.getTxtSubcategoria().getText();
        String tamanho = view.getTxtTamanho().getText();
        String cor = view.getTxtCor().getText();
        String estadoDeConservacao = view.getTxtEstadoConservacao().getText();
        String tipoPecaNome = (String) view.getSelectTipoDePeca().getSelectedItem();

        double massaEstimada;
        double precoBase;
        try {
            massaEstimada = Double.parseDouble(view.getTxtMassa().getText().replace(",", "."));
            precoBase = Double.parseDouble(view.getTxtPrecoBase().getText().replace(",", "."));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Certifique-se de que os campos 'Massa' e 'Preço-base' contêm números válidos.");
        }
        

        Peca peca = new Peca(id_c, subcategoria, tamanho, cor, massaEstimada, estadoDeConservacao, precoBase);

        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        
        ITipoDePecaRepository tipoPecaRepository = fabrica.getTipoDePecaRepository();
        int idTipo = tipoPecaRepository.buscarIdTipo(tipoPecaNome);
        peca.setTipoDePeca(tipoPecaNome);
        peca.setIdTipoDePeca(idTipo);

        peca.setDefeitos(extrairDefeitos(view));
        
        Map<String, Double> materiaisQuantidade = extrairMateriaisQuantidade(view);
        peca.setMaterialQuantidade(materiaisQuantidade);

        
        IComposicaoRepository composicaoRepository = fabrica.getComposicaoRepository();
        List<String> nomesMateriais = new ArrayList<>(materiaisQuantidade.keySet());
        Map<String, Double> materiaisDesconto = composicaoRepository.buscarMateriaisNome(nomesMateriais);
        peca.setMaterialDesconto(materiaisDesconto);

        return peca;
    }

    private static Map<String, Double> extrairDefeitos(IJanelaManterAnuncioView view) {
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
        return defeitosSelecionados;
    }


    private static Map<String, Double> extrairMateriaisQuantidade(IJanelaManterAnuncioView view) {
        Map<String, Double> materiaisQuantidade = new HashMap<>();

        int quantidade1 = (int) view.getSpinnerComposicao().getValue();
        if (quantidade1 > 0) {
            String material1 = (String) view.getSelectComposicao().getSelectedItem();
            materiaisQuantidade.put(material1, (double) quantidade1);
        }

        int quantidade2 = (int) view.getSpinnerComposicao1().getValue();
        if (quantidade2 > 0) {
            String material2 = (String) view.getSelectComposicao1().getSelectedItem();
            materiaisQuantidade.put(material2, (double) quantidade2);
        }

        int quantidade3 = (int) view.getSpinnerComposicao2().getValue();
        if (quantidade3 > 0) {
            String material3 = (String) view.getSelectComposicao2().getSelectedItem();
            materiaisQuantidade.put(material3, (double) quantidade3);
        }

        return materiaisQuantidade;
    }
}

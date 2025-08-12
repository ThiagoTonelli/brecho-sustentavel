/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command;

import br.brechosustentavel.presenter.ManterAnuncioPresenter;
import br.brechosustentavel.repository.IMaterialRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import static br.brechosustentavel.repository.RepositoryFactory.getRepositoryFactory;
import br.brechosustentavel.view.IJanelaInclusaoAnuncioView;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JSpinner;

/**
 *
 * @author thiag
 */
public class CarregarComposicaoCommand implements ICommand{
    
    @Override
    public void executar(ManterAnuncioPresenter presenter) {
        IJanelaInclusaoAnuncioView view = presenter.getView();
        JComboBox jcomboComposicao = view.getSelectTipoDePeca();
        JComboBox jcomboComposicao1 = view.getSelectTipoDePeca();
        JComboBox jcomboComposicao2 = view.getSelectTipoDePeca();
        jcomboComposicao.removeAllItems();
        jcomboComposicao1.removeAllItems();
        jcomboComposicao2.removeAllItems();
        JSpinner spinner1 = view.getSpinnerComposicao();
        JSpinner spinner2 = view.getSpinnerComposicao1();
        JSpinner spinner3 = view.getSpinnerComposicao2();
        spinner1.setValue("");
        spinner2.setValue("");
        spinner3.setValue("");
        
        RepositoryFactory fabrica = getRepositoryFactory();
        IMaterialRepository repository = fabrica.getMaterialRepository();
        Map<String, Double> tiposMateriais = repository.buscarMateriais();
        for (String nome : tiposMateriais.keySet()) {
            jcomboComposicao.addItem(nome);
            jcomboComposicao1.addItem(nome);
            jcomboComposicao2.addItem(nome);
        }
        jcomboComposicao.revalidate();
        jcomboComposicao.repaint();
        jcomboComposicao1.revalidate();
        jcomboComposicao1.repaint();
        jcomboComposicao2.revalidate();
        jcomboComposicao2.repaint();
        
        spinner1.addChangeListener(e -> atualizarEstado(spinner1, spinner2, spinner3, jcomboComposicao, jcomboComposicao1, jcomboComposicao2));
        spinner2.addChangeListener(e -> atualizarEstado(spinner1, spinner2, spinner3, jcomboComposicao, jcomboComposicao1, jcomboComposicao2));
        spinner3.addChangeListener(e -> atualizarEstado(spinner1, spinner2, spinner3, jcomboComposicao, jcomboComposicao1, jcomboComposicao2));

    } 
    
    private void atualizarEstado(JSpinner spinner1, JSpinner spinner2, JSpinner spinner3, JComboBox jcomboComposicao, JComboBox jcomboComposicao1, JComboBox jcomboComposicao2){
        int valor1 = (int ) spinner1.getValue();
        int valor2 = (int ) spinner2.getValue();
        int valor3 = (int ) spinner3.getValue();
        
        int soma = valor1+ valor2+ valor3;
        
        boolean bloqueia1 = (valor1 == 0 && soma >= 100);
        boolean bloqueia2 = (valor2 == 0 && soma >= 100);
        boolean bloqueia3 = (valor3 == 0 && soma >= 100);
        
        jcomboComposicao.setEnabled(!bloqueia1);
        spinner1.setEnabled(!bloqueia1);
        
        jcomboComposicao1.setEnabled(!bloqueia2);
        spinner2.setEnabled(!bloqueia2);
        
        jcomboComposicao2.setEnabled(!bloqueia3);
        spinner3.setEnabled(!bloqueia3);
    }
    
    
}

    
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.commandVendedor;

import br.brechosustentavel.presenter.ManterAnuncioPresenter.ManterAnuncioPresenter;
import br.brechosustentavel.repository.IMaterialRepository;
import br.brechosustentavel.repository.RepositoryFactory;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import br.brechosustentavel.view.IJanelaManterAnuncioView;

/**
 *
 * @author thiag
 */
public class CarregarComposicaoCommand implements ICommandVendedor{
    
    @Override
    public Object executar(ManterAnuncioPresenter presenter) {
        IJanelaManterAnuncioView view = presenter.getView();
        JComboBox jcomboComposicao = view.getSelectComposicao();
        JComboBox jcomboComposicao1 = view.getSelectComposicao1();
        JComboBox jcomboComposicao2 = view.getSelectComposicao2();
        jcomboComposicao.removeAllItems();
        jcomboComposicao1.removeAllItems();
        jcomboComposicao2.removeAllItems();
        JSpinner spinner1 = view.getSpinnerComposicao();
        JSpinner spinner2 = view.getSpinnerComposicao1();
        JSpinner spinner3 = view.getSpinnerComposicao2();
        spinner1.setValue(0);
        spinner2.setValue(0);
        spinner3.setValue(0);
        
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        IMaterialRepository repository = fabrica.getMaterialRepository();
        Map<String, Double> tiposMateriais = repository.buscarMateriais();
        System.out.println(tiposMateriais);
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
        
        spinner1.addChangeListener(e -> atualizarEstado(spinner1, spinner2, spinner3, jcomboComposicao, jcomboComposicao1, jcomboComposicao2, spinner1));
        spinner2.addChangeListener(e -> atualizarEstado(spinner1, spinner2, spinner3, jcomboComposicao, jcomboComposicao1, jcomboComposicao2, spinner2));
        spinner3.addChangeListener(e -> atualizarEstado(spinner1, spinner2, spinner3, jcomboComposicao, jcomboComposicao1, jcomboComposicao2, spinner3));
        //atualizarEstado(spinner1, spinner2, spinner3, jcomboComposicao, jcomboComposicao1, jcomboComposicao2, spinner1);
        return null;
    } 
    
    private void atualizarEstado(
        JSpinner spinner1, JSpinner spinner2, JSpinner spinner3,
        JComboBox jcomboComposicao, JComboBox jcomboComposicao1, JComboBox jcomboComposicao2,
        JSpinner spinnerAlterado
    ) {
        int valor1 = (int) spinner1.getValue();
        int valor2 = (int) spinner2.getValue();
        int valor3 = (int) spinner3.getValue();

        int soma = valor1 + valor2 + valor3;

        if (soma > 100) {
            int excesso = soma - 100;
            int valorAtual = (int) spinnerAlterado.getValue();
            spinnerAlterado.setValue(valorAtual - excesso);
            valor1 = (int) spinner1.getValue();
            valor2 = (int) spinner2.getValue();
            valor3 = (int) spinner3.getValue();
            soma = valor1 + valor2 + valor3;
        }

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

    
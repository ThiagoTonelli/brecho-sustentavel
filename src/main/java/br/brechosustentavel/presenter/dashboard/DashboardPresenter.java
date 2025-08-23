/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.dashboard;

import br.brechosustentavel.commandDashboard.GerarGraficoGWPCommand;
import br.brechosustentavel.commandDashboard.GerarGraficoMaterialCommand;
import br.brechosustentavel.commandDashboard.GerarGraficoRankingCommand;
import br.brechosustentavel.commandDashboard.GerarGraficoReputacaoCommand;
import br.brechosustentavel.view.DashboardView;
import javax.swing.JOptionPane;

/**
 *
 * @author thiag
 */
public class DashboardPresenter {

    private DashboardView view;

    public DashboardPresenter() {
        this.view = new DashboardView();
        
        // Delega a criação de cada gráfico para o seu respectivo Command.
        carregarGraficos();
    }
    
    private void carregarGraficos() {
        try {
            new GerarGraficoReputacaoCommand().executar(this);
            new GerarGraficoGWPCommand().executar(this);
            new GerarGraficoMaterialCommand().executar(this);
            new GerarGraficoRankingCommand().executar(this);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Erro ao Carregar Dashboard", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public DashboardView getView() {
        return view;
    }
}

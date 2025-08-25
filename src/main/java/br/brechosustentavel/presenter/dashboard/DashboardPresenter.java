/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.presenter.dashboard;

import br.brechosustentavel.command.commandDashboard.GerarGraficoGWPCommand;
import br.brechosustentavel.command.commandDashboard.GerarGraficoMaterialCommand;
import br.brechosustentavel.command.commandDashboard.GerarGraficoRankingCommand;
import br.brechosustentavel.command.commandDashboard.GerarGraficoReputacaoCommand;
import br.brechosustentavel.view.JanelaDashboardView;
import javax.swing.JOptionPane;

/**
 *
 * @author thiag
 */
public class DashboardPresenter {

    private JanelaDashboardView view;

    public DashboardPresenter() {
        this.view = new JanelaDashboardView();
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
    
    public JanelaDashboardView getView() {
        return view;
    }
}

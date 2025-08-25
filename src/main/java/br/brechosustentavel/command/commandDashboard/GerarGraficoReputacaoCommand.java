/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.command.commandDashboard;

import br.brechosustentavel.presenter.dashboard.DashboardPresenter;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import br.brechosustentavel.service.dashboard.DashboardService;
import java.awt.BorderLayout;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author thiag
 */
public class GerarGraficoReputacaoCommand implements ICommandDashboard {

    @Override
    public Object executar(DashboardPresenter presenter) {
        try {
            RepositoryFactory fabrica = RepositoryFactory.getInstancia();
            DashboardService dashboardService = new DashboardService(fabrica);

            Map<String, Integer> dadosReputacao = dashboardService.getDadosReputacao();

            DefaultPieDataset dataset = new DefaultPieDataset();
            if (dadosReputacao.isEmpty()) {
                dataset.setValue("Nenhum dado encontrado", 100);
            } else {
                dadosReputacao.forEach((nivel, total) -> {
                    dataset.setValue(nivel + " (" + total + ")", total);
                });
            }

            JFreeChart pieChart = ChartFactory.createPieChart(
                "Distribuição de Reputação (Total)", 
                dataset,                             
                true,                                
                true,
                false);

            ChartPanel chartPanel = new ChartPanel(pieChart);
            presenter.getView().getPanelReputacao().setLayout(new BorderLayout());
            presenter.getView().getPanelReputacao().add(chartPanel, BorderLayout.CENTER);
            presenter.getView().getPanelReputacao().revalidate(); 
            
            return pieChart;

        } catch (Exception e) {
            throw new RuntimeException("Falha ao gerar o gráfico de reputação: " + e.getMessage(), e);
        }
    }
}

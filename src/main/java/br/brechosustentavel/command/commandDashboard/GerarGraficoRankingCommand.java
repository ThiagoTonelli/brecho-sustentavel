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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author thiag
 */
public class GerarGraficoRankingCommand implements ICommandDashboard {

    @Override
    public Object executar(DashboardPresenter presenter) {
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        DashboardService dashboardService = new DashboardService(fabrica);

        Map<String, Double> dadosRanking = dashboardService.getDadosRankingVendedores();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (dadosRanking.isEmpty()) {
            dataset.setValue(0, "GWP", "Nenhum vendedor com pontuação");
        } else {
            dadosRanking.forEach((nomeVendedor, gwpContribuido) -> {
                dataset.setValue(gwpContribuido, "GWP Contribuído", nomeVendedor);
            });
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Top 5 Vendedores por GWP Contribuído", 
                "Vendedor",                          
                "GWP Contribuído",                     
                dataset,                                
                PlotOrientation.VERTICAL,
                false, true, false);                  

        ChartPanel chartPanel = new ChartPanel(barChart);
        presenter.getView().getPanelRanking().setLayout(new BorderLayout());
        presenter.getView().getPanelRanking().add(chartPanel, BorderLayout.CENTER);
        presenter.getView().getPanelRanking().revalidate();

        return barChart;
    }
}

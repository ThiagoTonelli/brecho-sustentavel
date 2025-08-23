/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.commandDashboard;

import br.brechosustentavel.presenter.dashboard.DashboardPresenter;
import br.brechosustentavel.repository.RepositoryFactory;
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
public class GerarGraficoGWPCommand implements ICommandDashboard{

    @Override
    public Object executar(DashboardPresenter presenter) {
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        DashboardService dashboardService = new DashboardService(fabrica);

        Map<String, Double> dadosGWP = dashboardService.getDadosEvolucaoGWP();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (dadosGWP.isEmpty()) {
            dataset.setValue(0, "GWP Evitado", "Nenhuma Semana");
        } else {
            dadosGWP.forEach((semana, totalGWP) -> {
                dataset.setValue(totalGWP, "GWP Evitado (kg CO2 eq.)", semana);
            });
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
            "Evolução Semanal de GWP Evitado",
            "Semana", "Total GWP Evitado",
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        presenter.getView().getPanelGwp().setLayout(new BorderLayout());
        presenter.getView().getPanelGwp().add(chartPanel, BorderLayout.CENTER);
        presenter.getView().getPanelGwp().revalidate();

        return lineChart;
    }
}


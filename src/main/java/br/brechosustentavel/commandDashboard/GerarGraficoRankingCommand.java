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
public class GerarGraficoRankingCommand implements ICommandDashboard {

    @Override
    public Object executar(DashboardPresenter presenter) {
        // 1. Inicializa o serviço
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        DashboardService dashboardService = new DashboardService(fabrica);

        // 2. Busca os dados do ranking
        //Map<String, Double> dadosRanking = dashboardService.getDadosRankingVendedores();

        // 3. Prepara o dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //if (dadosRanking.isEmpty()) {
         //   dataset.setValue(0, "GWP", "Nenhum vendedor");
        //} else {
          //  dadosRanking.forEach((nomeVendedor, gwpContribuido) -> {
          //      dataset.setValue(gwpContribuido, "GWP Contribuído", nomeVendedor);
          //  });
      //  }

        // 4. Cria o gráfico
        JFreeChart barChart = ChartFactory.createBarChart(
            "Top 5 Vendedores por GWP Contribuído",
            "Vendedor",
            "GWP Contribuído",
            dataset,
            PlotOrientation.VERTICAL,
            false, true, false); // Legenda é desnecessária aqui

        // 5. Adiciona o gráfico ao painel
        ChartPanel chartPanel = new ChartPanel(barChart);
        //presenter.getView().getRankingPanel().setLayout(new BorderLayout());
        //presenter.getView().getRankingPanel().add(chartPanel, BorderLayout.CENTER);
        //presenter.getView().getRankingPanel().revalidate();

        return barChart;
    }
}

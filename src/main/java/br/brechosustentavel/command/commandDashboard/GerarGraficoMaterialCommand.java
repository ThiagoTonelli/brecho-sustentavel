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
public class GerarGraficoMaterialCommand implements ICommandDashboard {

    @Override
    public Object executar(DashboardPresenter presenter) {
        RepositoryFactory fabrica = RepositoryFactory.getInstancia();
        DashboardService dashboardService = new DashboardService(fabrica);

        Map<String, Double> dadosMateriais = dashboardService.getDadosParticipacaoMateriais();

        DefaultPieDataset dataset = new DefaultPieDataset();
        if (dadosMateriais.isEmpty()) {
            dataset.setValue("Nenhum material cadastrado", 100);
        } else {
            dadosMateriais.forEach((material, massaTotal) -> {
                dataset.setValue(String.format("%s (%.2f g)", material, massaTotal), massaTotal);
            });
        }

        JFreeChart pieChart = ChartFactory.createPieChart(
            "Participação de Materiais (Massa Total)",
            dataset,
            true, true, false);

        ChartPanel chartPanel = new ChartPanel(pieChart);
        presenter.getView().getPanelMateriais().setLayout(new BorderLayout());
        presenter.getView().getPanelMateriais().add(chartPanel, BorderLayout.CENTER);
        presenter.getView().getPanelMateriais().revalidate();

        return pieChart;
    }
}

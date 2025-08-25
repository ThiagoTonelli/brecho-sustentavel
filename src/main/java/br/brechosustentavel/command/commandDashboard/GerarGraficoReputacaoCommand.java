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

            // 3. Prepara o conjunto de dados (dataset) para o JFreeChart.
            DefaultPieDataset dataset = new DefaultPieDataset();
            if (dadosReputacao.isEmpty()) {
                dataset.setValue("Nenhum dado encontrado", 100);
            } else {
                dadosReputacao.forEach((nivel, total) -> {
                    dataset.setValue(nivel + " (" + total + ")", total);
                });
            }

            // 4. Cria o objeto do gráfico de pizza (Pie Chart).
            JFreeChart pieChart = ChartFactory.createPieChart(
                "Distribuição de Reputação (Total)", // Título do gráfico
                dataset,                             // Dados
                true,                                // Exibir legenda
                true,
                false);

            // 5. Adiciona o gráfico ao painel correto na View, através do Presenter.
            ChartPanel chartPanel = new ChartPanel(pieChart);
            presenter.getView().getPanelReputacao().setLayout(new BorderLayout());
            presenter.getView().getPanelReputacao().add(chartPanel, BorderLayout.CENTER);
            presenter.getView().getPanelReputacao().revalidate(); // Garante que a UI seja atualizada
            
            return pieChart; // Retorna o objeto do gráfico criado

        } catch (Exception e) {
            // Lança uma exceção para ser tratada pela camada de apresentação (Presenter).
            throw new RuntimeException("Falha ao gerar o gráfico de reputação: " + e.getMessage(), e);
        }
    }
}

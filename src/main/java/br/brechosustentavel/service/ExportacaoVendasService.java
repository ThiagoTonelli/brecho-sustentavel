/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service;

import br.brechosustentavel.repository.repositoryFactory.ITransacaoRepository;
import br.brechosustentavel.repository.repositoryFactory.RepositoryFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thiag
 */
public class ExportacaoVendasService {

    private final ITransacaoRepository transacaoRepository;

    public ExportacaoVendasService() {
        this.transacaoRepository = RepositoryFactory.getInstancia().getTransacaoRepository();
    }

    public void exportarVendasParaCsv(File ficheiro) throws IOException {
        List<Map<String, Object>> dados = transacaoRepository.buscarDadosParaExportacao();
        
        if (dados.isEmpty()) {
            throw new IOException("Não há dados de vendas para exportar.");
        }

        try (FileWriter csvWriter = new FileWriter(ficheiro)) {
            csvWriter.append("ID;ID_C;Ciclo;Data;Massa;GWP_base;GWP_avoided;MCI_item\n");

            for (Map<String, Object> linha : dados) {
                csvWriter.append(String.join(";",
                        linha.get("ID").toString(),
                        linha.get("ID_C").toString(),
                        linha.get("Ciclo").toString(),
                        linha.get("Data").toString(),
                        linha.get("Massa").toString(),
                        linha.get("GWP_base").toString(),
                        linha.get("GWP_avoided").toString(),
                        linha.get("MCI_item").toString()
                ));
                csvWriter.append("\n");
            }
        }
    }
}

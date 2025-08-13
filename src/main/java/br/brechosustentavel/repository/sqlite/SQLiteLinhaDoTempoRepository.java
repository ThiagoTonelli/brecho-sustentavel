/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.EventoLinhaDoTempo;
import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.ILinhaDoTempoRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 *
 * @author thiag
 */
public class SQLiteLinhaDoTempoRepository implements ILinhaDoTempoRepository{
    private final ConexaoFactory conexaoFactory;
    
    public SQLiteLinhaDoTempoRepository(ConexaoFactory conexaoFactory) {
        this.conexaoFactory = conexaoFactory;
    }
    
    @Override
    public Optional<EventoLinhaDoTempo> ultimoEvento(String id_c) {
        // A consulta SQL busca o último evento (mais recente) para um 'id_peca',
        // ordenando pela data em ordem decrescente e limitando a 1 resultado.
        String sql = "SELECT * FROM evento_linha_tempo WHERE id_peca = ? ORDER BY data DESC LIMIT 1";

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, id_c);

            try (ResultSet rs = pstmt.executeQuery()) {
                // Verifica se a consulta retornou um registro.
                if (rs.next()) {
                    // Extrai os dados da linha atual do ResultSet.
                    int id = rs.getInt("id");
                    String idPeca = rs.getString("id_peca");
                    String descricao = rs.getString("descricao");
                    int cicloN = rs.getInt("ciclo_n");
                    String tipoEvento = rs.getString("tipo_evento");
                    // Converte o Timestamp do SQL para LocalDateTime do Java.
                    LocalDateTime data = rs.getTimestamp("data").toLocalDateTime();
                    double gwpEvitado = rs.getDouble("gwp_evitado");
                    double mciPeca = rs.getDouble("mci_peca");

                    // Cria o objeto EventoLinhaDoTempo.
                    EventoLinhaDoTempo evento = new EventoLinhaDoTempo(tipoEvento, data, gwpEvitado, mciPeca);
                    evento.setCliclo(cicloN);
                    
                    // Retorna um Optional com o evento encontrado.
                    return Optional.of(evento);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar o último evento da peça: " + e.getMessage());
            // Em um projeto real, trate a exceção de forma adequada (log, etc.).
        }

        // Se nenhum registro foi encontrado, retorna um Optional vazio.
        return Optional.empty();
    }
    
}

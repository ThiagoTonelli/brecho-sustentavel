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
import java.sql.Timestamp;
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
        String sql = "SELECT * FROM evento_linha_tempo WHERE id_peca = ? ORDER BY data DESC LIMIT 1";
        
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, id_c);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    int id = rs.getInt("id");
                    String idPeca = rs.getString("id_peca");
                    String descricao = rs.getString("descricao");
                    int cicloN = rs.getInt("ciclo_n");
                    String tipoEvento = rs.getString("tipo_evento");
                    LocalDateTime data = rs.getTimestamp("data").toLocalDateTime();
                    double gwpEvitado = rs.getDouble("gwp_evitado");
                    double mciPeca = rs.getDouble("mci_peca");


                    EventoLinhaDoTempo evento = new EventoLinhaDoTempo(descricao, tipoEvento, data, gwpEvitado, mciPeca);
                    evento.setCliclo(cicloN);
                    
                    
                    return Optional.of(evento);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ultimo evento no banco de dados", e);
        }
        return Optional.empty();
    }
    
    @Override
     public void criar(String id_c, EventoLinhaDoTempo evento) {
        String sql = "INSERT INTO evento_linha_tempo(id_peca, descricao, ciclo_n, tipo_evento, data, gwp_evitado, mci_peca) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            
            pstmt.setString(1, id_c);
            pstmt.setString(2, evento.getDescricao());
            pstmt.setInt(3, evento.getCiclo_n());
            pstmt.setString(4, evento.getTipoEvento());
            pstmt.setTimestamp(5, Timestamp.valueOf(evento.getDataHora()));
            pstmt.setDouble(6, evento.getGwpEvitado());
            pstmt.setDouble(7, evento.getMciPeca());

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir evento no banco de dados", e);
        }
    }
    
}

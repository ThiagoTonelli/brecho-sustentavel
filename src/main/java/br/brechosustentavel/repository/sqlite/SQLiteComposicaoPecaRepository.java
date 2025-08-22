/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.Peca;
import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IComposicaoPecaRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thiag
 */
public class SQLiteComposicaoPecaRepository implements IComposicaoPecaRepository{
    private ConexaoFactory conexaoFactory;
    
    public SQLiteComposicaoPecaRepository(ConexaoFactory conexaoFactory){
        this.conexaoFactory = conexaoFactory;
    }
    
    @Override
    public void adicionarComposicaoAPeca(Peca peca, List<Integer> idMateriais) {
        String sql = "INSERT INTO composicao_peca (id_composicao, id_peca) VALUES (?, ?)";
        String idPeca = peca.getId_c();

        for (Integer idComposicao : idMateriais) {
            try (Connection conexao = this.conexaoFactory.getConexao();
                PreparedStatement pstmt = conexao.prepareStatement(sql)) {
                pstmt.setInt(1, idComposicao);
                pstmt.setString(2, idPeca);

                pstmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao inserir a composição com ID " + idComposicao + " para a peça " + idPeca, e);
            }
        }
    }
    
}

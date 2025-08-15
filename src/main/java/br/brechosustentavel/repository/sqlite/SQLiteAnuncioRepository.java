package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.model.EventoLinhaDoTempo;
import br.brechosustentavel.model.Peca;
import br.brechosustentavel.model.Vendedor;
import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IAnuncioRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author thiag
 */
public class SQLiteAnuncioRepository implements IAnuncioRepository{
    private ConexaoFactory conexaoFactory;
    
    public SQLiteAnuncioRepository(ConexaoFactory conexaoFactory){
        this.conexaoFactory = conexaoFactory;
    }
    
    @Override
    public void criar(Anuncio anuncio){
        String sql = "INSERT INTO anuncio(id_vendedor, id_peca, valor_final, gwp, mci) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            
            pstmt.setInt(1, anuncio.getIdVendedor());
            pstmt.setString(2, anuncio.getPeca().getId_c());
            pstmt.setDouble(3, anuncio.getValor_final());
            pstmt.setDouble(4, anuncio.getGwp_avoided());
            pstmt.setDouble(5, anuncio.getMci());

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir anuncio no banco de dados", e);
        }
    }
    
    public List<Anuncio> buscarAnuncios(int id_vendedor) {
        List<Anuncio> anuncios = new ArrayList<>();

        String sql = """
            SELECT a.id, a.id_vendedor, a.id_peca, a.valor_final, a.gwp, a.mci,
                   p.subcategoria, p.tamanho, p.cor, p.massa, 
                   p.estado_conservacao, p.preco_base
            FROM anuncio a
            JOIN peca p ON a.id_peca = p.id_c
            WHERE a.id_vendedor = ?
        """;

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setInt(1, id_vendedor);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Peca peca = new Peca(
                    rs.getString("id_peca"),                 
                    rs.getString("subcategoria"),
                    rs.getString("tamanho"),
                    rs.getString("cor"),
                    rs.getDouble("massa"),
                    rs.getString("estado_conservacao"),
                    rs.getDouble("preco_base")
                );


                Anuncio anuncio = new Anuncio(
                    rs.getInt("id_vendedor"),
                    peca,
                    rs.getDouble("valor_final"),
                    rs.getDouble("gwp"),
                    rs.getDouble("mci")
                );
                anuncio.setIdAnuncio(rs.getInt("id"));

                anuncios.add(anuncio);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar anúncios no banco de dados", e);
        }

        return anuncios;
    }
}

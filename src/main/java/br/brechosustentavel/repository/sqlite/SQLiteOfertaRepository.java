/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.model.Comprador;
import br.brechosustentavel.model.Oferta;
import br.brechosustentavel.model.Peca;
import br.brechosustentavel.model.Usuario;
import br.brechosustentavel.model.Vendedor;
import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IOfertaRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public class SQLiteOfertaRepository implements IOfertaRepository{
    private final ConexaoFactory conexaoFactory;

    public SQLiteOfertaRepository(ConexaoFactory conexaoFactory) {
        this.conexaoFactory = conexaoFactory;
    }
    
    @Override
    public void adicionarOferta(Oferta oferta) {
        String sql = "INSERT INTO oferta (id_comprador, id_anuncio, valor) VALUES (?, ?, ?);";

        try (Connection conexao = conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, oferta.getComprador().getId());
            pstmt.setInt(2, oferta.getAnuncio().getId());
            pstmt.setDouble(3, oferta.getValor());
            
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                oferta.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar oferta: " + e.getMessage());
        }
    }

    @Override
    public Optional<Oferta> buscarOfertaPorId(int id) {
        String sql = """
                     SELECT
                        o.id as id_oferta, o.valor as valor_oferta,
                        a.id as id_anuncio, a.id_vendedor, a.valor_final as valor_final_anuncio, a.gwp as gwp_anuncio, a.mci as mci_anuncio,
                        p.id_c, p.subcategoria, p.tamanho, p.cor, p.massa, p.estado_conservacao, p.preco_base,
                        c.id_comprador, c.nivel_reputacao as comprador_reputacao, c.estrelas as comprador_estrelas, c.compras_finalizadas, c.gwp_evitado, c.selo_verificador,
                        u.id as id_usuario, u.nome, u.telefone, u.email, u.senha,
                        v.id_vendedor, v.nivel_reputacao as vendedor_reputacao, v.estrelas as vendedor_estrelas, v.vendas_concluidas, v.gwp_evitado
                     FROM 
                        oferta o
                     JOIN 
                        anuncio a ON o.id_anuncio = a.id
                     JOIN 
                        peca p ON a.id_peca = p.id_c
                     JOIN 
                        comprador c ON o.id_comprador = c.id_comprador
                     JOIN 
                        usuario u ON c.id_comprador = u.id
                     JOIN
                        usuario u ON v.id_vendedor = u.id
                     WHERE o.id = ?;
                     """;

        try(Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Comprador comprador = new Comprador(
                        rs.getString("comprador_reputacao"), 
                        rs.getDouble("comprador_estrelas"), 
                        rs.getInt("compras_finalizadas"), 
                        rs.getDouble("gwp_evitado"), 
                        rs.getBoolean("selo_verificador")
                );
                comprador.setId(rs.getInt("id_comprador"));
                
                Vendedor vendedor = new Vendedor(
                        rs.getString("vendedor_reputacao"), 
                        rs.getDouble("vendedor_estrelas"), 
                        rs.getInt("vendas_concluidas"), 
                        rs.getDouble("gwp_contribuido")
                );
                vendedor.setId(rs.getInt("id_vendedor"));
                
                Usuario usuario = new Usuario(
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
                usuario.setId(rs.getInt("id_usuario"));
                usuario.setComprador(comprador);
               
                Peca peca = new Peca(
                        rs.getString("id_c"),
                        rs.getString("subcategoria"),
                        rs.getString("tamanho"),
                        rs.getString("cor"),
                        rs.getDouble("massa"),
                        rs.getString("estado_conservacao"),
                        rs.getDouble("preco_base")
                );
                
                Anuncio anuncio = new Anuncio(
                        vendedor, 
                        peca, 
                        rs.getDouble("valor_final_anuncio"), 
                        rs.getDouble("gwp_anuncio"), 
                        rs.getDouble("mci_anuncio")
                );   
                anuncio.setId(rs.getInt("id_anuncio"));

                Oferta oferta = new Oferta(
                        anuncio, 
                        comprador, 
                        rs.getDouble("valor_oferta")        
                );
                oferta.setId(rs.getInt("id_oferta"));
                
                return Optional.of(oferta);
            }
            return Optional.empty();            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar oferta por ID: " + e.getMessage());
        }
    }

    @Override
    public int qtdOfertaPorComprador(int idComprador) {
        String sql = "SELECT COUNT(*) FROM oferta WHERE id_comprador = ?;";

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, idComprador);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar as ofertas do comprador: " + e.getMessage());
        }        
    }
    

    @Override
    public List<Oferta> buscarOfertaPorAnuncio(int idAnuncio) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

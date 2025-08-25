/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.model.Comprador;
import br.brechosustentavel.model.Denuncia;
import br.brechosustentavel.model.Peca;
import br.brechosustentavel.model.Vendedor;
import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IDenunciaRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author kaila
 */
public class SQLiteDenunciaRepository implements IDenunciaRepository{
    private final ConexaoFactory conexaoFactory;
    
    public SQLiteDenunciaRepository(ConexaoFactory conexaoFactory) {
        this.conexaoFactory = conexaoFactory;
    }
    
    @Override
    public void inserirDenuncia(Denuncia denuncia) {
        String sql = "INSERT INTO denuncia (id_anuncio, id_comprador, motivo, descricao, status) VALUES (?, ?, ?, ?, ?);";
        
        try(Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setInt(1, denuncia.getAnuncio().getId());
            pstmt.setInt(2, denuncia.getComprador().getId());
            pstmt.setString(3, denuncia.getMotivo());
            pstmt.setString(4, denuncia.getDescricao());
            pstmt.setString(5, denuncia.getStatus());
            
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                denuncia.setId(rs.getInt(1));
            }
       } catch(SQLException e) {
           throw new RuntimeException("Erro ao inserir denuncia: " + e.getMessage());
       }   
    }

    @Override
    public void atualizarStatusDenuncia(Denuncia denuncia, String novoStatus) {
        String sql = "UPDATE denuncia SET status = ? WHERE id = ?;";
        
        try(Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)){
            pstmt.setString(1, novoStatus);
            pstmt.setInt(2, denuncia.getId());
                      
            if(pstmt.executeUpdate() > 0){
                denuncia.setStatus(novoStatus);
            }
            else{
                throw new RuntimeException("Nenhuma denuncia encontrada com o ID " + denuncia.getId());
            }
       } catch(SQLException e) {
           throw new RuntimeException("Erro ao atualizar status da denuncia: " + e.getMessage());
       }   
    }

    @Override
    public Optional<Denuncia> buscarDenunciaPorId(int id) {
        String sql = """
                    SELECT 
                        d.id, d.motivo, d.descricao, d.status as denuncia_status, d.data,
                        a.id as id_anuncio, a.valor_final, a.gwp, a.mci, a.status as status_anuncio,
                        v.id_vendedor, v.nivel_reputacao as vendedor_reputacao, v.estrelas as vendedor_estrelas, v.vendas_concluidas, v.gwp_contribuido,
                        p.id_c, p.subcategoria, p.tamanho, p.cor, p.massa, p.estado_conservacao, p.preco_base, p.id_tipo,
                        c.id_comprador, c.nivel_reputacao as comprador_reputacao, c.estrelas as comprador_estrelas, c.compras_finalizadas, c.gwp_evitado,
                        c.selo_verificador
                    FROM 
                        denuncia d
                    INNER JOIN 
                        anuncio a ON d.id_anuncio = a.id
                    INNER JOIN 
                        vendedor v ON a.id_vendedor = v.id_vendedor
                    INNER JOIN 
                        peca p ON a.id_peca = p.id_c
                    INNER JOIN 
                        comprador c ON d.id_comprador = c.id_comprador
                    WHERE d.id = ?
                    """;

        try(Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)){
            pstmt.setInt(1, id);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {      
                Vendedor vendedor = new Vendedor(
                        rs.getString("vendedor_reputacao"), 
                        rs.getDouble("vendedor_estrelas"), 
                        rs.getInt("vendas_concluidas"),
                        rs.getDouble("gwp_contribuido")
                );
                vendedor.setId(rs.getInt("id_vendedor"));

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
                        rs.getDouble("valor_final"),
                        rs.getDouble("gwp"),
                        rs.getDouble("mci")
                );
                anuncio.setId(rs.getInt("id_anuncio"));
                anuncio.setStatus(rs.getString("status_anuncio"));
                
                Comprador comprador = new Comprador(
                       rs.getString("comprador_reputacao"), 
                       rs.getDouble("comprador_estrelas"), 
                       rs.getInt("compras_finalizadas"),
                       rs.getDouble("gwp_evitado"),
                       rs.getBoolean("selo_verificador")
                );
                comprador.setId(rs.getInt("id_comprador"));
                
                Denuncia denuncia = new Denuncia(
                        anuncio,
                        comprador,
                        rs.getString("motivo"),
                        rs.getString("descricao"),
                        rs.getString("denuncia_status")
                );
                denuncia.setId(rs.getInt("id"));
                denuncia.setData(LocalDateTime.parse(rs.getString("data"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));           
                
                return Optional.of(denuncia);
            }          
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar denúncias por status: " + e.getMessage());
        } 
    }

    @Override
    public List<Denuncia> buscarDenunciaPorStatus(String status, int id) {
        List<Denuncia> denuncias = new ArrayList<>();

        String sql = """
                    SELECT 
                        d.id, d.motivo, d.descricao, d.status as denuncia_status, d.data,
                        a.id as id_anuncio, a.valor_final, a.gwp, a.mci, a.status as status_anuncio,
                        v.id_vendedor, v.nivel_reputacao as vendedor_reputacao, v.estrelas as vendedor_estrelas, v.vendas_concluidas, v.gwp_contribuido,
                        p.id_c, p.subcategoria, p.tamanho, p.cor, p.massa, p.estado_conservacao, p.preco_base, p.id_tipo,
                        c.id_comprador, c.nivel_reputacao as comprador_reputacao, c.estrelas as comprador_estrelas, c.compras_finalizadas, c.gwp_evitado,
                        c.selo_verificador
                    FROM 
                        denuncia d
                    INNER JOIN 
                        anuncio a ON d.id_anuncio = a.id
                    INNER JOIN 
                        vendedor v ON a.id_vendedor = v.id_vendedor
                    INNER JOIN 
                        peca p ON a.id_peca = p.id_c
                    INNER JOIN 
                        comprador c ON d.id_comprador = c.id_comprador
                    WHERE d.status = ? AND a.id = ?
                    """;

        try(Connection conexao = this.conexaoFactory.getConexao();
            PreparedStatement pstmt = conexao.prepareStatement(sql)){
            pstmt.setString(1, status);
            pstmt.setInt(2, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {      
                Vendedor vendedor = new Vendedor(
                        rs.getString("vendedor_reputacao"), 
                        rs.getDouble("vendedor_estrelas"), 
                        rs.getInt("vendas_concluidas"),
                        rs.getDouble("gwp_contribuido")
                );
                vendedor.setId(rs.getInt("id_vendedor"));

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
                        rs.getDouble("valor_final"),
                        rs.getDouble("gwp"),
                        rs.getDouble("mci")
                );
                anuncio.setId(rs.getInt("id_anuncio"));
                anuncio.setStatus(rs.getString("status_anuncio"));
                
                Comprador comprador = new Comprador(
                       rs.getString("comprador_reputacao"), 
                       rs.getDouble("comprador_estrelas"), 
                       rs.getInt("compras_finalizadas"),
                       rs.getDouble("gwp_evitado"),
                       rs.getBoolean("selo_verificador")
                );
                comprador.setId(rs.getInt("id_comprador"));
                
                Denuncia denuncia = new Denuncia(
                        anuncio,
                        comprador,
                        rs.getString("motivo"),
                        rs.getString("descricao"),
                        rs.getString("denuncia_status")
                );
                denuncia.setId(rs.getInt("id"));
                denuncia.setData(LocalDateTime.parse(rs.getString("data"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                denuncias.add(denuncia);              
            }
            return denuncias;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar denúncias por status: " + e.getMessage());
        } 
    }

    @Override
    public int qtdDenunciasProcedentesPorComprador(int idComprador) {
        String sql = "SELECT COUNT(*) FROM denuncia WHERE id_comprador = ? AND status = 'Procedente';";
        
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {           
            pstmt.setInt(1, idComprador);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar as denúncias do comprador: " + e.getMessage());
        }     
    }

    @Override
    public int qtdDenunciasPorComprador(int idComprador) {
       String sql = "SELECT COUNT(*) FROM denuncia WHERE id_comprador = ?;";
        
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {           
            pstmt.setInt(1, idComprador);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar as denúncias do comprador: " + e.getMessage());
        }     
    }
    
}

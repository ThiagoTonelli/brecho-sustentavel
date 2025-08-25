package br.brechosustentavel.repository.sqlite;

import br.brechosustentavel.dto.FiltroAnuncioDTO;
import br.brechosustentavel.model.Anuncio;
import br.brechosustentavel.model.Peca;
import br.brechosustentavel.model.Vendedor;
import br.brechosustentavel.repository.repositoryFactory.ConexaoFactory;
import br.brechosustentavel.repository.repositoryFactory.IAnuncioRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SQLiteAnuncioRepository implements IAnuncioRepository {
    private final ConexaoFactory conexaoFactory;

    public SQLiteAnuncioRepository(ConexaoFactory conexaoFactory) {
        this.conexaoFactory = conexaoFactory;
    }

    @Override
    public void criar(Anuncio anuncio) {
        String sql = "INSERT INTO anuncio(id_vendedor, id_peca, valor_final, gwp, mci) VALUES(?, ?, ?, ?, ?)";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            
            pstmt.setInt(1, anuncio.getVendedor().getId());
            pstmt.setString(2, anuncio.getPeca().getId_c());
            pstmt.setDouble(3, anuncio.getValorFinal());
            pstmt.setDouble(4, anuncio.getGwpAvoided());
            pstmt.setDouble(5, anuncio.getMci());
            
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir anuncio no banco de dados: " + e.getMessage());
        }
    }

    @Override 
    public List<Anuncio> buscarAnuncios(int idVendedor) {
        List<Anuncio> anuncios = new ArrayList<>();
        String sql = """
            SELECT
                a.id as anuncio_id, a.valor_final, a.gwp, a.mci, a.status,
                p.id_c, p.subcategoria, p.tamanho, p.cor, p.massa, p.estado_conservacao, p.preco_base, p.id_tipo,
                v.id_vendedor, v.nivel_reputacao, v.estrelas, v.vendas_concluidas, v.gwp_contribuido,
                tp.nome AS nome_tipo
            FROM anuncio a
            INNER JOIN peca p ON a.id_peca = p.id_c
            INNER JOIN vendedor v ON a.id_vendedor = v.id_vendedor
            LEFT JOIN tipo_peca tp ON p.id_tipo = tp.id
            WHERE a.id_vendedor = ? AND a.status = 'ativo';;
            """;

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setInt(1, idVendedor);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                anuncios.add(mapearAnuncioDoResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar anúncios com JOIN: " + e.getMessage(), e);
        }
        return anuncios;
    }
    
    @Override
    public Optional<Anuncio> buscarAnuncioPorId(int idAnuncio) {
        String sql = """
            SELECT
                a.id as anuncio_id, a.valor_final, a.gwp, a.mci, a.status,
                p.id_c, p.subcategoria, p.tamanho, p.cor, p.massa, p.estado_conservacao, p.preco_base, p.id_tipo,
                v.id_vendedor, v.nivel_reputacao, v.estrelas, v.vendas_concluidas, v.gwp_contribuido,
                tp.nome AS nome_tipo
            FROM anuncio a
            INNER JOIN peca p ON a.id_peca = p.id_c
            INNER JOIN vendedor v ON a.id_vendedor = v.id_vendedor
            LEFT JOIN tipo_peca tp ON p.id_tipo = tp.id
            WHERE a.id = ? AND a.status = 'ativo';;
            """;

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            
            pstmt.setInt(1, idAnuncio);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapearAnuncioDoResultSet(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar anuncio por id com JOIN: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Anuncio> buscarTodos(int idUsuario) {
        List<Anuncio> anuncios = new ArrayList<>();
        String sql = """
            SELECT
                a.id as anuncio_id, a.valor_final, a.gwp, a.mci, a.status,
                p.id_c, p.subcategoria, p.tamanho, p.cor, p.massa, p.estado_conservacao, p.preco_base, p.id_tipo,
                v.id_vendedor, v.nivel_reputacao, v.estrelas, v.vendas_concluidas, v.gwp_contribuido,
                tp.nome AS nome_tipo
            FROM anuncio a
            INNER JOIN peca p ON a.id_peca = p.id_c
            INNER JOIN vendedor v ON a.id_vendedor = v.id_vendedor
            LEFT JOIN tipo_peca tp ON p.id_tipo = tp.id
            WHERE a.id_vendedor <> ? AND a.status = 'ativo';;
            """;

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                anuncios.add(mapearAnuncioDoResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os anúncios com JOIN: " + e.getMessage(), e);
        }
        return anuncios;
    }
    
    private Anuncio mapearAnuncioDoResultSet(ResultSet rs) throws SQLException {
        Peca peca = new Peca(
            rs.getString("id_c"),
            rs.getString("subcategoria"),
            rs.getString("tamanho"),
            rs.getString("cor"),
            rs.getDouble("massa"),
            rs.getString("estado_conservacao"),
            rs.getDouble("preco_base")
        );
        peca.setIdTipoDePeca(rs.getInt("id_tipo"));
        peca.setTipoDePeca(rs.getString("nome_tipo"));

        Vendedor vendedor = new Vendedor(
            rs.getString("nivel_reputacao"),
            rs.getDouble("estrelas"),
            rs.getInt("vendas_concluidas"),
            rs.getDouble("gwp_contribuido")
        );
        vendedor.setId(rs.getInt("id_vendedor"));

        Anuncio anuncio = new Anuncio(
            vendedor,
            peca,
            rs.getDouble("valor_final"),
            rs.getDouble("gwp"),
            rs.getDouble("mci")
        );
        anuncio.setId(rs.getInt("anuncio_id"));
        anuncio.setStatus(rs.getString("status"));

        return anuncio;
    }

    @Override
    public int qtdAnuncioPorVendedor(int idVendedor) {
        String sql = "SELECT COUNT(*) FROM anuncio WHERE id_vendedor = ?;";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, idVendedor);
            ResultSet rs = pstmt.executeQuery(); 
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar os anúncios do vendedor: " + e.getMessage(), e);
        }
    }

    @Override
    public void excluirPorPecaId(String idPeca) {
        String sql = "DELETE FROM anuncio WHERE id_peca = ?";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, idPeca);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir o anúncio: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void editar(Anuncio anuncio) {
        String sql = "UPDATE anuncio SET valor_final = ?, gwp = ?, mci = ? WHERE id_peca = ?";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setDouble(1, anuncio.getValorFinal());
            pstmt.setDouble(2, anuncio.getGwpAvoided());
            pstmt.setDouble(3, anuncio.getMci());
            pstmt.setString(4, anuncio.getPeca().getId_c());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao editar o anúncio: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Anuncio> buscarPorIdPeca(String idPeca) {
        String sql = """
            SELECT
                a.id as anuncio_id, a.valor_final, a.gwp, a.mci, a.status,
                p.id_c, p.subcategoria, p.tamanho, p.cor, p.massa, p.estado_conservacao, p.preco_base, p.id_tipo,
                v.id_vendedor, v.nivel_reputacao, v.estrelas, v.vendas_concluidas, v.gwp_contribuido,
                tp.nome AS nome_tipo       
            FROM anuncio a
            INNER JOIN peca p ON a.id_peca = p.id_c
            INNER JOIN vendedor v ON a.id_vendedor = v.id_vendedor
            LEFT JOIN tipo_peca tp ON p.id_tipo = tp.id
            WHERE p.id_c = ? AND a.status = 'ativo';;
            """;

        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            
            pstmt.setString(1, idPeca);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapearAnuncioDoResultSet(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar anuncio por id com JOIN: " + e.getMessage());
        }
    }

    @Override
    public void excluirPorId(int id) {
        String sql = "DELETE FROM anuncio WHERE id = ?";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir o anúncio: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Anuncio> buscarComFiltros(FiltroAnuncioDTO filtro, int idUsuarioLogado) {
        List<Anuncio> anuncios = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                """
                SELECT
                    a.id as anuncio_id, a.valor_final, a.gwp, a.mci, a.status,
                    p.id_c, p.subcategoria, p.tamanho, p.cor, p.massa, p.estado_conservacao, p.preco_base, p.id_tipo,
                    v.id_vendedor, v.nivel_reputacao, v.estrelas, v.vendas_concluidas, v.gwp_contribuido,
                    tp.nome AS nome_tipo
                FROM anuncio a
                INNER JOIN peca p ON a.id_peca = p.id_c
                INNER JOIN vendedor v ON a.id_vendedor = v.id_vendedor
                LEFT JOIN tipo_peca tp ON p.id_tipo = tp.id
                LEFT JOIN defeito_peca dp ON p.id_c = dp.id_peca
                LEFT JOIN defeito d ON dp.id_defeito = d.id
                WHERE a.status = 'ativo'
            """
        );
        List<Object> parametros = new ArrayList<>();

        sql.append(" AND v.id_vendedor != ?");
        parametros.add(idUsuarioLogado);

        if (filtro != null && filtro.getTipoCriterio() != null && !"Todos".equals(filtro.getTipoCriterio()))  {
            String criterio = filtro.getTipoCriterio();
            String valor = filtro.getValorFiltro();
            
            if ("Atributos".equals(criterio)) {
                String busca = filtro.getBusca();
                if (busca != null && !busca.isBlank()) {
                    sql.append("""
                         AND (
                             LOWER(p.subcategoria) LIKE LOWER(?) OR
                             LOWER(p.cor) LIKE LOWER(?) OR
                             LOWER(p.estado_conservacao) LIKE LOWER(?)
                    """);
                    String pattern = "%" + busca + "%";
                    parametros.add(pattern);
                    parametros.add(pattern);
                    parametros.add(pattern);

                    try {
                        double massa = Double.parseDouble(busca);
                        sql.append(" OR p.massa = ?");
                        parametros.add(massa);
                    } catch (NumberFormatException ignored) {
                    }

                    sql.append(")"); 
                }
            }else {
                if ("Tipo de Peça".equals(criterio) && !"Todos".equals(valor)) {
                    sql.append(" AND tp.nome = ?");
                    parametros.add(valor);
                } else if ("Faixa de Preço".equals(criterio)) {
                    switch (valor) {
                        case "Até R$ 50,00":
                            sql.append(" AND a.valor_final <= ?");
                            parametros.add(50.0);
                            break;
                        case "R$ 50,01 a R$ 100,00":
                            sql.append(" AND a.valor_final > ? AND a.valor_final <= ?");
                            parametros.add(50.0);
                            parametros.add(100.0);
                            break;
                        case "R$ 100,01 a R$ 500,00":
                            sql.append(" AND a.valor_final > ? AND a.valor_final <= ?");
                            parametros.add(100.0);
                            parametros.add(500.0);
                            break;
                        case "Acima de R$ 500,00":
                            sql.append(" AND a.valor_final > ?");
                            parametros.add(500.0);
                            break;
                    }
                } else if("Defeitos".equals(criterio) && !"Todos".equals(valor)) {
                    sql.append(" AND d.nome = ?");
                    parametros.add(valor);
                } else if ("GWP".equals(criterio)) {
                    switch (valor) {
                        case "Até 10kg CO₂e":
                            sql.append(" AND a.gwp < ?");
                            parametros.add(10.0);
                            break;
                        case "10,01 a 50kg CO₂e":
                            sql.append(" AND a.gwp >= ? AND a.gwp <= ?");
                            parametros.add(10.01);
                            parametros.add(50.0);
                            break;
                        case "50,01 a 100kg CO₂e":
                            sql.append(" AND a.gwp >= ? AND a.gwp <= ?");
                            parametros.add(50.01);
                            parametros.add(100.0);
                            break;
                        case "Acima de 100 kg CO₂e":
                            sql.append(" AND a.gwp > ?");
                            parametros.add(100.0);
                            break;
                    }
                } else if ("MCI".equals(criterio)) {
                    switch (valor) {
                        case "Baixo (< 0.3)":
                            sql.append(" AND a.mci < ?");
                            parametros.add(0.3);
                            break;
                        case "Médio (0.3 - 0.7)":
                            sql.append(" AND a.mci >= ? AND a.mci <= ?");
                            parametros.add(0.3);
                            parametros.add(0.7);
                            break;
                        case "Alto (> 0.7)":
                            sql.append(" AND a.mci > ?");
                            parametros.add(0.7);
                            break;
                    }
                } else if("Atributos".equals(criterio)){
                    String busca = filtro.getBusca();
                    if (busca != null && !busca.isBlank()) {
                        sql.append("""
                            AND (
                                LOWER(p.subcategoria) LIKE LOWER(?) OR
                                LOWER(p.tamanho) LIKE LOWER(?) OR
                                LOWER(p.cor) LIKE LOWER(?) OR
                                LOWER(p.estado_conservacao) LIKE LOWER(?) OR
                                LOWER (p.id_c) = LOWER(?)
                            )
                        """);

                        String pattern = "%" + busca + "%";
                        parametros.add(pattern);
                        parametros.add(pattern);
                        parametros.add(pattern);
                        parametros.add(pattern);
                        parametros.add(busca);

                        try {
                            double massa = Double.parseDouble(busca);
                            sql.append(" OR p.massa = ?");
                            parametros.add(massa);
                        } catch (NumberFormatException ignored) {
                            throw new RuntimeException("Masse deve ser numérica.");
                        }

                        sql.append(")");
                    }
                }
            }
        }
        sql.append(" GROUP BY a.id");
        
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql.toString())) {

            for (int i = 0; i < parametros.size(); i++) {
                pstmt.setObject(i + 1, parametros.get(i));
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Anuncio anuncioMapeado = mapearAnuncioDoResultSet(rs);
                anuncios.add(anuncioMapeado);
            }
            return anuncios;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar anúncios com filtros: " + e.getMessage(), e);
        }
    }
    
    
    @Override
    public void atualizarStatus(String idPeca, String novoStatus) {
        String sql = "UPDATE anuncio SET status = ? WHERE id_peca = ?";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, novoStatus);
            pstmt.setString(2, idPeca);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar status do anúncio: " + e.getMessage(), e);
        }
    }
}

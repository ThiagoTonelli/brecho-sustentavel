package br.brechosustentavel.seeder;

import br.brechosustentavel.service.hash.HashService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


public class H2Seeder implements ISeeder{

    private final Connection conexao;
    private final HashService hashService;

    public H2Seeder(Connection conexao, HashService hashService) {
        this.conexao = conexao;
        this.hashService = hashService;
    }

    
    @Override
    public void inserir() throws SQLException {
        inserirUsuarioEPerfis();
        inserirTiposPeca();
        inserirInsignias();
        inserirDefeitos();
        inserirComposicoes();
    }

    private void inserirUsuarioEPerfis() throws SQLException {
        String sql = "INSERT INTO usuario (nome, telefone, email, senha, data_criacao, admin) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, ?);";
        
        try (PreparedStatement pstmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, "Thiago Tonelli");
            pstmt.setString(2, "28 91234-5678");
            pstmt.setString(3, "thiago@brechosustentavel.com");
            pstmt.setString(4, hashService.gerarHash("123"));
            pstmt.setBoolean(5, true);
            pstmt.executeUpdate();

            pstmt.setString(1, "Karen Silva");
            pstmt.setString(2, "28 99999-1111");
            pstmt.setString(3, "k@gmail.com");
            pstmt.setString(4, hashService.gerarHash("123"));
            pstmt.setBoolean(5, false);
            pstmt.executeUpdate();
            int idKaren = pegarIdGerado(pstmt);

            pstmt.setString(1, "João Pedro");
            pstmt.setString(2, "27 98888-2222");
            pstmt.setString(3, "j@gmail.com");
            pstmt.setString(4, hashService.gerarHash("123"));
            pstmt.setBoolean(5, false);
            pstmt.executeUpdate();
            int idJoao = pegarIdGerado(pstmt);
            
            pstmt.setString(1, "Maria Clara");
            pstmt.setString(2, "27 97777-3333");
            pstmt.setString(3, "m@gmail.com");
            pstmt.setString(4, hashService.gerarHash("123"));
            pstmt.setBoolean(5, false);
            pstmt.executeUpdate();
            int idMaria = pegarIdGerado(pstmt);
            
            inserirPerfis(idKaren, idJoao, idMaria);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar usuários de teste: " + e.getMessage(), e);
        }
    }
    
    private int pegarIdGerado(PreparedStatement pstmt) throws SQLException {
        try (ResultSet rs = pstmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Não foi possível recuperar o ID gerado para o usuário.");
            }
        }
    }
    
    private void inserirPerfis(int idKaren, int idJoao, int idMaria) throws SQLException {
        String sqlVendedor = "INSERT INTO vendedor (id_vendedor, nivel_reputacao, estrelas, vendas_concluidas, gwp_contribuido) VALUES (?, 'Bronze', 0.0, 0, 0.0);";
        String sqlComprador = "INSERT INTO comprador (id_comprador, nivel_reputacao, estrelas, compras_finalizadas, gwp_evitado, selo_verificador) VALUES (?, 'Bronze', 0.0, 0, 0.0, 0);";

        // Cria perfil de Vendedor
        try (PreparedStatement pstmt = conexao.prepareStatement(sqlVendedor)) {

            pstmt.setInt(1, idKaren);
            pstmt.addBatch();
            
            pstmt.executeBatch();
        }

        // Cria perfil de Comprador
        try (PreparedStatement pstmt = conexao.prepareStatement(sqlComprador)) {

            pstmt.setInt(1, idJoao);
            pstmt.addBatch();

            pstmt.setInt(1, idMaria);
            pstmt.addBatch();
            
            pstmt.executeBatch();
        }
    }

    private void inserirTiposPeca() throws SQLException {
        String sql = "INSERT INTO tipo_peca (nome) VALUES (?)";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            String[] tipos = {"Vestuário", "Calçados", "Bolsas e mochilas", "Bijuterias e acessórios"};
            for (String tipo : tipos) {
                pstmt.setString(1, tipo);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar tipo de peça: " + e.getMessage(), e);
        }
    }
    
    private void inserirInsignias() throws SQLException {
        String sql = "INSERT INTO insignia (nome, valor_estrelas, tipo_perfil) VALUES (?,?,?)";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            Object[][] insignias = {
                {"Primeiro Anúncio", 0.2, "Vendedor"},
                {"Cinco Vendas", 0.2, "Vendedor"},
                {"Primeira Oferta", 0.2, "Comprador"},
                {"Dez Compras", 0.2, "Comprador"},
                {"Guardião da Qualidade", 0.2, "Comprador"}
            };
            
            for (Object[] insignia : insignias) {
                pstmt.setString(1, (String) insignia[0]);
                pstmt.setDouble(2, (Double) insignia[1]);
                pstmt.setString(3, (String) insignia[2]);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar insignias: " + e.getMessage(), e);
        }
    }

    private void inserirDefeitos() throws SQLException {
        Map<String, Integer> idTipos = new HashMap<>();
        String sqlBuscaTipos = "SELECT id, nome FROM tipo_peca";
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sqlBuscaTipos)) {
            while (rs.next()) {
                idTipos.put(rs.getString("nome"), rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tipos de peça para defeitos: " + e.getMessage(), e);
        }
        
        String sqlInsertDefeito = "MERGE INTO defeito (nome, desconto, id_tipo) KEY(nome) VALUES (?, ?, ?)";
        Object[][] defeitos = {
            {"Rasgo estruturante", 0.30, "Vestuário"}, {"Ausência de botão principal", 0.15, "Vestuário"},
            {"Zíper parcialmente funcional", 0.15, "Vestuário"}, {"Mancha permanente", 0.20, "Vestuário"},
            {"Desgaste por pilling acentuado", 0.10, "Vestuário"}, {"Sola sem relevo funcional", 0.25, "Calçados"},
            {"Descolamento parcial de entressola", 0.20, "Calçados"}, {"Arranhões profundos", 0.15, "Calçados"},
            {"Palmilha original ausente", 0.10, "Calçados"}, {"Odor persistente leve", 0.10, "Calçados"},
            {"Alça reparada", 0.20, "Bolsas e mochilas"}, {"Fecho defeituoso", 0.20, "Bolsas e mochilas"},
            {"Desbotamento extenso", 0.15, "Bolsas e mochilas"}, {"Forro rasgado", 0.15, "Bolsas e mochilas"},
            {"Oxidação visível", 0.20, "Bijuterias e acessórios"}, {"Pedra ausente", 0.15, "Bijuterias e acessórios"},
            {"Fecho frouxo", 0.10, "Bijuterias e acessórios"}
        };

        try (PreparedStatement pstmt = conexao.prepareStatement(sqlInsertDefeito)) {
            for (Object[] defeito : defeitos) {
                Integer idTipo = idTipos.get((String) defeito[2]);
                if (idTipo == null) {
                    System.err.println("Aviso: Tipo de peça não encontrado para o defeito: " + defeito[0]);
                    continue;
                }
                pstmt.setString(1, (String) defeito[0]);
                pstmt.setDouble(2, (Double) defeito[1]);
                pstmt.setInt(3, idTipo);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir defeitos: " + e.getMessage(), e);
        }
    }

    private void inserirComposicoes() throws SQLException {
        String sql = "MERGE INTO composicao (nome, fator_emissao) KEY(nome) VALUES (?, ?)";
        Object[][] composicoes = {
            {"algodão", 5.2}, {"poliéster", 9.5}, {"couro", 14.8},
            {"metal (ligas leves)", 8.6}, {"plástico de base fóssil", 3.1},
            {"outros", 4.0}
        };

        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            for (Object[] comp : composicoes) {
                pstmt.setString(1, (String) comp[0]);
                pstmt.setDouble(2, (Double) comp[1]);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir composições: " + e.getMessage(), e);
        }
    }
}

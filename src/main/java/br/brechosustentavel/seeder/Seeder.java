/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.seeder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kaila
 */
public class Seeder {

    private Connection conexao;

    public Seeder(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir() {
        inserirUsuario();
        inserirTiposPeca();
        inserirInsignias();
        inserirDefeitos();
    }

    private void inserirUsuario() {
        String sql = "INSERT INTO usuario (nome_completo, telefone, email, senha, data_criacao, admin) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, 1);";

        try(PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, "Thiago Tonelli");
            pstmt.setString(2, "28 91234-5678");
            pstmt.setString(3, "thiago@brechosustentavel.com");
            pstmt.setString(4, "123456"); //fazer hash
            pstmt.executeUpdate();
        } catch(SQLException e){
            throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage());
        } 
    }

    private void inserirTiposPeca() {
        String sql = "INSERT INTO tipo_peca (nome) VALUES (?)";

        try(PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            String[] tipos = {"Vestuário", "Calçados", "Bolsas e mochilas", "Bijuterias e acessórios"};
            for (String tipo : tipos) {
                pstmt.setString(1, tipo);
                pstmt.addBatch();
            }
            
            pstmt.executeBatch();
        }catch(SQLException e) {
            throw new RuntimeException("Erro ao salvar tipo de peça: " + e.getMessage());
        } 
    }
    
    private void inserirInsignias(){
        String sql = "INSERT INTO insignia (nome, valor_estrelas, tipo_perfil) VALUES (?,?,?)";
        
         try(PreparedStatement pstmt = conexao.prepareStatement(sql)) {
             pstmt.setString(1, "Primeiro Anúncio");
             pstmt.setDouble(2, 0.2);
             pstmt.setString(3, "Vendedor");
             pstmt.addBatch();
             
             pstmt.setString(1, "Cinco Vendas");
             pstmt.setDouble(2, 0.2);
             pstmt.setString(3, "Vendedor");
             pstmt.addBatch();
             
             pstmt.setString(1, "Primeira Oferta");
             pstmt.setDouble(2, 0.2);
             pstmt.setString(3, "Comprador");
             pstmt.addBatch();
             
             pstmt.setString(1, "Dez compras");
             pstmt.setDouble(2, 0.2);
             pstmt.setString(3, "Comprador");
             pstmt.addBatch();
             
             pstmt.setString(1, "Guardião da Qualidade");
             pstmt.setDouble(2, 0.2);
             pstmt.setString(3, "Comprador");
             pstmt.addBatch();
             
             pstmt.executeBatch();

         }catch(SQLException e) {
            throw new RuntimeException("Erro ao salvar tipo de peça: " + e.getMessage());
        } 
    }
    
    private void inserirDefeitos() {
        Map<String, Integer> idTipos = new HashMap<>();
        String sqlBuscaTipos = "SELECT id, nome FROM tipo_peca";

        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sqlBuscaTipos)) {

            while (rs.next()) {
                idTipos.put(rs.getString("nome"), rs.getInt("id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tipos de peça: " + e.getMessage(), e);
        }
        
        String sqlInsertDefeito = "INSERT OR IGNORE INTO defeito (nome, desconto, id_tipo) VALUES (?, ?, ?)";

        Object[][] defeitos = {
            {"Rasgo estruturante", 30.0, "Vestuário"},
            {"Ausência de botão principal", 15.0, "Vestuário"},
            {"Zíper parcialmente funcional", 15.0, "Vestuário"},
            {"Mancha permanente", 20.0, "Vestuário"},
            {"Desgaste por pilling acentuado", 10.0, "Vestuário"},

            {"Sola sem relevo funcional", 25.0, "Calçados"},
            {"Descolamento parcial de entressola", 20.0, "Calçados"},
            {"Arranhões profundos", 15.0, "Calçados"},
            {"Palmilha original ausente", 10.0, "Calçados"},
            {"Odor persistente leve", 10.0, "Calçados"},

            {"Alça reparada", 20.0, "Bolsas e mochilas"},
            {"Fecho defeituoso", 20.0, "Bolsas e mochilas"},
            {"Desbotamento extenso", 15.0, "Bolsas e mochilas"},
            {"Forro rasgado", 15.0, "Bolsas e mochilas"},

            {"Oxidação visível", 20.0, "Bijuterias e acessórios"},
            {"Pedra ausente", 15.0, "Bijuterias e acessórios"},
            {"Fecho frouxo", 10.0, "Bijuterias e acessórios"}
        };

        try (PreparedStatement pstmt = conexao.prepareStatement(sqlInsertDefeito)) {
            for (Object[] defeito : defeitos) {
                String nome = (String) defeito[0];
                Double desconto = (Double) defeito[1];
                String tipoNome = (String) defeito[2];

                Integer idTipo = idTipos.get(tipoNome);
                if (idTipo == null) {
                    throw new RuntimeException("Tipo de peça não encontrado: " + tipoNome);
                }

                pstmt.setString(1, nome);
                pstmt.setDouble(2, desconto);
                pstmt.setInt(3, idTipo);
                pstmt.addBatch();
            }
            pstmt.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir defeitos: " + e.getMessage(), e);
        }
    }


}

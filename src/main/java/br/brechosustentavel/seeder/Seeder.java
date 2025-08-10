/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.seeder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}

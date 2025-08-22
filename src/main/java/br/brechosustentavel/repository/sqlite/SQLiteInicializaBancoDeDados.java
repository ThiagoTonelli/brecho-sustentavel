/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.repository.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author kaila
 */
public class SQLiteInicializaBancoDeDados {
    private Connection conexao;
    
    
    public SQLiteInicializaBancoDeDados(Connection conexao) {
        this.conexao = conexao;
    }
    
    public void inicializar(){
        criarTabelaUsuario();
        criarTabelaVendedor();
        criarTabelaComprador();
        criarTabelaInsignia();
        criarTabelaVendedorInsignia();
        criarTabelaCompradorInsignia();
        criarTabelaTipoPeca();
        criarTabelaDefeito();
        criarTabelaComposicao();
        criarTabelaPeca();
        criarTabelaDefeitoPeca();
        criarTabelaAnuncio();
        criarTabelaOferta();
        criarTabelaTransacao();
        criarTabelaEventoLinhaDoTempo();
        criarTabelaComposicaoPeca();
        criarTabelaDenuncia();
    }
    
    private void criarTabelaUsuario() {
        String sql = """
                     CREATE TABLE IF NOT EXISTS usuario (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nome TEXT NOT NULL,
                        telefone TEXT,
                        email TEXT NOT NULL UNIQUE,
                        senha TEXT NOT NULL,
                        data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        admin BOOLEAN NOT NULL DEFAULT 0
                     );
                     """;
        executarSQL(sql);
    }
    
    private void criarTabelaVendedor() {
        String sql = """
                     CREATE TABLE IF NOT EXISTS vendedor (
                        id_vendedor INTEGER PRIMARY KEY,
                        nivel_reputacao TEXT NOT NULL DEFAULT 'Bronze',
                        estrelas REAL NOT NULL DEFAULT 0,
                        vendas_concluidas INTEGER NOT NULL DEFAULT 0,
                        gwp_contribuido REAL NOT NULL DEFAULT 0,  
                        FOREIGN KEY (id_vendedor) REFERENCES usuario(id) ON DELETE CASCADE
                     );
                     """;
        executarSQL(sql);
    }
    
    private void criarTabelaComprador() {
        String sql = """
                     CREATE TABLE IF NOT EXISTS comprador (
                        id_comprador INTEGER PRIMARY KEY,
                        nivel_reputacao TEXT NOT NULL DEFAULT 'Bronze',
                        estrelas REAL NOT NULL DEFAULT 0,
                        compras_finalizadas INTEGER NOT NULL DEFAULT 0,
                        gwp_evitado REAL NOT NULL DEFAULT 0,
                        selo_verificador BOOLEAN NOT NULL DEFAULT 0,
                        FOREIGN KEY (id_comprador) REFERENCES usuario(id) ON DELETE CASCADE
                     );
                     """;  
        executarSQL(sql);
    }
    
    private void criarTabelaInsignia() {
        String sql = """
                    CREATE TABLE IF NOT EXISTS insignia (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nome TEXT NOT NULL,
                        valor_estrelas REAL NOT NULL,
                        tipo_perfil TEXT NOT NULL CHECK(tipo_perfil IN ('Vendedor', 'Comprador'))
                    );
                     """;
        executarSQL(sql);
    }
    
    private void criarTabelaVendedorInsignia() {
        String sql = """
                    CREATE TABLE IF NOT EXISTS vendedor_insignia (
                        id_vendedor INTEGER NOT NULL,
                        id_insignia INTEGER NOT NULL,
                        PRIMARY KEY(id_vendedor, id_insignia),
                        FOREIGN KEY (id_vendedor) REFERENCES vendedor(id_vendedor) ON DELETE CASCADE,
                        FOREIGN KEY (id_insignia) REFERENCES insignia(id) ON DELETE CASCADE
                    );
                     """;
      executarSQL(sql);
    } 
    
    private void criarTabelaCompradorInsignia() {
        String sql = """
                    CREATE TABLE IF NOT EXISTS comprador_insignia (
                        id_comprador INTEGER NOT NULL,
                        id_insignia INTEGER NOT NULL,
                        PRIMARY KEY(id_comprador, id_insignia),
                        FOREIGN KEY (id_comprador) REFERENCES comprador(id_comprador) ON DELETE CASCADE,
                        FOREIGN KEY (id_insignia) REFERENCES insignia(id) ON DELETE CASCADE
                    );
                     """; 
        executarSQL(sql);
    }
    
    private void criarTabelaTipoPeca() {
        String sql = """
                    CREATE TABLE IF NOT EXISTS tipo_peca (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nome TEXT NOT NULL UNIQUE
                    );
                    """;    
        executarSQL(sql);
    }

    private void criarTabelaDefeito() {
        String sql = """
                    CREATE TABLE IF NOT EXISTS defeito (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nome TEXT NOT NULL UNIQUE,
                        desconto REAL NOT NULL,
                        id_tipo INTEGER NOT NULL,
                        FOREIGN KEY (id_tipo) REFERENCES tipo_peca(id) ON DELETE CASCADE
                    );
                    """;
        executarSQL(sql);
    }

    private void criarTabelaComposicao() {
        String sql = """
                    CREATE TABLE IF NOT EXISTS composicao (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nome TEXT NOT NULL UNIQUE,
                        fator_emissao REAL NOT NULL
                    );
                    """;
        executarSQL(sql);
    }
    
    private void criarTabelaPeca() {
        String sql = """
                    CREATE TABLE IF NOT EXISTS peca (
                        id_c TEXT PRIMARY KEY,
                        subcategoria TEXT NOT NULL,
                        tamanho TEXT NOT NULL,
                        cor TEXT NOT NULL,
                        massa REAL NOT NULL,
                        estado_conservacao TEXT NOT NULL,
                        preco_base REAL NOT NULL,
                        id_tipo INTEGER,
                        FOREIGN KEY (id_tipo) REFERENCES tipo_peca(id) ON DELETE SET NULL
                    );
                    """;
        executarSQL(sql);
    }

    private void criarTabelaComposicaoPeca() {
        String sql = """
                     CREATE TABLE IF NOT EXISTS composicao_peca (
                        id_composicao INTEGER NOT NULL,
                        id_peca TEXT NOT NULL,
                        quantidade INTEGER NOT NULL,
                        PRIMARY KEY (id_composicao, id_peca),
                        FOREIGN KEY (id_composicao) REFERENCES composicao(id) ON DELETE CASCADE,
                        FOREIGN KEY (id_peca) REFERENCES peca(id_c) ON DELETE CASCADE
                     );
                     """;
        executarSQL(sql);
    
    }
    
    private void criarTabelaDefeitoPeca() {
        String sql = """
                    CREATE TABLE IF NOT EXISTS defeito_peca (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        id_defeito INTEGER NOT NULL,
                        id_peca TEXT NOT NULL,
                        UNIQUE (id_defeito, id_peca),
                        FOREIGN KEY (id_defeito) REFERENCES defeito(id) ON DELETE CASCADE,
                        FOREIGN KEY (id_peca) REFERENCES peca(id_c) ON DELETE CASCADE
                    );
                    """;
        executarSQL(sql);
    }
    
    private void criarTabelaEventoLinhaDoTempo() {
        String sql = """
                    CREATE TABLE IF NOT EXISTS evento_linha_tempo (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        id_peca TEXT NOT NULL,
                        descricao TEXT NOT NULL,
                        ciclo_n INTEGER NOT NULL,
                        tipo_evento TEXT NOT NULL,
                        data DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        gwp_evitado REAL NOT NULL,
                        mci_peca REAL NOT NULL,
                        FOREIGN KEY (id_peca) REFERENCES peca(id_c) ON DELETE CASCADE
                    );
                    """;
        executarSQL(sql);
    }
    
    private void criarTabelaAnuncio() {
        String sql = """
                     CREATE TABLE IF NOT EXISTS anuncio (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        id_vendedor INTEGER NOT NULL,
                        id_peca TEXT NOT NULL UNIQUE,
                        valor_final REAL NOT NULL,
                        gwp REAL NOT NULL,
                        mci REAL NOT NULL,
                        FOREIGN KEY (id_vendedor) REFERENCES vendedor(id_vendedor) ON DELETE CASCADE,
                        FOREIGN KEY (id_peca) REFERENCES peca(id_c) ON DELETE CASCADE
                     );
                     """;
        executarSQL(sql);
    }
    
    private void criarTabelaOferta() {
        String sql = """
                    CREATE TABLE IF NOT EXISTS oferta (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        id_comprador INTEGER NOT NULL,
                        id_anuncio INTEGER NOT NULL,
                        valor REAL NOT NULL,
                        data DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (id_anuncio) REFERENCES anuncio(id) ON DELETE CASCADE,
                        FOREIGN KEY (id_comprador) REFERENCES comprador(id_comprador) ON DELETE CASCADE
                    );
                    """;
        executarSQL(sql);
    }

    private void criarTabelaTransacao() {
        String sql = """
                    CREATE TABLE IF NOT EXISTS transacao (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        id_oferta INTEGER NOT NULL,
                        valor_total REAL NOT NULL,
                        data DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (id_oferta) REFERENCES oferta(id) ON DELETE CASCADE
                    );
                    """;
        executarSQL(sql);
    }
    
    private void criarTabelaDenuncia() {
        String sql = """
                     CREATE TABLE IF NOT EXISTS denuncia (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        id_anuncio INTEGER NOT NULL,
                        id_comprador INTEGER NOT NULL,
                        data DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        motivo TEXT NOT NULL,
                        descricao TEXT,    
                        status TEXT NOT NULL DEFAULT 'Pendente',
                        FOREIGN KEY (id_anuncio) REFERENCES anuncio(id) ON DELETE CASCADE,
                        FOREIGN KEY (id_comprador) REFERENCES comprador(id_comprador) ON DELETE CASCADE
                     );
                     """;
        executarSQL(sql);
    }

    private void executarSQL(String sql) {
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.execute();
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao criar tabela: " + e.getMessage());
        }
    }
}


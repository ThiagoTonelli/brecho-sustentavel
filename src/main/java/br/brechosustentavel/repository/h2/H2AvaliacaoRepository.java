
package br.brechosustentavel.repository.h2;

import br.brechosustentavel.model.Avaliacao;
import br.brechosustentavel.repository.ConexaoFactory;
import br.brechosustentavel.repository.IAvaliacaoRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author thiag
 */
public class H2AvaliacaoRepository implements IAvaliacaoRepository {
    
    private final ConexaoFactory conexaoFactory;

    public H2AvaliacaoRepository(ConexaoFactory conexaoFactory) {
        this.conexaoFactory = conexaoFactory;
    }

    @Override
    public void salvar(Avaliacao avaliacao) {
        String sql = "INSERT INTO avaliacao (id_transacao, id_autor, texto) VALUES (?, ?, ?)";
        try (Connection conexao = this.conexaoFactory.getConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            
            pstmt.setInt(1, avaliacao.getTransacao().getId());
            pstmt.setInt(2, avaliacao.getAutor().getId());
            pstmt.setString(3, avaliacao.getTexto());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar avaliação no H2: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Avaliacao> buscarPorTransacao(int idTransacao) {
        throw new UnsupportedOperationException("Ainda não implementado para H2.");
    }
}

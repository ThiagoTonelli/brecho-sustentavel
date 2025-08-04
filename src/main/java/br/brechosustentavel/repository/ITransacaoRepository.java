/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.repository;

import br.projeto.model.Transacao;
import java.util.Optional;

/**
 *
 * @author thiag
 */
public interface ITransacaoRepository {
    public void criar();
    public void excluir();
    public void editar();
    public Optional<Transacao> consultar();
}

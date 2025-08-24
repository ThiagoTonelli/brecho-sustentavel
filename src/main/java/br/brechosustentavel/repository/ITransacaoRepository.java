/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.brechosustentavel.repository;

import br.brechosustentavel.model.Transacao;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author thiag
 */
public interface ITransacaoRepository {
    public void salvar(Transacao transacao);;
    public List<Map<String, Object>> buscarDadosParaExportacao();
}

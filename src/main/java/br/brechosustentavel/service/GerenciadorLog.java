/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.service;

import br.brechosustentavel.configuracao.ConfiguracaoAdapter;
import br.log.ILog;
import br.log.LogFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author thiag
 */
public final class GerenciadorLog {

    private static GerenciadorLog instancia;
    private ILog logger;
    private final SessaoUsuarioService sessao;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private GerenciadorLog() {
        this.sessao = SessaoUsuarioService.getInstancia();
        carregarConfiguracao();
    }

    public void carregarConfiguracao() {
        ConfiguracaoAdapter config = new ConfiguracaoAdapter();
        String tipoLog = config.getValor("LOG_TIPO");
        String caminhoLog = config.getValor("LOG_CAMINHO");

        if (tipoLog == null || tipoLog.isEmpty()) tipoLog = "csv";
        if (caminhoLog == null || caminhoLog.isEmpty()) caminhoLog = "log." + tipoLog;

        this.logger = LogFactory.criarLog(tipoLog, caminhoLog);
    }

    public static synchronized GerenciadorLog getInstancia() {
        if (instancia == null) {
            instancia = new GerenciadorLog();
        }
        return instancia;
    }
    
    private String getUsuarioLogado() {
        if (sessao != null && sessao.isAutenticado() && sessao.getUsuarioAutenticado() != null) {
            return sessao.getUsuarioAutenticado().getNome();
        }
        return "SISTEMA";
    }

    public void registrarSucesso(String operacao, String idc, String nome) {
        LocalDateTime agora = LocalDateTime.now();
        String data = agora.format(dateFormatter);
        String hora = agora.format(timeFormatter);
        String usuario = getUsuarioLogado();

        String mensagem = String.format("<<%s>><%s>: <<%s>>, (<<%s>>, <<%s>>, e <<%s>>",
                idc, operacao, nome, data, hora, usuario);
        
        logger.registrar(mensagem, usuario, agora);
    }

    public void registrarFalha(String operacao, String idc, String nome, String falha) {
        LocalDateTime agora = LocalDateTime.now();
        String data = agora.format(dateFormatter);
        String hora = agora.format(timeFormatter);
        String usuario = getUsuarioLogado();
        
        String idcFormatado = (idc == null || idc.isEmpty()) ? "" : ", <<" + idc + ">>";

        String mensagem = String.format("Ocorreu a falha <<%s>> ao realizar a \"%s do contato <<%s>>, (<<%s>>, <<%s>>, <<%s>>%s, quando for o caso.\"",
                falha, operacao, nome, data, hora, usuario, idcFormatado);

        logger.registrar(mensagem, usuario, agora);
    }
}

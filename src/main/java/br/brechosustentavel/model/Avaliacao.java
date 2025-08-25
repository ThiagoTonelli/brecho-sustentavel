/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.brechosustentavel.model;

import java.time.LocalDateTime;

/**
 *
 * @author thiag
 */
public class Avaliacao {
    private int id;
    private Transacao transacao;
    private Usuario autor;
    private String texto;
    private LocalDateTime data;

    public Avaliacao(Transacao transacao, Usuario autor, String texto) {
        this.transacao = transacao;
        this.autor = autor;
        this.texto = texto;
        this.data = LocalDateTime.now();
    }

    // Getters e Setters para todos os campos
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Transacao getTransacao() { return transacao; }
    public void setTransacao(Transacao transacao) { this.transacao = transacao; }
    public Usuario getAutor() { return autor; }
    public void setAutor(Usuario autor) { this.autor = autor; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
    
    public boolean isAutorComprador() {
        return autor.getId() == transacao.getOferta().getComprador().getId();
    }
}

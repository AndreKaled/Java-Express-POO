package com.example.javaexpress.model.model;

public class MensagemConversa {
    
    // "user" ou "bot"
    private String remetente; 
    
    // O conteúdo da mensagem
    private String conteudo;

    // Construtor padrão
    public MensagemConversa() {
    }

    // Getters e Setters
    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
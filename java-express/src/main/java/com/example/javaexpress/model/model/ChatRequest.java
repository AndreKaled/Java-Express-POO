package com.example.javaexpress.model.model;

import java.util.List;

public class ChatRequest {
    private String mensagem;

    private List<MensagemConversa> historico; 

    public ChatRequest() {
    }

    // Getters e setters
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public List<MensagemConversa> getHistorico() {
        return historico;
    }

    public void setHistorico(List<MensagemConversa> historico) {
        this.historico = historico;
    }
}
package com.example.javaexpress.model.model;

public class ChatResponse {

    private String resposta;

    private long timestamp;

    public ChatResponse() {
    }

    // Getters e Setters
    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
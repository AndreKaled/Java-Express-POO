package com.example.javaexpress.model.model;

public class ChatResponse {

    // A resposta final de texto gerada pelo ChatbotService/LLM
    private String resposta;

    // Timestamp para fins de log e ordenação no frontend
    private long timestamp;

    // Construtor padrão
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
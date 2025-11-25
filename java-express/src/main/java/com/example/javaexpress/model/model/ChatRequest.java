package com.example.javaexpress.model.model;

import java.util.List;

public class ChatRequest {

    // A mensagem atual que o usuário acabou de digitar
    private String mensagem;

    // Opcional: Para LLMs que precisam de contexto, o histórico é crucial
    // Você pode enviar as últimas N mensagens do diálogo para que o bot se lembre
    private List<MensagemConversa> historico; 

    // Construtor padrão (necessário para a desserialização do JSON pelo Spring)
    public ChatRequest() {
    }

    // Getters e Setters
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
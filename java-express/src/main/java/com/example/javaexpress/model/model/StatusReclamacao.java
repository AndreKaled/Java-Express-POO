package com.example.javaexpress.model.model;

public enum StatusReclamacao {
    ABERTA(0),
    EM_ANALISE(1),
    RESOLVIDA(2),
    RECUSADA(3);
    
    int statusReclamacao;
    StatusReclamacao(int statusReclamacao) {
        this.statusReclamacao = statusReclamacao;
    }

    public int getStatusReclamacao() {
        return statusReclamacao;
    }
}
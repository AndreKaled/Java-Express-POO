package com.example.javaexpress.model.model;

public enum StatusReclamacao {
    ABERTA("ABERTA"),
    EM_ANALISE("EM ANALISE"),
    RESOLVIDA("RESOLVIDA"),
    RECUSADA("RECUSADA");
    
    String statusReclamacao;
    StatusReclamacao(String statusReclamacao) {
        this.statusReclamacao = statusReclamacao;
    }

    public String getStatusReclamacao() {
        return statusReclamacao;
    }
}
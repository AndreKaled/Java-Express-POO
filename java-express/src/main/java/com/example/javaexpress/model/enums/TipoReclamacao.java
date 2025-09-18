package com.example.javaexpress.model.enums;

public enum TipoReclamacao {
    EXTRAVIO(0),
    DANIFICADA(1),
    ATRASO(2),
    OUTRO(3);

    private int tipoReclamacao;
    TipoReclamacao(int tipoReclamacao) {
        this.tipoReclamacao = tipoReclamacao;
    }

    public int getTipoReclamacao() {
        return tipoReclamacao;
    }
}

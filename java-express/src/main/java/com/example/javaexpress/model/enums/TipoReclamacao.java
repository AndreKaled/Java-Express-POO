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
  
    public TipoReclamacao mapearMotivoReclamacao(String motivoTexto) {
        if (motivoTexto == null) {
            return TipoReclamacao.OUTRO;
        }
        String m = motivoTexto.toUpperCase().trim();

        if (m.contains("EXTRAVIO") 
             || m.contains("PERDA") 
             || m.contains("SUMIÇO") 
             || m.contains("DESAPARECIMENTO") 
             || m.contains("DESAPARECIDA")
             || m.contains("SUMIU") 
             || m.contains("SUMIRAM")) {
            return TipoReclamacao.EXTRAVIO;
        } else if (m.contains("DANO")
             || m.contains("QUEBRADO") 
             || m.contains("DANIFICADA")
             || m.contains("DANIFICADO") 
             || m.contains("QUABRADO")) {
            return TipoReclamacao.DANIFICADA;
        } else if (m.contains("ATRASO") 
             || m.contains("DEMORA") 
             || m.contains("LENTO")) {
            return TipoReclamacao.ATRASO;
        } else {
            return TipoReclamacao.OUTRO;
        }
    }
}


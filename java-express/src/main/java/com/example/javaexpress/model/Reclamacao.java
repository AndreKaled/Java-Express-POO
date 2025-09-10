package com.example.javaexpress.model;

public class Reclamacao {
    int idReclamacao;
    Encomenda encomenda;
    Cliente cliente;
    String descricao;
    StatusReclamacao statusReclamacao;
    TipoReclamacao tipo;

    // 0 a 5 estrelas
    int feedback;
}

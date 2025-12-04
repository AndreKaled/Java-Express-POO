package com.example.javaexpress.model.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class SentimentoChatbot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sentimento; // POSITIVO, NEGATIVO, NEUTRO
    private int valorNumerico; // 1, -1, 0
    private LocalDateTime dataRegistro;

    public SentimentoChatbot() {
        this.dataRegistro = LocalDateTime.now();
    }

    public SentimentoChatbot(String sentimento, int valorNumerico) {
        this();
        this.sentimento = sentimento;
        this.valorNumerico = valorNumerico;
    }

    // GETTERS E SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSentimento() {
        return sentimento;
    }

    public void setSentimento(String sentimento) {
        this.sentimento = sentimento;
    }

    public int getValorNumerico() {
        return valorNumerico;
    }

    public void setValorNumerico(int valorNumerico) {
        this.valorNumerico = valorNumerico;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}
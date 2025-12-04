package com.example.javaexpress.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.javaexpress.model.model.SentimentoChatbot;

@Repository
public interface SentimentoChatbotRepository extends JpaRepository<SentimentoChatbot, Long> {

    // Método que calcula a média do valorNumerico de todas as entradas
    @Query("SELECT AVG(s.valorNumerico) FROM SentimentoChatbot s")
    Double calcularMediaSentimento();
}


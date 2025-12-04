package com.example.javaexpress.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.javaexpress.model.model.SentimentoChatbot;
import com.example.javaexpress.repository.SentimentoChatbotRepository;

@Service
public class AnaliseSentimentoService {

    private final SentimentoChatbotRepository repository;

    @Autowired
    public AnaliseSentimentoService(SentimentoChatbotRepository repository) {
        this.repository = repository;
    }

    /**
     * Registra o sentimento no banco de dados e retorna a mensagem de análise.
     * @param sentimento A string de sentimento (POSITIVO, NEGATIVO, NEUTRO).
     * @return A mensagem de análise para os programadores.
     */
    @Transactional
    public String registrarSentimento(String sentimento) {
        int valorNumerico;
        String analiseMsg;

        switch (sentimento.toUpperCase()) {
            case "POSITIVO":
                valorNumerico = 10; 
                analiseMsg = "Excelente! O usuário demonstrou satisfação na requisição inicial.";
                break;
            case "NEGATIVO":
                valorNumerico = 1; 
                analiseMsg = "Atenção: O usuário expressou insatisfação na requisição inicial.";
                break;
            case "NEUTRO":
            default:
                valorNumerico = 5; 
                analiseMsg = "Análise de sentimento (NEUTRA): O serviço foi concluído. Favor aguardar feedback.";
                break;
        }

        SentimentoChatbot registro = new SentimentoChatbot(sentimento, valorNumerico);
        repository.save(registro);

        return analiseMsg;
    }

    /**
     * Calcula a média de todos os sentimentos registrados (entre 1 e 10).
     * @return A média de sentimento como Double.
     */
    @Transactional(readOnly = true)
    public Double calcularMediaGeral() {
        return repository.calcularMediaSentimento();
    }
}
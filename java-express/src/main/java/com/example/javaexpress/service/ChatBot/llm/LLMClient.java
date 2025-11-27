package com.example.javaexpress.service.ChatBot.llm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LLMClient {

    @Value("${llm.api.key}")
    private String apiKey;
    public String primeiraChamada(String systemPrompt, String mensagemUsuario) {
        
        if (mensagemUsuario.toLowerCase().contains("rastreio") || mensagemUsuario.toLowerCase().contains("rastrear")) {
             return "{\"funcao\":\"rastrear_encomenda\",\"parametros\":{\"codigo_rastreio\":\"AA1234567BR\"}}"; 
        } else {
    
             return "Sou o assistente dos Correios. Por favor, me diga o que deseja rastrear.";
        }
    }

    public String chamadaFinal(String mensagemOriginal, String resultadoDaFuncao) {
        
        return "Conforme solicitado, seu código (" + mensagemOriginal + ") foi rastreado. Status: " + resultadoDaFuncao;
    }
}
package com.example.javaexpress.service.ChatBot.llm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LLMClient {

    @Value("${llm.api.key}")
    private String apiKey;
    
    // public LLMClient(...) {} 

    public String primeiraChamada(String systemPrompt, String mensagemUsuario) {
        
        // Lógica real de chamada HTTP/SDK aqui.
        // Esta função envia a mensagem e as definições das ferramentas para a LLM.

        if (mensagemUsuario.toLowerCase().contains("rastreio") || mensagemUsuario.toLowerCase().contains("rastrear")) {
             // SIMULAÇÃO do JSON de Function Calling retornado pela LLM:
             return "{\"funcao\":\"rastrear_encomenda\",\"parametros\":{\"codigo_rastreio\":\"AA1234567BR\"}}"; 
        } else {
             // SIMULAÇÃO da resposta direta da LLM:
             return "Sou o assistente dos Correios. Por favor, me diga o que deseja rastrear.";
        }
    }

    public String chamadaFinal(String mensagemOriginal, String resultadoDaFuncao) {
        
        // Lógica real de chamada HTTP/SDK aqui.
        // Esta função envia o resultadoDaFuncao (o dado real) de volta para a LLM formatar a resposta.

        // SIMULAÇÃO da Resposta Final:
        return "Conforme solicitado, seu código (" + mensagemOriginal + ") foi rastreado. Status: " + resultadoDaFuncao;
    }
}
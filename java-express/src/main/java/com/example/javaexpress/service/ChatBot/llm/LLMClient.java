package com.example.javaexpress.service.ChatBot.llm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LLMClient {

    @Value("${llm.api.key}")
    private String apiKey;

    public String primeiraChamada(String systemPrompt, String mensagemUsuario) {
    
     String msg = mensagemUsuario.toLowerCase();
     
     if (msg.contains("reclamação") 
          || msg.contains("reclamar")
          || msg.contains("registrar")
          || msg.contains("abrir chamado")) {
          return "{\"funcao\":\"registrar_nova_reclamacao\",\"parametros\":{\"codigo_rastreio\":\"BR011\",\"motivo\":\"extraviada\"}}";
    
     } else if (msg.contains("rastreio") 
          || msg.contains("rastrear")
          || msg.contains("codigo")) {
          return "{\"funcao\":\"rastrear_encomenda\",\"parametros\":{\"codigo_rastreio\":\"AA1234567BR\"}}"; 
     } else if (msg.contains("consulta")
          || msg.contains("status de")) {
          return "{\"funcao\":\"consultar_encomenda_cliente\",\"parametros\":{\"codigo_rastreio\":\"BR010\"}}";
     } else if (msg.contains("listar") 
          || msg.contains("minhas encomendas")) {
          return "{\"funcao\":\"listar_encomendas\",\"parametros\":{}}"; 
     } else {
          return "Sou o assistente virtual do Java Express. Por favor, me diga o que deseja: Rastrear,consultar,listar encomendas ou registrar uma reclamação.";
     }
 }
    public String chamadaFinal(String mensagemOriginal, String resultadoDaFuncao) {
        return "Conforme solicitado, sua requisição sobre '" + mensagemOriginal + "' foi processada. Resultado: " + resultadoDaFuncao;
    }
}
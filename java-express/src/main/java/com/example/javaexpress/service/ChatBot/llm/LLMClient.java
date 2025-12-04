package com.example.javaexpress.service.ChatBot.llm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.javaexpress.service.AnaliseSentimentoService;

@Component
public class LLMClient {

    @Value("${llm.api.key}")
    private String apiKey;

    private final AnaliseSentimentoService analiseSentimentoService;

    @Autowired
    public LLMClient(AnaliseSentimentoService analiseSentimentoService) {
        this.analiseSentimentoService = analiseSentimentoService;
    }

    public String primeiraChamada(String systemPrompt, String mensagemUsuario) {
    
        String msg = mensagemUsuario.toLowerCase();
    
        if (msg.contains("reclamação") || msg.contains("reclamar") || msg.contains("registrar") || msg.contains("abrir chamado")) {
             return "{\"funcao\":\"registrar_nova_reclamacao\",\"parametros\":{\"codigo_rastreio\":\"BR011\",\"motivo\":\"extraviada\"}}";
        
        } else if (msg.contains("rastreio") || msg.contains("rastrear") || msg.contains("codigo") || msg.contains("status")) {
             return "{\"funcao\":\"rastrear_encomenda\",\"parametros\":{\"codigo_rastreio\":\"BR001\"}}"; 
        
        } else if (msg.contains("consulta") || msg.contains("status de")) {
             return "{\"funcao\":\"consultar_encomenda_cliente\",\"parametros\":{\"codigo_rastreio\":\"BR010\"}}";
        
        } else if (msg.contains("listar") || msg.contains("minhas encomendas")) {
             return "{\"funcao\":\"listar_encomendas\",\"parametros\":{}}"; 
        
        } else {
             return "Sou o assistente virtual do Java Express. Por favor, me diga o que deseja rastrear ou me faça uma pergunta geral.";
        }
    }

    public String chamadaFinal(String mensagemOriginal, String resultadoDaFuncao) {
        
        String sentimentoSimulado = "NEUTRO"; 
        String lowerMsg = mensagemOriginal.toLowerCase();
        
       
        if (lowerMsg.contains("reclamação") || lowerMsg.contains("sumiu") || lowerMsg.contains("extravio") || lowerMsg.contains("atraso") || lowerMsg.contains("danificada")) {
            sentimentoSimulado = "NEGATIVO";
        } else if (lowerMsg.contains("ótimo") || lowerMsg.contains("excelente") || lowerMsg.contains("perfeito") || lowerMsg.contains("obrigado")) { 
            sentimentoSimulado = "POSITIVO";
        }
        
    
        String mensagemParaProgramadores = analiseSentimentoService.registrarSentimento(sentimentoSimulado);
        

        String respostaAoUsuarioAdicional = "Seu feedback é muito importante! Com base no serviço que realizamos, qual nota de 1 a 10 você daria para a experiência geral com o Sistema Java Express?";
        
        String respostaAoUsuario = String.format(
            "Conforme solicitado, sua requisição sobre '%s' foi processada. Resultado: %s. \n\n🤖 (Análise para o sistema: %s) \n\n%s",
            mensagemOriginal, 
            resultadoDaFuncao, 
            mensagemParaProgramadores, 
            respostaAoUsuarioAdicional
        );
        
        return respostaAoUsuario;
    }
}
package com.example.javaexpress.service.ChatBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// ATUALIZE ESTAS LINHAS:
import com.example.javaexpress.service.EncomendaService; 
import com.example.javaexpress.service.ChatBot.llm.LLMClient;
import com.example.javaexpress.service.ChatBot.tools.JsonParser;
import com.example.javaexpress.service.ChatBot.tools.FuncaoChamadaDTO;

@Service
public class ChatBotService {

    private final LLMClient llmClient; 
    private final JsonParser jsonParser; 
    private final EncomendaService encomendaService; 

    @Autowired
    public ChatBotService(LLMClient llmClient, JsonParser jsonParser, EncomendaService encomendaService) {
        this.llmClient = llmClient;
        this.jsonParser = jsonParser;
        this.encomendaService = encomendaService;
    }
    
    private static final String SYSTEM_PROMPT = 
        "Você é um assistente dos Correios do Brasil. Foco em rastreamento e prazos.";

    public String gerarResposta(String mensagemUsuario) {
        
        // 1. Primeira Chamada à LLM (Decisão)
        String respostaOuFuncaoJSON = llmClient.primeiraChamada(SYSTEM_PROMPT, mensagemUsuario);

        // 2. Tenta fazer o Parsing (Análise) da Chamada de Função
        FuncaoChamadaDTO chamada = jsonParser.parsearChamada(respostaOuFuncaoJSON);

        if (chamada != null) {
            
            // 3. Verifica qual função a LLM quer executar.
            if (chamada.getFuncao().equals("rastrear_encomenda")) {
                
                String codigo = jsonParser.extrairParametro(chamada, "codigo_rastreio");
                
                // 4. Executa o serviço real do seu sistema.
                String resultadoDaFuncao = encomendaService.rastrear(codigo);
                
                // 5. Segunda Chamada à LLM (Para formatar o resultado).
                return llmClient.chamadaFinal(mensagemUsuario, resultadoDaFuncao);
            }
        }

        // 6. Se não era uma função, retorna o texto direto.
        return respostaOuFuncaoJSON;
    }
}
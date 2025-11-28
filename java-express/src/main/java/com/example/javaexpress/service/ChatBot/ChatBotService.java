package com.example.javaexpress.service.ChatBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.javaexpress.service.ClienteService;
import com.example.javaexpress.service.EncomendaService; 
import com.example.javaexpress.service.ChatBot.llm.LLMClient;
import com.example.javaexpress.service.ChatBot.tools.JsonParser;
import com.example.javaexpress.service.ChatBot.tools.FuncaoChamadaDTO;
import com.example.javaexpress.model.model.Encomenda;

@Service
public class ChatBotService {

    private final LLMClient llmClient; 
    private final JsonParser jsonParser; 
    private final EncomendaService encomendaService; 
    private final ClienteService clienteService;

    @Autowired
    public ChatBotService(LLMClient llmClient, JsonParser jsonParser, EncomendaService encomendaService, ClienteService clienteService) {
        this.llmClient = llmClient;
        this.jsonParser = jsonParser;
        this.encomendaService = encomendaService;
        this.clienteService = clienteService;
    }
    
    private static final String SYSTEM_PROMPT = 
        "Você é um assistente virtual de um sistema web de correios chamado Java Express. Foco em rastreamento e prazos.";

    public String gerarResposta(String mensagemUsuario) {
        
        String respostaOuFuncaoJSON = llmClient.primeiraChamada(SYSTEM_PROMPT, mensagemUsuario);
        FuncaoChamadaDTO chamada = jsonParser.parsearChamada(respostaOuFuncaoJSON);
    
        if (chamada != null) {
            String resultadoDaFuncao;

            if (chamada.getFuncao().equals("rastrear_encomenda")) {
                String codigo = jsonParser.extrairParametro(chamada, "codigo_rastreio");
                resultadoDaFuncao = encomendaService.rastrear(codigo);
            } else if (chamada.getFuncao().equals("listar_encomendas")) {
                resultadoDaFuncao = encomendaService.listarEncomendasChatbot();
            } else if (chamada.getFuncao().equals("consultar_encomenda_cliente")) {
                
                String codigo = jsonParser.extrairParametro(chamada, "codigo_rastreio");
                Encomenda encomenda = clienteService.consultarEncomenda(codigo);
                resultadoDaFuncao = "Status: " + encomenda.getStatus() + ", Destino: " + encomenda.getDestino();

            } else if (chamada.getFuncao().equals("registrar_nova_reclamacao")) {
                
                String codigo = jsonParser.extrairParametro(chamada, "codigo_rastreio");
                String motivo = jsonParser.extrairParametro(chamada, "motivo");
                resultadoDaFuncao = String.format(
                    "Reclamação registrada com sucesso para o código %s com o motivo: %s.", codigo, motivo);
            } else {
                return "Erro interno: A LLM solicitou uma função desconhecida do sistema.";
            }
            return llmClient.chamadaFinal(mensagemUsuario, resultadoDaFuncao);
        } else {
            return respostaOuFuncaoJSON;
        }
    }
}
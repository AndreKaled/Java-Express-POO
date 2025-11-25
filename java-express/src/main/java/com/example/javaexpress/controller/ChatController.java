package com.example.javaexpress.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Importa os DTOs que você acabou de criar
import com.example.javaexpress.model.model.ChatRequest;
import com.example.javaexpress.model.model.ChatResponse;

// Importa o serviço que criamos na pasta Chatbot
import com.example.javaexpress.service.ChatBot.ChatBotService;

@RestController // Define que esta classe é um Controller REST
@RequestMapping("/api/chat") // Define o caminho base da API
public class ChatController {

    // Injeção do serviço que contém toda a lógica do chatbot
    private final ChatBotService chatbotService;

    // Injeção de Dependência via construtor (prática recomendada)
    @Autowired
    public ChatController(ChatBotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    /**
     * Endpoint POST para receber a mensagem do usuário e retornar a resposta do bot.
     * URL Completa: POST /api/chat
     */
    @PostMapping
    public ResponseEntity<ChatResponse> sendMessage(@RequestBody ChatRequest request) {
        
        // 1. Receber a mensagem do Request DTO
        String mensagemUsuario = request.getMensagem();

        // 2. Chamar o Service Layer (onde está a LLM e o Function Calling)
        String respostaDoBot = chatbotService.gerarResposta(mensagemUsuario);

        // 3. Criar o Response DTO
        ChatResponse response = new ChatResponse();
        response.setResposta(respostaDoBot);
        response.setTimestamp(System.currentTimeMillis());
        
        // 4. Retornar a resposta com status HTTP 200 (OK)
        return ResponseEntity.ok(response);
    }
}
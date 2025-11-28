package com.example.javaexpress.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.javaexpress.model.model.ChatRequest;
import com.example.javaexpress.model.model.ChatResponse;

import com.example.javaexpress.service.ChatBot.ChatBotService;

@RestController 
@RequestMapping("/api/chat") 
public class ChatController {

    private final ChatBotService chatbotService;


    @Autowired
    public ChatController(ChatBotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> sendMessage(@RequestBody ChatRequest request) {
        String mensagemUsuario = request.getMensagem();
        String respostaDoBot = chatbotService.gerarResposta(mensagemUsuario);

        ChatResponse response = new ChatResponse();
        response.setResposta(respostaDoBot);
        response.setTimestamp(System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }
}
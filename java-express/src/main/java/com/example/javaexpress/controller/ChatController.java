package com.example.javaexpress.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import com.example.javaexpress.model.model.ChatRequest;
import com.example.javaexpress.model.model.ChatResponse;

import com.example.javaexpress.service.ChatBot.ChatBotService;

@Controller
@RequestMapping("/api/chat") 
public class ChatController {

    private final ChatBotService chatbotService;


    @Autowired
    public ChatController(ChatBotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ChatResponse> sendMessage(@RequestBody ChatRequest request) {
        String mensagemUsuario = request.getMensagem();
        String respostaDoBot = chatbotService.gerarResposta(mensagemUsuario);

        ChatResponse response = new ChatResponse();
        response.setResposta(respostaDoBot);
        response.setTimestamp(System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }

    /**
     * Isso retorna um micro-html para anexar em alguma div no frontend,
     * mostra um chat e faz requests para o chatbot
     * */
    @GetMapping
    public String chat() {
        return "main_chat";
    }

}
package com.example.javaexpress.service.ChatBot.tools;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonParser {

    private final ObjectMapper objectMapper;

    public JsonParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public FuncaoChamadaDTO parsearChamada(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, FuncaoChamadaDTO.class);
        } catch (IOException e) {
            return null; 
        }
    }
    public String extrairParametro(FuncaoChamadaDTO chamada, String nomeParametro) {
        if (chamada != null && chamada.getParametros() != null) {
            return chamada.getParametros().get(nomeParametro);
        }
        return null;
    }
    
}
package com.example.javaexpress.service.ChatBot.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import java.io.IOException;

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
            // Se falhar (não é um JSON de função), retorna nulo.
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
package com.example.javaexpress.model.util;

import com.example.javaexpress.model.model.Coordenadas;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PontosCache {
    private final Map<String, List<Coordenadas>> cache = new ConcurrentHashMap<>();

    public void salvar(String sessionId, List<Coordenadas> pontos) {
        cache.put(sessionId, pontos);
    }

    public List<Coordenadas> buscar(String sessionId) {
        return cache.getOrDefault(sessionId, new ArrayList<>());
    }

    public void limpar(String sessionId) {
        cache.remove(sessionId);
    }
}

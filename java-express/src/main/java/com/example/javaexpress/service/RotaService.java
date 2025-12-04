package com.example.javaexpress.service;

import com.example.javaexpress.model.model.Coordenadas;
import com.example.javaexpress.model.model.Rota;
import com.example.javaexpress.model.util.RotaApiClient;
import com.example.javaexpress.model.util.RotaOtimizada;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RotaService {

    private final RotaApiClient api;
    private final RotaOtimizada otimizador;

    public RotaService(RotaApiClient api){
        this.api = api;
        this.otimizador = new RotaOtimizada();
    }

    public Rota otimizarRota(List<Coordenadas> pontosRaw){

        List<Coordenadas> pontosLimpos = pontosRaw.stream()
                .filter(c -> c != null)
                .toList();

        var resposta = api.getDistanciaMatriz(pontosLimpos);

        // remove pontos com snapped_distance alto
        List<Coordenadas> pontosValidos = new ArrayList<>();
        for (int i = 0; i < pontosLimpos.size(); i++) {
            if (resposta.snapped[i] <= 350) {
                pontosValidos.add(pontosLimpos.get(i));
            }
        }

        if (pontosValidos.isEmpty()) {
            throw new IllegalStateException("Nenhum ponto roteável dentro de 350m.");
        }

        // ajusta ids
        for(int i = 0; i < pontosValidos.size(); i++)
            pontosValidos.get(i).setId(i);

        double[][] matriz = api.getDistanciaMatriz(pontosValidos).distancias;

        Map<Coordenadas, Integer> idx = otimizador.criarIndexMap(pontosValidos);
        List<Coordenadas> rotaInicial;

        if (pontosValidos.size() <= 12) {
            rotaInicial = otimizador.vizinhoMaisProximo(pontosValidos, matriz).getCoordenadas();
            rotaInicial = otimizador.twoOpt(rotaInicial, matriz, idx);
        } else {
            rotaInicial = otimizador.simulatedAnnealing(pontosValidos, matriz, idx);
        }

        return api.getRota(rotaInicial);
    }
}

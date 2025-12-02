package com.example.javaexpress.service;

import com.example.javaexpress.model.model.Coordenadas;
import com.example.javaexpress.model.model.Rota;
import com.example.javaexpress.model.util.RotaApiClient;
import com.example.javaexpress.model.util.RotaOtimizada;
import org.springframework.stereotype.Service;

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

    public Rota otimizarRota(List<Coordenadas> pontos){
        for(int i = 0; i < pontos.size(); i++)
            pontos.get(i).setId(i);

        double[][] distanciaMatriz = api.getDistanciaMatriz(pontos);

        List<Coordenadas> rotaInicial;
        Map<Coordenadas, Integer> idx = otimizador.criarIndexMap(pontos);

        if(pontos.size() <= 12){
            rotaInicial = otimizador.vizinhoMaisProximo(pontos, distanciaMatriz).getCoordenadas();
            rotaInicial = otimizador.twoOpt(rotaInicial, distanciaMatriz, idx);
        }else{
            rotaInicial = otimizador.simulatedAnnealing(pontos, distanciaMatriz, idx);
        }
        return api.getRota(rotaInicial);
    }

    //teste
    public double[][] testarMatriz(List<Coordenadas> pontos) {
        return api.getDistanciaMatriz(pontos);
    }
    public Rota testarRota(List<Coordenadas> pontos) {
        return api.getRota(pontos);
    }
}

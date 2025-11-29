package com.example.javaexpress.service;

import com.example.javaexpress.model.model.Coordenadas;
import com.example.javaexpress.model.model.Rota;
import com.example.javaexpress.model.util.RotaApiClient;
import com.example.javaexpress.model.util.RotaOtimizada;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RotaService {

    private final RotaApiClient api;
    private final RotaOtimizada otimizador;

    public RotaService(RotaApiClient api){
        this.api = api;
        this.otimizador = new RotaOtimizada();
    }

    public Rota otimizarRota(List<Coordenadas> pontos){
        double[][] distanciaMatriz = api.getDistanciaMatriz(pontos);
        Rota rotaOrdenada = otimizador.vizinhoMaisProximo(pontos, distanciaMatriz);
        return api.getRota(rotaOrdenada.getCoordenadas());
    }

    //teste
    public double[][] testarMatriz(List<Coordenadas> pontos) {
        return api.getDistanciaMatriz(pontos);
    }
    public Rota testarRota(List<Coordenadas> pontos) {
        return api.getRota(pontos);
    }
}

package com.example.javaexpress.service;

import com.example.javaexpress.model.model.Coordenadas;
import com.example.javaexpress.model.model.Rota;
import com.example.javaexpress.model.util.RotaApiClient;
import com.example.javaexpress.model.util.RotaOtimizada;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RotaService {

    private final RotaApiClient client;
    private final RotaOtimizada otimizador;

    public RotaService(RotaApiClient client){
        this.client = client;
        this.otimizador = new RotaOtimizada();
    }

    public Rota otimizarRota(List<Coordenadas> pontos){
        return null;
    }

    //teste
    public double[][] testarMatriz(List<Coordenadas> pontos) {
        return client.getDistanciaMatriz(pontos);
    }
    public Rota testarRota(List<Coordenadas> pontos) {
        return client.getRota(pontos);
    }
}

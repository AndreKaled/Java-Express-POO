package com.example.javaexpress.model.util;

import com.example.javaexpress.model.model.Coordenadas;
import com.example.javaexpress.model.model.Rota;

import java.util.ArrayList;
import java.util.List;

public class RotaOtimizada {

    /**Aplica a heuristica gulosa para encontrar uma rota aproximada para o problema do caixeiro viajante
     * @param pontos Lista de Coordenadas original
     * @param matrizDistancias A matriz de distância NxN das coordenadas
     * @return Um objeto Rota com a sequência otimizada de coordenadas e suas distâncias
     * */
    public Rota vizinhoMaisProximo(List<Coordenadas> pontos, double[][] matrizDistancias){
        int numPontos = pontos.size();
        //casos triviais
        if(numPontos <= 1)
            return new Rota(new ArrayList<>(pontos), 0.0);

        boolean[] visitados = new boolean[numPontos];
        List<Coordenadas> rotaOtimizada = new ArrayList<>();
        double distanciaTotal = 0.0;

        //escolhe ponto inicial
        int pontoAtual = 0;
        visitados[pontoAtual] = true;
        rotaOtimizada.add(pontos.get(pontoAtual));

        /***
         * continua visitando ate todos os pontos serem visitados
         * buscando o vizinho mais proximo que nao foi visitado
         */
        while(rotaOtimizada.size() < numPontos){
            double menorDistancia = Double.MAX_VALUE;
            int proximoPonto = -1;

            //busca vizinho mais proximo nao visitado
            for(int i = 0; i < numPontos; i++){
                if(!visitados[i]){
                    double distancia = matrizDistancias[pontoAtual][i];
                    if(distancia < menorDistancia){
                        menorDistancia = distancia;
                        proximoPonto = i;
                    }
                }
            }

            //se move pro grafo mais proximo achado
            if(proximoPonto != -1){
                visitados[proximoPonto] = true;
                rotaOtimizada.add(pontos.get(proximoPonto));
                distanciaTotal += menorDistancia;
                pontoAtual = proximoPonto;
            }
        }

        //retorna do ultimo ponto pro inicial
        int primeiroPonto = 0;
        distanciaTotal += matrizDistancias[pontoAtual][primeiroPonto];
        rotaOtimizada.add(pontos.get(primeiroPonto));

        return new Rota(rotaOtimizada, distanciaTotal);
    }
}

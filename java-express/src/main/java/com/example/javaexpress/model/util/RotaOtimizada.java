package com.example.javaexpress.model.util;

import com.example.javaexpress.model.model.Coordenadas;
import com.example.javaexpress.model.model.Rota;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitária responsável por algoritmos de otimização de rotas.
 * <p>
 * Atualmente, implementa uma heurística gulosa (vizinho mais próximo)
 * para gerar uma rota aproximada do Problema do Caixeiro Viajante.
 * Essa heurística não garante a solução ótima, mas fornece um caminho eficiente
 * em tempo polinomial.
 * </p>
 *
 * <p>
 * Exemplo de uso:
 * <pre>{@code
 * RotaOtimizada otimizador = new RotaOtimizada();
 * Rota rota = otimizador.vizinhoMaisProximo(pontos, matrizDistancias);
 * }</pre>
 * </p>
 * @author André Kaled
 * @version 1.0
 * @since 19-10-2025
 * */
public class RotaOtimizada {

    /**
     * Aplica a heurística do vizinho mais próximo para gerar uma rota aproximada
     * conectando todas as coordenadas e retornando ao ponto inicial.
     * <p>
     * O algoritmo parte de um ponto inicial (índice 0) e a cada iteração
     * seleciona o ponto não visitado mais próximo até que todos tenham sido visitados.
     * No final, retorna ao ponto de origem, formando um ciclo completo.
     * </p>
     *
     * @param pontos Lista de {@link Coordenadas} representando os pontos a serem visitados.
     * @param matrizDistancias Matriz NxN contendo as distâncias entre os pontos.
     *                         A posição [i][j] deve conter a distância entre o ponto i e o ponto j.
     * @return Um objeto {@link Rota} contendo a sequência otimizada de coordenadas e a distância total percorrida.
     */
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

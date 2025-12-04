package com.example.javaexpress.model.util;

import com.example.javaexpress.model.model.Coordenadas;
import com.example.javaexpress.model.model.Rota;

import java.util.*;

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
     * Mapeia cada coordenada para sua posição na matriz NxN.
     */
    public Map<Coordenadas, Integer> criarIndexMap(List<Coordenadas> pontos) {
        Map<Coordenadas, Integer> mapa = new HashMap<>();
        for (int i = 0; i < pontos.size(); i++) {
            mapa.put(pontos.get(i), i);
        }
        return mapa;
    }

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
        List<Coordenadas> rota = new ArrayList<>();
        double distanciaTotal = 0.0;

        //escolhe ponto inicial
        int pontoAtual = 0;
        visitados[pontoAtual] = true;
        rota.add(pontos.get(pontoAtual));

        /***
         * continua visitando ate todos os pontos serem visitados
         * buscando o vizinho mais proximo que nao foi visitado
         */
        while(rota.size() < numPontos){
            double menorDistancia = Double.MAX_VALUE;
            int proximoPonto = -1;

            //busca vizinho mais proximo nao visitado
            for(int i = 0; i < numPontos; i++){
                if(!visitados[i] && matrizDistancias[pontoAtual][i] < menorDistancia){
                    menorDistancia = matrizDistancias[pontoAtual][i];
                    proximoPonto = i;
                }
            }

            //se move pro grafo mais proximo achado
            visitados[proximoPonto] = true;
            rota.add(pontos.get(proximoPonto));
            distanciaTotal += menorDistancia;
            pontoAtual = proximoPonto;
        }

        //retorna do ultimo ponto pro inicial
        int primeiroPonto = 0;
        distanciaTotal += matrizDistancias[pontoAtual][primeiroPonto];
        rota.add(pontos.get(primeiroPonto));

        return new Rota(rota, distanciaTotal);
    }

    /**
     * Aplica a meta-heurística Simulated Annealing para refinar uma rota.
     * Baseia-se em trocas 2-Opt aleatórias e aceita movimentos piores
     * no início para escapar de mínimos locais.
     *
     * @param pontos Rota de partida
     * @param dist Matriz de distâncias entre os pontos.
     * @return A rota mais otimizada encontrada.
     */
    public List<Coordenadas> simulatedAnnealing(List<Coordenadas> pontos, double[][] dist, Map<Coordenadas, Integer> idx) {

        //usa NN para começar com minimo local
        List<Coordenadas> rotaBase = vizinhoMaisProximo(pontos, dist).getCoordenadas();
        List<Coordenadas> melhorRota = new ArrayList<>(rotaBase);
        List<Coordenadas> rotaAtual = new ArrayList<>(rotaBase);

        // parametros de SA (precisam de tuning)
        double temperatura = 10_000.0; // temperatura inicial alta
        double taxaResfriamento = 0.01; // taxa de diminuição
        int maxIter = 100_000; //qnt de iterações maximas
        int iter = 0;

        double melhorCusto = calcularCustoTotal(melhorRota, dist, idx);
        double custoAtual = melhorCusto;

        while (temperatura > 1 && iter < maxIter) {
            iter++;
            // geração de vizinhança (movimento 2-Opt aleatório)
            List<Coordenadas> novaRota = gerarRotaVizinha(rotaAtual);
            double novoCusto = calcularCustoTotal(novaRota, dist, idx);

            // se a nova rota é melhor, aceita
            if (novoCusto < custoAtual) {
                rotaAtual = novaRota;
                custoAtual = novoCusto;

                // atualiza a melhor rota encontrada globalmente
                if (novoCusto < melhorCusto) {
                    melhorRota = novaRota;
                    melhorCusto = novoCusto;
                }
            }
            // se a nova rota é pior, aceita com uma probabilidade
            else {
                double aceitacao = Math.exp((custoAtual - novoCusto) / temperatura);

                if (Math.random() < aceitacao) {
                    // aceita o movimento pior
                    rotaAtual = novaRota;
                    custoAtual = novoCusto;
                }
            }

            // resfriamento (Diminuição da temperatura)
            temperatura *= (1 - taxaResfriamento);
        }

        return melhorRota;
    }

    // Métodos auxiliares pro simulated Annelinng
    private double calcularCustoTotal(List<Coordenadas> rota, double[][] dist, Map<Coordenadas, Integer> idx) {
        double total = 0.0;
        // Percorre do elemento 0 (ponto inicial) até o penúltimo elemento (N),
        // onde a conexão N para N+1 (que é o ponto 0) fecha o ciclo.
        for (int i = 0; i < rota.size() - 1; i++) {
            int a = idx.get(rota.get(i));
            int b = idx.get(rota.get(i+1));
            total += dist[a][b];
        }
        return total;
    }


    private List<Coordenadas> gerarRotaVizinha(List<Coordenadas> rota) {
        List<Coordenadas> nova = new ArrayList<>(rota);

        int n = rota.size();

        // pega dois pontos aleatórios distintos
        int i = (int) (Math.random() * (n - 1));
        int j = (int) (Math.random() * (n - 1));

        // garante que i < j
        if (i > j) {
            int temp = i;
            i = j;
            j = temp;
        }

        // aplica 2-opt invertendo o trecho entre i e j
        while (i < j) {
            Collections.swap(nova, i, j);
            i++;
            j--;
        }
        return nova;
    }


    /**
     * auxiliar
     * */
    public List<Coordenadas> twoOpt(List<Coordenadas> rota, double[][] dist, Map<Coordenadas,Integer> idx) {
        boolean melhorou = true;

        while (melhorou) {
            melhorou = false;
            for (int i = 1; i < rota.size() - 2; i++) {
                for (int j = i + 1; j < rota.size() - 1; j++) {
                    if (ganhoComTroca(rota, i, j, dist, idx) < 0) {
                        inverter(rota, i, j);
                        melhorou = true;
                    }
                }
            }
        }
        return rota;
    }


    private double ganhoComTroca(List<Coordenadas> rota, int i, int j, double[][] dist, Map<Coordenadas, Integer> idx) {
       Coordenadas A = rota.get(i -1);
       Coordenadas B = rota.get(i);
       Coordenadas C = rota.get(j);
       Coordenadas D = rota.get((j + 1) % rota.size());

       double atual = dist[idx.get(A)][idx.get(B)] + dist[idx.get(C)][idx.get(D)];
       double novo =  dist[idx.get(A)][idx.get(C)] + dist[idx.get(B)][idx.get(D)];
       return novo - atual;
    }

    private void inverter(List<Coordenadas> rota, int i, int j) {
        while (i < j) {
            Collections.swap(rota, i, j);
            i++;
            j--;
        }
    }

}

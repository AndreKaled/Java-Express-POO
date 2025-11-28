package com.example.javaexpress.model.util;

import com.example.javaexpress.model.model.Coordenadas;
import com.example.javaexpress.model.model.Rota;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RotaApiClient {

    private static final String URL_BASE = "https://api.openrouteservice.org/v2/";
    @Value("${OPENROUTE_API_KEY}")
    private String API_KEY;
    private final RestTemplate restTemplate = new RestTemplate();

    private long ultimaChamada = 0;
    private static final long INTERVALO_MINIMO_MS = 1500; // 1.5s por chamada


    public double[][] getDistanciaMatriz(List<Coordenadas> pontos){
        String url = URL_BASE + "matrix/driving-car";

        Map<String, Object> body = new HashMap<>();
        List<List<Double>> coords = pontos.stream().map(p -> List.of(p.getLongitude(), p.getLatitude())).toList(); //osr usa lon/lat
        body.put("locations", coords);
        body.put("metrics", List.of("distance"));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", API_KEY);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        try {
            respeitarRateLimit();
            Map<String, Object> response = restTemplate.postForObject(url, entity, Map.class);
            System.out.println("Resposta da API: " + response);
            if(response == null || !response.containsKey("distances")){
                throw new RuntimeException("Resposta inválida da API OpenRouteService");
            }
            //pega e converte matriz de distancias
            List<List<Double>> distancias = (List<List<Double>>) response.get("distances");
            return distancias.stream().map(list -> list.stream()
                            .mapToDouble(Double::doubleValue)
                            .toArray())
                    .toArray(double[][]::new);
        }catch (HttpClientErrorException | HttpServerErrorException e){
            System.err.println("Erro HTTP " +e.getStatusCode() +":" +e.getResponseBodyAsString());
            return new double[0][0];
        }
    }

    public Rota getRota(List<Coordenadas> pontos) {
        String url = URL_BASE + "directions/driving-car/geojson";

        Map<String, Object> body = new HashMap<>();
        List<List<Double>> coords = pontos.stream().map(p -> List.of(p.getLongitude(), p.getLatitude())).toList();
        body.put("coordinates", coords);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", API_KEY);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            Map<String, Object> response = restTemplate.postForObject(url, entity, Map.class);
            Map<String, Object> features = (Map<String, Object>) ((List<?>) response.get("features")).get(0);
            Map<String, Object> props = (Map<String, Object>) features.get("properties");
            Map<String, Object> summary = (Map<String, Object>) props.get("summary");

            double distancia = ((Number) summary.get("distance")).doubleValue();

            // converter coordenadas de volta pra lista de objetos
            Map<String, Object> geometry = (Map<String, Object>) features.get("geometry");
            List<List<Double>> coordsResposta = (List<List<Double>>) geometry.get("coordinates");
            List<Coordenadas> coordenadas = coordsResposta.stream()
                    .map(c -> new Coordenadas(c.get(1), c.get(0))) // volta pra lat/lon
                    .toList();

            return new Rota(coordenadas, distancia);
        } catch (Exception e) {
            System.err.println("Erro ao obter rota: " + e.getMessage());
            return null;
        }
    }


    /**
     * ponto crítico, ajustar dps para threads
     * */
    private void respeitarRateLimit() {
        long agora = System.currentTimeMillis();
        long espera = ultimaChamada + INTERVALO_MINIMO_MS - agora;
        if (espera > 0) {
            try { Thread.sleep(espera); } catch (InterruptedException ignored) {}
        }
        ultimaChamada = System.currentTimeMillis();
    }
}

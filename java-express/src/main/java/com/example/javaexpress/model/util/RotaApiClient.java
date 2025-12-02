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

import java.util.ArrayList;
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

    public static class DistanciaMatrizResposta {
        public double[][] distancias;
        public double[] snapped;
    }

    public DistanciaMatrizResposta getDistanciaMatriz(List<Coordenadas> pontos){
        String url = URL_BASE + "matrix/driving-car";

        Map<String, Object> body = new HashMap<>();
        List<List<Double>> coords = pontos.stream()
                .map(p -> List.of(p.getLongitude(), p.getLatitude()))
                .toList();
        body.put("locations", coords);
        body.put("metrics", List.of("distance"));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", API_KEY);
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            respeitarRateLimit();
            Map<String, Object> response = restTemplate.postForObject(url, entity, Map.class);

            List<List<Double>> distancias = (List<List<Double>>) response.get("distances");
            List<Map<String, Object>> sources = (List<Map<String, Object>>) response.get("sources");

            double[][] matriz = distancias.stream()
                    .map(row -> row.stream().mapToDouble(Double::doubleValue).toArray())
                    .toArray(double[][]::new);

            double[] snapped = sources.stream()
                    .mapToDouble(s -> ((Number)s.get("snapped_distance")).doubleValue())
                    .toArray();

            DistanciaMatrizResposta resp = new DistanciaMatrizResposta();
            resp.distancias = matriz;
            resp.snapped = snapped;
            return resp;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
            List<Coordenadas> coordenadas = new ArrayList<>();
            for (List<Double> c : coordsResposta) {
                Coordenadas original = encontrarOriginal(pontos, c.get(1), c.get(0));
                coordenadas.add(original);
            }


            return new Rota(coordenadas, distancia);
        } catch (Exception e) {
            System.err.println("Erro ao obter rota: " + e.getMessage());
            return null;
        }
    }

    private Coordenadas encontrarOriginal(List<Coordenadas> originais, double lat, double lon) {
        for (Coordenadas p : originais) {
            if (Math.abs(p.getLatitude() - lat) < 1e-7 &&
                    Math.abs(p.getLongitude() - lon) < 1e-7) {
                return p;
            }
        }
        return new Coordenadas(lat, lon); // fallback (quase nunca usado)
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

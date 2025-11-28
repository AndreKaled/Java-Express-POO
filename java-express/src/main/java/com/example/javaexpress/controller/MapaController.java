package com.example.javaexpress.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mapa")
public class MapaController {

    /**
     * Isso retorna um micro-html para anexar em alguma div no frontend,
     * mostra o mapa de manaus com leaflet, útil pra pegar os pontos pra otimizar rota
     * */
    @GetMapping
    public String mapa() {
        return "main_map";
    }
}

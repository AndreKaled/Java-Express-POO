package com.example.javaexpress.controller;

import com.example.javaexpress.model.model.Coordenadas;
import com.example.javaexpress.model.model.Rota;
import com.example.javaexpress.model.util.PontosCache;
import com.example.javaexpress.service.RotaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rota")
public class RotaController {

    private final RotaService rotaService;
    private final PontosCache pontosCache;

    public RotaController(RotaService rotaService, PontosCache pontosCache){
        this.rotaService = rotaService;
        this.pontosCache = pontosCache;
    }

    @GetMapping("/otimizar")
    public Rota otimizar(){
        List<Coordenadas> pontos = pontosCache.buscar("usuario1");
        //System.out.println("pontos: " + pontos);
        return rotaService.otimizarRota(pontos);
    }

    //se nao tiver salvo, pode usar esse
    @PostMapping("/otimizar")
    public Rota otimizar(@RequestBody List<Coordenadas> pontos){
        return rotaService.otimizarRota(pontos);
    }

    // salvar pontos sem otimizar
    @PostMapping("/salvar-pontos")
    public void salvarPontos(@RequestBody List<Coordenadas> pontos){
        pontosCache.salvar("usuario1", pontos); // pode usar sessionId real depois
    }
}

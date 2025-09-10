package com.example.javaexpress.model;

import java.time.LocalDate;
import java.util.List;

public class Encomenda {
    String codigoRastreio;
    String origem;
    String destino;
    String status;
    List<String> historicoRastreio;
    LocalDate dataPrevistaEntrega;

}

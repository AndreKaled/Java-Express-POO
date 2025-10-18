package com.example.javaexpress.model.model;

import java.util.List;

public class Rota {
    private List<Coordenadas> coordenadas;
    private double distanciaTotal;

    public Rota(){}
    public Rota(List<Coordenadas> coordenadas, double distanciaTotal){
        this.coordenadas = coordenadas;
        this.distanciaTotal = distanciaTotal;
    }

    public List<Coordenadas> getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(List<Coordenadas> coordenadas) {
        this.coordenadas = coordenadas;
    }

    public double getDistanciaTotal() {
        return distanciaTotal;
    }

    public void setDistanciaTotal(double distanciaTotal) {
        this.distanciaTotal = distanciaTotal;
    }
}

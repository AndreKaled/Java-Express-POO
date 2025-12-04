package com.example.javaexpress.model.model;


public class Coordenadas {
    private double latitude;
    private double longitude;
    private int id;

    public Coordenadas() {
    }

    public Coordenadas(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}
}

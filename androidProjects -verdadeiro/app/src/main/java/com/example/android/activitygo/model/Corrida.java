
package com.example.android.activitygo.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Corrida {

    private String data;
    private double distancia;
    private String tempo;
    private double pace;
    private ArrayList<LatLng> coordenadas;

    public Corrida(String data, double distancia, String tempo, double pace, ArrayList<LatLng> coordenadas) {
        this.data = data;
        this.distancia = distancia;
        this.tempo = tempo;
        this.pace = pace;
        this.coordenadas = coordenadas;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getDistancia() {
        return distancia;
    }


    public String getTempo() {
        return tempo;
    }


    public double getPace() {
        return pace;
    }

    public ArrayList<LatLng> getCoordenadas() {
        return coordenadas;
    }
}


package com.example.android.activitygo.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Corrida {

    private String data;
    private double distancia;
    private String tempo;
    private double pace;
    private ArrayList<Double> coordenadas;
    private String username;
    private String melhorkm;
    private String melhorSegundokm;

    public Corrida() {
    }

    public Corrida(String username, String data, double distancia, String tempo, double pace, ArrayList<Double> coordenadas, String melhorkm,String melhorSegundokm) {
        this.data = data;
        this.distancia = distancia;
        this.tempo = tempo;
        this.pace = pace;
        this.coordenadas = coordenadas;
        this.username = username;
        this.melhorkm = melhorkm;
        this.melhorSegundokm = melhorSegundokm;
    }


    public String getMelhorSegundokm() {
        return melhorSegundokm;
    }

    public String getMelhorkm() {
        return melhorkm;
    }

    public String getUsername() {
        return username;
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

    public ArrayList<Double> getCoordenadas() {
        return coordenadas;
    }
}
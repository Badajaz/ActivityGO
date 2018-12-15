package com.example.android.activitygo.model;

public class Desafio {

    private int desafio;
    private String username;

    public Desafio(){}

    public Desafio(int desafio,String username) {
        this.desafio = desafio;
        this.username = username;
    }


    public int getDesafio() {
        return desafio;
    }

    public String getUsername() {
        return username;
    }
}

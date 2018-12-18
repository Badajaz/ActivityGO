package com.example.android.activitygo.model;

public class Challenge {

    private String tipoDesporto;
    private String descricao;
    private String data;
    private String mUsername;
    private int pontos;
    private int completed;


    public Challenge() {

    }

    public Challenge(String username, String tipoDesporto, String descricao, String data, int pontos,int completed) {
        this.tipoDesporto = tipoDesporto;
        this.descricao = descricao;
        this.data = data;
        this.mUsername = username;
        this.pontos = pontos;
        this.completed = completed;

    }

    public String getmUsername() {
        return mUsername;
    }

    public String getTipoDesporto() {
        return tipoDesporto;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getData() {
        return data;
    }

    public int getPontos() {
        return pontos;
    }

    public int getCompleted() {
        return completed;
    }
}

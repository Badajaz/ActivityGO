package com.example.android.activitygo.model;

public class Challenge {

    private String tipoDesporto;
    private String descricao;
    private String data;
    private String mUsername;

    public Challenge() {

    }

    public Challenge(String username, String tipoDesporto, String descricao, String data) {
        this.tipoDesporto = tipoDesporto;
        this.descricao = descricao;
        this.data = data;
        this.mUsername = username;
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
}

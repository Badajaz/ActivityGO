package com.example.android.activitygo.model;

import java.util.ArrayList;

public class Ranking {

    private String desporto;
    //private String nome;
    private ArrayList<String> rankings;
    private int pontuacaoTotal;


    public Ranking() {

    }

    public Ranking(String desporto, ArrayList<String> rankings) {
        this.desporto = desporto;
        this.rankings = rankings;
    }

    /*
    public String getNome() {
        return this.nome;
    }
    */

    public String getDesporto() {
        return this.desporto;
    }

    public ArrayList<String> getRankings() {
        return this.rankings;
    }

}
package com.example.android.activitygo.model;

import java.util.ArrayList;

public class PedidoGrupo {

    private String nome;
    private String descricao;
    private int pontosTotais;
    private String desporto;
    private ArrayList<String> elementosGrupo;
    private String criador;

    public PedidoGrupo() {
    }

    public PedidoGrupo(String criador, String nome, String descricao, String desporto) {
        this.nome = nome;
        this.descricao = descricao;
        this.pontosTotais = 0;
        this.desporto = desporto;
        this.elementosGrupo = elementosGrupo;
        elementosGrupo = new ArrayList<>();
        this.criador = criador;
    }

    public String getCriador() {
        return criador;
    }

    public String getNome() {
        return nome;
    }

    public String getDesporto() {
        return desporto;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getPontosTotais() {
        return pontosTotais;
    }

    public ArrayList<String> getElementosGrupo() {
        return elementosGrupo;
    }

    public void setPontosTotais(int pontosTotais) {
        this.pontosTotais = pontosTotais;
    }

    public void setElementosGrupo(ArrayList<String> elementosGrupo) {
        this.elementosGrupo = elementosGrupo;
    }

    public void addElementToList(String element) {
        elementosGrupo.add(element);
    }

}

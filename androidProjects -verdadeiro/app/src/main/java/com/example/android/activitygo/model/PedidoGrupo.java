package com.example.android.activitygo.model;

public class PedidoGrupo {

    private String useQueQuerEntrar;
    private String criador;
    private String nomeGrupo;

    public PedidoGrupo() {
    }

    public PedidoGrupo(String useQueQuerEntrar, String nomeGrupo, String criador) {
        this.useQueQuerEntrar = useQueQuerEntrar;
        this.nomeGrupo = nomeGrupo;
        this.criador = criador;
    }

    public String getUseQueQuerEntrar() {
        return this.useQueQuerEntrar;
    }

    public String getNomeGrupo() {
        return this.nomeGrupo;
    }

    public String getCriador() {
        return this.criador;
    }

}

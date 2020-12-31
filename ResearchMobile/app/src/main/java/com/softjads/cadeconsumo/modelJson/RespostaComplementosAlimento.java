package com.softjads.cadeconsumo.modelJson;

import java.io.Serializable;

public class RespostaComplementosAlimento implements Serializable {

    private String id_crianca;
    private String id_alimento;
    private String descricao;

    public String getId_crianca() {
        return id_crianca;
    }

    public void setId_crianca(String id_crianca) {
        this.id_crianca = id_crianca;
    }

    public String getId_alimento() {
        return id_alimento;
    }

    public void setId_alimento(String id_alimento) {
        this.id_alimento = id_alimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

package com.softjads.cadeconsumo.modelJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RespostaComplementosAlimento implements Serializable {

    @SerializedName("id_crianca")
    @Expose
    private String id_crianca;

    @SerializedName("id_alimento")
    @Expose
    private String id_alimento;

    @SerializedName("descricao")
    @Expose
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

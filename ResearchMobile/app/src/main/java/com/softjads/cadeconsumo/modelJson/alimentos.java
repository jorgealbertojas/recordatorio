package com.softjads.cadeconsumo.modelJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class alimentos implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("codigo")
    @Expose
    private String codigo;

    @SerializedName("descricao")
    @Expose
    private String descricao;

    @SerializedName("gruposAlimentares")
    @Expose
    private List<String> gruposAlimentares;

    @SerializedName("modosPreparacao")
    @Expose
    private List<String> modosPreparacao;

    @SerializedName("medidasCaseiras")
    @Expose
    private List<medidasCaseiras> medidasCaseiras;

    @SerializedName("adicoes")
    @Expose
    private List<String> adicoes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<String> getGruposAlimentares() {
        return gruposAlimentares;
    }

    public void setGruposAlimentares(List<String> gruposAlimentares) {
        this.gruposAlimentares = gruposAlimentares;
    }

    public List<String> getModosPreparacao() {
        return modosPreparacao;
    }

    public void setModosPreparacao(List<String> modosPreparacao) {
        this.modosPreparacao = modosPreparacao;
    }

    public List<com.softjads.cadeconsumo.modelJson.medidasCaseiras> getMedidasCaseiras() {
        return medidasCaseiras;
    }

    public void setMedidasCaseiras(List<com.softjads.cadeconsumo.modelJson.medidasCaseiras> medidasCaseiras) {
        this.medidasCaseiras = medidasCaseiras;
    }

    public List<String> getAdicoes() {
        return adicoes;
    }

    public void setAdicoes(List<String> adicoes) {
        this.adicoes = adicoes;
    }
}

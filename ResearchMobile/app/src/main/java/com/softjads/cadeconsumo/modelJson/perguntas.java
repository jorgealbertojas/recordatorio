package com.softjads.cadeconsumo.modelJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class perguntas implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("identUnica")
    @Expose
    private String identUnica;

    @SerializedName("descricao")
    @Expose
    private String descricao;

    @SerializedName("ordem")
    @Expose
    private Integer ordem;

    @SerializedName("personalizacao")
    @Expose
    private String personalizacao;

    @SerializedName("bloco")
    @Expose
    private String bloco;

    @SerializedName("itens")
    @Expose
    private List<itens> itens;

    public List<com.softjads.cadeconsumo.modelJson.itens> getItens() {
        return itens;
    }

    public void setItens(List<com.softjads.cadeconsumo.modelJson.itens> itens) {
        this.itens = itens;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentUnica() {
        return identUnica;
    }

    public void setIdentUnica(String identUnica) {
        this.identUnica = identUnica;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public String getPersonalizacao() {
        return personalizacao;
    }

    public void setPersonalizacao(String personalizacao) {
        this.personalizacao = personalizacao;
    }

    public String getBloco() {
        return bloco;
    }

    public void setBloco(String bloco) {
        this.bloco = bloco;
    }
}

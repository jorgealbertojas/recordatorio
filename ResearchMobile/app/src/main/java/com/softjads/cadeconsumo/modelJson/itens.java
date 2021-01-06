package com.softjads.cadeconsumo.modelJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class itens implements Serializable {
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

    @SerializedName("tipo")
    @Expose
    private Integer tipo;

    @SerializedName("minimo")
    @Expose
    private Integer minimo;

    @SerializedName("maximo")
    @Expose
    private Integer maximo;

    @SerializedName("perguntaDestino")
    @Expose
    private String perguntaDestino;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getMinimo() {
        return minimo;
    }

    public void setMinimo(Integer minimo) {
        this.minimo = minimo;
    }

    public Integer getMaximo() {
        return maximo;
    }

    public void setMaximo(Integer maximo) {
        this.maximo = maximo;
    }

    public String getPerguntaDestino() {
        return perguntaDestino;
    }

    public void setPerguntaDestino(String perguntaDestino) {
        this.perguntaDestino = perguntaDestino;
    }

    public String getIdentUnica() {
        return identUnica;
    }

    public void setIdentUnica(String identUnica) {
        this.identUnica = identUnica;
    }
}

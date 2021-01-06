package com.softjads.cadeconsumo.modelJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Crianca implements Serializable {

    @SerializedName("nome")
    @Expose
    private String nome;

    @SerializedName("nomeEscola")
    @Expose
    private String nomeEscola;

    @SerializedName("codigo")
    @Expose
    private String codigo;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("ultimaResposta")
    @Expose
    private RespostaAdd ultimaResposta;

    @SerializedName("todasRespostas")
    @Expose
    private List<RespostaAdd> todasRespostas;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeEscola() {
        return nomeEscola;
    }

    public void setNomeEscola(String nomeEscola) {
        this.nomeEscola = nomeEscola;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<RespostaAdd> getTodasRespostas() {
        return todasRespostas;
    }

    public void setTodasRespostas(List<RespostaAdd> todasRespostas) {
        this.todasRespostas = todasRespostas;
    }

    public RespostaAdd getUltimaResposta() {
        return ultimaResposta;
    }

    public void setUltimaResposta(RespostaAdd ultimaResposta) {
        this.ultimaResposta = ultimaResposta;
    }
}

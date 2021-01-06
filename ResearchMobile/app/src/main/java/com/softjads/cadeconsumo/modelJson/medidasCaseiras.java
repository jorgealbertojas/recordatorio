package com.softjads.cadeconsumo.modelJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class medidasCaseiras implements Serializable {
    @SerializedName("codigo")
    @Expose
    private String codigo;

    @SerializedName("nome")
    @Expose
    private String nome;

    @SerializedName("fotos")
    @Expose
    private List<String> fotos;

    @SerializedName("abreSelecaoQuantidade")
    @Expose
    private String abreSelecaoQuantidade;

    @SerializedName("tipoFoto")
    @Expose
    private String tipoFoto;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public String getAbreSelecaoQuantidade() {
        return abreSelecaoQuantidade;
    }

    public void setAbreSelecaoQuantidade(String abreSelecaoQuantidade) {
        this.abreSelecaoQuantidade = abreSelecaoQuantidade;
    }

    public String getTipoFoto() {
        return tipoFoto;
    }

    public void setTipoFoto(String tipoFoto) {
        this.tipoFoto = tipoFoto;
    }


}

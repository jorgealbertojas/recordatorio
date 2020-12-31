package com.softjads.cadeconsumo.modelJson;

import java.io.Serializable;
import java.util.List;

public class medidasCaseiras implements Serializable {
    private String codigo;
    private String nome;
    private List<String> fotos;
    private String abreSelecaoQuantidade;
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

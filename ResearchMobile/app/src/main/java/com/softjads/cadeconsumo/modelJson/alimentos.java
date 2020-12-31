package com.softjads.cadeconsumo.modelJson;

import java.io.Serializable;
import java.util.List;

public class alimentos implements Serializable {

    private String id;
    private String codigo;
    private String descricao;
    private List<String> gruposAlimentares;
    private List<String> modosPreparacao;
    private List<medidasCaseiras> medidasCaseiras;
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

package com.example.jorgealberto.cadeconsumo.modelJson;

import java.io.Serializable;
import java.util.List;

public class perguntas implements Serializable {

    private String id;
    private String identUnica;
    private String descricao;
    private Integer ordem;
    private String personalizacao;
    private String bloco;
    private List<itens> itens;

    public List<com.example.jorgealberto.cadeconsumo.modelJson.itens> getItens() {
        return itens;
    }

    public void setItens(List<com.example.jorgealberto.cadeconsumo.modelJson.itens> itens) {
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

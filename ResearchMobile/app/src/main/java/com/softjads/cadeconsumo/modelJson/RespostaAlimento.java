package com.softjads.cadeconsumo.modelJson;

import java.io.Serializable;

public class RespostaAlimento implements Serializable {
    private String id_crianca;
    private String id_alimento;
    private String codigo;
    private String descricao;
    private String alimento_refeicao;

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

    public String getAlimento_refeicao() {
        return alimento_refeicao;
    }

    public void setAlimento_refeicao(String alimento_refeicao) {
        this.alimento_refeicao = alimento_refeicao;
    }
}

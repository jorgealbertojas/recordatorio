package com.softjads.cadeconsumo.modelJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RespostaAlimento implements Serializable {
    @SerializedName("id_crianca")
    @Expose
    private String id_crianca;

    @SerializedName("id_alimento")
    @Expose
    private String id_alimento;

    @SerializedName("codigo")
    @Expose
    private String codigo;

    @SerializedName("descricao")
    @Expose
    private String descricao;

    @SerializedName("alimento_refeicao")
    @Expose
    private String alimento_refeicao;

    @SerializedName("alimento_order")
    @Expose
    private Integer alimento_order;

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

    public int getAlimento_order() {
        return alimento_order;
    }

    public void setAlimento_order(Integer alimento_order) {
        this.alimento_order = alimento_order;
    }
}

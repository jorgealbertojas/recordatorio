package com.softjads.cadeconsumo.modelJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RespostaRefeicao implements Serializable {

    @SerializedName("refeicao")
    @Expose
    private String refeicao;

    @SerializedName("local")
    @Expose
    private String local;

    @SerializedName("atividade")
    @Expose
    private List<String> atividade;

    public String getRefeicao() {
        return refeicao;
    }

    public void setRefeicao(String refeicao) {
        this.refeicao = refeicao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public List<String> getAtividade() {
        return atividade;
    }

    public void setAtividade(List<String> atividade) {
        this.atividade = atividade;
    }
}

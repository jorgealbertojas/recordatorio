package com.softjads.cadeconsumo.modelJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ListPerguntas  implements Serializable {
    @SerializedName("perguntas")
    @Expose
    private List<perguntas> perguntas;

    public List<perguntas> getListaperguntas() {
        return perguntas;
    }

    public void setListaperguntas(List<perguntas> listaperguntas) {
        this.perguntas = listaperguntas;
    }
}

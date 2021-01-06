package com.softjads.cadeconsumo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class contagemGrupoAlimentar implements Serializable {
    @SerializedName("grupoAlimentar")
    @Expose
    private String grupoAlimentar;

    @SerializedName("totalAlimentos")
    @Expose
    private int totalAlimentos;

    public String getGrupoAlimentar() {
        return grupoAlimentar;
    }

    public void setGrupoAlimentar(String grupoAlimentar) {
        this.grupoAlimentar = grupoAlimentar;
    }

    public int getTotalAlimentos() {
        return totalAlimentos;
    }

    public void setTotalAlimentos(int totalAlimentos) {
        this.totalAlimentos = totalAlimentos;
    }
}

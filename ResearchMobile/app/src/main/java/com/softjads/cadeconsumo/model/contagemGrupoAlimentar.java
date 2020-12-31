package com.softjads.cadeconsumo.model;

import java.io.Serializable;

public class contagemGrupoAlimentar implements Serializable {
    private String grupoAlimentar;
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

package com.softjads.cadeconsumo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AlimentosCompletos<T>  {
    @SerializedName("alimentos")
    @Expose
    public List<com.softjads.cadeconsumo.modelJson.alimentos> alimentos;

    @SerializedName("contagemGrupoAlimentar")
    @Expose
    public List<contagemGrupoAlimentar> contagemGrupoAlimentar;

}

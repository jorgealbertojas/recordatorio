package com.softjads.cadeconsumo.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListWrapper<T>  {

    @SerializedName("perguntas")
    @Expose
    public List<T> perguntas;

}

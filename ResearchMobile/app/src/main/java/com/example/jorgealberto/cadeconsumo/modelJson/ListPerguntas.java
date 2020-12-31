package com.example.jorgealberto.cadeconsumo.modelJson;

import java.io.Serializable;
import java.util.List;

public class ListPerguntas  implements Serializable {
    private List<perguntas> perguntas;

    public List<perguntas> getListaperguntas() {
        return perguntas;
    }

    public void setListaperguntas(List<perguntas> listaperguntas) {
        this.perguntas = listaperguntas;
    }
}

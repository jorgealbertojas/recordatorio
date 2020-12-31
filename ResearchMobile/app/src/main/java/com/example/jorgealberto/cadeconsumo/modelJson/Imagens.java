package com.example.jorgealberto.cadeconsumo.modelJson;

import java.io.Serializable;

public class Imagens implements Serializable {

    private String url;
    private String id;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

package com.example.jorgealberto.researchmobile.modelJson;

import java.io.Serializable;

public class RespostaAdd implements Serializable {

    private String id;
    private String identUnicaPergunta = "";
    private String idPergunta;
    private String idItemPergunta;
    private String valor;
    private String dataCadastro;
    private String tagLivre;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPergunta() {
        return idPergunta;
    }

    public void setIdPergunta(String idPergunta) {
        this.idPergunta = idPergunta;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }


    public String getTagLivre() {
        return tagLivre;
    }

    public void setTagLivre(String tagLivre) {
        this.tagLivre = tagLivre;
    }

    public String getIdItemPergunta() {
        return idItemPergunta;
    }

    public void setIdItemPergunta(String idItemPergunta) {
        this.idItemPergunta = idItemPergunta;
    }

    public String getIdentUnicaPergunta() {
        return identUnicaPergunta;
    }

    public void setIdentUnicaPergunta(String identUnicaPergunta) {
        this.identUnicaPergunta = identUnicaPergunta;
    }
}

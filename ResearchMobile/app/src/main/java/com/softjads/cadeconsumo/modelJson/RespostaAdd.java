package com.softjads.cadeconsumo.modelJson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RespostaAdd implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("identUnicaPergunta")
    @Expose
    private String identUnicaPergunta = "";

    @SerializedName("idPergunta")
    @Expose
    private String idPergunta;

    @SerializedName("idItemPergunta")
    @Expose
    private String idItemPergunta;

    @SerializedName("idAlimento")
    @Expose
    private String idAlimento;

    @SerializedName("valor")
    @Expose
    private String valor;

    @SerializedName("dataCadastro")
    @Expose
    private String dataCadastro;

    @SerializedName("tagLivre")
    @Expose
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

    public String getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(String idAlimento) {
        this.idAlimento = idAlimento;
    }
}

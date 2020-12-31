package com.softjads.cadeconsumo.model;

/**
 * Created by jorgealberto on 10/09/2016.
 */
import java.io.Serializable;

public class Escola implements Serializable{


    private int ID_ESCOLA = 0;
    private int COD_UF = 0;
    private int COD_MUNIC = 0;
    private int COD_BAIRRO = 0;
    private String NOME_ESCOLA = "";
    private String ENDERECO = "";
    private String NUMERO = "";
    private String TIPO = "";
    private String SUBTIPO = "";
    private String REGIAO = "";

    public int getID_ESCOLA() {
        return ID_ESCOLA;
    }
    public void setID_ESCOLA(int iD_ESCOLA) {
        ID_ESCOLA = iD_ESCOLA;
    }
    public int getCOD_UF() {
        return COD_UF;
    }
    public void setCOD_UF(int cOD_UF) {
        COD_UF = cOD_UF;
    }
    public int getCOD_MUNIC() {
        return COD_MUNIC;
    }
    public void setCOD_MUNIC(int cOD_MUNIC) {
        COD_MUNIC = cOD_MUNIC;
    }
    public int getCOD_BAIRRO() {
        return COD_BAIRRO;
    }
    public void setCOD_BAIRRO(int cOD_BAIRRO) {
        COD_BAIRRO = cOD_BAIRRO;
    }
    public String getNOME_ESCOLA() {
        return NOME_ESCOLA;
    }
    public void setNOME_ESCOLA(String nOME_ESCOLA) {
        NOME_ESCOLA = nOME_ESCOLA;
    }
    public String getENDERECO() {
        return ENDERECO;
    }
    public void setENDERECO(String eNDERECO) {
        ENDERECO = eNDERECO;
    }
    public String getNUMERO() {
        return NUMERO;
    }
    public void setNUMERO(String nUMERO) {
        NUMERO = nUMERO;
    }
    public String getTIPO() {
        return TIPO;
    }
    public void setTIPO(String tIPO) {
        TIPO = tIPO;
    }
    public String getSUBTIPO() {
        return SUBTIPO;
    }
    public void setSUBTIPO(String sUBTIPO) {
        SUBTIPO = sUBTIPO;
    }
    public String getREGIAO() {
        return REGIAO;
    }
    public void setREGIAO(String rEGIAO) {
        REGIAO = rEGIAO;
    }


}


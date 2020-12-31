package com.example.jorgealberto.cadeconsumo.model;

/**
 * Created by jorgealberto on 10/09/2016.
 */
import java.io.Serializable;

public class Controle_inicio implements Serializable{

    private int ID_ALUNO = 0;
    private int ID_USUARIO = 0;
    private String NM_USUARIO = "";
    private String  DATA_INICIO = "";
    private String  INICIO = "";
    private String  LONGITUDE = "";
    private String  LATITUDE = "";
    private String GRAVACAO = "";
    private String NM_PESQUISA = "";
    private String PREVISAO = "";


    public String getNM_PESQUISA() {
        return NM_PESQUISA;
    }
    public void setNM_PESQUISA(String nM_PESQUISA) {
        NM_PESQUISA = nM_PESQUISA;
    }
    public String getPREVISAO() {
        return PREVISAO;
    }
    public void setPREVISAO(String pREVISAO) {
        PREVISAO = pREVISAO;
    }
    public String getGRAVACAO() {
        return GRAVACAO;
    }
    public void setGRAVACAO(String gRAVACAO) {
        GRAVACAO = gRAVACAO;
    }
    public int getID_ALUNO() {
        return ID_ALUNO;
    }
    public void setID_ALUNO(int iD_ALUNO) {
        ID_ALUNO = iD_ALUNO;
    }
    public int getID_USUARIO() {
        return ID_USUARIO;
    }
    public void setID_USUARIO(int iD_USUARIO) {
        ID_USUARIO = iD_USUARIO;
    }
    public String getDATA_INICIO() {
        return DATA_INICIO;
    }
    public void setDATA_INICIO(String dATA_INICIO) {
        DATA_INICIO = dATA_INICIO;
    }
    public String getINICIO() {
        return INICIO;
    }
    public void setINICIO(String iNICIO) {
        INICIO = iNICIO;
    }
    public String getLATITUDE() {
        return LATITUDE;
    }
    public void setLATITUDE(String lATITUDE) {
        LATITUDE = lATITUDE;
    }
    public String getLONGITUDE() {
        return LONGITUDE;
    }
    public void setLONGITUDE(String lONGITUDE) {
        LONGITUDE = lONGITUDE;
    }
    public String getNM_USUARIO() {
        return NM_USUARIO;
    }
    public void setNM_USUARIO(String nM_USUARIO) {
        NM_USUARIO = nM_USUARIO;
    }


}

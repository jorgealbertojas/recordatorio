package com.example.jorgealberto.cadeconsumo.model;

/**
 * Created by jorgealberto on 10/09/2016.
 */
import java.io.Serializable;

public class Controle_fim implements Serializable{
    private int ID_ALUNO = 0;
    private int ID_USUARIO = 0;
    private String NM_USUARIO = "";
    private String  DATA_FIM = "";
    private String  FIM = "";
    private String  LONGITUDE = "";
    private String  LATITUDE = "";

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
    public String getDATA_FIM() {
        return DATA_FIM;
    }
    public void setDATA_FIM(String dATA_FIM) {
        DATA_FIM = dATA_FIM;
    }
    public String getFIM() {
        return FIM;
    }
    public void setFIM(String fIM) {
        FIM = fIM;
    }

    public String getNM_USUARIO() {
        return NM_USUARIO;
    }
    public void setNM_USUARIO(String nM_USUARIO) {
        NM_USUARIO = nM_USUARIO;
    }
    public String getLONGITUDE() {
        return LONGITUDE;
    }
    public void setLONGITUDE(String lONGITUDE) {
        LONGITUDE = lONGITUDE;
    }
    public String getLATITUDE() {
        return LATITUDE;
    }
    public void setLATITUDE(String lATITUDE) {
        LATITUDE = lATITUDE;
    }

}


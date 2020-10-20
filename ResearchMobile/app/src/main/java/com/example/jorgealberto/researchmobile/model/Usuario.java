package com.example.jorgealberto.researchmobile.model;

/**
 * Created by jorgealberto on 10/09/2016.
 */
import java.io.Serializable;

public class Usuario implements Serializable {

    public  int ID  = 0;
    public  String NM_USUARIO  = "";
    public  String SENHA  = "";
    public  int  ID_GRUPO  = 0;
    public  int  ID_CLIENTE  = 0;
    public  String NOME  = "";

    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }
    public String getNM_USUARIO() {
        return NM_USUARIO;
    }
    public void setNM_USUARIO(String nM_USUARIO) {
        NM_USUARIO = nM_USUARIO;
    }
    public String getSENHA() {
        return SENHA;
    }
    public void setSENHA(String sENHA) {
        SENHA = sENHA;
    }
    public int getID_GRUPO() {
        return ID_GRUPO;
    }
    public void setID_GRUPO(int iD_GRUPO) {
        ID_GRUPO = iD_GRUPO;
    }
    public int getID_CLIENTE() {
        return ID_CLIENTE;
    }
    public void setID_CLIENTE(int iD_CLIENTE) {
        ID_CLIENTE = iD_CLIENTE;
    }
    public String getNOME() {
        return NOME;
    }
    public void setNOME(String nOME) {
        NOME = nOME;
    }



}


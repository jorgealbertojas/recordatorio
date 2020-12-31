package com.softjads.cadeconsumo.model;

/**
 * Created by jorgealberto on 10/09/2016.
 */
import java.io.Serializable;

public class pesquisa_usuario implements Serializable{

    public  int	ID = 0;
    public  int ID_PESQUISA = 0;
    public  int ID_USUARIO = 0;
    public  int ID_CLIENTE = 0;
    public  String DT_CADASTRO  = "";
    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }
    public int getID_PESQUISA() {
        return ID_PESQUISA;
    }
    public void setID_PESQUISA(int iD_PESQUISA) {
        ID_PESQUISA = iD_PESQUISA;
    }
    public int getID_USUARIO() {
        return ID_USUARIO;
    }
    public void setID_USUARIO(int iD_USUARIO) {
        ID_USUARIO = iD_USUARIO;
    }
    public int getID_CLIENTE() {
        return ID_CLIENTE;
    }
    public void setID_CLIENTE(int iD_CLIENTE) {
        ID_CLIENTE = iD_CLIENTE;
    }
    public String getDT_CADASTRO() {
        return DT_CADASTRO;
    }
    public void setDT_CADASTRO(String dT_CADASTRO) {
        DT_CADASTRO = dT_CADASTRO;
    }


}


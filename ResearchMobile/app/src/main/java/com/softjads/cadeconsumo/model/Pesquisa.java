package com.softjads.cadeconsumo.model;

/**
 * Created by jorgealberto on 10/09/2016.
 */
import java.io.Serializable;

public class Pesquisa implements Serializable{
    public  int 	ID_CLIENTE = 0;
    public  int ID_PESQUISA = 0;
    public  String DSC_PESQUISA = "";
    public  int AUTOMATICO = 0;
    public String PREVISAO = "";

    public String getPREVISAO() {
        return PREVISAO;
    }
    public void setPREVISAO(String pREVISAO) {
        PREVISAO = pREVISAO;
    }
    public int getID_CLIENTE() {
        return ID_CLIENTE;
    }
    public void setID_CLIENTE(int iD_CLIENTE) {
        ID_CLIENTE = iD_CLIENTE;
    }
    public int getID_PESQUISA() {
        return ID_PESQUISA;
    }
    public void setID_PESQUISA(int iD_PESQUISA) {
        ID_PESQUISA = iD_PESQUISA;
    }
    public String getDSC_PESQUISA() {
        return DSC_PESQUISA;
    }
    public void setDSC_PESQUISA(String dSC_PESQUISA) {
        DSC_PESQUISA = dSC_PESQUISA;
    }
    public int getAUTOMATICO() {
        return AUTOMATICO;
    }
    public void setAUTOMATICO(int aUTOMATICO) {
        AUTOMATICO = aUTOMATICO;
    }

}


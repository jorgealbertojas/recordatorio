package com.example.jorgealberto.researchmobile.model;

/**
 * Created by jorgealberto on 10/09/2016.
 */
import java.io.Serializable;

public class Turma implements Serializable{

    private int ID_TURMA;
    private String NOME_TURMA;
    private String TURNO;
    private String ID_ESCOLA;

    public int getID_TURMA() {
        return ID_TURMA;
    }
    public void setID_TURMA(int iD_TURMA) {
        ID_TURMA = iD_TURMA;
    }
    public String getNOME_TURMA() {
        return NOME_TURMA;
    }
    public void setNOME_TURMA(String nOME_TURMA) {
        NOME_TURMA = nOME_TURMA;
    }
    public String getTURNO() {
        return TURNO;
    }
    public void setTURNO(String tURNO) {
        TURNO = tURNO;
    }
    public String getID_ESCOLA() {
        return ID_ESCOLA;
    }
    public void setID_ESCOLA(String iD_ESCOLA) {
        ID_ESCOLA = iD_ESCOLA;
    }


}


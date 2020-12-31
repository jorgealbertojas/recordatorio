package com.example.jorgealberto.cadeconsumo.model;

/**
 * Created by jorgealberto on 10/09/2016.
 */
import java.io.Serializable;

public class Aluno implements Serializable {
    public  int ID_ALUNO  = 0;
    public  int ID_ESCOLA  = 0;
    public  int   ID_TURMA = 0;
    public  String NOME = "";
    public  String DT_NASC = "";
    public  String SEXO = "";


    public int getID_ALUNO() {
        return ID_ALUNO;
    }
    public void setID_ALUNO(int iD_ALUNO) {
        ID_ALUNO = iD_ALUNO;
    }
    public int getID_ESCOLA() {
        return ID_ESCOLA;
    }
    public void setID_ESCOLA(int iD_ESCOLA) {
        ID_ESCOLA = iD_ESCOLA;
    }
    public int getID_TURMA() {
        return ID_TURMA;
    }
    public void setID_TURMA(int iD_TURMA) {
        ID_TURMA = iD_TURMA;
    }
    public String getNOME() {
        return NOME;
    }
    public void setNOME(String nOME) {
        NOME = nOME;
    }
    public String getDT_NASC() {
        return DT_NASC;
    }
    public void setDT_NASC(String dT_NASC) {
        DT_NASC = dT_NASC;
    }
    public String getSEXO() {
        return SEXO;
    }
    public void setSEXO(String sEXO) {
        SEXO = sEXO;
    }


}


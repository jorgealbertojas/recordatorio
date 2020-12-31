package com.softjads.cadeconsumo.model;

/**
 * Created by jorgealberto on 10/09/2016.
 */
import java.io.Serializable;

public class Alimento implements Serializable{
    private String ID_ALUNO;
    private String ID;
    private String CODIGO;
    private String DESCRICAO;


    // ATENÇÃO NA ESQUCER DE INICIAR
    public Alimento(){
        this.ID_ALUNO = "0";
        this.ID = "0";
        this.CODIGO = "0";
        this.DESCRICAO = "0";
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCODIGO() {
        return CODIGO;
    }

    public void setCODIGO(String CODIGO) {
        this.CODIGO = CODIGO;
    }

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }

    public String getID_ALUNO() {
        return ID_ALUNO;
    }

    public void setID_ALUNO(String ID_ALUNO) {
        this.ID_ALUNO = ID_ALUNO;
    }

}


package com.example.jorgealberto.researchmobile.model;

/**
 * Created by jorgealberto on 10/09/2016.
 */
import java.io.Serializable;

public class Adicoes implements Serializable{
    private String ID_ALUNO;
    private String ID_ALIMENTO;
    private String DESCRICAO;


    // ATENÇÃO NA ESQUCER DE INICIAR
    public Adicoes(){
        this.ID_ALUNO = "0";
        this.ID_ALIMENTO = "0";
        this.DESCRICAO = "0";
    }

    public String getID_ALUNO() {
        return ID_ALUNO;
    }

    public void setID_ALUNO(String ID_ALUNO) {
        this.ID_ALUNO = ID_ALUNO;
    }

    public String getID_ALIMENTO() {
        return ID_ALIMENTO;
    }

    public void setID_ALIMENTO(String ID_ALIMENTO) {
        this.ID_ALIMENTO = ID_ALIMENTO;
    }

    public String getDESCRICAO() {
        return DESCRICAO;
    }

    public void setDESCRICAO(String DESCRICAO) {
        this.DESCRICAO = DESCRICAO;
    }
}


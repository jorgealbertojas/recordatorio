package com.example.jorgealberto.cadeconsumo.model;

/**
 * Created by jorgealberto on 10/09/2016.
 */
import java.io.Serializable;

public class Resposta implements Serializable{
    private int ID_ALUNO;
    private int ID_PERGUNTA;
    private String ID_OPCAO;
    private String VALOR;
    private int ID_OPCAO_PESSOA;

    // ATENÇÃO NA ESQUCER DE INICIAR
    public Resposta(){
        this.ID_ALUNO = 0;
        this.ID_PERGUNTA = 0;
        this.ID_OPCAO = "0";
        this.VALOR = "";
        this.ID_OPCAO_PESSOA = 0;
    }

    public int getID_ALUNO() {
        return ID_ALUNO;
    }
    public void setID_ALUNO(int iD_ALUNO) {
        ID_ALUNO = iD_ALUNO;
    }
    public int getID_PERGUNTA() {
        return ID_PERGUNTA;
    }
    public void setID_PERGUNTA(int iD_PERGUNTA) {
        ID_PERGUNTA = iD_PERGUNTA;
    }
    public int getID_OPCAO_PESSOA() {   return ID_OPCAO_PESSOA;
    }
    public void setID_OPCAO_PESSOA(int ID_OPCAO_PESSOA) {
        this.ID_OPCAO_PESSOA = ID_OPCAO_PESSOA;
    }

    public String getID_OPCAO() {
        return ID_OPCAO;
    }
    public void setID_OPCAO(String iD_OPCAO) {
        ID_OPCAO = iD_OPCAO;
    }
    public String getVALOR() {
        return VALOR;
    }
    public void setVALOR(String vALOR) {
        VALOR = vALOR;
    }


}


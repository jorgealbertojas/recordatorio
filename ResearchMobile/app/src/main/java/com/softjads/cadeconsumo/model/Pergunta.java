package com.softjads.cadeconsumo.model;

import java.io.Serializable;

public class Pergunta implements Serializable{
	
	private  String ID_PERGUNTA;
	private String DESCRICAO;
	private int ID_BLOCO;
	private String ID_PERGUNTA_JSON;
	private int NUM_SUBFORMULARIO;
	private String COD_PERGUNTA;
	private int ORDENA;

	public String getCOD_PERGUNTA() {
		return COD_PERGUNTA;
	}

	public void setCOD_PERGUNTA(String COD_PERGUNTA) {
		this.COD_PERGUNTA = COD_PERGUNTA;
	}

	public int getNUM_SUBFORMULARIO() {
		return NUM_SUBFORMULARIO;
	}

	public String getID_PERGUNTA() {
		return ID_PERGUNTA;
	}

	public void setID_PERGUNTA(String ID_PERGUNTA) {
		this.ID_PERGUNTA = ID_PERGUNTA;
	}

	public String getID_PERGUNTA_JSON() {
		return ID_PERGUNTA_JSON;
	}

	public void setID_PERGUNTA_JSON(String ID_PERGUNTA_JSON) {
		this.ID_PERGUNTA_JSON = ID_PERGUNTA_JSON;
	}

	public void setNUM_SUBFORMULARIO(int NUM_SUBFORMULARIO) {
		this.NUM_SUBFORMULARIO = NUM_SUBFORMULARIO;
	}

	// ATEN��O NA ESQUCER DE INICIAR
	public Pergunta(){
		this.ID_PERGUNTA = "0";
		this.DESCRICAO = "";
		this.ID_BLOCO = 0;
		this.NUM_SUBFORMULARIO = 0;
		this.NUM_SUBFORMULARIO = 0;
		this.ORDENA = 0;
	}
	
	public String getID_PERGUNTAS() {
		return ID_PERGUNTA;
	}
	public void setID_PERGUNTAS(String ID_PERGUNTAS) {
		this.ID_PERGUNTA = ID_PERGUNTAS;
	}
	public long getID_BLOCO() {
		return ID_BLOCO;
	}
	public void setID_BLOCO(int ID_BLOCO) {
		this.ID_BLOCO = ID_BLOCO;
	}

	public String getDESCRICAO() {
		return DESCRICAO;
	}
	public void setDESCRICAO(String DESCRICAO) {
		this.DESCRICAO = DESCRICAO;
	}

	public int getORDENA() {
		return ORDENA;
	}

	public void setORDENA(int ORDENA) {
		this.ORDENA = ORDENA;
	}
}

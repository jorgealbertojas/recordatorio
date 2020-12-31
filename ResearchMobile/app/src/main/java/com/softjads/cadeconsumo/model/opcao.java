package com.softjads.cadeconsumo.model;

import java.io.Serializable;

public class opcao implements Serializable{

	 private String ID_OPCAO;
	 private int MAXIMO;
	 private int VALOR;
	 private String OPCAO;
	 private String PERSONALIZACAO;
	 private int MINIMO;
	 private String ID_PERGUNTA;
	 private String SALTO;
	 private int ORDENA;
	 
	 // ATEN��O NA ESQUECER DE INICIAR
	 public opcao(){
		 this.ID_OPCAO = "0";
		 this.MAXIMO = 0;
		 this.VALOR = 0;
		 this.OPCAO = "";
		 this.PERSONALIZACAO = "";
		 this.MINIMO = 0;
		 this.ID_PERGUNTA = "0";
		 this.SALTO = "0";
		 this.ORDENA = 0;
	 }

	public String getPERSONALIZACAO() {
		return PERSONALIZACAO;
	}

	public void setPERSONALIZACAO(String PERSONALIZACAO) {
		this.PERSONALIZACAO = PERSONALIZACAO;
	}

	public String getID_OPCAO() {
		return ID_OPCAO;
	}
	public void setID_OPCAO(String iD_OPCAO) {
		ID_OPCAO = iD_OPCAO;
	}
	public long getMAXIMO() {
		return MAXIMO;
	}
	public void setMAXIMO(int mAXIMO) {
		MAXIMO = mAXIMO;
	}
	public long getVALOR() {
		return VALOR;
	}
	public void setVALOR(int vALOR) {
		VALOR = vALOR;
	}
	public String getOPCAO() {
		return OPCAO;
	}
	public void setOPCAO(String oPCAO) {
		OPCAO = oPCAO;
	}
	public long getMINIMO() {
		return MINIMO;
	}
	public void setMINIMO(int mINIMO) {
		MINIMO = mINIMO;
	}
	public String getID_PERGUNTA() {
		return ID_PERGUNTA;
	}
	public void setID_PERGUNTA(String iD_PERGUNTA) {
		ID_PERGUNTA = iD_PERGUNTA;
	}
	public String getSALTO() {
		return SALTO;
	}
	public void setSALTO(String sALTO) {
		SALTO = sALTO;
	}
	public long getORDENA() {
		return ORDENA;
	}
	public void setORDENA(int oRDENA) {
		ORDENA = oRDENA;
	}


}

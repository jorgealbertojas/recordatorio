package com.example.jorgealberto.researchmobile.model;

/**
 * Created by sspbr on 11/05/2016.
 */
public class Ocorrencia {

    public String nameMain = "ocorrencia";

    private String NUM_REFERENCIA  = "";
    private String INCIDENCIAS_GESTION  = "";
    private String COD_CONC_INI  = "";
    private String NOMBRE_PROCEDENCIA  = "";
    private String DIRECCION_ALFA  = "";
    private String TIEMPO_ASIG  = "";
    private String TIEMPO_ATEN  = "";
    private String LATITUDE  = "";
    private String LONGITUDE  = "";

    public String getEndereco() {
        return DIRECCION_ALFA;
    }

    public void setEndereco(String endereco) {
        DIRECCION_ALFA = endereco;
    }

    public String getConceito() {
        return COD_CONC_INI;
    }

    public void setConceito(String conceito) {
        COD_CONC_INI = conceito;
    }

    public String getEntrada() {
        return TIEMPO_ASIG;
    }

    public void setEntrada(String entrada) {
        TIEMPO_ASIG = entrada;
    }

    public String getTransmissao() {
        return TIEMPO_ATEN;
    }

    public void setTransmissao(String transmissao) {
        TIEMPO_ATEN = transmissao;
    }

    public String getReclamante() {
        return NOMBRE_PROCEDENCIA;
    }

    public void setReclamante(String reclamante) {
        NOMBRE_PROCEDENCIA = reclamante;
    }

    public String getIncidenciaGestao() {
        return INCIDENCIAS_GESTION;
    }

    public void setIncidenciaGestao(String incidenciaGestao) {
        INCIDENCIAS_GESTION = incidenciaGestao;
    }

    public String getId() {
        return NUM_REFERENCIA;
    }

    public void setId(String id) {
        NUM_REFERENCIA = id;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }



}

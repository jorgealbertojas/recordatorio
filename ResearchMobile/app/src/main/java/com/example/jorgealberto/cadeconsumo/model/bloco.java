package com.example.jorgealberto.cadeconsumo.model;

/**
 * Created by jorgealberto on 10/09/2016.
 */
import java.io.Serializable;

public class bloco implements Serializable{

    private long   ID_BLOCO;
    private String DESCBLOCO;
    private String INFORMACAO;

    // ATENÇÃO NA ESQUCER DE INICIAR
    public bloco(){
        this.ID_BLOCO = 0;
        this.DESCBLOCO = "";
        this.INFORMACAO = "";
    }


    public long getID_BLOCO() {
        return ID_BLOCO;
    }
    public void setID_BLOCO(long iD_BLOCO) {
        ID_BLOCO = iD_BLOCO;
    }
    public String getDESCBLOCO() {
        return DESCBLOCO;
    }
    public void setDESCBLOCO(String dESCBLOCO) {
        DESCBLOCO = dESCBLOCO;
    }
    public String getINFORMACAO() {
        return INFORMACAO;
    }
    public void setINFORMACAO(String iNFORMACAO) {
        INFORMACAO = iNFORMACAO;
    }


}


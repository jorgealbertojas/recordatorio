package com.softjads.cadeconsumo.util;

import android.content.Context;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Utility {
    /** Base URL for get Json */
    public final static String BASE_URL =
            "https://recordatorioapinovo.azure-api.net/";

    public final static String imagemURL = "https://recordatorioapi.azure-api.net/RecordatorioFuncNovo/foto/";

    /** Base URL for get Json */
    public final static String COMPLEMENT_URL = "/RecordatorioFuncNovo/questionario";

    public final static String COMPLEMENT_URL_ALIMENTO = "/RecordatorioFuncNovo/alimentoV2/";

    public final static String COMPLEMENT_URL_USUARIO = "/RecordatorioFuncNovo/usuario/login/";

    public final static String COMPLEMENT_URL_CREATE_USUARIO = "/RecordatorioFuncNovo/usuario/";

    public final static String COMPLEMENT_URL_CRIANCA = "/RecordatorioFuncNovo/crianca/";

    public final static String COMPLEMENT_URL_CRIANCA_FULL = "/RecordatorioFuncNovo/crianca/todas/full/";

    public final static String COMPLEMENT_URL_CRIANCA_DELETE = "/RecordatorioFuncNovo/crianca/";

    public final static String COMPLEMENT_URL_CRIANCA_CODIGO = "/RecordatorioFuncNovo/crianca/codigo/";

    public final static String COMPLEMENT_URL_ADICIONA_RESPOSTA = "/RecordatorioFuncNovo/resposta/";

    public final static String COMPLEMENT_URL_ADICIONA_RESPOSTA_DELETE = "/RecordatorioFuncNovo/RespostaDeletando/";

    public final static String COMPLEMENT_URL_RESPOSTA_CRIANCA_DELETE = "/RecordatorioFuncNovo/resposta/";

    public final static String COMPLEMENT_URL_RESPOSTA_ALIMENTO_DELETE = "/RecordatorioFuncNovo/alimento/";

    public static int countCaracter(String string){


        int count = 1;
        for(int i = 0; i < string.length(); i++) {
            if(string.charAt(i) == '|')
                count++;
        }
        return count;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static int positionCaracter(String string){
        int position = string.length();
        for(int i = 0; i < string.length(); i++) {
            if(string.charAt(i) == '|')
                return i;
        }
        return position;
    }

    public static int positionFirstCaracter(String string){
        int position = 0;
        for(int i = 0; i < string.length(); i++) {
            if(string.charAt(i) == '|')
                return i+1;
        }
        return position;
    }

    public static int getAge(int year, int month, int day) {

        int ageCount = 0;

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, noofyears;

        y = cal.get(Calendar.YEAR);// current year ,
        m = cal.get(Calendar.MONTH);// current month
        d = cal.get(Calendar.DAY_OF_MONTH);// current day
        cal.set(year, month, day);// here ur date
        noofyears = (int) (y - cal.get(Calendar.YEAR));


        if ((m < cal.get(Calendar.MONTH)) || ((m == cal.get(Calendar.MONTH)) && (d < cal.get(Calendar.DAY_OF_MONTH)))) {
            --noofyears;
        }

        if (noofyears != 0) {
            ageCount = noofyears;
        } else {
            ageCount = 0;
        }
        if (noofyears < 0){
            return ageCount;
        }

        return noofyears;
    }

    public static String getAmbiente(String string){
        String result  = "0";
        if (string.length() > 0){
           if (string.substring(string.length()-1,string.length()).equals("1")){
               result = "E";
           }else if (string.substring(string.length()-1,string.length()).equals("2")){
               result = "C";
           }else{
               result = "P";
           }

        }
        return result;
    }

}

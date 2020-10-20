package com.example.jorgealberto.researchmobile.util;

import android.content.Context;

public class Utility {
    /** Base URL for get Json */
    public final static String BASE_URL =
            "https://recordatorioapi.azure-api.net/";

    /** Base URL for get Json */
    public final static String COMPLEMENT_URL = "/RecordatorioFunc/questionario";

    public final static String COMPLEMENT_URL_ALIMENTO = "/RecordatorioFunc/alimento/";

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

}

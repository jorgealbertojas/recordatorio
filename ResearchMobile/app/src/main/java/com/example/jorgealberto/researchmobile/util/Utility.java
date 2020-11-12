package com.example.jorgealberto.researchmobile.util;

import android.content.Context;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

}

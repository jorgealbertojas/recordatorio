package com.example.jorgealberto.researchmobile.util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Date;

public class SharedPreferencesService {
    private final String SHARED_PREFERENCES = "shared";

    private static final String VISTORIA_FOTO = "vistoriafoto";



    private Activity activity;

    public SharedPreferencesService(Activity activity) {
        Log.e("LOGOUT", "SharedPreferencesService");
        this.activity = activity;
    }


    public void setVistoriaFoto(String foto, String numero) {
        SharedPreferences settings = activity.getSharedPreferences(SHARED_PREFERENCES, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(VISTORIA_FOTO+numero, foto);
        editor.apply();
    }

    public String getVistoriaFoto(String numero) {
        Log.e("LOGOUT", "getPrimeiraViagem");
        SharedPreferences settings = activity.getSharedPreferences(SHARED_PREFERENCES, Activity.MODE_PRIVATE);
        return settings.getString(VISTORIA_FOTO+numero,"0");
    }


}


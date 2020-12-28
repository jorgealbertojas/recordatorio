package com.example.jorgealberto.researchmobile.util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Date;

public class SharedPreferencesService {
    private final String SHARED_PREFERENCES = "shared";

    private static final String VISTORIA_FOTO = "vistoriafoto";
    private static final String CODIGO_CRIANCA = "codigocrianca";
    private static final String PERMISSAO_USO = "permissao_uso";



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


    public void setCodigoCrianca(String numero) {
        SharedPreferences settings = activity.getSharedPreferences(SHARED_PREFERENCES, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(CODIGO_CRIANCA, numero);
        editor.apply();
    }

    public String getCodigoCrianca() {
        SharedPreferences settings = activity.getSharedPreferences(SHARED_PREFERENCES, Activity.MODE_PRIVATE);
        return settings.getString(CODIGO_CRIANCA,"0");
    }

    public void setPermissao(Boolean permissao) {
        SharedPreferences settings = activity.getSharedPreferences(SHARED_PREFERENCES, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(PERMISSAO_USO, permissao);
        editor.apply();
    }

    public Boolean getPermissao() {
        SharedPreferences settings = activity.getSharedPreferences(SHARED_PREFERENCES, Activity.MODE_PRIVATE);
        return settings.getBoolean(PERMISSAO_USO,false);
    }


}


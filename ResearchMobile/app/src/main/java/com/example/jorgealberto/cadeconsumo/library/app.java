package com.example.jorgealberto.cadeconsumo.library;

/**
 * Created by sspbr on 22/07/2016.
 */
import android.app.Application;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import androidx.appcompat.app.ActionBar;

/**
 * Created by jorgesantos on 05/06/15.
 */
public class app extends Application implements TextToSpeech.OnInitListener {

    private static Context mContext;
    private static Context mContextCrountries;

    private static Context mContextLanguage;

    public static Context getmContextLanguage() {
        return mContextLanguage;
    }

    public static void setmContextLanguage(Context mContextLanguage) {
        app.mContextLanguage = mContextLanguage;
    }




    public static Context getmContextCrountries() {
        return mContextCrountries;
    }

    public static void setmContextCrountries(Context mContextCrountries) {
        app.mContextCrountries = mContextCrountries;
    }






    public static Context getmContext() {
        return mContext;
    }

    public static void setmContext(Context mContext) {
        app.mContext = mContext;
    }






    public static ActionBar getnActionBar() {
        return nActionBar;
    }

    public static void setnActionBar(ActionBar nActionBar) {
        app.nActionBar = nActionBar;
    }

    private static ActionBar nActionBar;



    public static Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public void onInit(int status) {

    }
}

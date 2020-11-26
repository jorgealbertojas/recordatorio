package com.example.jorgealberto.researchmobile;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.jorgealberto.researchmobile.SQL.sql_create;
import com.example.jorgealberto.researchmobile.SQL.sql_delete;
import com.example.jorgealberto.researchmobile.model.Aluno;
import com.example.jorgealberto.researchmobile.model.Escola;
import com.example.jorgealberto.researchmobile.model.Pergunta;
import com.example.jorgealberto.researchmobile.model.Turma;
import com.example.jorgealberto.researchmobile.model.opcao;
import com.example.jorgealberto.researchmobile.modelJson.perguntas;
import com.example.jorgealberto.researchmobile.service.DB;
import com.example.jorgealberto.researchmobile.service.DataBase;
import com.example.jorgealberto.researchmobile.util.InterfaceRetrofit;
import com.example.jorgealberto.researchmobile.util.ListWrapper;
import com.example.jorgealberto.researchmobile.util.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 6000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        ImageView ceg = (ImageView) findViewById(R.id.ceg);

        //Bitmap bMap = BitmapFactory.decodeFile(MyConstant.storageCliente+"cliente.png");
        //sspbr.setImageBitmap(bMap);

        YoYo.with(Techniques.BounceInDown)
                .duration(3000)
                .playOn(ceg);

/*        YoYo.with(Techniques.RotateOutUpLeft)
                .duration(2000)
                .delay(4000)
                .playOn(ceg);*/

/*        YoYo.with(Techniques.BounceInDown)
                .duration(2000);*/

/*        YoYo.with(Techniques.RotateOut)

                .duration(2000)
                .delay(4000);*/

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);

               // Intent i2 = new Intent(SplashActivity.this, login.class);
               // startActivity(i2);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    /**
     * Find Data the API Json with Retrofit
     */



}

package com.example.jorgealberto.researchmobile;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorgealberto.researchmobile.SQL.sql_create;
import com.example.jorgealberto.researchmobile.SQL.sql_delete;
import com.example.jorgealberto.researchmobile.SQL.sql_select;
import com.example.jorgealberto.researchmobile.commom.modulo;
import com.example.jorgealberto.researchmobile.library.app;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends Activity {

    private Toolbar mToolbar;
    private TextView actionBarTitle;
    private ImageView ibBegin;
    private ImageView imageViewStart;
    private ImageView imageViewEnd;

    private TextView textViewStart;
    private TextView textViewEnd;

    private FragmentTransaction ft;

    private SQLiteDatabase bd;
    //private Context context;
    private DataBase nDataBase;

    private void contaCompletoEIncompleto(){

        nDataBase = new DataBase(this);
        bd = nDataBase.getReadableDatabase();

        int AlunoAtual = 0;
        int contaAluno = 0;

        int valoTotalCompleto = 0;
        int valoTotalIncompleto = 0;


        Cursor cursoAluno = bd.rawQuery(sql_select.get_todos_alunos, null);
        cursoAluno.moveToFirst();

        Cursor Cursorget_contapergunta = bd.rawQuery(sql_select.get_contapergunta,null);
        Cursorget_contapergunta.moveToFirst();
        int varCursorget_contapergunta = Cursorget_contapergunta.getInt(0);

        Cursor cursoContapergunta = bd.rawQuery(sql_select.get_contapergunta, null);
        cursoAluno.moveToFirst();


        while (!cursoAluno.isAfterLast()) {

            AlunoAtual = cursoAluno.getInt(0);

            Cursor cursorReposta1 = bd.rawQuery(sql_select.GET_completo,new String[] {Integer.toString(varCursorget_contapergunta), Integer.toString(AlunoAtual)});
            cursorReposta1.moveToFirst();
            if (cursorReposta1.getCount() == 1){
                valoTotalCompleto = valoTotalCompleto + 1;
            }else{
                valoTotalIncompleto = valoTotalIncompleto + 1;
            }


            cursoAluno.moveToNext();
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        app nApp = new app();
        nApp.setContext(this);


        if(savedInstanceState == null){
         //   Intent WSActivity = new Intent(this, login.class);
        //    startActivity(WSActivity);

        }

        MainFragment nMainFragment = new MainFragment();
        ft = getFragmentManager().beginTransaction();
        ft.replace( R.id.container_body, nMainFragment);
        ft.commit();



    }

    public void onStart() {
        super.onStart();

        contaCompletoEIncompleto();

        if (!modulo.Liberado) {
            this.finish();
            try {
                this.finalize();
            } catch (Throwable e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    };

    @Override
    protected void onDestroy() {
        //if (modulo.nGPS){
        //	Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        //	intent.putExtra("enabled", false);
        //	sendBroadcast(intent);
        //	intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        //    startActivityForResult(intent, 1);
        //}

        super.onDestroy();
        //cursorPergunta.close();
    };



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //  if (id == R.id.action_settings) {
        //      return true;
        //  }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // MenuUser();

        getMenuInflater().inflate(R.menu.menu_main, menu);

        menu.hasVisibleItems();
        return true;
    }




    private void eventChangeColorEnd(View v, MotionEvent motionEvent){
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                imageViewEnd.setColorFilter(imageViewEnd.getContext().getResources().getColor(R.color.color_accent), PorterDuff.Mode.SRC_ATOP);
                textViewEnd.setTextColor(imageViewEnd.getContext().getResources().getColor(R.color.color_accent));
                v.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                imageViewEnd.setColorFilter(imageViewEnd.getContext().getResources().getColor(R.color.gold), PorterDuff.Mode.SRC_ATOP);
                textViewEnd.setTextColor(imageViewEnd.getContext().getResources().getColor(R.color.gold));
                v.invalidate();
                break;
            }
        }
    }


    private void eventChangeColorStart(View v, MotionEvent motionEvent){
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                imageViewStart.setColorFilter(imageViewStart.getContext().getResources().getColor(R.color.MediumOrchid), PorterDuff.Mode.SRC_ATOP);
                textViewStart.setTextColor(imageViewStart.getContext().getResources().getColor(R.color.MediumOrchid));
                v.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                imageViewStart.setColorFilter(imageViewStart.getContext().getResources().getColor(R.color.green_accent), PorterDuff.Mode.SRC_ATOP);
                textViewStart.setTextColor(imageViewStart.getContext().getResources().getColor(R.color.green_accent));
                v.invalidate();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.mensagem01))
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                        finish();
                    }
                }).create().show();
    }


}

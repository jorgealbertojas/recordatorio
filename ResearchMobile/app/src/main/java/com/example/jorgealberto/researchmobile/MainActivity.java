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

    private InterfaceRetrofit mInterfaceObject;



    /**
     * Find Data the API Json with Retrofit
     */
    private void createStackOverflowAPI() {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utility.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        mInterfaceObject = retrofit.create(InterfaceRetrofit.class);
    }



    private void contaCompletoEIncompleto(){

        nDataBase = new DataBase(this);
        bd = nDataBase.getReadableDatabase();

        int AlunoAtual = 0;
        int contaAluno = 0;

        int valoTotalCompleto = 0;
        int valoTotalIncompleto = 0;

        TextView textoCompleto = (TextView) findViewById(R.id.actionbarTitle);


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

    /**
     * Call Get InformationNew OBJECTS .
     */
    private Callback<ListWrapper<perguntas>> objectCallback = new Callback<ListWrapper<perguntas>>() {
        @Override
        public void onResponse(Call<ListWrapper<perguntas>> call, Response<ListWrapper<perguntas>> response) {
            try {
                if (response.isSuccessful()) {
                    ArrayList<perguntas> data = new ArrayList<>();
                    data.addAll(response.body().perguntas);

                    for (int i = 0; i < data.size() ; i++) {
                        Pergunta pergunta = new Pergunta();
                        pergunta.setDESCRICAO(data.get(i).getDescricao());
                        pergunta.setID_BLOCO(1);
                        pergunta.setID_PERGUNTA_JSON(data.get(i).getId());
                        pergunta.setID_PERGUNTA(i);
                        pergunta.setNUM_SUBFORMULARIO(0);
                        pergunta.setCOD_PERGUNTA(data.get(i).getIdentUnica());
                        savePERGUNTA(pergunta);

                        for (int y = 0 ; y < data.get(i).getItens().size(); y++){
                            opcao opcao = new opcao();
                            opcao.setID_OPCAO(data.get(i).getItens().get(y).getId());
                            opcao.setID_PERGUNTA(i);
                            opcao.setOPCAO(data.get(i).getItens().get(y).getDescricao());
                            if (data.get(i).getItens().get(y).getTipo() == 1) {
                                opcao.setVALOR(0);
                            }else if (data.get(i).getItens().get(y).getTipo() == 3) {
                                opcao.setVALOR(33);
                            }else if (data.get(i).getItens().get(y).getTipo() == 5) {
                                opcao.setVALOR(4);
                            }else if (data.get(i).getItens().get(y).getTipo() == 4) {
                                opcao.setVALOR(3);
                            }else if (data.get(i).getItens().get(y).getTipo() == 0) {
                                opcao.setVALOR(34);
                            }else if (data.get(i).getItens().get(y).getTipo() == 6) {
                                opcao.setVALOR(10);
                            }else if (data.get(i).getItens().get(y).getTipo() == 9) {
                                opcao.setVALOR(0);
                            }

                            opcao.setPERSONALIZACAO(data.get(i).getItens().get(y).getPersonalizacao());

                            if (data.get(i).getItens().get(y).getMaximo() > 0) {
                                opcao.setMAXIMO(data.get(i).getItens().get(y).getMaximo());
                            }else{
                                opcao.setMAXIMO(250);
                            }

                            if (data.get(i).getItens().get(y).getMinimo() > 0) {
                                opcao.setMINIMO(data.get(i).getItens().get(y).getMinimo());
                            }else{
                                opcao.setMINIMO(0);
                            }

                            opcao.setORDENA(y);
                            opcao.setSALTO(data.get(i).getItens().get(y).getPerguntaDestino());
                            saveOPCAO(opcao);
                        }
                    }

                    Aluno aluno = new Aluno();
                    aluno.setDT_NASC("1");
                    aluno.setID_ALUNO(1);
                    aluno.setID_ESCOLA(1);
                    aluno.setID_TURMA(1);
                    saveALUNO(aluno);

                    Turma turma = new Turma();
                    turma.setID_ESCOLA("1");
                    turma.setID_TURMA(1);
                    turma.setNOME_TURMA("1");
                    turma.setTURNO("1");
                    saveTurma(turma);

                    Escola escola = new Escola();
                    escola.setCOD_BAIRRO(1);
                    escola.setCOD_MUNIC(1);
                    escola.setCOD_UF(1);
                    escola.setID_ESCOLA(1);
                    escola.setENDERECO("1");
                    escola.setNOME_ESCOLA("1");
                    escola.setNUMERO("1");
                    escola.setREGIAO("1");
                    escola.setSUBTIPO("1");
                    escola.setTIPO("1");
                    saveEscola(escola);




                } else {
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                }
            } catch (NullPointerException e) {
                System.out.println("onActivityResult consume crashed");
                runOnUiThread(new Runnable() {
                    public void run() {

                        Context context = getApplicationContext();

                    }
                });
            }
        }

        private void saveEscola(Escola nEscola)
        {

            ContentValues obj = new ContentValues();
            obj.put("ID_ESCOLA",nEscola.getID_ESCOLA());
            obj.put("COD_UF",nEscola.getCOD_UF());
            obj.put("COD_MUNIC",nEscola.getCOD_MUNIC());
            obj.put("COD_BAIRRO",nEscola.getCOD_BAIRRO());
            obj.put("NOME_ESCOLA",nEscola.getNOME_ESCOLA());
            obj.put("ENDERECO",nEscola.getENDERECO());
            obj.put("NUMERO",nEscola.getNUMERO());
            obj.put("REGIAO",nEscola.getREGIAO());
            obj.put("TIPO",nEscola.getTIPO());
            obj.put("SUBTIPO",nEscola.getSUBTIPO());
            onInsert( getApplicationContext(), obj , sql_create.TABLE_ESCOLA);

        }

        @Override
        public void onFailure(Call<ListWrapper<perguntas>> call, Throwable t) {
            Context context = getApplicationContext();

        }

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nDataBase = new DataBase(this);
        bd = nDataBase.getReadableDatabase();
        bd.execSQL(sql_delete.DEL_ALUNO_TODOS);
        bd.execSQL(sql_delete.DEL_PERGUNTAS_TODOS);
        bd.execSQL(sql_delete.DEL_OPCAO_TODOS);
        bd.execSQL(sql_delete.DEL_GRUPOS_ALIMENTOS);
        bd.execSQL(sql_delete.DEL_MEDIDAS_CASEIRASS);
        bd.execSQL(sql_delete.DEL_MODO_PREPARACAO);
        bd.execSQL(sql_delete.DEL_ADICOES);
        bd.execSQL(sql_delete.DEL_BLOCO_TODOS);
        bd.execSQL(sql_delete.DEL_RESPOSTA_TODOS);
        bd.execSQL(sql_delete.DEL_CONTROLE_INICIO);
        bd.execSQL(sql_delete.DEL_CONTROLE_FIM);
        bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ALUNO);
        bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ALUNO,new String[] {"1"});
        bd.execSQL(sql_delete.DEL_TODOS_CONTROLE_INICIO,new String[] {"1"});
        bd.execSQL(sql_delete.DEL_TODOS_CONTROLE_FIM,new String[] {"1"});



        createStackOverflowAPI();
        mInterfaceObject.getObject().enqueue(objectCallback);

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


    /// novas código
    /// para o recordatorio

    private void savePERGUNTA(Pergunta nPergunta)
    {
        String descricao = " ";
        ContentValues obj = new ContentValues();
        obj.put("ID_PERGUNTA",nPergunta.getID_PERGUNTAS());
        if (nPergunta.getDESCRICAO() != null){
            descricao = nPergunta.getDESCRICAO();
        }
        obj.put("DESCRICAO", descricao);
        obj.put("ID_BLOCO",nPergunta.getID_BLOCO());
        obj.put("NUM_SUBFORMULARIO",nPergunta.getNUM_SUBFORMULARIO());
        obj.put("ID_PERGUNTA_JSON",nPergunta.getID_PERGUNTA_JSON());
        obj.put("COD_PERGUNTA",nPergunta.getCOD_PERGUNTA());
        this.onInsert( getApplicationContext(), obj , sql_create.TABLE_PERGUNTA);

    }

    private void saveTurma(Turma nTurma)
    {

        ContentValues obj = new ContentValues();
        obj.put("ID_TURMA",nTurma.getID_TURMA());
        obj.put("ID_ESCOLA",nTurma.getID_ESCOLA());
        obj.put("NOME_TURMA",nTurma.getNOME_TURMA());
        obj.put("TURNO",nTurma.getTURNO());
        this.onInsert( getApplicationContext(), obj , sql_create.TABLE_TURMA);

    }

    private void saveALUNO(Aluno nAluno)
    {



        ContentValues obj = new ContentValues();
        obj.put("ID_ALUNO",nAluno.getID_ALUNO());
        obj.put("ID_ESCOLA",nAluno.getID_ESCOLA());
        obj.put("ID_TURMA",nAluno.getID_TURMA());

        //String nomelimpo = nAluno.getNOME().substring(0,nAluno.getNOME().indexOf("="));
        String nomelimpo = nAluno.getNOME().substring(nAluno.getNOME().indexOf("=")+1,nAluno.getNOME().length());

        obj.put("NOME",  nomelimpo);
        obj.put("DT_NASC",nAluno.getDT_NASC());
        obj.put("SEXO",nAluno.getSEXO());

        this.onInsert( getApplicationContext(), obj , sql_create.TABLE_ALUNO);

    }

    private void saveOPCAO(opcao nOpcao)
    {
        String  salto = "0";
        ContentValues obj = new ContentValues();
        obj.put("ID_OPCAO",nOpcao.getID_OPCAO());
        obj.put("MAXIMO", nOpcao.getMAXIMO());
        obj.put("VALOR",nOpcao.getVALOR());
        obj.put("OPCAO",nOpcao.getOPCAO());
        obj.put("MINIMO",nOpcao.getMINIMO());
        obj.put("ID_PERGUNTA",nOpcao.getID_PERGUNTA());
        if (nOpcao.getSALTO() != null){
            salto = nOpcao.getSALTO();
        }
        obj.put("PERSONALIZACAO",nOpcao.getPERSONALIZACAO());
        obj.put("SALTO",salto);
        obj.put("ORDENA",nOpcao.getORDENA());
        this.onInsert( getApplicationContext(), obj , sql_create.TABLE_OPCAO);

    }

    private void onInsert(Context context, ContentValues obj, String nTabela) {
        try{
            DB.getInstance( context ).insert( nTabela, obj );
        }
        catch (Throwable ex){

        }

    }


}

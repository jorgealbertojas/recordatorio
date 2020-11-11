package com.example.jorgealberto.researchmobile;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.daasuu.ei.Ease;
import com.daasuu.ei.EasingInterpolator;


import com.example.jorgealberto.researchmobile.SQL.sql_create;
import com.example.jorgealberto.researchmobile.SQL.sql_delete;
import com.example.jorgealberto.researchmobile.login_user.LoginFragment;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.IOException;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity


{

   // private static FirebaseAuth mAuth;

    Fragment frag_login;
    ProgressBar pbar;
    View button_login, button_icon, ic_menu1, ic_menu2;

    TextView button_label;

    public static EditText email;
    public static EditText password;
    public static EditText confirmPassword;
    static ValueAnimator va;
    private DisplayMetrics dm;

    private SQLiteDatabase bd;
    //private Context context;
    private DataBase nDataBase;

    private InterfaceRetrofit mInterfaceObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pbar = (ProgressBar) findViewById(R.id.mainProgressBar1);
        button_icon = findViewById(R.id.button_icon);
        button_label = findViewById(R.id.button_label);

        // Login with FireBase
       // mAuth = FirebaseAuth.getInstance();

        nDataBase = new DataBase(this);
        bd = nDataBase.getReadableDatabase();
        bd.execSQL(sql_delete.DEL_ALUNO_TODOS);
        bd.execSQL(sql_delete.DEL_PERGUNTAS_TODOS);
        bd.execSQL(sql_delete.DEL_OPCAO_TODOS);
        //bd.execSQL(sql_delete.DEL_GRUPOS_ALIMENTOS);
        //bd.execSQL(sql_delete.DEL_MEDIDAS_CASEIRASS);
        //bd.execSQL(sql_delete.DEL_MODO_PREPARACAO);
        //bd.execSQL(sql_delete.DEL_ADICOES);
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

        dm = getResources().getDisplayMetrics();
        button_login = findViewById(R.id.button_login);
        button_login.setTag(0);
        pbar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        frag_login = new LoginFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, frag_login).commit();
        va = new ValueAnimator();
        va.setDuration(1500);
        va.setInterpolator(new DecelerateInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator p1) {
                RelativeLayout.LayoutParams button_login_lp = (RelativeLayout.LayoutParams) button_login.getLayoutParams();
                button_login_lp.width = Math.round((Float) p1.getAnimatedValue());
                button_login.setLayoutParams(button_login_lp);
            }


        });

        button_login.animate().translationX(dm.widthPixels + button_login.getMeasuredWidth()).setDuration(0).setStartDelay(0).start();
        button_login.animate().translationX(0).setStartDelay(6500).setDuration(1500).setInterpolator(new OvershootInterpolator()).start();
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                if ((int) button_login.getTag() != 4) {

                    if (notNullEmailAndPassword()) {
                        ExistAccount();
                    }else{
                        Toast.makeText(LoginActivity.this, "login_login",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    // CREATE NEW account WITH E-MAIL


                    if (validateEmailAndPassword()) {


          /*              mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG_LOGIN_FIREBASE, "createUserWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();

                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG_LOGIN_FIREBASE, "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();

                                        }


                                    }
                                });*/


                        button_login.setTag(1);
                        va.setFloatValues(button_login.getMeasuredWidth(), button_login.getMeasuredHeight());
                        va.start();
                        pbar.animate().setStartDelay(300).setDuration(1000).alpha(1).start();
                        button_label.animate().setStartDelay(100).setDuration(500).alpha(0).start();
                        button_login.animate().setInterpolator(new FastOutSlowInInterpolator()).setStartDelay(4000).setDuration(1000).scaleX(30).scaleY(30).setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator p1) {
                                pbar.animate().setStartDelay(0).setDuration(0).alpha(0).start();
                            }

                            @Override
                            public void onAnimationEnd(Animator p1) {
                                try {

                                } catch (Exception e) {
                                }
                                button_login.animate().setStartDelay(0).alpha(1).setDuration(1000).scaleX(1).scaleY(1).x(dm.widthPixels - button_login.getMeasuredWidth() - 100).y(dm.heightPixels - button_login.getMeasuredHeight() - 100).setListener(new Animator.AnimatorListener() {

                                    @Override
                                    public void onAnimationStart(Animator p1) {
                                        // TODO: Implement this method
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator p1) {

                                        Intent i = new Intent(LoginActivity.this, SplashActivity.class);
                                        startActivity(i);
                                        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                                        finish();
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator p1) {
                                        // TODO: Implement this method
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator p1) {
                                        // TODO: Implement this method
                                    }
                                }).start();
                            }

                            @Override
                            public void onAnimationCancel(Animator p1) {
                                // TODO: Implement this method
                            }

                            @Override
                            public void onAnimationRepeat(Animator p1) {
                                // TODO: Implement this method
                            }
                        }).start();


                    } else {
                        Toast.makeText(LoginActivity.this, "enter_information",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
       // FirebaseUser currentUser = mAuth.getCurrentUser();

        email = frag_login.getView().findViewById(R.id.mainEditText1);
        password = frag_login.getView().findViewById(R.id.mainEditText2);
        confirmPassword = frag_login.getView().findViewById(R.id.mainEditText3);




        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);


        email.setText(sharedPref.getString("preference_key_email",null));
        password.setText(sharedPref.getString("preference_key_password",null));

    }

    public boolean validateEmailAndPassword() {

        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String ConfirmPassword = confirmPassword.getText().toString();
        return true;
/*        if (Email != null && Password != null && ConfirmPassword != null && (!Email.equals("")) && (!Password.equals(""))  && (!ConfirmPassword.equals("")))  {

            if (Password.equals(ConfirmPassword)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }*/

    }

    public boolean notNullEmailAndPassword() {


      //  if (email.getText().toString() != null && password.getText().toString() != null) {
      //      if (!email.getText().toString().equals("") && !password.getText().toString().equals("")) {
                return true;
     //       }
    //    }

     ////   Toast.makeText(LoginActivity.this, "login_null",
    //            Toast.LENGTH_SHORT).show();
    //    return false;


    }

    public void ExistAccount(){
        login();
        /*    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG_LOGIN_FIREBASE, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                login();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG_LOGIN_FIREBASE, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_not_exist),
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    });*/
    }


    public void login(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("preference_key_email", email.getText().toString());
        editor.putString("preference_key_password", password.getText().toString());
        editor.commit();

        if ((int) button_login.getTag() == 1) {
            return;
        } else if ((int) button_login.getTag() == 2) {
            button_login.animate().x(dm.widthPixels / 2).y(dm.heightPixels / 2).setInterpolator(new EasingInterpolator(Ease.CUBIC_IN)).setListener(null).setDuration(1000).setStartDelay(0).start();
            button_login.animate().setStartDelay(600).setDuration(1000).scaleX(40).scaleY(40).setInterpolator(new EasingInterpolator(Ease.CUBIC_IN_OUT)).start();
            button_icon.animate().alpha(0).rotation(90).setStartDelay(0).setDuration(800).start();
            return;
        }
        button_login.setTag(1);
        va.setFloatValues(button_login.getMeasuredWidth(), button_login.getMeasuredHeight());
        va.start();
        pbar.animate().setStartDelay(300).setDuration(1000).alpha(1).start();
        button_label.animate().setStartDelay(100).setDuration(500).alpha(0).start();
        button_login.animate().setInterpolator(new FastOutSlowInInterpolator()).setStartDelay(4000).setDuration(1000).scaleX(30).scaleY(30).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator p1) {
                pbar.animate().setStartDelay(0).setDuration(0).alpha(0).start();
            }

            @Override
            public void onAnimationEnd(Animator p1) {
                try {

                } catch (Exception e) {
                }
                button_login.animate().setStartDelay(0).alpha(1).setDuration(1000).scaleX(1).scaleY(1).x(dm.widthPixels - button_login.getMeasuredWidth() - 100).y(dm.heightPixels - button_login.getMeasuredHeight() - 100).setListener(new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator p1) {
                        // TODO: Implement this method
                    }

                    @Override
                    public void onAnimationEnd(Animator p1) {
                        button_icon.animate().setDuration(0).setStartDelay(0).rotation(85).alpha(1).start();
                        button_icon.animate().setDuration(2000).setInterpolator(new BounceInterpolator()).setStartDelay(0).rotation(0).start();
                        button_login.setTag(2);
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                        finish();
                    }

                    @Override
                    public void onAnimationCancel(Animator p1) {
                        // TODO: Implement this method
                    }

                    @Override
                    public void onAnimationRepeat(Animator p1) {
                        // TODO: Implement this method
                    }
                }).start();
            }

            @Override
            public void onAnimationCancel(Animator p1) {
                // TODO: Implement this method
            }

            @Override
            public void onAnimationRepeat(Animator p1) {
                // TODO: Implement this method
            }
        }).start();

    }

    private void createStackOverflowAPI() {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utility.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfaceObject = retrofit.create(InterfaceRetrofit.class);
    }

    /**
     * Call Get InformationNew OBJECTS .
     */
    private Callback<ListWrapper<perguntas>> objectCallback = new Callback<ListWrapper<perguntas>>() {
        @Override
        public void onResponse(Call<ListWrapper<perguntas>> call, Response<ListWrapper<perguntas>> response) {
            try {

                String tempBloco = "0";
                Integer tempBlocoNumber = -1;
                if (response.isSuccessful()) {
                    ArrayList<perguntas> data = new ArrayList<>();
                    data.addAll(response.body().perguntas);

                    for (int i = 0; i < data.size() ; i++) {
                        Pergunta pergunta = new Pergunta();
                        pergunta.setDESCRICAO(data.get(i).getDescricao());
                        if (!data.get(i).getBloco().equals(tempBloco)){
                            tempBlocoNumber ++;
                            tempBloco = data.get(i).getBloco();
                        }

                        if (tempBlocoNumber >= 5){
                            pergunta.setID_BLOCO(5);
                        }else {
                            pergunta.setID_BLOCO(tempBlocoNumber);
                        }
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
                                if (data.get(i).getItens().get(y).getPersonalizacao() != null) {
                                    if (data.get(i).getItens().get(y).getPersonalizacao().equals("Hora e minuto")) {
                                        opcao.setVALOR(8);
                                    } else {
                                        opcao.setVALOR(10);
                                    }
                                }else{
                                    opcao.setVALOR(10);
                                }
                            }else if (data.get(i).getItens().get(y).getTipo() == 9) {
                                opcao.setVALOR(0);
                            }else if (data.get(i).getItens().get(y).getTipo() == 10) {
                                opcao.setVALOR(99);
                            }else if (data.get(i).getItens().get(y).getTipo() == 11) {
                                opcao.setVALOR(98);
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


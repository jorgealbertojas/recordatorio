package com.softjads.cadeconsumo;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.softjads.cadeconsumo.modelJson.Crianca;
import com.softjads.cadeconsumo.util.InterfaceRetrofit;
import com.softjads.cadeconsumo.util.Utility;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CriarCrianca extends AppCompatActivity {

    private Spinner spinner;
    private Button cancelar;
    private Button salvar;
    private EditText edtEscola;
    private EditText edtNome;

    private String perfil = "0";

    private LinearLayout ll;

    private InterfaceRetrofit mInterfaceObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_crianca);

        createStackOverflowAPI();
        mInterfaceObject.getCriancaFull().enqueue(objectCriancaCallback);

        edtEscola = (EditText) findViewById(R.id.edtEscola);
        edtNome = (EditText) findViewById(R.id.edtNome);
        ll = (LinearLayout) this.findViewById(R.id.edits_ll);

        cancelar = (Button) findViewById(R.id.cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        salvar = (Button)  findViewById(R.id.salvar);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEmailAndPassword()) {

                    Crianca crianca = new Crianca();
                    crianca.setNome(edtNome.getText().toString());
                    crianca.setNomeEscola(edtEscola.getText().toString());

                    createStackOverflowAPI();
                    mInterfaceObject.postCrianca(crianca).enqueue(cadatrarCriancaCallback);


                }
            }
        });

    }

    public boolean validateEmailAndPassword() {

        String nome = edtNome.getText().toString();
        String escola = edtEscola.getText().toString();

        if (perfil != null && nome != null  && (!escola.equals(""))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Call post cadatrar CriancaCallback .
     */
    private Callback<Crianca> cadatrarCriancaCallback = new Callback<Crianca>() {
        @Override
        public void onResponse(Call<Crianca> call, Response<Crianca> response) {
            try {
                if (response.isSuccessful()) {

                    Toast.makeText(CriarCrianca.this, "Cadastrado com sucesso!",
                            Toast.LENGTH_SHORT).show();

                    apagarValoresLinearLayout();
                    createStackOverflowAPI();
                    mInterfaceObject.getCriancaFull().enqueue(objectCriancaCallback);


                } else {
                    Toast.makeText(CriarCrianca.this, "Cadastro invalido!",
                            Toast.LENGTH_SHORT).show();
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

        @Override
        public void onFailure(Call<Crianca> call, Throwable t) {
            Toast.makeText(CriarCrianca.this, "Entre com login e senha!",
                    Toast.LENGTH_SHORT).show();
        }
    };

    private void createStackOverflowAPI() {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utility.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfaceObject = retrofit.create(InterfaceRetrofit.class);
    }

    // personalizado
    private void criarGridCriancaInseridas(List<Crianca> listaCrianca) {

        if (listaCrianca.size() > 0) {
            for (int i = 0; i < listaCrianca.size(); i++) {
                TextView textView = new TextView(getApplicationContext());
                textView.setText(listaCrianca.get(i).getCodigo() + " " +  listaCrianca.get(i).getNome() + " - " + listaCrianca.get(i).getNomeEscola());
                textView.setTag(i);

                textView.setPadding(20, 20, 20, 20);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                Resources res = getResources();

                ImageView nButton2 = new ImageView(this);
                nButton2.setImageResource(R.mipmap.ic_delete);

                final int newColor2 = res.getColor(R.color.red);
                nButton2.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
                nButton2.setTag(listaCrianca.get(i).getId());

                nButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        createStackOverflowAPI();
                        mInterfaceObject.deleteCrianca(view.getTag().toString()).enqueue(deletarCriancaCallback);

                        apagarValoresLinearLayout();
                    }
                });

                LinearLayout.LayoutParams params;
                params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 0, 10, 0);
                params.weight = 8f;

                LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams((int) 200d, (int) 170d);
                paramsButton.weight = 1f;

                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setLayoutParams(params);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setGravity(Gravity.CENTER);
                paramsButton.gravity = Gravity.CENTER;

                linearLayout.addView(textView, params);
                linearLayout.addView(nButton2, paramsButton);

                //

                LinearLayout.LayoutParams paramsTextHelp = new LinearLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                paramsTextHelp.setMargins(0, 0, 0, 0);
                TextInputLayout editAnimation = new TextInputLayout(this);
                editAnimation.setTag(listaCrianca.get(i).getId());

                editAnimation.setHelperText("");
                editAnimation.setHelperTextTextAppearance(R.style.TextHelp10);
                editAnimation.setBackground(getResources().getDrawable(R.drawable.rounded_corner_questionario));

                editAnimation.addView(linearLayout, paramsTextHelp);
                ll.addView(editAnimation, params);

            }
        }
    }

    public void apagarValoresLinearLayout() {
        try {

            for (int i = 0; i < ll.getChildCount(); i++) {
                View child = ll.getChildAt(i);
                if (child instanceof TextInputLayout) {
                    int ii = ll.getChildCount();
                    while (-2 < ii - 1) {
                        View child1 = ((LinearLayout) child).getChildAt(ii);
                        if (child1 instanceof LinearLayout) {
                            LinearLayout et = (LinearLayout) child1;
                            ViewGroup parent = (ViewGroup) et.getParent();
                            parent.removeView(et);
                        } else if (child1 instanceof TextView) {
                            TextView et = (TextView) child1;
                            ViewGroup parent = (ViewGroup) et.getParent();
                            parent.removeView(et);
                        }
                        ii--;
                    }
                }
            }

        } catch (Throwable ex) {

        }
    }


    private Callback<List<Crianca>> objectCriancaCallback = new Callback<List<Crianca>>() {
        @Override
        public void onResponse(Call<List<Crianca>> call, Response<List<Crianca>> response) {
            try {
                if (response.isSuccessful()) {
                    criarGridCriancaInseridas(response.body());
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

        @Override
        public void onFailure(Call<List<Crianca>> call, Throwable t) {
            System.out.println("onActivityResult consume crashed");
        }
    };


    /**
     * Call post cadatrar CriancaCallback .
     */
    private Callback<Boolean>deletarCriancaCallback = new Callback<Boolean>() {
        @Override
        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
            try {
                if (response.isSuccessful()) {

                    Toast.makeText(CriarCrianca.this, "Deletado com sucesso!",
                            Toast.LENGTH_SHORT).show();

                    apagarValoresLinearLayout();
                    createStackOverflowAPI();
                    mInterfaceObject.getCriancaFull().enqueue(objectCriancaCallback);


                } else {
                    Toast.makeText(CriarCrianca.this, "Deleção invalida!",
                            Toast.LENGTH_SHORT).show();
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

        @Override
        public void onFailure(Call<Boolean> call, Throwable t) {
            Toast.makeText(CriarCrianca.this, "Entre com login e senha!",
                    Toast.LENGTH_SHORT).show();
        }
    };




}
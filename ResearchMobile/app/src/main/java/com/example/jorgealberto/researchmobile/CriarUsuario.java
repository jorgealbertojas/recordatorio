package com.example.jorgealberto.researchmobile;

import android.animation.Animator;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jorgealberto.researchmobile.SQL.sql_delete;
import com.example.jorgealberto.researchmobile.SQL.sql_select;
import com.example.jorgealberto.researchmobile.modelJson.Usuario;
import com.example.jorgealberto.researchmobile.util.InterfaceRetrofit;
import com.example.jorgealberto.researchmobile.util.Utility;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CriarUsuario extends AppCompatActivity {

    private Spinner spinner;
    private Button cancelar;
    private Button salvar;
    private EditText edtSenha;
    private EditText edtLogin;
    private EditText edtNome;

    private String perfil = "0";

    private LinearLayout ll;

    private InterfaceRetrofit mInterfaceObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_usuario);

        edtSenha = (EditText) findViewById(R.id.edtSenha);
        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtNome = (EditText) findViewById(R.id.edtNome);
        ll = (LinearLayout) this.findViewById(R.id.edits_ll);

        spinner = (Spinner) findViewById(R.id.spinnerperfil);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
               perfil = ((TextView) selectedItemView).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });
        String array_spinner[];
        array_spinner = new String[3];
        array_spinner[0] = "Usuario Escolar";
        array_spinner[1] = "Usuario Domiciliar";
        array_spinner[2] = "Coordenador";
        ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.textview_spinner_item, array_spinner);
        spinner.setAdapter(adapter);

        cancelar.findViewById(R.id.cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        salvar.findViewById(R.id.salvar);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEmailAndPassword()) {

                    Usuario usuario = new Usuario();
                    usuario.setNome(edtNome.getText().toString());
                    usuario.setSenha(edtSenha.getText().toString());
                    usuario.setLogin(edtLogin.getText().toString());
                    usuario.setPerfil(perfil);

                    createStackOverflowAPI();
                    mInterfaceObject.getUsuario(usuario).enqueue(cadatrarUsuarioCallback);


                } else {
                    Toast.makeText(CriarUsuario.this, "Entre com login e senha!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean validateEmailAndPassword() {

        String nome = edtNome.getText().toString();
        String senha = edtSenha.getText().toString();
        String login = edtLogin.getText().toString();

        if (perfil != null && nome != null && (!senha.equals("")) && (!login.equals(""))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Call Get InformationNew OBJECTS .
     */
    private Callback<Usuario> cadatrarUsuarioCallback = new Callback<Usuario>() {
        @Override
        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
            try {
                if (response.isSuccessful()) {



                    Toast.makeText(CriarUsuario.this, "Cadastrado com sucesso!",
                            Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CriarUsuario.this, "Login ou senha invalida!",
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
        public void onFailure(Call<Usuario> call, Throwable t) {
            Toast.makeText(CriarUsuario.this, "Entre com login e senha!",
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
    private void criarAlimentosInseridos(List<String> usuarios) {

        if (usuarios.size() > 0) {
            for (int i = 0; i < usuarios.size(); i++) {
                TextView textView = new TextView(getApplicationContext());
                textView.setText(usuarios.get(i));
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
                nButton2.setTag(usuarios.get(i));

                nButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                editAnimation.setTag(usuarios.get(i));

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


}
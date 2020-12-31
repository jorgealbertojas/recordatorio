package com.softjads.cadeconsumo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.softjads.cadeconsumo.util.SharedPreferencesService;

public class ValidarPermissao extends AppCompatActivity {

    private Button aceitar;
    private CheckBox checkTermo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validar_permissao);

        aceitar = (Button) findViewById(R.id.aceitar);
        checkTermo = (CheckBox) findViewById(R.id.checkTermo);

        checkTermo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (((CheckBox) arg0).isChecked()){
                    aceitar.setEnabled(true);
                }else{
                    aceitar.setEnabled(false);
                }


            }
        });

        aceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SharedPreferencesService shared = new SharedPreferencesService(ValidarPermissao.this);
                shared.setPermissao(true);
                finish();
            }
        });



    }
}
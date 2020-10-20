package com.example.jorgealberto.researchmobile;

import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.jorgealberto.researchmobile.library.MyConstant;
import com.example.jorgealberto.researchmobile.service.DB;
import com.example.jorgealberto.researchmobile.service.DataBase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class LoginActivity extends AppCompatActivity {

    private SQLiteDatabase bd;
    //private Context context;
    private DataBase nDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nDataBase = new DataBase(this);
        bd = nDataBase.getReadableDatabase();
        nDataBase.onCreate(bd);

        DB.getInstance(this);


        LinearLayout frameLogin = (LinearLayout) findViewById(R.id.frameLogin);

        YoYo.with(Techniques.StandUp)
                .duration(2000)
                .playOn(frameLogin);

        Properties properties = new Properties();
        FileInputStream fis;
        try {
            fis = new  FileInputStream(MyConstant.nomeArquivoINI);
            properties.load(fis);
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        String Diretorio = properties.getProperty("conf.Diretorio");

        carregarsuario_login();

        Button logar = (Button) findViewById(R.id.logar);
        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar_USuario_login();
                finish();
            }
        });

        EditText EditTextUser = (EditText) findViewById(R.id.EditTextUser);

        EditText Editpassword = (EditText) findViewById(R.id.Editpassword);



        YoYo.with(Techniques.StandUp)
                .duration(2000)
                .playOn(EditTextUser);
        YoYo.with(Techniques.StandUp)
                .duration(2000)
                .playOn(Editpassword);
        YoYo.with(Techniques.StandUp)
                .duration(2000)
                .playOn(logar);

        Editpassword.setHint("Password");
    }


    public void carregarsuario_login()
    {


        EditText nEditText = (EditText) findViewById(R.id.EditTextUser);
        Properties properties = new Properties();
        try
        {
            FileInputStream fis;
            fis = new  FileInputStream(MyConstant.nomeArquivoINI);
            properties.load(fis);
            String nusuariologin = properties.getProperty("conf.usuario_login");

            nEditText.setText(nusuariologin);



        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }


    public void salvar_USuario_login()
    {
        EditText nEditText = (EditText) findViewById(R.id.EditTextUser);

        if (nEditText.getText().toString().equals("")) {
            String outFileName = MyConstant.usuario_login;
            nEditText.setText(outFileName);
        }


        Properties properties = new Properties();
        FileInputStream fis;
        try
        {
            fis = new  FileInputStream(MyConstant.nomeArquivoINI);
            properties.load(fis);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        properties.setProperty("conf.usuario_login", nEditText.getText().toString());
        try
        {

            FileOutputStream fos = new FileOutputStream(MyConstant.nomeArquivoINI);
            properties.store(fos, "CONFIGURACAO usuario login:");
            fos.close();
            MyConstant.usuario_login = nEditText.getText().toString();
            //Toast.makeText(this, "Dados Salvos com sucesso!!", Toast.LENGTH_SHORT).show();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}

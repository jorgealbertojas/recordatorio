package com.example.jorgealberto.researchmobile.comunication;

import android.widget.EditText;

import com.example.jorgealberto.researchmobile.library.MyConstant;
import com.example.jorgealberto.researchmobile.R;
import com.example.jorgealberto.researchmobile.commom.modulo;
import com.example.jorgealberto.researchmobile.model.Ocorrencia;

import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by sspbr on 12/05/2016.
 */
public class HandleJson {


    public String urlString = "";


    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public void carregarsuario_login()
    {






    }


    public Thread fetchJsonModel_olx(final String nurlString) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                urlString = nurlString;
                try {
                    URL url;
                    InputStream stream;

                    url = new URL(MyConstant.PATH_API);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout( 10000 /*milliseconds*/ );
                    conn.setConnectTimeout( 15000 /* milliseconds */ );
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                   // conn.setFixedLengthStreamingMode(message.getBytes().length);



                    String usuariologin = "";
                    Properties properties = new Properties();
                    try
                    {
                        FileInputStream fis;
                        fis = new  FileInputStream(MyConstant.nomeArquivoINI);
                        properties.load(fis);
                        usuariologin = properties.getProperty("conf.usuario_login");
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                   // String input = "{ \"Operation\": \"ListarOcorrenciasFiltro\", \"NOME_EQUIPE\":\"" + usuariologin + "\"}";

                    String input = "{ \"Operation\": \"ListarOcorrenciasFiltro\", \"NOME_EQUIPE\":\"" + usuariologin + "\"}";

                    OutputStream os = conn.getOutputStream();
                    os.write(input.getBytes());
                    os.flush();

                    conn.connect();


                    stream = conn.getInputStream();


                    modulo.oObject = getDataGson(convertStreamToString(stream));



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        return thread;
    }


    public ArrayList<Object> getDataGson(String oStringObject){


        ArrayList<Object> ListaOcorrencia;

        Ocorrencia ocorrencia = new Ocorrencia();
        ListaOcorrencia = new ArrayList<Object>();

        cConsumptionJson oConsumptionJson = new cConsumptionJson();
        try {
            return oConsumptionJson.cosumption(((Object) ocorrencia), ((ArrayList<Object>) ListaOcorrencia), oStringObject,ocorrencia.nameMain);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


}


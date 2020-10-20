package com.example.jorgealberto.researchmobile;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jorgealberto.researchmobile.Adapter.AdapterOcorrencia;
import com.example.jorgealberto.researchmobile.commom.modulo;
import com.example.jorgealberto.researchmobile.comunication.cConsumptionJson;
import com.example.jorgealberto.researchmobile.library.AppStatus;
import com.example.jorgealberto.researchmobile.library.MyConstant;
import com.example.jorgealberto.researchmobile.model.Ocorrencia;

import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by sspbr on 14/07/2016.
 */
public class OcorrenciaActivityNEW extends Fragment {

    private ArrayList<Ocorrencia> OcorrenciaList;

    private ArrayList<Object> ListaOcorencia;

    private RecyclerView rv = null;

    public AdapterOcorrencia adapter = null;

    public static LinearLayoutManager llm;

    public String url = MyConstant.PATH_API;

    public View fragmentView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        //this.container = inflater.inflate(R.layout.activity_login,container);

        fragmentView = inflater.inflate(R.layout.activity_ocorrencia, container, false);

        rv = (RecyclerView) fragmentView.findViewById(R.id.recyclerViewOcorrencia);
        rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(fragmentView.getContext());
        rv.setLayoutManager(llm);


  /*      ImageButton addimageButtonOk = (ImageButton) fragmentView.findViewById(R.id.imageButtonOk);
        addimageButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // Pass the fragmentView through to the handler
                // so that findViewById can be used to get a handle on
                // the fragments own views.
                addimageButtonOk(fragmentView);
            }
        });*/

        AppStatus nAppStatus2 = new AppStatus();
        if (nAppStatus2.isOnline()) {
            new AsyncHttpTask().execute(url);
        }

        return fragmentView;
    }



    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {





        @Override
        protected void onPreExecute() {
            //setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
            URL url = null;
            InputStream stream = null;
            while (true) {

            try {
                url = new URL(MyConstant.PATH_API);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /*milliseconds*/);
                    conn.setConnectTimeout(15000 /* milliseconds */);

                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);


                    // conn.setFixedLengthStreamingMode(message.getBytes().length);


                    String usuariologin = "";
                    Properties properties = new Properties();

                    FileInputStream fis;
                    fis = new FileInputStream(MyConstant.nomeArquivoINI);
                    properties.load(fis);
                    usuariologin = properties.getProperty("conf.usuario_login");


                    // String input = "{ \"Operation\": \"ListarOcorrenciasFiltro\", \"NOME_EQUIPE\":\"" + usuariologin + "\"}";

                    String input = "{ \"Operation\": \"ListarOcorrenciasFiltro\", \"NOME_EQUIPE\":\"" + usuariologin + "\"}";

                    OutputStream os = null;

                    os = conn.getOutputStream();


                    os.write(input.getBytes());

                    os.flush();

                    conn.connect();


                    stream = conn.getInputStream();


                }catch(ProtocolException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }

                if (stream != null) {
                    modulo.oObject = getDataGson(convertStreamToString(stream));
                    modulo.OcorenciaLista = parseResult(stream.toString());
                    return 1;
                }else{
                    return 0;
                }

            }

            //return 1; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {

            //setProgressBarIndeterminateVisibility(false);

            /* Download complete. Lets update UI */
            if (result == 1) {
                adapter = new AdapterOcorrencia(modulo.OcorenciaLista,OcorrenciaActivityNEW.this.getActivity(),OcorrenciaActivityNEW.this.getActivity());
                rv.setAdapter(adapter);
            } else {
                Log.e("TAG", "Failed to fetch data!");
            }
            AppStatus nAppStatus2 = new AppStatus();
            if (nAppStatus2.isOnline()) {
                new AsyncHttpTask().execute(url);
            }
        }
    }

    private List<Ocorrencia> parseResult(String result) {

           // JSONObject response = new JSONObject(result);
            //JSONArray posts = response.optJSONArray("posts");


            ListaOcorencia = modulo.oObject;
            List<Ocorrencia> nnmodel_olx = new ArrayList<>();
            modulo.OcorenciaLista = new ArrayList<>();
            for (int i = 0; i < ListaOcorencia.size(); i++) {
                if (nnmodel_olx != null) {
                    nnmodel_olx.add((Ocorrencia) ListaOcorencia.get(i));
                }
            }
            for (int i = 0; i < nnmodel_olx.size(); i++) {
                if (nnmodel_olx != null) {
                    modulo.OcorenciaLista.add(nnmodel_olx.get(i));
                }
            }

            /*Initialize array if null*/
            if (null == OcorrenciaList) {
                OcorrenciaList = new ArrayList<Ocorrencia>();
            }

            //for (int i = 0; i < posts.length(); i++) {
            //    JSONObject post = posts.optJSONObject(i);

            //    Ocorrencia item = new Ocorrencia();
           //     item.setConceito(post.optString("COD_CONC_INI"));
           //     item.setEntrada(post.optString("TIEMPO_ASIG"));
           //     item.setId(post.optString("NUM_REFERENCIA"));
           //     OcorrenciaList.add(item);
            //}
            return modulo.OcorenciaLista;


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

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}

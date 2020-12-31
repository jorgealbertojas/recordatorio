package com.softjads.cadeconsumo.util;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sspbr on 12/05/2016.
 */
public class cConsumptionJson {
    JSONArray ArrayNameMain = null;

    public ArrayList<Object> cosumption(Object output, ArrayList<Object> listaOpcao, String stringJson, String nameMain) throws JSONException {
        if (MyConstant.PATH_API != null) {

            try {
                JSONObject jsonObj = null;
                stringJson = "{"+nameMain+":"+ stringJson+"}";
                jsonObj = new JSONObject(stringJson.toString());
                Gson gson = new Gson();

                // Getting JSON Array node
                ArrayNameMain = jsonObj.getJSONArray(nameMain);
                for (int i = 0; i < ArrayNameMain.length(); i++) {
                    ClasseADO nClasseADO = new ClasseADO();
                    output = nClasseADO.IntanciarClasse(output.getClass().getSimpleName());
                    String countryInfo = ArrayNameMain.getJSONObject(i).toString();
                    output = gson.fromJson(countryInfo, output.getClass());
                    listaOpcao.add(output);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return listaOpcao;

    }


}


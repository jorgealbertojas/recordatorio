package com.example.jorgealberto.researchmobile.util;


import com.example.jorgealberto.researchmobile.model.AlimentosCompletos;
import com.example.jorgealberto.researchmobile.modelJson.ListPerguntas;
import com.example.jorgealberto.researchmobile.modelJson.Usuario;
import com.example.jorgealberto.researchmobile.modelJson.alimentos;
import com.example.jorgealberto.researchmobile.modelJson.perguntas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InterfaceRetrofit {

        /** Get Data Assets Json with Retrofit */
        @GET(Utility.COMPLEMENT_URL)
        Call<ListWrapper<perguntas>> getObject();

        /** Get Data Assets Json with Retrofit */
        @GET(Utility.COMPLEMENT_URL_ALIMENTO + "{alimento_parte}")
        Call<AlimentosCompletos> getAlimentos(@Path("alimento_parte") String alimento_parte);

        /** Get Data Assets Json with Retrofit */
        @POST(Utility.COMPLEMENT_URL_USUARIO)
        Call<Usuario> getUsuario(@Body Usuario usuario);

    }

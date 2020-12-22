package com.example.jorgealberto.researchmobile.util;


import com.example.jorgealberto.researchmobile.model.AlimentosCompletos;
import com.example.jorgealberto.researchmobile.modelJson.Crianca;
import com.example.jorgealberto.researchmobile.modelJson.ListPerguntas;
import com.example.jorgealberto.researchmobile.modelJson.Usuario;
import com.example.jorgealberto.researchmobile.modelJson.alimentos;
import com.example.jorgealberto.researchmobile.modelJson.perguntas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InterfaceRetrofit {

        /** Get Data Assets Json with Retrofit */
        @GET(Utility.COMPLEMENT_URL)
        Call<ListWrapper<perguntas>> getObject();

        @GET(Utility.COMPLEMENT_URL_ALIMENTO + "{alimento_parte}")
        Call<AlimentosCompletos> getAlimentos(@Path("alimento_parte") String alimento_parte);

        @POST(Utility.COMPLEMENT_URL_USUARIO)
        Call<Usuario> getUsuario(@Body Usuario usuario);

        @POST(Utility.COMPLEMENT_URL_CRIANCA)
        Call<Crianca> postCrianca(@Body Crianca crianca);

        @GET(Utility.COMPLEMENT_URL_CRIANCA_FULL)
        Call<List<Crianca>> getCriancaFull();

        @DELETE(Utility.COMPLEMENT_URL_CRIANCA_DELETE + "{id_crianca}")
        Call<Boolean> deleteCrianca(@Path("id_crianca") String id_crianca);

    }

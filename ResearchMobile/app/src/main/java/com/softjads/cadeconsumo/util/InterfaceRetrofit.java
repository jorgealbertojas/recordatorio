package com.softjads.cadeconsumo.util;


import com.softjads.cadeconsumo.model.AlimentosCompletos;
import com.softjads.cadeconsumo.modelJson.Crianca;
import com.softjads.cadeconsumo.modelJson.RespostaAdd;
import com.softjads.cadeconsumo.modelJson.Usuario;
import com.softjads.cadeconsumo.modelJson.perguntas;

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

        @POST(Utility.COMPLEMENT_URL_CREATE_USUARIO)
        Call<Usuario> createUsuario(@Body Usuario usuario);

        @POST(Utility.COMPLEMENT_URL_CRIANCA)
        Call<Crianca> postCrianca(@Body Crianca crianca);

        @GET(Utility.COMPLEMENT_URL_CRIANCA_FULL)
        Call<List<Crianca>> getCriancaFull();

        @DELETE(Utility.COMPLEMENT_URL_CRIANCA_DELETE + "{id_crianca}")
        Call<Boolean> deleteCrianca(@Path("id_crianca") String id_crianca);

        @GET(Utility.COMPLEMENT_URL_CRIANCA_CODIGO + "{id_crianca}")
        Call<Crianca> getCriancaCodigo(@Path("id_crianca") String id_crianca);

        @POST(Utility.COMPLEMENT_URL_ADICIONA_RESPOSTA + "{id_crianca}")
        Call<Crianca> postAdicionaCrianca(@Body RespostaAdd respostaAdd, @Path("id_crianca") String id_crianca);

        @POST(Utility.COMPLEMENT_URL_ADICIONA_RESPOSTA_DELETE + "{id_crianca}")
        Call<Crianca> postAdicionaCriancaDelete(@Body RespostaAdd respostaAdd, @Path("id_crianca") String id_crianca);

        @DELETE(Utility.COMPLEMENT_URL_RESPOSTA_CRIANCA_DELETE + "{id_crianca}" + "/{id_pergunta}")
        Call<Integer> deleteRespostaCrianca(@Path("id_crianca") String id_crianca, @Path("id_pergunta") String id_pergunta);

    }

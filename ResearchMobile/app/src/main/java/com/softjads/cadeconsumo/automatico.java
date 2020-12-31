package com.softjads.cadeconsumo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.softjads.cadeconsumo.SQL.sql_create;
import com.softjads.cadeconsumo.modelJson.Crianca;
import com.softjads.cadeconsumo.modelJson.RespostaAdd;
import com.softjads.cadeconsumo.modelJson.RespostaAlimento;
import com.softjads.cadeconsumo.modelJson.RespostaComplementosAlimento;
import com.softjads.cadeconsumo.service.DB;
import com.softjads.cadeconsumo.service.DataBase;
import com.softjads.cadeconsumo.util.InterfaceRetrofit;
import com.softjads.cadeconsumo.util.SharedPreferencesService;
import com.softjads.cadeconsumo.util.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class automatico extends Activity {

	private int opcaoQuestionario = 0;
	private int opcaoQuestionarioFINAL = 0;

	private String usuario = "";
	private String Nomeusuario = "";
	private String filtro_desc_pesquisa = "";
	private String filtro_previsao = "";
	private String filtro_id_cliente = "";
	private String filtro_id_pesquisa = "";
	private String filtro_automatico = "";
	private String nFONTE = "";
	private String nVOZ = "";

	private SQLiteDatabase bd;
	private DataBase nDataBase;

	private ImageView imageViewAvancar;
	private EditText editTextcodigo;

	private InterfaceRetrofit mInterfaceObject;
	/**
	 * Call post cadatrar CriancaCallback .
	 */
	private Callback<Crianca> getCriancaCodigoCallback = new Callback<Crianca>() {
		@Override
		public void onResponse(Call<Crianca> call, Response<Crianca> response) {
			try {
				if (response.isSuccessful()) {
					String ultimaResposta = "";
					if (response.body().getUltimaResposta() != null){
						if (response.body().getUltimaResposta().getIdentUnicaPergunta() != null){
							ultimaResposta = response.body().getUltimaResposta().getIdentUnicaPergunta();
						}
					}
					Entrar(editTextcodigo.getText().toString(), response.body().getId(),  ultimaResposta, response.body().getTodasRespostas());

				} else {
					Toast.makeText(getApplicationContext(), "Código inválido", Toast.LENGTH_LONG).show();
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
			Toast.makeText(automatico.this, "Entre com login e senha!",
					Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.automatico);

		Bundle extras = getIntent().getExtras();
		filtro_id_cliente = extras.getString("filtro_id_cliente");
		filtro_id_pesquisa = extras.getString("filtro_id_pesquisa");
		filtro_automatico = extras.getString("filtro_automatico");
		filtro_desc_pesquisa = extras.getString("filtro_desc_pesquisa");
		filtro_previsao = extras.getString("filtro_previsao");
		usuario = extras.getString("usuario");

		Nomeusuario = extras.getString("Nomeusuario");
		nFONTE = extras.getString("nFONTE");
		nVOZ = extras.getString("nVOZ");

		nDataBase = new DataBase(this);
		bd = nDataBase.getReadableDatabase();
		nDataBase.onCreate(bd);

		DB.getInstance(this);

		editTextcodigo = (EditText) findViewById(R.id.editTextcodigo);
		SharedPreferencesService shared = new SharedPreferencesService(this);
		editTextcodigo.setText(shared.getCodigoCrianca());

		imageViewAvancar = (ImageView) findViewById(R.id.imageViewAvancar);
		imageViewAvancar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (editTextcodigo.getText() != null) {
					createStackOverflowAPI();
					mInterfaceObject.getCriancaCodigo(editTextcodigo.getText().toString()).enqueue(getCriancaCodigoCallback);

					SharedPreferencesService shared = new SharedPreferencesService(automatico.this);
					shared.setCodigoCrianca(editTextcodigo.getText().toString());

				} else {
					Toast.makeText(getApplicationContext(), "Entre com o código", Toast.LENGTH_LONG).show();
				}
			}
		});

	}

	private void Entrar(String AlunoAtual, String AlunoAtualID, String saltoTEMP_NOVO, List<RespostaAdd> listRespostaAdd) {

		try {
			Intent WSActivity = new Intent(this, Questionario.class);
			WSActivity.putExtra("filtro_id_cliente", filtro_id_cliente);
			WSActivity.putExtra("filtro_id_pesquisa", filtro_id_pesquisa);
			WSActivity.putExtra("filtro_desc_pesquisa", filtro_desc_pesquisa);
			WSActivity.putExtra("filtro_automatico", filtro_automatico);
			WSActivity.putExtra("filtro_previsao", filtro_previsao);
			WSActivity.putExtra("usuario", usuario);
			WSActivity.putExtra("Nomeusuario", Nomeusuario);
			WSActivity.putExtra("AlunoAtual", AlunoAtual);
			WSActivity.putExtra("AlunoAtualID", AlunoAtualID);
			WSActivity.putExtra("saltoTEMP_NOVO",saltoTEMP_NOVO);
			WSActivity.putExtra("nFONTE", nFONTE);
			WSActivity.putExtra("nGPS", "false");
			WSActivity.putExtra("NomeGravacaoArquivo", "GRAVACAO_" + usuario + "_" + System.currentTimeMillis() + ".amr");
			WSActivity.putExtra("opcao", Integer.toString(1));
			WSActivity.putExtra("opcaoQuestionario", Integer.toString(opcaoQuestionario));
			WSActivity.putExtra("opcaoQuestionarioFINAL", Integer.toString(opcaoQuestionarioFINAL));
			if (true) {
				WSActivity.putExtra("nTIME", "1");
			} else {
				WSActivity.putExtra("nTIME", "0");
			}

			startActivity(WSActivity);


			if (listRespostaAdd != null) {
				for (int i = 0; i < listRespostaAdd.size(); i++) {
					insereRegistro(listRespostaAdd.get(i), AlunoAtual);
				}
			}

			this.finish();

		} catch (Throwable ex) {
			System.out.println(ex.getMessage());
		}

	}

	public void insereRegistro(RespostaAdd respostaAdd, String AlunoAtual) {

		try {

			Boolean eNormal = true;

			if (respostaAdd.getTagLivre() != null) {
				if (respostaAdd.getTagLivre().equals(sql_create.TABLE_ALIMENTO) ||
						respostaAdd.getTagLivre().equals(sql_create.TABLE_ADICOES) ||
						respostaAdd.getTagLivre().equals(sql_create.TABLE_MODO_PREPARACAO) ||
						respostaAdd.getTagLivre().equals(sql_create.TABLE_GRUPOS_ALIMENTOS) ||
						respostaAdd.getTagLivre().equals(sql_create.TABLE_MEDIDAS_CASEIRAS)) {
					eNormal = false;
				}
			}

			if (eNormal) {
				ContentValues obj = new ContentValues();
				obj.put("ID_ALUNO", AlunoAtual);
				obj.put("ID_PERGUNTA", respostaAdd.getIdPergunta());
				obj.put("ID_OPCAO", respostaAdd.getIdItemPergunta());
				obj.put("VALOR", respostaAdd.getValor());
				obj.put("ID_OPCAO_PESSOA", 0);


				// bebeto atenção mundar no futuro bebeto
				if (respostaAdd.getTagLivre() != null) {
					obj.put("ID_ALIMENTO", respostaAdd.getTagLivre());
				} else {
					obj.put("ID_ALIMENTO", "");
				}

				this.onInsert(this, obj, sql_create.TABLE_RESPOSTA);
			} else {

				Gson gson = new Gson();

				if (respostaAdd.getTagLivre().equals(sql_create.TABLE_ALIMENTO)) {
					RespostaAlimento personsFromJson = gson.fromJson(respostaAdd.getValor(), RespostaAlimento.class);
					if (personsFromJson != null) {
						ContentValues obj = new ContentValues();
						obj.put("ID_ALUNO", AlunoAtual);
						obj.put("ID", respostaAdd.getIdPergunta());
						obj.put("CODIGO", personsFromJson.getCodigo());
						obj.put("DESCRICAO", personsFromJson.getDescricao());
						obj.put("QUAL_E_ESSE_ITEM", respostaAdd.getIdItemPergunta());
						obj.put("ALIMENTO_REFEICAO", personsFromJson.getAlimento_refeicao());
						this.onInsert(this, obj, sql_create.TABLE_ALIMENTO);
					}
				} else {
					RespostaComplementosAlimento respostaComplementosAlimento = gson.fromJson(respostaAdd.getValor(), RespostaComplementosAlimento.class);
					if (respostaComplementosAlimento != null) {
						ContentValues obj = new ContentValues();
						obj.put("ID_ALUNO", AlunoAtual);
						obj.put("ID_ALIMENTO", respostaAdd.getIdPergunta());
						obj.put("DESCRICAO", respostaComplementosAlimento.getDescricao());
						this.onInsert(this, obj, respostaAdd.getTagLivre());
					}
				}

			}

		} catch (Throwable ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void onInsert(Context context, ContentValues obj, String nTabela) {

		DB.getInstance(context).insert(nTabela, obj);
	}

	private void createStackOverflowAPI() {
		Gson gson = new GsonBuilder()
				.create();

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(Utility.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create(gson))
				.build();

		mInterfaceObject = retrofit.create(InterfaceRetrofit.class);
	}

}

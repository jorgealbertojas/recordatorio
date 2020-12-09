package com.example.jorgealberto.researchmobile.service;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.jorgealberto.researchmobile.model.Pergunta;
import com.example.jorgealberto.researchmobile.model.opcao;
import com.example.jorgealberto.researchmobile.SQL.sql_create;

public class DataBase extends SQLiteOpenHelper{

	private SQLiteDatabase bd;
	private Context context;


	public DataBase(Context context){
		super(context,sql_create.DBNAME,null,sql_create.DBVERSION);
		this.context = context;
	}

	@Override
	public void onOpen(SQLiteDatabase db) {

	};

	@Override
	public void onCreate(SQLiteDatabase db) {
		// if (db.isDbLockedByCurrentThread()){
		//	 Toast.makeText(context, "Database locked by current thread..." , Toast.LENGTH_LONG).show();
		// }

		// if (db.isOpen()){
		//	 Toast.makeText(context, "OK.. Database open" , Toast.LENGTH_LONG).show();
		// }

		// if (db.isReadOnly()){
		//	 Toast.makeText(context, "The database is read only" , Toast.LENGTH_LONG).show();
		// }

		//if (db.inTransaction()){
		//	 Toast.makeText(context, "Why id the databse in transaction???" , Toast.LENGTH_LONG).show();
		//}

		// ATENÇÃO TEM QUE TIRAR
		db.execSQL("DROP TABLE IF EXISTS " + sql_create.TABLE_BLOCO );
		//db.execSQL("DROP TABLE IF EXISTS " + sql_create.TABLE_PESQUISA);
		//db.execSQL("DROP TABLE IF EXISTS " + sql_create.TABLE_ALUNO );
		//db.execSQL("DROP TABLE IF EXISTS " + sql_create.TABLE_ESCOLA );
		//db.execSQL("DROP TABLE IF EXISTS " + sql_create.TABLE_TURMA );

		db.execSQL(sql_create.CREATE_BLOCO);
		db.execSQL(sql_create.CREATE_OPCAO);
		db.execSQL(sql_create.CREATE_PERGUNTA);
		db.execSQL(sql_create.CREATE_RESPOSTA);
		db.execSQL(sql_create.CREATE_SALTO);
		db.execSQL(sql_create.CREATE_CONFIGURACAO);
		db.execSQL(sql_create.CREATE_ALUNO);
		db.execSQL(sql_create.CREATE_USUARIO);
		db.execSQL(sql_create.CREATE_ESCOLA);
		db.execSQL(sql_create.CREATE_TURMA);
		db.execSQL(sql_create.CREATE_PESQUISA);
		db.execSQL(sql_create.CREATE_CONTROLE_INICIO);
		db.execSQL(sql_create.CREATE_CONTROLE_FIM);
		db.execSQL(sql_create.CREATE_PESQUISA_USUARIO);

		db.execSQL(sql_create.CREATE_ALIMENTO);
		db.execSQL(sql_create.CREATE_GRUPOS_ALIMENTOS);
		db.execSQL(sql_create.CREATE_MODO_PREPARACAO);
		db.execSQL(sql_create.CREATE_MEDIDAS_CASEIRAS);
		db.execSQL(sql_create.CREATE_ADICOES);



		//Toast.makeText(context, "Criação Tabelas" , Toast.LENGTH_LONG).show();

		ContentValues obj = new ContentValues();

		obj.put("ID_BLOCO","0");
		obj.put("DESCBLOCO","teste descrição bloco");
		obj.put("INFORMACAO","teste bloco");
		db.insert(sql_create.TABLE_BLOCO, null, obj);

		obj.put("ID_BLOCO","1");
		obj.put("DESCBLOCO","teste descrição bloco");
		obj.put("INFORMACAO","teste bloco");
		db.insert(sql_create.TABLE_BLOCO, null, obj);

		obj.put("ID_BLOCO","2");
		obj.put("DESCBLOCO","teste descrição bloco");
		obj.put("INFORMACAO","teste bloco");
		db.insert(sql_create.TABLE_BLOCO, null, obj);

		obj.put("ID_BLOCO","3");
		obj.put("DESCBLOCO","teste descrição bloco");
		obj.put("INFORMACAO","teste bloco");
		db.insert(sql_create.TABLE_BLOCO, null, obj);

		obj.put("ID_BLOCO","4");
		obj.put("DESCBLOCO","teste descrição bloco");
		obj.put("INFORMACAO","teste bloco");
		db.insert(sql_create.TABLE_BLOCO, null, obj);

		obj.put("ID_BLOCO","5");
		obj.put("DESCBLOCO","teste descrição bloco");
		obj.put("INFORMACAO","teste bloco");
		db.insert(sql_create.TABLE_BLOCO, null, obj);

		//obj = new ContentValues();

		//obj.put("IP","146.164.25.139");
		//obj.put("CLIENTE","IBOPE");
		//obj.put("PESQUISA", "IBOPE_TESTE");
		//obj.put("AUTOMATICO", "0");
		//obj.put("AUTOMATICO", "1");
		//db.insert(sql_create.TABLE_CONFIGURACAO, null, obj);

		//obj = new ContentValues();

		//obj.put("ID_USUARIO","1");
		//obj.put("NOME","ibope");
		//obj.put("SENHA", "123");
		//obj.put("ID_GRUPO", "1");
		//obj.put("MATRICULA", "1");
		//db.insert(sql_create.TABLE_USUARIOS, null, obj);


		//obj = new ContentValues();
		//obj.put("ID_ESCOLA","1");
		//obj.put("COD_UF","1");
		//obj.put("COD_MUNIC", "1");
		//obj.put("COD_BAIRRO", "1");
		//obj.put("NOME_ESCOLA", "ESCOLA ESTATUAL 1");
		//obj.put("ENDERECO","ENDERECO 1");
		//obj.put("NUMERO","NUMERO 1");
		//obj.put("TIPO", "1");
		//obj.put("SUBTIPO", "1");
		//obj.put("REGIAO", "1");
		//db.insert(sql_create.TABLE_ESCOLA, null, obj);

		//obj = new ContentValues();
		//obj.put("ID_ESCOLA","2");
		//obj.put("COD_UF","2");
		//obj.put("COD_MUNIC", "2");
		//obj.put("COD_BAIRRO", "2");
		//obj.put("NOME_ESCOLA", "ESCOLA MUNICIPAL 2");
		//obj.put("ENDERECO","ENDERECO 2");
		//obj.put("NUMERO","NUMERO 2");
		//obj.put("TIPO", "1");
		//obj.put("SUBTIPO", "1");
		//obj.put("REGIAO", "1");
		//db.insert(sql_create.TABLE_ESCOLA, null, obj);

		//obj = new ContentValues();
		//obj.put("ID_ESCOLA","3");
		//obj.put("COD_UF","3");
		//obj.put("COD_MUNIC", "3");
		//obj.put("COD_BAIRRO", "3");
		//obj.put("NOME_ESCOLA", "ESCOLA MUNICIPAL 3");
		//obj.put("ENDERECO","ENDERECO 3");
		//obj.put("NUMERO","NUMERO 3");
		//obj.put("TIPO", "1");
		//obj.put("SUBTIPO", "1");
		//obj.put("REGIAO", "1");
		//db.insert(sql_create.TABLE_ESCOLA, null, obj);

		//obj = new ContentValues();
		//obj.put("ID_TURMA","1");
		//obj.put("NOME_TURMA","TURMA 1");
		//obj.put("TURNO", "1");
		//obj.put("ID_ESCOLA", "1");
		//db.insert(sql_create.TABLE_TURMA, null, obj);

		//obj = new ContentValues();
		//obj.put("ID_TURMA","2");
		//obj.put("NOME_TURMA","TURMA 2");
		//obj.put("TURNO", "1");
		//obj.put("ID_ESCOLA", "1");
		//db.insert(sql_create.TABLE_TURMA, null, obj);



		//obj = new ContentValues();

		//obj.put("ID_PERGUNTAS","1");
		//obj.put("DESCRICAO","Você gosta de jogar futebol?");
		//obj.put("ID_BLOCO", "1");
		//db.insert(sql_create.TABLE_PERGUNTA, null, obj);
		//	db.setTransactionSuccessful();
		//	db.endTransaction();



		//obj.put("ID_PERGUNTAS","2");
		//obj.put("DESCRICAO","Você pratica esporte?");
		//obj.put("ID_BLOCO", "1");
		//db.insert(sql_create.TABLE_PERGUNTA, null, obj);

		//obj.put("ID_PERGUNTAS","3");
		//obj.put("DESCRICAO","Quais esportes você gosta?");
		//obj.put("ID_BLOCO", "1");
		//db.insert(sql_create.TABLE_PERGUNTA, null, obj);

		//obj.put("ID_PERGUNTAS","4");
		//obj.put("DESCRICAO","Quantos irmãos você têm?");
		//obj.put("ID_BLOCO", "1");
		//db.insert(sql_create.TABLE_PERGUNTA, null, obj);

		//obj.put("ID_PERGUNTAS","5");
		//obj.put("DESCRICAO","o que você achou do questionario? A tamanho da pergunta pode ser muito grande que o sistema faz cauculo matematico, para gerar a posição do frase!");
		//obj.put("ID_BLOCO", "1");
		//db.insert(sql_create.TABLE_PERGUNTA, null, obj);

		//obj = new ContentValues();
		//obj.put("ID_OPCAO","1");
		//obj.put("MAXIMO", "0");
		//obj.put("VALOR","0");
		//obj.put("OPCAO","sim");
		//obj.put("MINIMO","0");
		//obj.put("ID_PERGUNTA","1");
		//obj.put("SALTO","0");
		//obj.put("ORDENA","1");

		//db.insert(sql_create.TABLE_OPCAO, null, obj);

		//obj.put("ID_OPCAO","2");
		//obj.put("MAXIMO", "0");
		//obj.put("VALOR","0");
		//obj.put("OPCAO","não");
		//obj.put("MINIMO","0");
		//obj.put("ID_PERGUNTA","1");
		//obj.put("SALTO","0");
		//obj.put("ORDENA","2");

		//db.insert(sql_create.TABLE_OPCAO, null, obj);

		//obj.put("ID_OPCAO","3");
		//obj.put("MAXIMO", "0");
		//obj.put("VALOR","0");
		//obj.put("OPCAO","sim");
		//obj.put("MINIMO","0");
		//obj.put("ID_PERGUNTA","2");
		//obj.put("SALTO","0");
		//obj.put("ORDENA","1");

		//db.insert(sql_create.TABLE_OPCAO, null, obj);

		//obj.put("ID_OPCAO","4");
		//obj.put("MAXIMO", "0");
		//obj.put("VALOR","0");
		//obj.put("OPCAO","não");
		//obj.put("MINIMO","0");
		//obj.put("ID_PERGUNTA","2");
		//obj.put("SALTO","4");
		//obj.put("ORDENA","2");

		//db.insert(sql_create.TABLE_OPCAO, null, obj);

		//obj.put("ID_OPCAO","5");
		//obj.put("MAXIMO", "0");
		//obj.put("VALOR","2");
		//obj.put("OPCAO","Volei");
		//obj.put("MINIMO","0");
		//obj.put("ID_PERGUNTA","3");
		//obj.put("SALTO","0");
		//obj.put("ORDENA","1");

		//db.insert(sql_create.TABLE_OPCAO, null, obj);

		//obj.put("ID_OPCAO","6");
		//obj.put("MAXIMO", "0");
		//obj.put("VALOR","2");
		//obj.put("OPCAO","futebol");
		//obj.put("MINIMO","0");
		//obj.put("ID_PERGUNTA","3");
		//obj.put("SALTO","0");
		//obj.put("ORDENA","2");

		//db.insert(sql_create.TABLE_OPCAO, null, obj);

		//obj.put("ID_OPCAO","7");
		//obj.put("MAXIMO", "0");
		//obj.put("VALOR","2");
		//obj.put("OPCAO","natação");
		//obj.put("MINIMO","0");
		//obj.put("ID_PERGUNTA","3");
		//obj.put("SALTO","0");
		//obj.put("ORDENA","3");

		//db.insert(sql_create.TABLE_OPCAO, null, obj);

		//obj.put("ID_OPCAO","8");
		//obj.put("MAXIMO", "0");
		//obj.put("VALOR","2");
		//obj.put("OPCAO","judo");
		//obj.put("MINIMO","0");
		//obj.put("ID_PERGUNTA","3");
		//obj.put("SALTO","0");
		//obj.put("ORDENA","4");

		//db.insert(sql_create.TABLE_OPCAO, null, obj);

		//obj.put("ID_OPCAO","9");
		//obj.put("MAXIMO", "0");
		//obj.put("VALOR","2");
		//obj.put("OPCAO","basquete");
		//obj.put("MINIMO","0");
		//obj.put("ID_PERGUNTA","3");
		//obj.put("SALTO","0");
		//obj.put("ORDENA","5");

		//db.insert(sql_create.TABLE_OPCAO, null, obj);

		//obj.put("ID_OPCAO","10");
		//obj.put("MAXIMO", "10");
		//obj.put("VALOR","1");
		//obj.put("OPCAO","irmãos");
		//obj.put("MINIMO","0");
		//obj.put("ID_PERGUNTA","4");
		//obj.put("SALTO","0");
		//obj.put("ORDENA","1");

		//db.insert(sql_create.TABLE_OPCAO, null, obj);

		//obj.put("ID_OPCAO","11");
		//obj.put("MAXIMO", "0");
		//obj.put("VALOR","0");
		//obj.put("OPCAO","muito bom");
		//obj.put("MINIMO","0");
		//obj.put("ID_PERGUNTA","5");
		//obj.put("SALTO","0");
		//obj.put("ORDENA","1");

		//db.insert(sql_create.TABLE_OPCAO, null, obj);

		//obj.put("ID_OPCAO","12");
		//obj.put("MAXIMO", "0");
		//obj.put("VALOR","0");
		//obj.put("OPCAO","bom");
		//obj.put("MINIMO","0");
		//obj.put("ID_PERGUNTA","5");
		//obj.put("SALTO","0");
		//obj.put("ORDENA","2");

		//db.insert(sql_create.TABLE_OPCAO, null, obj);

		//obj.put("ID_OPCAO","13");
		//obj.put("MAXIMO", "0");
		//obj.put("VALOR","0");
		//obj.put("OPCAO","ruim");
		//obj.put("MINIMO","0");
		//obj.put("ID_PERGUNTA","5");
		//obj.put("SALTO","0");
		//obj.put("ORDENA","3");

		//db.insert(sql_create.TABLE_OPCAO, null, obj);

		//obj.put("ID_OPCAO","14");
		//obj.put("MAXIMO", "0");
		//obj.put("VALOR","0");
		//obj.put("OPCAO","muito ruim muito ruim muito ruim muito ruim muito ruim muito ruim");
		//obj.put("MINIMO","0");
		//obj.put("ID_PERGUNTA","5");
		//obj.put("SALTO","0");
		//obj.put("ORDENA","4");

		//db.insert(sql_create.TABLE_OPCAO, null, obj);

		//obj.put("ID_OPCAO","15");
		//obj.put("MAXIMO", "0");
		//obj.put("VALOR","0");
		//obj.put("OPCAO","péssimo");
		//obj.put("MINIMO","0");
		//obj.put("ID_PERGUNTA","5");
		//obj.put("SALTO","0");
		//obj.put("ORDENA","5");

		//db.insert(sql_create.TABLE_OPCAO, null, obj);

		//Toast.makeText(context, "Inserida pergunta" , Toast.LENGTH_LONG).show();

	}

	@Override
	public void onUpgrade(SQLiteDatabase ndb, int oldVersion, int newVersion) {

	}

	public long insert(String table,ContentValues values){



		bd = this.getWritableDatabase();

		long row = bd.insert(table, null, values);
		bd.close();

		return row;
	}

	public long delete(String table,String[] strings){



		bd = this.getWritableDatabase();

		long row = bd.delete(table, null , strings);
		bd.close();

		return row;
	}


	public Pergunta getPergunta(Cursor ncursor)
	{
		Pergunta pergunta = null;
		//bd = this.getReadableDatabase();
		//onCreate(bd);
		//Cursor cursor = bd.rawQuery(sql_select.GET_PERGUNTA, new String[] { id } );
		//Cursor cursor = bd.rawQuery(sql_select.GET_PERGUNTA_COMPLETA,null);
		//ncursor.moveToFirst();

		//while(!ncursor.isAfterLast() )
		//{
		pergunta = new Pergunta();

		try
		{
			pergunta.setID_PERGUNTAS(ncursor.getString(0));
			pergunta.setDESCRICAO(ncursor.getString(1));
			pergunta.setID_BLOCO(ncursor.getInt(2));
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			//}




		}
		//cursor.close();
		ncursor.moveToNext();
		return pergunta;

	}


	public void insereObjeto(opcao nOpcao){

		//bd = nDataBase.getWritableDatabase();

		ContentValues obj = new ContentValues();

		obj.put("ID_OPCAO",nOpcao.getID_OPCAO());
		obj.put("MAXIMO", nOpcao.getMAXIMO());
		obj.put("VALOR",nOpcao.getVALOR());
		obj.put("OPCAO",nOpcao.getOPCAO());
		obj.put("MINIMO",nOpcao.getMINIMO());
		obj.put("ID_PERGUNTA",nOpcao.getID_PERGUNTA());
		obj.put("SALTO",nOpcao.getSALTO());
		obj.put("ORDENA",nOpcao.getORDENA());

		bd.isOpen();
		bd.insert(sql_create.TABLE_OPCAO, null, obj);
		obj = new ContentValues();
	}

	public void insereObjeto(Object nObject){
		opcao nOpcao;
		nOpcao = new opcao();

		if (nObject == nOpcao) {

		}
		//bd = nDataBase.getWritableDatabase();

		ContentValues obj = new ContentValues();

		obj.put("ID_OPCAO",nOpcao.getID_OPCAO());
		obj.put("MAXIMO", nOpcao.getMAXIMO());
		obj.put("VALOR",nOpcao.getVALOR());
		obj.put("OPCAO",nOpcao.getOPCAO());
		obj.put("MINIMO",nOpcao.getMINIMO());
		obj.put("ID_PERGUNTA",nOpcao.getID_PERGUNTA());
		obj.put("SALTO",nOpcao.getSALTO());
		obj.put("ORDENA",nOpcao.getORDENA());

		bd.isOpen();
		bd.insert(sql_create.TABLE_OPCAO, null, obj);
		obj = new ContentValues();
	}



	//public ArrayList<opcao> getAll() {

	//ArrayList<opcao> result = new ArrayList<opcao>();

	//bd = this.getReadableDatabase();

	//Cursor cursor = bd.rawQuery(sql_select.GET_OPCAO, null);

	//cursor cursor
	//return null;


	//}



}


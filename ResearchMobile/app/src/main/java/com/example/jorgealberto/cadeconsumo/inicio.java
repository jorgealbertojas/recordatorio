package com.example.jorgealberto.cadeconsumo;

import java.io.IOException;

import com.example.jorgealberto.cadeconsumo.library.MyConstant;
import com.example.jorgealberto.cadeconsumo.service.DB;
import com.example.jorgealberto.cadeconsumo.service.DataBase;
import com.example.jorgealberto.cadeconsumo.SQL.sql_create;
import com.example.jorgealberto.cadeconsumo.SQL.sql_delete;
import com.example.jorgealberto.cadeconsumo.SQL.sql_select;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;


public class inicio extends Activity {

	private Boolean Liberado = false;
	private String usuario = "";
	private String Nomeusuario = "";
	private int AlunoAtual = 0;
	private String filtro_desc_pesquisa = "";
	private String filtro_previsao = "";
	private String filtro_id_cliente = "";
	private String filtro_id_pesquisa = "";
	private String filtro_automatico = "";
	private String log = "";
	private String lat = "";
	private Boolean nVOZ = false;
	private String NomeGravacaoArquivo = "";
	private String NomeGravacao = "";
	
  	private SQLiteDatabase bd;
  	private Context context;
	private DataBase nDataBase;
	
	MediaRecorder recorder = new MediaRecorder(); 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.iniciar);
	     
	    nDataBase = new DataBase(this);
		bd = nDataBase.getReadableDatabase();
		nDataBase.onCreate(bd);
		DB.getInstance(this);
			
		 ImageButton btn_create = (ImageButton) findViewById(R.id.imageButton1);
		 
			btn_create.setOnClickListener(new View.OnClickListener() {
	        	@Override
	        	public void onClick(View arg0) {
	        		  insereRegistro();
	        		  Buscaproximoaluno();
	        		  
	        			//bebeto
	        			if (nVOZ){
	        				try {
								startRecord();
							} catch (IllegalStateException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	        			}
						
						Entrar();
						
	        		  //Intent WSActivity = new Intent(automatico.this, confirmar.class);
	        		  //startActivity(WSActivity);	
	        	}
	        });
			
			ImageButton nImageButton = (ImageButton) findViewById(R.id.imageButton1);
			nImageButton.setOnLongClickListener(new View.OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					
					fechar();
					return false;
				}
	        });
			
	 }
	 
	 public void fechar(){
		 this.finish();
	 }
	 
	 private void startRecord() throws IllegalStateException, IOException{
         
         recorder.setAudioSource(MediaRecorder.AudioSource.MIC);  //ok so I say audio source is the microphone, is it windows/linux microphone on the emulator? 
         recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); 
         recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); 
     
         NomeGravacaoArquivo = "GRAVACAO_" + usuario + "_" + System.currentTimeMillis()+".amr";
         
         String nNomeGravacao = MyConstant.storage +  NomeGravacaoArquivo;
         NomeGravacao = nNomeGravacao;
         recorder.setOutputFile(nNomeGravacao); 
         recorder.prepare(); 
         recorder.start(); 
	            }

     private void stopRecord(){
         recorder.stop();
       //recorder.release();
     }
	  
	  private void Entrar(){
			bd.execSQL(sql_delete.DEL_TODOS_CONTROLE_INICIO,new String[] {Integer.toString(AlunoAtual)});
	        Intent WSActivity = new Intent(this, Questionario.class);
	        startActivity(WSActivity);
	  }
	  
	 
	 public void Buscaproximoaluno(){
		 try{
			 //int nMaxAluno = 0;
			 Cursor cursor = bd.rawQuery(sql_select.GET_ALUNO_MAX,null);
			 cursor.moveToFirst();
			 try{
				 AlunoAtual = cursor.getInt(0);
			 }
			 finally{
	    		 	cursor.close();
	    	 	}
    	 	}
    	 	catch (Throwable ex){
	    		  
    	 	}
	 }

	 
	 public void insereRegistro(){
    	 try{
    	 int nMaxAluno = 0;
    	 Cursor cursor = bd.rawQuery(sql_select.GET_ALUNO_MAX,null);
    	 cursor.moveToFirst();
    	 try{
    	 
    	 if (cursor.getCount() > 0) {
    		 nMaxAluno = cursor.getInt(0);
    		 nMaxAluno = nMaxAluno + 1;
    	 }
    	 
     	ContentValues obj = new ContentValues(); 
 		obj.put("ID_ALUNO",nMaxAluno);
 		obj.put("ID_ESCOLA", "1");
 		obj.put("ID_TURMA","1");
 		obj.put("NOME","Entrevistado");
 		obj.put("DT_NASC","");
 		obj.put("SEXO","");
        this.onInsert( context, obj , sql_create.TABLE_ALUNO);  
    	 }
    	 finally{
    		 cursor.close();
    	 }
    	  }
    	  catch (Throwable ex){
    		  
    	  }
     }
	 
     private void onInsert(Context context, ContentValues obj, String nTabela) { 
    	  try{
    		      		  
    		  DB.getInstance( context ).insert( nTabela, obj ); 
    	  }
    	  catch (Throwable ex){
    		  
    	  }
    	  
    } 
    

}

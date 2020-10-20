package com.example.jorgealberto.researchmobile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jorgealberto.researchmobile.library.MyConstant;
import com.example.jorgealberto.researchmobile.model.Aluno;
import com.example.jorgealberto.researchmobile.model.Escola;
import com.example.jorgealberto.researchmobile.model.Ksoap2ResultParser;
import com.example.jorgealberto.researchmobile.model.Pergunta;
import com.example.jorgealberto.researchmobile.model.Turma;
import com.example.jorgealberto.researchmobile.model.opcao;
import com.example.jorgealberto.researchmobile.service.DB;
import com.example.jorgealberto.researchmobile.service.DataBase;
import com.example.jorgealberto.researchmobile.SQL.sql_create;
import com.example.jorgealberto.researchmobile.SQL.sql_delete;
import com.example.jorgealberto.researchmobile.SQL.sql_select;



 public class ExecutorsActivity extends Activity {

	 private String nFONTE = "";
	 private String NomeAplicacaoNova = "";
	 private String filtro_id_cliente = "";
	 private String filtro_id_pesquisa = "";
	 private String filtro_desc_pesquisa = "";
	 private String filtro_automatico = "";
	 private String filtro_previsao = "";
	 private String Nomeusuario = "";
	 private String usuario = "";

	 private boolean nAutomatico = false;
	 private boolean nTIME = false;
	 private boolean nVOZ = false;
	 private boolean nGPS = false;
	 private boolean Liberado = false;
	 
		private ProgressDialog progressDialog;
	 
	  	private SQLiteDatabase bd;

		private DataBase nDataBase;
		
		private String Arrayfiltro_id_cliente[]; 
		private String Arrayfiltro_id_pesquisa[]; 
		private String Arrayfiltro_automatico[];
		private String Arrayfiltro_desc_pesquisa[];
		private String Arrayfiltro_previsao[];

		private Context context; 
		//private DataBase nDataBase;
		
		
		public final String SOAP_ACTION = "http://ericapesquisa.org/BuscarDadosGenerico";
		public  final String OPERATION_NAME = "BuscarDadosGenerico"; 
		public  final String WSDL_TARGET_NAMESPACE = "http://ericapesquisa.org/";
		public  final String SOAP_ADDRESS = "http://"+ MyConstant.ip_servidor+"/WebServiceSubFormulario/ApoioErica.asmx";

		private static final int TAM_MAX_BUFFER = 10240; // 10Kbytes
		
		private boolean nOK = true;
		
		ImageButton button;
		MyControlThread mThread;
		ProgressDialog mDialog;
		Handler mHandler;
		int LIMITE = 10;
     
		/** Called when the activity is first created. */
     
		@Override
     
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			
			setContentView(R.layout.main);
			
			
			nDataBase = new DataBase(this);
			bd = nDataBase.getReadableDatabase();
			nDataBase.onCreate(bd);
			DB.getInstance(this);
			
			
            String Nomeusuario = carregarsuario_login();
			Cursor cusrsorlogin = bd.rawQuery(sql_select.GET_USUARIO2,new String[] {(carregarsuario_login())});
			cusrsorlogin.moveToFirst();
				
			if (cusrsorlogin.getCount() > 0) {
				Liberado = true;
				usuario = cusrsorlogin.getString(2).toString();
				Nomeusuario = cusrsorlogin.getString(0).toString();
			}

			
			Cursor cursor1 = bd.rawQuery(sql_select.GET_PESQUISA_usuario,new String[] {(Nomeusuario)});
			//Cursor cursor1 = bd.rawQuery(sql_select.GET_PESQUISA_usuario,new String[] {(modulo.usuario)});
			//Cursor cursor1 = bd.rawQuery(sql_select.GET_PESQUISA_usuario,null);
			//Cursor cursorConfiguracao = bd.rawQuery(sql_select.GET_PESQUISA,null);
			Cursor cursorConfiguracao = bd.rawQuery(sql_select.GET_PESQUISA_usuario,new String[] {(Nomeusuario)});
			
			pesquisaSelecionada();
			
			carregarGPS();
			carregarVOZ();
			carregarFONTE();
			
			try{

				cursor1.moveToFirst();
				int i = 0;
				Arrayfiltro_id_cliente = new String[cursor1.getCount()];
				Arrayfiltro_id_pesquisa = new String[cursor1.getCount()];
				Arrayfiltro_automatico = new String[cursor1.getCount()];
				Arrayfiltro_desc_pesquisa = new String[cursor1.getCount()];
				Arrayfiltro_previsao = new String[cursor1.getCount()];
				
				if (cursor1.getCount()>0){

				
			
				filtro_id_cliente = cursor1.getString(0);
				filtro_id_pesquisa = cursor1.getString(1);
				filtro_desc_pesquisa = cursor1.getString(2);
				filtro_automatico = cursor1.getString(3);
				filtro_previsao = cursor1.getString(4);
				
				cursorConfiguracao.moveToFirst();
			
				int ii = 0;
				if (cursorConfiguracao.getCount() > 0){
				
				} 
			
				while(!cursor1.isAfterLast() )
				{
					Arrayfiltro_id_cliente[i] = cursor1.getString(0);
					Arrayfiltro_id_pesquisa[i] = cursor1.getString(1);
					Arrayfiltro_desc_pesquisa[i] = cursor1.getString(2);
					Arrayfiltro_automatico[i] = cursor1.getString(3);
					Arrayfiltro_previsao[i] = cursor1.getString(4);
					
					if (cursorConfiguracao.getCount() > 0){
						if (cursor1.getString(1) == cursorConfiguracao.getString(1)){
							ii = i;
						}
					}
					cursor1.moveToNext();
					i = i + 1;
				}
				cursor1.close();
				cursorConfiguracao.close();
			
				//ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, ArrayEscola);
				//AutoCompleteTextView clubes = (AutoCompleteTextView) findViewById(R.id.clubes);
				//clubes.setAdapter(adp); 
		
				Spinner combo1 = (Spinner) findViewById(R.id.spinner1);
				ArrayAdapter adp1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Arrayfiltro_desc_pesquisa);
				adp1.setDropDownViewResource(android.R.layout.simple_spinner_item);
				combo1.setAdapter(adp1);
         
				combo1.setId(ii);
         


				combo1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					filtro_id_cliente = (Arrayfiltro_id_cliente[(int) arg3].toString());
					filtro_id_pesquisa = (Arrayfiltro_id_pesquisa[(int) arg3].toString());
					filtro_desc_pesquisa = (Arrayfiltro_desc_pesquisa[(int) arg3].toString());
					filtro_automatico = (Arrayfiltro_automatico[(int) arg3].toString());
					filtro_previsao = (Arrayfiltro_previsao[(int) arg3].toString());

					
				}

		          
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				};
			
			});
				
				CheckBox repeatChkBx = ( CheckBox ) findViewById( R.id.checkBoxGPS );
				repeatChkBx.setOnCheckedChangeListener(new OnCheckedChangeListener()
				{
				    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				    {
				    	salvarGPS();
				    }
				});
				
				CheckBox repeatChkBxVoz = ( CheckBox ) findViewById( R.id.checkBoxvoz );
				repeatChkBxVoz.setOnCheckedChangeListener(new OnCheckedChangeListener()
				{
				    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				    {
				    	salvarVOZ();
				    }
				});
				
				
				
				final RadioButton rPequeno = ( RadioButton ) findViewById( R.id.rPequena );
				final RadioButton rMedio = ( RadioButton ) findViewById( R.id.rMedia);
				final RadioButton rGrande = ( RadioButton ) findViewById( R.id.rGrande );
				
				rPequeno.setOnClickListener(new OnClickListener() {

		            @Override
		            public void onClick(View view) {
		            	 rMedio.setChecked(false);
		            	 rGrande.setChecked(false);
			             ((RadioButton) view).setChecked(true);
			             salvarFONTE();
		            }
		        });
				
				rMedio.setOnClickListener(new OnClickListener() {

		            @Override
		            public void onClick(View view) {
		            	 rPequeno.setChecked(false);
		            	 rGrande.setChecked(false);
			             ((RadioButton) view).setChecked(true);
			             salvarFONTE();
		            }
		        });
				
				rGrande.setOnClickListener(new OnClickListener() {

		            @Override
		            public void onClick(View view) {
		            	 rPequeno.setChecked(false);
		            	 rMedio.setChecked(false);
			             ((RadioButton) view).setChecked(true);
			             salvarFONTE();
		            }
		        });
				

				button = (ImageButton) findViewById(R.id.iniciarthread);
				button.setOnClickListener(new  OnClickListener() {
					public void onClick(View v) {
						try{
			
							if (ExisteDados()){
								apagaTodosRegistrosRespsotaAluno();
							}
							else
							{
								this.del(sql_create.TABLE_CONFIGURACAO);
								SaveConfiguracao();
								pesquisaSelecionada();
								ativaThread();
								Verificaatualizacao();
							}
							
					

						} catch (Exception exception) {
							mDialog.setMessage("Atenção. Problemas com a internet ");
						}
        	  
					}
					
					private boolean ExisteDados(){

						Cursor cursorRESPOSTA;
						cursorRESPOSTA = bd.rawQuery(sql_select.GET_RESPOSTA,null);
						cursorRESPOSTA.moveToFirst();
						int conta = cursorRESPOSTA.getCount();
						cursorRESPOSTA.close();
						
						if (conta > 0) {
							return true;
						}else{
							return false;
						}
					
					
					}
					
					private void del(String nTabela)                           
					{                                   
						this.onDEL( context, nTabela);              
                                       
					}  
          
					private void onDEL(Context context, String nTabela) { 
         	 
						DB.getInstance( context ).delete( nTabela, null );      
					} 
					private void SaveConfiguracao(){
						ContentValues obj = new ContentValues(); 
						obj.put("ID_CLIENTE",filtro_id_cliente);
						obj.put("ID_PESQUISA", filtro_id_pesquisa);
						obj.put("DSC_PESQUISA",filtro_desc_pesquisa);
						obj.put("AUTOMATICO",filtro_automatico);
						obj.put("PREVISAO",filtro_previsao);
						
						this.onInsert( context, obj , sql_create.TABLE_CONFIGURACAO);  
					}
					private void onInsert(Context context, ContentValues obj, String nTabela) { 
        	  
						DB.getInstance( context ).insert( nTabela, obj );      
					}  
          
				});
         
/*
				button.setOnTouchListener(new View.OnTouchListener() 
				{ 
					public boolean onTouch(View v, MotionEvent event) { switch (event.getAction()) { 
					case MotionEvent.ACTION_DOWN: {  
						((ImageButton) v).setBackgroundResource(R.mipmap.refresh_2);
						v.invalidate(); 
						break;
					}
					case MotionEvent.ACTION_UP: 
					{ 
						((ImageButton) v).setBackgroundResource(R.mipmap.refresh_1);
						v.invalidate(); 
						break; 
					} 
					} 
					return false; 
					}
				});*/
         
				// Fim
				}else{
					Toast.makeText(ExecutorsActivity.this, "NãO existe PESQUISAS associados a esse USUÁRIO!", Toast.LENGTH_SHORT).show();
					this.finish();
				}
			}
			finally{
			cursorConfiguracao.close();
			cursor1.close();
				
		}
         
     }

     private void ativaThread() {
    	 mDialog = ProgressDialog.show(this, "Aguarde", "Processando...", false, false);
    	 mHandler = new Handler();
    	 mThread = new MyControlThread(LIMITE);
    	 mThread.start();
     }

     private class MyControlThread extends Thread {
      private int numTasks;
      
      public MyControlThread(int tasks) {
       this.numTasks = tasks;
      }
      
      @Override
       public void run() {
         ExecutorService executor =  Executors.newSingleThreadExecutor();
       //for (int i = 1; i <= numTasks; i++) {
        //Runnable worker = new MyTask("task" +i, 5);
        //executor.submit(worker);
       //}
         
       //Webservice v = new Webservice("http://146.164.25.139/EricaWebServ/EricaSrv.asmx");
       //v.getEstados();
       
       Runnable worker = new MythreadWeservice("...Aguarde...", 22);
       executor.submit(worker);
       
       executor.shutdown();
       while (!executor.isTerminated()) {
        try {
      Thread.sleep(1000);
     } catch (InterruptedException e) {
     }
       }
       mHandler.post(new Runnable() {
        @Override
        public void run() {
         mDialog.dismiss();
        }
       }); 
      }
     }
     
     private class MyTask implements Runnable {
      private int numLoops;
      private String nome;
      
      public MyTask(String nome, int loops) {
       this.nome = nome;
       numLoops = loops;
      }
      
       public void run() {
       mHandler.post(new Runnable() {
        @Override
        public void run() {
         mDialog.setMessage("Atualizando sistema com a pesquisa " + filtro_desc_pesquisa);
        }
       });       
    
       fazerAlgoDemorado();
       
       mHandler.post(new Runnable() {
        @Override
        public void run() {
         Toast.makeText(ExecutorsActivity.this, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
        }
       });
      }
         
       private void fazerAlgoDemorado() {
       int i = 0;
       do {
         try {
          Thread.sleep(1000); 
          i++;
         } catch (InterruptedException e) { 
         }
        } while (i<numLoops); 
        
       } 
     }
     
     
     private class MythreadWeservice implements Runnable {
         private int numLoops;
         private String nome;
         
         ArrayList<Object> ListaOpcao;
         ArrayList<Object> ListaPergunta;
         ArrayList<Object> ListaAluno;
         ArrayList<Object> ListaTurma;
         ArrayList<Object> ListaEscola;
         
         //ArrayList list = new ArrayList();
        // Object seuObjeto = new Object();
       
         public MythreadWeservice(String nome, int loops) {
          this.nome = nome;
          numLoops = loops;
         }
         
          public void run() {
          mHandler.post(new Runnable() {
           @Override
           public void run() {
            mDialog.setMessage("Atualizando sistema com a pesquisa " + filtro_desc_pesquisa  + nome);
           }
          });       
       
          
          //Webservice v = new Webservice(SOAP_ADDRESS);
          //v.getEstados();
          try {
        	  
        	  this.del(sql_create.TABLE_SALTO);
              this.del(sql_create.TABLE_OPCAO);
              this.del(sql_create.TABLE_PERGUNTA);
              this.del(sql_create.TABLE_RESPOSTA);
              this.del(sql_create.TABLE_ALUNO);
              this.del(sql_create.TABLE_ESCOLA);
              this.del(sql_create.TABLE_TURMA);
              this.del(sql_create.TABLE_CONTROLE_FIM);
              this.del(sql_create.TABLE_CONTROLE_INICIO);
              
              nome = ChamaThead();
              
              
			
              // opcao
              opcao Opcao = new opcao();
              ListaOpcao = new ArrayList<Object>();
              nome = Ksoap2ResultParser.MinhaparseBusinessObject(nome,((Object) Opcao),((ArrayList<Object>) ListaOpcao), "T_OPCAO");
              ForDaLista(ListaOpcao,Opcao.getClass().getSimpleName());
	    
              // Pergunta
              Pergunta OPergunta = new Pergunta();
              ListaPergunta = new ArrayList<Object>();
              nome = Ksoap2ResultParser.MinhaparseBusinessObject(nome,((Object) OPergunta),((ArrayList<Object>) ListaPergunta), "T_PERGUNTA");
              ForDaLista(ListaPergunta,OPergunta.getClass().getSimpleName());
			
              
              if (filtro_automatico.toString().equals("1")) {
            	  // Aluno
            	  Aluno OAluno = new Aluno();
            	  ListaAluno = new ArrayList<Object>();
            	  nome = Ksoap2ResultParser.MinhaparseBusinessObject(nome,((Object) OAluno),((ArrayList<Object>) ListaAluno), "T_ALUNO");
            	  ForDaLista(ListaAluno,OAluno.getClass().getSimpleName());
              
            	  // Escola
            	  Escola OEscola = new Escola();
            	  ListaEscola = new ArrayList<Object>();
            	  nome = Ksoap2ResultParser.MinhaparseBusinessObject(nome,((Object) OEscola),((ArrayList<Object>) ListaEscola), "T_ESCOLA");
            	  ForDaLista(ListaEscola,OEscola.getClass().getSimpleName());
			
            	  // Turma
            	  Turma OTurma = new Turma();
            	  ListaTurma = new ArrayList<Object>();
            	  nome = Ksoap2ResultParser.MinhaparseBusinessObject(nome,((Object) OTurma),((ArrayList<Object>) ListaTurma), "T_TURMA");
            	  ForDaLista(ListaTurma,OTurma.getClass().getSimpleName());
			
              }



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
          mHandler.post(new Runnable() {
           @Override
           public void run() {
        	   if (nOK){
        		   Toast.makeText(ExecutorsActivity.this, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
        	   }else{
        		   Toast.makeText(ExecutorsActivity.this, "PROBLEMAS com a internet", Toast.LENGTH_LONG).show();   
        	   }
           }
          });
         }
          
          private void ForDaLista(ArrayList<Object> nObjetoLista, String nTipo){
        	  try{
        		  if (nTipo.equals("opcao") == true) {
        			  this.del(sql_create.TABLE_OPCAO);
        		  }
        	
        		  if (nTipo.equals("Pergunta") == true) {
        			  this.del(sql_create.TABLE_PERGUNTA);
        		  }
        		  if (nTipo.equals("Aluno") == true) {
        			  this.del(sql_create.TABLE_ALUNO);
        		  }
        	
        		  if (nTipo.equals("Turma") == true) {
        			  this.del(sql_create.TABLE_TURMA);
        		  }
        		  
           		  if (nTipo.equals("Escola") == true) {
        			  this.del(sql_create.TABLE_ESCOLA);
        		  }
        	
        		  for (int i=0; i < nObjetoLista.size(); i++){
        			  if ((nObjetoLista.get(i).toString() != null)) {
        				  if (nTipo.equals("opcao") == true) {
        					  this.saveOPCAO((opcao)nObjetoLista.get(i));
        				  }
        				  else if (nTipo.equals("Pergunta") == true) {
        					  this.savePERGUNTA((Pergunta)nObjetoLista.get(i));
        				  }
         				  else if (nTipo.equals("Aluno") == true) {
        					  this.saveALUNO((Aluno)nObjetoLista.get(i));
        				  }
         				  else if (nTipo.equals("Turma") == true) {
        					  this.saveTurma((Turma)nObjetoLista.get(i));
        				  }
         				  else if (nTipo.equals("Escola") == true) {
         					 this.saveEscola((Escola)nObjetoLista.get(i));
         				 }
        			  }
        		  }
         	 }
         	 catch (Throwable ex){
         		  
         	 }
          
          }
          
          private void del(String nTabela)                           
          {  
        	  try
        	  {                                 
                this.onDEL( context, nTabela);     
        	  }
        	  catch (Throwable ex){
        	  }
          }       
          
          private void saveOPCAO(opcao nOpcao)                           
          {                                   
                                 
            	ContentValues obj = new ContentValues(); 
        		obj.put("ID_OPCAO",nOpcao.getID_OPCAO());
        		obj.put("MAXIMO", nOpcao.getMAXIMO());
        		obj.put("VALOR",nOpcao.getVALOR());
        		obj.put("OPCAO",nOpcao.getOPCAO());
			    obj.put("PERSONALIZACAO",nOpcao.getPERSONALIZACAO());
        		obj.put("MINIMO",nOpcao.getMINIMO());
        		obj.put("ID_PERGUNTA",nOpcao.getID_PERGUNTA());
        		obj.put("SALTO",nOpcao.getSALTO());
        		obj.put("ORDENA",nOpcao.getORDENA());     
                this.onInsert( context, obj , sql_create.TABLE_OPCAO);              
                                       
          }  
          
          private void savePERGUNTA(Pergunta nPergunta)                           
          {                                   
                                 
            	ContentValues obj = new ContentValues(); 
        		obj.put("ID_PERGUNTA",nPergunta.getID_PERGUNTAS());
        		obj.put("DESCRICAO", nPergunta.getDESCRICAO());
        		obj.put("ID_BLOCO",nPergunta.getID_BLOCO());
			  	obj.put("NUM_SUBFORMULARIO",nPergunta.getNUM_SUBFORMULARIO());
                this.onInsert( context, obj , sql_create.TABLE_PERGUNTA);              
                                       
          }     
          
          
          private void saveALUNO(Aluno nAluno)                           
          {                                   
                                 
            	ContentValues obj = new ContentValues(); 
        		obj.put("ID_ALUNO",nAluno.getID_ALUNO());
        		obj.put("ID_ESCOLA",nAluno.getID_ESCOLA());
        		obj.put("ID_TURMA",nAluno.getID_TURMA());
        		
				//String nomelimpo = nAluno.getNOME().substring(0,nAluno.getNOME().indexOf("="));
        		String nomelimpo = nAluno.getNOME().substring(nAluno.getNOME().indexOf("=")+1,nAluno.getNOME().length());
        		
        		obj.put("NOME",  nomelimpo);
        		obj.put("DT_NASC",nAluno.getDT_NASC());
        		obj.put("SEXO",nAluno.getSEXO());

                this.onInsert( context, obj , sql_create.TABLE_ALUNO);              
                                       
          }   
          
          private void saveTurma(Turma nTurma)                           
          {                                   
                                 
            	ContentValues obj = new ContentValues(); 
        		obj.put("ID_TURMA",nTurma.getID_TURMA());
        		obj.put("ID_ESCOLA",nTurma.getID_ESCOLA());
        		obj.put("NOME_TURMA",nTurma.getNOME_TURMA());
        		obj.put("TURNO",nTurma.getTURNO());
                this.onInsert( context, obj , sql_create.TABLE_TURMA);              
                                       
          }
          
          private void saveEscola(Escola nEscola)                           
          {                                   
                                 
            	ContentValues obj = new ContentValues(); 
        		obj.put("ID_ESCOLA",nEscola.getID_ESCOLA());
        		obj.put("COD_UF",nEscola.getCOD_UF());
        		obj.put("COD_MUNIC",nEscola.getCOD_MUNIC());
        		obj.put("COD_BAIRRO",nEscola.getCOD_BAIRRO());
        		obj.put("NOME_ESCOLA",nEscola.getNOME_ESCOLA());
        		obj.put("ENDERECO",nEscola.getENDERECO());
        		obj.put("NUMERO",nEscola.getNUMERO());
        		obj.put("REGIAO",nEscola.getREGIAO());
        		obj.put("TIPO",nEscola.getTIPO());
        		obj.put("SUBTIPO",nEscola.getSUBTIPO());
        		this.onInsert( context, obj , sql_create.TABLE_ESCOLA);              
                                       
          } 
            
          private void onInsert(Context context, ContentValues obj, String nTabela) { 
        	  try{
        		  DB.getInstance( context ).insert( nTabela, obj );  
        	  }
        	  catch (Throwable ex){
        		  
        	  }
        	  
          }  
          
          private void onDEL(Context context, String nTabela) { 
        	 try{
        		 DB.getInstance( context ).delete( nTabela, null );  
        	 }
        	 catch (Throwable ex){
        		  
        	 }
          } 
          
          private String ChamaThead() throws IOException, XmlPullParserException{
        	  
        	  nOK = true;
         	   SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
         	   request.addProperty("endIP","");
         	   request.addProperty("nmPesquisa",filtro_desc_pesquisa);
         	   
         	   SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
         	   envelope.dotNet = true;
         	   
         	   envelope.setOutputSoapObject(request);
         	   
         	   HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
         	   httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
         	   try
         	   {
         		   httpTransport.call(SOAP_ACTION, envelope); // ERRO AQUI 
           		   SoapObject resultString =  (SoapObject)envelope.getResponse();
           		   return resultString.getProperty(1).toString();
               	   }
              catch (Exception exception)
              {
            	  nOK = false;
           	   return "Problemas com a internet!";
           	   
              }
            } 

        }
     

 private void pesquisaSelecionada(){
	 Cursor cursor2 = bd.rawQuery(sql_select.GET_CONFIGURACAO,null);
	 cursor2.moveToFirst();
	try{
		TextView nTextView = (TextView) findViewById(R.id.textViewselecionado);
		if (cursor2.getCount() > 0) {
			nTextView.setText("Pesquisa ativa é a: " + cursor2.getString(2).toString());
		}else{
			nTextView.setText("Nenhuma pesquisa ativa!");
		}
		cursor2.close();
	}
	finally{
		
		
	}
 }
 
 
 private void apagaTodosRegistrosRespsotaAluno(){
	 
	    LayoutInflater factory = LayoutInflater.from(this);
	    final View deleteDialogView = factory.inflate(
	            R.layout.custom_dialog, null);
	    final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
	    deleteDialog.setView(deleteDialogView);
	    
	    TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
	    nTextView.setText("ATENÇÃO! Existe dados de pesquisados neste dispositivo. Tem certeza que deseja atualizar outra pesquisa?");
	    
	    deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new OnClickListener() {

	    	@Override
	        public void onClick(View v) {
	    		//your business logic 
	    		
				del(sql_create.TABLE_CONFIGURACAO);
				SaveConfiguracao();
				pesquisaSelecionada();
				ativaThread();
				Verificaatualizacao();
	    		deleteDialog.dismiss();
	        }
	    });
	    deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {

	            deleteDialog.dismiss();

	        }
	    });

	    deleteDialog.show();

		
 	}
 
	private void del(String nTabela)                           
	{                                   
		this.onDEL( context, nTabela);              
                    
	}  

	private void onDEL(Context context, String nTabela) { 

		DB.getInstance( context ).delete( nTabela, null );      
	} 
 
	private void SaveConfiguracao(){
		
		//Spinner combo1 = (Spinner) findViewById(R.id.spinner1);
		
		
		
		//Cursor cursor1 = bd.rawQuery(sql_select.GET_PESQUISA_nome,new String[] {(combo1.getSelectedItem().toString())});

		//cursor1.moveToFirst();

		
		//modulo.filtro_id_cliente = cursor1.getString(0);
		//modulo.filtro_id_pesquisa = cursor1.getString(1);
		//modulo.filtro_desc_pesquisa = cursor1.getString(2);
		//modulo.filtro_automatico = cursor1.getString(3);
		
		
		ContentValues obj = new ContentValues(); 
		obj.put("ID_CLIENTE",filtro_id_cliente);
		obj.put("ID_PESQUISA", filtro_id_pesquisa);
		obj.put("DSC_PESQUISA",filtro_desc_pesquisa);
		obj.put("AUTOMATICO",filtro_automatico);
		obj.put("PREVISAO",filtro_previsao);
		
		this.onInsert( context, obj , sql_create.TABLE_CONFIGURACAO);  
	}
	private void onInsert(Context context, ContentValues obj, String nTabela) { 

		DB.getInstance( context ).insert( nTabela, obj );      
	} 
   
	public static Thread performOnBackgroundThread(final Runnable runnable) {
	    final Thread t = new Thread() {
	        @Override
	        public void run() {
	            try {
	                runnable.run();
	            } finally {

	            }
	        }
	    };
	    t.start();
	    return t;
	}

	private void doMyOperations() {

		
		
		if (filtro_automatico.equals("2")){
			NomeAplicacaoNova = filtro_desc_pesquisa;
			ftpDownload("ProjetoERICA", "49P3ric439", NomeAplicacaoNova + ".apk", MyConstant.storage + NomeAplicacaoNova +".apk");
		}else
		{
			NomeAplicacaoNova = "ResearchMobile";
			ftpDownload("ProjetoERICA", "49P3ric439", "ResearchMobile" + ".apk", MyConstant.storage + "ResearchMobile" +".apk");
		}
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + MyConstant.storage +"/"+ NomeAplicacaoNova + ".apk"), "application/vnd.android.package-archive");
		intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		
		salvarpInformacaoArquivo(filtro_automatico, filtro_desc_pesquisa);
		try{
		bkp(MyConstant.caminhobanco,MyConstant.storage + "TEMP_DB" +".db");
		}catch (IOException e)
		{
			
		}
		
	    this.runOnUiThread(new Runnable() {
	        public void run() {
	             progressDialog.dismiss();                  
	        }
	    });
	}	
	
	 public boolean ftpDownload(String login, String senha,String srcFilePath, String desFilePath)  
	    {  
	        FTPClient ftp = new FTPClient();
	    	boolean status = false; 
	    	try
	    	{
	    		ftp.connect(MyConstant.ip_servidor, 21);
	    		ftp.login(login, senha);
	    		try {  
	    			FileOutputStream desFileStream = new FileOutputStream(desFilePath);;  
	    			//Tipo de arquivo  
	    			ftp.setFileType(FTP.BINARY_FILE_TYPE);  
	    
	    			ftp.enterLocalPassiveMode();  
	    		
	    			//Faz o download do arquivo  
	    			status = ftp.retrieveFile(srcFilePath, desFileStream);  
	    			
	    		
	    			//Fecho o output  
	    			desFileStream.close();  
	    			 ftp.logout();
	    	            ftp.disconnect();
	    	            
	    	//            Intent intent = new Intent(Intent.ACTION_VIEW);
	    	//            intent.setDataAndType(Uri.parse("file://" + modulo.storage +"/" + modulo.NomeAplicacaoNova + ".apk"), "application/vnd.android.package-archive");
	    	//			intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
	    				//startActivity(intent);

	    			return status; 
	    	
	    		 
	    		} catch (Exception e) {  
	    			Log.e("Log", "download falhou");  
	    		
	    		}  
	    	} catch (SocketException e) {
	    		e.printStackTrace();
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    
	    	return status;  
	    
	    }
	 
	 
	 
	 
	  public void salvarpInformacaoArquivo(String NumeroPes, String PesquisaPes)
	   {
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
          
		  properties.setProperty("conf.numero", NumeroPes);
		  properties.setProperty("conf.pesquisa", PesquisaPes);
	      try 
	      {
	      
	    	  FileOutputStream fos = new FileOutputStream(MyConstant.nomeArquivoINI);
	    	  properties.store(fos, "CONFIGURACAO NUM:");
	    	  properties.store(fos, "CONFIGURACAO PES:");
	    	  fos.close();
	    	  //Toast.makeText(this, "Dados Salvos com sucesso!!", Toast.LENGTH_SHORT).show();
	      } 
	      catch (IOException ex) 
	      {
	    	  ex.printStackTrace();
	      }
	 }
	  
	  public String PegarNumeroPesquisa() 
	  {
  
		  
	      Properties properties = new Properties();
	      String num = null;
	      try 
	      {
	    	  FileInputStream fis = new  FileInputStream(MyConstant.nomeArquivoINI);
	    	  properties.load(fis);
	    	  

	    	  if (properties.containsKey("conf.numero")){
	    		 num = properties.getProperty("conf.numero");
	    	  }else{
	    		  num = "";
	    	  }
	    		  
	          
	    	  return num;
			
	     } 
	      catch (IOException e) 
	      {
	          e.printStackTrace();
	      }
		return num;
	         
	  }
	  
	  public String PegarDescricaoPesquisa() 
	  {
  
		  
	      Properties properties = new Properties();
	      String des = null;
	      try 
	      {
	    	  FileInputStream fis = new  FileInputStream(MyConstant.nomeArquivoINI);
	    	  properties.load(fis);
	    	  

	    	  if (properties.containsKey("conf.pesquisa")){
	    		  des = properties.getProperty("conf.pesquisa");
	    	  }else{
	    		  des = "";
	    	  }
	    	  

	    	  return des;
			
	     } 
	      catch (IOException e) 
	      {
	          e.printStackTrace();
	      }
		return des;
		

	         
	      }
	  
	  
	  private void bkp(String Origem, String Destino) throws IOException{
		    final String inFileName = Origem;

		    File dbFile = new File(inFileName);
		    FileInputStream fis = new FileInputStream(dbFile);
		    
		    

		    //String outFileName = Environment.getExternalStoragePublicDirectory()+"/database_copy.db";
		    String outFileName = Destino;
		    
		    // Open the empty db as the output stream
		    OutputStream output = new FileOutputStream(outFileName);

		    // Transfer bytes from the inputfile to the outputfile
		    byte[] buffer = new byte[1024];
		    int length;
		    while ((length = fis.read(buffer))>0){
		        output.write(buffer, 0, length);
		    }

		    // Close the streams
		    output.flush();
		    output.close();
		    fis.close();
		}
	  
	  
	  
	  private void Verificaatualizacao() {
		  String tempAutomatico = filtro_automatico.toString();
		  String tempDescricao = filtro_desc_pesquisa.toString();
		  boolean fazAtauliazacao = false;
		
		  if ( (tempAutomatico.equals("2")) && (!PegarNumeroPesquisa().equals("2")) ){
			  fazAtauliazacao = true;
		  }else if  ( (tempAutomatico.equals("1") || tempAutomatico.equals("0") ) && (PegarNumeroPesquisa().equals("2")) ){
			  fazAtauliazacao = true;
		  }else if ((!tempDescricao.equals(PegarDescricaoPesquisa()) && (tempAutomatico.equals("2")) && (PegarNumeroPesquisa().equals("2"))) )  {
			  fazAtauliazacao = true;
		  }
		

		
		  if (PegarNumeroPesquisa().equals("") && (!tempAutomatico.equals("2")) ){
			  fazAtauliazacao = false;
		  }
		

		
		  // se for um especial tem que atualizar um executavel
		  if (fazAtauliazacao){
			
			  //	salvarpInformacaoArquivo(modulo.filtro_automatico, modulo.filtro_desc_pesquisa);
			  //	bkp(modulo.caminhobanco,modulo.storage + "TEMP_DB" +".db");
			
			  final Runnable r = new Runnable()
			  {
			        public void run()
			        {
			            doMyOperations();
			            
			        }
			    };
			progressDialog = ProgressDialog.show(ExecutorsActivity.this, "Pesquisa","Aguarde Atualizando a versão dessa pesquisa");
			performOnBackgroundThread(r);
			
		}
		  
	  }

	  public void salvarGPS() 
	   {
		  CheckBox nCheckBox = (CheckBox) findViewById(R.id.checkBoxGPS);
		  

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
	     
        String nBoolean = "false";
        if (nCheckBox.isChecked()) {
        	nBoolean = "true";
			Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
			intent.putExtra("enabled", true);
			sendBroadcast(intent);
        }else
        {
			Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
			intent.putExtra("enabled", false);
			sendBroadcast(intent);
        }
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);  
	    startActivityForResult(intent, 1); 
        
		  properties.setProperty("conf.GPS", nBoolean);
	      try 
	      {
	      
	    	  FileOutputStream fos = new FileOutputStream(MyConstant.nomeArquivoINI);
	    	  properties.store(fos, "CONFIGURACAO GPS:");
	    	  fos.close();
	    	  nGPS = nCheckBox.isChecked();
	    	  //Toast.makeText(this, "Dados Salvos com sucesso!!", Toast.LENGTH_SHORT).show();
	      } 
	      catch (IOException ex) 
	      {
	    	  ex.printStackTrace();
	      }
	 }
	  
	  public void carregarGPS() 
	  {

		  
		  CheckBox nCheckBox = (CheckBox) findViewById(R.id.checkBoxGPS);
	      Properties properties = new Properties();
	      try 
	      {
	    	  FileInputStream fis;
	    	  fis = new  FileInputStream(MyConstant.nomeArquivoINI);
	    	  properties.load(fis);
	    	  String nIP = properties.getProperty("conf.GPS");
	          
	    	  if (nIP != null){
	    		  if (nIP.equals("true")){
	    			  nCheckBox.setChecked(true); 
	    			  nGPS = true;
	    		  }else{
	    			  nCheckBox.setChecked(false); 
	    			  nGPS = false;
	    		  }
	    	  }else
	    	  {
	    		  nGPS = false;
	    	  }


	     } 
	      catch (IOException e) 
	      {
	          e.printStackTrace();
	      }

	         
	      }
	  
	  
	  public void salvarVOZ() 
	   {
		  CheckBox nCheckBox = (CheckBox) findViewById(R.id.checkBoxvoz);
		  

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
	     
       String nBoolean = "false";
       if (nCheckBox.isChecked()) {
       	nBoolean = "true";
       }
       
		  properties.setProperty("conf.VOZ", nBoolean);
	      try 
	      {
	      
	    	  FileOutputStream fos = new FileOutputStream(MyConstant.nomeArquivoINI);
	    	  properties.store(fos, "CONFIGURACAO VOZ:");
	    	  fos.close();
	    	  nVOZ = nCheckBox.isChecked();
	    	  //Toast.makeText(this, "Dados Salvos com sucesso!!", Toast.LENGTH_SHORT).show();
	      } 
	      catch (IOException ex) 
	      {
	    	  ex.printStackTrace();
	      }
	 }
	  
	  public void carregarVOZ() 
	  {

		  
		  CheckBox nCheckBox = (CheckBox) findViewById(R.id.checkBoxvoz);
	      Properties properties = new Properties();
	      try 
	      {
	    	  FileInputStream fis;
	    	  fis = new  FileInputStream(MyConstant.nomeArquivoINI);
	    	  properties.load(fis);
	    	  String nIP = properties.getProperty("conf.VOZ");
	          
	    	  if (nIP != null){
	    		  if (nIP.equals("true")){
	    			  nCheckBox.setChecked(true); 
	    			  nVOZ = true;
	    		  }else{
	    			  nCheckBox.setChecked(false); 
	    			  nVOZ = false;
	    		  }
	    	  }else
	    	  {
	    		  nVOZ = false;
	    	  }


	     } 
	      catch (IOException e) 
	      {
	          e.printStackTrace();
	      }

	         
	      }
	  
	  
	  
	  
	  public void salvarFONTE() 
	   {
		  RadioButton nPequena = (RadioButton) findViewById(R.id.rPequena);
		  RadioButton nMedia = (RadioButton) findViewById(R.id.rMedia);
		  RadioButton nGrande = (RadioButton) findViewById(R.id.rGrande);
		  

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
	     
	  	if (nPequena.isChecked()){
	  		nFONTE = "P";
	  	}
	  	else if(nMedia.isChecked()){
	  		nFONTE = "M";
	  	}
	  	else if(nGrande.isChecked()){
	  		nFONTE = "G";
	  	}
      
		  properties.setProperty("conf.FONTE", nFONTE);
	      try 
	      {
	      
	    	  FileOutputStream fos = new FileOutputStream(MyConstant.nomeArquivoINI);
	    	  properties.store(fos, "CONFIGURACAO FONTE:");
	    	  fos.close();
	    	  if (nPequena.isChecked()){
	    		  nFONTE = "P";
	    	  }
	    	   else if(nMedia.isChecked()){
	    		  nFONTE = "M";
	    	  }
	    	   else if(nGrande.isChecked()){
	    		  nFONTE = "G";
	    	  }
	    	  //Toast.makeText(this, "Dados Salvos com sucesso!!", Toast.LENGTH_SHORT).show();
	      } 
	      catch (IOException ex) 
	      {
	    	  ex.printStackTrace();
	      }
	 }
	  
	  public void carregarFONTE() 
	  {

		  
		  RadioButton nPequena = (RadioButton) findViewById(R.id.rPequena);
		  RadioButton nMedia = (RadioButton) findViewById(R.id.rMedia);
		  RadioButton nGrande = (RadioButton) findViewById(R.id.rGrande);
		  

		  
	      Properties properties = new Properties();
	      try 
	      {
	    	  FileInputStream fis;
	    	  fis = new  FileInputStream(MyConstant.nomeArquivoINI);
	    	  properties.load(fis);
	    	  String nIP = properties.getProperty("conf.FONTE");
	          
	    	  if (nIP != null){
	        	  if (nFONTE.equals("P")){
	        		  nPequena.setChecked(true);
	        	  }
	        	   else if (nFONTE.equals("M")){
	        		   nMedia.setChecked(true);
	        	  }
	        	   else if (nFONTE.equals("G")){
	        		   nGrande.setChecked(true);
	        	  }
	    	  }else
	    	  {
	    		//  modulo.nFONTE = false; 
	    	  }


	     } 
	      catch (IOException e) 
	      {
	          e.printStackTrace();
	      }

	         
	      }


	  public String carregarsuario_login() 
      {
      	Properties properties = new Properties();
      	try 
      	{
      		FileInputStream fis;
      		fis = new  FileInputStream(MyConstant.nomeArquivoINI);
      		properties.load(fis);
      		String nusuariologin = properties.getProperty("conf.usuario_login");
	          
      		return nusuariologin;
      	} 
      	catch (IOException e) 
      	{
      		e.printStackTrace();
      	}
			return null;

	         
      }
	  
	  
	  
	  public void salvarTime() 
	   {
		  CheckBox nCheckBox = (CheckBox) findViewById(R.id.checkBoxTime);
		  

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
	     
      String nBoolean = "false";
      if (nCheckBox.isChecked()) {
      	nBoolean = "true";
      }
      
		  properties.setProperty("conf.TIME", nBoolean);
	      try 
	      {
	      
	    	  FileOutputStream fos = new FileOutputStream(MyConstant.nomeArquivoINI);
	    	  properties.store(fos, "CONFIGURACAO Time:");
	    	  fos.close();
	    	  nTIME = nCheckBox.isChecked();
	    	  //Toast.makeText(this, "Dados Salvos com sucesso!!", Toast.LENGTH_SHORT).show();
	      } 
	      catch (IOException ex) 
	      {
	    	  ex.printStackTrace();
	      }
	 }
	  
	  public void carregarTIME() 
	  {

		  
		  CheckBox nCheckBox = (CheckBox) findViewById(R.id.checkBoxTime);
	      Properties properties = new Properties();
	      try 
	      {
	    	  FileInputStream fis;
	    	  fis = new  FileInputStream(MyConstant.nomeArquivoINI);
	    	  properties.load(fis);
	    	  String nIP = properties.getProperty("conf.TIME");
	          
	    	  if (nIP != null){
	    		  if (nIP.equals("true")){
	    			  nCheckBox.setChecked(true); 
	    			  nTIME = true;
	    		  }else{
	    			  nCheckBox.setChecked(false); 
	    			  nTIME = false;
	    		  }
	    	  }else
	    	  {
	    		  nTIME = false;
	    	  }


	     } 
	      catch (IOException e) 
	      {
	          e.printStackTrace();
	      }

	         
	      }
	  
	  public void salvarAutomatico() 
	   {
		  CheckBox nCheckBox = (CheckBox) findViewById(R.id.checkBoxAutomatico);
		  

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
	     
     String nBoolean = "false";
     if (nCheckBox.isChecked()) {
     	nBoolean = "true";
     }
     
		  properties.setProperty("conf.Automatico", nBoolean);
	      try 
	      {
	      
	    	  FileOutputStream fos = new FileOutputStream(MyConstant.nomeArquivoINI);
	    	  properties.store(fos, "CONFIGURACAO Automatico:");
	    	  fos.close();
	    	  nAutomatico = nCheckBox.isChecked();
	    	  //Toast.makeText(this, "Dados Salvos com sucesso!!", Toast.LENGTH_SHORT).show();
	      } 
	      catch (IOException ex) 
	      {
	    	  ex.printStackTrace();
	      }
	 }
	  
	  public void carregarAutomatico() 
	  {

		  
		  CheckBox nCheckBox = (CheckBox) findViewById(R.id.checkBoxAutomatico);
	      Properties properties = new Properties();
	      try 
	      {
	    	  FileInputStream fis;
	    	  fis = new  FileInputStream(MyConstant.nomeArquivoINI);
	    	  properties.load(fis);
	    	  String nIP = properties.getProperty("conf.Automatico");
	          
	    	  if (nIP != null){
	    		  if (nIP.equals("true")){
	    			  nCheckBox.setChecked(true); 
	    			  nAutomatico = true;
	    		  }else{
	    			  nCheckBox.setChecked(false); 
	    			  nAutomatico = false;
	    		  }
	    	  }else
	    	  {
	    		  nAutomatico = false;
	    	  }


	     } 
	      catch (IOException e) 
	      {
	          e.printStackTrace();
	      }

	         
	      }	  



 
 }
  
    
     


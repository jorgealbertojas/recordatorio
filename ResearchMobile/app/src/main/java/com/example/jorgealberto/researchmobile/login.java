package com.example.jorgealberto.researchmobile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jorgealberto.researchmobile.commom.modulo;
import com.example.jorgealberto.researchmobile.library.MyConstant;
import com.example.jorgealberto.researchmobile.model.Ksoap2ResultParser;
import com.example.jorgealberto.researchmobile.model.Pesquisa;
import com.example.jorgealberto.researchmobile.model.Usuario;
import com.example.jorgealberto.researchmobile.model.pesquisa_usuario;
import com.example.jorgealberto.researchmobile.service.DB;
import com.example.jorgealberto.researchmobile.service.DataBase;
import com.example.jorgealberto.researchmobile.SQL.sql_create;
import com.example.jorgealberto.researchmobile.SQL.sql_select;

public class login  extends Activity {

	private boolean nGPS = false;
	private boolean nVOZ = false;
	private String usuario = "";
	private String Nomeusuario = "";
	private String storage = "";
	private String ip_servidor = "";
	private String usuario_login = "";
	private String cliente_login = "";

	private Context context; 
  	private SQLiteDatabase bd;
  	//private Context context;
	private DataBase nDataBase;
	private String RetornoCleinte;
	
	public final String SOAP_ACTION = "http://ericapesquisa.org/RetornaPesquisaPorCliente";
	public  final String OPERATION_NAME = "RetornaPesquisaPorCliente"; 
	public  final String WSDL_TARGET_NAMESPACE = "http://ericapesquisa.org/";
	public  final String SOAP_ADDRESS = "http://" + MyConstant.ip_servidor +"/WebServiceSubFormulario/ApoioErica.asmx";

	MyControlThread mThread;
	ProgressDialog mDialog;
	Handler mHandler;
	int LIMITE = 10;
	

	 

	private boolean gps_enabled = false;

	private boolean network_enabled = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.login);

			modulo.Liberado = false;
		 	carregar();
		 	carregarIP();
		 	salvarIP();
		 	carregarGPS();
		 	carregarVOZ();
		 	carregarsuario_login();
		 	carregarcliente_login();
		 
	        nDataBase = new DataBase(this);
			bd = nDataBase.getReadableDatabase();
			nDataBase.onCreate(bd);
			
			DB.getInstance(this);
			
			
			// ACESSA O ANTIGO QUE VOCE ACABOU DE ATAULIZAR NA APLICA��O

			Properties properties = new Properties();
			FileInputStream fis;
			try {
				fis = new  FileInputStream(MyConstant.nomeArquivoINI);
				properties.load(fis);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String Diretorio = properties.getProperty("conf.Diretorio");
	 

			//if (!((Diretorio.equals("")))){
				File file = new File(Diretorio+ "TEMP_DB"+".db");
				if(file.exists()) {
					try {
						restaura_bkp(Diretorio+ "TEMP_DB" +".db",MyConstant.caminhobanco);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					final String inFileName = Diretorio+ "TEMP_DB" +".db";
					File dbFile = new File(inFileName);
					dbFile.delete();
				}
			//}
			// FIM
			
			
			// Tem que acertar
			Cursor cursorPESQUISA;
			cursorPESQUISA = bd.rawQuery(sql_select.GET_PESQUISA,null);
			cursorPESQUISA.moveToFirst();
			
			if (cursorPESQUISA.getCount() > 0) {
				trocaLogin(true);
			}
			else	
			{
				trocaLogin(false);
			}
			cursorPESQUISA.close();
		
			ImageView nimageView1 = (ImageView) findViewById(R.id.imageView1);
			nimageView1.setOnLongClickListener(new View.OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					
					apagaTodosRegistrosRespsotaAluno();
					return false;
				}
	        });
			
			nimageView1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					EditText EditTextBKP = (EditText) findViewById(R.id.EditTextBKP);
					EditText EditTextIP = (EditText) findViewById(R.id.editTextIP);
					EditTextBKP.setVisibility(View.VISIBLE);
					EditTextIP.setVisibility(View.VISIBLE);
			
				}
	        });

		TextView btn_create = (TextView) findViewById(R.id.imageButton1);
			btn_create.setOnClickListener(new View.OnClickListener() {
	        	@Override
	        	public void onClick(View arg0) {
	        		
	    			Cursor cursorPESQUISA;
	    			cursorPESQUISA = bd.rawQuery(sql_select.GET_PESQUISA,null);
	    			cursorPESQUISA.moveToFirst();
	    			
	    			if (cursorPESQUISA.getCount() > 0) {
	    				trocaLogin(true);
	    				// GPS Ligado
	    				if (nGPS){
	    					//Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
	    					//intent.putExtra("enabled", true);
	    					//sendBroadcast(intent);
	    					
	    					Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);  
	    		    	    startActivityForResult(intent, 1); 
	    					
	    					//turnGPSOn();
	    					//canToggleGPS();
	    					
	    				}
	    			}
	    			else	
	    			{
	    				Toast.makeText(login.this, "PROBLEMAS com a internet!", Toast.LENGTH_LONG).show();
	    				trocaLogin(false);
	    			}
	    			cursorPESQUISA.close();
	         		
	        		Entrar();
	        		salvar();
	        		salvarIP();
	        		salvar_USuario_login();
	        		salvar_cleinte_login();
	        		carregar();
	        	}
	        });

/*
		TextView volumemuteImageButton3 = (TextView) findViewById(R.id.imageButton1);
	        volumemuteImageButton3.setOnTouchListener(new View.OnTouchListener() 
	        { 
	        	public boolean onTouch(View v, MotionEvent event) { switch (event.getAction()) { 
	        	case MotionEvent.ACTION_DOWN: {  
	        		((ImageButton) v).setImageResource(R.mipmap.ok_02);
	        		v.invalidate(); 
	        		break;
	        	}
	        	case MotionEvent.ACTION_UP: 
	        	{ 
	        		((ImageButton) v).setImageResource(R.mipmap.ok_01);
	        		v.invalidate(); 
	        		break; 
	        	} 
	        	} 
	        		return false; 
	        	}
	        });*/


		TextView btn_create2 = (TextView) findViewById(R.id.imageButton2);
			btn_create2.setOnClickListener(new View.OnClickListener() {
	        	@Override
	        	public void onClick(View arg0) {
	        		
	        		
	        		ativaThread();
	        		trocaLogin(true);
	        	}
	        });

/*		TextView volumemuteImageButton4 = (TextView) findViewById(R.id.imageButton2);
	        volumemuteImageButton4.setOnTouchListener(new View.OnTouchListener()
	        {
	        	public boolean onTouch(View v, MotionEvent event) { switch (event.getAction()) {
	        	case MotionEvent.ACTION_DOWN: {
	        		((TextView) v).setImageResource(R.mipmap.find_02);
	        		v.invalidate();
	        		break;
	        	}
	        	case MotionEvent.ACTION_UP:
	        	{
	        		((TextView) v).setImageResource(R.mipmap.find_01);
	        		v.invalidate();
	        		break;
	        	}
	        	}
	        		return false;
	        	}
	        });*/

	}
	
	 private void ForDaLista(ArrayList<Object> nObjetoLista, String nTipo){
		 if (nTipo.equals("Pesquisa") == true) {
			 this.del(sql_create.TABLE_PESQUISA);
		 }
		 
		 if (nTipo.equals("Usuario") == true) {
			 this.del(sql_create.TABLE_USUARIOS);
		 }
		 
		 if (nTipo.equals("pesquisa_usuario") == true) {
			 this.del(sql_create.TABLE_PESQUISA_USUARIO);
		 }
     	
		 for (int i=0; i < nObjetoLista.size(); i++){
			 if ((nObjetoLista.get(i).toString() != null)) {
				 if (nTipo.equals("Pesquisa") == true) {
   	    			this.savePESQUISA((Pesquisa)nObjetoLista.get(i));
				 }
				 else if (nTipo.equals("Usuario") == true) {
	   	    			this.saveUSUARIO((Usuario)nObjetoLista.get(i));
				 }
				 else if (nTipo.equals("pesquisa_usuario") == true) {
	   	    			this.savePESQUISA_USUARIO((pesquisa_usuario)nObjetoLista.get(i));
				 }
			 }
		 }
       
	 }
	 
	 private void del(String nTabela)                           
     {                                   
           this.onDEL( context, nTabela);              
                                  
     }       
     
     private void savePESQUISA(Pesquisa nPesquisa)                           
     {                                   
                            
       	ContentValues obj = new ContentValues(); 
   		obj.put("ID_CLIENTE",nPesquisa.getID_CLIENTE());
   		obj.put("ID_PESQUISA", nPesquisa.getID_PESQUISA());
   		obj.put("DSC_PESQUISA",nPesquisa.getDSC_PESQUISA());
   		obj.put("AUTOMATICO",nPesquisa.getAUTOMATICO());
   		obj.put("PREVISAO",nPesquisa.getPREVISAO());
   
   		this.onInsert( context, obj , sql_create.TABLE_PESQUISA);              
                                  
     }  
     
     private void savePESQUISA_USUARIO(pesquisa_usuario nPesquisa_usuario)                           
     {                                   
                            
       	ContentValues obj = new ContentValues(); 
   		obj.put("ID",nPesquisa_usuario.getID());
   		obj.put("ID_PESQUISA", nPesquisa_usuario.getID_PESQUISA());
   		obj.put("ID_USUARIO",nPesquisa_usuario.getID_USUARIO());
   		obj.put("ID_CLIENTE",nPesquisa_usuario.getID_CLIENTE());
   		obj.put("DT_CADASTRO",nPesquisa_usuario.getDT_CADASTRO());
 
   		this.onInsert( context, obj , sql_create.TABLE_PESQUISA_USUARIO);              
                                  
     }  
     
     private void saveUSUARIO(Usuario nUsuario)                           
     {                                   
                            
       	ContentValues obj = new ContentValues(); 
   		obj.put("ID",nUsuario.getID());
   		obj.put("NM_USUARIO", nUsuario.getNM_USUARIO());
   		obj.put("SENHA",nUsuario.getSENHA());
   		obj.put("ID_GRUPO",nUsuario.getID_GRUPO());
   		obj.put("ID_CLIENTE",nUsuario.getID_CLIENTE());
   		obj.put("NOME",nUsuario.getNOME());
  
   		this.onInsert( context, obj , sql_create.TABLE_USUARIOS);              
                                  
     }  
   
     private void onInsert(Context context, ContentValues obj, String nTabela) { 
   	  
         DB.getInstance( context ).insert( nTabela, obj );      
     }  
     
     private void onDEL(Context context, String nTabela) { 
   	 
         DB.getInstance( context ).delete( nTabela, null );      
     } 
	
	  private void Entrar(){
		  Cursor cusrsorlogin;
		  
		  TextView txtNome = (TextView) findViewById(R.id.editEntrevistado);
		  TextView txtSenha = (TextView) findViewById(R.id.editsenhalogin);
		  
		  //cusrsorlogin = bd.rawQuery(sql_select.GET_USUARIO,new String[] {(txtNome.getText().toString().toUpperCase()), (txtSenha.getText().toString().toUpperCase())});
		  cusrsorlogin = bd.rawQuery(sql_select.GET_USUARIO,new String[] {(txtNome.getText().toString().toUpperCase()), (txtSenha.getText().toString())});
		  cusrsorlogin.moveToFirst();
		
		  if (cusrsorlogin.getCount() > 0) {
			  modulo.Liberado = true;
			  usuario = cusrsorlogin.getString(2).toString();
			  Nomeusuario = cusrsorlogin.getString(0).toString();

			  this.finish();
			  
		  } else
		  {
			  Toast.makeText(this, "Login ou senha não cadastrado!" , Toast.LENGTH_LONG).show();
		  }
	  }
	  
	  private void trocaLogin(boolean nBoolean){
		  
			EditText EditTextBKP = (EditText) findViewById(R.id.EditTextBKP);
			EditText EditTextIP = (EditText) findViewById(R.id.editTextIP);
			EditTextBKP.setVisibility(View.INVISIBLE);
			EditTextIP.setVisibility(View.INVISIBLE);
			
		  // Login
		  TextView ImagembuttonLogin = (TextView) findViewById(R.id.imageButton1);
			
			if (nBoolean) {
				ImagembuttonLogin.setVisibility(View.VISIBLE);
			}
			else{
				ImagembuttonLogin.setVisibility(View.INVISIBLE);
					
			}

			

			
			
			TextView TextView2 = (TextView) findViewById(R.id.textViewselecionado);
		
			if (nBoolean) {
				TextView2.setVisibility(View.VISIBLE);
			}
			else{
				TextView2.setVisibility(View.INVISIBLE);
					
			}
			
			
			EditText EditTextLogin = (EditText) findViewById(R.id.editEntrevistado);
			
			if (nBoolean) {
				EditTextLogin.setVisibility(View.VISIBLE);
			}
			else{
				EditTextLogin.setVisibility(View.INVISIBLE);
					
			}
			
			
			EditText EditTextsenhaLogin = (EditText) findViewById(R.id.editsenhalogin);
			
			if (nBoolean) {
				EditTextsenhaLogin.setVisibility(View.VISIBLE);
			}
			else{
				EditTextsenhaLogin.setVisibility(View.INVISIBLE);
					
			}

			
		  
		  
		  // cleinte
		  TextView ImagembuttonCliente = (TextView) findViewById(R.id.imageButton2);
			
			if (nBoolean) {
				ImagembuttonCliente.setVisibility(View.INVISIBLE);
			}
			else{
				ImagembuttonCliente.setVisibility(View.VISIBLE);
					
			}

			
			TextView TextView3 = (TextView) findViewById(R.id.textView3);
			
			if (nBoolean) {
				TextView3.setVisibility(View.INVISIBLE);
			}
			else{
				TextView3.setVisibility(View.VISIBLE);
					
			}
			
			
			
			TextView TextView4 = (TextView) findViewById(R.id.textView4);
		
			if (nBoolean) {
				TextView4.setVisibility(View.INVISIBLE);
			}
			else{
				TextView4.setVisibility(View.VISIBLE);
					
			}
			
			
			EditText EditTextCleinte = (EditText) findViewById(R.id.editcliente);
			
			if (nBoolean) {
				EditTextCleinte.setVisibility(View.INVISIBLE);
			}
			else{
				EditTextCleinte.setVisibility(View.VISIBLE);
					
			}
			
			
			EditText EditTextsenhaCleinte = (EditText) findViewById(R.id.editsenhacliente);
			
			if (nBoolean) {
				EditTextsenhaCleinte.setVisibility(View.INVISIBLE);
			}
			else{
				EditTextsenhaCleinte.setVisibility(View.VISIBLE);
					
			}
			
	  }
	  
	  
	  
	  
	  private String ChamaThead() throws IOException, XmlPullParserException{
    	  
		  EditText EditTextCleinte = (EditText) findViewById(R.id.editcliente);
		  
		  EditText EditTextsenhaCleinte = (EditText) findViewById(R.id.editsenhacliente);
		  
    	   SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
    	   request.addProperty("v_user_name",EditTextCleinte.getText().toString());
    	   request.addProperty("v_password",EditTextsenhaCleinte.getText().toString());
    	   
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
      	   return "Problemas com a internet!";
      	   
         }
       } 
	  
	    private void ativaThread() {
	    	mDialog = ProgressDialog.show(this, "Buscando dados", "Processando...", false, false);
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
	         
	         Runnable worker = new MythreadWeservice("Aguarde...", 22);
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
	  
	  
	  private class MythreadWeservice implements Runnable {
	    	 private int numLoops;
	        private String nome;
	        
	        public MythreadWeservice(String nome, int loops) {
	         this.nome = nome;
	         numLoops = loops;
	        }
	        
	         public void run() {
	         mHandler.post(new Runnable() {
	          @Override
	          public void run() {
	           mDialog.setMessage("Buscando pesquisas... " + nome);
	          }
	         });       
	      

	         try {

	             
	        		String nome;
	        		ArrayList<Object> ListaPesquisa;
	        		ArrayList<Object> ListaPesquisa_usuario;
	        		ArrayList<Object> ListaUsuario;
					
	        		nome = ChamaThead();
	        		
	    		
	        		// Pesquisa
	    			Pesquisa pesquisa = new Pesquisa();
	    			ListaPesquisa = new ArrayList<Object>();
	    			nome = Ksoap2ResultParser.MinhaparseBusinessObject(nome,((Object) pesquisa),((ArrayList<Object>) ListaPesquisa), "T_PESQUISA");
	    			ForDaLista(ListaPesquisa,pesquisa.getClass().getSimpleName());
				
		        		// Usuario
	    			Usuario usuario = new Usuario();
	    			ListaUsuario = new ArrayList<Object>();
	    			nome = Ksoap2ResultParser.MinhaparseBusinessObject(nome,((Object) usuario),((ArrayList<Object>) ListaUsuario), "T_USUARIO");
	    			ForDaLista(ListaUsuario,usuario.getClass().getSimpleName());
				
	        		// Pesquisa_USUARIO
	    			pesquisa_usuario Pesquisa_usuario = new pesquisa_usuario();
	    			ListaPesquisa_usuario = new ArrayList<Object>();
	    			nome = Ksoap2ResultParser.MinhaparseBusinessObject(nome,((Object) Pesquisa_usuario),((ArrayList<Object>) ListaPesquisa_usuario), "T_PESQUISA_USUARIO");
	    			ForDaLista(ListaPesquisa_usuario,Pesquisa_usuario.getClass().getSimpleName());
					
	        		if (nome == "Problemas com a internet!") {
	        		    Toast.makeText(login.this, "PROBLEMAS com a internet!", Toast.LENGTH_LONG).show();
	        	        
	        		}else{
	        		    Toast.makeText(login.this, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
	        	        
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
	           //Toast.makeText(login.this, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
	          }
	         });
	        }


   }
	  
	  private void apagaTodosRegistrosRespsotaAluno(){
			 
		    LayoutInflater factory = LayoutInflater.from(this);
		    final View deleteDialogView = factory.inflate(
		            R.layout.custom_dialog, null);
		    final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
		    deleteDialog.setView(deleteDialogView);
		    
		    TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
		    nTextView.setText("ATENÇÃO! Tem ceterteza que deseja apagar todas as pesquisas desse cliente?");
		    
		    deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new OnClickListener() {

		    	@Override
		        public void onClick(View v) {
		    		//your business logic 
					del(sql_create.TABLE_PESQUISA);
					del(sql_create.TABLE_USUARIOS);
					del(sql_create.TABLE_PESQUISA_USUARIO);
					del(sql_create.TABLE_CONFIGURACAO);
					trocaLogin(false);
					Toast.makeText(login.this, "Registros apagados com sucesso!" , Toast.LENGTH_LONG).show();
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
	  
	  public void salvar() 
	   {
		  EditText nEditText = (EditText) findViewById(R.id.EditTextBKP);
		  
		  if (nEditText.getText().toString().equals("")) {
			  String outFileName = Environment.getExternalStorageDirectory().toString()+"/";
			  nEditText.setText(outFileName);
		  }
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
	      
		  properties.setProperty("conf.Diretorio", nEditText.getText().toString());
	      try 
	      {
	      
	    	  FileOutputStream fos = new FileOutputStream(MyConstant.nomeArquivoINI);
	    	  properties.store(fos, "CONFIGURACAO BKP:");
	    	  fos.close();
	    	  storage = nEditText.getText().toString();
	    	  //Toast.makeText(this, "Dados Salvos com sucesso!!", Toast.LENGTH_SHORT).show();
	      } 
	      catch (IOException ex) 
	      {
	    	  ex.printStackTrace();
	      }
	 }
	  
	  public void carregar() 
	  {
  
		  
		  EditText nEditText = (EditText) findViewById(R.id.EditTextBKP);
	      Properties properties = new Properties();
	      try 
	      {
	    	  FileInputStream fis;
	    	  fis = new  FileInputStream(MyConstant.nomeArquivoINI);
	    	  properties.load(fis);
	    	  String Diretorio = properties.getProperty("conf.Diretorio");
	          
	    	  nEditText.setText(Diretorio);
	    	  
	    	  storage = Diretorio;
	    		
	    	  //modulo.AudioPesquisa = Diretorio;

	    	  //Toast.makeText(this, "Dados Carregados com sucesso!!", Toast.LENGTH_SHORT).show();

	     } 
	      catch (IOException e) 
	      {
	          e.printStackTrace();
	      }

	         
	      }
	  
	  private void restaura_bkp(String Origem, String Destino) throws IOException{
		    final String inFileName = Origem;

		    File dbFile = new File(inFileName);
		    FileInputStream fis = new FileInputStream(dbFile);

		    //String outFileName = Environment.getExternalStoragePublicDirectory()+"/database_copy.db";
		    String outFileName =  Destino; 
		    
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
	  
	  public void salvarIP() 
	   {
		  EditText nEditText = (EditText) findViewById(R.id.editTextIP);
		  
		  if (nEditText.getText().toString().equals("")) {
			  String outFileName = MyConstant.ip_servidor;
			  nEditText.setText(outFileName);
		  }
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
	      
		  properties.setProperty("conf.IP", nEditText.getText().toString());
	      try 
	      {
	      
	    	  FileOutputStream fos = new FileOutputStream(MyConstant.nomeArquivoINI);
	    	  properties.store(fos, "CONFIGURACAO IP:");
	    	  fos.close();
	    	  ip_servidor = nEditText.getText().toString();
	    	  //Toast.makeText(this, "Dados Salvos com sucesso!!", Toast.LENGTH_SHORT).show();
	      } 
	      catch (IOException ex) 
	      {
	    	  ex.printStackTrace();
	      }
	 }
	  
	  public void carregarIP() 
	  {
 
		  
		  EditText nEditText = (EditText) findViewById(R.id.editTextIP);
	      Properties properties = new Properties();
	      try 
	      {
	    	  FileInputStream fis;
	    	  fis = new  FileInputStream(MyConstant.nomeArquivoINI);
	    	  properties.load(fis);
	    	  String nIP = properties.getProperty("conf.IP");
	          
	    	  nEditText.setText(nIP);



	     } 
	      catch (IOException e) 
	      {
	          e.printStackTrace();
	      }

	         
	      }
	  
	  
	  public void carregarGPS() 
	  {

		  
	      Properties properties = new Properties();
	      try 
	      {
	    	  FileInputStream fis;
	    	  fis = new  FileInputStream(MyConstant.nomeArquivoINI);
	    	  properties.load(fis);
	    	  String nIP = properties.getProperty("conf.GPS");
	          
	    	  if (nIP != null){
	    		  if (nIP.equals("true")){
	    			  nGPS = true;
	    		  }else{
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
	  
	  public void carregarVOZ() 
	  {

		  
	      Properties properties = new Properties();
	      try 
	      {
	    	  FileInputStream fis;
	    	  fis = new  FileInputStream(MyConstant.nomeArquivoINI);
	    	  properties.load(fis);
	    	  String nIP = properties.getProperty("conf.VOZ");
	          
	    	  if (nIP != null){
	    		  if (nIP.equals("true")){
	    			  nVOZ = true;
	    		  }else{
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


	  private void turnGPSOn(){
		  
 		  String provider = Secure.getString(getContentResolver(), Secure.LOCATION_PROVIDERS_ALLOWED);
		  //if(provider.length() == 0)
          //{
              //ativa e desativa gps
           //   Intent intent = new Intent();
           //   intent.setClassName("com.android.settings", 
           //   "com.android.settings.widget.SettingsAppWidgetProvider");
           //   intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
           //   intent.setData(Uri.parse("3"));//3 - c�digo do gps
           //   sendBroadcast(intent);
          //}
		  
		 		//String provider = Secure.getString(getContentResolver(), Secure.LOCATION_PROVIDERS_ALLOWED);
		 // if(provider.contains("gps")){ //if gps is enabled
		 //     final Intent poke = new Intent();
		 //     poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
		 //     poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
		 //     poke.setData(Uri.parse("3")); 
		 //     this.sendBroadcast(poke);

		//}
    		 // if(provider.length() == 0)
             // {   
    		  Intent intentnew = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);   
    		  startActivityForResult(intentnew, 1); 
              //}
    		  //String provider = Secure.getString(getContentResolver(), Secure.LOCATION_PROVIDERS_ALLOWED);
    		  //if(provider.length() == 0)
              //{
	  }
	  
	  private boolean canToggleGPS() {
		    PackageManager pacman = getPackageManager();
		    PackageInfo pacInfo = null;

		    try {
		        pacInfo = pacman.getPackageInfo("com.android.settings", PackageManager.GET_RECEIVERS);
		    } catch (NameNotFoundException e) {
		        return false; //package not found
		    }

		    if(pacInfo != null){
		        for(ActivityInfo actInfo : pacInfo.receivers){
		            //test if recevier is exported. if so, we can toggle GPS.
		            if(actInfo.name.equals("com.android.settings.widget.SettingsAppWidgetProvider") && actInfo.exported){
		                return true;
		            }
		        }
		    }

		    return false; //default
		}
	  
	  
	  
	  public void salvar_USuario_login() 
	   {
		  EditText nEditText = (EditText) findViewById(R.id.editEntrevistado);
		  
/*		  if (!(nEditText.getText().toString().equals(""))) {
			  String outFileName = usuario_login;
			  nEditText.setText(outFileName);
		  }*/
		  
		  
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

		  properties.setProperty("conf.usuario_login", nEditText.getText().toString().toUpperCase());
	      try 
	      {
	      
	    	  FileOutputStream fos = new FileOutputStream(MyConstant.nomeArquivoINI);
	    	  properties.store(fos, "CONFIGURACAO usuario login:");
	    	  fos.close();
	    	  usuario_login = nEditText.getText().toString();
	    	  //Toast.makeText(this, "Dados Salvos com sucesso!!", Toast.LENGTH_SHORT).show();
	      } 
	      catch (IOException ex) 
	      {
	    	  ex.printStackTrace();
	      }
	 }
	  
	  public void carregarsuario_login() 
	  {

		  
		  EditText nEditText = (EditText) findViewById(R.id.editEntrevistado);
	      Properties properties = new Properties();
	      try 
	      {
	    	  FileInputStream fis;
	    	  fis = new  FileInputStream(MyConstant.nomeArquivoINI);
	    	  properties.load(fis);
	    	  String nusuariologin = properties.getProperty("conf.usuario_login");
	          
	    	  nEditText.setText(nusuariologin);



	     } 
	      catch (IOException e) 
	      {
	          e.printStackTrace();
	      }

	         
	      }
	  
	  
	  public void salvar_cleinte_login() 
	   {
		  EditText nEditText = (EditText) findViewById(R.id.editcliente);
		  
		  if (nEditText.getText().toString().equals("")) {
			  String outFileName = cliente_login;
			  nEditText.setText(outFileName);
		  }
		  
		  
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

		  properties.setProperty("conf.cliente_login", nEditText.getText().toString());
	      try
	      {

	    	  FileOutputStream fos = new FileOutputStream(MyConstant.nomeArquivoINI);
	    	  properties.store(fos, "CONFIGURACAO cliente login:");
	    	  fos.close();
	    	  cliente_login = nEditText.getText().toString();
	    	  //Toast.makeText(this, "Dados Salvos com sucesso!!", Toast.LENGTH_SHORT).show();
	      }
	      catch (IOException ex)
	      {
	    	  ex.printStackTrace();
	      }
	 }
	  
	  public void carregarcliente_login() 
	  {

		  
		  EditText nEditText = (EditText) findViewById(R.id.editcliente);
	      Properties properties = new Properties();
	      try 
	      {
	    	  FileInputStream fis;
	    	  fis = new  FileInputStream(MyConstant.nomeArquivoINI);
	    	  properties.load(fis);
	    	  String nclientelogin = properties.getProperty("conf.cliente_login");
	          
	    	  nEditText.setText(nclientelogin);



	     } 
	      catch (IOException e) 
	      {
	          e.printStackTrace();
	      }

	         
	      }

	  
 



}

	  



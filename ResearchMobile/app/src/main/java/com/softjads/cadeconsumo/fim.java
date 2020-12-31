package com.softjads.cadeconsumo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import com.softjads.cadeconsumo.util.MyConstant;
import com.softjads.cadeconsumo.model.Aluno;
import com.softjads.cadeconsumo.model.Controle_fim;
import com.softjads.cadeconsumo.model.Controle_inicio;
import com.softjads.cadeconsumo.model.Resposta;
import com.softjads.cadeconsumo.util.ClasseADO;
import com.softjads.cadeconsumo.util.FTPtransferencia;


import com.softjads.cadeconsumo.service.DB;
import com.softjads.cadeconsumo.service.DataBase;
import com.softjads.cadeconsumo.SQL.sql_create;
import com.softjads.cadeconsumo.SQL.sql_delete;
import com.softjads.cadeconsumo.SQL.sql_select;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;
import android.util.Xml;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class fim extends Activity{


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


  	private SQLiteDatabase bd;
  	private Context context;
	private DataBase nDataBase;
	
    private ProgressDialog dialog;
    private Handler handler = new Handler();
	public final String SOAP_ACTION = "http://ericapesquisa.org/AtualizaAlunoErica";
	public  final String OPERATION_NAME = "AtualizaAlunoErica"; 
	public  final String WSDL_TARGET_NAMESPACE = "http://ericapesquisa.org/";
	public  final String SOAP_ADDRESS = "http://"+ MyConstant.ip_servidor+"/Servicos_Erica/ApoioErica.asmx";
	MyControlThread mThread;
	ProgressDialog mDialog;
	Handler mHandler;
	int LIMITE = 10;
	Boolean nboolean = false;


	private XmlSerializer serializer = Xml.newSerializer();
	private StringWriter writer;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fim);
        
        nDataBase = new DataBase(this);
		bd = nDataBase.getReadableDatabase();
		nDataBase.onCreate(bd);




        /*if (modulo.nGPS){
        	GPSTracker gps;
    		gps = new GPSTracker(this);
    		// check if GPS enabled     
    		if(gps.canGetLocation()){
             
    			modulo.lat = Double.toString(gps.getLatitude());
    			modulo.log = Double.toString(gps.getLongitude());
    			insereControleFim();
    			
    			if (modulo.nAutomatico){
    				enviar();
    			}
             
    			// \n is for new line
    			//Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();    
    		}else{
    			// can't get location
    			// GPS or Network is not enabled
    			// Ask user to enable GPS/network in settings
    			gps.showSettingsAlert();
    		}
        }else{
			modulo.lat = "0";
			modulo.log = "0";
			insereControleFim();
			if (modulo.nAutomatico){
				enviar();
			}
        }*/
        
        //


		TextView btn_create = (TextView) findViewById(R.id.imageButton1);
		 
			btn_create.setOnClickListener(new View.OnClickListener() {
	        	@Override
	        	public void onClick(View arg0) {
	             fechar();
	        	}
	        });

    }
	
	
	//@Override
	protected void onStart() {
		super.onStart();
		//if (modulo.nTIME){
		//	Contar15segundos();
		//}
};
	
	
	public void fechar(){
		this.finish();
	}
	  public void insereControleFim(){
		  
		  bd.execSQL(sql_delete.DEL_TODOS_CONTROLE_FIM,new String[] {Integer.toString(AlunoAtual)});
      	
		  String DataCompleta;
		  String horaCompleta;
			
		  Time now = new Time();
		  now.setToNow();
			
		  DataCompleta = Integer.toString(now.monthDay);
		  DataCompleta = DataCompleta + "/"+Integer.toString(now.month+1);
		  DataCompleta = DataCompleta + "/"+Integer.toString(now.year);
			
		  horaCompleta = Integer.toString(now.hour);
		  horaCompleta = horaCompleta + ":"+Integer.toString(now.minute);
		  horaCompleta = horaCompleta + ":"+Integer.toString(now.second);
		  
		  if (usuario.equals("") || usuario.equals("0")){
			  Cursor cusrsorlogin = bd.rawQuery(sql_select.GET_USUARIO,new String[] {(carregarsuario_login())});
			  cusrsorlogin.moveToFirst();
			
			  if (cusrsorlogin.getCount() > 0) {
				  Liberado = true;
				  usuario = cusrsorlogin.getString(2).toString();
				  Nomeusuario = cusrsorlogin.getString(0).toString();
				  
			  }
		}
		
		if (filtro_desc_pesquisa.equals("") || filtro_desc_pesquisa.equals("0")){
	    	Cursor cursorConfiguracao;
	    	cursorConfiguracao = bd.rawQuery(sql_select.GET_CONFIGURACAO,null);
	   	
	    	cursorConfiguracao.moveToFirst();
	    	if (cursorConfiguracao.getCount() > 0) {
	    		filtro_desc_pesquisa = cursorConfiguracao.getString(2);
	    		filtro_previsao = cursorConfiguracao.getString(4);
	    		
	    	}
		}
			
		  ContentValues obj = new ContentValues(); 
		  obj.put("ID_ALUNO",AlunoAtual);
		  obj.put("ID_USUARIO", usuario);
		  obj.put("NM_USUARIO", Nomeusuario);
		  obj.put("DATA_FIM",DataCompleta.toString());
		  obj.put("FIM",horaCompleta.toString());
		  obj.put("LONGITUDE",log);
		  obj.put("LATITUDE",lat);
		  this.onInsert( context, obj , sql_create.TABLE_CONTROLE_FIM);    
      }
	  
      private void onInsert(Context context, ContentValues obj, String nTabela) { 
      	  
          DB.getInstance( context ).insert( nTabela, obj );      
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
      
      public void enviar(){
  		try {
			Cursor cursorPesquisa;
		    cursorPesquisa = bd.rawQuery(sql_select.GET_PESQUISA,null);
	    	cursorPesquisa.moveToFirst();
	    	if (cursorPesquisa.getCount() > 0) {
	    		ativaThread();
	    		new FTPtransferencia().execute("seuparametro");
	    	}else{
	    		//Toast.makeText(MainActivity.this, "N�o existe pesquisa para atualizar!", Toast.LENGTH_SHORT).show();
	    	}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
      }
      
      private class MyHandler extends Handler {
          

          @Override
          public void handleMessage(Message msg) {
          	//TextView nTextView = (TextView) findViewById(R.id.textViewselecionado);
                  switch (msg.what) {
                  case 0:
                  //	nTextView.setVisibility(View.INVISIBLE);

                      break;
                  case 1:
                  //	nTextView.setVisibility(View.VISIBLE);
                      break;
                  case 29:
                		fechar();
                	  break;

                  }

              super.handleMessage(msg);
          }
      }
      
      public void Contar15segundos(){
  		MyHandler blinker = new MyHandler();
  		//ImageView ImageViewbkpe = imgView;
  		//blinker.imgView = ImageViewbkpe;
  		for (int j = 0; j < 30; j++) {
  		    Message msg = new Message();
  		    if (j % 2 == 0) {
  		        msg.what = 0;
  		    } else {
  		        msg.what = 1;
  		    }

  		    if (j==29){
  		    	msg.what = 29;
  		    }
  		    blinker.sendMessageDelayed(msg, j * 300);
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
            mDialog.setMessage("Atualizando o servidor...");
           }
          });       
       

          try {

              
              
              nboolean = ChamaThead();
 			




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


 				
 				if (nboolean){
 					//Toast.makeText(fim.this, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
         		    bd.execSQL(sql_delete.DEL_RESPOSTA_TODOS);
         		    //bd.execSQL(sql_delete.DEL_BLOCO_TODOS);
         		    bd.execSQL(sql_delete.DEL_ALUNO_TODOS);
         		    bd.execSQL(sql_delete.DEL_CONTROLE_INICIO);
         		    bd.execSQL(sql_delete.DEL_CONTROLE_FIM);
         		    
 				}else{
 					//Toast.makeText(MainActivity.this, "Verifique a internet! Problemas na atualiza��o!", Toast.LENGTH_SHORT).show();
 				}
           }
          });
          }
      
      
      private void pegaDadosPesquisa(){
      	Cursor cursorConfiguracao;
      	cursorConfiguracao = bd.rawQuery(sql_select.GET_CONFIGURACAO,null);
     	
      	cursorConfiguracao.moveToFirst();
      	if (cursorConfiguracao.getCount() > 0) {
      		filtro_id_cliente = cursorConfiguracao.getString(0);
      		filtro_id_pesquisa = cursorConfiguracao.getString(1);
      		filtro_desc_pesquisa = cursorConfiguracao.getString(2);
      		filtro_automatico = cursorConfiguracao.getString(3);
      		filtro_previsao = cursorConfiguracao.getString(4);
      		
      	}
      	else{
			filtro_id_cliente = "";
      		filtro_id_pesquisa = "";
      		filtro_desc_pesquisa = "";
      		filtro_automatico = "";
      		filtro_previsao = "";
      	}
      	
      }
      
      private StringWriter ParametroRetorna(){
      	try{
  		writeXmlInicio();
     		
  		//configuracao Oconfiguracao = new configuracao();
  		//writeXml(PegarDados(sql_select.GET_CONFIGURACAO,Oconfiguracao), sql_create.TABLE_CONFIGURACAO);
         		
  		Resposta OResposta = new Resposta();
  		writeXml(PegarDados(sql_select.GET_RESPOSTAOrdenado,OResposta), sql_create.TABLE_RESPOSTA);
  		
  		// descomentar para teste IERC
  		// bd.execSQL(" update aluno set id_turma = 1, id_escola = 1 ");
  		
  		Aluno OAluno = new Aluno();
  		writeXml(PegarDados(sql_select.GET_ALUNO_completoNOVO,OAluno), sql_create.TABLE_ALUNO);
  	
  		
  		// comentar para teste IERC
  		Controle_inicio OControle_inicio = new Controle_inicio();
  		writeXml(PegarDados(sql_select.GET_CONTROLE_INICIO,OControle_inicio), sql_create.TABLE_CONTROLE_INICIO);
  		
  		// comentar para teste IERC
  		Controle_fim OControle_fim = new Controle_fim();
  		writeXml(PegarDados(sql_select.GET_CONTROLE_FIM,OControle_fim), sql_create.TABLE_CONTROLE_FIM);
         		
  		
         		
  		// sql_select.GET_RESPOSTA
  		
  		writeXmlFim();
  		StringWriter s = new StringWriter();
  		s = writer;
  		//TextView textoEdits = (TextView) findViewById(R.id.textView2);
  		
  		//textoEdits.setText(s.toString());
         	
  		
  		return s;
      	}
      	catch (Throwable ex){
      		return  null;
      	}
  	
      }
      
      private void writeXmlInicio(){
      	//serializer = null;
  	    writer = new StringWriter();
  	    try {
  	        serializer.setOutput(writer);
  	        serializer.startDocument("UTF-8", true);
  	        serializer.startTag("", "NewDataSet");
  	       
  	    } catch (Exception e) {
  	    	
  	        throw new RuntimeException(e);
  	    } 
      }
      
      private void writeXmlFim(){
      	//StringWriter writer = new StringWriter();
      	
         	try{
         		
         		serializer.endTag("", "NewDataSet");
         		serializer.endDocument();

         	} catch (Exception e) {
         		throw new RuntimeException(e);
         	} 
      }
      
      private XmlSerializer writeXml(ArrayList<Object> Listaoutput,String nNomeTabela){
  	    //XmlSerializer serializer = Xml.newSerializer();
  	    //StringWriter writer = new StringWriter();
  	    try {
  	        //serializer.setOutput(writer);
  	        //serializer.startDocument("UTF-8", true);
  	        String NTAGClasse = nNomeTabela;
  	        //serializer.startTag("", "NewDataSet");
  	        
  	        for (int j = 0; Listaoutput.size() > j; j++){
  	        	
  	        	serializer.startTag("", NTAGClasse);
  	        	
  	        	Class classe = Listaoutput.get(j).getClass();
  	        	Class theClass = Listaoutput.get(j).getClass();
  	        	Field[] fields = theClass.getDeclaredFields();
  	        
  	        	for (int i = 0; i < fields.length; i++) {
  	        		Type type = fields[i].getType();
  	        		fields[i].setAccessible(true);
      	            
  	        		if (fields[i].getType().equals(String.class)) {
  	        			String r = fields[i].toString();
  	        			
  	        			serializer.startTag("", fields[i].getName());
  	        			if (!r.trim().equals("")){
  	        				serializer.text(fields[i].get(Listaoutput.get(j)).toString());
  	        			}else{
  	        				serializer.text("");
  	        			}
  	        			serializer.endTag("", fields[i].getName());
  	        		}
  	        		else if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
  	        			serializer.startTag("", fields[i].getName());
  	        			int nInt = fields[i].getInt(Listaoutput.get(j));
  	        			String Temp = Integer.toString(nInt);
  	        			serializer.text(Temp);
  	        			serializer.endTag("", fields[i].getName());
  	        		}
  	        		else if (type.equals(Float.TYPE) || type.equals(Float.class)) {
  	        			serializer.startTag("", fields[i].getName());
  	        			float nFloat = fields[i].getFloat(Listaoutput.get(j));
  	        			String Temp = Float.toString(nFloat);
  	        			serializer.text(Temp);
  	        			serializer.endTag("", fields[i].getName());
  	        		}

  	        		}
  	        	serializer.endTag("", NTAGClasse);
  	        	}
  	        
  	        //serializer.endTag("", "NewDataSet");
  	        //serializer.endDocument();
  	        return serializer;
  	        //return writer.toString();
  	    } catch (Exception e) {
  	        throw new RuntimeException(e);
  	    } 
  	}

      
      public ArrayList<Object> PegarDados(String nString,  Object output) {
          nDataBase = new DataBase(fim.this);
  		bd = nDataBase.getReadableDatabase();
  	    Cursor cursorPergunta = bd.rawQuery(nString,null);
  		cursorPergunta.moveToFirst();
  		String strValue;
  		String d;
  		ArrayList ResultadoLista = new ArrayList<Object>();
  		
  		for (int iConto = 0;   cursorPergunta.getCount() > iConto ; iConto ++ ){
  			
  			//Resposta output = new Resposta();
  			
          	ClasseADO nClasseADO = new ClasseADO();
          	output = nClasseADO.IntanciarClasse(output.getClass().getSimpleName());
          	

  			
  			cursorPergunta.getString(0);	
  			Class classe = output.getClass();
  			Class theClass = output.getClass();
  			Field[] fields = theClass.getDeclaredFields();
  			for (int i = 0; i < fields.length; i++) {
  				Type type=fields[i].getType();
  				fields[i].setAccessible(true);
  				if (fields[i].getType().equals(String.class)) {
  					int ii = cursorPergunta.getColumnIndex(fields[i].getName());
  					strValue = cursorPergunta.getString(ii);
  					if(strValue.length()!=0){
  						try {
  							fields[i].set(output, strValue);
  						} catch (IllegalArgumentException e) {
  							// TODO Auto-generated catch block
  							e.printStackTrace();
  						} catch (IllegalAccessException e) {
  							// TODO Auto-generated catch block
  							e.printStackTrace();
  						}
  					}
  				}
  				else if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
  					int ie = cursorPergunta.getColumnIndex(fields[i].getName());	
  					strValue = cursorPergunta.getString(ie);
  					if(strValue.length()!=0){
  						try {
  							fields[i].setInt(output, Integer.valueOf(strValue));
  						} catch (NumberFormatException e) {
  							// TODO Auto-generated catch block
  							e.printStackTrace();
  						} catch (IllegalArgumentException e) {
  							// TODO Auto-generated catch block
  							e.printStackTrace();
  						} catch (IllegalAccessException e) {
  							// TODO Auto-generated catch block
  							e.printStackTrace();
  						}
  					}
  				}
  				else if (type.equals(Float.TYPE) || type.equals(Float.class)) {
  					int ia = cursorPergunta.getColumnIndex(fields[i].getName());
  					strValue = cursorPergunta.getString(ia);
  					if(strValue.length()!=0){
  						try {
  							fields[i].setFloat(output, Float.valueOf(strValue));
  						} catch (NumberFormatException e) {
  							// TODO Auto-generated catch block
  							e.printStackTrace();
  						} catch (IllegalArgumentException e) {
  							// TODO Auto-generated catch block
  							e.printStackTrace();
  						} catch (IllegalAccessException e) {
  							// TODO Auto-generated catch block
  							e.printStackTrace();
  						}
  					}
  				}
  			}
  			ResultadoLista.add(output);
  			cursorPergunta.moveToNext();
  		}
  		return ResultadoLista;
      }
      
      
      private Boolean ChamaThead() throws IOException, XmlPullParserException{
       	  
       	  
   	   SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);
   	   
   	   pegaDadosPesquisa();
   	       	   
   	   request.addProperty("ds",ParametroRetorna().toString());
   	   request.addProperty("w_TipoAluno",filtro_automatico.toString());
   	   request.addProperty("w_idCliente",filtro_id_cliente.toString());
   	   request.addProperty("w_idPesquisa",filtro_id_pesquisa.toString());


   	// descomentar para teste IERC
    	//   request.addProperty("w_TipoAluno",0);
    	//   request.addProperty("w_idCliente",1);
    	//   request.addProperty("w_idPesquisa",1);
   	   
   	   SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
   	   envelope.dotNet = true;
   	   
   	   envelope.setOutputSoapObject(request);

   	   
   	   
   	   HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
   	   httpTransport.debug = true;
   	   //httpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
   	   try
   	   {
   		   httpTransport.call(SOAP_ACTION, envelope); // ERRO AQUI 
     		   String resultString =  (String)envelope.getResponse().toString();
     		 Boolean nBoolean;
     		   if (resultString.equals("true")){
     			   nBoolean = true;
     		   }
     		   else{
     			 nBoolean = false;   
     		   }
     		  return nBoolean;        		  
   		  


   	   }
        catch (Exception exception)
        {
     	   return false;
     	   
        }
      } 

  }


}

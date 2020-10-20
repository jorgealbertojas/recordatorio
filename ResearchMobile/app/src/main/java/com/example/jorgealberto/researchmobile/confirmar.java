package com.example.jorgealberto.researchmobile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.media.MediaRecorder;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorgealberto.researchmobile.library.MyConstant;
import com.example.jorgealberto.researchmobile.service.DB;
import com.example.jorgealberto.researchmobile.service.DataBase;
import com.example.jorgealberto.researchmobile.SQL.sql_delete;
import com.example.jorgealberto.researchmobile.SQL.sql_select;

public class confirmar extends Activity {

	private Boolean nTIME = false;
	private String nGPS = "";
	private String nVOZ = "false";
	private String nFONTE = "";

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

	private String NomeGravacaoArquivo = "";
	private String NomeGravacao = "";

  	private SQLiteDatabase bd;
  	//private Context context;
	private DataBase nDataBase;
	private boolean PlayIniciado = false;

	private int opcaoQuestionario = 0;
	private int opcaoQuestionarioFINAL = 0;

	ImageView imageViewsound;
	ImageView imageViewgps;

	MediaRecorder recorder = new MediaRecorder();

	Cursor cursorPerguntaReposta01;

	Cursor cursorPergunta;

	Cursor cursorPerguntamim;

	@Override
	protected void onStart(){
		mostrarSatusPesquisa();
		super.onStart();
	}


	@Override
	protected void onResume() {
		super.onResume();
		AbrirQuestionarioSQL();
	};
	
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.confirmar);
     
	      	carregarFONTE();
		  	carregarGPS();
		  	carregarTIME();

	        nDataBase = new DataBase(this);
			bd = nDataBase.getReadableDatabase();
			nDataBase.onCreate(bd);
			
			DB.getInstance(this);

		  	Bundle extras = getIntent().getExtras();
		  	filtro_id_cliente= extras.getString("filtro_id_cliente");
		  	filtro_id_pesquisa= extras.getString("filtro_id_pesquisa");
		  	filtro_automatico= extras.getString("filtro_automatico");
		  	filtro_desc_pesquisa= extras.getString("filtro_desc_pesquisa");
		  	filtro_previsao= extras.getString("filtro_previsao");
		  	usuario= extras.getString("usuario");
		  	Nomeusuario= extras.getString("Nomeusuario");
		    String vvv = extras.getString("AlunoAtual");
		    AlunoAtual= Integer.parseInt(extras.getString("AlunoAtual"));
		  	nFONTE= extras.getString("nFONTE");
		  	nVOZ= extras.getString("nVOZ");

		  	imageViewsound = (ImageView) findViewById(R.id.imageViewsound);
		  	imageViewsound.setColorFilter(imageViewsound.getContext().getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);

		  	if (!(nVOZ.toString().equals("true"))){
			  	imageViewsound.setVisibility(View.INVISIBLE);
		  	}

		  imageViewgps = (ImageView) findViewById(R.id.imageViewgps);
		  imageViewgps.setColorFilter(imageViewgps.getContext().getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);

		  if (!(nGPS.toString().equals("true"))){
			  imageViewgps.setVisibility(View.INVISIBLE);
		  }


		  CardView CardView1 = (CardView) findViewById(R.id.cardViewOcorrencia1);
		  	CardView1.setOnClickListener(new OnClickListener() {
	        	@Override
	        	public void onClick(View arg0) {
	        		try {
	        			//bebeto
	        			if (nVOZ.toString().equals("true")){
							if (PlayIniciado == false) {
								startRecord();
							}
	        			}
						
						Entrar(1);
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	        	}
	        });

		  /////////////////////////////////////
		  // PARA QUANDO TIVER OUTROS BOTOES DE SUBFORMULARIOS
		  ///////////////////////////
		 /* CardView CardView2 = (CardView) findViewById(R.id.cardViewOcorrencia2);
		  CardView2.setOnClickListener(new OnClickListener() {
			  @Override
			  public void onClick(View arg0) {
				  try {
					  //bebeto
					  if (nVOZ){
						  if (PlayIniciado == false) {
							  startRecord();
						  }
					  }

					  Entrar(2);
				  } catch (IllegalStateException e) {
					  // TODO Auto-generated catch block
					  e.printStackTrace();
				  } catch (IOException e) {
					  // TODO Auto-generated catch block
					  e.printStackTrace();
				  }

			  }
		  });*/





			
			ImageButton btn_excluir = (ImageButton) findViewById(R.id.imageButton2);
			btn_excluir.setOnClickListener(new OnClickListener() {
	        	@Override
	        	public void onClick(View arg0) {
	        		MensagemConfirmacao();
	        	}
	        });
	        
	    	
	        ImageButton volumemuteImageButton3 = (ImageButton) findViewById(R.id.imageButton2);
	        volumemuteImageButton3.setOnTouchListener(new View.OnTouchListener() 
	        { 
	        	public boolean onTouch(View v, MotionEvent event) { switch (event.getAction()) { 
	        	case MotionEvent.ACTION_DOWN: {  
	        		((ImageButton) v).setImageResource(R.mipmap.del_02);
	        		v.invalidate(); 
	        		break;
	        	}
	        	case MotionEvent.ACTION_UP: 
	        	{ 
	        		((ImageButton) v).setImageResource(R.mipmap.del_01);
	        		v.invalidate(); 
	        		break; 
	        	} 
	        	} 
	        		return false; 
	        	}
	        });
	        
	       
	        
	        
	    			
	    	
	    			
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
           PlayIniciado = true;
       }

       private void stopRecord(){
           recorder.stop();
         //recorder.release();
       }
	  
	  private void Entrar(){
  			bd.execSQL(sql_delete.DEL_TODOS_CONTROLE_INICIO,new String[] {Integer.toString(AlunoAtual)});
	        Intent WSActivity = new Intent(this, Questionario.class);
		  	WSActivity.putExtra("filtro_id_cliente",filtro_id_cliente);
		  	WSActivity.putExtra("filtro_id_pesquisa",filtro_id_pesquisa);
		  	WSActivity.putExtra("filtro_desc_pesquisa",filtro_desc_pesquisa);
		  	WSActivity.putExtra("filtro_automatico",filtro_automatico);
		  	WSActivity.putExtra("filtro_previsao",filtro_previsao);
		  	WSActivity.putExtra("usuario",usuario);
		  	WSActivity.putExtra("Nomeusuario",Nomeusuario);
		  	WSActivity.putExtra("AlunoAtual",Integer.toString(AlunoAtual));
		    WSActivity.putExtra("nFONTE",nFONTE);
		  	WSActivity.putExtra("nGPS",nGPS);


		  	if (nTIME) {
				WSActivity.putExtra("nTIME", "1");
		  	}
		  	else{
			  	WSActivity.putExtra("nTIME", "0");
		  	}



		  	WSActivity.putExtra("nFONTE",nFONTE);

	        startActivity(WSActivity);
		    this.finish();
	  }
	  
		 protected void AbrirQuestionarioSQL(){

			 Cursor cursorPerguntaReposta;
			 Cursor cusrsorSaltoMax;
			 int maxSalto;
			 int maxPergunta;
			 int maxTOTAL;
	 
			 nDataBase = new DataBase(this);
			 bd = nDataBase.getReadableDatabase();
			 cusrsorSaltoMax = bd.rawQuery(sql_select.GET_MAX_SALTO,null);
			 cusrsorSaltoMax.moveToFirst();
			 
			 maxSalto =  cusrsorSaltoMax.getInt(0);
		 
			 nDataBase = new DataBase(this);
			 bd = nDataBase.getReadableDatabase();
			 cursorPergunta = bd.rawQuery(sql_select.GET_PERGUNTA_CONTA,null);
			 cursorPerguntamim = bd.rawQuery(sql_select.GET_PERGUNTA_CONTA_mim,null);
			 cursorPergunta.moveToFirst();
			 cursorPerguntamim.moveToFirst();
			 
			 maxPergunta=  cursorPergunta.getInt(0);
			 
			 if (maxSalto > maxPergunta) {
				 maxTOTAL = cusrsorSaltoMax.getInt(1);
			 }else{
				 maxTOTAL = maxPergunta;
			 }
			 
				

				
		 }
		 
		 private void apagaTodosRegistrosRespsotaAluno(){
			 
			    LayoutInflater factory = LayoutInflater.from(this);
			    final View deleteDialogView = factory.inflate(
			            R.layout.custom_dialog, null);
			    final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
			    deleteDialog.setView(deleteDialogView);
			    
			    TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
			    nTextView.setText("ATENÇÃO! Tem ceterteza que deseja apagar todas as resposta desse entrevistado?");
			    
			    deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new OnClickListener() {

			    	@Override
			        public void onClick(View v) {
			    		//your business logic 
			    		bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ALUNO,new String[] {Integer.toString(AlunoAtual)});
			    		//bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ALUNO,new String[] {Integer.toString(2)});
			    		bd.execSQL(sql_delete.DEL_TODOS_CONTROLE_INICIO,new String[] {Integer.toString(AlunoAtual)});
			    		bd.execSQL(sql_delete.DEL_TODOS_CONTROLE_FIM,new String[] {Integer.toString(AlunoAtual)});
			    		ImageButton volumemuteImageButton1 = (ImageButton) findViewById(R.id.imageButton1);
			    		volumemuteImageButton1.setImageResource(R.mipmap.exc_1);
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
		 
		 
		 
		 private void MensagemConfirmacao(){
			 
			    LayoutInflater factory = LayoutInflater.from(this);
			    final View deleteDialogView = factory.inflate(
			            R.layout.custom_dialog, null);
			    final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
			    deleteDialog.setView(deleteDialogView);
			    
			    TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
			    nTextView.setText("você deseja apagar todas as resposta desse entrevistado?");
			    deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new OnClickListener() {

			    	@Override
			        public void onClick(View v) {
			    		//your business logic 
			    		apagaTodosRegistrosRespsotaAluno();
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

		 @Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			 // bebeto
			 if (nVOZ.toString().equals("true")){
				 if (PlayIniciado == true){
					 stopRecord();
				 }
			 }
			super.onDestroy();
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

					nGPS = "true";
				}else{

					nGPS = "false";
				}
			}else
			{
				nGPS = "false";
			}


		}
		catch (IOException e)
		{
			e.printStackTrace();
		}


	}



	public void carregarFONTE()
	{




		Properties properties = new Properties();
		try
		{
			FileInputStream fis;
			fis = new  FileInputStream(MyConstant.nomeArquivoINI);
			properties.load(fis);
			String nIP = properties.getProperty("conf.FONTE");

			if (nIP != null){
				if (nIP.equals("P")){
					nFONTE = "P";
				}
				else if (nFONTE.equals("M")){
					nFONTE = "M";
				}
				else if (nFONTE.equals("G")){
					nFONTE = "G";
				}
			}else
			{
				nFONTE = "";
			}


		}
		catch (IOException e)
		{
			e.printStackTrace();
		}


	}

	public void carregarTIME()
	{
		Properties properties = new Properties();
		try
		{
			FileInputStream fis;
			fis = new  FileInputStream(MyConstant.nomeArquivoINI);
			properties.load(fis);
			String nIP = properties.getProperty("conf.TIME");

			if (nIP != null){
				if (nIP.equals("true")){
					nTIME = true;
				}else{
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


	private void mostrarSatusPesquisa(){




		nDataBase = new DataBase(this);
		bd = nDataBase.getReadableDatabase();

		cursorPerguntaReposta01 = bd.rawQuery(sql_select.GET_PERGUNTA_CHECK01,new String[] {Integer.toString(AlunoAtual) });
		cursorPerguntaReposta01.moveToFirst();

		////////////////////////////
		//quando tivre mais de um botão
		///////////////////
		//Cursor cursorPerguntaReposta03;
		//cursorPerguntaReposta03 = bd.rawQuery(sql_select.GET_PERGUNTA_CHECK03,new String[] {Integer.toString(AlunoAtual) });
		//cursorPerguntaReposta03.moveToFirst();



		ImageButton volumemuteImageButton01 = (ImageButton) findViewById(R.id.imageButton1);
		if (cursorPerguntaReposta01.getCount() > 0) {
			volumemuteImageButton01.setImageResource(R.mipmap.con_1);
		}
		else{
			volumemuteImageButton01.setImageResource(R.mipmap.exc_1);
		}

		// quando tiver mais de um botão
/*		ImageButton volumemuteImageButton02 = (ImageButton) findViewById(R.id.imageButton3);
		if (cursorPerguntaReposta03.getCount() > 0) {
			volumemuteImageButton02.setImageResource(R.mipmap.con_1);
		}
		else{
			volumemuteImageButton02.setImageResource(R.mipmap.exc_1);
		}*/




	}

	private  void pegarOpcao(int opcao){
		if (opcao == 1){
			opcaoQuestionario = cursorPerguntamim.getInt(0);
			opcaoQuestionarioFINAL =  cursorPergunta.getInt(0);
		}
		/////
		// QUANDO TIVER MAIS DE UM BOTÃO
		//
		//
		/*else if (opcao == 2){
			opcaoQuestionario = 10;
			opcaoQuestionarioFINAL = 125;
		}*/
	}


	private void Entrar(int opcao){


		if (opcao == 1){
			pegarOpcao(opcao);
			Intent WSActivity = new Intent(this, Questionario.class);
			WSActivity.putExtra("filtro_id_cliente",filtro_id_cliente);
			WSActivity.putExtra("filtro_id_pesquisa",filtro_id_pesquisa);
			WSActivity.putExtra("filtro_desc_pesquisa",filtro_desc_pesquisa);
			WSActivity.putExtra("filtro_automatico",filtro_automatico);
			WSActivity.putExtra("filtro_previsao",filtro_previsao);
			WSActivity.putExtra("usuario",usuario);
			WSActivity.putExtra("Nomeusuario",Nomeusuario);
			WSActivity.putExtra("AlunoAtual",Integer.toString(AlunoAtual));
			WSActivity.putExtra("nFONTE",nFONTE);
			WSActivity.putExtra("nGPS",nGPS);
			WSActivity.putExtra("NomeGravacaoArquivo",NomeGravacaoArquivo);
			WSActivity.putExtra("opcao",Integer.toString(1));
			WSActivity.putExtra("opcaoQuestionario",Integer.toString(opcaoQuestionario));
			WSActivity.putExtra("opcaoQuestionarioFINAL",Integer.toString(opcaoQuestionarioFINAL));
			if (nTIME) {
				WSActivity.putExtra("nTIME", "1");
			}
			else{
				WSActivity.putExtra("nTIME", "0");
			}

			startActivity(WSActivity);
			this.finish();
		}


		// QUNADO TIVER MAIS DE UM BOTÃO
		/*else if (opcao == 2){
			pegarOpcao(opcao);
			Intent WSActivity = new Intent(this, Questionario.class);
			WSActivity.putExtra("filtro_id_cliente",filtro_id_cliente);
			WSActivity.putExtra("filtro_id_pesquisa",filtro_id_pesquisa);
			WSActivity.putExtra("filtro_desc_pesquisa",filtro_desc_pesquisa);
			WSActivity.putExtra("filtro_automatico",filtro_automatico);
			WSActivity.putExtra("filtro_previsao",filtro_previsao);
			WSActivity.putExtra("usuario",usuario);
			WSActivity.putExtra("Nomeusuario",Nomeusuario);
			WSActivity.putExtra("AlunoAtual",Integer.toString(AlunoAtual));
			WSActivity.putExtra("nFONTE",nFONTE);
			WSActivity.putExtra("nGPS",nGPS);
			WSActivity.putExtra("NomeGravacaoArquivo",NomeGravacaoArquivo);
			WSActivity.putExtra("opcao",Integer.toString(2));
			WSActivity.putExtra("opcaoQuestionario",Integer.toString(opcaoQuestionario));
			WSActivity.putExtra("opcaoQuestionarioFINAL",Integer.toString(opcaoQuestionarioFINAL));
			if (nTIME) {
				WSActivity.putExtra("nTIME", "1");
			}
			else{
				WSActivity.putExtra("nTIME", "0");
			}

			startActivity(WSActivity);

		}*/

	}




}

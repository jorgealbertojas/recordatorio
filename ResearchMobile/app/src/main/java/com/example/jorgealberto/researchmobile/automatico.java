package com.example.jorgealberto.researchmobile;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jorgealberto.researchmobile.service.DB;
import com.example.jorgealberto.researchmobile.service.DataBase;
import com.example.jorgealberto.researchmobile.SQL.sql_create;
import com.example.jorgealberto.researchmobile.SQL.sql_select;

public class automatico extends Activity{

	private String filtro_entrevistadonumero = "";

	private int opcaoQuestionario = 0;
	private int opcaoQuestionarioFINAL = 0;

	private int filtro_escola = 0;
	private int filtro_turma = 0;
	private String filtro_entrevistado = "";
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
	private String nFONTE = "";
	private String nVOZ = "";

	Cursor cursorPergunta;

	Cursor cursorPerguntamim;

	private LinearLayout ll;
	private ArrayList<ImageView> ImageButtons;
	private ArrayList<EditText> EditTexts;
	private TextView nTotalPes;
	
  	private SQLiteDatabase bd;
  	private Context context;
	private DataBase nDataBase;

	private ImageView btn_create;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        //requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.automatico);

		    Bundle extras = getIntent().getExtras();
		 	filtro_id_cliente= extras.getString("filtro_id_cliente");
		 	filtro_id_pesquisa= extras.getString("filtro_id_pesquisa");
		 	filtro_automatico= extras.getString("filtro_automatico");
		 	filtro_desc_pesquisa= extras.getString("filtro_desc_pesquisa");
		 	filtro_previsao= extras.getString("filtro_previsao");
		 	usuario= extras.getString("usuario");
		 	Nomeusuario= extras.getString("Nomeusuario");
		 	filtro_entrevistado= extras.getString("filtro_entrevistado");
		 	nFONTE= extras.getString("nFONTE");
		 	nVOZ= extras.getString("nVOZ");
		 	filtro_entrevistadonumero = extras.getString("filtro_entrevistadonumero");



	        
	        nDataBase = new DataBase(this);
			bd = nDataBase.getReadableDatabase();
			nDataBase.onCreate(bd);


			
			DB.getInstance(this);

		 	this.setTitle(pesquisaSelecionada());
			
			ImageButtons = new ArrayList<ImageView>();
			ll = (LinearLayout) findViewById(R.id.edits_ll);

			criarAluno();


		//	ImageButton btn_excluir = (ImageButton) findViewById(R.id.imageButton2);
		//	btn_excluir.setOnClickListener(new View.OnClickListener() {
	    //    	@Override
	    //    	public void onClick(View arg0) {

	    //    	}
	    //    });


/*         ImageView volumemuteImageButton3 = (ImageView) findViewById(R.id.imageButton1);
	        volumemuteImageButton3.setOnTouchListener(new View.OnTouchListener()
	        {
	        	public boolean onTouch(View v, MotionEvent event) { switch (event.getAction()) {
	        	case MotionEvent.ACTION_DOWN: {
	        		((ImageView) v).setImageResource(R.mipmap.user_mais_02);
	        		v.invalidate();
	        		break;
	        	}
	        	case MotionEvent.ACTION_UP:
	        	{
	        		((ImageView) v).setImageResource(R.mipmap.user_mais_01);
	        		v.invalidate();
	        		break;
	        	}
	        	}
	        		return false;
	        	}
	        });*/

	    	
	    			
	    }
	 

	 
	 private void criarAluno(){
		   nDataBase = new DataBase(this);
		   bd = nDataBase.getReadableDatabase();
		   int quantidadePes = 0;
		   

		   String SQL_Completo = sql_select.GET_ALUNO + " AND P.NOME NOT LIKE 'OUTRO%' ";
		   
		   if (filtro_escola != 0 ){
			   SQL_Completo = SQL_Completo + " AND  P.ID_ESCOLA = " + Integer.toString(filtro_escola);
		   }
		   if (filtro_turma != 0 ){
			   SQL_Completo = SQL_Completo + " AND P.ID_TURMA = " + Integer.toString(filtro_turma);
		   }
		   if (filtro_entrevistado != null) {
			 	if (!filtro_entrevistado.trim().equals("")) {
				 	SQL_Completo = SQL_Completo + "  AND P.NOME LIKE '%" + (filtro_entrevistado) + "%' ";
			 	}
		   }

		 if (filtro_entrevistadonumero != null) {
			 if (!filtro_entrevistadonumero.trim().equals("")) {
				 SQL_Completo = SQL_Completo + "  AND P.ID_ALUNO = " + filtro_entrevistadonumero;
			 }
		 }
			
		   SQL_Completo = SQL_Completo + "  ORDER BY P.ID_ALUNO DESC ";
		   
		   Cursor cursor = bd.rawQuery(SQL_Completo,null);
		   cursor.moveToFirst();
		   
		   try{

			int igual = 0;
			
			int contaID = 0;

			   String varusuario = "";
			   String varGPS = "";
			   String varData = "";

			 int eeeee =  cursor.getCount();
			
			while(!cursor.isAfterLast() )
			{
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
				ll.setOrientation(LinearLayout.VERTICAL);
				
				LayoutInflater inflater = (LayoutInflater)      this.getSystemService(LAYOUT_INFLATER_SERVICE);
				View childLayout = inflater.inflate(R.layout.item_main,
						(ViewGroup) findViewById(R.id.contener));

				ImageView imageView  = childLayout.findViewById(R.id.imageView);
				ImageView imageView2  = childLayout.findViewById(R.id.imageView2);
				ImageView imageView3  = childLayout.findViewById(R.id.imageView3);
				ImageView imageView4  = childLayout.findViewById(R.id.imageView4);

				Resources res = getResources();
				final int newColor = res.getColor(R.color.color_primary);
				imageView.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
				imageView2.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
				imageView3.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
				imageView4.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);


				varusuario = "";
				varGPS = "";
				varData = "";


				nDataBase = new DataBase(this);
				bd = nDataBase.getReadableDatabase();
				Cursor GET_tem_controle_inicio = bd.rawQuery(sql_select.GET_tem_controle_inicio, new String[]{(cursor.getString(0))});
				GET_tem_controle_inicio.moveToFirst();
				if (GET_tem_controle_inicio.getCount() > 0) {

					varusuario = GET_tem_controle_inicio.getString(0) + " - " + GET_tem_controle_inicio.getString(1);
					varGPS  = GET_tem_controle_inicio.getString(2) + " - " + GET_tem_controle_inicio.getString(3);;
					varData =  GET_tem_controle_inicio.getString(4) + " - " + GET_tem_controle_inicio.getString(5);;
				}

				TextView tv_title_user = childLayout.findViewById(R.id.tv_title_user);
				TextView tv_title_hora = childLayout.findViewById(R.id.tv_title_hora);

				tv_title_user.setText(varusuario);
				tv_title_hora.setText(varData);

				tv_title_user.setText("Jorge Alberto");
				tv_title_hora.setText("20:10 01/02/2020");

				CardView nCardView = new CardView(this);
				nCardView.setRadius(2);
				nCardView.addView(childLayout);
				nCardView.setContentPadding(15, 15, 15, 15);
				nCardView.setMaxCardElevation(7);
				nCardView.setTag(cursor.getString(0));


				if (cursor.getInt(2) == 0){
				//	nImageButtonUSER.setColorFilter(nImageButtonUSER.getContext().getResources().getColor(R.color.blue_dark), PorterDuff.Mode.SRC_ATOP);
				//	TextViewGPS.setTextColor(TextViewGPS.getContext().getResources().getColor(R.color.blue_dark));

						
				}else{


				//	nImageButtonUSER.setColorFilter(nImageButtonUSER.getContext().getResources().getColor(R.color.blue_dark), PorterDuff.Mode.SRC_ATOP);
				//	TextViewGPS.setTextColor(TextViewGPS.getContext().getResources().getColor(R.color.blue_dark));


				}

				nCardView.setOnClickListener(new View.OnClickListener() {

		            @Override
		            public void onClick(View view) {
						Entrar(1);
					      

		            }
		        });
				
				
				ll.addView(nCardView);


				cursor.moveToNext();
			}
		    }
		     finally{
		    	 cursor.close();
		    }
	 }
	 
     private void onInsert(Context context, ContentValues obj, String nTabela) { 
     	  try{
     		      		  
     		  DB.getInstance( context ).insert( nTabela, obj ); 
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
     private String pesquisaSelecionada(){
    	 Cursor cursor2 = bd.rawQuery(sql_select.GET_CONFIGURACAO,null);
    	 cursor2.moveToFirst();
    	try{
    		if (cursor2.getCount() > 0) {
    			return "Pesquisa ativa � a: " + cursor2.getString(2).toString();
    		}else{
    			return "Nenhuma pesquisa Ativa!";
    		}
    	}
    	finally{
    		cursor2.close();
    		
    	}
     }
     
     @Override
    protected void onRestart() {
    	// TODO Auto-generated method stub
    	super.onRestart();
		  ll.removeAllViewsInLayout();
		  criarAluno();

    }


	private void Entrar(int opcao) {


		if (opcao == 1) {
			pegarOpcao(opcao);
			Intent WSActivity = new Intent(this, Questionario.class);
			WSActivity.putExtra("filtro_id_cliente", filtro_id_cliente);
			WSActivity.putExtra("filtro_id_pesquisa", filtro_id_pesquisa);
			WSActivity.putExtra("filtro_desc_pesquisa", filtro_desc_pesquisa);
			WSActivity.putExtra("filtro_automatico", filtro_automatico);
			WSActivity.putExtra("filtro_previsao", filtro_previsao);
			WSActivity.putExtra("usuario", usuario);
			WSActivity.putExtra("Nomeusuario", Nomeusuario);
			WSActivity.putExtra("AlunoAtual", Integer.toString(AlunoAtual));
			WSActivity.putExtra("nFONTE", nFONTE);
			WSActivity.putExtra("nGPS", "false");
			WSActivity.putExtra("NomeGravacaoArquivo", "GRAVACAO_" + usuario + "_" + System.currentTimeMillis()+".amr");
			WSActivity.putExtra("opcao", Integer.toString(1));
			WSActivity.putExtra("opcaoQuestionario", Integer.toString(opcaoQuestionario));
			WSActivity.putExtra("opcaoQuestionarioFINAL", Integer.toString(opcaoQuestionarioFINAL));
			if (true) {
				WSActivity.putExtra("nTIME", "1");
			} else {
				WSActivity.putExtra("nTIME", "0");
			}

			startActivity(WSActivity);
			this.finish();
		}
	}

		private  void pegarOpcao(int opcao){
			nDataBase = new DataBase(this);
			bd = nDataBase.getReadableDatabase();
			cursorPergunta = bd.rawQuery(sql_select.GET_PERGUNTA_CONTA,null);
			cursorPerguntamim = bd.rawQuery(sql_select.GET_PERGUNTA_CONTA_mim,null);
			cursorPergunta.moveToFirst();
			cursorPerguntamim.moveToFirst();


			if (opcao == 1){
				opcaoQuestionario = cursorPerguntamim.getInt(0);
				opcaoQuestionarioFINAL =  cursorPergunta.getInt(0);
			}

		}

}

package com.example.jorgealberto.cadeconsumo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.jorgealberto.cadeconsumo.service.DB;
import com.example.jorgealberto.cadeconsumo.service.DataBase;
import com.example.jorgealberto.cadeconsumo.SQL.sql_select;

public class automatico_nao extends Activity{
	private String filtro_entrevistadonumero = "";
	private String filtro_id_cliente = "";
	private String filtro_id_pesquisa = "";
	private String filtro_desc_pesquisa = "";
	private String filtro_automatico = "";
	private String filtro_previsao = "";
	private String usuario  = "";
	private String Nomeusuario = "";

	private int filtro_escola = 0;
	private int filtro_turma = 0;
	private String filtro_entrevistado = "";

  	private SQLiteDatabase bd;

	private DataBase nDataBase;
	
	private String ArrayEscola[]; 
	private String ArrayTurma[];
	private String ArrayEscolaNum[]; 
	private String ArrayTurmaNum[];
	private String nFONTE = "";
	private String nVOZ = "";

    @Override
    public void onCreate(Bundle icicle){
    	super.onCreate(icicle);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.automatico_usuario);

		Bundle extras = getIntent().getExtras();
		filtro_id_cliente= extras.getString("filtro_id_cliente");
		filtro_id_pesquisa= extras.getString("filtro_id_pesquisa");
		filtro_automatico= extras.getString("filtro_automatico");
		filtro_desc_pesquisa= extras.getString("filtro_desc_pesquisa");
		filtro_previsao= extras.getString("filtro_previsao");
		usuario= extras.getString("usuario");
		Nomeusuario= extras.getString("Nomeusuario");
		nFONTE= extras.getString("nFONTE");
		nVOZ= extras.getString("nFVOZ");
        
    	nDataBase = new DataBase(this);
    	bd = nDataBase.getReadableDatabase();
    	nDataBase.onCreate(bd);
        		
    	DB.getInstance(this);
        		
    	Cursor cursor1 = bd.rawQuery(sql_select.GET_ESCOLA,null);
    	
    	try{

    	cursor1.moveToFirst();
    	int i = 0;
    	ArrayEscola = new String[cursor1.getCount()+1];
    	ArrayEscolaNum = new String[cursor1.getCount()+1];
    	ArrayEscola[0] = "TODOS";
    	ArrayEscolaNum[0] = "0";
    	while(!cursor1.isAfterLast() )
    	{
    		i = i + 1;
    		ArrayEscola[i] = cursor1.getString(0).toString() + " - " +cursor1.getString(1);
    		ArrayEscolaNum[i] = cursor1.getString(0).toString();
    		cursor1.moveToNext();
    	}

    	//ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, ArrayEscola);
    	//AutoCompleteTextView clubes = (AutoCompleteTextView) findViewById(R.id.clubes);
    	//clubes.setAdapter(adp);
    	Spinner combo1 = (Spinner) findViewById(R.id.Spinnerperfil1);
    	ArrayAdapter adp1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ArrayEscola);
    	adp1.setDropDownViewResource(android.R.layout.simple_spinner_item);
    	combo1.setAdapter(adp1);

    	combo1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    		@Override
    		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
    			filtro_escola = Integer.parseInt(ArrayEscolaNum[(int) arg3].toString());
    			if (filtro_escola == 0){
    				CarregarCombo2(false,"");
    			}else{
    				CarregarCombo2(true,Integer.toString(filtro_escola));
    			}
    		}

    		@Override
    		public void onNothingSelected(AdapterView<?> arg0) {
    			// TODO Auto-generated method stub
    		};

    	});


    	CarregarCombo2(false,"");


    	ImageButton btn_confirmar = (ImageButton) findViewById(R.id.imageButton2);
    	btn_confirmar.setOnClickListener(new View.OnClickListener() {
    		@Override
    		public void onClick(View arg0) {
				EditText editEntrevistado1 = (EditText) findViewById(R.id.editEntrevistado);
				CheckBox ncheckbox = (CheckBox) findViewById(R.id.checkBoxNumero);
				if (ncheckbox.isChecked()){
					filtro_entrevistadonumero = editEntrevistado1.getText().toString();
					filtro_entrevistado = "";
				}
				else{
					filtro_entrevistadonumero = "";
					filtro_entrevistado = editEntrevistado1.getText().toString();
				}
    			Intent WSActivity = new Intent(automatico_nao.this, automatico.class);
				WSActivity.putExtra("filtro_id_cliente",filtro_id_cliente);
				WSActivity.putExtra("filtro_id_pesquisa",filtro_id_pesquisa);
				WSActivity.putExtra("filtro_desc_pesquisa",filtro_desc_pesquisa);
				WSActivity.putExtra("filtro_automatico",filtro_automatico);
				WSActivity.putExtra("filtro_previsao",filtro_previsao);
				WSActivity.putExtra("usuario",usuario);
				WSActivity.putExtra("Nomeusuario",Nomeusuario);
				WSActivity.putExtra("filtro_entrevistado",filtro_entrevistado);
				WSActivity.putExtra("filtro_entrevistadonumero",filtro_entrevistadonumero);
				WSActivity.putExtra("nFONTE",nFONTE);
				WSActivity.putExtra("nVOZ",nVOZ);
    			startActivity(WSActivity);
    		}
    	});

			final EditText nEditText = (EditText) findViewById(R.id.editEntrevistado);


			CheckBox ncheckbox = (CheckBox)  findViewById(R.id.checkBoxNumero);
			ncheckbox.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (((CheckBox) arg0).isChecked()){
						nEditText.setHint("Digitar número");
						nEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
					}else{
						nEditText.setHint("Digitar parte do nome");
						nEditText.setInputType(InputType.TYPE_CLASS_TEXT);
					}


				}
			});


    	btn_confirmar.setOnTouchListener(new View.OnTouchListener()
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
    	});

    }
     finally{
    	 cursor1.close();
    }
    }
    
    private void CarregarCombo2(boolean executa, String id_escola){
    	Cursor cursor2; 
    	if (executa){
    		cursor2 = bd.rawQuery(sql_select.GET_TURMA_parametro,new String[] { id_escola });	
    	}else
    	{
    		cursor2 = bd.rawQuery(sql_select.GET_TURMA,null);
    	}
    	cursor2.moveToFirst();
    	int j = 0;
    	ArrayTurma = new String[cursor2.getCount()+1];
    	ArrayTurmaNum = new String[cursor2.getCount()+1];
    	ArrayTurma[0] = "TODOS";
    	ArrayTurmaNum[0] = "0";
    	while(!cursor2.isAfterLast() )
    	{
    		j = j + 1;
    		ArrayTurma[j] = cursor2.getString(0).toString() + " - " + cursor2.getString(1);
    		ArrayTurmaNum[j] = cursor2.getString(0).toString();
    		cursor2.moveToNext();
    	}

    	Spinner combo2 = (Spinner) findViewById(R.id.Spinnerperfil2);
    	ArrayAdapter adp2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ArrayTurma);
    	adp2.setDropDownViewResource(android.R.layout.simple_spinner_item);
    	combo2.setAdapter(adp2);

    	combo2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    		@Override
    		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
    			filtro_turma = Integer.parseInt(ArrayTurmaNum[(int) arg3].toString());
    		}
    		
    		@Override
    		public void onNothingSelected(AdapterView<?> arg0) {
    			// TODO Auto-generated method stub
						
    		};
    			
    	});
    }
}

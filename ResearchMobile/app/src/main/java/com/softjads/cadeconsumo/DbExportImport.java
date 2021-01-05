package com.softjads.cadeconsumo;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import com.softjads.cadeconsumo.util.MyConstant;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.Time;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DbExportImport extends Activity{

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
	
	private LinearLayout ll;
	private ArrayList<ImageButton> ImageButtons;
	private ArrayList<EditText> EditTexts;
	
	private String NomeCopia = "backup_";
	
	private String bkp_selecionado = "";
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.bkp);
	        
	        
	        
			ImageButtons = new ArrayList<ImageButton>();
			ll = (LinearLayout) findViewById(R.id.edits_ll);
	        
			PreencherBKP(MyConstant.storage,NomeCopia);
			
	        ImageButton btn_create = (ImageButton) findViewById(R.id.imageButton1);
	        btn_create.setOnClickListener(new View.OnClickListener() {

	        	@Override
	            public void onClick(View view) {
	        		try {
	        			String DataCompleta;
	        			Time now = new Time();
	        			now.setToNow();
	        			
	        			DataCompleta = Integer.toString(now.year);
	        			DataCompleta = DataCompleta + "_"+Integer.toString(now.month+1);
	        			DataCompleta = DataCompleta + "_"+Integer.toString(now.monthDay);
	        			DataCompleta = DataCompleta + "_"+Integer.toString(now.hour);
	        			DataCompleta = DataCompleta + "_"+Integer.toString(now.minute);
	        			DataCompleta = DataCompleta + "_"+Integer.toString(now.second);

	        			
						bkp(MyConstant.caminhobanco,MyConstant.storage +NomeCopia + filtro_desc_pesquisa + DataCompleta +".db");
					
						ll.removeAllViewsInLayout();
						
						PreencherBKP(MyConstant.storage,NomeCopia);
						
						Toast.makeText(DbExportImport.this, "Cópia realizada com sucesso!" , Toast.LENGTH_LONG).show();
	        		} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        });
	        
	        ImageButton btn_create2 = (ImageButton) findViewById(R.id.imageButton2);
	        btn_create2.setOnClickListener(new View.OnClickListener() {

	        	@Override
	            public void onClick(View view) {
	        		try {

		            	if (!bkp_selecionado.toString().equals("")){
		        			String nomeRestauracao = bkp_selecionado;
							restaura_bkp( MyConstant.storage+nomeRestauracao,MyConstant.caminhobanco);
							Toast.makeText(DbExportImport.this, "Restauração com sucesso!" , Toast.LENGTH_LONG).show();
			            	
		            	}else
		            	{
		            		Toast.makeText(DbExportImport.this, "Selecione uma cópia de segurança!" , Toast.LENGTH_LONG).show();
		            	}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        });
	        
	        ImageButton btn_create3 = (ImageButton) findViewById(R.id.imageButton3);
	        btn_create3.setOnClickListener(new View.OnClickListener() {

	        	@Override
	            public void onClick(View view) {
	        		if (!bkp_selecionado.toString().equals("")){
						String nomeRestauracao = bkp_selecionado;
						DeleteBKP(MyConstant.storage+nomeRestauracao);
						ll.removeAllViewsInLayout();
						PreencherBKP(MyConstant.storage,NomeCopia);
						Toast.makeText(DbExportImport.this, "Excluido com sucesso!" , Toast.LENGTH_LONG).show();
						
					}else
					{
						Toast.makeText(DbExportImport.this, "Selecione uma cópia de segurança!" , Toast.LENGTH_LONG).show();
					}
	            }
	        });

	        
			ImageButton btn_mais = (ImageButton) findViewById(R.id.imageButton1);
			btn_mais.setOnTouchListener(new View.OnTouchListener() 
			{ 
				public boolean onTouch(View v, MotionEvent event) { switch (event.getAction()) { 
					case MotionEvent.ACTION_DOWN: {  
						((ImageButton) v).setImageResource(R.mipmap.bkp_mais2);
						v.invalidate(); 
						break;
					}
					case MotionEvent.ACTION_UP: 
					{ 
						((ImageButton) v).setImageResource(R.mipmap.bkp_mais);
						v.invalidate(); 
						break; 
					} 
				} 
				return false; 
				}
			});
			
			ImageButton btn_res = (ImageButton) findViewById(R.id.imageButton2);
			btn_res.setOnTouchListener(new View.OnTouchListener() 
			{ 
				public boolean onTouch(View v, MotionEvent event) { switch (event.getAction()) { 
					case MotionEvent.ACTION_DOWN: {  
						((ImageButton) v).setImageResource(R.mipmap.bkp_res2);
						v.invalidate(); 
						break;
					}
					case MotionEvent.ACTION_UP: 
					{ 
						((ImageButton) v).setImageResource(R.mipmap.bkp_res);
						v.invalidate(); 
						break; 
					} 
				} 
				return false; 
				}
			});
			
			ImageButton btn_del = (ImageButton) findViewById(R.id.imageButton3);
			btn_del.setOnTouchListener(new View.OnTouchListener() 
			{ 
				public boolean onTouch(View v, MotionEvent event) { switch (event.getAction()) { 
					case MotionEvent.ACTION_DOWN: {  
						((ImageButton) v).setImageResource(R.mipmap.bkp_del2);
						v.invalidate(); 
						break;
					}
					case MotionEvent.ACTION_UP: 
					{ 
						((ImageButton) v).setImageResource(R.mipmap.bkp_del);
						v.invalidate(); 
						break; 
					} 
				} 
				return false; 
				}
			});
	}
	
	private boolean DeleteBKP(String Origem){
	    final String inFileName = Origem;
	    File dbFile = new File(inFileName);
	    bkp_selecionado = "";
	    return  dbFile.delete();
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
	    bkp_selecionado = "";
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
	
	
	 private void PreencherBKP(String Origem,String inicioArquivo){
		 
		 try{
			 
		
		    File TODOS_File = new File(Origem);

		 
		    File list[] = TODOS_File.listFiles();
		    
		    EditText txtnumeroTotal = (EditText) findViewById(R.id.editEntrevistado);
		    
		    txtnumeroTotal.setText("Nenhum backup!");
		    
		    int numeroTotal = 0;
		    
		    for (int i =0; i < list.length; i++)
		    {
		    	int taminico = inicioArquivo.length();
		    	
		    	
		    	int tamArquivo = list[i].getName().length();
		    	
		    	if (tamArquivo > taminico) {

		    			
		    	if (inicioArquivo.toString().equals(list[i].getName().substring(0, taminico))) {
		    		
		    		numeroTotal = numeroTotal + 1;
		    		if (numeroTotal == 1){
		    			txtnumeroTotal.setText("Existe 1 backup!");
		    			
		    		}else
		    		{
		    			txtnumeroTotal.setText("Existem " + Integer.toString(numeroTotal) + " backups!");
		    		}
		    		
		    		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		    		ll.setOrientation(LinearLayout.VERTICAL);
				
		    		LinearLayout.LayoutParams paramsEdit = new LinearLayout.LayoutParams(  80, 80);
				
		    		ImageButton nImageButton = new ImageButton(this);
				 
		    		EditText nEditText = new EditText(this);
		    		nEditText.setText(list[i].getName());
		    		nEditText.setLeft(250);
		    		//nEditText.setEnabled(false);
				
		    		nEditText.setCursorVisible(false);
		    		nEditText.setFocusable(false);
		    		nEditText.setFocusableInTouchMode(false);
		    		//android:clickable="false"

	
		    		nEditText.setTag(list[i].getName());
		    		nImageButton.setTag(list[i].getName());
				
		    		nImageButton.setImageResource(R.mipmap.bkp_normal);
				
		    		nImageButton.setBackgroundDrawable(null);
		    		nImageButton.setOnClickListener(new View.OnClickListener() {

		            @Override
		            public void onClick(View view) {
		            String n = (((ImageButton) view).getTag().toString().substring(0, 3));
		            
		    		
		            	if (!n.toString().equals("SEL")) {
		            		bkp_selecionado = ((ImageButton) view).getTag().toString();
		            		String nbkp_selecionado = ((ImageButton) view).getTag().toString();
		            		tirarMarcacao();
		            		Marcacao(nbkp_selecionado);
		            	}
		  
		            }
		    		});
				
				
		    		nEditText.setOnClickListener(new View.OnClickListener() {

		            @Override
		            public void onClick(View view) {
		            	String n = (((EditText) view).getTag().toString().substring(0, 3));
		            	if (!n.toString().equals("SEL")) {
		            		bkp_selecionado = ((EditText) view).getTag().toString();
		            		String nbkp_selecionado = ((EditText) view).getTag().toString();
		            		tirarMarcacao();
		            		Marcacao(nbkp_selecionado);
		            	}
		            	}
		    		});
																
		    		ImageButtons.add(nImageButton); // adiciona a nova editText a lista.
				
		    		//f = ll.getId();
		    		ll.addView(nImageButton); // adiciona a editText ao ViewGroup
		    		//f = ll.getId();
		    		ll.addView(nEditText);
		    	}
		    }
		    }
		 }
		 catch (Exception e)
		 {
			 System.out.println(e.getMessage());
		 }
	 }

	 public void tirarMarcacao(){
		 try{
			 boolean nBoolean = false;
			 for (int i = 0; i < ll.getChildCount(); i++){
				 View child = ll.getChildAt(i);
				 if (child instanceof EditText){
					 EditText et = (EditText) child;
					 String n = (et.getTag().toString().substring(0, 3));
					 if (n.toString().equals("SEL")) {
						 et.setTag(et.getTag().toString().substring(3, et.getTag().toString().length()));
						 et.setTypeface(null, Typeface.NORMAL);
					 }
				 }
				 else if (child instanceof ImageButton){
					 ImageButton im = (ImageButton) child;
					 String n = (im.getTag().toString().substring(0, 3));
					 if (n.toString().equals("SEL")) {
						 im.setTag(im.getTag().toString().substring(3, im.getTag().toString().length()));
						 im.setImageResource(R.mipmap.bkp_normal);
					 }
				 }
			 }
		 }
		 catch (Exception e)
		 {
			 System.out.println(e.getMessage());
		 }
     }
	 
	 public void Marcacao(String nomeArquivo){
		 try{
	     	boolean nBoolean = false;
	     	for (int i = 0; i < ll.getChildCount(); i++){
	     		View child = ll.getChildAt(i);
	     		if (child instanceof EditText){
	     			EditText et = (EditText) child;
	     			if (nomeArquivo.equals(et.getTag().toString())){
	     				bkp_selecionado = et.getTag().toString();
	     				et.setTypeface(null, Typeface.BOLD);
	     				et.setTag("SEL" + et.getTag().toString());
	     			}
	     		}
	     		else if (child instanceof ImageButton){
	     			ImageButton im = (ImageButton) child;
	     			if (nomeArquivo.equals(im.getTag().toString())){
	     				bkp_selecionado = im.getTag().toString();
	     				im.setImageResource(R.mipmap.bkp_selecionado);
	     				im.setTag("SEL" + im.getTag().toString());
	     			}
	     		}
	  
	     	}

		 }
		 catch (Exception e)
		 {
			 System.out.println(e.getMessage());
		 }
	 }



	 
	 
}


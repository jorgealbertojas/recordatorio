package com.example.jorgealberto.researchmobile;

import java.util.ArrayList;

//import com.softjads.adapter.MinhaTAG;
//import com.softjads.adapter.modulo;
import com.example.jorgealberto.researchmobile.library.MinhaTAG;
//import com.example.jorgealberto.researchmobile.GPSTracker;
import com.example.jorgealberto.researchmobile.service.DB;
import com.example.jorgealberto.researchmobile.service.DataBase;
import com.example.jorgealberto.researchmobile.SQL.sql_create;
import com.example.jorgealberto.researchmobile.SQL.sql_delete;
import com.example.jorgealberto.researchmobile.SQL.sql_select;
import com.github.pinball83.maskededittext.MaskedEditText;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.NumberPicker.OnValueChangeListener;
/**
 * Created by jorgealberto on 22/09/2016.
 */
public class faz_opcao_dinamica extends Activity{

    private SQLiteDatabase bd;
    private DataBase nDataBase;

    private LinearLayout ll;
    private LinearLayout llPergunta;

    private int count = 0;

    private String subPerguntaAluno = "";

    private ArrayList<TextInputLayout> edits;
    private ArrayList<RadioButton> radionbuttons;
    private ArrayList<CheckBox> checkboxs;
    private ArrayList<NumberPicker> numberPickers;

    // novo Subpergunta
    private ArrayList<ImageButton> imageButtons;
    private ArrayList<Button> Buttons;


    private EditText TemEditText;
    private MaskedEditText TempMaskEditText;
    private ArrayList<String> MaxText;
    private ArrayList<String> TagText;


    private EditText editsPergunta;

    private int numero_pergunta = 0;
    private int numero_aluno = 0;
    private String nome_pergunta = "";

    private String nFONTE = "";

    private int AtualMax = 0;

    @Override
    protected void onDestroy() {

        finishWithResult();
        super.onDestroy();
    };

    private void finishWithResult()
    {
        Bundle conData = new Bundle();
        conData.putString("param_result", "bebeto");
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void insereRegistro(String pTag, String pvalue, int pPessoa) {
        ContentValues obj = new ContentValues();
        obj.put("ID_ALUNO", numero_aluno);
        obj.put("ID_PERGUNTA", numero_pergunta);
        obj.put("ID_OPCAO", pTag);
        obj.put("VALOR", pvalue);
        obj.put("ID_OPCAO_PESSOA", pPessoa);
        this.onInsert(this, obj, sql_create.TABLE_RESPOSTA);
    }

    private int locazinaEditText(EditText c) {
        c.getTag();
        int i = 0;
        int j = 0;
        for (i = 0; i < TagText.size(); i++) {
            if (TagText.get(i) == c.getTag().toString()) {
                j = i;
            }

        }
        return Integer.parseInt(MaxText.get(j));

    }

    private int locazinaEditText(MaskedEditText c) {
        c.getTag();
        int i = 0;
        int j = 0;
        for (i = 0; i < TagText.size(); i++) {
            if (TagText.get(i) == c.getTag().toString()) {
                j = i;
            }

        }
        return Integer.parseInt(MaxText.get(j));

    }

    public void VisivelLabelButton(String nOpcao, String valor, Boolean TornarVisivel, String ntipoValor) {
        try {

            for (int i = 0; i < ll.getChildCount(); i++) {
                View child = ll.getChildAt(i);
                if (child instanceof TextView) {
                    TextView et = (TextView) child;
                    if (et.getTag().equals(nOpcao)) {
                        if (TornarVisivel) {
                            if (ntipoValor.toString().equals("5")){
                                et.setText("    Data: " + valor);
                            }
                            else if (ntipoValor.toString().equals("8")){
                                et.setText("    Hora: " + valor);
                            }else if (ntipoValor.toString().equals("9")){
                                et.setText("    R$: " + valor);
                            }else{
                                et.setText(valor);
                            }

                            et.setVisibility(View.VISIBLE);
                        } else {
                            et.setVisibility(View.GONE);
                        }
                    }
                } else if (child instanceof Button) {
                    Button et = (Button) child;
                    if (et.getTag().equals(nOpcao)) {
                        if (TornarVisivel) {
                            et.setText("");
                            et.setVisibility(View.VISIBLE);
                        } else {
                            et.setVisibility(View.GONE);
                        }
                    }
                }
            }
        } catch (Throwable ex) {

        }
    }

    public void VisivleMasTexbox(String nOpcao) {
        try {
            for (int i = 0; i < ll.getChildCount(); i++) {
                View child = ll.getChildAt(i);
                if (child instanceof TextInputLayout) {
                    for (int y = 0; y < ((TextInputLayout) child).getChildCount(); y++) {
                        View child2 = ((TextInputLayout) child).getChildAt(y);
                        if (child2 instanceof EditText) {
                            EditText et = ((EditText) child2);
                            if (et.getTag().equals(nOpcao)) {

                                et.setVisibility(View.VISIBLE);

                            }

                        }
                    }
                }
            }
        } catch (Throwable ex) {

        }
    };




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        numero_pergunta = Integer.parseInt(extras.getString("numero_pergunta"));
        numero_aluno = Integer.parseInt(extras.getString("numero_aluno"));
        nFONTE = extras.getString("nFONTE");
        subPerguntaAluno = extras.getString("subPerguntaAluno");
        nome_pergunta = extras.getString("nome_pergunta");



        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.faz_opcao_dinamica);

        final EditText llPergunta = (EditText) findViewById(R.id.editEntrevistado);
        llPergunta.setText(nome_pergunta);
        llPergunta.refreshDrawableState();

        this.ll = (LinearLayout) this.findViewById(R.id.edits_ll);

        edits = new ArrayList<TextInputLayout>();
        radionbuttons = new ArrayList<RadioButton>();
        checkboxs = new ArrayList<CheckBox>();
        numberPickers = new ArrayList<NumberPicker>();
        imageButtons = new ArrayList<ImageButton>();
        Buttons = new ArrayList<Button>();

        MaxText = new ArrayList<String>();
        TagText = new ArrayList<String>();

        //if(savedInstanceState == null){
            montarOpcaoQuestionario();
        //}else if (savedInstanceState != null){
        //    MaxText = new ArrayList<String>();
        //    TagText = new ArrayList<String>();
        //}

        ImageButton btn_create = (ImageButton) findViewById(R.id.imageButton1);
        btn_create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    finish();
                } catch (Throwable e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });





        ImageButton volumemuteImageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        volumemuteImageButton1.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View v, MotionEvent event) { switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    ((ImageButton) v).setImageResource(R.mipmap.sair_02);
                    v.invalidate();
                    break;
                }
                case MotionEvent.ACTION_UP:
                {
                    ((ImageButton) v).setImageResource(R.mipmap.sair_02);
                    v.invalidate();
                    break;
                }
            }
                return false;
            }
        });

    }

    public int NovoID_SALTO() {
        Cursor cursorTMP = bd.rawQuery(sql_select.GET_MAX_OPCAO, null);
        cursorTMP.moveToFirst();
        int i = 0;
        if (cursorTMP.getCount() > 0) {
            i = cursorTMP.getInt(0);
        }
        return i + 1;
    }

    public void InsereSalto(String pSalto, String nItemPergunta) {
        ContentValues obj = new ContentValues();
        obj.put("ID_SALTO", NovoID_SALTO());
        obj.put("SALTO_PARA", pSalto);
        obj.put("PERGUNTA", numero_pergunta);
        obj.put("ITEM_PERGUNTA", nItemPergunta);
        this.onInsert(this, obj, sql_create.TABLE_SALTO);
    }

    protected void montarOpcaoQuestionario() {
        try {
            String TipoOpcao = "";

            EditText llPergunta = (EditText) findViewById(R.id.editEntrevistado);
            llPergunta.setText(nome_pergunta.toString());
            llPergunta.refreshDrawableState();
            int nMax;
            int nMin = 0;
            int igual = 0;

            nDataBase = new DataBase(this);
            bd = nDataBase.getReadableDatabase();

            Cursor cursor = bd.rawQuery(sql_select.GET_OPCAO, new String[]{Integer.toString(numero_pergunta)});
            cursor.moveToFirst();


            while (!cursor.isAfterLast()) {
                try {
                    // RADIONBUTTON
                    if (cursor.getInt(2) == 0) {
                        if ((cursor.getInt(0)) != igual) {
                            igual = cursor.getInt(0);
                            count++;
                            LinearLayout.LayoutParams params;
                            //if (modulo.numero_pergunta == 9 ){
                            params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                            //} else {
                            //	 params = new LinearLayout.LayoutParams(   LayoutParams.FILL_PARENT, (int) 75d);
                            //}

                            RadioButton radionbutton = new RadioButton(this);
                            radionbutton.setText(cursor.getString(3));
                            radionbutton.setTag(cursor.getString(0));
                            radionbutton.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);

                            if (nFONTE.equals("P")) {
                                radionbutton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                            } else if (nFONTE.equals("M")) {
                                radionbutton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                            } else if (nFONTE.equals("G")) {
                                radionbutton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                            }

                            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                                    .hideSoftInputFromWindow(radionbutton.getWindowToken(), 0);

                            radionbutton.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_SUBPERGUNTA, new String[]{Integer.toString(numero_aluno), Integer.toString(numero_pergunta), (subPerguntaAluno) });
                                    bd.execSQL(sql_delete.DEL_SALTO_PERGUNTA, new String[]{Integer.toString(numero_pergunta)});
                                    insereRegistro(((RadioButton) view).getTag().toString(), "", Integer.parseInt(subPerguntaAluno));
                                    Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{Integer.toString(numero_pergunta), ((RadioButton) view).getTag().toString()});
                                    cursorSALTO.moveToFirst();
                                    cursorSALTO.getCount();

                                    if (cursorSALTO.getCount() > 0) {
                                        String n = cursorSALTO.getString(6);
                                        if (cursorSALTO.getInt(6) > 0) {
                                            InsereSalto(cursorSALTO.getString(6), ((RadioButton) view).getTag().toString());
                                        }
                                    }


/*                                    if (numero_pergunta == 35){
                                        Cursor cursorSEXO = bd.rawQuery(" select id_opcao from resposta where id_opcao = 83 and id_aluno = ? and id_opcao_pessoa = ? ", new String[]{Integer.toString(numero_aluno),subPerguntaAluno });
                                        cursorSEXO.moveToFirst();
                                        if (cursorSEXO.getCount() > 0){

                                        }else{
                                            InsereSalto("37", ((RadioButton) view).getTag().toString());
                                        }



                                    }


                                    if (numero_pergunta == 43){
                                        Cursor cursorSEXO = bd.rawQuery(" select id_opcao from resposta where (Cast(VALOR  AS INTEGER)  < 10) AND id_opcao = 84 and id_aluno = ? and id_opcao_pessoa = ? ", new String[]{Integer.toString(numero_aluno),subPerguntaAluno });
                                        cursorSEXO.moveToFirst();
                                        if (cursorSEXO.getCount() > 0){
                                            InsereSalto("46", ((RadioButton) view).getTag().toString());
                                        }



                                    }*/

                                    for (int i = 0; i < radionbuttons.size(); i++) {
                                        radionbuttons.get(i).setChecked(false);
                                    }

                                    //for (int i = 0; i < checkboxs.size(); i++) {
                                    //    checkboxs.get(i).setChecked(false);
                                    //}


                                    ((RadioButton) view).setChecked(true);
                                }
                            });

                            radionbuttons.add(radionbutton); // adiciona a nova editText a lista.

                            ll.addView(radionbutton, params); // adiciona a editText ao ViewGroup
                        }
                    }

                    // CHECK
                    else if (cursor.getInt(2) == 2) {

                        if ((cursor.getInt(0)) != igual) {
                            igual = cursor.getInt(0);
                            count++;
                            LinearLayout.LayoutParams params;
                            //if (modulo.numero_pergunta == 9 ){
                            params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                            //} else {
                            // params = new LinearLayout.LayoutParams(   LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                            //}

                            CheckBox checkbox = new CheckBox(this);
                            checkbox.setTag(cursor.getString(0));
                            checkbox.setText(cursor.getString(3));
                            checkbox.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);

                            if (nFONTE.equals("P")) {
                                checkbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                            } else if (nFONTE.equals("M")) {
                                checkbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                            } else if (nFONTE.equals("G")) {
                                checkbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                            }

                            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                                    .hideSoftInputFromWindow(checkbox.getWindowToken(), 0);

                            checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                    bd.execSQL(sql_delete.DEL_SALTO_PERGUNTA, new String[]{Integer.toString(numero_pergunta)});


                                    boolean pegarEstado = isChecked;

                                    Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{Integer.toString(numero_pergunta), ((CheckBox) buttonView).getTag().toString()});
                                    cursorSALTO.moveToFirst();
                                    cursorSALTO.getCount();

                                    //for (int i = 0; i < radionbuttons.size(); i++) {
                                    //    radionbuttons.get(i).setChecked(false);
                                    //}

                                    if (isChecked) {
                                        bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ID_OPCAO_SUBPERGUNTA, new String[]{Integer.toString(numero_aluno), Integer.toString(numero_pergunta), ((CheckBox) buttonView).getTag().toString(), subPerguntaAluno});
                                        insereRegistro(buttonView.getTag().toString(), "", Integer.parseInt(subPerguntaAluno));

                                        if (cursorSALTO.getCount() > 0) {
                                            String n = cursorSALTO.getString(6);
                                            if (cursorSALTO.getInt(6) > 0) {
                                                InsereSalto(cursorSALTO.getString(6), ((CheckBox) buttonView).getTag().toString());
                                            }
                                        }
                                    } else {
                                        bd.execSQL(sql_delete.DEL_SALTO_PERGUNTA, new String[]{Integer.toString(numero_pergunta)});
                                        bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ID_OPCAO_SUBPERGUNTA, new String[]{Integer.toString(numero_aluno), Integer.toString(numero_pergunta), ((CheckBox) buttonView).getTag().toString(), subPerguntaAluno});
                                        if (cursorSALTO.getCount() > 0) {
                                            String n = cursorSALTO.getString(6);
                                            if (cursorSALTO.getInt(6) > 0) {
                                                bd.execSQL(sql_delete.DEL_SALTO, new String[]{cursorSALTO.getString(1)});
                                            }
                                        }
                                    }
                                }
                            });

                            checkboxs.add(checkbox); // adiciona a nova editText a lista.
                            ll.addView(checkbox, params); // adiciona a editText ao ViewGroup
                        }
                    }
                    // numero 4 � o numero normal
                    else if (cursor.getInt(2) == 4) {
                        if ((cursor.getInt(0)) != igual) {
                            nMin = cursor.getInt(4);
                            nMax = cursor.getInt(1);

                            igual = cursor.getInt(0);
                            count++;

                            TextInputLayout editAnimation = new TextInputLayout(this);
                            editAnimation.setTag(cursor.getString(0));

                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                            EditText edit = new EditText(this);
                            edit.setInputType(InputType.TYPE_CLASS_NUMBER);

                            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                                    .hideSoftInputFromWindow(edit.getWindowToken(), 0);

                            MinhaTAG asTags = new MinhaTAG();

                            edit.setTag(cursor.getString(0));
                            edit.setHint(cursor.getString(3));
                            edit.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                            edit.setBackgroundResource(R.drawable.rounded_corner);

                            if (nFONTE.equals("P")) {
                                edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                            } else if (nFONTE.equals("M")) {
                                edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                            } else if (nFONTE.equals("G")) {
                                edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                            }

                            TemEditText = edit;
                            TagText.add(edit.getTag().toString());
                            MaxText.add(Integer.toString(nMax));
                            AtualMax = nMax;


                            edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                @Override
                                public void onFocusChange(View v, boolean hasFocus) {
                                    if (hasFocus) {
                                        AtualMax = locazinaEditText((EditText) v);
                                        TemEditText = ((EditText) v);


                                    } else {
                                        //Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            edit.setOnTouchListener(new View.OnTouchListener() {
                                public boolean onTouch(View v, MotionEvent event) {
                                    AtualMax = locazinaEditText((EditText) v);
                                    TemEditText = ((EditText) v);
                                    ((EditText) v).setInputType(InputType.TYPE_CLASS_NUMBER);
                                    return false;
                                }
                            });

                            TextWatcher textWatcher = new TextWatcher() {
                                public void afterTextChanged(Editable s) {

                                    bd.execSQL(sql_delete.DEL_SALTO_PERGUNTA, new String[]{Integer.toString(numero_pergunta)});

                                    bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_OPCAO_SUBPERGUNTA, new String[]{Integer.toString(numero_aluno), Integer.toString(numero_pergunta), (TemEditText.getTag().toString()), subPerguntaAluno});
                                    if (s.length() > 0) {
                                        insereRegistro((TemEditText.getTag().toString()), s.toString(), Integer.parseInt(subPerguntaAluno));

                                        Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{Integer.toString(numero_pergunta), TemEditText.getTag().toString()});
                                        cursorSALTO.moveToFirst();
                                        cursorSALTO.getCount();
                                        if (cursorSALTO.getCount() > 0) {
                                            String n = cursorSALTO.getString(6);
                                            if (cursorSALTO.getInt(6) > 0) {
                                                InsereSalto(cursorSALTO.getString(6), TemEditText.getTag().toString());
                                            }
                                        }
                                    }
                                    try {
                                        if (!((s.toString().equals("")))) {

                                            if (s.toString().length() > 1) {
                                                String gggg = s.toString().substring(0, 1).toString();
                                                if (s.toString().substring(0, 1).toString().equals("0")) {
                                                    s.clear();
                                                }
                                            }

                                            if (s.toString().length() > 6) {
                                                String tamanho = Integer.toString(AtualMax);
                                                if (tamanho.length() < s.toString().length()) {
                                                    Toast.makeText(faz_opcao_dinamica.this, "Atenção! Máximo permitido: " + tamanho.length(), Toast.LENGTH_LONG).show();
                                                    s.clear();
                                                }
                                            } else if (Integer.parseInt(s.toString()) > (AtualMax)) {
                                                Toast.makeText(faz_opcao_dinamica.this, "Atenção! Máximo permitido:" + Integer.toString(AtualMax), Toast.LENGTH_LONG).show();
                                                s.clear();
                                            }
                                        }
                                    } catch (Exception e) {

                                    }

                                }

                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }


                            };
                            edit.addTextChangedListener(textWatcher);
                            editAnimation.addView(edit, params);
                            edits.add(editAnimation); // adiciona a nova editText a lista.
                            ll.addView(editAnimation, params); // adiciona a editText ao ViewGroup
                        }
                    }

                    // TEXTO
                    else if (cursor.getInt(2) == 3) {
                        if ((cursor.getInt(0)) != igual) {
                            nMin = cursor.getInt(4);
                            nMax = cursor.getInt(1);

                            igual = cursor.getInt(0);
                            count++;


                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                            EditText edit = new EditText(this);
                            MinhaTAG asTags = new MinhaTAG();


                            TextInputLayout editAnimation = new TextInputLayout(this);
                            editAnimation.setTag(cursor.getString(0));

                            edit.setTag(cursor.getString(0));
                            edit.setHint(cursor.getString(3));
                            edit.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                            edit.setBackgroundResource(R.drawable.rounded_corner);
                            // edit.setHeight(20);
                            //edit.setGravity(Gravity.CENTER_HORIZONTAL);

                            if (nFONTE.equals("P")) {
                                edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                            } else if (nFONTE.equals("M")) {
                                edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                            } else if (nFONTE.equals("G")) {
                                edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                            }

                            edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(nMax)});

                            Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{Integer.toString(numero_pergunta), edit.getTag().toString()});
                            cursorSALTO.moveToFirst();
                            cursorSALTO.getCount();

                            if (cursorSALTO.getCount() > 0) {
                                String n = cursorSALTO.getString(6);
                                if (cursorSALTO.getInt(6) > 0) {
                                    InsereSalto(cursorSALTO.getString(6), edit.getTag().toString());
                                }
                            }

                            TemEditText = edit;

                            edit.setInputType(InputType.TYPE_NULL);


/*                            if (numero_pergunta == 44 || numero_pergunta == 45 ){
                                edit.setInputType(InputType.TYPE_CLASS_NUMBER);
                            }*/


                            TagText.add(edit.getTag().toString());
                            MaxText.add(Integer.toString(nMax));
                            AtualMax = nMax;

                            edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                @Override
                                public void onFocusChange(View v, boolean hasFocus) {
                                    if (hasFocus) {
                                        AtualMax = locazinaEditText((EditText) v);
                                        TemEditText = ((EditText) v);
                                        //llPergunta.setText(valorPergunta + " \n" + ((EditText)v).getHint().toString() );
                                    } else {
                                        //Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            edit.setOnTouchListener(new View.OnTouchListener() {
                                public boolean onTouch(View v, MotionEvent event) {
                                    AtualMax = locazinaEditText((EditText) v);
                                    TemEditText = ((EditText) v);
                                    ((EditText) v).setInputType(InputType.TYPE_CLASS_TEXT);

                                    return false;
                                }
                            });


                            TextWatcher textWatcher = new TextWatcher() {
                                public void afterTextChanged(Editable s) {

                                    bd.execSQL(sql_delete.DEL_SALTO_PERGUNTA, new String[]{Integer.toString(numero_pergunta)});

                                    bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_OPCAO_SUBPERGUNTA, new String[]{Integer.toString(numero_aluno), Integer.toString(numero_pergunta), (TemEditText.getTag().toString()), subPerguntaAluno});
                                    if (s.length() > 0) {
                                        insereRegistro((TemEditText.getTag().toString()), s.toString(), Integer.parseInt(subPerguntaAluno));
                                        Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{Integer.toString(numero_pergunta), TemEditText.getTag().toString()});
                                        cursorSALTO.moveToFirst();
                                        cursorSALTO.getCount();
                                        if (cursorSALTO.getCount() > 0) {
                                            String n = cursorSALTO.getString(6);
                                            if (cursorSALTO.getInt(6) > 0) {
                                                InsereSalto(cursorSALTO.getString(6), TemEditText.getTag().toString());
                                            }
                                        }
                                    }
                                    if (!((s.toString().equals("")))) {
                                        if (s.toString().length() + 1 > AtualMax) {
                                            Toast.makeText(faz_opcao_dinamica.this, "Atenção! Máximo permitido:" + Integer.toString(AtualMax), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }

                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }
                            };
                            edit.addTextChangedListener(textWatcher);
                            editAnimation.addView(edit, params);
                            edits.add(editAnimation); // adiciona a nova editText a lista.
                            ll.addView(editAnimation, params); // adiciona a editText ao ViewGroup

                        }


                        ///	DATA MASCARA
                    } else if (cursor.getInt(2) == 5) {
                        if ((cursor.getInt(0)) != igual) {
                            nMin = cursor.getInt(4);
                            nMax = cursor.getInt(1);

                            igual = cursor.getInt(0);
                            count++;


                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                            //MaskedEditText edit = new MaskedEditText(this);
                            MaskedEditText edit = new MaskedEditText(this,
                                    "**/**/****",
                                    "*",
                                    getResources().getDrawable(R.mipmap.begin_1),
                                    new MaskedEditText.MaskIconCallback() {
                                        @Override
                                        public void onIconPushed() {
                                            System.out.println("Icon pushed");

                                            //Invoke here contact list or just clear input

                                        }
                                    });

                            MinhaTAG asTags = new MinhaTAG();


                            TextInputLayout editAnimation = new TextInputLayout(this);
                            editAnimation.setTag(cursor.getString(0));

                            edit.setTag(cursor.getString(0));


                            LinearLayout.LayoutParams paramsTextView = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, (int) 40d);
                            LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams((int) 40d, (int) 40d);

                            TextView nTextView = new TextView(this);
                            nTextView.setText("eeee");
                            nTextView.setVisibility(View.GONE);
                            nTextView.setTag(cursor.getString(0));

                            Button nButton = new Button(this);
                            nButton.setBackgroundResource(R.mipmap.cancel);
                            nButton.setVisibility(View.GONE);
                            nButton.setText(" ");
                            nButton.setTag(cursor.getString(0));

                            nButton.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    VisivelLabelButton(((Button) view).getTag().toString(), "", false, "0");
                                    VisivleMasTexbox(((Button) view).getTag().toString());
                                }
                            });

                            edit.setHint(cursor.getString(3));
                            edit.setInputType(InputType.TYPE_CLASS_NUMBER);
                            edit.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);


                            //edit.setBackgroundResource(R.drawable.rounded_corner);
                            // edit.setHeight(20);
                            //edit.setGravity(Gravity.CENTER_HORIZONTAL);

                            if (nFONTE.equals("P")) {
                                edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                            } else if (nFONTE.equals("M")) {
                                edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                            } else if (nFONTE.equals("G")) {
                                edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                            }

                            //edit.setFilters(new InputFilter[] {new InputFilter.LengthFilter(100)});

                            Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{Integer.toString(numero_pergunta), edit.getTag().toString()});
                            cursorSALTO.moveToFirst();
                            cursorSALTO.getCount();

                            if (cursorSALTO.getCount() > 0) {
                                String n = cursorSALTO.getString(6);
                                if (cursorSALTO.getInt(6) > 0) {
                                    InsereSalto(cursorSALTO.getString(6), edit.getTag().toString());
                                }
                            }

                            TemEditText = edit;
                            TagText.add(edit.getTag().toString());
                            MaxText.add(Integer.toString(100));
                            AtualMax = 100;

                            edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                @Override
                                public void onFocusChange(View v, boolean hasFocus) {
                                    if (hasFocus) {
                                        AtualMax = locazinaEditText((MaskedEditText) v);
                                        TempMaskEditText = ((MaskedEditText) v);
                                        //llPergunta.setText(valorPergunta + " \n" + ((EditText)v).getHint().toString() );
                                    } else {
                                        //Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            edit.setOnTouchListener(new View.OnTouchListener() {
                                public boolean onTouch(View v, MotionEvent event) {
                                    AtualMax = locazinaEditText((MaskedEditText) v);
                                    TempMaskEditText = ((MaskedEditText) v);
                                    //((EditText)v).setInputType(InputType.TYPE_CLASS_NUMBER);
                                    return false;
                                }
                            });


                            TextWatcher textWatcher = new TextWatcher() {
                                public void afterTextChanged(Editable s) {

                                    bd.execSQL(sql_delete.DEL_SALTO_PERGUNTA, new String[]{Integer.toString(numero_pergunta)});

                                    for (int i = 0; i < radionbuttons.size(); i++) {
                                        bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ID_OPCAO, new String[]{Integer.toString(numero_aluno), Integer.toString(numero_pergunta), radionbuttons.get(i).getTag().toString()});

                                        radionbuttons.get(i).setChecked(false);
                                    }
                                    bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_OPCAO_SUBPERGUNTA, new String[]{Integer.toString(numero_aluno), Integer.toString(numero_pergunta), (TempMaskEditText.getTag().toString()), subPerguntaAluno});
                                    if (s.length() > 0) {
                                        insereRegistro((TempMaskEditText.getTag().toString()), s.toString(), Integer.parseInt(subPerguntaAluno));
                                        Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{Integer.toString(numero_pergunta), TempMaskEditText.getTag().toString()});
                                        cursorSALTO.moveToFirst();
                                        cursorSALTO.getCount();
                                        if (cursorSALTO.getCount() > 0) {
                                            String n = cursorSALTO.getString(6);
                                            if (cursorSALTO.getInt(6) > 0) {
                                                InsereSalto(cursorSALTO.getString(6), TempMaskEditText.getTag().toString());
                                            }
                                        }
                                    }
                                    if (!((s.toString().equals("")))) {
                                        if (s.toString().length() + 1 > AtualMax) {
                                            Toast.makeText(faz_opcao_dinamica.this, "Atenção! Máximo permitido:" + Integer.toString(AtualMax), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }

                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }
                            };


                            edit.addTextChangedListener(textWatcher);
                            editAnimation.addView(edit, params);
                            edits.add(editAnimation); // adiciona a nova editText a lista.

                            ll.addView(nTextView, paramsTextView);
                            ll.addView(nButton, paramsButton);
                            ll.addView(editAnimation, params); // adiciona a editText ao ViewGroup

                        }
                    }

                    /// HORA MASCARA
                    else if (cursor.getInt(2) == 8) {
                        if ((cursor.getInt(0)) != igual) {
                            nMin = cursor.getInt(4);
                            nMax = cursor.getInt(1);

                            igual = cursor.getInt(0);
                            count++;


                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                            //MaskedEditText edit = new MaskedEditText(this);
                            MaskedEditText edit = new MaskedEditText(this,
                                    "**:**",
                                    "*",
                                    getResources().getDrawable(R.mipmap.begin_1),
                                    new MaskedEditText.MaskIconCallback() {
                                        @Override
                                        public void onIconPushed() {
                                            System.out.println("Icon pushed");

                                            //Invoke here contact list or just clear input

                                        }
                                    });

                            MinhaTAG asTags = new MinhaTAG();


                            TextInputLayout editAnimation = new TextInputLayout(this);
                            editAnimation.setTag(cursor.getString(0));

                            edit.setTag(cursor.getString(0));


                            LinearLayout.LayoutParams paramsTextView = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, (int) 40d);
                            LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams((int) 40d, (int) 40d);

                            TextView nTextView = new TextView(this);
                            nTextView.setText("eeee");
                            nTextView.setVisibility(View.GONE);
                            nTextView.setTag(cursor.getString(0));

                            Button nButton = new Button(this);
                            nButton.setBackgroundResource(R.mipmap.cancel);
                            nButton.setVisibility(View.GONE);
                            nButton.setText(" ");
                            nButton.setTag(cursor.getString(0));

                            nButton.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    VisivelLabelButton(((Button) view).getTag().toString(), "", false, "0");
                                    VisivleMasTexbox(((Button) view).getTag().toString());
                                }
                            });

                            edit.setHint(cursor.getString(3));
                            edit.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                            edit.setBackgroundResource(R.drawable.rounded_corner);
                            edit.setInputType(InputType.TYPE_CLASS_NUMBER);


                            //edit.setBackgroundResource(R.drawable.rounded_corner);
                            // edit.setHeight(20);
                            //edit.setGravity(Gravity.CENTER_HORIZONTAL);

                            if (nFONTE.equals("P")) {
                                edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                            } else if (nFONTE.equals("M")) {
                                edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                            } else if (nFONTE.equals("G")) {
                                edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                            }

                            //edit.setFilters(new InputFilter[] {new InputFilter.LengthFilter(100)});

                            Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{Integer.toString(numero_pergunta), edit.getTag().toString()});
                            cursorSALTO.moveToFirst();
                            cursorSALTO.getCount();

                            if (cursorSALTO.getCount() > 0) {
                                String n = cursorSALTO.getString(6);
                                if (cursorSALTO.getInt(6) > 0) {
                                    InsereSalto(cursorSALTO.getString(6), edit.getTag().toString());
                                }
                            }

                            TemEditText = edit;
                            TagText.add(edit.getTag().toString());
                            MaxText.add(Integer.toString(100));
                            AtualMax = 100;

                            edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                @Override
                                public void onFocusChange(View v, boolean hasFocus) {
                                    if (hasFocus) {
                                        AtualMax = locazinaEditText((MaskedEditText) v);
                                        TempMaskEditText = ((MaskedEditText) v);
                                        //llPergunta.setText(valorPergunta + " \n" + ((EditText)v).getHint().toString() );
                                    } else {
                                        //Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            edit.setOnTouchListener(new View.OnTouchListener() {
                                public boolean onTouch(View v, MotionEvent event) {
                                    AtualMax = locazinaEditText((MaskedEditText) v);
                                    TempMaskEditText = ((MaskedEditText) v);

                                    return false;
                                }
                            });


                            TextWatcher textWatcher = new TextWatcher() {
                                public void afterTextChanged(Editable s) {

                                    bd.execSQL(sql_delete.DEL_SALTO_PERGUNTA, new String[]{Integer.toString(numero_pergunta)});

                                    for (int i = 0; i < radionbuttons.size(); i++) {

                                        bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ID_OPCAO, new String[]{Integer.toString(numero_aluno), Integer.toString(numero_pergunta), radionbuttons.get(i).getTag().toString()});

                                        radionbuttons.get(i).setChecked(false);
                                    }
                                    bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_OPCAO_SUBPERGUNTA, new String[]{Integer.toString(numero_aluno), Integer.toString(numero_pergunta), (TempMaskEditText.getTag().toString()), subPerguntaAluno});
                                    if (s.length() > 0) {
                                        insereRegistro((TempMaskEditText.getTag().toString()), s.toString(), Integer.parseInt(subPerguntaAluno));
                                        Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{Integer.toString(numero_pergunta), TempMaskEditText.getTag().toString()});
                                        cursorSALTO.moveToFirst();
                                        cursorSALTO.getCount();
                                        if (cursorSALTO.getCount() > 0) {
                                            String n = cursorSALTO.getString(6);
                                            if (cursorSALTO.getInt(6) > 0) {
                                                InsereSalto(cursorSALTO.getString(6), TempMaskEditText.getTag().toString());
                                            }
                                        }
                                    }
                                    if (!((s.toString().equals("")))) {
                                        if (s.toString().length() + 1 > AtualMax) {
                                            Toast.makeText(faz_opcao_dinamica.this, "Atenção! Máximo permitido:" + Integer.toString(AtualMax), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }

                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }
                            };


                            edit.addTextChangedListener(textWatcher);
                            editAnimation.addView(edit, params);
                            edits.add(editAnimation); // adiciona a nova editText a lista.

                            ll.addView(nTextView, paramsTextView);
                            ll.addView(nButton, paramsButton);
                            ll.addView(editAnimation, params); // adiciona a editText ao ViewGroup

                        }
                    }


                    /// R$ MASCARA
                    else if (cursor.getInt(2) == 9) {
                        if ((cursor.getInt(0)) != igual) {
                            nMin = cursor.getInt(4);
                            nMax = cursor.getInt(1);

                            igual = cursor.getInt(0);
                            count++;


                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, (int) 100d);
                            //MaskedEditText edit = new MaskedEditText(this);
                            MaskedEditText edit = new MaskedEditText(this,
                                    "R$ ***.***,**",
                                    "*",
                                    getResources().getDrawable(R.mipmap.begin_1),
                                    new MaskedEditText.MaskIconCallback() {
                                        @Override
                                        public void onIconPushed() {
                                            System.out.println("Icon pushed");

                                            //Invoke here contact list or just clear input

                                        }
                                    });

                            MinhaTAG asTags = new MinhaTAG();


                            TextInputLayout editAnimation = new TextInputLayout(this);
                            editAnimation.setTag(cursor.getString(0));

                            edit.setTag(cursor.getString(0));


                            LinearLayout.LayoutParams paramsTextView = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, (int) 40d);
                            LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams((int) 40d, (int) 40d);

                            TextView nTextView = new TextView(this);
                            nTextView.setText("eeee");
                            nTextView.setVisibility(View.GONE);
                            nTextView.setTag(cursor.getString(0));

                            Button nButton = new Button(this);
                            nButton.setBackgroundResource(R.mipmap.cancel);
                            nButton.setVisibility(View.GONE);
                            nButton.setText(" ");
                            nButton.setTag(cursor.getString(0));

                            nButton.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    VisivelLabelButton(((Button) view).getTag().toString(), "", false, "0");
                                    VisivleMasTexbox(((Button) view).getTag().toString());
                                }
                            });

                            edit.setHint(cursor.getString(3));
                            edit.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                            edit.setBackgroundResource(R.drawable.rounded_corner);
                            edit.setInputType(InputType.TYPE_CLASS_NUMBER);


                            //edit.setBackgroundResource(R.drawable.rounded_corner);
                            // edit.setHeight(20);
                            //edit.setGravity(Gravity.CENTER_HORIZONTAL);

                            if (nFONTE.equals("P")) {
                                edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                            } else if (nFONTE.equals("M")) {
                                edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                            } else if (nFONTE.equals("G")) {
                                edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                            }

                            //edit.setFilters(new InputFilter[] {new InputFilter.LengthFilter(100)});

                            Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{Integer.toString(numero_pergunta), edit.getTag().toString()});
                            cursorSALTO.moveToFirst();
                            cursorSALTO.getCount();

                            if (cursorSALTO.getCount() > 0) {
                                String n = cursorSALTO.getString(6);
                                if (cursorSALTO.getInt(6) > 0) {
                                    InsereSalto(cursorSALTO.getString(6), edit.getTag().toString());
                                }
                            }

                            TemEditText = edit;
                            TagText.add(edit.getTag().toString());
                            MaxText.add(Integer.toString(100));
                            AtualMax = 100;

                            edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                @Override
                                public void onFocusChange(View v, boolean hasFocus) {
                                    if (hasFocus) {
                                        AtualMax = locazinaEditText((MaskedEditText) v);
                                        TempMaskEditText = ((MaskedEditText) v);
                                        //llPergunta.setText(valorPergunta + " \n" + ((EditText)v).getHint().toString() );
                                    } else {
                                        //Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            edit.setOnTouchListener(new View.OnTouchListener() {
                                public boolean onTouch(View v, MotionEvent event) {
                                    AtualMax = locazinaEditText((MaskedEditText) v);
                                    TempMaskEditText = ((MaskedEditText) v);
                                    return false;
                                }
                            });


                            TextWatcher textWatcher = new TextWatcher() {
                                public void afterTextChanged(Editable s) {

                                    bd.execSQL(sql_delete.DEL_SALTO_PERGUNTA, new String[]{Integer.toString(numero_pergunta)});


                                    for (int i = 0; i < radionbuttons.size(); i++) {

                                        bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ID_OPCAO, new String[]{Integer.toString(numero_aluno), Integer.toString(numero_pergunta), radionbuttons.get(i).getTag().toString()});

                                        radionbuttons.get(i).setChecked(false);
                                    }
                                    bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_OPCAO_SUBPERGUNTA, new String[]{Integer.toString(numero_aluno), Integer.toString(numero_pergunta), (TempMaskEditText.getTag().toString()), subPerguntaAluno});
                                    if (s.length() > 0) {
                                        insereRegistro((TempMaskEditText.getTag().toString()), s.toString(), Integer.parseInt(subPerguntaAluno));
                                        Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{Integer.toString(numero_pergunta), TempMaskEditText.getTag().toString()});
                                        cursorSALTO.moveToFirst();
                                        cursorSALTO.getCount();
                                        if (cursorSALTO.getCount() > 0) {
                                            String n = cursorSALTO.getString(6);
                                            if (cursorSALTO.getInt(6) > 0) {
                                                InsereSalto(cursorSALTO.getString(6), TempMaskEditText.getTag().toString());
                                            }
                                        }
                                    }
                                    if (!((s.toString().equals("")))) {
                                        if (s.toString().length() + 1 > AtualMax) {
                                            Toast.makeText(faz_opcao_dinamica.this, "Atenção! Máximo permitido:" + Integer.toString(AtualMax), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }

                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }
                            };


                            edit.addTextChangedListener(textWatcher);
                            editAnimation.addView(edit, params);
                            edits.add(editAnimation); // adiciona a nova editText a lista.

                            ll.addView(nTextView, paramsTextView);
                            ll.addView(nButton, paramsButton);
                            ll.addView(editAnimation, params); // adiciona a editText ao ViewGroup

                        }
                    }


                    // NUMERO
                    else if (cursor.getInt(2) == 1) {
                        if ((cursor.getInt(0)) != igual) {
                            nMin = cursor.getInt(4);
                            nMax = cursor.getInt(1);

                            igual = cursor.getInt(0);
                            count++;
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                            NumberPicker number = new NumberPicker(this);
                            MinhaTAG asTags = new MinhaTAG();
                            asTags.nTAG = cursor.getInt(0);
                            asTags.nTAGMIN = nMin;
                            asTags.nTAGMAX = nMax;

                            number.setTag(cursor.getString(0));
                            number.setValue(0);
                            number.setMinValue(nMin);
                            number.setMaxValue(nMax);
                            number.setWrapSelectorWheel(true);

                            insereRegistro(cursor.getString(0), Integer.toString(nMin), Integer.parseInt(subPerguntaAluno));

                            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                                    .hideSoftInputFromWindow(number.getWindowToken(), 0);

                            number.setOnValueChangedListener(new OnValueChangeListener() {

                                @Override
                                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                    // TODO Auto-generated method stub
                                    try {
                                        String Old = "Old Value : ";
                                        String New = "New Value : ";

                                        MinhaTAG asTags = new MinhaTAG();
                                        int ntag = asTags.nTAG;
                                        int nMin = asTags.nTAGMIN;
                                        int nMax = asTags.nTAGMAX;

                                        bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA, new String[]{Integer.toString(numero_aluno), Integer.toString(numero_pergunta), subPerguntaAluno});
                                        insereRegistro(Integer.toString(ntag), Integer.toString(picker.getValue()), Integer.parseInt(subPerguntaAluno));

                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            });

                            numberPickers.add(number); // adiciona a nova editText a lista.
                            ll.addView(number, params); // adiciona a editText ao ViewGroup
                        }
                    }


                    // TEXTO especial
                    else {
                        if ((cursor.getInt(0)) != igual) {
                            nMin = cursor.getInt(4);
                            nMax = cursor.getInt(1);

                            igual = cursor.getInt(0);
                            count++;
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                            EditText edit = new EditText(this);
                            MinhaTAG asTags = new MinhaTAG();

                            edit.setTag(cursor.getString(0));
                            edit.setHint(cursor.getString(3));
                            edit.setVisibility(View.INVISIBLE);
                            edit.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);

                            if (nFONTE.equals("P")) {
                                edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                            } else if (nFONTE.equals("M")) {
                                edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                            } else if (nFONTE.equals("G")) {
                                edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                            }

                            edit.setHeight(0);
                            edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(nMax)});

                            TemEditText = edit;
                            edit.setInputType(InputType.TYPE_NULL);

                            TagText.add(edit.getTag().toString());
                            MaxText.add(Integer.toString(nMax));
                            AtualMax = nMax;

                            edit.setOnTouchListener(new View.OnTouchListener() {
                                public boolean onTouch(View v, MotionEvent event) {
                                    AtualMax = locazinaEditText((EditText) v);
                                    TemEditText = ((EditText) v);
                                    ((EditText) v).setInputType(InputType.TYPE_CLASS_TEXT);
                                    return false;
                                }
                            });

                            TextWatcher textWatcher = new TextWatcher() {
                                public void afterTextChanged(Editable s) {

                                    bd.execSQL(sql_delete.DEL_SALTO_PERGUNTA, new String[]{Integer.toString(numero_pergunta)});

                                    bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_OPCAO_SUBPERGUNTA, new String[]{Integer.toString(numero_aluno), Integer.toString(numero_pergunta), (TemEditText.getTag().toString()), subPerguntaAluno});
                                    if (s.length() > 0) {
                                        insereRegistro((TemEditText.getTag().toString()), s.toString(), Integer.parseInt(subPerguntaAluno));
                                    }
                                    if (!((s.toString().equals("")))) {
                                        if (s.toString().length() + 1 > AtualMax) {
                                            Toast.makeText(faz_opcao_dinamica.this, "Atenção! Máximo permitido:" + Integer.toString(AtualMax), Toast.LENGTH_LONG).show();
                                        }
                                    }

                                }

                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }
                            };
                            edit.addTextChangedListener(textWatcher);
                            //edits.add(edit); // adiciona a nova editText a lista.
                            ll.addView(edit, params); // adiciona a editText ao ViewGroup
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                cursor.moveToNext();
            }


        }catch (Throwable ex){

        }
    };






    private void onInsert(Context context, ContentValues obj, String nTabela) {

        DB.getInstance( context ).insert( nTabela, obj );
    }




}

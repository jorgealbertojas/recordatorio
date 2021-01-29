package com.softjads.cadeconsumo;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.pinball83.maskededittext.MaskedEditText;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softjads.cadeconsumo.SQL.sql_create;
import com.softjads.cadeconsumo.SQL.sql_delete;
import com.softjads.cadeconsumo.SQL.sql_select;
import com.softjads.cadeconsumo.model.AlimentosCompletos;
import com.softjads.cadeconsumo.model.Pergunta;
import com.softjads.cadeconsumo.model.contagemGrupoAlimentar;
import com.softjads.cadeconsumo.modelJson.Crianca;
import com.softjads.cadeconsumo.modelJson.Imagens;
import com.softjads.cadeconsumo.modelJson.RespostaAdd;
import com.softjads.cadeconsumo.modelJson.RespostaAlimento;
import com.softjads.cadeconsumo.modelJson.RespostaComplementosAlimento;
import com.softjads.cadeconsumo.modelJson.alimentos;
import com.softjads.cadeconsumo.modelJson.medidasCaseiras;
import com.softjads.cadeconsumo.service.DB;
import com.softjads.cadeconsumo.service.DataBase;
import com.softjads.cadeconsumo.service.DateValidator;
import com.softjads.cadeconsumo.service.NotificationPublisher;
import com.softjads.cadeconsumo.util.ImageUtils;
import com.softjads.cadeconsumo.util.InterfaceRetrofit;
import com.softjads.cadeconsumo.util.MinhaTAG;
import com.softjads.cadeconsumo.util.MyConstant;
import com.softjads.cadeconsumo.util.SharedPreferencesService;
import com.softjads.cadeconsumo.util.Utility;
import com.softjads.cadeconsumo.util.VariavelAPI;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Questionario extends Activity  {

    Boolean esperaFinalizar = false;

    Boolean imagemEstaMarca = false;

    int TAKE_PHOTO_CODE = 0;
    int NOTIFICATION_REMINDER_NIGHT = 1;
    private final int REQUEST_TAKE_PHOTO_CODE = 1;
    private DisplayMetrics dm;

    private ArrayList<String> arrayVoltar = null;

    public EditText llPergunta;

    public boolean isBackPressed = false;

    private ProgressBar progressBar;

    private List<Imagens> listimagens = null;
    private List<String> saltoTelaImagem = null;
    private List<String> qualTelaImagem = null;
    private List<String> listimagensCodigo = null;

    public ImageButton fotoCamera = null;
    public ImageButton fotoCamera2 = null;
    public ImageButton fotoCameraElipse = null;
    public ImageView foto_camera_dentro = null;

    public ImageView imageView1 = null;
    public ImageView imageView2 = null;
    public ImageView imageView3 = null;
    public ImageView imageView4 = null;

    public ConstraintLayout constraintLayout1 = null;
    public ConstraintLayout constraintLayout2 = null;
    public ConstraintLayout constraintLayout3 = null;
    public ConstraintLayout constraintLayout4 = null;


    public boolean NaoENotificacao = false;

    public static final String ConstAmbienteTEMP = VariavelAPI.constante_variavel_papel;
    //public static final String ConstAmbienteTEMP = VariavelAPI.constante_variavel_domiciliar;
    //public static final String ConstAmbienteTEMP = VariavelAPI.constante_variavel_escolar;

    public static final String MAIOR_7ANO = VariavelAPI.constante_variavel_idade1;
    public static final String MENOR_OU_IGUAL_7ANOS = VariavelAPI.constante_variavel_idade2;

    public static Integer idade = 6;
    public static Boolean idadeBoolean = false;

    public static String saltoTEMP_NOVO = "";
    public String saltoTEMP = saltoTEMP_NOVO;
    public static String ambienteTEMP = ConstAmbienteTEMP;

    public static String nomeCrianca = "Jorge Alberto";
    public static String nomeAlimento = "Jorge Alberto";

    public static String idAlimento = "";
    public static String idPerguntaParaAliemnto = "";

    public boolean PassouPorAquiTextoTemp = false;

    public String numero_alimento_atual = "0";

    public String numero_refeicao_atual_domic = "0";

    public String refeicao_para_papel = "0";

    public String mCurrentPhotoPath = "";

    public ArrayList<alimentos> data = null;
    public ArrayList<contagemGrupoAlimentar> grupo = null;

    public DateValidator dateValidator = null;

    private Button buttonPersonalizado = null;
    private boolean buttonPersonalizadoBolean = true;
    private Button buttonPersonalizado2 = null;
    private Button imageButtonAvancar = null;
    private String opcao = "";
    private String opcaoQuestionario = "";
    private String opcaoQuestionarioFINAL = "";
    private int subPerguntaAluno = 0;
    private boolean DeletaBoolean = false;
    private boolean voltarparainsercao = false;
    private int subformulario = 0;
    private int contid = 0;
    private String filtro_id_cliente = "";
    private String filtro_id_pesquisa = "";
    private String filtro_automatico = "";
    private String NumeroPerguntaAtual = "0";
    private String nFONTE = "";
    private int AlunoAtual = 0;
    private String AlunoAtualID = "0";
    private String usuario = "";
    private String Nomeusuario = "";
    private String NomeGravacaoArquivo = "";
    private String filtro_desc_pesquisa = "";
    private String filtro_previsao = "";
    private String lat = "";
    private String log = "";
    private Boolean nTIME = false;
    private String nGPS = "";
    private LinearLayout ll;
    private int count = 0;
    //private ArrayList<EditText> edits;
    private ArrayList<TextInputLayout> edits;
    private ArrayList<RadioButton> radionbuttons;
    private ArrayList<CheckBox> checkboxs;
    private ArrayList<NumberPicker> numberPickers;
    private ArrayList<DatePicker> DatePickers;
    private ArrayList<ImageButton> imageButtons;
    private ArrayList<Button> Buttons;
    private EditText TemEditText;
    private MaskedEditText TempMaskEditText;
    //private ArrayList<MinhaTAG> listaTags;
    private ArrayList<String> MaxText;
    private ArrayList<String> HintText;
    private ArrayList<String> PERSONALIZACAOHint;
    private ArrayList<String> PERSONALIZAOText;
    private ArrayList<String> TagText;
    private int AtualMax = 0;
    private String AtualHint = "";
    private String AtualPERSONALIZACAOHint = "";
    private EditText editsPergunta;
    // private TextView textoEdits;
    private Pergunta nPergunta;
    private SQLiteDatabase bd;
    private Context context;
    private DataBase nDataBase;
    private Cursor cursorPergunta;
    private int bloco = 0;
    private int Contador = 0;
    private int pcodPergunta = 0;
    private int pcodResposta = 0;
    private List<String> steps = null;
    private boolean podedefechar = false;
    private InterfaceRetrofit mInterfaceObject;

    private String anteriorRefeicaoCombo = "";


    private GoogleApiClient client;
    private Callback<AlimentosCompletos> objectCallback = new Callback<AlimentosCompletos>() {
        @Override
        public void onResponse(Call<AlimentosCompletos> call, Response<AlimentosCompletos> response) {
            try {
                if (response.isSuccessful()) {
                    data = new ArrayList<>();
                    data.addAll(response.body().alimentos);

                    grupo = new ArrayList<>();
                    grupo.addAll(response.body().contagemGrupoAlimentar);

                    Questionario.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DestruirTextView();

                            if (grupo.size() > 0) {
                                for (int i = 0; i < grupo.size(); i++) {
                                    CheckBox checkBox = new CheckBox(getApplicationContext());
                                    checkBox.setText( grupo.get(i).getGrupoAlimentar() + " (" + grupo.get(i).getTotalAlimentos() +")" );
                                    checkBox.setTag(grupo.get(i).getGrupoAlimentar());
                                    checkBox.setTextColor(getResources().getColor(R.color.black));
                                    checkBox.setBackgroundColor(getResources().getColor(R.color.white));
                                    checkBox.setPadding(20, 2, 20, 2);
                                    checkBox.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            TornaInvisivelTextView(false);
                                           if (((CheckBox) view).isChecked()) {
                                               TornaVisivelTextViewGrupo(listaGrupoCheckbox(), true);
                                            }else{
                                               if (estaUmMarcadoCheckbox()) {
                                                   TornaVisivelTextViewGrupo(listaGrupoCheckbox(), true);
                                               }else{
                                                   TornaInvisivelTextView(true);
                                               }

                                            }


                                        }
                                    });

                                    LinearLayout.LayoutParams params;
                                    params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                                    params.setMargins(10, 2, 10, 2);
                                    ll.addView(checkBox, params);
                                }
                            }

                            if (data.size() > 0) {
                                for (int i = 0; i < data.size(); i++) {
                                    TextView textView = new TextView(getApplicationContext());
                                    textView.setText(data.get(i).getDescricao());
                                    textView.setTag(i);
                                    textView.setTextColor(getResources().getColor(R.color.white));
                                    textView.setBackgroundColor(getResources().getColor(R.color.color_primary_dark));
                                    textView.setPadding(20, 20, 20, 20);
                                    textView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            desejaAdicionar(view, TemEditText.getTag().toString());
                                        }
                                    });

                                    LinearLayout.LayoutParams params;
                                    params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                                    params.setMargins(10, 10, 10, 40);
                                    ll.addView(textView, params);
                                }
                            }
                        }
                    });
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                }
            } catch (NullPointerException e) {
                progressBar.setVisibility(View.INVISIBLE);
                System.out.println("onActivityResult consume crashed");
                runOnUiThread(new Runnable() {
                    public void run() {

                        Context context = getApplicationContext();
                    }
                });
            }
        }

        @Override
        public void onFailure(Call<AlimentosCompletos
                > call, Throwable t) {
            Context context = getApplicationContext();
            progressBar.setVisibility(View.INVISIBLE);

        }

    };

    @Override
    protected void onDestroy() {
        nDataBase = new DataBase(this);
        bd = nDataBase.getReadableDatabase();
        Cursor GET_tem_controle_fim = bd.rawQuery(sql_select.GET_tem_controle_fim, new String[]{Integer.toString(AlunoAtual)});
        GET_tem_controle_fim.moveToFirst();
        if (GET_tem_controle_fim.getCount() == 0) {
            if (nGPS.toString().equals("true")) {
                Intent nShowLocationActivity = new Intent(this, ShowLocationActivity.class);
                nShowLocationActivity.putExtra("filtro_id_cliente", filtro_id_cliente);
                nShowLocationActivity.putExtra("filtro_id_pesquisa", filtro_id_pesquisa);
                nShowLocationActivity.putExtra("filtro_desc_pesquisa", filtro_desc_pesquisa);
                nShowLocationActivity.putExtra("filtro_automatico", filtro_automatico);
                nShowLocationActivity.putExtra("filtro_previsao", filtro_previsao);
                nShowLocationActivity.putExtra("usuario", usuario);
                nShowLocationActivity.putExtra("Nomeusuario", Nomeusuario);
                nShowLocationActivity.putExtra("AlunoAtual", Integer.toString(AlunoAtual));
                nShowLocationActivity.putExtra("nFONTE", nFONTE);
                nShowLocationActivity.putExtra("NomeGravacaoArquivo", NomeGravacaoArquivo);
                nShowLocationActivity.putExtra("nGPS", nGPS);
                nShowLocationActivity.putExtra("nINICIO", "FALSE");
                startActivity(nShowLocationActivity);
            } else {
                insereControleFim();
            }
        }
        saltoTEMP = saltoTEMP_NOVO;
        super.onDestroy();
        //cursorPergunta.close();
    }

    private Boolean getPersonalizacaoBOOLEAN(String nDescricao, String id_opcao) {

        Cursor cursorgetPersonalizacao = bd.rawQuery(sql_select.getPersonalizacao_opcao_opcao, new String[]{(nDescricao), (id_opcao)});
        cursorgetPersonalizacao.moveToFirst();
        if (cursorgetPersonalizacao.getCount() > 0) {
            if (nDescricao.toString().indexOf("]]") > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public void insereControleFim() {

        String DataCompleta;
        String horaCompleta;

        Time now = new Time();
        now.setToNow();

        DataCompleta = Integer.toString(now.monthDay);
        DataCompleta = DataCompleta + "/" + Integer.toString(now.month + 1);
        DataCompleta = DataCompleta + "/" + Integer.toString(now.year);

        horaCompleta = Integer.toString(now.hour);
        horaCompleta = horaCompleta + ":" + Integer.toString(now.minute);
        horaCompleta = horaCompleta + ":" + Integer.toString(now.second);

        ContentValues obj = new ContentValues();
        obj.put("ID_ALUNO", AlunoAtual);
        obj.put("ID_USUARIO", usuario);
        obj.put("NM_USUARIO", Nomeusuario);
        obj.put("DATA_FIM", DataCompleta.toString());
        obj.put("FIM", horaCompleta.toString());
        obj.put("LONGITUDE", "sem gps");
        obj.put("LATITUDE", "sem gps");
        this.onInsert(this, obj, sql_create.TABLE_CONTROLE_FIM);
    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        numero_refeicao_atual_domic = "0";

        dm = getResources().getDisplayMetrics();

        arrayVoltar = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        saltoTEMP = (extras.getString("saltoTEMP_NOVO"));
        if (!NaoENotificacao) {
            ambienteTEMP = ConstAmbienteTEMP;
        }

        nDataBase = new DataBase(this);
        bd = nDataBase.getReadableDatabase();


        bd.execSQL(sql_delete.DEL_SALTO_TODOS, new String[]{});

        if (!saltoTEMP.equals("")) {
            bd.execSQL(sql_delete.DEL_SALTO_TODOS, new String[]{});

            if (VariavelAPI.constant_chave_107.equals(saltoTEMP)) {
                saltoTEMP = VariavelAPI.constant_chave_108;
            }

            if (VariavelAPI.contant_chave_inicair_anterior_DETALIMDOMIC2.equals(saltoTEMP)) {
                saltoTEMP = VariavelAPI.contant_chave_inicair_anterior_DETALIMDOMIC1;
            }

            if (VariavelAPI.contant_chave_inicair_anterior_DETALIMPAPEL2.equals(saltoTEMP)) {
                saltoTEMP = VariavelAPI.contant_chave_inicair_anterior_DETALIMPAPEL1;
            }

            if (VariavelAPI.contant_chave_inicair_anterior_DETALIMESC2.equals(saltoTEMP)) {
                saltoTEMP = VariavelAPI.contant_chave_inicair_anterior_DETALIMESC1;
            }

            if (VariavelAPI.contant_chave_inicair_anterior_DETALIMESC30.equals(saltoTEMP)) {
                saltoTEMP = VariavelAPI.contant_chave_inicair_anterior_DETALIMESC1;
            }

            if (VariavelAPI.contant_chave_inicair_anterior_DETALIMDOMIC8.equals(saltoTEMP)) {
                saltoTEMP = VariavelAPI.contant_chave_inicair_anterior_DETALIMDOMIC1;
            }

            if (VariavelAPI.contant_chave_inicair_anterior_DETALIMDOMIC30.equals(saltoTEMP)) {
                saltoTEMP = VariavelAPI.contant_chave_inicair_anterior_DETALIMDOMIC1;
            }

            if (VariavelAPI.contant_chave_inicair_anterior_DETALIMDOMIC40.equals(saltoTEMP)) {
                saltoTEMP = VariavelAPI.contant_chave_inicair_anterior_DETALIMDOMIC1;
            }

            if (VariavelAPI.contant_chave_inicair_anterior_DETALIMDOMIC50.equals(saltoTEMP)) {
                saltoTEMP = VariavelAPI.contant_chave_inicair_anterior_DETALIMDOMIC1;
            }


            InsereSalto(saltoTEMP, saltoTEMP);
            saltoTEMP = "";
        }

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.questionario);
        this.ll = (LinearLayout) this.findViewById(R.id.edits_ll);

        llPergunta = (EditText) findViewById(R.id.editEntrevistado);
        imageButtonAvancar = (Button) findViewById(R.id.imageButtonAvancar);
        buttonPersonalizado = (Button) findViewById(R.id.buttonPersonalizado);
        buttonPersonalizado2 = (Button) findViewById(R.id.buttonPersonalizado2);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);

        constraintLayout1 = (ConstraintLayout) findViewById(R.id.constraintLayout1);
        constraintLayout2 = (ConstraintLayout) findViewById(R.id.constraintLayout2);
        constraintLayout3 = (ConstraintLayout) findViewById(R.id.constraintLayout3);
        constraintLayout4 = (ConstraintLayout) findViewById(R.id.constraintLayout4);

        String notificacao = extras.getString("saltoTEMP_NOVO");
        if (notificacao != null) {
            if (notificacao.equals("NOTIFICACAO/1")) {
                NaoENotificacao = true;
            }
        }

        filtro_id_cliente = extras.getString("filtro_id_cliente");
        filtro_id_pesquisa = extras.getString("filtro_id_pesquisa");
        filtro_automatico = extras.getString("filtro_automatico");
        filtro_desc_pesquisa = extras.getString("filtro_desc_pesquisa");
        filtro_previsao = extras.getString("filtro_previsao");
        usuario = extras.getString("usuario");
        Nomeusuario = extras.getString("Nomeusuario");
        AlunoAtual = Integer.parseInt(extras.getString("AlunoAtual"));
        if (AlunoAtual == 0) {
            SharedPreferencesService shared = new SharedPreferencesService(this);
            AlunoAtual = Integer.parseInt(shared.getCodigoCrianca());
        }


        AlunoAtualID = (extras.getString("AlunoAtualID"));
        if (AlunoAtualID.equals("0")) {
            SharedPreferencesService shared = new SharedPreferencesService(this);
            AlunoAtualID = (shared.getIDCriancaCodigo());
        }

        nFONTE = extras.getString("nFONTE");
        nGPS = extras.getString("nGPS").toString();
        opcao = extras.getString("opcao");
        opcaoQuestionario = extras.getString("opcaoQuestionario");
        opcaoQuestionarioFINAL = extras.getString("opcaoQuestionarioFINAL");
        NomeGravacaoArquivo = extras.getString("NomeGravacaoArquivo");

        String ambienteTEMPNotificacao = extras.getString("ambienteTEMP");
        if (ambienteTEMPNotificacao != null) {
            ambienteTEMP = ambienteTEMPNotificacao;
        }

        String stingnTIME = "";
        stingnTIME = extras.getString("nTIME");
        if (stingnTIME.equals("1")) {
            nTIME = true;
        } else {
            nTIME = false;
        }

        nomeCrianca = colocaNomeCrianca();

        Cursor cursorQualAmbiente2;
        cursorQualAmbiente2 = bd.rawQuery(sql_select.GET_AMBIENTE_CASA_TESTE, null);
        cursorQualAmbiente2.moveToFirst();
        if (cursorQualAmbiente2.getCount() > 0) {
            Log.d("bebeto12", "teste" + cursorQualAmbiente2.getCount());
        }

        Cursor cursorQualAmbiente;
        cursorQualAmbiente = bd.rawQuery(sql_select.GET_AMBIENTE_CASA, new String[]{(Integer.toString(AlunoAtual))});
        cursorQualAmbiente.moveToFirst();
        if (cursorQualAmbiente.getCount() > 0) {
            ambienteTEMP = VariavelAPI.constante_variavel_domiciliar;
        }

        cursorQualAmbiente = bd.rawQuery(sql_select.GET_AMBIENTE_ESCOLAR, new String[]{(Integer.toString(AlunoAtual))});
        cursorQualAmbiente.moveToFirst();
        if (cursorQualAmbiente.getCount() > 0) {
            ambienteTEMP = VariavelAPI.constante_variavel_escolar;
        }

        //edits = new ArrayList<EditText>();
        edits = new ArrayList<TextInputLayout>();
        radionbuttons = new ArrayList<RadioButton>();
        checkboxs = new ArrayList<CheckBox>();
        numberPickers = new ArrayList<NumberPicker>();
        DatePickers = new ArrayList<DatePicker>();
        imageButtons = new ArrayList<ImageButton>();
        Buttons = new ArrayList<Button>();

        AbrirMaxPergunta();

       // if (savedInstanceState == null) {
            AbrirQuestionarioSQL();
            if (opcao == "0") {
                Ir_utimo();
            } else {
                Ir_utimo_OPCAOQUESTIONARIO();
            }
            AvancarQuestionario("");

            Cursor GET_tem_controle_inicio = bd.rawQuery(sql_select.GET_tem_controle_inicio, new String[]{Integer.toString(AlunoAtual)});
            GET_tem_controle_inicio.moveToFirst();

            int uuu = GET_tem_controle_inicio.getCount();

            if (GET_tem_controle_inicio.getCount() == 0) {
                if (nGPS.toString().equals("true")) {
                    LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
                    boolean enabled = service
                            .isProviderEnabled(LocationManager.GPS_PROVIDER);
                    // check if enabled and if not send user to the GSP settings
                    // Better solution would be to display a dialog and suggesting to
                    // go to the settings
                    if (!enabled) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);

                    }

                    Intent nShowLocationActivity = new Intent(this, ShowLocationActivity.class);
                    nShowLocationActivity.putExtra("filtro_id_cliente", filtro_id_cliente);
                    nShowLocationActivity.putExtra("filtro_id_pesquisa", filtro_id_pesquisa);
                    nShowLocationActivity.putExtra("filtro_desc_pesquisa", filtro_desc_pesquisa);
                    nShowLocationActivity.putExtra("filtro_automatico", filtro_automatico);
                    nShowLocationActivity.putExtra("filtro_previsao", filtro_previsao);
                    nShowLocationActivity.putExtra("usuario", usuario);
                    nShowLocationActivity.putExtra("Nomeusuario", Nomeusuario);
                    nShowLocationActivity.putExtra("AlunoAtual", Integer.toString(AlunoAtual));
                    nShowLocationActivity.putExtra("NomeGravacaoArquivo", NomeGravacaoArquivo);
                    nShowLocationActivity.putExtra("nFONTE", nFONTE);
                    nShowLocationActivity.putExtra("nGPS", nGPS);
                    nShowLocationActivity.putExtra("nINICIO", "TRUE");
                    startActivity(nShowLocationActivity);

                } else {
                    insereControleInicio();
                }
            }

/*        } else if (savedInstanceState != null) {
            AbrirQuestionarioSQL();
            //     Localizador();
            MaxText = new ArrayList<String>();
            HintText = new ArrayList<String>();
            PERSONALIZACAOHint = new ArrayList<String>();
            PERSONALIZAOText = new ArrayList<String>();
            TagText = new ArrayList<String>();
            criarNovaEditText();
            //  PreencherResposta((NumeroPerguntaAtual));
            cursorPergunta.moveToNext();
        }*/

        imageButtonAvancar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                putNotificacao(buttonPersonalizado);

                putAlimento();

                if ((estaPreenchido())) {
                    AvancarQuestionario("");
                } else {
                    Toast.makeText(Questionario.this, "Por favor, preencha todos os campos para prosseguir.", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonPersonalizado.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                putNotificacao(buttonPersonalizado);

                putAlimento();

                if ((estaPreenchido() || estaPreenchidoDESCRICAO())) {
                    if (!gravaAlimento(buttonPersonalizado.getText().toString())) {
                        bd.execSQL(sql_delete.DEL_SALTO_TODOS, new String[]{});
                        InsereSalto(buttonPersonalizado.getTag().toString(), buttonPersonalizado.getTag().toString());
                        AvancarQuestionario(buttonPersonalizado.getTag().toString());
                    }
                } else {
                    Toast.makeText(Questionario.this, "Por favor, preencha todos os campos para prosseguir", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonPersonalizado2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                putNotificacao(buttonPersonalizado2);

                putAlimento();

                if ((estaPreenchido() || estaPreenchidoDESCRICAO())) {
                    bd.execSQL(sql_delete.DEL_SALTO_TODOS, new String[]{});
                    InsereSalto(buttonPersonalizado2.getTag().toString(), buttonPersonalizado2.getTag().toString());
                    AvancarQuestionario(buttonPersonalizado.getTag().toString());
                } else {
                    Toast.makeText(Questionario.this, "Por favor, preencha todos os campos para prosseguir", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void putNotificacao(Button butao) {
        if (butao.getText().toString() != null) {
            if ((butao.getText().toString().equals(VariavelAPI.constant_ativar_natificacao)) || (butao.getText().toString().equals(VariavelAPI.constant_ativar_natificacaoNao) && (cursorPergunta.getString(0).equals(VariavelAPI.constant_chave_1015)))) {
                notificationDialog();
                Toast.makeText(this, "Notificação agendada com sucesso!", Toast.LENGTH_LONG).show();
                finishAffinity();
            }
        }
    }

    private void putAlimento() {
        int valor = 1000;
        if (NumeroPerguntaAtual.equals(VariavelAPI.constant_chave_1017)) {
            bd.execSQL(" update ALIMENTO set ALIMENTO_REFEICAO_ORDER = " + valor);

            insereeAtualizaAlimentoeRefeicaoComoCodigocomFOR(valor);
        }
    }

    private int RetornoTAG(MinhaTAG nTAG, int nIndex) {
        if (nIndex == 1) {

            return nTAG.nTAG;
        } else if (nIndex == 2) {

            return nTAG.nTAGMIN;
        } else {
            return nTAG.nTAGMAX;
        }
    }

    protected void AbrirQuestionarioSQL() {
        nDataBase = new DataBase(this);
        bd = nDataBase.getReadableDatabase();
        cursorPergunta = bd.rawQuery(sql_select.GET_PERGUNTA_QUESTIONARIO_NOVO, null);
        cursorPergunta.moveToFirst();
    }

    protected int AbrirMaxPergunta() {
        try {

            nDataBase = new DataBase(this);
            bd = nDataBase.getReadableDatabase();
            cursorPergunta = bd.rawQuery(sql_select.GET_PERGUNTA_CONTA, null);
            cursorPergunta.moveToFirst();
            return cursorPergunta.getInt(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }

    }

    public void finalizar() {
        this.finish();
    }

    protected void criarNovaEditText() {

        idadeBoolean = false;
        boolean passou = false;
        nDataBase = new DataBase(this);
        bd = nDataBase.getReadableDatabase();





     //   llPergunta.animate().translationY(dm.heightPixels).setStartDelay(0).setDuration(0).start();
    //    llPergunta.animate().translationY(0).setDuration(500).alpha(1).setStartDelay(1500).start();

        buttonPersonalizado.animate().translationX(dm.widthPixels + buttonPersonalizado.getMeasuredWidth()).setDuration(0).setStartDelay(0).start();
        buttonPersonalizado.animate().translationX(0).setStartDelay(500).setDuration(1500).setInterpolator(new OvershootInterpolator()).start();

        buttonPersonalizado2.animate().translationX(dm.widthPixels + buttonPersonalizado2.getMeasuredWidth()).setDuration(0).setStartDelay(0).start();
        buttonPersonalizado2.animate().translationX(0).setStartDelay(500).setDuration(1500).setInterpolator(new OvershootInterpolator()).start();

        imageButtonAvancar.animate().translationX(dm.widthPixels + imageButtonAvancar.getMeasuredWidth()).setDuration(0).setStartDelay(0).start();
        imageButtonAvancar.animate().translationX(0).setStartDelay(500).setDuration(1500).setInterpolator(new OvershootInterpolator()).start();

        NumeroPerguntaAtual = (cursorPergunta.getString(6));

        subformulario = Integer.parseInt(cursorPergunta.getString(5));

        if (subformulario == 0) {
            subPerguntaAluno = 0;

            if (nFONTE.equals("P")) {
                llPergunta.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            } else if (nFONTE.equals("M")) {
                llPergunta.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            } else if (nFONTE.equals("G")) {
                llPergunta.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            }

                if (cursorPergunta.getString(1) != null) {

                    String pergunta = cursorPergunta.getString(1);
                    if (pergunta.toString() != null) {
                        if (pergunta.length() > 0) {
                            pergunta = pergunta.replace("          ", " ");
                            pergunta = pergunta.replace("\\n", "\n");
                            pergunta = pergunta.replace(VariavelAPI.constante_variavel_entrevistado, nomeCrianca);
                        }
                    }

                    if (pergunta.toString() != null) {
                        if (pergunta.length() > 0) {
                            pergunta = pergunta.replace("          ", " ");
                            pergunta = pergunta.replace("\\n", "\n");
                            pergunta = pergunta.replace(VariavelAPI.constante_variavel_entrevistado2, nomeCrianca);
                        }
                    }

                    if (pergunta.toString() != null) {
                        if (pergunta.length() > 0) {
                            pergunta = pergunta.replace("          ", " ");
                            pergunta = pergunta.replace("\\n", "\n");
                            pergunta = pergunta.replace(VariavelAPI.contante_variavel_detalhar_alimento, nomeAlimento);
                        }
                    }

                    if (pergunta.toString() != null) {
                        if (pergunta.length() > 0) {
                            pergunta = pergunta.replace("          ", " ");
                            pergunta = pergunta.replace("\\n", "\n");
                            pergunta = pergunta.replace(VariavelAPI.contante_variavel_detalhar_alimento2, nomeAlimento);
                        }
                    }

                    if (pergunta.toString() != null && numero_refeicao_atual_domic != null) {
                        if (pergunta.length() > 0) {
                            pergunta = pergunta.replace("          ", " ");
                            pergunta = pergunta.replace("\\n", "\n");
                            pergunta = pergunta.replace(VariavelAPI.constante_variavel_refeicao_nome_selecionada, numero_refeicao_atual_domic);
                        }
                    }

                    if (pergunta.toString() != null && colocaRefeicaoOrder() != null) {
                        if (pergunta.length() > 0) {
                            pergunta = pergunta.replace("          ", " ");
                            pergunta = pergunta.replace("\\n", "\n");
                            pergunta = pergunta.replace(VariavelAPI.constante_variavel_refeicao_nome, colocaRefeicaoOrder());
                        }
                    }

                    if (pergunta.toString() != null) {
                        if (pergunta.length() > 0) {

                            String medidacaseira = "";
                            Cursor cursorRESPOSTA_OPCAO_MEDIDA_CASEIRA = bd.rawQuery(sql_select.GET_RESPOSTA_OPCAO_MEDIDA_CASEIRA_2, new String[]{Integer.toString(AlunoAtual)});
                            cursorRESPOSTA_OPCAO_MEDIDA_CASEIRA.moveToFirst();
                            cursorRESPOSTA_OPCAO_MEDIDA_CASEIRA.getCount();
                            if (cursorRESPOSTA_OPCAO_MEDIDA_CASEIRA.getCount() > 0) {
                                medidacaseira = cursorRESPOSTA_OPCAO_MEDIDA_CASEIRA.getString(4);
                            }

                            pergunta = pergunta.replace("          ", " ");
                            pergunta = pergunta.replace("\\n", "\n");
                            pergunta = pergunta.replace(VariavelAPI.constante_descricao_medida_caseira_detalhar, medidacaseira);
                        }
                    }

                    llPergunta.setText(pergunta);


                }

                llPergunta.refreshDrawableState();

                final String valorPergunta = cursorPergunta.getString(1);

                int nMax;
                int nMin = 0;

                Cursor cursor = bd.rawQuery(sql_select.GET_OPCAO, new String[]{cursorPergunta.getString(0)});
                cursor.moveToFirst();

                String igual = "0";

                Resources res = getResources();

            buttonPersonalizado.setVisibility(View.INVISIBLE);
            buttonPersonalizado2.setVisibility(View.INVISIBLE);
            imageButtonAvancar.setVisibility(View.VISIBLE);

            if (!isBackPressed) {
                arrayVoltar.add(cursorPergunta.getString(7));
            }
            isBackPressed = false;

            imagemEstaMarca = false;

            saltoTelaImagem = new ArrayList<String>();
            qualTelaImagem = new ArrayList<String>();

            while (!cursor.isAfterLast()) {

                if (mostraAmbiente(cursor.getString(8))) {
                    pintaSeta(cursorPergunta.getInt(2));

                    try {

                        if (cursor.getInt(2) == 99) {
                            AdicionarYouTube();
                        } else if (cursor.getInt(2) == 98) {
                            if (cursor.getString(8).equals(VariavelAPI.constante_variavel_fotografar)) {
                                    AdicionarIconeFoto(true);
                                } else {
                                    AdicionarIconeFoto(false);
                                }

                            } else if (cursor.getInt(2) == 44) {
                                if (mostraAmbiente(cursor.getString(8))) {
                                    TextView textView = new TextView(getApplicationContext());
                                    if (cursor.getString(3) != null) {
                                        textView.setText(cursor.getString(3));
                                    }
                                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                                    textView.setTag(cursor.getString(0));
                                    textView.setTextColor(getResources().getColor(R.color.black));
                                    textView.setPadding(20, 20, 20, 20);
                                    LinearLayout.LayoutParams params;
                                    params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                                    params.setMargins(10, 10, 10, 40);
                                    ll.addView(textView, params);
                                }
                            }
                            // RADIONBUTTON
                            else if (cursor.getInt(2) == 0) {
                                if (!((cursor.getString(0)).equals(igual))) {
                                    igual = cursor.getString(0);
                                    count++;
                                    LinearLayout.LayoutParams params;

                                    if (cursor.getString(3).equals(VariavelAPI.constante_descricao_acrescentou)) {

                                    } else if (personalizado(cursor.getString(3))) {
                                        params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                                        RadioButton radionbutton = new RadioButton(this);
                                        params.setMargins(0, 10, 0, 30);

                                        radionbutton.setText(cursor.getString(3));

                                        radionbutton.setTag(cursor.getString(0));
                                        radionbutton.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/Roboto-Regular.ttf"));


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

                                                if (VariavelAPI.constante_descricao_domiciliar.equals(((RadioButton) view).getText().toString())) {
                                                    colocarValorAmbiente(VariavelAPI.constante_variavel_domiciliar);
                                                }
                                                if (VariavelAPI.constante_descricao_escolar.equals(((RadioButton) view).getText().toString())) {
                                                    colocarValorAmbiente(VariavelAPI.constante_variavel_escolar);
                                                }
                                                if (cursorPergunta.getString(7).equals(VariavelAPI.constant_chave_100)) {
                                                    if (VariavelAPI.constante_descricao_inicio_2_confirma.equals(((RadioButton) view).getText().toString())) {
                                                        colocarValorAmbiente(VariavelAPI.constante_variavel_papel);
                                                    }
                                                }

                                               // esperaFinalizar = true;
                                                bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual)});
                                                createStackOverflowAPI();

                                                //mInterfaceObject.deleteRespostaCrianca(AlunoAtualID,(NumeroPerguntaAtual)).enqueue(deletarRespostaCriancaCallback);



                                                bd.execSQL(sql_delete.DEL_SALTO_PERGUNTA, new String[]{(NumeroPerguntaAtual)});

                                                if (getPersonalizacaoBOOLEAN((NumeroPerguntaAtual), ((RadioButton) view).getTag().toString())) {
                                                    insereRegistro(((RadioButton) view).getTag().toString(), ((RadioButton) view).getText().toString(), 0, true);
                                                } else {
                                                    insereRegistro(((RadioButton) view).getTag().toString(), "", 0, true);
                                                }

                                                Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{(NumeroPerguntaAtual), ((RadioButton) view).getTag().toString()});
                                                cursorSALTO.moveToFirst();
                                                cursorSALTO.getCount();

                                                if (cursorSALTO.getCount() > 0) {
                                                    if (!cursorSALTO.getString(6).equals("0")) {
                                                        InsereSalto(cursorSALTO.getString(6), ((RadioButton) view).getTag().toString());
                                                    }
                                                }

                                                for (int i = 0; i < radionbuttons.size(); i++) {
                                                    radionbuttons.get(i).setChecked(false);
                                                }

                                                for (int i = 0; i < checkboxs.size(); i++) {
                                                    checkboxs.get(i).setChecked(false);
                                                }

                                                ((RadioButton) view).setChecked(true);
                                            }
                                        });

                                        radionbuttons.add(radionbutton); // adiciona a nova editText a lista.

                                        // coloca resposta
                                        if (colocaValorRadio(radionbutton.getTag().toString())){
                                            radionbutton.setChecked(true);
                                            Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{(NumeroPerguntaAtual), cursor.getString(0)});
                                            cursorSALTO.moveToFirst();
                                            cursorSALTO.getCount();

                                            if (cursorSALTO.getCount() > 0) {
                                                if (!cursorSALTO.getString(6).equals("0")) {
                                                    InsereSalto(cursorSALTO.getString(6), cursor.getString(0));
                                                }
                                            }
                                        }

                                        ll.addView(radionbutton, params); // adiciona a editText ao ViewGroup
                                    } else if (cursor.getString(3).equals(VariavelAPI.constante_variavel_alimento_excluir_editar)) {
                                        criarAlimentosInseridos();
                                    } else if (cursor.getString(3).equals(VariavelAPI.constante_variavel_alimento_porcoes) || cursor.getString(3).equals(VariavelAPI.constante_variavel_alimento_utensilios)) {
                                        colocarImagens();

                                    } else if (cursor.getString(3).equals(VariavelAPI.constante_variavel_refeicao_com_opcao_mais)) {
                                        criarRefeicaoInseridos(true);

                                    } else if (cursor.getString(3).equals(VariavelAPI.constante_variavel_refeicao)) {
                                        criarRefeicaoInseridos(false);
                                    } else if (cursor.getString(3).equals(VariavelAPI.constante_variavel_alimento_cadastrado)) {

                                        Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{(NumeroPerguntaAtual), cursor.getString(0)});
                                        cursorSALTO.moveToFirst();
                                        cursorSALTO.getCount();

                                        if (cursorSALTO.getCount() > 0) {
                                            if (!cursorSALTO.getString(6).equals("0")) {
                                                InsereSalto(cursorSALTO.getString(6), cursor.getString(0));
                                            }
                                        }


                                        criarAlimentosInseridosDetalhes(true);
                                    }
                                    else if (cursor.getString(3).equals(VariavelAPI.constante_variavel_alimento_cadastrado_simples)) {

                                        Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{(NumeroPerguntaAtual), cursor.getString(0)});
                                        cursorSALTO.moveToFirst();
                                        cursorSALTO.getCount();

                                        if (cursorSALTO.getCount() > 0) {
                                            if (!cursorSALTO.getString(6).equals("0")) {
                                                InsereSalto(cursorSALTO.getString(6), cursor.getString(0));
                                            }
                                        }


                                        criarAlimentosInseridosDetalhes(false);
                                    }
                                }
                            } else if (cursor.getInt(2) == 34) {
                            String opcoes = cursor.getString(3);

                            saltoTelaImagem.add(cursor.getString(6));
                            qualTelaImagem.add(cursor.getString(8));

                            if (buttonPersonalizado.getVisibility() == View.INVISIBLE) {
                                if (mostraAmbiente(cursor.getString(8)) && (!temIdadeFrase(cursor.getString(8)))) {
                                    if (eParaFechar(cursor.getString(8))) {
                                        buttonPersonalizado.setTag(cursor.getString(8));
                                    } else {
                                        buttonPersonalizado.setTag(cursor.getString(6));
                                    }
                                    if (buttonPersonalizadoBolean) {
                                        buttonPersonalizado.setVisibility(View.VISIBLE);
                                    }
                                        buttonPersonalizadoBolean = true;
                                        buttonPersonalizado.setText(opcoes);
                                    }
                                } else {
                                    if (mostraAmbiente(cursor.getString(8)) && (!temIdadeFrase(cursor.getString(8)))) {
                                        if (eParaFechar(cursor.getString(8))){
                                            buttonPersonalizado2.setTag(cursor.getString(8));
                                        }else{
                                            buttonPersonalizado2.setTag(cursor.getString(6));
                                        }
                                        buttonPersonalizado2.setVisibility(View.VISIBLE);
                                        buttonPersonalizado2.setText(opcoes);
                                    }
                                }
                                if (buttonPersonalizado.getVisibility() == View.INVISIBLE) {
                                    if (idadeMaior7(cursor.getString(8), idade >= 7)) {
                                        if (temIdadeFrase(cursor.getString(8))) {
                                            if (eParaFechar(cursor.getString(8))) {
                                                buttonPersonalizado.setTag(cursor.getString(8));
                                            } else {
                                                buttonPersonalizado.setTag(cursor.getString(6));
                                            }
                                            buttonPersonalizado.setVisibility(View.VISIBLE);
                                            buttonPersonalizado.setText(opcoes);
                                        }
                                    }
                                } else {
                                    if (idadeMaior7(cursor.getString(8), idade >= 7)) {
                                        if (temIdadeFrase(cursor.getString(8))) {
                                            if (eParaFechar(cursor.getString(8))) {
                                                buttonPersonalizado2.setTag(cursor.getString(8));
                                            } else {
                                                buttonPersonalizado2.setTag(cursor.getString(6));
                                            }
                                            buttonPersonalizado2.setVisibility(View.VISIBLE);
                                            buttonPersonalizado2.setText(opcoes);
                                        }
                                    }
                                }
                                imageButtonAvancar.setVisibility(View.INVISIBLE);
                            }
                            // Combo
                            else if (cursor.getInt(2) == 33) {
                                if (!((cursor.getString(0)).equals(igual))) {
                                    igual = cursor.getString(0);
                                    count++;
                                    LinearLayout.LayoutParams params;

                                    TextInputLayout editAnimation = new TextInputLayout(this);
                                    editAnimation.setTag(cursor.getString(0));

                                    params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

                                    String array_spinner[];

                                    final Spinner spinner = new Spinner(this, Spinner.MODE_DIALOG);
                                    String opcoes = personalizadoTESTE(cursor.getString(8));

                                    array_spinner = new String[Utility.countCaracter(opcoes) + 1];

                                    for (int i = 0; i < array_spinner.length; i++) {
                                        if (i == 0) {
                                            array_spinner[0] = VariavelAPI.constanteSelecione;
                                        } else {
                                            array_spinner[i] = opcoes.substring(0, Utility.positionCaracter(opcoes));
                                            opcoes = opcoes.substring(Utility.positionFirstCaracter(opcoes), opcoes.length());
                                        }
                                    }

                                    ArrayAdapter adapter = new ArrayAdapter(this,
                                            R.layout.textview_spinner_item, array_spinner);
                                    spinner.setAdapter(adapter);
                                    params.setMargins(0, 5, 0, 20);
                                    spinner.setTag(cursor.getString(0));

                                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                                            .hideSoftInputFromWindow(spinner.getWindowToken(), 0);

                                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                            if (((TextView) selectedItemView).getText() != VariavelAPI.constanteSelecione) {
                                                if (((TextView) selectedItemView).getText().toString() != null) {

                                                    bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ID_OPCAO_ID_ALIMENTO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), parentView.getTag().toString(), idAlimento});

                                                    insereRegistro(parentView.getTag().toString(), ((TextView) selectedItemView).getText().toString(), 0, false);

                                                    Cursor cursorMEDIDAS_CASEIRAS = bd.rawQuery(sql_select.GET_MEDIDAS_CASEIRAS, new String[]{idAlimento});
                                                    cursorMEDIDAS_CASEIRAS.moveToFirst();
                                                    cursorMEDIDAS_CASEIRAS.getCount();

                                                    if (cursorMEDIDAS_CASEIRAS.getCount() > 0) {
                                                        for (int h = 0; h < cursorMEDIDAS_CASEIRAS.getCount(); h++) {
                                                            Gson gson = new Gson();
                                                            String tempMedida = cursorMEDIDAS_CASEIRAS.getString(1);

                                                            medidasCaseiras medidasCaseiras = gson.fromJson(tempMedida, medidasCaseiras.class);
                                                            if (medidasCaseiras.getNome().equals(((TextView) selectedItemView).getText().toString())) {

                                                                if (medidasCaseiras.getTipoFoto().equals("FOTO_PORCAO_ALIMENTO")){
                                                                    habilitarBotao(VariavelAPI.constant_foto_alimento);
                                                                }else if (medidasCaseiras.getTipoFoto().equals("SEM FOTO")){
                                                                    habilitarBotao(VariavelAPI.constant_sem_foto);
                                                                }else{
                                                                    habilitarBotao(VariavelAPI.constant_foto_utensilio);
                                                                }

                                                                listimagens = new ArrayList<>();
                                                                listimagensCodigo = new ArrayList<>();
                                                                for (int y = 0; y < medidasCaseiras.getFotos().size(); y++) {
                                                                    Imagens imagens = new Imagens();
                                                                    imagens.setUrl(Utility.imagemURL + medidasCaseiras.getFotos().get(y));
                                                                    imagens.setId(medidasCaseiras.getNome());
                                                                    listimagens.add(imagens);
                                                                    listimagensCodigo.add(medidasCaseiras.getFotos().get(y));
                                                                }
                                                            }
                                                            cursorMEDIDAS_CASEIRAS.moveToNext();
                                                        }
                                                    }

                                                    if ((!cursorPergunta.isAfterLast())) {
                                                        if (cursorPergunta.getString(7) != null && cursorPergunta.getString(7) != "") {
                                                            if (VariavelAPI.constante_variavel_domiciliar.equals(ambienteTEMP)) {
                                                                if (cursorPergunta.getString(7).equals(VariavelAPI.constant_chave_101_domic)  || cursorPergunta.getString(7).equals(VariavelAPI.contant_chave_inicair_anterior_DETALIMESC1)  || cursorPergunta.getString(7).equals(VariavelAPI.contant_chave_inicair_anterior_DETALIMPAPEL1)) {
                                                                    Cursor cursorRefeicaoatualiza = bd.rawQuery(sql_select.GET_ALIMENTO_TODOS + " WHERE ALIMENTO_REFEICAO is NULL or  ALIMENTO_REFEICAO = '" + anteriorRefeicaoCombo + "'", null);
                                                                    cursorRefeicaoatualiza.moveToFirst();
                                                                    cursorRefeicaoatualiza.getCount();
                                                                    anteriorRefeicaoCombo = ((TextView) selectedItemView).getText().toString();
                                                                    if (cursorRefeicaoatualiza.getCount() > 0) {
                                                                        if (cursorPergunta.getString(7).equals(VariavelAPI.constant_chave_101_domic)) {

                                                                                bd.execSQL(" update ALIMENTO set ALIMENTO_REFEICAO_ORDER = " + getRefeicaoOrder() + ", ALIMENTO_REFEICAO = '" + ((TextView) selectedItemView).getText() + "' WHERE ID = '" + idAlimento + "'" + " AND ALIMENTO_REFEICAO_ORDER <> 1000 " );
                                                                                insereeAtualizaAlimentoeRefeicaoComoCodigo(idAlimento, ((TextView) selectedItemView).getText().toString());

                                                                        }else{
                                                                            for (int h = 0; h < cursorRefeicaoatualiza.getCount(); h++) {
                                                                                bd.execSQL(" update ALIMENTO set ALIMENTO_REFEICAO_ORDER = " + getRefeicaoOrder() + ", ALIMENTO_REFEICAO = '" + ((TextView) selectedItemView).getText() + "' WHERE ID = '" + cursorRefeicaoatualiza.getString(0) + "'" + " AND ALIMENTO_REFEICAO_ORDER <> 1000 ");
                                                                                insereeAtualizaAlimentoeRefeicaoComoCodigo(cursorRefeicaoatualiza.getString(0), ((TextView) selectedItemView).getText().toString());
                                                                                cursorRefeicaoatualiza.moveToNext();


                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else if (VariavelAPI.constante_variavel_escolar.equals(ambienteTEMP)) {
                                                                if (cursorPergunta.getString(7).equals(VariavelAPI.constant_chave_101_domic) || cursorPergunta.getString(7).equals(VariavelAPI.contant_chave_inicair_anterior_DETALIMDOMIC1) || cursorPergunta.getString(7).equals(VariavelAPI.contant_chave_inicair_anterior_DETALIMPAPEL1)) {
                                                                    Cursor cursorRefeicaoatualiza = bd.rawQuery(sql_select.GET_ALIMENTO_TODOS + " WHERE ALIMENTO_REFEICAO is NULL or  ALIMENTO_REFEICAO = '" + anteriorRefeicaoCombo + "'", null);
                                                                    cursorRefeicaoatualiza.moveToFirst();
                                                                    cursorRefeicaoatualiza.getCount();
                                                                    anteriorRefeicaoCombo = ((TextView) selectedItemView).getText().toString();
                                                                    if (cursorRefeicaoatualiza.getCount() > 0) {
                                                                        if (cursorPergunta.getString(7).equals(VariavelAPI.constant_chave_101_domic) ) {

                                                                                bd.execSQL(" update ALIMENTO set ALIMENTO_REFEICAO_ORDER = " + getRefeicaoOrder() + ", ALIMENTO_REFEICAO = '" + ((TextView) selectedItemView).getText() + "' WHERE ID = '" + idAlimento + "'" + " AND ALIMENTO_REFEICAO_ORDER <> 1000 ");
                                                                                insereeAtualizaAlimentoeRefeicaoComoCodigo(idAlimento, ((TextView) selectedItemView).getText().toString());

                                                                        }else{
                                                                            for (int h = 0; h < cursorRefeicaoatualiza.getCount(); h++) {
                                                                                bd.execSQL(" update ALIMENTO set ALIMENTO_REFEICAO_ORDER = " + getRefeicaoOrder() + ", ALIMENTO_REFEICAO = '" + ((TextView) selectedItemView).getText() + "' WHERE ID = '" + cursorRefeicaoatualiza.getString(0) + "'"  + " AND ALIMENTO_REFEICAO_ORDER <> 1000 ");
                                                                                insereeAtualizaAlimentoeRefeicaoComoCodigo(cursorRefeicaoatualiza.getString(0), ((TextView) selectedItemView).getText().toString());
                                                                                cursorRefeicaoatualiza.moveToNext();
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
                                                // pega resposta
                                                String retornoTexto = colocaValor(parentView.getTag().toString());
                                                anteriorRefeicaoCombo = retornoTexto;
                                                if (!retornoTexto.equals("")) {
                                                    for (int i = 0; i < parentView.getAdapter().getCount(); i++) {
                                                        if (retornoTexto.equals(parentView.getAdapter().getItem(i).toString())) {
                                                            if (nestaPerguntaNaoColocaResposta()) {
                                                                spinner.setSelection(i);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }


                                    });


                                    editAnimation.addView(spinner, params);
                                    editAnimation.setHelperText("  " + cursor.getString(3));

                                    editAnimation.setHelperTextTextAppearance(R.style.TextHelp);
                                    editAnimation.setBackground(getResources().getDrawable(R.drawable.rounded_corner_questionario));
                                    edits.add(editAnimation); // adiciona a nova editText a lista.
                                    ll.addView(editAnimation, params);

                                    ll.addView(spinner, params); // adiciona a editText ao ViewGroup
                                }
                            }

                            // CHECK
                            else if (cursor.getInt(2) == 2) {

                                if (!((cursor.getString(0)).equals(igual))) {
                                    igual = cursor.getString(0);
                                    count++;
                                    LinearLayout.LayoutParams params;

                                    params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                                    params.setMargins(0, 10, 0, 30);

                                    CheckBox checkbox = new CheckBox(this);
                                    checkbox.setTag(cursor.getString(0));

                                    String getPersonalizacaoRE = "";


                                    checkbox.setText(cursor.getString(3));

                                    checkbox.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/Roboto-Regular.ttf"));

                                    // colocar respostas
                                    if (colocaValorRadio(checkbox.getTag().toString())) {
                                        checkbox.setChecked(true);
                                    }

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

                                            //if (PassouPorAquiTextoTemp) {
                                            boolean pegarEstado = isChecked;

                                            Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{(NumeroPerguntaAtual), ((CheckBox) buttonView).getTag().toString()});
                                            cursorSALTO.moveToFirst();
                                            cursorSALTO.getCount();

                                            for (int i = 0; i < radionbuttons.size(); i++) {

                                                if (radionbuttons.get(i).isChecked()) {
                                                    bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ID_OPCAO_ID_ALIMENTO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), (radionbuttons.get(i).getTag().toString()),idAlimento});
                                                }
                                                radionbuttons.get(i).setChecked(false);
                                            }

                                            if (isChecked) {
                                                bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ID_OPCAO_ID_ALIMENTO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), ((CheckBox) buttonView).getTag().toString(),idAlimento});

                                                if (getPersonalizacaoBOOLEAN((NumeroPerguntaAtual), ((CheckBox) buttonView).getTag().toString())) {
                                                    insereRegistro(buttonView.getTag().toString(), ((CheckBox) buttonView).getText().toString(), 0, false);
                                                } else {
                                                    insereRegistro(buttonView.getTag().toString(), "", 0, false);
                                                }

                                                if (cursorSALTO.getCount() > 0) {
                                                    String n = cursorSALTO.getString(6);
                                                    if (!cursorSALTO.getString(6).equals("0")) {
                                                        InsereSalto(cursorSALTO.getString(6), ((CheckBox) buttonView).getTag().toString());
                                                    }
                                                }
                                            } else {
                                                bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ID_OPCAO_ID_ALIMENTO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), ((CheckBox) buttonView).getTag().toString(),idAlimento});
                                                if (cursorSALTO.getCount() > 0) {
                                                    String n = cursorSALTO.getString(6);
                                                    if (!cursorSALTO.getString(6).equals("0")) {
                                                        bd.execSQL(sql_delete.DEL_SALTO, new String[]{cursorSALTO.getString(1)});
                                                    }
                                                }
                                            }
                                        }
                                        //}
                                    });

                                    checkboxs.add(checkbox); // adiciona a nova editText a lista.
                                    ll.addView(checkbox, params); // adiciona a editText ao ViewGroup
                                }
                            }
                            // numero 4 o numero normal
                            else if (cursor.getInt(2) == 4) {
                                if (!((cursor.getString(0)).equals(igual))) {
                                    nMin = cursor.getInt(4);
                                    nMax = cursor.getInt(1);

                                    igual = cursor.getString(0);
                                    count++;
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                    params.setMargins(0, 10, 0, 30);

                                    EditText edit = new EditText(this);
                                    edit.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    edit.setBackgroundResource(R.drawable.rounded_corner);

                                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                                            .hideSoftInputFromWindow(edit.getWindowToken(), 0);

                                    MinhaTAG asTags = new MinhaTAG();

                                    edit.setTag(cursor.getString(0));

                                    // colocar respostas
                                    if (nestaPerguntaNaoColocaResposta()) {
                                        edit.setText(colocaValor(edit.getTag().toString()));
                                    }



                                    if (cursor.getString(3).indexOf("]]") > 0) {
                                        PERSONALIZACAOHint.add(cursor.getString(3));
                                        edit.setHint(cursor.getString(3).toString().replace(cursor.getString(3).toString().substring(cursor.getString(3).toString().indexOf("[["), cursor.getString(3).toString().indexOf("]]") + 2), " "));
                                        HintText.add(cursor.getString(3).toString().replace(cursor.getString(3).toString().substring(cursor.getString(3).toString().indexOf("[["), cursor.getString(3).toString().indexOf("]]") + 2), " "));
                                    } else {
                                        //edit.setHint(cursor.getString(3));
                                        HintText.add(cursor.getString(3));
                                        PERSONALIZACAOHint.add(cursor.getString(3));
                                    }
                                    edit.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/Roboto-Regular.ttf"));

                                    if (nFONTE.equals("P")) {
                                        edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                                    } else if (nFONTE.equals("M")) {
                                        edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                                    } else if (nFONTE.equals("G")) {
                                        edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                                    }

                                    TemEditText = edit;
                                    //HintText.add(edit.getTag().toString());
                                    TagText.add(edit.getTag().toString());
                                    AtualHint = cursor.getString(3);

                                    MaxText.add(Integer.toString(nMax));

                                    PERSONALIZAOText.add(edit.getTag().toString());
                                    AtualMax = nMax;
                                    PassouPorAquiTextoTemp = false;

                                    edit.setOnFocusChangeListener(new OnFocusChangeListener() {
                                        @Override
                                        public void onFocusChange(View v, boolean hasFocus) {
                                            if (hasFocus) {
                                                AtualMax = locazinaEditText((EditText) v);
                                                AtualHint = locazinaEditHINT((EditText) v);
                                                AtualPERSONALIZACAOHint = locazinaEditPERSONALIZACAO((EditText) v);
                                                TemEditText = ((EditText) v);
                                                PassouPorAquiTextoTemp = true;
                                            } else {
                                                //Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                                    edit.setOnTouchListener(new View.OnTouchListener() {
                                        public boolean onTouch(View v, MotionEvent event) {
                                            AtualMax = locazinaEditText((EditText) v);
                                            AtualHint = locazinaEditHINT((EditText) v);
                                            AtualPERSONALIZACAOHint = locazinaEditPERSONALIZACAO((EditText) v);
                                            TemEditText = ((EditText) v);
                                            ((EditText) v).setInputType(InputType.TYPE_CLASS_NUMBER);
                                            PassouPorAquiTextoTemp = true;

                                            return false;
                                        }
                                    });

                                    TextWatcher textWatcher = new TextWatcher() {
                                        public void afterTextChanged(Editable s) {
                                            if (PassouPorAquiTextoTemp) {
                                                for (int i = 0; i < radionbuttons.size(); i++) {
                                                    if (radionbuttons.get(i).isChecked()) {
                                                        bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ID_OPCAO_ID_ALIMENTO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), radionbuttons.get(i).getTag().toString(),idAlimento});
                                                    }
                                                    radionbuttons.get(i).setChecked(false);
                                                }

                                                bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ID_OPCAO_ID_ALIMENTO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), (TemEditText.getTag().toString()),idAlimento});
                                                if (s.length() > 0) {
                                                    insereRegistro((TemEditText.getTag().toString()), s.toString(), 0, false);

                                                    Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{(NumeroPerguntaAtual), TemEditText.getTag().toString()});
                                                    cursorSALTO.moveToFirst();
                                                    cursorSALTO.getCount();
                                                    if (cursorSALTO.getCount() > 0) {
                                                        String n = cursorSALTO.getString(6);

                                                        String varAtualHint = AtualPERSONALIZACAOHint;

                                                        if (AtualPERSONALIZACAOHint.contains(VariavelAPI.constante_descricao_nascimento)) {
                                                            idade = Integer.parseInt(s.toString());
                                                        }

                                                        String varAtualHintCompletoTM = "";

                                                        if (!cursorSALTO.getString(6).equals("0")) {


                                                            String varAtualHintCompleto = "";

                                                            if (varAtualHint.toString().indexOf("<") > 0) {


                                                                if (s.toString().length() > 6) {
                                                                    String tamanho = Integer.toString(AtualMax);
                                                                    if (tamanho.length() < s.toString().length()) {
                                                                        Toast.makeText(Questionario.this, "Atenção! Máximo permitido: " + tamanho.length(), Toast.LENGTH_LONG).show();
                                                                        s.clear();
                                                                    }
                                                                } else if (Integer.parseInt(s.toString()) < (Integer.parseInt(varAtualHintCompleto))) {
                                                                    InsereSalto(cursorSALTO.getString(6), TemEditText.getTag().toString());
                                                                } else {
                                                                    bd.execSQL(sql_delete.DEL_SALTO_PERGUNTA, new String[]{(NumeroPerguntaAtual)});
                                                                }


                                                            } else if (varAtualHint.toString().indexOf(">") > 0) {


                                                                if (s.toString().length() > 6) {
                                                                    String tamanho = Integer.toString(AtualMax);
                                                                    if (tamanho.length() < s.toString().length()) {
                                                                        Toast.makeText(Questionario.this, "Atenção! Máximo permitido: " + tamanho.length(), Toast.LENGTH_LONG).show();
                                                                        s.clear();
                                                                    }
                                                                } else if (Integer.parseInt(s.toString()) > (Integer.parseInt(varAtualHintCompleto))) {
                                                                    InsereSalto(cursorSALTO.getString(6), TemEditText.getTag().toString());
                                                                } else {
                                                                    bd.execSQL(sql_delete.DEL_SALTO_PERGUNTA, new String[]{(NumeroPerguntaAtual)});
                                                                }


                                                            } else {
                                                                InsereSalto(cursorSALTO.getString(6), TemEditText.getTag().toString());
                                                            }


                                                        } else {
                                                            if (varAtualHint.toString().indexOf("<") > 0) {

                                                                Cursor cursorvarAtualHintCompleto = bd.rawQuery(" SELECT R.VALOR FROM RESPOSTA R, OPCAO O WHERE R.ID_OPCAO = O.ID_OPCAO AND O.ID_OPCAO  = ?  AND R.ID_ALUNO =  ?  ", new String[]{varAtualHintCompletoTM, Integer.toString(AlunoAtual)});
                                                                cursorvarAtualHintCompleto.moveToFirst();
                                                                if (cursorvarAtualHintCompleto.getCount() > 0) {
                                                                    AtualMax = cursorvarAtualHintCompleto.getInt(0);
                                                                }


                                                                if (s.toString().length() > 6) {
                                                                    String tamanho = Integer.toString(AtualMax);
                                                                    if (tamanho.length() < s.toString().length()) {
                                                                        Toast.makeText(Questionario.this, "Atenção! Máximo permitido: " + tamanho, Toast.LENGTH_LONG).show();
                                                                        s.clear();
                                                                    }
                                                                } else if (s.toString().length() > 0) {
                                                                    String tamanho = Integer.toString(AtualMax);
                                                                    if (tamanho.length() < s.toString().length()) {
                                                                        Toast.makeText(Questionario.this, "Atenção! Máximo permitido: " + tamanho, Toast.LENGTH_LONG).show();
                                                                        s.clear();
                                                                    }
                                                                } else {
                                                                    bd.execSQL(sql_delete.DEL_SALTO_PERGUNTA, new String[]{(NumeroPerguntaAtual)});
                                                                }


                                                            } else if (varAtualHint.toString().indexOf(">") > 0) {

                                                                Cursor cursorvarAtualHintCompleto = bd.rawQuery(" SELECT R.VALOR FROM RESPOSTA R, OPCAO O WHERE R.ID_OPCAO = O.ID_OPCAO AND O.ID_OPCAO  = ?  AND R.ID_ALUNO =  ?  ", new String[]{varAtualHintCompletoTM, Integer.toString(AlunoAtual)});
                                                                cursorvarAtualHintCompleto.moveToFirst();
                                                                if (cursorvarAtualHintCompleto.getCount() > 0) {
                                                                    AtualMax = cursorvarAtualHintCompleto.getInt(0);
                                                                }

                                                                if (s.toString().length() > 6) {
                                                                    String tamanho = Integer.toString(AtualMax);
                                                                    if (tamanho.length() < s.toString().length()) {
                                                                        Toast.makeText(Questionario.this, "Atenção! Máximo permitido: " + tamanho, Toast.LENGTH_LONG).show();
                                                                        s.clear();
                                                                    }
                                                                } else if (s.toString().length() > 0) {
                                                                    String tamanho = Integer.toString(AtualMax);
                                                                    if (tamanho.length() < s.toString().length()) {
                                                                        Toast.makeText(Questionario.this, "Atenção! Máximo permitido: " + tamanho, Toast.LENGTH_LONG).show();
                                                                        s.clear();
                                                                    }
                                                                } else {
                                                                    bd.execSQL(sql_delete.DEL_SALTO_PERGUNTA, new String[]{(NumeroPerguntaAtual)});
                                                                }


                                                            }
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
                                                                Toast.makeText(Questionario.this, "Atenção! Máximo permitido: " + tamanho.length(), Toast.LENGTH_LONG).show();
                                                                s.clear();
                                                            }
                                                        } else if (Integer.parseInt(s.toString()) > (AtualMax)) {
                                                            Toast.makeText(Questionario.this, "Atenção! Máximo permitido:" + Integer.toString(AtualMax), Toast.LENGTH_LONG).show();
                                                            s.clear();
                                                        }
                                                    }
                                                } catch (Exception e) {

                                                }
                                            }
                                        }

                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                        }

                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        }

                                    };

                                    LinearLayout.LayoutParams paramsTextHelp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                                    paramsTextHelp.setMargins(0, 0, 0, 0);
                                    TextInputLayout editAnimation = new TextInputLayout(this);
                                    editAnimation.setTag(cursor.getString(0));
                                    editAnimation.setHelperText("  " + cursor.getString(3));

                                    editAnimation.setHelperTextTextAppearance(R.style.TextHelp);
                                    editAnimation.setBackground(getResources().getDrawable(R.drawable.rounded_corner_questionario));

                                    edit.addTextChangedListener(textWatcher);
                                    edit.setBackground(null);
                                    editAnimation.addView(edit, paramsTextHelp);
                                    edits.add(editAnimation); // adiciona a nova editText a lista.
                                    ll.addView(editAnimation, params);
                                }
                            }

                            // TEXTO
                            else if (cursor.getInt(2) == 3) {
                                if (!((cursor.getString(0)).equals(igual))) {
                                    nMin = cursor.getInt(4);
                                    nMax = cursor.getInt(1);

                                    igual = cursor.getString(0);
                                    count++;

                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

                                    params.setMargins(0, 10, 0, 30);
                                    EditText edit = new EditText(this);
                                    MinhaTAG asTags = new MinhaTAG();


                                    edit.setTag(cursor.getString(0));
                                    edit.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/Roboto-Regular.ttf"));
                                    edit.setBackgroundResource(R.drawable.rounded_corner);

                                    // colocar respostas
                                    if (nestaPerguntaNaoColocaResposta()) {
                                        edit.setText(colocaValor(edit.getTag().toString()));
                                    }

                                    if (nFONTE.equals("P")) {
                                        edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                                    } else if (nFONTE.equals("M")) {
                                        edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                                    } else if (nFONTE.equals("G")) {
                                        edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                                    }

                                    edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(nMax)});

                                    TemEditText = edit;

                                    edit.setInputType(InputType.TYPE_NULL);

                                    TagText.add(edit.getTag().toString());

                                    PERSONALIZAOText.add(edit.getTag().toString());
                                    MaxText.add(Integer.toString(nMax));
                                    AtualMax = nMax;
                                    PassouPorAquiTextoTemp = false;

                                    edit.setOnFocusChangeListener(new OnFocusChangeListener() {
                                        @Override
                                        public void onFocusChange(View v, boolean hasFocus) {
                                            if (hasFocus) {
                                                AtualMax = locazinaEditText((EditText) v);
                                                TemEditText = ((EditText) v);
                                                PassouPorAquiTextoTemp = true;

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
                                            PassouPorAquiTextoTemp = true;

                                            if (AtualMax == 111111) {
                                                ((EditText) v).setInputType(InputType.TYPE_CLASS_NUMBER);
                                            }

                                            return false;
                                        }
                                    });

                                    TextWatcher textWatcher = new TextWatcher() {
                                        public void afterTextChanged(Editable s) {
                                            for (int i = 0; i < radionbuttons.size(); i++) {
                                                if (radionbuttons.get(i).isChecked()) {
                                                    bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ID_OPCAO_ID_ALIMENTO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), radionbuttons.get(i).getTag().toString(),idAlimento});
                                                }
                                                radionbuttons.get(i).setChecked(false);
                                            }
                                            bd.execSQL(sql_delete.DEL_SALTO_PERGUNTA, new String[]{(NumeroPerguntaAtual)});

                                            bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_OPCAO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), (TemEditText.getTag().toString())});
                                            if (s.length() > 0) {
                                                insereRegistro((TemEditText.getTag().toString()), s.toString(), 0, false);
                                                Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{(NumeroPerguntaAtual), TemEditText.getTag().toString()});
                                                cursorSALTO.moveToFirst();
                                                cursorSALTO.getCount();
                                                if (cursorSALTO.getCount() > 0) {
                                                    String n = cursorSALTO.getString(6);
                                                    if (!cursorSALTO.getString(6).equals("0")) {
                                                        InsereSalto(cursorSALTO.getString(6), TemEditText.getTag().toString());
                                                    }
                                                }
                                            }
                                            if (!((s.toString().equals("")))) {
                                                if (s.toString().length() + 1 > AtualMax) {
                                                    Toast.makeText(Questionario.this, "Atenção! Máximo permitido:" + Integer.toString(AtualMax), Toast.LENGTH_LONG).show();
                                                }
                                            }

                                            if (!s.toString().equals("")) {
                                                if (s.toString().length() > 2) {
                                                    if (cursorPergunta.getString(7).equals(VariavelAPI.constant_chave_102)) {
                                                        progressBar.setVisibility(View.VISIBLE);
                                                        String alimento = s.toString();
                                                        DestruirTextView();
                                                        callApiAlimentos();

                                                        mInterfaceObject.getAlimentos(alimento).enqueue(objectCallback);
                                                    }
                                                } else {
                                                    DestruirStringAlimento();
                                                }

                                            }
                                        }

                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                                        }
                                    };

                                    LinearLayout.LayoutParams paramsTextHelp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                                    paramsTextHelp.setMargins(0, 0, 0, 0);
                                    TextInputLayout editAnimation = new TextInputLayout(this);
                                    editAnimation.setTag(cursor.getString(0));
                                    editAnimation.setHelperText("  " + cursor.getString(3));
                                    editAnimation.setHelperTextTextAppearance(R.style.TextHelp);
                                    editAnimation.setBackground(getResources().getDrawable(R.drawable.rounded_corner_questionario));

                                    edit.addTextChangedListener(textWatcher);
                                    edit.setBackground(null);
                                    editAnimation.addView(edit, paramsTextHelp);
                                    edits.add(editAnimation); // adiciona a nova editText a lista.
                                    ll.addView(editAnimation, params); // adiciona a editText ao ViewGroup
                                }

                                ///	DATA MASCARA
                            } else if (cursor.getInt(2) == 5) {
                                if (!((cursor.getString(0)).equals(igual))) {
                                    nMin = cursor.getInt(4);
                                    nMax = cursor.getInt(1);

                                    igual = cursor.getString(0);
                                    count++;

                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                                    params.setMargins(0, 10, 0, 30);
                                    //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                                    //MaskedEditText edit = new MaskedEditText(this);
                                    MaskedEditText edit = new MaskedEditText(this,
                                            "**/**/****",
                                            "*",
                                            null,
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
                                    edit.setInputType(InputType.TYPE_CLASS_NUMBER);

                                    edit.setBackgroundResource(R.drawable.rounded_corner);

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

                                    //edit.setHint(cursor.getString(3));
                                    edit.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/Roboto-Regular.ttf"));


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

                                    Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{(NumeroPerguntaAtual), edit.getTag().toString()});
                                    cursorSALTO.moveToFirst();
                                    cursorSALTO.getCount();

                                    if (cursorSALTO.getCount() > 0) {
                                        String n = cursorSALTO.getString(6);
                                        if (!cursorSALTO.getString(6).equals("0")) {
                                            InsereSalto(cursorSALTO.getString(6), edit.getTag().toString());
                                        }
                                    }

                                    TemEditText = edit;
                                    TagText.add(edit.getTag().toString());
                                    //HintText.add(edit.getTag().toString());
                                    PERSONALIZAOText.add(edit.getTag().toString());
                                    MaxText.add(Integer.toString(100));
                                    AtualMax = 100;

                                    edit.setOnFocusChangeListener(new OnFocusChangeListener() {
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
                                            for (int i = 0; i < radionbuttons.size(); i++) {
                                                if (radionbuttons.get(i).isChecked()) {
                                                    bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ID_OPCAO_ID_ALIMENTO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), radionbuttons.get(i).getTag().toString(),idAlimento});
                                                }
                                                radionbuttons.get(i).setChecked(false);
                                            }
                                            bd.execSQL(sql_delete.DEL_SALTO_PERGUNTA, new String[]{(NumeroPerguntaAtual)});

                                            bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_OPCAO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), (TempMaskEditText.getTag().toString())});
                                            if (s.length() > 0) {
                                                insereRegistro((TempMaskEditText.getTag().toString()), s.toString(), 0, false);
                                                Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{(NumeroPerguntaAtual), TempMaskEditText.getTag().toString()});
                                                cursorSALTO.moveToFirst();
                                                cursorSALTO.getCount();
                                                if (cursorSALTO.getCount() > 0) {
                                                    String n = cursorSALTO.getString(6);
                                                    if (!cursorSALTO.getString(6).equals("0")) {
                                                        InsereSalto(cursorSALTO.getString(6), TempMaskEditText.getTag().toString());
                                                    }
                                                }
                                            }
                                            if (!((s.toString().equals("")))) {
                                                if (s.toString().length() + 1 > AtualMax) {
                                                    Toast.makeText(Questionario.this, "Atenção! Máximo permitido:" + Integer.toString(AtualMax), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }

                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                                        }
                                    };

                                    LinearLayout.LayoutParams paramsTextHelp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                                    paramsTextHelp.setMargins(0, 0, 0, 0);

                                    edit.addTextChangedListener(textWatcher);
                                    editAnimation.addView(edit, paramsTextHelp);
                                    editAnimation.setTag(cursor.getString(0));
                                    editAnimation.setHelperText("  " + cursor.getString(3));

                                    editAnimation.setHelperTextTextAppearance(R.style.TextHelp);
                                    editAnimation.setBackground(getResources().getDrawable(R.drawable.rounded_corner_questionario));

                                    edits.add(editAnimation); // adiciona a nova editText a lista.
                                    edit.setBackground(null);
                                    ll.addView(nTextView, paramsTextHelp);
                                    ll.addView(nButton, paramsButton);
                                    ll.addView(editAnimation, params); // adiciona a editText ao ViewGroup

                                }
                            }
                            /// HORA MASCARA
                            else if (cursor.getInt(2) == 8) {
                                if (!((cursor.getString(0)).equals(igual))) {
                                    nMin = cursor.getInt(4);
                                    nMax = cursor.getInt(1);

                                    igual = cursor.getString(0);
                                    count++;


                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                                    //MaskedEditText edit = new MaskedEditText(this);
                                    MaskedEditText edit = new MaskedEditText(this,
                                            "**:**",
                                            "*",
                                            null,
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
                                    edit.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    edit.setBackgroundResource(R.drawable.rounded_corner);

                                    LinearLayout.LayoutParams paramsTextView = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, (int) 40d);
                                    LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams((int) 40d, (int) 40d);

                                    TextView nTextView = new TextView(this);
                                    nTextView.setText("eeee");
                                    nTextView.setVisibility(View.GONE);
                                    nTextView.setTag(cursor.getString(0));

                                    // colocar respostas
                                    if (nestaPerguntaNaoColocaResposta()) {
                                        String temp = colocaValor(edit.getTag().toString());
                                        if (!temp.equals("")) {
                                            temp = temp.trim();
                                            if (!temp.equals("")) {
                                                if (temp.equals(":")){
                                                    temp = "00:00";
                                                }

                                                edit.setText(temp);
                                            }
                                        }
                                    }

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

                                    //edit.setHint(cursor.getString(3));
                                    edit.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/Roboto-Regular.ttf"));

                                    if (nFONTE.equals("P")) {
                                        edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                                    } else if (nFONTE.equals("M")) {
                                        edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                                    } else if (nFONTE.equals("G")) {
                                        edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                                    }

                                    //edit.setFilters(new InputFilter[] {new InputFilter.LengthFilter(100)});

                                    Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{(NumeroPerguntaAtual), edit.getTag().toString()});
                                    cursorSALTO.moveToFirst();
                                    cursorSALTO.getCount();

                                    if (cursorSALTO.getCount() > 0) {
                                        String n = cursorSALTO.getString(6);
                                        if (!cursorSALTO.getString(6).equals("0")) {
                                            InsereSalto(cursorSALTO.getString(6), edit.getTag().toString());
                                        }
                                    }

                                    TemEditText = edit;
                                    TagText.add(edit.getTag().toString());
                                    //HintText.add(edit.getTag().toString());
                                    PERSONALIZAOText.add(edit.getTag().toString());
                                    MaxText.add(Integer.toString(100));
                                    AtualMax = 100;

                                    edit.setOnFocusChangeListener(new OnFocusChangeListener() {
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
                                            for (int i = 0; i < radionbuttons.size(); i++) {
                                                if (radionbuttons.get(i).isChecked()) {
                                                    bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ID_OPCAO_ID_ALIMENTO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), radionbuttons.get(i).getTag().toString(),idAlimento});
                                                }
                                                radionbuttons.get(i).setChecked(false);
                                            }
                                            bd.execSQL(sql_delete.DEL_SALTO_PERGUNTA, new String[]{(NumeroPerguntaAtual)});

                                            bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_OPCAO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), (TempMaskEditText.getTag().toString())});
                                            if (s.length() > 0) {
                                                insereRegistro((TempMaskEditText.getTag().toString()), s.toString(), 0, false);
                                                Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{(NumeroPerguntaAtual), TempMaskEditText.getTag().toString()});
                                                cursorSALTO.moveToFirst();
                                                cursorSALTO.getCount();
                                                if (cursorSALTO.getCount() > 0) {
                                                    String n = cursorSALTO.getString(6);
                                                    if (!cursorSALTO.getString(6).equals("0")) {
                                                        InsereSalto(cursorSALTO.getString(6), TempMaskEditText.getTag().toString());
                                                    }
                                                }
                                            }
                                            if (!((s.toString().equals("")))) {
                                                if (s.toString().length() + 1 > AtualMax) {
                                                    Toast.makeText(Questionario.this, "Atenção! Máximo permitido:" + Integer.toString(AtualMax), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }

                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                                        }
                                    };
                                    LinearLayout.LayoutParams paramsTextHelp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                                    paramsTextHelp.setMargins(50, 0, 20, 0);

                                    edit.addTextChangedListener(textWatcher);
                                    editAnimation.addView(edit, paramsTextHelp);
                                    editAnimation.setTag(cursor.getString(0));
                                    editAnimation.setHelperText("  " + cursor.getString(3));
                                    editAnimation.setHelperTextTextAppearance(R.style.TextHelp);
                                    editAnimation.setBackground(getResources().getDrawable(R.drawable.rounded_corner_questionario));
                                    edits.add(editAnimation); // adiciona a nova editText a lista.

                                    ll.addView(nTextView, paramsTextView);
                                    ll.addView(nButton, paramsButton);
                                    ll.addView(editAnimation, params); // adiciona a editText ao ViewGroup

                                }
                            }
                            /// R$ MASCARA
                            else if (cursor.getInt(2) == 9) {
                                if (!((cursor.getString(0)).equals(igual))) {
                                    nMin = cursor.getInt(4);
                                    nMax = cursor.getInt(1);

                                    igual = cursor.getString(0);
                                    count++;

                                    MaskedEditText edit;

                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                                    //MaskedEditText edit = new MaskedEditText(this);
                                    if (nMax == 4) {
                                        edit = new MaskedEditText(this,
                                                "R$ **,**",
                                                "*",
                                                null,
                                                new MaskedEditText.MaskIconCallback() {
                                                    @Override
                                                    public void onIconPushed() {
                                                        System.out.println("Icon pushed");

                                                        //Invoke here contact list or just clear input

                                                    }
                                                });
                                    } else if (nMax == 5) {
                                        edit = new MaskedEditText(this,
                                                "R$ ***,**",
                                                "*",
                                                null,
                                                new MaskedEditText.MaskIconCallback() {
                                                    @Override
                                                    public void onIconPushed() {
                                                        System.out.println("Icon pushed");

                                                        //Invoke here contact list or just clear input

                                                    }
                                                });
                                    } else if (nMax == 6) {
                                        edit = new MaskedEditText(this,
                                                "R$ *.***,**",
                                                "*",
                                                null,
                                                new MaskedEditText.MaskIconCallback() {
                                                    @Override
                                                    public void onIconPushed() {
                                                        System.out.println("Icon pushed");

                                                        //Invoke here contact list or just clear input

                                                    }
                                                });
                                    } else if (nMax == 7) {
                                        edit = new MaskedEditText(this,
                                                "R$ **.***,**",
                                                "*",
                                                null,
                                                new MaskedEditText.MaskIconCallback() {
                                                    @Override
                                                    public void onIconPushed() {
                                                        System.out.println("Icon pushed");

                                                        //Invoke here contact list or just clear input

                                                    }
                                                });
                                    } else {
                                        edit = new MaskedEditText(this,
                                                "R$ ***.***,**",
                                                "*",
                                                null,
                                                new MaskedEditText.MaskIconCallback() {
                                                    @Override
                                                    public void onIconPushed() {
                                                        System.out.println("Icon pushed");

                                                        //Invoke here contact list or just clear input

                                                    }
                                                });
                                    }

                                    MinhaTAG asTags = new MinhaTAG();


                                    TextInputLayout editAnimation = new TextInputLayout(this);
                                    editAnimation.setTag(cursor.getString(0));

                                    edit.setTag(cursor.getString(0));
                                    edit.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    edit.setBackgroundResource(R.drawable.rounded_corner);


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

                                    //edit.setHint(cursor.getString(3));
                                    edit.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/Roboto-Regular.ttf"));


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

                                    Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{(NumeroPerguntaAtual), edit.getTag().toString()});
                                    cursorSALTO.moveToFirst();
                                    cursorSALTO.getCount();

                                    if (cursorSALTO.getCount() > 0) {
                                        String n = cursorSALTO.getString(6);
                                        if (!cursorSALTO.getString(6).equals("0")) {
                                            InsereSalto(cursorSALTO.getString(6), edit.getTag().toString());
                                        }
                                    }

                                    TemEditText = edit;
                                    TagText.add(edit.getTag().toString());
                                    //HintText.add(edit.getTag().toString());
                                    PERSONALIZAOText.add(edit.getTag().toString());
                                    MaxText.add(Integer.toString(100));
                                    AtualMax = 100;

                                    edit.setOnFocusChangeListener(new OnFocusChangeListener() {
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
                                            for (int i = 0; i < radionbuttons.size(); i++) {
                                                if (radionbuttons.get(i).isChecked()) {
                                                    bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ID_OPCAO_ID_ALIMENTO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), radionbuttons.get(i).getTag().toString(),idAlimento});
                                                }
                                                radionbuttons.get(i).setChecked(false);
                                            }
                                            bd.execSQL(sql_delete.DEL_SALTO_PERGUNTA, new String[]{(NumeroPerguntaAtual)});

                                            bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_OPCAO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), (TempMaskEditText.getTag().toString())});
                                            if (s.length() > 0) {
                                                insereRegistro((TempMaskEditText.getTag().toString()), s.toString(), 0, false);
                                                Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{(NumeroPerguntaAtual), TempMaskEditText.getTag().toString()});
                                                cursorSALTO.moveToFirst();
                                                cursorSALTO.getCount();
                                                if (cursorSALTO.getCount() > 0) {
                                                    String n = cursorSALTO.getString(6);
                                                    if (!cursorSALTO.getString(6).equals("0")) {
                                                        InsereSalto(cursorSALTO.getString(6), TempMaskEditText.getTag().toString());
                                                    }
                                                }
                                            }
                                            if (!((s.toString().equals("")))) {
                                                if (s.toString().length() + 1 > AtualMax) {
                                                    Toast.makeText(Questionario.this, "Atenção! Máximo permitido:" + Integer.toString(AtualMax), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }

                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                                        }
                                    };
                                    LinearLayout.LayoutParams paramsTextHelp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                                    paramsTextHelp.setMargins(0, 0, 0, 0);

                                    edit.addTextChangedListener(textWatcher);
                                    editAnimation.addView(edit, paramsTextHelp);
                                    edits.add(editAnimation); // adiciona a nova editText a lista.

                                    ll.addView(nTextView, paramsTextView);
                                    ll.addView(nButton, paramsButton);
                                    ll.addView(editAnimation, params); // adiciona a editText ao ViewGroup

                                }
                            }
                            // Data
                            else if (cursor.getInt(2) == 10) {
                                if (!((cursor.getString(0)).equals(igual))) {
                                    nMin = cursor.getInt(4);
                                    nMax = cursor.getInt(1);

                                    igual = cursor.getString(0);
                                    count++;

                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                                    params.setMargins(0, 10, 0, 30);
                                    final EditText edit = new EditText(this);
                                    MinhaTAG asTags = new MinhaTAG();

                                    edit.setTag(cursor.getString(0));

                                    edit.setText(colocaValor(cursor.getString(0)));

                                    if (cursor.getString(3).contains(VariavelAPI.constante_descricao_nascimento)) {
                                        idadeBoolean = true;
                                    }

                                    edit.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/Roboto-Regular.ttf"));
                                    edit.setBackgroundResource(R.drawable.rounded_corner);

                                    if (nFONTE.equals("P")) {
                                        edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                                    } else if (nFONTE.equals("M")) {
                                        edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                                    } else if (nFONTE.equals("G")) {
                                        edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                                    }

                                    edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(nMax)});

                                    final Calendar c = Calendar.getInstance();
                                    final int[] mYear = {c.get(Calendar.YEAR)};
                                    final int[] mMonth = {c.get(Calendar.MONTH)};
                                    final int[] mDay = {c.get(Calendar.DAY_OF_MONTH)};

                                    //////

                                    edit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DatePickerDialog dpd = new DatePickerDialog(Questionario.this,
                                                    new DatePickerDialog.OnDateSetListener() {
                                                        @Override
                                                        public void onDateSet(DatePicker view, int year, int month, int day) {
                                                            try {
                                                            c.set(year, month, day);

                                                            String date = new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
                                                            edit.setText(date);

                                                            mYear[0] = c.get(Calendar.YEAR);
                                                            mMonth[0] = c.get(Calendar.MONTH);
                                                            mDay[0] = c.get(Calendar.DAY_OF_MONTH);

                                                            if (idadeBoolean) {
                                                                idade = Utility.getAge(mYear[0], mMonth[0], mDay[0]);
                                                            }
                                                            } catch (Exception e) {
                                                                idade = 0;
                                                            }
                                                        }
                                                    }, mYear[0], mMonth[0], mDay[0]);
                                            dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
                                            Calendar d = Calendar.getInstance();
                                            dpd.updateDate(d.get(Calendar.YEAR),d.get(Calendar.MONTH),d.get(Calendar.DAY_OF_MONTH));
                                            dpd.show();
                                        }
                                    });
                                    ////////////

                                    TemEditText = edit;

                                    edit.setInputType(InputType.TYPE_NULL);

                                    TagText.add(edit.getTag().toString());


                                    PERSONALIZAOText.add(edit.getTag().toString());
                                    MaxText.add(Integer.toString(nMax));
                                    AtualMax = nMax;
                                    PassouPorAquiTextoTemp = false;

                                    edit.setOnFocusChangeListener(new OnFocusChangeListener() {
                                        @Override
                                        public void onFocusChange(View v, boolean hasFocus) {
                                            if (hasFocus) {
                                                AtualMax = locazinaEditText((EditText) v);
                                                TemEditText = ((EditText) v);
                                                PassouPorAquiTextoTemp = true;

                                                DatePickerDialog dpd = new DatePickerDialog(Questionario.this,
                                                        new DatePickerDialog.OnDateSetListener() {
                                                            @Override
                                                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                                                try {
                                                                c.set(year, month, day);

                                                                String date = new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
                                                                edit.setText(date);

                                                                mYear[0] = c.get(Calendar.YEAR);
                                                                mMonth[0] = c.get(Calendar.MONTH);
                                                                mDay[0] = c.get(Calendar.DAY_OF_MONTH);

                                                                if (idadeBoolean) {
                                                                    idade = Utility.getAge(mYear[0], mMonth[0], mDay[0]);
                                                                }
                                                                } catch (Exception e) {
                                                                    idade = 0;
                                                                }
                                                            }
                                                        }, mYear[0], mMonth[0], mDay[0]);
                                                dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
                                                Calendar d = Calendar.getInstance();
                                                dpd.updateDate(d.get(Calendar.YEAR),d.get(Calendar.MONTH),d.get(Calendar.DAY_OF_MONTH));
                                                dpd.show();

                                               // edit.clearFocus();
                                            }
                                        }
                                    });
                                    edit.setOnTouchListener(new View.OnTouchListener() {
                                        public boolean onTouch(View v, MotionEvent event) {
                                            AtualMax = locazinaEditText((EditText) v);
                                            TemEditText = ((EditText) v);
                                            ((EditText) v).setInputType(InputType.TYPE_CLASS_TEXT);
                                            PassouPorAquiTextoTemp = true;

                                            if (AtualMax == 111111) {
                                                ((EditText) v).setInputType(InputType.TYPE_CLASS_NUMBER);
                                            }


                                            return false;
                                        }
                                    });

                                    TextWatcher textWatcher = new TextWatcher() {
                                        public void afterTextChanged(Editable s) {
                                            for (int i = 0; i < radionbuttons.size(); i++) {
                                                if (radionbuttons.get(i).isChecked()) {
                                                    bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ID_OPCAO_ID_ALIMENTO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), radionbuttons.get(i).getTag().toString(),idAlimento});
                                                }
                                                radionbuttons.get(i).setChecked(false);
                                            }
                                            bd.execSQL(sql_delete.DEL_SALTO_PERGUNTA, new String[]{(NumeroPerguntaAtual)});

                                            bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_OPCAO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), (TemEditText.getTag().toString())});
                                            if (s.length() > 0) {
                                                insereRegistro((TemEditText.getTag().toString()), s.toString(), 0, false);
                                                Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{(NumeroPerguntaAtual), TemEditText.getTag().toString()});
                                                cursorSALTO.moveToFirst();
                                                cursorSALTO.getCount();
                                                if (cursorSALTO.getCount() > 0) {
                                                    String n = cursorSALTO.getString(6);
                                                    if (!cursorSALTO.getString(6).equals("0")) {
                                                        InsereSalto(cursorSALTO.getString(6), TemEditText.getTag().toString());
                                                    }
                                                }
                                            }
                                            if (!((s.toString().equals("")))) {
                                                if (s.toString().length() + 1 > AtualMax) {
                                                    Toast.makeText(Questionario.this, "Atenção! Máximo permitido:" + Integer.toString(AtualMax), Toast.LENGTH_LONG).show();
                                                }
                                            }

                                            if (!s.toString().equals("")) {
                                                if (s.toString().length() > 2) {
                                                    if (cursorPergunta.getString(7).equals(VariavelAPI.constant_chave_102)) {
                                                        String alimento = s.toString();
                                                        callApiAlimentos();
                                                        mInterfaceObject.getAlimentos(alimento).enqueue(objectCallback);
                                                    }
                                                } else {
                                                    DestruirStringAlimento();
                                                }

                                            }
                                        }

                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                                        }
                                    };

                                    LinearLayout.LayoutParams paramsTextHelp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                                    paramsTextHelp.setMargins(0, 0, 0, 0);
                                    TextInputLayout editAnimation = new TextInputLayout(this);
                                    editAnimation.setTag(cursor.getString(0));
                                    editAnimation.setHelperText("  " + cursor.getString(3));
                                    editAnimation.setHelperTextTextAppearance(R.style.TextHelp);
                                    editAnimation.setBackground(getResources().getDrawable(R.drawable.rounded_corner_questionario));


                                    edit.addTextChangedListener(textWatcher);
                                    edit.setBackground(null);
                                    editAnimation.addView(edit, paramsTextHelp);
                                    edits.add(editAnimation); // adiciona a nova editText a lista.
                                    ll.addView(editAnimation, params); // adiciona a editText ao ViewGroup
                                }
                            }
                           /* else if (cursor.getInt(2) == 10) {
                                if (!((cursor.getString(0)).equals(igual))) {
                                    igual = cursor.getString(0);
                                    count++;
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                                    Time now = new Time();
                                    now.setToNow();
                                    Integer nDia = (now.monthDay);
                                    Integer nMes = (now.month) + 1;
                                    Integer nAno = (now.year);

                                   *//* final DatePickerDialog datePicker = new DatePickerDialog(
                                            this,
                                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                            null,
                                            nAno,
                                            nMes,
                                            nDia);

                                    datePicker.setCancelable(true);
                                    datePicker.setCanceledOnTouchOutside(true);
                                    datePicker.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    int dayOfMonth = datePicker.getDatePicker().getDayOfMonth();
                                                    int monthOfYear = datePicker.getDatePicker().getMonth() ;
                                                    int year = datePicker.getDatePicker().getYear();


                                                   idade =  Utility.getAge(year,monthOfYear,dayOfMonth);
                                                }
                                            });
*//*
                                    final Calendar c = Calendar.getInstance();
                                    final int[] mYear = {c.get(Calendar.YEAR)};
                                    final int[] mMonth = {c.get(Calendar.MONTH)};
                                    final int[] mDay = {c.get(Calendar.DAY_OF_MONTH)};

                                    textView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DatePickerDialog dpd = new DatePickerDialog(this,
                                                    new DatePickerDialog.OnDateSetListener() {
                                                        @Override
                                                        public void onDateSet(DatePicker view, int year, int month, int day) {
                                                            c.set(year, month, day);

                                                            mYear[0] = c.get(Calendar.YEAR);
                                                            mMonth[0] = c.get(Calendar.MONTH);
                                                            mDay[0] = c.get(Calendar.DAY_OF_MONTH);
                                                        }
                                                    }, mYear[0], mMonth[0], mDay[0]);
                                            dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
                                            Calendar d = Calendar.getInstance();
                                            dpd.updateDate(d.get(Calendar.YEAR),d.get(Calendar.MONTH),d.get(Calendar.DAY_OF_MONTH));
                                            dpd.show();
                                        }
                                    });

                                    Cursor cursorTEMP = bd.rawQuery(sql_select.GET_RESPOSTA_3, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual)});
                                    cursorTEMP.moveToFirst();
                                    Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{(NumeroPerguntaAtual), cursor.getString(0)});
                                    cursorSALTO.moveToFirst();
                                    cursorSALTO.getCount();

                                    if (cursorSALTO.getCount() > 0) {
                                        String n = cursorSALTO.getString(6);
                                        if (!cursorSALTO.getString(6).equals("0")) {
                                            InsereSalto(cursorSALTO.getString(6), cursor.getString(0));
                                        }
                                    }


                                    ///
                                    LinearLayout.LayoutParams paramsTextHelp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                                    paramsTextHelp.setMargins(0, 0, 0, 0);
                                    TextInputLayout editAnimation = new TextInputLayout(this);
                                    editAnimation.setTag(cursor.getString(0));
                                    editAnimation.setHelperText("  " + cursor.getString(3));
                                    editAnimation.setHelperTextTextAppearance(R.style.TextHelp);
                                    editAnimation.setBackground(getResources().getDrawable(R.drawable.rounded_corner_questionario));


                                    editAnimation.addView(datePicker.getDatePicker(), paramsTextHelp);
                                    edits.add(editAnimation); // adiciona a nova editText a lista.
                                    ll.addView(editAnimation, params); // adiciona a editText ao ViewGroup

                              }

                            }*/
                            // NUMERO
                            else if (cursor.getInt(2) == 1) {
                                if (!((cursor.getString(0)).equals(igual))) {
                                    nMin = cursor.getInt(4);
                                    nMax = cursor.getInt(1);

                                    igual = cursor.getString(0);
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

                                    insereRegistro(cursor.getString(0), Integer.toString(nMin), 0, false);

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

                                                bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual)});

                                                createStackOverflowAPI();
                                                //mInterfaceObject.deleteRespostaCrianca(AlunoAtualID,(NumeroPerguntaAtual)).enqueue(deletarRespostaCriancaCallback);

                                                insereRegistro(Integer.toString(ntag), Integer.toString(picker.getValue()), 0, true);

                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                        }
                                    });

                                    numberPickers.add(number); // adiciona a nova editText a lista.
                                    ll.addView(number, params); // adiciona a editText ao ViewGroup
                                }
                            }
                            // CHECK especial
                            else if (cursor.getInt(2) == 6) {
                                if (!((cursor.getString(0)).equals(igual))) {
                                    igual = cursor.getString(0);
                                    count++;
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

                                    CheckBox checkbox = new CheckBox(this);
                                    checkbox.setTag(cursor.getString(0));
                                    checkbox.setText(cursor.getString(3));
                                    checkbox.setTypeface(null, Typeface.NORMAL);

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
                                            boolean pegarEstado = isChecked;

                                            Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{(NumeroPerguntaAtual), ((CheckBox) buttonView).getTag().toString()});
                                            cursorSALTO.moveToFirst();
                                            cursorSALTO.getCount();

                                            if (isChecked) {
                                                TornaVisivelTextEspecial(Integer.toString(Integer.parseInt(buttonView.getTag().toString()) + 1), true);
                                                bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ID_OPCAO_ID_ALIMENTO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), ((CheckBox) buttonView).getTag().toString(),idAlimento});
                                                insereRegistro(buttonView.getTag().toString(), "", 0, false);

                                                if (cursorSALTO.getCount() > 0) {
                                                    String n = cursorSALTO.getString(6);
                                                    if (!cursorSALTO.getString(6).equals("0")) {
                                                        InsereSalto(cursorSALTO.getString(6), ((CheckBox) buttonView).getTag().toString());
                                                    }
                                                }
                                            } else {
                                                TornaVisivelTextEspecial(Integer.toString(Integer.parseInt(buttonView.getTag().toString()) + 1), false);
                                                bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ID_OPCAO_ID_ALIMENTO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), ((CheckBox) buttonView).getTag().toString(),idAlimento});
                                                bd.execSQL(sql_delete.DEL_SALTO_PERGUNTA, new String[]{(NumeroPerguntaAtual)});
                                                if (cursorSALTO.getCount() > 0) {
                                                    String n = cursorSALTO.getString(6);
                                                    if (!cursorSALTO.getString(6).equals("0")) {
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
                            // TEXTO especial
                            else {
                                if (!((cursor.getString(0)).equals(igual))) {
                                    nMin = cursor.getInt(4);
                                    nMax = cursor.getInt(1);

                                    igual = cursor.getString(0);
                                    count++;
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                                    EditText edit = new EditText(this);
                                    MinhaTAG asTags = new MinhaTAG();

                                    edit.setTag(cursor.getString(0));
                                    //edit.setHint(cursor.getString(3));
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
                                    //HintText.add(edit.getTag().toString());
                                    PERSONALIZAOText.add(edit.getTag().toString());
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
                                            bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_OPCAO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), (TemEditText.getTag().toString())});
                                            if (s.length() > 0) {
                                                insereRegistro((TemEditText.getTag().toString()), s.toString(), 0, false);
                                            }
                                            if (!((s.toString().equals("")))) {
                                                if (s.toString().length() + 1 > AtualMax) {
                                                    Toast.makeText(Questionario.this, "Atenção! Máximo permitido:" + Integer.toString(AtualMax), Toast.LENGTH_LONG).show();
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
                }
                cursor.moveToNext();
            }

            if (cursorPergunta.getString(7).equals(VariavelAPI.contant_chave_inicair_anterior_DETALIMESC2) || cursorPergunta.getString(7).equals(VariavelAPI.contant_chave_inicair_anterior_DETALIMDOMIC2) || cursorPergunta.getString(7).equals(VariavelAPI.contant_chave_inicair_anterior_DETALIMPAPEL2
            )) {
                buttonPersonalizado.setVisibility(View.VISIBLE);
                buttonPersonalizado2.setVisibility(View.INVISIBLE);
                imageButtonAvancar.setVisibility(View.INVISIBLE);
            }

            cursor.close();


        } else {
            subPerguntaAluno = 1;
            Cursor cursorID_OPCAO_PESSOA = bd.rawQuery(sql_select.GET_RESPOSTA_SUBFORMULARIO, new String[]{(Integer.toString(AlunoAtual)), (Integer.toString(subformulario))});
            cursorID_OPCAO_PESSOA.moveToFirst();
            if (cursorID_OPCAO_PESSOA.getCount() > 0) {
                cursorID_OPCAO_PESSOA.moveToLast();
                subPerguntaAluno = cursorID_OPCAO_PESSOA.getInt(0) + 1;
            }

            ///////////////////////
            // criacao subformulario
            ///////////////////////
            final EditText llPergunta = (EditText) findViewById(R.id.editEntrevistado);
            llPergunta.setText("SubFormulario");
            llPergunta.refreshDrawableState();
            boolean pssouaqui = false;
            while (((cursorPergunta.getInt(5)) == subformulario)) {

                pssouaqui = false;
                subformulario = cursorPergunta.getInt(5);
                count++;

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                EditText edit = new EditText(this);
                MinhaTAG asTags = new MinhaTAG();


                TextInputLayout editAnimation = new TextInputLayout(this);
                editAnimation.setTag(cursorPergunta.getString(0));

                edit.setTextColor(Color.BLUE);
                edit.setTag(cursorPergunta.getString(0));
                //edit.setText(cursorPergunta.getString(1));
                edit.setHint(cursorPergunta.getString(1));
                edit.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
                edit.setBackgroundResource(R.drawable.rounded_corner);
                edit.setHeight(20);

                edit.setFocusableInTouchMode(false);

                if (nFONTE.equals("P")) {
                    edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                } else if (nFONTE.equals("M")) {
                    edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                } else if (nFONTE.equals("G")) {
                    edit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                }

                edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});

                TemEditText = edit;

                edit.setInputType(InputType.TYPE_NULL);

                TagText.add(edit.getTag().toString());
                //HintText.add(edit.getTag().toString());
                PERSONALIZAOText.add(edit.getTag().toString());
                MaxText.add(Integer.toString(200));
                AtualMax = 200;

                edit.setOnFocusChangeListener(new OnFocusChangeListener() {
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
/*
						nomeOpcao = ((EditText)v).getHint().toString();
						numeroOpcao = Integer.parseInt(((EditText)v).getTag().toString());*/

                        ((EditText) v).setInputType(InputType.TYPE_CLASS_TEXT);

                        return false;
                    }
                });


                /// inicio
                edit.setOnLongClickListener(new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setClassName("com.softjads.cadeconsumo", "com.softjads.cadeconsumo.faz_opcao_dinamica");

                        intent.putExtra("numero_pergunta", ((EditText) v).getTag().toString());
                        intent.putExtra("numero_aluno", Integer.toString(AlunoAtual));


                        Cursor cursorGET_PERGUNTA_DESCRICAO = bd.rawQuery(sql_select.GET_PERGUNTA_DESCRICAO, new String[]{((EditText) v).getTag().toString()});
                        cursorGET_PERGUNTA_DESCRICAO.moveToFirst();

                        String GET_PERGUNTA_DESCRICAO = cursorGET_PERGUNTA_DESCRICAO.getString(1);


                        intent.putExtra("nome_pergunta", GET_PERGUNTA_DESCRICAO);
                        intent.putExtra("nFONTE", (nFONTE));
                        intent.putExtra("subPerguntaAluno", Integer.toString(subPerguntaAluno));


                        startActivityForResult(intent, 90);

                        return true;

                    }
                });
                /// fim


                TextWatcher textWatcher = new TextWatcher() {
                    public void afterTextChanged(Editable s) {

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


                if (cursorPergunta.isLast()) {
                    pssouaqui = true;
                    subformulario = subformulario + 1;
                }

                cursorPergunta.moveToNext();


            }

            if (pssouaqui) {
                subformulario = subformulario - 1;
            }

            AdicionarRegistro();
            mostrarnaGrid();
            cursorPergunta.moveToPrevious();
        }

    }

    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private void colocarImagens() {

        mensagemInformadoAlimento(llPergunta.getText().toString());
        llPergunta.setText("Clique na foto.");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;// - (convertPixelsToDp(15f,this) * 2);

        int tempImageID = 0;
        ConstraintLayout constraintLayout = new ConstraintLayout(this);
        constraintLayout.setTag("imagem");
        ConstraintSet constraintSet = new ConstraintSet();


        int tempWidth = (((width - ((int) convertDpToPixel(getResources().getDimension(R.dimen.imageTamanho),this)))) / 2);;

        int temHeight = 0;
        if (listimagens.size() <= 50){
            temHeight = height / (listimagens.size());
        }

        for (int i = 0; i < listimagens.size(); i++) {

            if (i % 2 == 0) {
                constraintLayout = new ConstraintLayout(this);
                constraintLayout.setTag("imagem");
                constraintSet = new ConstraintSet();

                ImageView imageView1 = new ImageView(this);
                imageView1.setId(View.generateViewId());
                imageView1.setScaleType(ImageView.ScaleType.FIT_START);

                imageView1.setLayoutParams(new ConstraintLayout.LayoutParams(
                        tempWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

                Picasso.get().load(listimagens.get(i).getUrl()).placeholder(R.drawable.progress_animation).into(imageView1);
                imageView1.setTag(Integer.toString(i));


                constraintLayout.addView(imageView1);

                LinearLayout.LayoutParams params;
                params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                params.setMargins(0, 0, 0, 0);
                ll.addView(constraintLayout, params);

                imageView1.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       imagemEstaMarca = true;
                                                       desmarcarImagem();
                                                       ((ImageView) view).setBackground(getResources().getDrawable(R.drawable.image_select));
                                                       Intent imageActivity = new Intent(Questionario.this, ImageActivity.class);
                                                       imageActivity.putExtra("imagem_url", listimagens.get(Integer.parseInt(view.getTag().toString())).getUrl());
                                                       bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ID_OPCAO_ID_ALIMENTO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), listimagensCodigo.get(Integer.parseInt(view.getTag().toString())),idAlimento});
                                                       insereRegistro(listimagensCodigo.get(Integer.parseInt(view.getTag().toString())), "", 0, true);

                                                       startActivity(imageActivity);
                                                   }
                                               }
                );

                tempImageID = imageView1.getId();
            } else  {
                ImageView imageView2 = new ImageView(this);
                imageView2.setId(View.generateViewId());
                imageView2.setScaleType(ImageView.ScaleType.FIT_START);

                imageView2.setLayoutParams(new ConstraintLayout.LayoutParams(
                            tempWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

                imageView2.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View view) {
                                                      imagemEstaMarca = true;
                                                      desmarcarImagem();
                                                      ((ImageView) view).setBackground(getResources().getDrawable(R.drawable.image_select));
                                                      Intent imageActivity = new Intent(Questionario.this, ImageActivity.class);
                                                      imageActivity.putExtra("imagem_url", listimagens.get(Integer.parseInt(view.getTag().toString())).getUrl());
                                                      bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_ID_OPCAO_ID_ALIMENTO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), listimagensCodigo.get(Integer.parseInt(view.getTag().toString())),idAlimento});
                                                      insereRegistro(listimagensCodigo.get(Integer.parseInt(view.getTag().toString())), "", 0, true);

                                                      startActivity(imageActivity);

                                                  }
                                              }
                );

                Picasso.get().load(listimagens.get(i).getUrl()).placeholder(R.drawable.progress_animation).into(imageView2);

                imageView2.setTag(Integer.toString(i));

                constraintLayout.addView(imageView2);
                constraintSet.clone(constraintLayout);
                constraintSet.connect(imageView2.getId(), ConstraintSet.START, tempImageID, ConstraintSet.END);

                tempImageID = imageView2.getId();

            }

            constraintSet.applyTo(constraintLayout);

        }

    }

    private AlertDialog mensagemInformadoAlimento(String mensagem) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(
                R.layout.custom_dialog_ok, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);

        TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
        nTextView.setText(mensagem);

        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                deleteDialog.dismiss();
            }
        });


        deleteDialog.show();

        return deleteDialog;
    }

    protected void AdicionarRegistro() {
        LinearLayout.LayoutParams paramsNovo = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        ImageButton imageButton = new ImageButton(this);
        imageButton.setImageResource(R.mipmap.user_mais_01);
        imageButton.setBackgroundColor(Color.WHITE);
        imageButtons.add(imageButton); // adiciona a nova editText a lista.
        ll.addView(imageButton, paramsNovo);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bd.execSQL(sql_delete.DEL_SALTO_TODOS, new String[]{});

                if (voltarparainsercao == false) {
                    InserirRegistroAdcionado();
                } else {
                    AvancarParaDepoisdoUltimo();
                }
                voltarparainsercao = false;

                mostrarnaGrid();
                DeletaBoolean = false;

                apagarValores();
                habilitaTUDO_OPCOES();
                desmarcarSUBFORMULARIO();
                tornarSUBFORMULARIOVisivel();
                DeletaBoolean = false;


            }
        });

        imageButton.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ((ImageButton) v).setImageResource(R.mipmap.user_mais_02);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        ((ImageButton) v).setImageResource(R.mipmap.user_mais_01);
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });

    }

    private void habilitaTUDO_OPCOES() {
        try {

            for (int i = 0; i < ll.getChildCount(); i++) {
                View child = ll.getChildAt(i);
                if (child instanceof EditText) {
                    EditText et = (EditText) child;
                    et.setVisibility(View.VISIBLE);
                }
            }
        } catch (Throwable ex) {
        }
    }

    public void DestruirString() {
        try {
            //for (int i = 0; i < ll.getChildCount(); i++){
            int i = ll.getChildCount();
            while (-2 < i - 1) {
                View child = ll.getChildAt(i);
                if (child instanceof Button) {
                    Button et = (Button) child;
                    ViewGroup parent = (ViewGroup) et.getParent();
                    parent.removeView(et);
                }
                i--;
            }
        } catch (Throwable ex) {

        }
    }

    public void DestruirTextView() {
        try {
            //for (int i = 0; i < ll.getChildCount(); i++){
            int i = ll.getChildCount();
            while (-2 < i - 1) {
                View child = ll.getChildAt(i);
                if (child instanceof TextView) {
                    TextView et = (TextView) child;
                    ViewGroup parent = (ViewGroup) et.getParent();
                    parent.removeView(et);
                }
                i--;
            }
        } catch (Throwable ex) {

        }
    }

    public void apagarValores() {
        try {

            for (int i = 0; i < ll.getChildCount(); i++) {
                View child = ll.getChildAt(i);
                if (child instanceof TextInputLayout) {
                    for (int y = 0; y < ((TextInputLayout) child).getChildCount(); y++) {
                        View child2 = ((TextInputLayout) child).getChildAt(y);
                        if (child2 instanceof EditText) {
                            EditText et = ((EditText) child2);
                            et.setText("");
                        }
                    }
                }
            }

        } catch (Throwable ex) {

        }
    }

    public void apagarValoresLinearLayout() {
        try {

            for (int i = 0; i < ll.getChildCount(); i++) {
                View child = ll.getChildAt(i);
                if (child instanceof TextInputLayout) {
                    int ii = ll.getChildCount();
                    while (-2 < ii - 1) {
                        View child1 = ((LinearLayout) child).getChildAt(ii);
                        if (child1 instanceof LinearLayout) {
                            LinearLayout et = (LinearLayout) child1;
                            ViewGroup parent = (ViewGroup) et.getParent();
                            parent.removeView(et);
                        } else if (child1 instanceof TextView) {
                            TextView et = (TextView) child1;
                            ViewGroup parent = (ViewGroup) et.getParent();
                            parent.removeView(et);
                        }
                        ii--;
                    }
                }
            }

        } catch (Throwable ex) {

        }
    }

    protected void mostrarnaGrid() {
        DestruirString();
        String Temp_SubPgunta = "";
        int pegaPerguntaTresPrimeiros = 0;
        int pegaPerguntaTresPrimeirosant = 0;
        String StrinValor = "";
        Cursor cursorbusca = bd.rawQuery(sql_select.GET_BUSCA_SUBPERGUNTA, new String[]{Integer.toString(AlunoAtual), Integer.toString(subformulario)});
        try {
            cursorbusca.moveToFirst();
            cursorbusca.getCount();
            if (cursorbusca.getCount() > 0) {
                LinearLayout.LayoutParams paramsNovo = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

                while (!cursorbusca.isAfterLast()) {

                    pegaPerguntaTresPrimeiros = Integer.parseInt(cursorbusca.getString(3).toString());

                    if (cursorbusca.getString(5).equals("0")) {
                        StrinValor = cursorbusca.getString(4);
                    } else if (cursorbusca.getString(5).equals("2")) {
                        StrinValor = cursorbusca.getString(4);
                    } else {
                        StrinValor = cursorbusca.getString(0);
                    }


                    if ((pegaPerguntaTresPrimeiros != pegaPerguntaTresPrimeirosant) && (pegaPerguntaTresPrimeirosant != 0)) {
                        Button button = new Button(this);
                        //button.setBackgroundColor(color.white);
                        button.setText(Temp_SubPgunta);
                        button.setTag(pegaPerguntaTresPrimeirosant);
                        Buttons.add(button); // adiciona a nova editText a lista.
                        ll.addView(button, 0, paramsNovo);
                        //ll.addView(button, paramsNovo);
                        Temp_SubPgunta = "";

                        AssociaEvento(button);

                    }
                    Temp_SubPgunta = Temp_SubPgunta + " - " + StrinValor;
                    pegaPerguntaTresPrimeirosant = pegaPerguntaTresPrimeiros;
                    cursorbusca.moveToNext();
                }
                Button button = new Button(this);
                //button.setBackgroundColor(color.white);
                button.setTag(pegaPerguntaTresPrimeirosant);
                button.setText(Temp_SubPgunta);
                Buttons.add(button); // adiciona a nova editText a lista.
                //ll.addView(button, paramsNovo);
                ll.addView(button, 0, paramsNovo);

                AssociaEvento(button);

            }
        } finally {
            cursorbusca.close();
        }
    }

    protected void AssociaEvento(Button button) {
        //
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                voltarparainsercao = true;


            }
        });

        button.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {


                subPerguntaAluno = Integer.parseInt(v.getTag().toString());

                //PreencherResposta_PARA_GRID(numeroInicio);

                tornarSUBFORMULARIOVisivel();
                marcaCheckSUBFORMULARIO();

                if (DeletaBoolean) {
                    apagaTodosRegistrosRespsotaAluno_Clicado();

                } else {
                    //AlertDialog tempAlertDialog;
                    //tempAlertDialog = apagaTodosRegistrosRespsotaAluno_Clicado();
                    voltarparainsercao = true;
                    DeletaBoolean = true;
                    v.setBackgroundColor(Color.RED);
                }
                return false;
            }
        });
    }

    private AlertDialog apagaTodosRegistrosRespsotaAluno_Clicado() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(
                R.layout.custom_dialog, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);

        TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
        nTextView.setText("ATENÇÃO! Tem ceterteza que deseja apagar esse registro? Se sim, escolha SIM e precione novamente!");

        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                bd.execSQL(sql_delete.DEL_TODOS_RESPOSTA_SUBPERGUNTA_nova, new String[]{Integer.toString(AlunoAtual), Integer.toString(subPerguntaAluno)});
                mostrarnaGrid();
                DeletaBoolean = false;
                apagarValores();

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

        return deleteDialog;
    }

    protected void DestruirPergunta() {
        ll.removeAllViewsInLayout();

    }

    protected void AvancarQuestionario(String pergunta) {
        try {


           /* if (cursorPergunta.isAfterLast()) {
                if (pergunta.contains(""))
                Intent WSActivity = new Intent(this, fim.class);
                startActivity(WSActivity);
                this.finish();
            }*/

            TagText = null;
            MaxText = null;
            HintText = null;
            PERSONALIZACAOHint = null;
            PERSONALIZAOText = null;

            EditText llPergunta = (EditText) findViewById(R.id.editEntrevistado);
            llPergunta.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/Roboto-Bold.ttf"));

            String pnomePergunta, pnomeRespsosta, nMax, nMin;

            int PerguntaAnterior;
            int nValor;
            nMax = "";
            nMin = "";
            Contador = 0;

            DestruirPergunta();

            nDataBase = new DataBase(this);
            bd = nDataBase.getReadableDatabase();

            String ValorSalto = "0";

            Cursor cursorSALTO = bd.rawQuery(sql_select.GET_SALTO, null);
            cursorSALTO.moveToFirst();

            int ttt = cursorPergunta.getCount();

            cursorPergunta.moveToFirst();

            if (!cursorSALTO.isAfterLast()) {
                while (!cursorSALTO.isAfterLast()) {
                    ValorSalto = cursorSALTO.getString(1);

                    if (cursorSALTO.getString(1) == "0") {
                        // BREAKPOINT N�O PARA NO BREAK
                        break;
                    } else if ((cursorSALTO.getString(1).equals(cursorPergunta.getString(7)))) {
                        // BREAKPOINT N�O PARA NO BREAK
                        break;
                    }  else if (eParaFechar(cursorSALTO.getString(1))) {
                        //   util.nome_crianca = nomeCrianca;
                        // Intent WSActivity = new Intent(this, fim.class);
                        //  startActivity(WSActivity);
                        this.finish();
                        break;
                    }
                    cursorPergunta.moveToNext();
                }
                bd.execSQL(sql_delete.DEL_SALTO, new String[]{ValorSalto});
            }

            if ((bloco == cursorPergunta.getInt(2)) || (cursorPergunta.getString(1) != "")) {
                llPergunta.setText(cursorPergunta.getString(3));
            } else {
            }

            NumeroPerguntaAtual = cursorPergunta.getString(6);
            pcodPergunta = cursorPergunta.getInt(0);
            pnomePergunta = cursorPergunta.getString(1);

            llPergunta.setTag(pcodPergunta);
            llPergunta.setText(pnomePergunta);
            llPergunta.refreshDrawableState();

            MaxText = new ArrayList<String>();
            HintText = new ArrayList<String>();
            PERSONALIZACAOHint = new ArrayList<String>();
            TagText = new ArrayList<String>();
            PERSONALIZAOText = new ArrayList<String>();

            criarNovaEditText();

            //   PreencherResposta((NumeroPerguntaAtual));

            cursorPergunta.moveToNext();

        } catch (Exception e) {
            finishAffinity();
            System.out.println(e.getMessage());
        }

    }

/*    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 90:
                tornarSUBFORMULARIOVisivel();
                marcaCheckSUBFORMULARIO();
                break;
        }
    }*/

    private void onInsert(Context context, ContentValues obj, String nTabela) {

        DB.getInstance(context).insert(nTabela, obj);
    }

    private void notificationDialog() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "tutorialspoint_01";


        Intent notificationIntent2 = new Intent(Questionario.this, Questionario.class); notificationIntent2.putExtra("ambienteTEMP", ambienteTEMP);
        notificationIntent2.putExtra("filtro_id_cliente", "");
        notificationIntent2.putExtra("filtro_id_pesquisa", "");
        notificationIntent2.putExtra("filtro_desc_pesquisa", "");
        notificationIntent2.putExtra("filtro_automatico", "");
        notificationIntent2.putExtra("filtro_previsao", "");
        notificationIntent2.putExtra("usuario", "bebeto");
        notificationIntent2.putExtra("Nomeusuario", "bebeto");
        notificationIntent2.putExtra("AlunoAtual", "0");
        notificationIntent2.putExtra("AlunoAtualID", "0");
        notificationIntent2.putExtra("saltoTEMP_NOVO","NOTIFICACAO/1");
        notificationIntent2.putExtra("nFONTE", "M");
        notificationIntent2.putExtra("nGPS", "false");
        notificationIntent2.putExtra("NomeGravacaoArquivo", "GRAVACAO_" + "bebeto" + "_" + System.currentTimeMillis() + ".amr");
        notificationIntent2.putExtra("opcao", Integer.toString(1));
        notificationIntent2.putExtra("opcaoQuestionario", Integer.toString(0));
        notificationIntent2.putExtra("opcaoQuestionarioFINAL", Integer.toString(0));

        if (true) {
            notificationIntent2.putExtra("nTIME", "1");
        } else {
            notificationIntent2.putExtra("nTIME", "0");
        }
        notificationIntent2.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent2.setAction(Intent.ACTION_MAIN);
        notificationIntent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity(Questionario.this, 0, notificationIntent2, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_MAX);
            // Configure the notification channel.
            notificationChannel.setDescription("Sample Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();
            notificationChannel.setSound(uri,att);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_icon)
                .setTicker("Tutorialspoint")
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(resultIntent)
                .setContentTitle("Cade - Consumo Alimentar")
                .setContentText("Relate a refeição que está consumindo agora")
                .setContentInfo("Information");


        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notificationBuilder.build());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = 3000;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);


    }

    public void insereRegistro(String pTag, String pvalue, int pPessoa, Boolean delete) {

        try {

            if (pTag.equals(VariavelAPI.constant_chave_1011)){
                refeicao_para_papel = pvalue;
                bd.execSQL(" update ALIMENTO set ALIMENTO_REFEICAO = '" + pvalue + "' WHERE ALIMENTO_REFEICAO ISNULL ");
                insereeAtualizaAlimentoeRefeicaoComoCodigo(pTag, pvalue);
            }


            ContentValues obj = new ContentValues();
            obj.put("ID_ALUNO", AlunoAtual);
            obj.put("ID_PERGUNTA", NumeroPerguntaAtual);
            obj.put("ID_OPCAO", pTag);
            obj.put("VALOR", pvalue);
            obj.put("ID_OPCAO_PESSOA", pPessoa);

            String temId = "";

            if (refeicao_para_papel.equals("0") || !NumeroPerguntaAtual.equals(VariavelAPI.constant_chave_1012)) {
                if (cursorPergunta.getString(7).equals(VariavelAPI.constant_chave_109)) {
                    temId = numero_refeicao_atual_domic;
                    obj.put("ID_ALIMENTO", numero_refeicao_atual_domic);
                }else{
                    if (colocaIDAlimento()) {
                        temId = idAlimento;
                        obj.put("ID_ALIMENTO", idAlimento);
                    } else {
                        obj.put("ID_ALIMENTO", "");
                    }
                }
            }else{
                if (!refeicao_para_papel.equals("0")){
                    temId = refeicao_para_papel;
                    obj.put("ID_ALIMENTO", refeicao_para_papel);
                }else{
                    obj.put("ID_ALIMENTO", "");
                }
            }

/*           if (cursorPergunta.getString(7).equals(VariavelAPI.constant_chave_109)) {
                obj.put("REFEICAO_DESCRICAO", numero_refeicao_atual_domic);
            } else {
                obj.put("REFEICAO_DESCRICAO", "");
            }*/


            RespostaAdd respostaAdd = new RespostaAdd();
            respostaAdd.setIdPergunta(NumeroPerguntaAtual);
            respostaAdd.setIdItemPergunta(pTag);

            respostaAdd.setIdAlimento(temId);

            respostaAdd.setValor(pvalue);
            respostaAdd.setTagLivre(idAlimento);

            this.onInsert(this, obj, sql_create.TABLE_RESPOSTA);

            createStackOverflowAPI();

          //  while (esperaFinalizar){
         //       Log.d("bebeto12", "teste" );
         //   }

            if (delete) {
                mInterfaceObject.postAdicionaCriancaDelete(respostaAdd, AlunoAtualID).enqueue(cadatrarRespostaCallback);
            }else{
                mInterfaceObject.postAdicionaCrianca(respostaAdd, AlunoAtualID).enqueue(cadatrarRespostaCallback);
            }



        } catch (Throwable ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void InsereSalto(String pSalto, String nItemPergunta) {
        ContentValues obj = new ContentValues();
        obj.put("ID_SALTO", NovoID_SALTO());
        obj.put("SALTO_PARA", pSalto);
        obj.put("PERGUNTA", NumeroPerguntaAtual);
        obj.put("ITEM_PERGUNTA", nItemPergunta);
        this.onInsert(this, obj, sql_create.TABLE_SALTO);
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

    public void addKeyListener(EditText edittext) {
        edittext.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                // if keydown and "enter" is pressed
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    // display a floating message
                    Toast.makeText(context, "enter!!!!!", Toast.LENGTH_LONG).show();
                    return true;

                } else if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_9)) {

                    // display a floating message
                    Toast.makeText(context, "nove!!!!!", Toast.LENGTH_LONG).show();
                    return true;
                }

                return false;
            }
        });
    }

    public void marcaCheck(String nOpcao, String nValorOpcao, String tipoValor, String VALOR) {
        try {
            for (int i = 0; i < ll.getChildCount(); i++) {
                View child = ll.getChildAt(i);
                if (child instanceof TextInputLayout) {
                    for (int y = 0; y < ((TextInputLayout) child).getChildCount(); y++) {
                        View child2 = ((TextInputLayout) child).getChildAt(y);
                        if (child2 instanceof EditText) {
                            EditText et = ((EditText) child2);
                            if (et.getTag().equals(nOpcao) && tipoValor.toString().equals("5")) {

                                et.setVisibility(View.GONE);
                                VisivelLabelButton(nOpcao, nValorOpcao, true, tipoValor);
                            } else if (et.getTag().equals(nOpcao) && tipoValor.toString().equals("8")) {

                                et.setVisibility(View.GONE);
                                VisivelLabelButton(nOpcao, nValorOpcao, true, tipoValor);
                            } else if (et.getTag().equals(nOpcao) && tipoValor.toString().equals("9")) {

                                et.setVisibility(View.GONE);
                                VisivelLabelButton(nOpcao, nValorOpcao, true, tipoValor);
                            } else if (et.getTag().equals(nOpcao)) {
                                et.setText(nValorOpcao);
                            }

                        }
                    }
                } else if (child instanceof CheckBox) {
                    CheckBox cb = (CheckBox) child;
                    if (cb.getTag().equals(nOpcao)) {
                        cb.setChecked(true);
                        // Novo codigo
                        Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{(NumeroPerguntaAtual), cb.getTag().toString()});
                        cursorSALTO.moveToFirst();
                        cursorSALTO.getCount();

                        if (cursorSALTO.getCount() > 0) {
                            if (!cursorSALTO.getString(6).equals("0")) {
                                InsereSalto(cursorSALTO.getString(6), cb.getTag().toString());
                            }
                        }
                    }
                } else if (child instanceof RadioButton) {
                    RadioButton rb = (RadioButton) child;
                    String n = rb.getTag().toString();
                    if (rb.getTag().equals(nOpcao)) {
                        rb.setChecked(true);
                        Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{(NumeroPerguntaAtual), rb.getTag().toString()});
                        cursorSALTO.moveToFirst();
                        cursorSALTO.getCount();

                        if (cursorSALTO.getCount() > 0) {
                            if (!cursorSALTO.getString(6).equals("0")) {
                                InsereSalto(cursorSALTO.getString(6), rb.getTag().toString());
                            }
                        }
                    }
                } else if (child instanceof NumberPicker) {
                    NumberPicker np = (NumberPicker) child;
                    String n = np.getTag().toString();
                    if (np.getTag().equals(nOpcao)) {
                        np.setValue(Integer.parseInt(nValorOpcao));
                    }
                } else if (child instanceof DatePicker) {
                    DatePicker dp = (DatePicker) child;
                    String n = dp.getTag().toString();
                    if (dp.getTag().equals(nOpcao)) {
                        if (nValorOpcao.indexOf("/") > 0) {
                            //String nDia = nValorOpcao.substring(0,nValorOpcao.indexOf("/"));
                            //nValorOpcao = nValorOpcao.substring(nValorOpcao.indexOf("/")+1,nValorOpcao.length());
                            //String nMes = nValorOpcao.substring(0,nValorOpcao.indexOf("/"));
                            //nValorOpcao = nValorOpcao.substring(nValorOpcao.indexOf("/")+1,nValorOpcao.length());
                            //String nAno = nValorOpcao;

                            //dp.init(Integer.parseInt(nAno), Integer.parseInt(nMes), Integer.parseInt(nDia),(OnDateChangedListener) dp.getOnFocusChangeListener());
                        }
                    }
                }
            }
        } catch (Throwable ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void PreencherResposta(String nPergunta) {
        Cursor cursorReposta = bd.rawQuery(sql_select.GET_RESPOSTA_3, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual)});
        try {
            cursorReposta.moveToFirst();
            cursorReposta.getCount();


            if (cursorReposta.getCount() > 0) {
                while (!cursorReposta.isAfterLast()) {
                    marcaCheck(cursorReposta.getString(2), cursorReposta.getString(4), cursorReposta.getString(3), cursorReposta.getString(5));
                    cursorReposta.moveToNext();
                }
            }

            if (subformulario > 0) {

                //AvancarParaDepoisdoUltimo();
                voltarparainsercao = false;
                mostrarnaGrid();
                DeletaBoolean = false;

            }


        } finally {
            cursorReposta.close();
        }
    }

    public void Ir_utimo() {

        Cursor cursorReposta = bd.rawQuery(sql_select.GET_RESPOSTA_1, new String[]{Integer.toString(AlunoAtual)});

        try {
            cursorReposta.moveToFirst();
            cursorReposta.getCount();

            if (cursorReposta.getCount() > 0) {
                if (cursorPergunta.isAfterLast()) {
                    cursorPergunta.moveToLast();
                }
                cursorReposta.moveToLast();

                while (!cursorPergunta.isAfterLast()) {
                    if (cursorPergunta.getInt(0) == cursorReposta.getInt(1)) {
                        if (estaPreenchido()) {
                            if (!cursorPergunta.isLast()) {
                                cursorPergunta.moveToNext();
                            }
                        }
                        break;
                    }
                    if (!cursorPergunta.isLast()) {
                        cursorPergunta.moveToNext();
                    } else {
                        break;
                    }
                }

            }
        } finally {
            cursorReposta.close();
        }
    }

    public void Localizador() {

        while (!cursorPergunta.isAfterLast()) {
            if (cursorPergunta.getString(0) == NumeroPerguntaAtual) {
                break;
            }
            if (!cursorPergunta.isLast()) {
                cursorPergunta.moveToNext();
            } else {
                break;
            }
        }

    }

    public void MoveAnteriorPergunta() {


        Cursor cursorReposta = bd.rawQuery(sql_select.GET_RESPOSTA_personalizado, new String[]{Integer.toString(AlunoAtual), opcaoQuestionario, opcaoQuestionarioFINAL});
        try {
            cursorReposta.moveToFirst();
            cursorReposta.getCount();
            cursorReposta.moveToLast();
            if (!((NumeroPerguntaAtual == (opcaoQuestionario)))) {
                if (cursorReposta.getCount() > 0) {
                    if ((cursorPergunta.isAfterLast())) {
                        cursorPergunta.moveToLast();
                    }
                    cursorReposta.moveToLast();

                    while (!cursorPergunta.isBeforeFirst()) {

                        if ((cursorPergunta.getInt(0) == cursorReposta.getInt(1))) {
                            if (estaPreenchido()) {
                                if (!cursorPergunta.isFirst()) {
                                    cursorPergunta.moveToPrevious();
                                }
                            }
                            break;
                        }
                        if (!cursorPergunta.isFirst()) {
                            cursorPergunta.moveToPrevious();
                        } else {
                            break;
                        }
                    }

                    NumeroPerguntaAtual = cursorPergunta.getString(6);

                    Cursor cursorReposta2 = bd.rawQuery(sql_select.GET_RESPOSTA_1, new String[]{Integer.toString(AlunoAtual), opcaoQuestionario, opcaoQuestionarioFINAL});
                    cursorReposta2.moveToLast();
                    while (!cursorPergunta.isBeforeFirst()) {

                        if (cursorReposta2.getInt(1) < cursorPergunta.getInt(0)) {
                            if (!cursorPergunta.isFirst()) {
                                cursorPergunta.moveToPrevious();
                            }
                        } else {
                            break;
                        }
                    }

                    AvancarQuestionario("");
                }
            }
        } finally {
            cursorReposta.close();
        }

    }

    public boolean estaPreenchidoDESCRICAO() {
        if (!cursorPergunta.isAfterLast()) {
            if (cursorPergunta.getString(7).equals(VariavelAPI.constant_chave_102)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public boolean estaPreenchido() {
        try {

            // minimo com o numero 1 significa obrigat�rio
            int obrigatorio = 0;
            boolean obrigatoioUm = false;
            boolean nBoolean = false;

            if (subPerguntaAluno > 1) {
                return true;
            }

            if (ll.getChildCount() == 0) {
                return true;
            }

            for (int i = 0; i < ll.getChildCount(); i++) {
                View child = ll.getChildAt(i);

                obrigatorio = 0;

                if (child.getTag().toString() == null) {
                    return true;
                }

                if (child instanceof ConstraintLayout) {
                    if (imagemEstaMarca){
                        return true;
                    }else{
                        return false;
                    }
                }

                if (!child.getTag().toString().equals("")) {

                    if (child.getTag().toString().equals("apagar")) {
                        return true;
                    }
                    Cursor Cursorminimo = bd.rawQuery(sql_select.GET_OBRIGATORIO, new String[]{(child.getTag().toString())});
                    Cursorminimo.moveToFirst();
                    if (Cursorminimo.getCount() > 0) {
                        obrigatorio = Cursorminimo.getInt(0);
                    }
                }

                if (obrigatorio == 1) {
                    // Nao vale sozinhoaqui n�o nada por que ele n�o tem o poder de ser preenchido isto � uma opcoa que depende de outra ser marcada ou preenchida
                } else if (child instanceof TextInputLayout) {
                    nBoolean = false;
                    for (int y = 0; y < ((TextInputLayout) child).getChildCount(); y++) {

                        View child2 = ((TextInputLayout) child).getChildAt(y);
                        if (child2 instanceof FrameLayout) {
                            for (int x = 0; x < ((FrameLayout) child2).getChildCount(); x++) {
                                View child3 = ((FrameLayout) child2).getChildAt(x);
                                if (child3 instanceof EditText) {
                                    EditText et = (EditText) child3;

                                    if (!et.getText().toString().trim().equals("")) {

                                        if (child2 instanceof MaskedEditText) {
                                            MaskedEditText ett = (MaskedEditText) child3;
                                            String r = ett.getText().toString();
                                            dateValidator = new DateValidator();
                                            Boolean EData = false;

                                            if (r.toString().indexOf("/") > 0) {
                                                if (r.toString().equals("  /  /    ")) {
                                                    EData = false;
                                                } else {
                                                    EData = true;
                                                }
                                            }

                                            if (EData) {
                                                if (dateValidator.isThisDateValid(r, "dd/MM/yyyy")) {
                                                    nBoolean = true;
                                                } else {
                                                    return false;
                                                }
                                            } else if (ett.getUnmaskedText().toString().equals(ett.getText())) {

                                            } else if (!ett.getUnmaskedText().toString().trim().equals("")) {
                                                nBoolean = true;
                                            } else if (obrigatorio == 2) {
                                                obrigatoioUm = true;
                                            }

                                        } else {
                                            if (!et.getText().toString().trim().equals("")) {
                                                nBoolean = true;
                                            }
                                        }
                                    } else if (obrigatorio == 2) {
                                        obrigatoioUm = true;
                                    }

                                } else if (child2 instanceof Spinner) {
                                    Spinner sp = (Spinner) child2;
                                    if (sp.getSelectedItemPosition() > 0) {
                                        nBoolean = true;
                                    } else {
                                        return false;
                                    }
                                } else if (child2 instanceof DatePicker) {
                                    nBoolean = true;

                                }
                            }
                        }else if (child2 instanceof Spinner) {
                            Spinner sp = (Spinner) child2;
                            if (sp.getSelectedItemPosition() > 0) {
                                nBoolean = true;
                            } else {
                                return false;
                            }
                        }else if (child2 instanceof LinearLayout) {
                            nBoolean = true;
                        }
                    }
                } else if (child instanceof CheckBox) {
                    CheckBox cb = (CheckBox) child;
                    if (cb.isChecked()) {
                        nBoolean = true;
                    } else if (obrigatorio == 2) {
                        obrigatoioUm = true;
                    }
                } else if (child instanceof RadioButton) {
                    RadioButton rb = (RadioButton) child;
                    if (rb.isChecked()) {
                        nBoolean = true;
                    } else if (obrigatorio == 2) {
                        obrigatoioUm = true;
                    }
                } else if (child instanceof NumberPicker) {
                    NumberPicker np = (NumberPicker) child;
                    nBoolean = true;
                } else if (child instanceof DatePicker) {
                    DatePicker dp = (DatePicker) child;
                    nBoolean = true;
                } else if (child instanceof TextView) {
                    TextView te = (TextView) child;
                    if (te.getVisibility() == View.VISIBLE) {
                        return true;
                    } else {
                        nBoolean = true;
                    }
                } else if (child instanceof Button) {
                    Button bu = (Button) child;
                    if (bu.getVisibility() == View.VISIBLE) {
                        return true;
                    }
                }else if (child instanceof LinearLayout) {
                    nBoolean = true;
                }
            }

            if (obrigatoioUm) {
                nBoolean = false;
            }

            return nBoolean;

        } catch (Throwable ex) {
            return true;
        }
    }

    public int NovoValorPerguntaVirtual() {
        int Retorno = 0;
        Cursor cursorVIRTUAL = bd.rawQuery(sql_select.GET_RESPOSTA_VIRTUAL, new String[]{Integer.toString(AlunoAtual)});
        cursorVIRTUAL.moveToFirst();
        if (cursorVIRTUAL.getCount() > 0) {
            Retorno = cursorVIRTUAL.getCount();
        }
        return Retorno;
    }

    public void insereControleInicio() {
        try {
            String DataCompleta;
            String horaCompleta;

            Time now = new Time();
            now.setToNow();

            DataCompleta = Integer.toString(now.monthDay);
            DataCompleta = DataCompleta + "/" + Integer.toString(now.month);
            DataCompleta = DataCompleta + "/" + Integer.toString(now.year);

            horaCompleta = Integer.toString(now.hour);
            horaCompleta = horaCompleta + ":" + Integer.toString(now.minute);
            horaCompleta = horaCompleta + ":" + Integer.toString(now.second);

            ContentValues obj = new ContentValues();
            obj.put("ID_ALUNO", AlunoAtual);
            obj.put("ID_USUARIO", usuario);
            obj.put("NM_USUARIO", Nomeusuario);
            obj.put("DATA_INICIO", DataCompleta.toString());
            obj.put("INICIO", horaCompleta.toString());
            obj.put("LONGITUDE", "sem gps");
            obj.put("LATITUDE", "sem gps");
            obj.put("GRAVACAO", NomeGravacaoArquivo);
            obj.put("NM_PESQUISA", filtro_desc_pesquisa);
            obj.put("PREVISAO", filtro_previsao);
            this.onInsert(context, obj, sql_create.TABLE_CONTROLE_INICIO);
        } catch (Throwable ex) {
        }
    }

    public String carregarsuario_login() {
        Properties properties = new Properties();
        try {
            FileInputStream fis;
            fis = new FileInputStream(MyConstant.nomeArquivoINI);
            properties.load(fis);
            String nusuariologin = properties.getProperty("conf.usuario_login");

            return nusuariologin;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    private String locazinaEditHINT(EditText c) {
        c.getTag();
        int i = 0;
        int j = 0;
        for (i = 0; i < HintText.size(); i++) {
            if (HintText.get(i) == c.getTag().toString()) {
                j = i;
            }

        }
        return (HintText.get(j));

    }

    private String locazinaEditPERSONALIZACAO(EditText c) {
        c.getTag();
        int i = 0;
        int j = 0;
        for (i = 0; i < PERSONALIZAOText.size(); i++) {
            if (PERSONALIZAOText.get(i) == c.getTag().toString()) {
                j = i;
            }

        }

        if (j >= PERSONALIZACAOHint.size() ){
            j = PERSONALIZACAOHint.size() - 1;
        }

        return (PERSONALIZACAOHint.get(j));

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

    public void TornaVisivelTextEspecial(String nOpcao, Boolean TornarVisivel) {
        try {
            for (int i = 0; i < ll.getChildCount(); i++) {
                View child = ll.getChildAt(i);
                if (child instanceof EditText) {
                    EditText et = (EditText) child;
                    if (et.getTag().equals(nOpcao)) {
                        if (TornarVisivel) {
                            et.setVisibility(View.VISIBLE);
                            if (nFONTE.equals("P")) {
                                et.setHeight(80);
                                et.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                            } else if (nFONTE.equals("M")) {
                                et.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                                et.setHeight(100);
                            } else if (nFONTE.equals("G")) {
                                et.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                                et.setHeight(120);
                            }
                        } else {
                            et.setVisibility(View.INVISIBLE);
                            et.setText("");
                            et.setHeight(0);
                        }
                    }
                }
            }
        } catch (Throwable ex) {

        }
    }


    public void TornaInvisivelTextView(boolean visible) {
        try {
            for (int i = 0; i < ll.getChildCount(); i++) {
                View child = ll.getChildAt(i);
                if (!(child instanceof CheckBox)) {
                    if (child instanceof TextView) {
                        TextView et = (TextView) child;
                        if (visible) {
                            et.setVisibility(View.VISIBLE);
                        }else{
                            et.setVisibility(View.GONE);
                        }
                    } else {
                        Log.d("bebeto12", "teste" + child.getTag());
                    }
                }
            }
        } catch (Throwable ex) {

        }
    }

    public boolean estaUmMarcadoCheckbox() {
        try {
            int count = 0;
            for (int i = 0; i < ll.getChildCount(); i++) {
                View child = ll.getChildAt(i);
                if ((child instanceof CheckBox)) {
                    CheckBox et = (CheckBox) child;
                    if (et.isChecked()) {
                        count++;
                    }

                }
            }
            if (count > 0 ) {
                return true;
            }else{
                return false;
            }

        } catch (Throwable ex) {
            return false;
        }
    }

    public List<String> listaGrupoCheckbox() {
        try {

            List<String> list = new ArrayList<String>();
            for (int i = 0; i < ll.getChildCount(); i++) {
                View child = ll.getChildAt(i);
                if ((child instanceof CheckBox)) {
                    CheckBox et = (CheckBox) child;
                    if (et.isChecked()) {
                        list.add(et.getTag().toString());
                    }

                }
            }
          return list;

        } catch (Throwable ex) {
            return null;
        }
    }


    public void TornaVisivelTextViewGrupo(List<String> listaGrupoCheckbox, boolean visible) {
        try {
            for (int y = 0; y < listaGrupoCheckbox.size(); y++) {
                for (int i = 0; i < ll.getChildCount(); i++) {
                    View child = ll.getChildAt(i);
                    if (!(child instanceof CheckBox)) {
                        if (child instanceof TextView) {
                            TextView et = (TextView) child;
                            if (data.get(((int) et.getTag())).getGruposAlimentares() != null)
                                for (int x = 0; x < data.get(((int) et.getTag())).getGruposAlimentares().size(); x++) {
                                    if (data.get(((int) et.getTag())).getGruposAlimentares().get(x).equals(listaGrupoCheckbox.get(y))) {
                                        if (visible) {
                                            et.setVisibility(View.VISIBLE);
                                        } else {
                                            et.setVisibility(View.GONE);
                                        }
                                        Log.d("bebeto12", "ok" + child.getTag());
                                    }
                                }

                        } else {
                            Log.d("bebeto12", "teste" + child.getTag());
                        }
                    }
                }
            }
        } catch (Throwable ex) {

        }
    }

    public void fechar() {
        this.finish();
    }





    public void VisivelLabelButton(String nOpcao, String valor, Boolean TornarVisivel, String ntipoValor) {
        try {

            for (int i = 0; i < ll.getChildCount(); i++) {
                View child = ll.getChildAt(i);
                if (child instanceof TextView) {
                    TextView et = (TextView) child;
                    if (et.getTag().equals(nOpcao)) {
                        if (TornarVisivel) {
                            if (ntipoValor.toString().equals("5")) {
                                et.setText("    Data: " + valor);
                            } else if (ntipoValor.toString().equals("8")) {
                                et.setText("    Hora: " + valor);
                            } else if (ntipoValor.toString().equals("9")) {
                                et.setText("    R$: " + valor);
                            } else {
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
    }

    private void desabilitaOPCOES(String opcaoDesabilita, boolean nboolean) {
        try {

            for (int i = 0; i < ll.getChildCount(); i++) {
                View child = ll.getChildAt(i);
                if (child instanceof EditText) {
                    EditText et = (EditText) child;
                    if (et.getTag().toString().equals(opcaoDesabilita)) {
                        if (nboolean) {
                            et.setVisibility(View.INVISIBLE);
                        } else {
                            et.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        } catch (Throwable ex) {
        }
    }

    protected void AvancarParaDepoisdoUltimo() {
        Cursor cursorID_OPCAO_PESSOA = bd.rawQuery(sql_select.GET_RESPOSTA_SUBFORMULARIO, new String[]{(Integer.toString(AlunoAtual)), (Integer.toString(subformulario))});

        try {
            cursorID_OPCAO_PESSOA.moveToFirst();
            if (cursorID_OPCAO_PESSOA.getCount() > 0) {
                cursorID_OPCAO_PESSOA.moveToLast();
                subPerguntaAluno = cursorID_OPCAO_PESSOA.getInt(0) + 1;
            }
        } finally {
            cursorID_OPCAO_PESSOA.close();
        }

    }

    public void marcaCheckSUBFORMULARIO() {

        try {

            Cursor cursorSALTOSUBFORMULARIO = bd.rawQuery(sql_select.GET_SALTO, new String[]{});
            cursorSALTOSUBFORMULARIO.moveToFirst();


            Cursor cursorMarcarSubformulario = null;
            cursorMarcarSubformulario = bd.rawQuery(sql_select.GET_RESPOSTA_subformulario, new String[]{Integer.toString(AlunoAtual), Integer.toString(subPerguntaAluno)});
            cursorMarcarSubformulario.moveToFirst();
            if (cursorMarcarSubformulario.getCount() > 0) {
                while (!cursorMarcarSubformulario.isAfterLast()) {

                    for (int i = 0; i < ll.getChildCount(); i++) {
                        cursorSALTOSUBFORMULARIO.moveToFirst();
                        View child = ll.getChildAt(i);
                        if (child instanceof TextInputLayout) {
                            for (int y = 0; y < ((TextInputLayout) child).getChildCount(); y++) {
                                View child2 = ((TextInputLayout) child).getChildAt(y);


                                if (child2 instanceof EditText) {
                                    EditText et = ((EditText) child2);
                                    if (cursorMarcarSubformulario.getString(1).toString().equals(et.getTag().toString())) {
                                        if (cursorMarcarSubformulario.getString(3).toString().equals("0")) {
                                            et.setText(cursorMarcarSubformulario.getString(5).toString());
                                        } else if (cursorMarcarSubformulario.getString(3).toString().equals("2")) {
                                            et.setText(cursorMarcarSubformulario.getString(5).toString());
                                        } else {
                                            et.setText(cursorMarcarSubformulario.getString(4).toString());
                                        }

                                    }


                                    if (cursorSALTOSUBFORMULARIO.getCount() > 0) {
                                        while (!cursorSALTOSUBFORMULARIO.isAfterLast()) {


                                            if (Integer.parseInt(et.getTag().toString()) > cursorSALTOSUBFORMULARIO.getInt(2)) {
                                                if (Integer.parseInt(et.getTag().toString()) < cursorSALTOSUBFORMULARIO.getInt(1)) {
                                                    et.setVisibility(View.GONE);
                                                }
                                            }


                                            cursorSALTOSUBFORMULARIO.moveToNext();

                                        }
                                    }

                                }


                            }
                        }
                    }
                    cursorMarcarSubformulario.moveToNext();
                }


            }
            //bd.execSQL(sql_delete.DEL_SALTO_TODOS, new String[]{});
        } catch (Throwable ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void tornarSUBFORMULARIOVisivel() {
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof TextInputLayout) {
                for (int y = 0; y < ((TextInputLayout) child).getChildCount(); y++) {
                    View child2 = ((TextInputLayout) child).getChildAt(y);
                    if (child2 instanceof EditText) {
                        EditText et = ((EditText) child2);
                        et.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void desmarcarSUBFORMULARIO() {

        try {

            for (int i = 0; i < ll.getChildCount(); i++) {

                View child = ll.getChildAt(i);
                if (child instanceof TextInputLayout) {
                    for (int y = 0; y < ((TextInputLayout) child).getChildCount(); y++) {
                        View child2 = ((TextInputLayout) child).getChildAt(y);
                        if (child2 instanceof EditText) {
                            EditText et = ((EditText) child2);
                            et.setText("");
                        }
                    }
                }
            }

        } catch (Throwable ex) {
            System.out.println(ex.getMessage());
        }

    }

    protected int ultimoAdicionado() {
        return subPerguntaAluno;
    }

    protected void InserirRegistroAdcionado() {
        subPerguntaAluno = ultimoAdicionado() + 1;
    }

    public void Ir_utimo_OPCAOQUESTIONARIO() {

        int numeroTemppergunta = 0;

        Cursor cursorReposta = bd.rawQuery(sql_select.GET_RESPOSTA_1, new String[]{Integer.toString(AlunoAtual)});

        try {
            cursorReposta.moveToFirst();
            cursorReposta.getCount();

            if (cursorReposta.getCount() > 0) {

                cursorReposta.moveToLast();
                while (!cursorReposta.isBeforeFirst()) {
                    if (cursorReposta.getInt(1) > 4) {
                        if (cursorReposta.getInt(1) > Integer.parseInt(opcaoQuestionarioFINAL)) {
                            if (cursorReposta.isFirst()) {
                                break;
                            }
                            cursorReposta.moveToPrevious();
                        } else {
                            break;
                        }
                    } else {
                        if (cursorReposta.getInt(1) > Integer.parseInt(opcaoQuestionario)) {
                            cursorReposta.moveToPrevious();
                        } else {
                            break;
                        }
                    }
                }
            }

            if (cursorReposta.getCount() > 0 && opcaoQuestionario != null) {
                if (cursorReposta.getInt(1) > Integer.parseInt(opcaoQuestionario)) {
                    numeroTemppergunta = cursorReposta.getInt(1);
                    if (numeroTemppergunta > Integer.parseInt(opcaoQuestionarioFINAL)) {
                        numeroTemppergunta = Integer.parseInt(opcaoQuestionarioFINAL);
                        //if (modulo.opcaoQuestionarioFINAL == 4){
                        //	 numeroTemppergunta = modulo.opcaoQuestionario;
                        //}
                        cursorReposta.moveToFirst();
                        int numeroTempperguntaTEMP = cursorReposta.getInt(1);
                        if (numeroTempperguntaTEMP > Integer.parseInt(opcaoQuestionarioFINAL)) {
                            numeroTemppergunta = Integer.parseInt(opcaoQuestionario);
                        }

                    }
                } else {
                    numeroTemppergunta = Integer.parseInt(opcaoQuestionario);
                }
            } else {
                numeroTemppergunta = Integer.parseInt(opcaoQuestionario);
            }

            //int o = cursorReposta.getInt(1);

            if (cursorPergunta.isAfterLast()) {
                cursorPergunta.moveToLast();
            }

            //o = cursorPergunta.getInt(0);
            //o = o + 1;
            while (!cursorPergunta.isAfterLast()) {


                if (cursorPergunta.getInt(0) == numeroTemppergunta) {
                    if (estaPreenchido()) {
                        if (!cursorPergunta.isLast()) {
                            //cursorPergunta.moveToNext();
                        }
                    }
                    break;
                }

                if (!cursorPergunta.isLast()) {
                    cursorPergunta.moveToNext();
                } else {
                    break;
                }
            }


        } finally {
            cursorReposta.close();
        }
    }

    private void callApiAlimentos() {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utility.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfaceObject = retrofit.create(InterfaceRetrofit.class);
    }

    public void DestruirStringAlimento() {
        try {
            //for (int i = 0; i < ll.getChildCount(); i++){
            int i = ll.getChildCount();
            while (-2 < i - 1) {
                View child = ll.getChildAt(i);
                if (child instanceof TextView) {
                    if (child.getTag().equals("apagar")) {
                        TextView et = (TextView) child;
                        ViewGroup parent = (ViewGroup) et.getParent();
                        parent.removeView(et);
                    }
                }
                i--;
            }
        } catch (Throwable ex) {

        }
    }

    public void insereRegistroAlimento(String pTag, String codigo, String pvalue, String novoItemDesseAlimento, String alimentoRefeicao) {
        ContentValues obj = new ContentValues();
        obj.put("ID_ALUNO", AlunoAtual);
        obj.put("ID", pTag);
        obj.put("CODIGO", codigo);
        obj.put("DESCRICAO", pvalue);
        obj.put("QUAL_E_ESSE_ITEM", novoItemDesseAlimento);
        obj.put("ALIMENTO_REFEICAO_ORDER", getRefeicaoOrder());

        if (!refeicao_para_papel.equals("0")){
            obj.put("ALIMENTO_REFEICAO", refeicao_para_papel);
            alimentoRefeicao = refeicao_para_papel;
        }

        insereeAtualizaAlimentoeRefeicao(pTag, codigo, pvalue, alimentoRefeicao, getRefeicaoOrder());

        this.onInsert(this, obj, sql_create.TABLE_ALIMENTO);
    }

    public void insereeAtualizaAlimentoeRefeicaoComoCodigo(String pTag, String alimentoRefeicao){

        Cursor cursorALIMENTO = bd.rawQuery(sql_select.GET_ALIMENTOS_REFEICAO, new String[]{Integer.toString(AlunoAtual), pTag});
        cursorALIMENTO.moveToFirst();
        cursorALIMENTO.getCount();

        if (cursorALIMENTO.getCount() > 0) {
            insereeAtualizaAlimentoeRefeicao(pTag, cursorALIMENTO.getString(3), cursorALIMENTO.getString(1), alimentoRefeicao, getRefeicaoOrder());
        }

    }

    public void insereeAtualizaAlimentoeRefeicaoComoCodigocomFOR(int valor1000){

        Cursor cursorALIMENTO = bd.rawQuery(sql_select.GET_ALIMENTOS_REFEICAO_ALL, new String[]{Integer.toString(AlunoAtual)});
        cursorALIMENTO.moveToFirst();
        cursorALIMENTO.getCount();

        if (cursorALIMENTO.getCount() > 0) {
            for (int i = 0; i < cursorALIMENTO.getCount(); i++ ) {
                insereeAtualizaAlimentoeRefeicao(cursorALIMENTO.getString(0), cursorALIMENTO.getString(3), cursorALIMENTO.getString(1), cursorALIMENTO.getString(4), valor1000);
                cursorALIMENTO.moveToNext();
            }
        }

    }

    public void insereeAtualizaAlimentoeRefeicao(String pTag, String codigo, String pvalue, String alimentoRefeicao, Integer alimentoOrder) {
        RespostaAlimento respostaAlimento = new RespostaAlimento();
        respostaAlimento.setId_crianca(Integer.toString(AlunoAtual));
        respostaAlimento.setId_alimento(pTag);
        respostaAlimento.setCodigo(codigo);
        respostaAlimento.setDescricao(pvalue);
        respostaAlimento.setAlimento_refeicao(alimentoRefeicao);
        respostaAlimento.setAlimento_order(alimentoOrder);

        Gson gson = new Gson();

        RespostaAdd respostaAdd = new RespostaAdd();
        respostaAdd.setIdPergunta(sql_create.TABLE_ALIMENTO);
        respostaAdd.setIdItemPergunta(pTag);
        respostaAdd.setValor(gson.toJson(respostaAlimento));
        respostaAdd.setTagLivre(sql_create.TABLE_ALIMENTO);
        respostaAdd.setIdAlimento(pTag);

        while (esperaFinalizar){
            Log.d("bebeto12", "teste" );
        }

        createStackOverflowAPI();
        mInterfaceObject.postAdicionaCrianca(respostaAdd, AlunoAtualID).enqueue(cadatrarRespostaCallback);
    }

    public void insereRegistroGruposAlimentos(String pTag, String pvalue) {
        ContentValues obj = new ContentValues();
        obj.put("ID_ALUNO", AlunoAtual);
        obj.put("ID_ALIMENTO", pTag);
        obj.put("DESCRICAO", pvalue);
        this.onInsert(this, obj, sql_create.TABLE_GRUPOS_ALIMENTOS);

        insereeAtualizaOsComplementos(pTag,pvalue,sql_create.TABLE_GRUPOS_ALIMENTOS);
    }

    public void insereRegistroModosPreparacao(String pTag, String pvalue) {
        ContentValues obj = new ContentValues();
        obj.put("ID_ALUNO", AlunoAtual);
        obj.put("ID_ALIMENTO", pTag);
        obj.put("DESCRICAO", pvalue);
        this.onInsert(this, obj, sql_create.TABLE_MODO_PREPARACAO);

        insereeAtualizaOsComplementos(pTag,pvalue,sql_create.TABLE_MODO_PREPARACAO);
    }

    public void insereRegistroAdicoes(String pTag, String pvalue) {
        ContentValues obj = new ContentValues();
        obj.put("ID_ALUNO", AlunoAtual);
        obj.put("ID_ALIMENTO", pTag);
        obj.put("DESCRICAO", pvalue);
        this.onInsert(this, obj, sql_create.TABLE_ADICOES);

        insereeAtualizaOsComplementos(pTag, pvalue, sql_create.TABLE_ADICOES);
    }

    public void insereRegistroMedidasCaseiras(String pTag, String pvalue) {
        ContentValues obj = new ContentValues();
        obj.put("ID_ALUNO", AlunoAtual);
        obj.put("ID_ALIMENTO", pTag);
        obj.put("DESCRICAO", pvalue);

        this.onInsert(this, obj, sql_create.TABLE_MEDIDAS_CASEIRAS);

        insereeAtualizaOsComplementos(pTag, pvalue, sql_create.TABLE_MEDIDAS_CASEIRAS);
    }


    public void insereeAtualizaOsComplementos(String pTag, String pvalue, String ValorTipo) {
        RespostaComplementosAlimento respostaComplementosAlimento = new RespostaComplementosAlimento();
        respostaComplementosAlimento.setId_crianca(Integer.toString(AlunoAtual));
        respostaComplementosAlimento.setId_alimento(pTag);
        respostaComplementosAlimento.setDescricao(pvalue);

        Gson gson = new Gson();

        RespostaAdd respostaAdd = new RespostaAdd();
        respostaAdd.setIdPergunta(ValorTipo);
        respostaAdd.setIdItemPergunta(pvalue);
        respostaAdd.setValor(gson.toJson(respostaComplementosAlimento));
        respostaAdd.setTagLivre(ValorTipo);
        respostaAdd.setIdAlimento(pTag);

        createStackOverflowAPI();

        while (esperaFinalizar){
            Log.d("bebeto12", "teste" );
        }

        mInterfaceObject.postAdicionaCrianca(respostaAdd, AlunoAtualID).enqueue(cadatrarRespostaCallback);
    }

    private AlertDialog desejaAdicionar(final View view, final String destino) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(
                R.layout.custom_dialog, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);

        TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
        nTextView.setText("Deseja incluir esse alimento?");

        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                insereRegistroAlimento(data.get(((int) view.getTag())).getId(), data.get(((int) view.getTag())).getCodigo(), ((TextView) view).getText().toString(), "1", colocaRefeicaoOrder());

                List<String> gruposAlimentos = data.get(((int) view.getTag())).getGruposAlimentares();
                if (gruposAlimentos != null) {
                    for (int x = 0; x < gruposAlimentos.size(); x++) {
                        insereRegistroGruposAlimentos(data.get(((int) view.getTag())).getId(), gruposAlimentos.get(x));
                    }
                }

                List<String> modosPreparacao = data.get(((int) view.getTag())).getModosPreparacao();
                if (modosPreparacao != null) {
                    for (int x = 0; x < modosPreparacao.size(); x++) {
                        insereRegistroModosPreparacao(data.get(((int) view.getTag())).getId(), modosPreparacao.get(x));
                    }
                }

                List<medidasCaseiras> medidasCaseiras = data.get(((int) view.getTag())).getMedidasCaseiras();
                if (medidasCaseiras != null) {
                    for (int x = 0; x < medidasCaseiras.size(); x++) {
                        Gson gson = new Gson();
                        insereRegistroMedidasCaseiras(data.get(((int) view.getTag())).getId(), gson.toJson(medidasCaseiras.get(x)));
                    }
                }

                List<String> adicoes = data.get(((int) view.getTag())).getAdicoes();
                if (adicoes != null) {
                    for (int x = 0; x < adicoes.size(); x++) {
                        insereRegistroAdicoes(data.get(((int) view.getTag())).getId(), adicoes.get(x));
                    }
                }

                if (destino.length() > 0) {

                    Cursor cursorSALTO = bd.rawQuery(sql_select.GET_OPCAO_OPCAO, new String[]{(NumeroPerguntaAtual), TemEditText.getTag().toString()});
                    cursorSALTO.moveToFirst();
                    cursorSALTO.getCount();
                    if (cursorSALTO.getCount() > 0) {
                        String n = cursorSALTO.getString(6);
                        if (!cursorSALTO.getString(6).equals("0")) {
                            InsereSalto(cursorSALTO.getString(6), TemEditText.getTag().toString());
                        }
                    }

                    AvancarQuestionario("");
                }

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

        return deleteDialog;
    }

    // personalizado
    private void criarAlimentosInseridos() {
        Cursor cursorALIMENTO = bd.rawQuery(sql_select.GET_ALIMENTOS, new String[]{Integer.toString(AlunoAtual)});
        cursorALIMENTO.moveToFirst();
        cursorALIMENTO.getCount();

        if (cursorALIMENTO.getCount() > 0) {
            for (int i = 0; i < cursorALIMENTO.getCount(); i++) {
                TextView textView = new TextView(getApplicationContext());
                textView.setText(cursorALIMENTO.getString(1));
                textView.setTag(i);

                textView.setPadding(20, 20, 20, 20);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                Resources res = getResources();

                ImageView nButton2 = new ImageView(this);
                nButton2.setImageResource(R.mipmap.ic_delete);

                final int newColor2 = res.getColor(R.color.red);
                nButton2.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
                nButton2.setTag(cursorALIMENTO.getString(0));

                nButton2.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bd.execSQL(sql_delete.DEL_ALIMENTO, new String[]{Integer.toString(AlunoAtual), (view.getTag().toString())});
                        apagarValoresLinearLayout();
                        criarAlimentosInseridos();
                    }
                });

                LinearLayout.LayoutParams params;
                params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 0, 10, 0);
                params.weight = 8f;

                LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams((int) 200d, (int) 170d);
                paramsButton.weight = 1f;

                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setTag("detalhes");
                linearLayout.setLayoutParams(params);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setGravity(Gravity.CENTER);
                paramsButton.gravity = Gravity.CENTER;

                linearLayout.addView(textView, params);
                linearLayout.addView(nButton2, paramsButton);

                //

                LinearLayout.LayoutParams paramsTextHelp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                paramsTextHelp.setMargins(0, 0, 0, 0);
                TextInputLayout editAnimation = new TextInputLayout(this);
                editAnimation.setTag(cursorALIMENTO.getString(0));
                if (cursorALIMENTO.getString(4)  != null) {
                    if (cursorALIMENTO.getString(4) != null && !cursorALIMENTO.getString(4).equals("")) {
                        editAnimation.setHelperText(" Refeição:" + cursorALIMENTO.getString(4));
                        editAnimation.setHelperTextTextAppearance(R.style.TextHelp10);
                    }else{
                        editAnimation.setHelperText("");
                        editAnimation.setHelperTextTextAppearance(R.style.TextHelp10);
                    }
                }else{
                    editAnimation.setHelperText("");
                    editAnimation.setHelperTextTextAppearance(R.style.TextHelp10);
                }

                editAnimation.setBackground(getResources().getDrawable(R.drawable.rounded_corner_questionario));

                editAnimation.addView(linearLayout, paramsTextHelp);
                edits.add(editAnimation); // adiciona a nova editText a lista.
                ll.addView(editAnimation, params);
                //
             //   ll.addView(linearLayout);

                cursorALIMENTO.moveToNext();
            }
        }
    }


    // personalizado
    private void criarRefeicaoInseridos(Boolean comOpcaoMais) {
        Cursor cursorREFEICAO = bd.rawQuery(sql_select.GET_ALIMENTOS_REFEICAO_DESCRICAO, new String[]{Integer.toString(AlunoAtual)});
        cursorREFEICAO.moveToFirst();
        cursorREFEICAO.getCount();

        if (cursorREFEICAO.getCount() > 0) {
            for (int i = 0; i < cursorREFEICAO.getCount(); i++) {
                TextView textView = new TextView(getApplicationContext());
                textView.setText(cursorREFEICAO.getString(0));
                textView.setTag(i);

                textView.setPadding(20, 20, 20, 20);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                Resources res = getResources();


                ImageView nButton2 = new ImageView(this);
                nButton2.setImageResource(R.mipmap.ic_sum);

                if (!comOpcaoMais) {
                    nButton2.setVisibility(View.INVISIBLE);
                }


                final int newColor2 = res.getColor(R.color.black);
                nButton2.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
                nButton2.setTag(cursorREFEICAO.getString(0));

                nButton2.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        numero_refeicao_atual_domic = view.getTag().toString();

                        idAlimento = numero_refeicao_atual_domic;

                        InsereSalto(VariavelAPI.constant_chave_107, VariavelAPI.constant_chave_107);

                        AvancarQuestionario("");
                    }
                });


                // Check in refeicao domiciliar
                /////////
                ImageView nButtonCheck = new ImageView(this);
                nButtonCheck.setImageResource
                        (R.drawable.ic_check_white_24dp);
                nButtonCheck.setTag("CHECK");

                final int newColor = res.getColor(R.color.green);
                nButtonCheck.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
                nButtonCheck.setTag(cursorREFEICAO.getString(0));

                boolean mostarCheck = false;

                if (colocaIDAlimento()) {
                    Cursor cursorTUDO_REFEICAO_CHECADO = bd.rawQuery(sql_select.GET_RESPOSTA_OPCAO_TOTAS_MAIOR_2, new String[]{Integer.toString(AlunoAtual), cursorREFEICAO.getString(0)});
                    cursorTUDO_REFEICAO_CHECADO.moveToFirst();
                    cursorTUDO_REFEICAO_CHECADO.getCount();
                    if ((cursorTUDO_REFEICAO_CHECADO.getCount() > 1) &&  (ambienteTEMP.equals(VariavelAPI.constante_variavel_domiciliar))) {
                        mostarCheck = true;
                    }
                }

                if (mostarCheck) {
                    nButtonCheck.setVisibility(View.VISIBLE);
                } else {
                    nButtonCheck.setVisibility(View.INVISIBLE);
                    buttonPersonalizadoBolean = false;
                    // coloca o botao invicivel, so volta para o visivel deposi que coletar todos
                    buttonPersonalizado.setVisibility(View.INVISIBLE);
                    buttonPersonalizado2.setVisibility(View.INVISIBLE);
                    imageButtonAvancar.setVisibility(View.INVISIBLE);
                }
                ///////


                // Editar

                ImageView nButtonEditar = new ImageView(this);
                nButtonEditar.setImageResource(R.mipmap.ic_edit);

                final int newColornEditar = res.getColor(R.color.red);
                nButtonEditar.setColorFilter(newColornEditar, PorterDuff.Mode.SRC_ATOP);
                nButtonEditar.setTag(cursorREFEICAO.getString(0));

                if (ambienteTEMP.equals(VariavelAPI.constante_variavel_papel) ) {
                    nButtonEditar.setVisibility(View.VISIBLE);
                }else{
                    nButtonEditar.setVisibility(View.INVISIBLE);
                }

                nButtonEditar.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         editarRefeicaodosAlimentos(view.getTag().toString());

                    }
                });

                LinearLayout.LayoutParams params;
                params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 0, 10, 0);
                params.weight = 8f;

                LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams((int) 200d, (int) 170d);
                paramsButton.weight = 1f;

                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setTag("detalhes");
                linearLayout.setLayoutParams(params);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setGravity(Gravity.CENTER);
                paramsButton.gravity = Gravity.CENTER;

                linearLayout.addView(textView, params);

                linearLayout.addView(nButtonEditar, paramsButton);
                linearLayout.addView(nButton2, paramsButton);
                linearLayout.addView(nButtonCheck, paramsButton);
                //

                LinearLayout.LayoutParams paramsTextHelp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                paramsTextHelp.setMargins(0, 0, 0, 0);
                TextInputLayout editAnimation = new TextInputLayout(this);
                editAnimation.setTag(cursorREFEICAO.getString(0));


                editAnimation.setBackground(getResources().getDrawable(R.drawable.rounded_corner_questionario));

                editAnimation.addView(linearLayout, paramsTextHelp);
                edits.add(editAnimation); // adiciona a nova editText a lista.
                ll.addView(editAnimation, params);


                cursorREFEICAO.moveToNext();
            }
        }
    }

    private AlertDialog editarRefeicaodosAlimentos(final String refeicao) {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(
                R.layout.custom_dialog, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);

        TextView nTextView = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
        nTextView.setText("ATENÇÃO! Tem ceterteza que deseja editar essa refeição?");

        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Cursor cursorALIMENTO = bd.rawQuery(sql_select.GET_ALIMENTOS_REFEICAO_NULL, new String[]{Integer.toString(AlunoAtual)});
                cursorALIMENTO.moveToFirst();
                cursorALIMENTO.getCount();

                if (cursorALIMENTO.getCount() > 0) {
                    for (int i = 0; i < cursorALIMENTO.getCount(); i++) {
                        insereeAtualizaAlimentoeRefeicaoComoCodigo(cursorALIMENTO.getString(0), refeicao);
                        cursorALIMENTO.moveToNext();
                    }
                }

                bd.execSQL(" update ALIMENTO set ALIMENTO_REFEICAO = NULL WHERE ALIMENTO_REFEICAO = '" + refeicao + "'");

                bd.execSQL(sql_delete.DEL_SALTO_TODOS, new String[]{});
                InsereSalto(VariavelAPI.constant_chave_1013, VariavelAPI.constant_chave_1013);
                saltoTEMP = "";
                AvancarQuestionario("");

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

        return deleteDialog;
    }

    private void criarAlimentosInseridosDetalhes(Boolean MostrarMais) {
        Cursor cursorALIMENTO = bd.rawQuery(sql_select.GET_ALIMENTOS, new String[]{Integer.toString(AlunoAtual)});
        cursorALIMENTO.moveToFirst();
        cursorALIMENTO.getCount();

        if (cursorALIMENTO.getCount() > 0) {
            for (int i = 0; i < cursorALIMENTO.getCount(); i++) {
                TextView textView = new TextView(getApplicationContext());
                textView.setText(cursorALIMENTO.getString(1));
                textView.setTag(i);


                textView.setPadding(20, 20, 20, 20);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                idAlimento = cursorALIMENTO.getString(0);

                Resources res = getResources();

                ImageView nButton = new ImageView(this);
                nButton.setImageResource
                        (R.drawable.ic_check_white_24dp);
                nButton.setTag("CHECK");

                final int newColor = res.getColor(R.color.green);
                nButton.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
                nButton.setTag(cursorALIMENTO.getString(0));

                boolean mostarCheck = false;

                if (colocaIDAlimento()) {
                    Cursor cursorTUDO_ALIMENTO_CHECADO = bd.rawQuery(sql_select.GET_RESPOSTA_OPCAO_TOTAS_MAIOR_3, new String[]{Integer.toString(AlunoAtual), idPerguntaParaAliemnto, idAlimento});
                    cursorTUDO_ALIMENTO_CHECADO.moveToFirst();
                    cursorTUDO_ALIMENTO_CHECADO.getCount();
                    if (cursorTUDO_ALIMENTO_CHECADO.getCount() > 2) {
                        mostarCheck = true;
                    }else if (cursorTUDO_ALIMENTO_CHECADO.getCount() == 2 && (ambienteTEMP.equals(VariavelAPI.constante_variavel_papel)) ) {
                        mostarCheck = true;
                    }
                }

                ImageView nButton2 = new ImageView(this);
                nButton2.setBackgroundResource(R.mipmap.ic_sum);
                nButton2.setTag(cursorALIMENTO.getString(0));
                if (!MostrarMais){
                    nButton2.setVisibility(View.INVISIBLE);
                }

                if (cursorALIMENTO.getInt(3) == 1){
                    nButton2.setVisibility(View.INVISIBLE);
                    mostarCheck = true;
                }



                if (mostarCheck) {
                    nButton.setVisibility(View.VISIBLE);
                } else {
                    nButton.setVisibility(View.INVISIBLE);
                    buttonPersonalizadoBolean = false;
                    // coloca o botao invicivel, so volta para o visivel deposi que coletar todos
                    buttonPersonalizado.setVisibility(View.INVISIBLE);
                    buttonPersonalizado2.setVisibility(View.INVISIBLE);
                    imageButtonAvancar.setVisibility(View.INVISIBLE);
                }

                final int newColor2 = res.getColor(R.color.abobora);
                nButton2.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);

                nButton2.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        numero_alimento_atual = view.getTag().toString();

                        pegaNomeAlimento(numero_alimento_atual);

                        AvancarQuestionario("");
                    }
                });

                LinearLayout.LayoutParams params;
                params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                params.setMargins(10, 0, 10, 0);
                params.weight = 8f;

                LinearLayout.LayoutParams paramsButton = new LinearLayout.LayoutParams((int) 200d, (int) 170d);
                paramsButton.weight = 1f;

                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setTag("detalhes");
                linearLayout.setLayoutParams(params);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setGravity(Gravity.CENTER);

                paramsButton.gravity = Gravity.CENTER;

                linearLayout.addView(textView, params);
                linearLayout.addView(nButton, paramsButton);
                linearLayout.addView(nButton2, paramsButton);
                ll.addView(linearLayout);

                cursorALIMENTO.moveToNext();
            }
        }
    }

    public boolean colocaIDAlimento() {
        Cursor cursorID_ALIEMNTO_DETALHE;

        if (ambienteTEMP != "") {
            if (ambienteTEMP.equals(VariavelAPI.constante_variavel_domiciliar)) {
                cursorID_ALIEMNTO_DETALHE = bd.rawQuery(sql_select.GET_TUDO_ALIMENTO_CHECADO_DOMIC, null);
            }else if (ambienteTEMP.equals(VariavelAPI.constante_variavel_escolar)) {
                cursorID_ALIEMNTO_DETALHE = bd.rawQuery(sql_select.GET_TUDO_ALIMENTO_CHECADO_ESC, null);
            }else{
                cursorID_ALIEMNTO_DETALHE = bd.rawQuery(sql_select.GET_TUDO_ALIMENTO_CHECADO_PAPEL, null);
            }


            cursorID_ALIEMNTO_DETALHE.moveToFirst();
            cursorID_ALIEMNTO_DETALHE.getCount();

            if (cursorID_ALIEMNTO_DETALHE.getCount() > 0) {
                idPerguntaParaAliemnto = cursorID_ALIEMNTO_DETALHE.getString(0);
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }


    }

    public boolean personalizado(String personalizado) {
        if (personalizado == null) {
            return true;
        } else {
            if (personalizado.length() > 3) {
                if (personalizado.indexOf("{{") >= 0) {
                    return false;
                }
            } else {
                return true;
            }
        }
        return true;
    }


    public String personalizadoTESTE(String personalizado) {
        if (personalizado == null) {
            return personalizado;
        } else {
            if (personalizado.length() > 3) {
                String personalizadoTEMP = "";
                if (personalizado.equals(VariavelAPI.constante_variavel_modo_preparo)) {
                    Cursor cursorMODO_PREPARACAO = bd.rawQuery(sql_select.GET_MODO_PREPARACAO, new String[]{numero_alimento_atual});
                    cursorMODO_PREPARACAO.moveToFirst();
                    cursorMODO_PREPARACAO.getCount();

                    if (cursorMODO_PREPARACAO.getCount() > 0) {
                        for (int i = 0; i < cursorMODO_PREPARACAO.getCount(); i++) {
                            personalizadoTEMP = personalizadoTEMP + cursorMODO_PREPARACAO.getString(1);
                            if (i < cursorMODO_PREPARACAO.getCount() - 1) {
                                personalizadoTEMP = personalizadoTEMP + "|";
                            }
                            cursorMODO_PREPARACAO.moveToNext();
                        }
                    }
                    return personalizadoTEMP;
                }
                if (personalizado.equals(VariavelAPI.constante_variavel_adicoes)) {
                    Cursor cursorADICAO = bd.rawQuery(sql_select.GET_ADICOES, new String[]{numero_alimento_atual});
                    cursorADICAO.moveToFirst();
                    cursorADICAO.getCount();

                    if (cursorADICAO.getCount() > 0) {
                        for (int i = 0; i < cursorADICAO.getCount(); i++) {
                            personalizadoTEMP = personalizadoTEMP + cursorADICAO.getString(1);
                            if (i < cursorADICAO.getCount() - 1) {
                                personalizadoTEMP = personalizadoTEMP + "|";
                            }
                            cursorADICAO.moveToNext();
                        }
                    }
                    return personalizadoTEMP;
                }
                if (personalizado.equals(VariavelAPI.constante_variavel_medida)) {
                    Cursor cursorMEDIDAS_CASEIRAS = bd.rawQuery(sql_select.GET_MEDIDAS_CASEIRAS, new String[]{numero_alimento_atual});
                    cursorMEDIDAS_CASEIRAS.moveToFirst();
                    cursorMEDIDAS_CASEIRAS.getCount();


                    if (cursorMEDIDAS_CASEIRAS.getCount() > 0) {


                        for (int i = 0; i < cursorMEDIDAS_CASEIRAS.getCount(); i++) {

                            if (cursorMEDIDAS_CASEIRAS.getString(1) != null) {

                                Gson gson = new Gson();
                                String tempMedida = cursorMEDIDAS_CASEIRAS.getString(1);

                                medidasCaseiras medidasCaseiras = gson.fromJson(tempMedida, medidasCaseiras.class);

                                personalizadoTEMP = personalizadoTEMP + medidasCaseiras.getNome();
                                if (i < cursorMEDIDAS_CASEIRAS.getCount() - 1) {
                                    personalizadoTEMP = personalizadoTEMP + "|";
                                }
                                cursorMEDIDAS_CASEIRAS.moveToNext();

                            }
                        }
                    }
                    return personalizadoTEMP;
                }

                if ((personalizado.substring(0, 2).equals("{{"))) {
                    if (personalizado.contains("}}")) {
                        if (personalizado.indexOf("}") > 0) {
                            personalizado = personalizado.substring(personalizado.indexOf("}") + 2, personalizado.length());
                        }
                    }
                } else {
                    if (personalizado.contains("{{")) {
                        if (personalizado.indexOf("{") > 0) {
                            personalizado = personalizado.substring(0, personalizado.indexOf("{") - 1);
                        }
                    }
                }


                return personalizado;
            } else {
                return personalizado;
            }
        }
    }



    protected void AdicionarYouTube() {
        LinearLayout.LayoutParams paramsNovo = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        ImageButton imageButton = new ImageButton(this);
        imageButton.setImageResource(R.mipmap.ic_youtube);
        imageButton.setBackgroundColor(Color.WHITE);
        imageButtons.add(imageButton); // adiciona a nova editText a lista.
        ll.addView(imageButton, paramsNovo);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watchYoutubeVideo("syXd7kgLSN8");

            }
        });

    }

    public void watchYoutubeVideo(String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }


    public void colocarValorAmbiente(String valor) {

        if (valor != null) {
            if (valor.contains(VariavelAPI.constante_variavel_domiciliar)) {
                ambienteTEMP = VariavelAPI.constante_variavel_domiciliar;
            } else if (valor.contains(VariavelAPI.constante_variavel_escolar)) {
                ambienteTEMP = VariavelAPI.constante_variavel_escolar;
            } else if (valor.contains(VariavelAPI.constante_variavel_papel)) {
                ambienteTEMP = VariavelAPI.constante_variavel_papel;
            }
        }
    }


    public boolean mostraAmbiente(String valor) {

        if (valor != null) {
            if (valor.contains(VariavelAPI.constante_variavel_regra1)) {
                if (idade < 7) {
                    return true;
                } else {
                    return false;
                }
            } else if (valor.contains(VariavelAPI.constante_variavel_regra2)) {
                if (idade >= 7) {
                    return true;
                } else {
                    return false;
                }
            } else if (valor.contains(VariavelAPI.constante_variavel_domiciliar)) {
                if (valor.contains(ambienteTEMP)) {
                    return true;
                } else {
                    return false;
                }
            } else if (valor.contains(VariavelAPI.constante_variavel_escolar)) {
                if (valor.contains(ambienteTEMP)) {
                    return true;
                } else {
                    return false;
                }
            } else if (valor.contains(VariavelAPI.constante_variavel_papel)) {
                if (valor.contains(ambienteTEMP)) {
                    return true;
                } else {
                    return false;
                }
            }
        }


        return true;

    }

    public boolean eParaFechar(String valor) {

        if (valor != null) {
            if (valor.contains(VariavelAPI.getConstante_variavel_fechar)) {
                return true;
            }
        }
        return false;
    }

    public boolean idadeMaior7(String valor, boolean maior7) {
        if (valor != null) {
            if (valor.contains(MAIOR_7ANO)) {

                if (maior7) {
                    return true;
                } else {
                    return false;
                }

            } else if (valor.contains(MENOR_OU_IGUAL_7ANOS)) {

                if (!maior7) {
                    return true;
                } else {
                    return false;
                }

            }
        }
        return true;
    }

    public boolean temIdadeFrase(String valor) {
        if (valor != null) {
            if (valor.contains(MAIOR_7ANO)) {
                return true;
            } else if (valor.contains(MENOR_OU_IGUAL_7ANOS)) {
                return true;
            }
        }
        return false;

    }

    /**
     * Check if this device has a camera
     */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }


    protected void AdicionarIconeFoto(Boolean tiraFoto) {
        LinearLayout.LayoutParams paramsNovo = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
/*        ImageButton imageButton = new ImageButton(this);
        imageButton.setImageResource(R.mipmap.ic_camera);
        imageButton.setBackgroundColor(Color.WHITE);
        imageButtons.add(imageButton); // adiciona a nova editText a lista.
        ll.addView(imageButton, paramsNovo);*/

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View childLayout = inflater.inflate(R.layout.item_foto,
                (ViewGroup) findViewById(R.id.contener));

        ConstraintLayout contener = childLayout.findViewById(R.id.contener);

        fotoCamera = childLayout.findViewById(R.id.foto_camera);
        fotoCamera2 = childLayout.findViewById(R.id.foto_camera2);
        fotoCameraElipse = childLayout.findViewById(R.id.foto_camera_elipse);
        foto_camera_dentro = childLayout.findViewById(R.id.foto_camera_dentro);

        foto_camera_dentro.setVisibility(View.INVISIBLE);
        fotoCameraElipse.setVisibility(View.INVISIBLE);

        if (!tiraFoto) {
            carregarFoto(fotoCamera, Integer.toString(AlunoAtual));
        } else {
            contener.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dispatchTakePictureIntent();
                }
            });

            fotoCamera2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dispatchTakePictureIntent();
                }
            });

            fotoCameraElipse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dispatchTakePictureIntent();
                }
            });
        }

        imageButtons.add(fotoCamera);

        // adiciona a nova editText a lista.
        ll.addView(childLayout, paramsNovo);


        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
        File newdir = new File(dir);
        newdir.mkdirs();


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            switch (requestCode) {
                case 1: {
                    if (resultCode == RESULT_OK) {
                        System.gc();
                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap = ImageUtils.getInstant().getCompressedBitmap(file.getPath(), true);
                        //  Bitmap bitmap = MediaStore.Images.Media
                        //  .getBitmap(this.getContentResolver(), Uri.fromFile(file));
                        if (bitmap != null) {
                            fotoCameraElipse.setVisibility(View.VISIBLE);
                            foto_camera_dentro.setVisibility(View.VISIBLE);

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();

                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] decodedProfilePicture = stream.toByteArray();

                            String fotoPerfilEncoded = "";

                            try {
                                fotoPerfilEncoded = Base64.encodeToString(decodedProfilePicture,
                                        Base64.DEFAULT);
                            } catch (OutOfMemoryError e) {
                                e.printStackTrace();

                                System.gc();

                                try {
                                    fotoPerfilEncoded = Base64.encodeToString(decodedProfilePicture,
                                            Base64.DEFAULT);
                                } catch (OutOfMemoryError e2) {
                                    e2.printStackTrace();
                                    // handle gracefully.
                                }
                            }

                            setSharedPreferencesServiceVistoriaFoto(fotoPerfilEncoded, Integer.toString(AlunoAtual));

                            fotoCamera.setImageBitmap(bitmap);
                        }
                    }
                    break;
                }
            }

        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    private void setSharedPreferencesServiceVistoriaFoto(String fotoPerfilEncoded, String numero) {
        SharedPreferencesService shared = new SharedPreferencesService(this);
        shared.setVistoriaFoto(fotoPerfilEncoded, numero);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {

                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.softjads.cadeconsumo.fileprovider2",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO_CODE);
            }
        }
    }

    private void carregarFoto(ImageView foto, String numero) {
        SharedPreferencesService shared = new SharedPreferencesService(this);
        String stringFoto = shared.getVistoriaFoto(numero);

        Object fotoProfile = null;
        if (!stringFoto.equals("0")) {
            try {

                insereRegistro(md5(stringFoto), stringFoto, 0, false);

                byte[] decodedByte = null;

                try {
                    decodedByte = Base64.decode(stringFoto, Base64.DEFAULT);
                    fotoProfile = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();

                    System.gc();

                    try {
                        fotoProfile = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
                    } catch (OutOfMemoryError e2) {
                        e2.printStackTrace();
                        // handle gracefully.
                    }
                }

                Glide.with(getApplicationContext())
                        .load(fotoProfile)
                        .into(foto);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onBackPressed() {

        if (arrayVoltar.size() > 0) {
            if (arrayVoltar.size() == 1){
                saltoTEMP = VariavelAPI.constant_chave_105;
            }else {
                saltoTEMP = arrayVoltar.get(arrayVoltar.size() - 2);
            }
            arrayVoltar.remove(arrayVoltar.size()-1);

            isBackPressed = true;

            if (!saltoTEMP.equals("")) {
                bd.execSQL(sql_delete.DEL_SALTO_TODOS, new String[]{});
                InsereSalto(saltoTEMP, saltoTEMP);
                saltoTEMP = "";
            }
            AvancarQuestionario("");
        }else{
            isBackPressed = true;
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle(getString(R.string.app_name))
                    .setMessage("Deseja voltar para a tela inicial?")
                    .setNegativeButton("Não", null)
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            if (NaoENotificacao){
                                Intent i = new Intent(Questionario.this, MainActivity.class);
                                startActivity(i);
                            }else{
                                Questionario.super.onBackPressed();
                            }
                        }
                    }).create().show();
        }

    }


    public void pintaSeta(int seta) {
        Resources res = getResources();
        final int newColor1 = res.getColor(R.color.color_primary2);

        imageView1.setColorFilter(newColor1, PorterDuff.Mode.SRC_ATOP);
        imageView2.setColorFilter(newColor1, PorterDuff.Mode.SRC_ATOP);
        imageView3.setColorFilter(newColor1, PorterDuff.Mode.SRC_ATOP);
        imageView4.setColorFilter(newColor1, PorterDuff.Mode.SRC_ATOP);

        imageView2.setBackgroundColor(getResources().getColor(R.color.color_primary2));
        imageView3.setBackgroundColor(getResources().getColor(R.color.color_primary2));
        imageView4.setBackgroundColor(getResources().getColor(R.color.color_primary2));

        constraintLayout1.setBackgroundColor(getResources().getColor(R.color.color_primary2));
        constraintLayout2.setBackgroundColor(getResources().getColor(R.color.color_primary2));
        constraintLayout3.setBackgroundColor(getResources().getColor(R.color.color_primary2));
        constraintLayout4.setBackgroundColor(getResources().getColor(R.color.color_primary2));

        final int newColor2 = res.getColor(R.color.color_primary);
        if (seta == 0) {
            imageView1.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
            constraintLayout1.setBackgroundColor(getResources().getColor(R.color.color_primary));
            imageView1.setBackgroundColor(getResources().getColor(R.color.color_primary2));
        } else if (seta == 1) {
            imageView1.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
            imageView2.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
            constraintLayout1.setBackgroundColor(getResources().getColor(R.color.color_primary));
            constraintLayout2.setBackgroundColor(getResources().getColor(R.color.color_primary));
            imageView1.setBackgroundColor(getResources().getColor(R.color.color_primary));
        } else if (seta == 2) {
            imageView1.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
            imageView2.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
            imageView3.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
            constraintLayout1.setBackgroundColor(getResources().getColor(R.color.color_primary));
            constraintLayout2.setBackgroundColor(getResources().getColor(R.color.color_primary));
            constraintLayout3.setBackgroundColor(getResources().getColor(R.color.color_primary));
            imageView1.setBackgroundColor(getResources().getColor(R.color.color_primary));
            imageView2.setBackgroundColor(getResources().getColor(R.color.color_primary));
        } else if (seta == 3) {
            imageView1.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
            imageView2.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
            imageView3.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
            imageView4.setColorFilter(newColor2, PorterDuff.Mode.SRC_ATOP);
            constraintLayout1.setBackgroundColor(getResources().getColor(R.color.color_primary));
            constraintLayout2.setBackgroundColor(getResources().getColor(R.color.color_primary));
            constraintLayout3.setBackgroundColor(getResources().getColor(R.color.color_primary));
            constraintLayout4.setBackgroundColor(getResources().getColor(R.color.color_primary));
            imageView1.setBackgroundColor(getResources().getColor(R.color.color_primary));
            imageView2.setBackgroundColor(getResources().getColor(R.color.color_primary));
            imageView3.setBackgroundColor(getResources().getColor(R.color.color_primary));
            imageView4.setBackgroundColor(getResources().getColor(R.color.color_primary));
        }
    }


    private void pegaNomeAlimento(String aliemntoid) {
        Cursor cursorGET_ALIMENTOS_nome = bd.rawQuery(sql_select.GET_ALIMENTOS_nome, new String[]{aliemntoid});
        cursorGET_ALIMENTOS_nome.moveToFirst();
        cursorGET_ALIMENTOS_nome.getCount();

        if (cursorGET_ALIMENTOS_nome.getCount() > 0) {
            idAlimento = aliemntoid;
            nomeAlimento = cursorGET_ALIMENTOS_nome.getString(1);
        }

    }


    private String colocaValor(String tag) {

        String retorna = "";

        if (cursorPergunta.isAfterLast()) {
            cursorPergunta.moveToPrevious();
        }
        if (cursorPergunta.getString(7).equals(VariavelAPI.constant_chave_109)) {
            Cursor cursorRESPOSTA_OPCAO_TOTAS_REFEICAO = bd.rawQuery(sql_select.GET_RESPOSTA_OPCAO_TOTAS_REFEICAO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), tag, numero_refeicao_atual_domic});
            cursorRESPOSTA_OPCAO_TOTAS_REFEICAO.moveToFirst();
            cursorRESPOSTA_OPCAO_TOTAS_REFEICAO.getCount();
            if (cursorRESPOSTA_OPCAO_TOTAS_REFEICAO.getCount() > 0) {
                return cursorRESPOSTA_OPCAO_TOTAS_REFEICAO.getString(4);
            } else {
                return "";
            }
        }

        Cursor cursorRESPOSTA_OPCAO_TOTAS = bd.rawQuery(sql_select.GET_RESPOSTA_OPCAO_TOTAS, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), tag, idAlimento});
        cursorRESPOSTA_OPCAO_TOTAS.moveToFirst();
        cursorRESPOSTA_OPCAO_TOTAS.getCount();
        if (cursorRESPOSTA_OPCAO_TOTAS.getCount() > 0) {
            retorna = cursorRESPOSTA_OPCAO_TOTAS.getString(4);
        } else {
            cursorRESPOSTA_OPCAO_TOTAS = bd.rawQuery(sql_select.GET_RESPOSTA_OPCAO_TOTAS, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), tag, ""});
            cursorRESPOSTA_OPCAO_TOTAS.moveToFirst();
            cursorRESPOSTA_OPCAO_TOTAS.getCount();
            if (cursorRESPOSTA_OPCAO_TOTAS.getCount() > 0) {
                retorna = cursorRESPOSTA_OPCAO_TOTAS.getString(4);
            }
        }


        return retorna;
    }

    private Boolean colocaValorRadio(String tag) {

        Boolean retorna = false;

        if (cursorPergunta.getString(7).equals(VariavelAPI.constant_chave_107)) {
            Cursor cursorRESPOSTA_OPCAO_TOTAS_REFEICAO = bd.rawQuery(sql_select.GET_RESPOSTA_OPCAO_TOTAS_REFEICAO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), tag, numero_refeicao_atual_domic});
            cursorRESPOSTA_OPCAO_TOTAS_REFEICAO.moveToFirst();
            cursorRESPOSTA_OPCAO_TOTAS_REFEICAO.getCount();
            if (cursorRESPOSTA_OPCAO_TOTAS_REFEICAO.getCount() > 0) {
                return true;
            } else {
                return false;
            }
        }

        Cursor cursorRESPOSTA_OPCAO_TOTAS = bd.rawQuery(sql_select.GET_RESPOSTA_OPCAO_TOTAS, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), tag, idAlimento});
        cursorRESPOSTA_OPCAO_TOTAS.moveToFirst();
        cursorRESPOSTA_OPCAO_TOTAS.getCount();
        if (cursorRESPOSTA_OPCAO_TOTAS.getCount() > 0) {
            retorna = true;
        } else {
            cursorRESPOSTA_OPCAO_TOTAS = bd.rawQuery(sql_select.GET_RESPOSTA_OPCAO_TOTAS, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), tag, ""});
            cursorRESPOSTA_OPCAO_TOTAS.moveToFirst();
            cursorRESPOSTA_OPCAO_TOTAS.getCount();
            if (cursorRESPOSTA_OPCAO_TOTAS.getCount() > 0) {
                retorna = true;
            }
        }

        return retorna;
    }


    private boolean estaRefeicaoEstaCompleta(String tag) {
        Cursor cursorRESPOSTA_OPCAO_TOTAS_REFEICAO = bd.rawQuery(sql_select.GET_RESPOSTA_OPCAO_TOTAS_REFEICAO, new String[]{Integer.toString(AlunoAtual), (NumeroPerguntaAtual), tag, numero_refeicao_atual_domic});
        cursorRESPOSTA_OPCAO_TOTAS_REFEICAO.moveToFirst();
        cursorRESPOSTA_OPCAO_TOTAS_REFEICAO.getCount();
        if (cursorRESPOSTA_OPCAO_TOTAS_REFEICAO.getCount() > 1) {
            return true;
        } else {
            return false;
        }


    }


    private boolean gravaAlimento(String personalizacao) {
        if (personalizacao.equals(VariavelAPI.constante_descricoa_gravar)) {
            pegarAlimentoInserido();
            return true;
        } else {
            return false;
        }

    }

    public void pegarAlimentoInserido() {
        try {

            Cursor cursorALIMENTO_INSERIDO = bd.rawQuery(sql_select.GET_ALIMENTO_INSERIDO, null);
            cursorALIMENTO_INSERIDO.moveToFirst();
            cursorALIMENTO_INSERIDO.getCount();

            String alimento = "";
            String medidacaseira = "";
            String modopreparacao = "";
            String quantidade = "";

            if (cursorALIMENTO_INSERIDO.getCount() > 0) {
                String numeropergunta = cursorALIMENTO_INSERIDO.getString(0);

                Cursor cursorRESPOSTA_OPCAO_NOME_ALIMENTO = bd.rawQuery(sql_select.GET_RESPOSTA_OPCAO_NOME_ALIMENTO, new String[]{Integer.toString(AlunoAtual), (numeropergunta)});
                cursorRESPOSTA_OPCAO_NOME_ALIMENTO.moveToFirst();
                cursorRESPOSTA_OPCAO_NOME_ALIMENTO.getCount();
                if (cursorRESPOSTA_OPCAO_NOME_ALIMENTO.getCount() > 0) {
                    alimento = cursorRESPOSTA_OPCAO_NOME_ALIMENTO.getString(4);
                }

                Cursor cursorRESPOSTA_OPCAO_MEDIDA_CASEIRA = bd.rawQuery(sql_select.GET_RESPOSTA_OPCAO_MEDIDA_CASEIRA, new String[]{Integer.toString(AlunoAtual), (numeropergunta)});
                cursorRESPOSTA_OPCAO_MEDIDA_CASEIRA.moveToFirst();
                cursorRESPOSTA_OPCAO_MEDIDA_CASEIRA.getCount();
                if (cursorRESPOSTA_OPCAO_MEDIDA_CASEIRA.getCount() > 0) {
                    medidacaseira = cursorRESPOSTA_OPCAO_MEDIDA_CASEIRA.getString(4);
                }

                Cursor cursorRESPOSTA_OPCAO_MODO_PREPARACAO = bd.rawQuery(sql_select.GET_RESPOSTA_OPCAO_MODO_PREPARACAO, new String[]{Integer.toString(AlunoAtual), (numeropergunta)});
                cursorRESPOSTA_OPCAO_MODO_PREPARACAO.moveToFirst();
                cursorRESPOSTA_OPCAO_MODO_PREPARACAO.getCount();
                if (cursorRESPOSTA_OPCAO_MODO_PREPARACAO.getCount() > 0) {
                    modopreparacao = cursorRESPOSTA_OPCAO_MODO_PREPARACAO.getString(4);
                }

                Cursor cursorRESPOSTA_OPCAO_QUANTIDADE_MEDIDAS = bd.rawQuery(sql_select.GET_RESPOSTA_OPCAO_QUANTIDADE_MEDIDAS, new String[]{Integer.toString(AlunoAtual), (numeropergunta)});
                cursorRESPOSTA_OPCAO_QUANTIDADE_MEDIDAS.moveToFirst();
                cursorRESPOSTA_OPCAO_QUANTIDADE_MEDIDAS.getCount();
                if (cursorRESPOSTA_OPCAO_QUANTIDADE_MEDIDAS.getCount() > 0) {
                    quantidade = cursorRESPOSTA_OPCAO_QUANTIDADE_MEDIDAS.getString(4);
                }
            }

            if (!alimento.equals("") && !medidacaseira.equals("") && !modopreparacao.equals("") && !quantidade.equals("")) {
                insereRegistroAlimento(alimento + "  " + medidacaseira + "  " + modopreparacao + "  " + quantidade, "1", alimento + "  " + medidacaseira + "  " + modopreparacao + "  " + quantidade, "1", colocaRefeicaoOrder());
                Toast.makeText(this, "Inserido com sucesso!", Toast.LENGTH_LONG).show();
                bd.execSQL(sql_delete.DEL_SALTO_TODOS, new String[]{});
                InsereSalto(VariavelAPI.constant_chave_102, VariavelAPI.constant_chave_102);
                saltoTEMP = "";
                AvancarQuestionario("");
            } else {
                Toast.makeText(this, "Preencha todos os dados", Toast.LENGTH_LONG).show();
            }

        } catch (Throwable ex) {

        }
    }

    private boolean nestaPerguntaNaoColocaResposta() {
        if (!cursorPergunta.isAfterLast()) {
            if (cursorPergunta.getString(7).equals(VariavelAPI.constant_chave_103)) {
                return false;
            }
            if (cursorPergunta.getString(7).equals(VariavelAPI.constant_chave_104)) {
                return false;
            }
        }
        return true;
    }

    // Inserir resposta
    private Callback<Crianca> cadatrarRespostaCallback = new Callback<Crianca>() {
        @Override
        public void onResponse(Call<Crianca> call, Response<Crianca> response) {
            try {
                if (response.isSuccessful()) {


                }

            } catch (NullPointerException e) {
                System.out.println("onActivityResult consume crashed");
                runOnUiThread(new Runnable() {
                    public void run() {

                        Context context = getApplicationContext();

                    }
                });
            }
        }

        @Override
        public void onFailure(Call<Crianca> call, Throwable t) {

        }
    };

    private void createStackOverflowAPI() {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utility.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mInterfaceObject = retrofit.create(InterfaceRetrofit.class);
    }

    /**
     * Call post cadatrar CriancaCallback .
     */
    private Callback<Integer>deletarRespostaCriancaCallback = new Callback<Integer>() {


        @Override
        public void onResponse(Call<Integer> call, Response<Integer> response) {
            try {
                //esperaFinalizar = false;
                if (response.isSuccessful()) {

                }
            } catch (NullPointerException e) {
                System.out.println("onActivityResult consume crashed");
                runOnUiThread(new Runnable() {
                    public void run() {

                        Context context = getApplicationContext();

                    }
                });
            }
        }

        @Override
        public void onFailure(Call<Integer> call, Throwable t) {
            Log.d("bebeto1212", "Code: " );
            esperaFinalizar = false;
        }
    };

    public void habilitarBotao(String carregarImagens) {
        for (int i = 0; i < qualTelaImagem.size() ; i++){
            if (qualTelaImagem.get(i) != null) {
                if (qualTelaImagem.get(i).equals(carregarImagens)) {
                    buttonPersonalizado.setTag(saltoTelaImagem.get(i));
                }
            }
        }
    }

    public void desmarcarImagem() {

        try {
            for (int i = 0; i < ll.getChildCount(); i++) {
                View child = ll.getChildAt(i);
                if (child instanceof ConstraintLayout) {
                    for (int y = 0; y < ((ConstraintLayout) child).getChildCount(); y++) {
                        View child2 = ((ConstraintLayout) child).getChildAt(y);
                        if (child2 instanceof ImageView) {
                            ImageView et = (ImageView) child2;
                            et.setBackground(null);
                        }
                    }
                }
            }

        } catch (Throwable ex) {
            System.out.println(ex.getMessage());
        }

    }


    private Integer getRefeicaoOrder() {
        Cursor cursorALIMENTO_REFEICAO = bd.rawQuery(sql_select.GET_ALIMENTO_REFEICAO, null);
        cursorALIMENTO_REFEICAO.moveToFirst();
        cursorALIMENTO_REFEICAO.getCount();

        return cursorALIMENTO_REFEICAO.getCount();

    }

    private String colocaRefeicaoOrder() {
        String retorno = "";
        Cursor cursorALIMENTO_REFEICAO = bd.rawQuery(sql_select.GET_ALIMENTO_REFEICAO, null);
        cursorALIMENTO_REFEICAO.moveToFirst();
        cursorALIMENTO_REFEICAO.getCount();

        if (cursorALIMENTO_REFEICAO.getCount() > 0) {
            retorno = cursorALIMENTO_REFEICAO.getString(0);
        }

        return retorno;

    }


    private String colocaNomeCrianca() {
        String retorno = "";
        Cursor cursorNOME_CRIANCA = bd.rawQuery(sql_select.GET_RESPOSTA_NOME, new String[]{Integer.toString(AlunoAtual)});
        cursorNOME_CRIANCA.moveToFirst();
        cursorNOME_CRIANCA.getCount();

        if (cursorNOME_CRIANCA.getCount() > 0) {
            retorno = cursorNOME_CRIANCA.getString(4);
        }

        return retorno;

    }


}
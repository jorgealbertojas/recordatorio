package com.softjads.cadeconsumo;

import android.app.Fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
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

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.text.format.Time;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.softjads.cadeconsumo.util.modulo;
import com.softjads.cadeconsumo.util.FTPtransferencia;
import com.softjads.cadeconsumo.util.MyConstant;
import com.softjads.cadeconsumo.model.Aluno;
import com.softjads.cadeconsumo.model.Controle_fim;
import com.softjads.cadeconsumo.model.Controle_inicio;
import com.softjads.cadeconsumo.model.Pergunta;
import com.softjads.cadeconsumo.model.Resposta;
import com.softjads.cadeconsumo.util.ClasseADO;
import com.softjads.cadeconsumo.service.DB;
import com.softjads.cadeconsumo.service.DataBase;
import com.softjads.cadeconsumo.SQL.sql_create;
import com.softjads.cadeconsumo.SQL.sql_delete;
import com.softjads.cadeconsumo.SQL.sql_select;
import com.getbase.floatingactionbutton.FloatingActionButton;

/**
 * Created by jorgealberto on 12/09/2016.
 */
public class MainFragment extends Fragment {

    private String filtro_id_cliente = "";
    private String filtro_id_pesquisa = "";
    private String filtro_desc_pesquisa = "";
    private String filtro_automatico = "";
    private String filtro_previsao = "";
    private String usuario  = "";
    private String Nomeusuario = "";
    private String nFONTE = "";
    private String nVOZ = "";



    private ProgressDialog dialog;
    public View fragmentView;

    private Handler handler = new Handler();

    public final String SOAP_ACTION = "http://ericapesquisa.org/AtualizaAlunoErica";
    public  final String OPERATION_NAME = "AtualizaAlunoErica";
    public  final String WSDL_TARGET_NAMESPACE = "http://ericapesquisa.org/";
    public  final String SOAP_ADDRESS = "http://"+ MyConstant.ip_servidor+"/WebServiceSubFormulario/ApoioErica.asmx";
    //private Context context;

    MyControlThread mThread;
    ProgressDialog mDialog;
    Handler mHandler;
    int LIMITE = 10;
    Boolean nboolean = false;



    private LinearLayout ll;
    private int count = 0;
    private ArrayList<EditText> edits;
    // private TextView textoEdits;
    private Pergunta nPergunta;

    public static ImageView main_icon_schedule_image_view;
    public static ImageView imageviewEnviar;


    private SQLiteDatabase bd;
    //private Context context;
    private DataBase nDataBase;


    private StringWriter writer;


    public static LocationManager locationManager;


    //@Override
    public void onStart() {
        super.onStart();



       /* if (!modulo.Liberado) {
            fragmentView.finish();
            try {
                this.finalize();
            } catch (Throwable e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }*/

    };





    public void carregarFONTE()
    {



        Properties properties = new Properties();
        try
        {
            FileInputStream fis;
            fis = new  FileInputStream(MyConstant.nomeArquivoINI);
            properties.load(fis);
            String nIP = properties.getProperty("conf.FONTE");

            nFONTE = "M";

            if (nIP != null){
                if (nIP.equals("P")){
                    nFONTE = "P";
                }
                else if (nIP.equals("M")){
                    nFONTE = "M";
                }
                else if (nIP.equals("G")){
                    nFONTE = "G";
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
                    nVOZ = "true";
                }else{

                   nVOZ = "false";
                }
            }else
            {
                nVOZ = "false";
            }


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.activity_main2, container, false);

        modulo.Liberado = true;

        FloatingActionsMenu multiple_actions_down = fragmentView.findViewById(R.id.multiple_actions_down);
        if (modulo.perfilUsuario.equals("3")){
            multiple_actions_down.setVisibility(View.VISIBLE);
        }

        main_icon_schedule_image_view = (ImageView) fragmentView.findViewById(R.id.main_icon_schedule_image_view);
        imageviewEnviar = (ImageView) fragmentView.findViewById(R.id.imageviewEnviar);
        main_icon_schedule_image_view.setColorFilter(main_icon_schedule_image_view.getContext().getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);

        ImageView main_icon_schedule_image_view3 = fragmentView.findViewById(R.id.main_icon_schedule_image_view3 );


        //main_icon_schedule_image_view.setColorFilter(main_icon_schedule_image_view.getContext().getResources().getColor(R.color.blue_dark), PorterDuff.Mode.SRC_ATOP);
        //imageviewEnviar.setColorFilter(imageviewEnviar.getContext().getResources().getColor(R.color.blue_dark), PorterDuff.Mode.SRC_ATOP);

        edits = new ArrayList<EditText>();
        ll = (LinearLayout) fragmentView.findViewById(R.id.edits_ll);
        //textoEdits = (TextView) findViewById(R.id.showvalues);
        Button btn_create = (Button) fragmentView.findViewById(R.id.criar);

        nDataBase = new DataBase(fragmentView.getContext());
        bd = nDataBase.getReadableDatabase();
        nDataBase.onCreate(bd);

        DB.getInstance(fragmentView.getContext());

        buttonAnimation();

        salvar_USuario_login();

        Cursor cusrsorlogin = bd.rawQuery(sql_select.GET_USUARIO2,new String[] {(carregarsuario_login())});
        cusrsorlogin.moveToFirst();

        if (cusrsorlogin.getCount() > 0) {
            usuario = cusrsorlogin.getString(2).toString();
            Nomeusuario = cusrsorlogin.getString(0).toString();
        }


        // comentar para teste IERC
        pegaDadosPesquisa();

        btn_create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                irParaTelaUsuario();
            }
        });

/*        Button btn_calc = (Button) fragmentView.findViewById(R.id.getValues);
        btn_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                calcular();
            }
        });*/

/*        Button btn_Trans = (Button) fragmentView.findViewById(R.id.buttonTransferir);
        btn_Trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    Cursor cursorPesquisa;
                    cursorPesquisa = bd.rawQuery(sql_select.GET_PESQUISA,null);
                    cursorPesquisa.moveToFirst();
                    if (cursorPesquisa.getCount() > 0) {
                        ativaThread();

                    }else{
                        Toast.makeText(fragmentView.getContext(), "Não existe pesquisa para atualizar!", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });*/


       /* Button volumemuteImageButton1 = (Button) fragmentView.findViewById(R.id.criar);
        volumemuteImageButton1.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View v, MotionEvent event) { switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {


                    //imageButtonConecao.setImageResource(R.mipmap.aceitar);
                    //textViewInformacao.setText(getString(R.string.conexao));
                    //textViewInformacao.setTextColor(textViewInformacao.getContext().getResources().getColor(R.color.green_main));
                   // main_icon_schedule_image_view.setColorFilter(main_icon_schedule_image_view.getContext().getResources().getColor(R.color.color_accent), PorterDuff.Mode.SRC_ATOP);
                   // ((Button) v).setTextColor(((Button) v).getContext().getResources().getColor(R.color.color_accent));


                //    v.invalidate();
                    break;
                }
                case MotionEvent.ACTION_UP:
                {
                //    main_icon_schedule_image_view.setColorFilter(main_icon_schedule_image_view.getContext().getResources().getColor(R.color.blue_dark), PorterDuff.Mode.SRC_ATOP);
                //    ((Button) v).setTextColor(((Button) v).getContext().getResources().getColor(R.color.blue_dark));

                //    v.invalidate();
                    break;
                }
            }
                return false;
            }
        });*/



      /*  Button volumemuteImageButton3 = (Button) fragmentView.findViewById(R.id.buttonTransferir);
        volumemuteImageButton3.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View v, MotionEvent event) { switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {

                    imageviewEnviar.setColorFilter(imageviewEnviar.getContext().getResources().getColor(R.color.color_accent), PorterDuff.Mode.SRC_ATOP);
                    ((Button) v).setTextColor(((Button) v).getContext().getResources().getColor(R.color.color_accent));

                    v.invalidate();
                    break;
                }
                case MotionEvent.ACTION_UP:
                {
                    imageviewEnviar.setColorFilter(imageviewEnviar.getContext().getResources().getColor(R.color.blue_dark), PorterDuff.Mode.SRC_ATOP);
                    ((Button) v).setTextColor(((Button) v).getContext().getResources().getColor(R.color.blue_dark));

                    v.invalidate();
                    break;
                }
            }
                return false;
            }
        });*/




        return fragmentView;

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

    public void salvar_USuario_login()
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

        properties.setProperty("conf.usuario_login", "");
        try
        {

            FileOutputStream fos = new FileOutputStream(MyConstant.nomeArquivoINI);
            properties.store(fos, "CONFIGURACAO usuario login:");
            fos.close();

            //Toast.makeText(this, "Dados Salvos com sucesso!!", Toast.LENGTH_SHORT).show();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
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

    private void ativaThread() {
        mDialog = ProgressDialog.show(fragmentView.getContext(), "Aguarde", "Processando...", false, false);
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

    protected void irParaTelaUsuario() {


           // Cursor cusrsorlogin = bd.rawQuery(sql_select.GET_USUARIO2,new String[] {(carregarsuario_login())});
          //  cusrsorlogin.moveToFirst();

          //  if (cusrsorlogin.getCount() > 0) {
                usuario = "bebeto"; //cusrsorlogin.getString(2).toString();"
                Nomeusuario = "bebeto";// cusrsorlogin.getString(0).toString();
         //   }

            carregarFONTE();
            carregarVOZ();

            if (filtro_automatico.equals("1")){



                Intent WSActivity = new Intent(fragmentView.getContext(), automatico_nao.class);
                WSActivity.putExtra("filtro_id_cliente",filtro_id_cliente);
                WSActivity.putExtra("filtro_id_pesquisa",filtro_id_pesquisa);
                WSActivity.putExtra("filtro_desc_pesquisa",filtro_desc_pesquisa);
                WSActivity.putExtra("filtro_automatico",filtro_automatico);
                WSActivity.putExtra("filtro_previsao",filtro_previsao);
                WSActivity.putExtra("usuario",usuario);
                WSActivity.putExtra("Nomeusuario",Nomeusuario);
                WSActivity.putExtra("nFONTE",nFONTE);
                WSActivity.putExtra("nVOZ",nVOZ);
                startActivity(WSActivity);

            }
            else
            {
                Intent WSActivity = new Intent(fragmentView.getContext(), automatico.class);
                WSActivity.putExtra("filtro_id_cliente",filtro_id_cliente);
                WSActivity.putExtra("filtro_id_pesquisa",filtro_id_pesquisa);
                WSActivity.putExtra("filtro_desc_pesquisa",filtro_desc_pesquisa);
                WSActivity.putExtra("filtro_automatico",filtro_automatico);
                WSActivity.putExtra("filtro_previsao",filtro_previsao);
                WSActivity.putExtra("usuario",usuario);
                WSActivity.putExtra("Nomeusuario",Nomeusuario);
                WSActivity.putExtra("nFONTE",nFONTE);
                WSActivity.putExtra("nVOZ",nVOZ);
                startActivity(WSActivity);
            }





    }

    private void calcular() {
        StringBuilder texto = new StringBuilder();
        for (int i = 0; i < edits.size(); i++) {
            texto.append(edits.get(i).getText().toString());
        }
        //textoEdits.setText(texto);

        Intent ExecutorsActivity = new Intent(fragmentView.getContext(), ExecutorsActivity.class);
        ExecutorsActivity.putExtra("usuario", usuario);
        startActivity(ExecutorsActivity);

    }








    public ArrayList<Object> PegarDados(String nString,  Object output) {
        nDataBase = new DataBase(fragmentView.getContext());
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
                    mDialog.setMessage("Atualizando o servidor com os dados coletados da pesquisa " + filtro_desc_pesquisa);
                }
            });



            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    String DataCompleta;
                    Time now = new Time();
                    now.setToNow();
                    DataCompleta = Integer.toString(now.year);
                    DataCompleta = DataCompleta + "_" + Integer.toString(now.month + 1);
                    DataCompleta = DataCompleta + "_" + Integer.toString(now.monthDay);
                    DataCompleta = DataCompleta + "_" + Integer.toString(now.hour);
                    DataCompleta = DataCompleta + "_" + Integer.toString(now.minute);
                    DataCompleta = DataCompleta + "_" + Integer.toString(now.second);


                    try {
                        bkp(MyConstant.caminhobanco, MyConstant.storage + MyConstant.NomeCopia + filtro_desc_pesquisa + DataCompleta + ".db");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    if (nboolean) {
                        Toast.makeText(fragmentView.getContext(), "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                        bd.execSQL(sql_delete.DEL_RESPOSTA_TODOS);
                        bd.execSQL(sql_delete.DEL_BLOCO_TODOS);
                        bd.execSQL(sql_delete.DEL_ALUNO_TODOS);
                        bd.execSQL(sql_delete.DEL_CONTROLE_INICIO);
                        bd.execSQL(sql_delete.DEL_CONTROLE_FIM);
                        new FTPtransferencia().execute("seuparametro");

                    } else {
                        Toast.makeText(fragmentView.getContext(), "Verifique a internet! Problemas na atualização!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        private void bkp(String Origem, String Destino) throws IOException {
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
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Close the streams
            output.flush();
            output.close();
            fis.close();

        }


        private String fffff() {
            File sdcard = Environment.getExternalStorageDirectory();


            File file = new File(sdcard, "/testexml.txt");


            StringBuilder text = new StringBuilder();

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
            } catch (IOException e) {
                //You'll need to add proper error handling here
            }

            return text.toString();
        }

    }


    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    public static String getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return formatSize(availableBlocks * blockSize);
    }

    public static String getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return formatSize(totalBlocks * blockSize);
    }

    public static String getAvailableExternalMemorySize(String caminhoStorage) {
        if (externalMemoryAvailable()) {
            //File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(caminhoStorage);
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return formatSize(availableBlocks * blockSize);
        } else {
            return "ERROR";
        }
    }

    public static String getTotalExternalMemorySize(String caminhoStorage) {
        if (externalMemoryAvailable()) {
            //File path = Environment.getExternalStorageDirectory();
            //StatFs stat = new StatFs(path.getPath());
            StatFs stat = new StatFs(caminhoStorage);
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return formatSize(totalBlocks * blockSize);
        } else {
            return "ERROR";
        }
    }

    public static String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
            }
        }

        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, '.');
            commaOffset -= 3;
        }

        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }


    private void buttonAnimation() {
        // https://github.com/futuresimple/android-floating-action-button

        final FloatingActionButton chat = (FloatingActionButton) fragmentView.findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent WSActivity = new Intent(fragmentView.getContext(), CriarCrianca.class);
                startActivity(WSActivity);
            }
        });

        final FloatingActionButton gps = (FloatingActionButton) fragmentView.findViewById(R.id.gps);
        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent WSActivity = new Intent(fragmentView.getContext(), CriarUsuario.class);
                 startActivity(WSActivity);

            }
        });

        ImageView imageViewFinalizar = (ImageView) fragmentView.findViewById(R.id.bkp);
        if (BuildConfig.DEBUG) {
            imageViewFinalizar.setVisibility(View.VISIBLE);
        }else{
            imageViewFinalizar.setVisibility(View.INVISIBLE);
        }
        imageViewFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent WSActivity = new Intent(fragmentView.getContext(), DbExportImport.class);
                startActivity(WSActivity);

                String ocupado = getAvailableExternalMemorySize(MyConstant.storage);
                String total = getTotalExternalMemorySize(MyConstant.storage);

                int indice = 0;

                indice = total.toString().indexOf("MB");
                if (indice > 0){
                    total =    total.substring(0,indice);
                }else{ indice = 0;};

                indice = total.toString().indexOf("KB");
                if (indice > 0){
                    total =    total.substring(0,indice);
                }else{ indice = 0;};

                indice = ocupado.toString().indexOf("MB");
                if (indice > 0){
                    ocupado =    ocupado.substring(0,indice);
                }else{ indice = 0;};

                indice = ocupado.toString().indexOf("KB");
                if (indice > 0){
                    ocupado =    ocupado.substring(0,indice);
                }else{ indice = 0;};

                double resultado = 0.0;
                String resultadoSTR = "";
                String e = "";

                if (!ocupado.toString().equals("") && (!total.toString().equals(""))){
                    resultado = Double.parseDouble(ocupado) * 100;
                    resultado = resultado / Double.parseDouble(total);
                    resultadoSTR = Double.toString(resultado);



                    indice = resultadoSTR.toString().indexOf(".");
                    if (indice > 0){
                        e=    resultadoSTR.substring(0,indice);
                    }
                }

                Toast.makeText(fragmentView.getContext(), (e) + "% Livre - Capacidade:" + getTotalExternalMemorySize(MyConstant.storage), Toast.LENGTH_SHORT).show();

            }
        });

    }


  }


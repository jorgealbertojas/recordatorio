package com.example.jorgealberto.researchmobile;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import android.text.format.Time;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jorgealberto.researchmobile.SQL.sql_create;
import com.example.jorgealberto.researchmobile.service.DB;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ShowLocationActivity extends Activity implements LocationListener {
    private TextView TextViewLOG;
    private TextView TextViewLAT;
    private TextView TextViewID;
    private TextView TextViewNOME;
    private TextView timeField;
    private LocationManager locationManager;
    private String provider;

    private ImageButton imageButtonClose;
    private ImageView imageButtonConecao;
    private ImageView imageViewUSER;
    private ImageView imageViewGPS;
    private ImageView imageViewTIME;

    private TextView TextViewDATA;
    private TextView TextViewHORA;

    private String filtro_id_cliente = "";
    private String filtro_id_pesquisa = "";
    private String filtro_automatico = "";
    private String nFONTE = "";
    private int AlunoAtual = 0;
    private String usuario = "";
    private String Nomeusuario = "";
    private String NomeGravacaoArquivo = "";
    private String filtro_desc_pesquisa = "";
    private String filtro_previsao = "";

    private String nINICIO = "";

    public void fechar() {
        this.finish();
    }
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutgps);
        TextViewLAT = (TextView) findViewById(R.id.TextViewLAT);
        TextViewLOG = (TextView) findViewById(R.id.TextViewLOG);
        TextViewID = (TextView) findViewById(R.id.TextViewID);
        TextViewNOME = (TextView) findViewById(R.id.TextViewNOME);


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

        TextViewDATA = (TextView) findViewById(R.id.TextViewDATA);
        TextViewHORA = (TextView) findViewById(R.id.TextViewHORA);

        TextViewDATA.setText(DataCompleta.toString());
        TextViewHORA.setText(horaCompleta.toString());


        timeField = (TextView) findViewById(R.id.TextView06);


        imageButtonConecao = (ImageView)  findViewById(R.id.imageButtonConecao);

        imageButtonClose = (ImageButton) findViewById(R.id.imageButtonClose);

        imageViewUSER = (ImageView) findViewById(R.id.imageViewUSER);

        imageViewGPS = (ImageView)  findViewById(R.id.imageViewGPS);

        imageViewTIME = (ImageView) findViewById(R.id.imageViewTIME);

        imageViewUSER.setColorFilter(imageViewUSER.getContext().getResources().getColor(R.color.blue_dark), PorterDuff.Mode.SRC_ATOP);
        imageViewGPS.setColorFilter(imageViewUSER.getContext().getResources().getColor(R.color.blue_dark), PorterDuff.Mode.SRC_ATOP);
        imageViewTIME.setColorFilter(imageViewUSER.getContext().getResources().getColor(R.color.blue_dark), PorterDuff.Mode.SRC_ATOP);


        imageButtonClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                fechar();
            }
        });




        Bundle extras = getIntent().getExtras();
        filtro_id_cliente = extras.getString("filtro_id_cliente");
        filtro_id_pesquisa = extras.getString("filtro_id_pesquisa");
        filtro_automatico = extras.getString("filtro_automatico");
        filtro_desc_pesquisa = extras.getString("filtro_desc_pesquisa");
        filtro_previsao = extras.getString("filtro_previsao");
        usuario = extras.getString("usuario");
        Nomeusuario = extras.getString("Nomeusuario");
        AlunoAtual = Integer.parseInt(extras.getString("AlunoAtual"));
        NomeGravacaoArquivo = extras.getString("NomeGravacaoArquivo");
        nFONTE = extras.getString("nFONTE");
        nINICIO = extras.getString("nINICIO");

        TextViewID.setText(usuario);
        TextViewNOME.setText(Nomeusuario);

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);






        //SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        //Date dateD=new Date();
        //dateD.setTime(location.getTime());
        //String date=dateFormat.format(dateD);


        // Initialize the location fields


            onLocationChanged(location);

    }

    @Override
    protected void onDestroy() {
        if (nINICIO.toString().equals("TRUE")){
            insereControleInicio();
        }else{
            insereControleFim();
        }

        super.onDestroy();

    }

    public void insereControleInicio() {
        try {
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
            obj.put("DATA_INICIO", DataCompleta.toString());
            obj.put("INICIO", horaCompleta.toString());
            obj.put("LONGITUDE", TextViewLOG.getText().toString());
            obj.put("LATITUDE", TextViewLAT.getText().toString());
            obj.put("GRAVACAO", NomeGravacaoArquivo);
            obj.put("NM_PESQUISA", filtro_desc_pesquisa);
            obj.put("PREVISAO", filtro_previsao);
            this.onInsert(this, obj, sql_create.TABLE_CONTROLE_INICIO);
        } catch (Throwable ex) {
        }
    };

    public void insereControleFim(){

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

        ContentValues obj = new ContentValues();
        obj.put("ID_ALUNO",AlunoAtual);
        obj.put("ID_USUARIO", usuario);
        obj.put("NM_USUARIO", Nomeusuario);
        obj.put("DATA_FIM",DataCompleta.toString());
        obj.put("FIM",horaCompleta.toString());
        obj.put("LONGITUDE",TextViewLOG.getText().toString());
        obj.put("LATITUDE",TextViewLAT.getText().toString());
        this.onInsert( this, obj , sql_create.TABLE_CONTROLE_FIM);
    }


    private void onInsert(Context context, ContentValues obj, String nTabela) {

        DB.getInstance(context).insert(nTabela, obj);
    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            double lat = (double) (location.getLatitude());
            double lng = (double) (location.getLongitude());
            TextViewLAT.setText(String.valueOf(lat));
            TextViewLOG.setText(String.valueOf(lng));
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis((location.getTime()));
            Date d = (Date) c.getTime();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String time = format.format(d);

            timeField.setText(String.valueOf(time));
            imageButtonConecao.setImageResource(R.mipmap.aceitar);
            TextViewLAT.setTextColor(TextViewLAT.getContext().getResources().getColor(R.color.green_main));
            TextViewLOG.setTextColor(TextViewLAT.getContext().getResources().getColor(R.color.green_main));

            imageButtonConecao.setColorFilter(imageButtonConecao.getContext().getResources().getColor(R.color.green_main), PorterDuff.Mode.SRC_ATOP);




        } else {
            TextViewLAT.setText(R.string.posicaoGPS);
            TextViewLOG.setText(R.string.posicaoGPS);
            timeField.setText(R.string.posicaoGPS);
            imageButtonConecao.setImageResource(R.mipmap.aceitarno);
            TextViewLAT.setTextColor(TextViewLAT.getContext().getResources().getColor(R.color.red));
            TextViewLOG.setTextColor(TextViewLOG.getContext().getResources().getColor(R.color.red));

            imageButtonConecao.setColorFilter(imageButtonConecao.getContext().getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);

    }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }
}

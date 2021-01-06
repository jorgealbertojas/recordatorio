package com.softjads.cadeconsumo;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.softjads.cadeconsumo.SQL.sql_select;
import com.softjads.cadeconsumo.util.modulo;
import com.softjads.cadeconsumo.util.app;
import com.softjads.cadeconsumo.service.DataBase;
import com.softjads.cadeconsumo.util.SharedPreferencesService;


public class MainActivity extends Activity {

    private Toolbar mToolbar;
    private TextView actionBarTitle;
    private ImageView ibBegin;
    private ImageView imageViewStart;
    private ImageView imageViewEnd;

    private TextView textViewStart;
    private TextView textViewEnd;

    private FragmentTransaction ft;

    private SQLiteDatabase bd;
    //private Context context;
    private DataBase nDataBase;

    private void contaCompletoEIncompleto(){

        nDataBase = new DataBase(this);
        bd = nDataBase.getReadableDatabase();

        int AlunoAtual = 0;
        int contaAluno = 0;

        int valoTotalCompleto = 0;
        int valoTotalIncompleto = 0;


        Cursor cursoAluno = bd.rawQuery(sql_select.get_todos_alunos, null);
        cursoAluno.moveToFirst();

        Cursor Cursorget_contapergunta = bd.rawQuery(sql_select.get_contapergunta,null);
        Cursorget_contapergunta.moveToFirst();
        int varCursorget_contapergunta = Cursorget_contapergunta.getInt(0);

        Cursor cursoContapergunta = bd.rawQuery(sql_select.get_contapergunta, null);
        cursoAluno.moveToFirst();


        while (!cursoAluno.isAfterLast()) {

            AlunoAtual = cursoAluno.getInt(0);

            Cursor cursorReposta1 = bd.rawQuery(sql_select.GET_completo,new String[] {Integer.toString(varCursorget_contapergunta), Integer.toString(AlunoAtual)});
            cursorReposta1.moveToFirst();
            if (cursorReposta1.getCount() == 1){
                valoTotalCompleto = valoTotalCompleto + 1;
            }else{
                valoTotalIncompleto = valoTotalIncompleto + 1;
            }


            cursoAluno.moveToNext();
        }
    }


    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    /**
     * this method check permission and return current state of permission need.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * this method request to permission asked.
     */
    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA);

        if (shouldProvideRationale) {

        } else {

            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.

            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted. Kick off the process of building and connecting
                // GoogleApiClient.
                // perform your operation
            } else {
                // Permission denied.
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        app nApp = new app();
        nApp.setContext(this);

        if (!checkPermissions()) {
            requestPermissions();
        }


        if(savedInstanceState == null){
         //   Intent WSActivity = new Intent(this, login.class);
        //    startActivity(WSActivity);

        }

        MainFragment nMainFragment = new MainFragment();
        ft = getFragmentManager().beginTransaction();
        ft.replace( R.id.container_body, nMainFragment);
        ft.commit();



    }

    public void onStart() {
        super.onStart();

        contaCompletoEIncompleto();

        if (!modulo.Liberado) {
            this.finish();
            try {
                this.finalize();
            } catch (Throwable e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    };

    @Override
    protected void onDestroy() {
        //if (modulo.nGPS){
        //	Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        //	intent.putExtra("enabled", false);
        //	sendBroadcast(intent);
        //	intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        //    startActivityForResult(intent, 1);
        //}

        super.onDestroy();
        //cursorPergunta.close();
    };

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        SharedPreferencesService shared = new SharedPreferencesService(MainActivity.this);
        if (shared.getPermissao()){
            finish();
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //  if (id == R.id.action_settings) {
        //      return true;
        //  }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // MenuUser();

        getMenuInflater().inflate(R.menu.menu_main, menu);

        menu.hasVisibleItems();
        return true;
    }




    private void eventChangeColorEnd(View v, MotionEvent motionEvent){
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                imageViewEnd.setColorFilter(imageViewEnd.getContext().getResources().getColor(R.color.color_accent), PorterDuff.Mode.SRC_ATOP);
                textViewEnd.setTextColor(imageViewEnd.getContext().getResources().getColor(R.color.color_accent));
                v.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                imageViewEnd.setColorFilter(imageViewEnd.getContext().getResources().getColor(R.color.gold), PorterDuff.Mode.SRC_ATOP);
                textViewEnd.setTextColor(imageViewEnd.getContext().getResources().getColor(R.color.gold));
                v.invalidate();
                break;
            }
        }
    }


/*    private void eventChangeColorStart(View v, MotionEvent motionEvent){
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                imageViewStart.setColorFilter(imageViewStart.getContext().getResources().getColor(R.color.MediumOrchid), PorterDuff.Mode.SRC_ATOP);
                textViewStart.setTextColor(imageViewStart.getContext().getResources().getColor(R.color.MediumOrchid));
                v.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                imageViewStart.setColorFilter(imageViewStart.getContext().getResources().getColor(R.color.green_accent), PorterDuff.Mode.SRC_ATOP);
                textViewStart.setTextColor(imageViewStart.getContext().getResources().getColor(R.color.green_accent));
                v.invalidate();
                break;
            }
        }
    }*/

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.mensagem01))
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                        finish();
                    }
                }).create().show();
    }


}

package com.example.jorgealberto.researchmobile;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;

import com.getbase.floatingactionbutton.FloatingActionButton;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View.OnClickListener;


public class InformationMain extends AppCompatActivity {

    String stringtextViewEndereco;
    String stringtextViewReclamante;
    String stringtextId;
    String stringtextConceito;
    String stringtextOc;
    String stringtextEntrada;
    String stringtextTransmissao;
    String stringtextIncidenciaGestao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_main);
        // https://github.com/daimajia/AndroidViewAnimations
        YoYo.with(Techniques.FadeInUp)
                .playOn(findViewById(R.id.relativecard));

       // ButtoclickDown();
       // LabelColor();


        ImageView imageViewBandeira = (ImageView) findViewById(R.id.ImageViewOcorrencia);
        imageViewBandeira.setColorFilter(imageViewBandeira.getContext().getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);



        CreateAnimationDetail();

        buttonAnimation();

    }


    private void CreateAnimationDetail() {
        // http://www.materialup.com/posts/android-expanding-view-library


        stringtextViewEndereco = getIntent().getStringExtra("endereco");
        stringtextViewReclamante = getIntent().getStringExtra("reclamante");
        stringtextId = getIntent().getStringExtra("id");
        stringtextConceito = getIntent().getStringExtra("conceito");
        stringtextOc = getIntent().getStringExtra("oc");
        stringtextEntrada = getIntent().getStringExtra("entrada");
        stringtextTransmissao = getIntent().getStringExtra("transmissao");
        stringtextIncidenciaGestao = getIntent().getStringExtra("incidenciaGestao");


        TextView textViewOcorrencia = (TextView) findViewById(R.id.TextViewOcorrencia);
        textViewOcorrencia.setText(stringtextId);

        TextView textViewReclamante = (TextView) findViewById(R.id.TextViewReclamante);
        textViewReclamante.setText(stringtextViewReclamante);

        TextView textViewEntrada = (TextView) findViewById(R.id.TextViewEntrada);
        textViewEntrada.setText(stringtextEntrada);

        TextView textViewConceito = (TextView) findViewById(R.id.textViewConceito);
        textViewConceito.setText(stringtextConceito);


       // Informacao.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/Signika-Light.ttf"));


/*        ImageView salve = (ImageView) findViewById(R.id.salve);
        salve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.RotateIn)
                        .duration(1000)
                        .playOn((ImageView) view);
            }
        });
        YoYo.with(Techniques.ZoomInRight)
                .duration(4000)
                .playOn(salve);*/


        /*ExpandingItem extends from View, so you can call
        findViewById to get any View inside the layout*/


        // new item
        ExpandingList expandingList2 = (ExpandingList) findViewById(R.id.expanding_list_main);
        ExpandingItem item2 = expandingList2.createNewItem(R.layout.expanding_layout);

        TextView title2 = (TextView) item2.findViewById(R.id.title);
        title2.setText("Endereço");


        item2.createSubItems(2);

        //View subItemZero2 = item2.getSubItemView(0);
        //((TextView) subItemZero2.findViewById(R.id.sub_title)).setText(stringtextViewEndereco);

        item2.setIndicatorColorRes(R.color.blue_button);
        item2.setIndicatorIconRes(R.mipmap.detailopen);


        // new item

        ExpandingList expandingList3 = (ExpandingList) findViewById(R.id.expanding_list_main);
        ExpandingItem item3 = expandingList3.createNewItem(R.layout.expanding_layout);

        TextView title3 = (TextView) item3.findViewById(R.id.title);
        title3.setText("Horas");


        item3.createSubItems(2);

        //View subItemZero3 = item3.getSubItemView(0);
        //((TextView) subItemZero3.findViewById(R.id.sub_title)).setText("Entrada: " + stringtextEntrada + "\nTransmissão: " + stringtextTransmissao);

        item3.setIndicatorColorRes(R.color.blue_button);

        item3.setIndicatorIconRes(R.mipmap.detailopen);

        expandingList3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ExpandingItem) view).setIndicatorIconRes(R.mipmap.detaildown);
            }
        });



        // new item

        ExpandingList expandingList4 = (ExpandingList) findViewById(R.id.expanding_list_main);
        ExpandingItem item4 = expandingList4.createNewItem(R.layout.expanding_layout);

        TextView title4 = (TextView) item4.findViewById(R.id.title);
        title4.setText("Incidência de Gestão");


        item4.createSubItems(2);

        //View subItemZero4 = item4.getSubItemView(0);
       // ((TextView) subItemZero4.findViewById(R.id.sub_title)).setText(stringtextIncidenciaGestao);

        item4.setIndicatorColorRes(R.color.blue_button);

        item4.setIndicatorIconRes(R.mipmap.detailopen);


    }

    private void LabelColor() {

/*        TextView menu_text_opcao1 = (TextView) findViewById(R.id.menu_text_opcao1);
        menu_text_opcao1.setTextColor(menu_text_opcao1.getContext().getResources().getColor(R.color.blue_dark));

        TextView menu_text_opcao2 = (TextView) findViewById(R.id.menu_text_opcao2);
        menu_text_opcao2.setTextColor(menu_text_opcao1.getContext().getResources().getColor(R.color.marrom));

        TextView menu_text_opcao3 = (TextView) findViewById(R.id.menu_text_opcao3);
        menu_text_opcao3.setTextColor(menu_text_opcao1.getContext().getResources().getColor(R.color.green));

        TextView menu_text_opcao4 = (TextView) findViewById(R.id.menu_text_opcao4);
        menu_text_opcao4.setTextColor(menu_text_opcao1.getContext().getResources().getColor(R.color.abobora));*/
    }

    private void ButtoclickDown() {

      /*  ImageButton menu_main = (ImageButton) findViewById(R.id.menu_main);
        menu_main.setColorFilter(menu_main.getContext().getResources().getColor(R.color.blue_dark), PorterDuff.Mode.SRC_ATOP);
        menu_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        ImageButton menu_map = (ImageButton) findViewById(R.id.menu_map);
        menu_map.setColorFilter(menu_map.getContext().getResources().getColor(R.color.marrom), PorterDuff.Mode.SRC_ATOP);
        menu_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startActivity(MapsActivity.createIntent(InformationMain.this));

                //Uri gmmIntentUri = Uri.parse("google.navigation:q=rua+Professor+Henrique+Costa,+Rio+de+Janeiro+Brasil");
                //Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                //mapIntent.setPackage("com.google.android.apps.maps");

                //

                String uri = "waze://?q=rua%Professor%Henrique%Costa%Rio%de%Janeiro%Brasil";
                startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));

                //Intent intent = new Intent(InformationMain., FragmentActivity.class);
                //startActivity(intent);
                //overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                //finish();
            }
        });
        ImageButton menu_chat = (ImageButton) findViewById(R.id.menu_chat);
        menu_chat.setColorFilter(menu_map.getContext().getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
        menu_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(InformationMain.this, InformationChat.class);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                //finish();
            }
        });
        ImageButton menu_other = (ImageButton) findViewById(R.id.menu_other);
        menu_other.setColorFilter(menu_map.getContext().getResources().getColor(R.color.abobora), PorterDuff.Mode.SRC_ATOP);
        menu_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(InformationMain.this, InformationOther.class);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                //finish();
            }
        });
*/
    }


    private void buttonAnimation() {
       // https://github.com/futuresimple/android-floating-action-button

        final FloatingActionButton chat = (FloatingActionButton) findViewById(R.id.chat);
        chat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //((FloatingActionsMenu) findViewById(R.id.multiple_actions_down)).removeButton(removeAction);

                Intent intent = new Intent(InformationMain.this, LoginActivity.class);
                intent.putExtra("id", stringtextId);
                intent.putExtra("endereco", stringtextViewEndereco);
                intent.putExtra("conceito", stringtextConceito);
                intent.putExtra("oc", stringtextOc);
                intent.putExtra("entrada", stringtextEntrada);
                intent.putExtra("transmissao", stringtextTransmissao);
                intent.putExtra("reclamante", stringtextViewReclamante);
                intent.putExtra("incidenciaGestao", stringtextIncidenciaGestao);


                startActivity(intent);
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);


            }
        });

        final FloatingActionButton gps = (FloatingActionButton) findViewById(R.id.gps);
        gps.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //((FloatingActionsMenu) findViewById(R.id.multiple_actions_down)).removeButton(removeAction);
                //String uri= "waze://?ll="+"-22.909698"+","+"-43.1850868"+"&navigate=yes";

                String endereco = stringtextViewEndereco + "Brasil";
                String uri = "waze://?q=" + endereco;
                startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));


            }
        });

        ImageView imageViewFinalizar = (ImageView) findViewById(R.id.imageViewFinalizar);
        imageViewFinalizar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                finalizar();


            }
        });



        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(getResources().getColor(R.color.white));

        // Test that FAMs containing FABs with visibility GONE do not cause crashes
        findViewById(R.id.action_enable).setVisibility(View.GONE);

        FloatingActionButton addedOnce = new FloatingActionButton(this);
        addedOnce.setTitle("Added once");

        FloatingActionButton addedTwice = new FloatingActionButton(this);
        addedTwice.setTitle("Added twice");

    }

    public void finalizar(){
        Intent intent = new Intent(InformationMain.this, LoginActivity.class);
        intent.putExtra("id", stringtextId);
        intent.putExtra("endereco", stringtextViewEndereco);
        intent.putExtra("conceito", stringtextConceito);
        intent.putExtra("oc", stringtextOc);
        intent.putExtra("entrada", stringtextEntrada);
        intent.putExtra("transmissao", stringtextTransmissao);
        intent.putExtra("reclamante", stringtextViewReclamante);
        intent.putExtra("incidenciaGestao", stringtextIncidenciaGestao);


        startActivity(intent);
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }





}

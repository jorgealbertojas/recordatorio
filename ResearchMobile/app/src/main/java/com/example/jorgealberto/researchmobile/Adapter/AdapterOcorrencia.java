package com.example.jorgealberto.researchmobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jorgealberto.researchmobile.InformationMain;
import com.example.jorgealberto.researchmobile.R;
import com.example.jorgealberto.researchmobile.model.Ocorrencia;

import java.util.List;

/**
 * Created by sspbr on 11/05/2016.
 */
public class AdapterOcorrencia extends RecyclerView.Adapter<AdapterOcorrencia.ViewHolder> {

    private List<Ocorrencia> OcorrenciaList;
    Context context;
    private Activity activity;



    View view;

    public AdapterOcorrencia(List<Ocorrencia> ocorrenciaList, Activity activity, Context context) {
        this.context = context;
        this.OcorrenciaList = ocorrenciaList;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.cardocorrencia, parent, false);
        ViewHolder ViewHolder = new ViewHolder(view);

        RelativeLayout nRelativeLayout = (RelativeLayout) view.findViewById(R.id.relativecard);

        CardView cardView = (CardView) view.findViewById(R.id.cardViewOcorrencia);
      //  android.support.v7.widget.CardView.LayoutParams layoutParams = (android.support.v7.widget.CardView.LayoutParams) cardView.getLayoutParams();



        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        holder.TextViewId.setText(OcorrenciaList.get(position).getId());
        holder.TextViewReclamante.setText(OcorrenciaList.get(position).getReclamante());
        holder.TextViewEntrada.setText(OcorrenciaList.get(position).getEntrada());
        holder.TextViewConceito.setText(OcorrenciaList.get(position).getConceito());


        // https://github.com/daimajia/AndroidViewAnimations

        holder.ImageViewOcorrencia.setColorFilter(holder.ImageViewOcorrencia.getContext().getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);


        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(100, 100);

        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        ProgressBar mProgressBar;
        mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge);
        mProgressBar.setIndeterminate(true);
        mProgressBar.getIndeterminateDrawable().setColorFilter(holder.ImageViewOcorrencia.getContext().getResources().getColor(R.color.blue_ligth), android.graphics.PorterDuff.Mode.MULTIPLY);
        mProgressBar.setVisibility(View.INVISIBLE);

        holder.relative.addView(mProgressBar, params);

    }

    @Override
    public int getItemCount() {
        return OcorrenciaList.size();
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {




        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {



        super.onAttachedToRecyclerView(recyclerView);
    }

    //this function associated data with component Activity
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ImageViewOcorrencia;

        TextView TextViewId;
        TextView TextViewConceito;
        TextView TextViewEntrada;
        TextView  TextViewReclamante;

        CardView cardView;
        RelativeLayout relative;


        public ViewHolder(View itemView) {
            super(itemView);
            ImageViewOcorrencia = (ImageView) itemView.findViewById(R.id.ImageViewOcorrencia);

            TextViewId = (TextView) itemView.findViewById(R.id.TextViewId);
            TextViewConceito = (TextView) itemView.findViewById(R.id.textViewConceito);
            TextViewEntrada = (TextView) itemView.findViewById(R.id.TextViewEntrada);
            TextViewReclamante= (TextView) itemView.findViewById(R.id.TextViewReclamante);

            cardView = (CardView) itemView.findViewById(R.id.cardViewOcorrencia);
            relative = (RelativeLayout) itemView.findViewById(R.id.root);





            cardView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            ((CardView) v).setCardElevation(1);
                            v.invalidate();
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            ((CardView) v).setCardElevation(10);
                            v.invalidate();
                            int itemPosition = getAdapterPosition();

                            String Id = OcorrenciaList.get(itemPosition).getId();
                            String Endereco = OcorrenciaList.get(itemPosition).getEndereco();
                            String Conceito = OcorrenciaList.get(itemPosition).getConceito();
                            String Oc = "LAT"+ OcorrenciaList.get(itemPosition).getLATITUDE()  + "LOG" + OcorrenciaList.get(itemPosition).getLONGITUDE() ;
                            String Entrada = OcorrenciaList.get(itemPosition).getEntrada();
                            String Transmissao = OcorrenciaList.get(itemPosition).getTransmissao();
                            String Reclamante = OcorrenciaList.get(itemPosition).getReclamante();
                            String IncidenciaGestao = OcorrenciaList.get(itemPosition).getIncidenciaGestao();

                            Intent intent = new Intent(activity, InformationMain.class);
                            intent.putExtra("id", Id);
                            intent.putExtra("endereco", Endereco);
                            intent.putExtra("conceito", Conceito);
                            intent.putExtra("oc", Oc);
                            intent.putExtra("entrada", Entrada);
                            intent.putExtra("transmissao", Transmissao);
                            intent.putExtra("reclamante", Reclamante);
                            intent.putExtra("incidenciaGestao", IncidenciaGestao);

                            activity.startActivity(intent);
                            activity.overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
                            break;
                        }
                    }
                    return false;
                }
            });


        }
    }


    private void animate(final View view, final int position){


        //view.setVisibility(View.VISIBLE);


    }


/*    private void ProgresDynamic(CardView nCardview) {

        RelativeLayout nRelativeLayout = (RelativeLayout) nCardview.getParent();
        int count =  nRelativeLayout.getChildCount();
        int i = 0;
        while (i < count) {
            View child = nRelativeLayout.getChildAt(i);
            if (child instanceof ProgressBar) {
                ((ProgressBar) child).setVisibility(View.VISIBLE);


            }
            i++;
        }
    }*/









    private void ProgresBegin(CardView nCardview) {

        nCardview.setCardBackgroundColor(nCardview.getContext().getResources().getColor(R.color.white));
        RelativeLayout nRelativeLayout = (RelativeLayout) nCardview.getParent();
        int count =  nRelativeLayout.getChildCount();
        int i = 0;
        while (i < count) {
            View child = nRelativeLayout.getChildAt(i);
            if (child instanceof ProgressBar) {
                // testar em aparelho
                ((ProgressBar) child).setVisibility(View.INVISIBLE);

            }
            i++;
        }


    }


}

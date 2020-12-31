package com.example.jorgealberto.cadeconsumo.Adapter;

/**
 * Created by jorgealberto on 10/09/2016.
 */
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.jorgealberto.cadeconsumo.model.*;
//import com.softjads.pesquisaandroid.MainActivity;

public class MainAdapter extends BaseAdapter{
    private LayoutInflater mInflater;

    private opcao Oopcao;

    private ArrayList <opcao> listaOpcao;

    private TextView txt_id_opcao;
    private TextView txt_pergunta_opcao;
    private TextView txt_opcao;

    public MainAdapter(Context context, ArrayList<opcao> listaOpcao)
    {
        this.listaOpcao = listaOpcao;
        mInflater = LayoutInflater.from(context);
    }


    public int getCount(){
        return listaOpcao.size();

    }

    public long getItemId(int position){
        return position;
    }


    public Object getItem(int position){
        return listaOpcao.get(position);
    }

    public void addItem(opcao Oopcao){
        listaOpcao.add(Oopcao);
        this.notifyDataSetChanged();
    }

    public void setItem(int position, opcao Oopcao){
        listaOpcao.set(position,Oopcao);
        this.notifyDataSetChanged();
    }


    public View getView(int position, View view, ViewGroup parent){

        return view;
    }



}


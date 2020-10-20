package com.example.jorgealberto.researchmobile;

import android.app.Fragment;
import android.content.Context;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorgealberto.researchmobile.library.AppStatus;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InternetFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InternetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InternetFragment extends Fragment {



    public View fragmentView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        fragmentView = inflater.inflate(R.layout.fragment_internet, container, false);

        ImageView imageButtonConecao = (ImageView) fragmentView.findViewById(R.id.imageButtonConecao);
        TextView textViewInformacao = (TextView) fragmentView.findViewById(R.id.textViewInformacao);

        AppStatus nAppStatus = new AppStatus();
        if (nAppStatus.isOnline()) {
            imageButtonConecao.setImageResource(R.mipmap.aceitar);
            textViewInformacao.setText(getString(R.string.conexao));
            textViewInformacao.setTextColor(textViewInformacao.getContext().getResources().getColor(R.color.green_main));
            imageButtonConecao.setColorFilter(imageButtonConecao.getContext().getResources().getColor(R.color.green_main), PorterDuff.Mode.SRC_ATOP);
        }else{
            imageButtonConecao.setImageResource(R.mipmap.aceitarno);
            textViewInformacao.setText(getString(R.string.Naoconexao));
            textViewInformacao.setTextColor(textViewInformacao.getContext().getResources().getColor(R.color.red));
            imageButtonConecao.setColorFilter(imageButtonConecao.getContext().getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);

        }
       // rv = (RecyclerView) fragmentView.findViewById(R.id.recyclerViewOcorrencia);


        return fragmentView;
    }





}

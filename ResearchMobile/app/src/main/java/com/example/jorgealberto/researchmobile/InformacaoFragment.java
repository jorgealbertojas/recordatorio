package com.example.jorgealberto.researchmobile;

import android.app.Fragment;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InformacaoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InformacaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InformacaoFragment extends Fragment {



    public View fragmentView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        fragmentView = inflater.inflate(R.layout.fragment_informacao, container, false);

        //ImageView imageButtonConecao = (ImageView) fragmentView.findViewById(R.id.imageButtonConecao);
        //imageButtonConecao.setColorFilter(imageButtonConecao.getContext().getResources().getColor(R.color.green_main), PorterDuff.Mode.SRC_ATOP);

       // rv = (RecyclerView) fragmentView.findViewById(R.id.recyclerViewOcorrencia);


        return fragmentView;
    }





}

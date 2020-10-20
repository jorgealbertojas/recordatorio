package com.example.jorgealberto.researchmobile.util;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jorgealberto.researchmobile.R;
import com.example.jorgealberto.researchmobile.modelJson.alimentos;

import java.util.ArrayList;

public class StatusAdapter  extends ArrayAdapter<alimentos> {

    private Context context;
    private ArrayList<alimentos> statuses;
    public Resources res;
    alimentos currRowVal = null;
    LayoutInflater inflater;

    public StatusAdapter(Context context,
                         int textViewResourceId, ArrayList<alimentos> statuses,
                         Resources resLocal) {
        super(context, textViewResourceId, statuses);
        this.context = context;
        this.statuses = statuses;
        this.res = resLocal;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.textview_spinner_item, parent, false);
        currRowVal = null;
        currRowVal = (alimentos) statuses.get(position);
        TextView label = (TextView) row.findViewById(R.id.text1);
        if (position == 0) {
            label.setText("Please select status");
        } else {
            label.setText(currRowVal.getId());
        }

        return row;
    }
}

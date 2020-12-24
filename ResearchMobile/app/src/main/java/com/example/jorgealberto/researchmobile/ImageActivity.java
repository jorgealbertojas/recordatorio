package com.example.jorgealberto.researchmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Bundle extras = getIntent().getExtras();
        String imagem_url = extras.getString("imagem_url");

        ImageView imageView = (ImageView) findViewById(R.id.imageurl);
        Picasso.get().load(imagem_url).into(imageView);
    }
}
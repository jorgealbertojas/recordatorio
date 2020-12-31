package com.softjads.cadeconsumo;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class SplashActivity extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 6000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        ImageView ceg = (ImageView) findViewById(R.id.ceg);

        //Bitmap bMap = BitmapFactory.decodeFile(MyConstant.storageCliente+"cliente.png");
        //sspbr.setImageBitmap(bMap);

        YoYo.with(Techniques.BounceInDown)
                .duration(3000)
                .playOn(ceg);

/*        YoYo.with(Techniques.RotateOutUpLeft)
                .duration(2000)
                .delay(4000)
                .playOn(ceg);*/

/*        YoYo.with(Techniques.BounceInDown)
                .duration(2000);*/

/*        YoYo.with(Techniques.RotateOut)

                .duration(2000)
                .delay(4000);*/

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);

               // Intent i2 = new Intent(SplashActivity.this, login.class);
               // startActivity(i2);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    /**
     * Find Data the API Json with Retrofit
     */



}

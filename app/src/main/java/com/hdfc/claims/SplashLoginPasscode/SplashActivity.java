package com.hdfc.claims.SplashLoginPasscode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.hdfc.claims.R;

public class SplashActivity extends AppCompatActivity {

    private Context context;
    private SharedPreferences pref;
    private ImageView imgHDFC;

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initializeViews();

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                if (pref.getString("OnlinePassCode", "").equals("")) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, PasscodeActivity.class));
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);


    }

    private void initializeViews() {

        context = this;
        pref = context.getSharedPreferences("Session Data", MODE_PRIVATE);

        imgHDFC = (ImageView) findViewById(R.id.imgHDFC);

        Animation myFadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in_image);
        imgHDFC.startAnimation(myFadeInAnimation); //Set animation to your ImageView

    }


}

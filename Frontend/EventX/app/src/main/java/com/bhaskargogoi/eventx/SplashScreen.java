package com.bhaskargogoi.eventx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    Animation top_animation;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        top_animation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        imageView = findViewById(R.id.splashScreenLogo);

        imageView.setAnimation(top_animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ifLoggedIn();
            }
        }, 1000);
    }

    void ifLoggedIn()
    {
        SharedPreferences sp = getSharedPreferences("credentials",MODE_PRIVATE);
        if(sp.contains("token")){
            startActivity(new Intent(getApplicationContext(),  MainActivity.class));
            finish();
        } else{
            startActivity(new Intent(getApplicationContext(),  Login.class));
            finish();

        }

    }
}
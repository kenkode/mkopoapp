package com.example.lixnet.mkopo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.example.lixnet.mkopo.R;
import com.example.lixnet.mkopo.animations.LogoProgressAnimation;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_TIME = 5000;

    private ProgressBar logoProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logoProgress = (ProgressBar) findViewById(R.id.logo_progress);

        new Thread(new Runnable() {
            @Override
            public void run() {
                updateProgress();
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                //Intent intent = new Intent(GESplashActivity.this, GasExpress.class);
                startActivity(intent);
                finish();
            }
        }).start();
    }

    private void updateProgress() {
        try {
            LogoProgressAnimation logoProgressAnimation = new LogoProgressAnimation(logoProgress);
            logoProgressAnimation.setDuration(SPLASH_TIME);
            logoProgress.startAnimation(logoProgressAnimation);
            Thread.sleep(SPLASH_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

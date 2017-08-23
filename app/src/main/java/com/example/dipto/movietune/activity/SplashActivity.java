package com.example.dipto.movietune.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.dipto.movietune.R;

/**
 * Created by Dipto on 8/22/2017.
 */

public class SplashActivity extends AppCompatActivity {

    Thread mythread ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE) ;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        mythread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2500);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {

                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class) ;
                    startActivity(intent);
                    finish();
                }
            }
        };

        mythread.start();

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

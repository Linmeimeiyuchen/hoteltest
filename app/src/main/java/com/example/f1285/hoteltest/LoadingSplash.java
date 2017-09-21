package com.example.f1285.hoteltest;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LoadingSplash extends AppCompatActivity {

    private Typeface typeface;
    private TextView textView_welcome;
    private TextView textView_beta;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_splash);

        typeface = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
        textView_welcome = (TextView) findViewById(R.id.textView_welcome);
        textView_beta = (TextView) findViewById(R.id.textView_beta);
        //textView_welcome.setTypeface(typeface);
        //textView_beta.setTypeface(typeface);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                intent = new Intent();
                intent.setClass(LoadingSplash.this, LaunchPage.class);
                startActivity(intent);
                finish();
            }
        }, 3000);


    }
}

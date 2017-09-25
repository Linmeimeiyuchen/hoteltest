package com.example.f1285.hoteltest;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoadingSplash extends AppCompatActivity {

    private Typeface typeface;
    private TextView textView_welcome;
    private TextView textView_beta;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_splash);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.f1285.hoteltest",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

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

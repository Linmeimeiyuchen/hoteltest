package com.example.f1285.hoteltest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Login extends AppCompatActivity {

    Toolbar toolbarLogin;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbarLogin = (Toolbar) findViewById(R.id.toolbar_login);
        toolbarLogin.setNavigationIcon(R.drawable.button_back);
        setSupportActionBar(toolbarLogin);

        toolbarLogin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                intent.setClass(Login.this, LaunchPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

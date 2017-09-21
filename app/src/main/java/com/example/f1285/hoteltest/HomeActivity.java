package com.example.f1285.hoteltest;

import android.graphics.Typeface;
import android.support.constraint.solver.SolverVariable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentContainer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView toolbar_title;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar_title = (TextView) findViewById(R.id.toolbar_home_title);
        typeface = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        toolbar_title.setTypeface(typeface);

    }
}

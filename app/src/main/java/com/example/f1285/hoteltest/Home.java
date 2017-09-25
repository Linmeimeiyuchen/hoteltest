package com.example.f1285.hoteltest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity {

    private static final String TAG = "homeActivity";
    private TextView textViewUserName;
    private ImageView imageViewUserPhoto;
    private Bundle bundle;
    private String userName, photoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bundle  = this.getIntent().getExtras();
        userName = bundle.getString("name");
        photoUrl = bundle.getString("photo");
        //Log.d(TAG, bundle.getString("photo"));

        imageViewUserPhoto = (ImageView) findViewById(R.id.imageView_home_userPhoto);

        if( userName != null ){
            textViewUserName = (TextView) findViewById(R.id.textView_home_userName);
            textViewUserName.setText(userName);
        }
        if( photoUrl != null ) {
            Picasso.with(getApplicationContext()).load(photoUrl).into(imageViewUserPhoto);
        }
    }

}

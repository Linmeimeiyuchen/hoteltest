package com.example.f1285.hoteltest;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

public class Register extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private Toolbar toolbarRegister;
    private EditText editTextEmail, editTextPassword, editTextUsername, editTextBirthday;
    private ImageView imageViewEmailAvailable;
    private String[] userEmail = {"f1285709@gmail.com","linmeimeiyuchen@gmail.com"};
    private Calendar calendar;
    private Intent intent;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbarRegister = (Toolbar) findViewById(R.id.toolbar_register);
        toolbarRegister.setNavigationIcon(R.drawable.button_back);
        setSupportActionBar(toolbarRegister);

        toolbarRegister.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                intent.setClass(Register.this, LaunchPage.class);
                startActivity(intent);
                finish();
            }
        });

        editTextEmail = (EditText) findViewById(R.id.editText_register_email);
        editTextPassword = (EditText) findViewById(R.id.editText_register_password);
        editTextUsername = (EditText) findViewById(R.id.editText_register_username);
        editTextBirthday = (EditText) findViewById(R.id.editText_register_birthday);
        imageViewEmailAvailable = (ImageView) findViewById(R.id.imageView_emailAvailable);
        calendar = Calendar.getInstance();

        // 當 user 離開 email EditText
        editTextEmail.setOnFocusChangeListener(new EditText.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus == false){
                    if(checkEmailIsAvailable())
                        setUsernameForEmail();
                }
            }
        });

        final DatePickerDialog.OnDateSetListener datepickListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            }
        };

        editTextBirthday.setInputType(InputType.TYPE_NULL);
        editTextBirthday.setOnClickListener(new EditText.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Register.this,
                        datepickListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });



    }

    // check email 是否已經有人使用過
    private boolean checkEmailIsAvailable(){
        Boolean emailAvailable = true;

        for(int i = 0; i < userEmail.length; i++){
            if( editTextEmail.getText().toString().equals(userEmail[i]) ) {
                emailAvailable = false;
            }
        }
        if( emailAvailable == true )
            imageViewEmailAvailable.setVisibility(View.VISIBLE);

        return  emailAvailable;
    }

    // 將 email 當 username 設定
    private void setUsernameForEmail(){
        String[] email = editTextEmail.getText().toString().split("@");
        editTextUsername.setText(email[0]);
    }

}

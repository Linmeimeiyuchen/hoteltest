package com.example.f1285.hoteltest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.AppEventsLoggerUtility;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Login extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private LinearLayout buttonLoginFB;
    private Toolbar toolbarLogin;
    private Intent intent;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "Login");

        callbackManager = CallbackManager.Factory.create();
        buttonLoginFB = (LinearLayout) findViewById(R.id.button_login_fb);
        buttonLoginFB.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
            }
        });

        getFacebookToken();

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

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void getFacebookToken(){
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = loginResult.getAccessToken();
                Log.d(TAG, "access token got");

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        try {
                            //Log.d(TAG, "userName = " + object.getString("name"));
                            //Log.d(TAG, "Photo = " + object.getJSONObject("picture").getJSONObject("data").getString("url"));

                            // 如果確定有登入成功，且有 name 就換頁面(Home)
                            if( object.getString("name") != "" ){
                                intent = new Intent();
                                intent.setClass(Login.this, Home.class);
                                bundle = new Bundle();
                                bundle.putString("name", object.getString("name"));
                                bundle.putString("photo", object.getJSONObject("picture").getJSONObject("data").getString("url"));
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Get facebook data from login
                        //Bundle bFacebookData = getFacebookData(object);
                    }
                });

                // 設定要讀取那些資料，這四行一定要打非常重要
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, first_name, last_name, email, gender, birthday, picture.type(small)"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "Login cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "Login error");
            }
        });
    }

}

package com.example.f1285.hoteltest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.display.DisplayManager;
import android.support.design.widget.TabLayout;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

public class LaunchPage extends AppCompatActivity {

    private static final String TAG = "LaunchPage";
    private Button buttonLgoin, buttonRegister, buttonSubmit;
    private ImageButton buttonScan;
    private TextView textViewViewpager, textViewError;
    private EditText editTextAuthcode;
    private LinearLayout linearLayoutError, linearLayoutButtonSubmit;
    private PercentRelativeLayout percentRelativeLayout;
    private View childOfPercentRelativeLayout;
    private int usableHeightPrevious;
    private Typeface typefaceRobotoRegular, typefaceRobotoMedium;
    private ViewPager viewPager;
    private ViewpagerAdapter viewpagerAdapter;
    private String[] stringsViewpager = {"Customizing your best stay of travel,\\nincredible experience of hotel.1","Customizing your best stay of travel,\\nincredible experience of hotel.2"};
    private TabLayout tabLayoutDots;
    private int dotsCount;
    private ImageView[] dots;
    private Context context;
    private boolean keyboard = false;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_page);

        context = this;

        // 設定 Typeface
        typefaceRobotoRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        typefaceRobotoMedium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");

        // 設定 textView，自訂字體
        textViewError = (TextView) findViewById(R.id.textView_error);
        textViewError.setTypeface(typefaceRobotoRegular);

        buttonSubmit = (Button) findViewById(R.id.button_submit);
        buttonSubmit.setTypeface(typefaceRobotoRegular);
        buttonLgoin = (Button) findViewById(R.id.button_login);
        buttonLgoin.setTypeface(typefaceRobotoRegular);
        buttonRegister = (Button) findViewById(R.id.button_register);
        buttonRegister.setTypeface(typefaceRobotoRegular);
        buttonScan = (ImageButton) findViewById(R.id.button_scan);

        buttonScan.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {

                // QRcode scanner for Zxing
                IntentIntegrator intentIntegrator = new IntentIntegrator(LaunchPage.this);
                intentIntegrator.setCaptureActivity(CustomCaptureActivity.class);
                intentIntegrator.initiateScan();
                /*
                intent = new Intent();
                intent.setClass(LaunchPage.this, QrcodeScan.class);
                startActivity(intent);*/
            }
        });

        linearLayoutError = (LinearLayout) findViewById(R.id.linearLayout_launchpager_textView_error);
        percentRelativeLayout = (PercentRelativeLayout) findViewById(R.id.percentRelativeLayout);
        linearLayoutButtonSubmit = (LinearLayout) findViewById(R.id.linearLayout_launchpager_button_submit);

        // Get all child view in PercentRelativeLayout
        childOfPercentRelativeLayout = percentRelativeLayout.getChildAt(0);
        // Get previous height
        usableHeightPrevious = getUsableHeight();
        //Log.d(TAG, "usableHeightPrevious = " + usableHeightPrevious);

        // Listener when view change
        childOfPercentRelativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                if( checkKeyboardJump() ) {
                    // Get LayoutParams in linearLayoutButtonSubmit
                    PercentRelativeLayout.LayoutParams layoutParams = (PercentRelativeLayout.LayoutParams) linearLayoutButtonSubmit.getLayoutParams();
                    // Set topMargin in linearLayoutButtonSubmit, 因為 getUsableHeight  單位為 px, 因此須轉為 pd
                    layoutParams.topMargin = (int) (getUsableHeight() - dpTopx(context, 68));
                    linearLayoutButtonSubmit.setLayoutParams(layoutParams);
                    //Log.d(TAG, "usableHeight = "+ getScreenHeight());
                    //Log.d(TAG, "Top = "+ layoutParams.topMargin);
                    linearLayoutButtonSubmit.setVisibility(View.VISIBLE);
                }
                if( keyboard == false )
                    linearLayoutButtonSubmit.setVisibility(View.GONE);
            }
        });

        editTextAuthcode = (EditText) findViewById(R.id.editText_authcode);
        // Listener when someone touch this editText
        editTextAuthcode.setOnTouchListener( new EditText.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });

        buttonSubmit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutError.setVisibility(View.VISIBLE);
            }
        });

        buttonLgoin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                intent.setClass(LaunchPage.this, Login.class);
                startActivity(intent);
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager_launchpage);
        viewpagerAdapter = new ViewpagerAdapter(this, stringsViewpager);
        viewPager.setAdapter(viewpagerAdapter);

        tabLayoutDots = (TabLayout) findViewById(R.id.viewpager_dot);
        tabLayoutDots.setupWithViewPager(viewPager, true);

    }

    // QR code scanner 結束後，回傳三個參數( requestCode 表示哪個 Activity 回傳的，resultCode 為 subActivity 透過 setResult() 回傳，data 帶有回傳值 )
    // resultCode, data 皆由 subActivity 的 setResult(int resultCode, Intent data) 回傳
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 获取解析结果
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                //Toast.makeText(this, "取消扫描", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, "扫描内容:" + result.getContents(), Toast.LENGTH_LONG).show();
                editTextAuthcode.setText( result.getContents() );
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // Get keyboard 彈起後剩多少螢幕可以使用, 單位為 px
    private int getUsableHeight(){
        // new one rect(矩形)
        Rect rect = new Rect();
        // Get 視窗可視大小，存入 rect 中
        childOfPercentRelativeLayout.getWindowVisibleDisplayFrame(rect);
        //Log.d(TAG, Integer.toString(rect.bottom));
        //Log.d(TAG, Integer.toString((rect.bottom - rect.top)));
        return (rect.bottom - rect.top);
    }

    // 確認 keyboard 是否彈起
    private boolean checkKeyboardJump(){
        int usableHeightNow = getUsableHeight();
        Log.d(TAG, "Previous = "+ usableHeightPrevious);
        Log.d(TAG, "Now = "+ usableHeightNow);
        Log.d(TAG, "Height = "+ percentRelativeLayout.getHeight());
        if( usableHeightNow != usableHeightPrevious ){
            if( (percentRelativeLayout.getHeight()-usableHeightNow) > (percentRelativeLayout.getHeight()/4) ){
                usableHeightPrevious = usableHeightNow;
                keyboard = true;
                return true;
            }
            else{
                if( usableHeightNow == percentRelativeLayout.getHeight() ){
                    usableHeightPrevious = usableHeightNow;
                    keyboard = false;
                    return false;
                }
            }
        }
        return false;
    }

    private float dpTopx(Context context, int dp){
        return dp * getDensity(context);
    }

    private static float getDensity(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.density;
    }

}

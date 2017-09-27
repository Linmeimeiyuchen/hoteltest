package com.example.f1285.hoteltest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.io.FileNotFoundException;
import java.util.Hashtable;

public class CustomCaptureActivity extends AppCompatActivity {

    private static final String TAG = "CustomCaptureActivity";
    private static final int PHOTO = 1;
    private Toolbar toolbarScan;
    private Intent intent;
    private TextView textViewError;


    // 條碼掃描管理器
    private CaptureManager captureManager;
    // 條碼掃描 View
    private DecoratedBarcodeView decoratedBarcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_custom_capture);
        decoratedBarcodeView = (DecoratedBarcodeView) findViewById(com.google.zxing.client.android.R.id.zxing_barcode_scanner);

        Log.d("123", "enter");

        captureManager = new CaptureManager(this, decoratedBarcodeView);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();

        textViewError = (TextView) findViewById(R.id.textView_custom_error);

        toolbarScan = (Toolbar) findViewById(R.id.toolbar_scan);
        toolbarScan.setNavigationIcon(R.drawable.button_back);
        toolbarScan.inflateMenu(R.menu.menu_toolbar_scan);

        // 按下 back page button → to LauchPage
        toolbarScan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                intent.setClass(CustomCaptureActivity.this, LaunchPage.class);
                startActivity(intent);
                finish();
            }
        });

        // 按下 load photo button → open album
        toolbarScan.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_toolbar_loadingImage:
                        Log.d(TAG, "enter");
                        intent = new Intent();
                        // set file type 檔案型態，此設定所有影像檔
                        intent.setType("image/*");
                        // Intent.ACTION_GET_CONTENT 系統會自動尋找適合的 APP 來讀取
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        // set requestCode = PHOTO
                        startActivityForResult(intent, PHOTO);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Uri uriPhoto = null;
        Log.d(TAG, "Enter");
        if( data != null ){

            switch (requestCode){
                case PHOTO:
                    // get photo uri
                    uriPhoto = data.getData();
                    Log.d(TAG, "uriPhoto = "+uriPhoto);

                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriPhoto);

                        // 此為 Bitmap to Text for Zxing 固定寫法
                        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
                        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
                        int pixels[] = new int[bitmap.getWidth()*bitmap.getHeight()];
                        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
                        RGBLuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), pixels);
                        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
                        QRCodeReader qrCodeReader = new QRCodeReader();
                        Result result = qrCodeReader.decode(binaryBitmap, hints);      // 結果值

                        // 利用 setResult 回傳 qrcode 給 LaunchPage.java
                        intent = new Intent();
                        intent.setClass(CustomCaptureActivity.this, LaunchPage.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("qrcode", result.getText());
                        intent.putExtras(bundle);
                        setResult(1, intent);   // result = 1 代表是利用 photo 掃描
                        finish();
                    } catch (Exception e){
                        // 如果 photo 無法辨識則顯示 error
                        textViewError.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        captureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return decoratedBarcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

}

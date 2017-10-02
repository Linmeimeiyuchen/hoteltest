package com.example.f1285.hoteltest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

import com.journeyapps.barcodescanner.ViewfinderView;

/**
 * Created by f1285 on 2017/9/20.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class CustomViewfinderView extends ViewfinderView {

    private static final String TAG = "CustomViewfinderView";

    // 邊角線寬度
    // TypedValue.applyDimension(int unit, float value, DisplayMetrics metrics) 用來轉換 px/dp/sp
    private float mLineDepth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
    // 邊角線顏色
    //private int mLineColor = getResources().getColor(R.color.line_viewfinderView, null);
    private int mLineColor = ContextCompat.getColor(getContext(),R.color.line_viewfinderView);
    // 邊角線長度
    private float mLineRate = 0.1f;

    public CustomViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        refreshSizes();
        if(framingRect == null || previewFramingRect == null){
            return;
        }

        Rect frame = framingRect;
        Rect previewFrame = previewFramingRect;

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        // Draw 四個角
        paint.setColor(mLineColor);
        canvas.drawRect(frame.left - mLineDepth, frame.top - mLineDepth, frame.left + frame.width() * mLineRate, frame.top, paint);
        canvas.drawRect(frame.left - mLineDepth, frame.top, frame.left, frame.top + frame.height() * mLineRate, paint);

        canvas.drawRect(frame.right - frame.width() * mLineRate, frame.top - mLineDepth, frame.right + mLineDepth, frame.top, paint);
        canvas.drawRect(frame.right, frame.top, frame.right + mLineDepth, frame.top + frame.height() * mLineRate, paint);

        canvas.drawRect(frame.left - mLineDepth, frame.bottom - frame.height() * mLineRate, frame.left, frame.bottom, paint);
        canvas.drawRect(frame.left - mLineDepth, frame.bottom, frame.left + frame.width() * mLineRate, frame.bottom + mLineDepth, paint);

        canvas.drawRect(frame.right, frame.bottom - frame.height() * mLineRate, frame.right + mLineDepth, frame.bottom, paint);
        canvas.drawRect(frame.right - frame.width() * mLineRate, frame.bottom, frame.right + mLineDepth, frame.bottom + mLineDepth, paint);

    }
}

package com.example.f1285.hoteltest;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by f1285 on 2017/9/15.
 */

public class TextViewRobotoMedium extends android.support.v7.widget.AppCompatTextView {
    public TextViewRobotoMedium(Context context) {
        super(context);

        applyCustomeFont(context);
    }

    public TextViewRobotoMedium(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        applyCustomeFont(context);
    }

    public TextViewRobotoMedium(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        applyCustomeFont(context);
    }

    private void applyCustomeFont(Context context){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
        setTypeface(typeface);
    }
}

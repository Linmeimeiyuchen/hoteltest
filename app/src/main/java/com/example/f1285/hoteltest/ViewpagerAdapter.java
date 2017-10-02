package com.example.f1285.hoteltest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ViewpagerAdapter extends PagerAdapter{

    private int [] background_resource = {R.mipmap.guest_bg_loading, R.mipmap.guest_img_bg};
    private String [] strings;
    private Context context;
    private LayoutInflater layoutInflater;
    private TextView textView;

    public ViewpagerAdapter(Context context, String [] strings){
        this.context = context;
        this.strings = strings;
    }

    @Override
    public int getCount() {
        return background_resource.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.launchpage_viewpager_layout, container, false);

        RelativeLayout relativeLayout = (RelativeLayout) item_view.findViewById(R.id.ralatiovelayout_viewpager);
        relativeLayout.setBackgroundResource( background_resource[position] );

        textView = (TextView) item_view.findViewById(R.id.textView_viewpager);
        textView.setText( strings[position] );

        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}

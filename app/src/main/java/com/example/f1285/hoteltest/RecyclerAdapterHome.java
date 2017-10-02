package com.example.f1285.hoteltest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapterHome extends RecyclerView.Adapter<RecyclerAdapterHome.ViewHolder> {

    //private String[] myDataset_text;
    //private int[] myDataset_img;
    private final static String TAG = "RecyclerAdapterHome";
    private List myDataset_text;
    private List myDataset_img;
    private ViewGroup viewGroup;
    private Bitmap bitmap = null;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewCardView;
        public RelativeLayout relativeLayoutCardView;
        public ViewHolder(View itemView){
            super(itemView);
            textViewCardView = (TextView) itemView.findViewById(R.id.textView_cardView);
            relativeLayoutCardView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_cardView);
        }
    }

    // 設定預設值
    public RecyclerAdapterHome (List myDataset_text, List myDataset_img){
        this.myDataset_text = myDataset_text;
        this.myDataset_img = myDataset_img;
    }

    // 現有 ViewHolder 不夠用，產生新的 ViewHolder
    @Override
    public RecyclerAdapterHome.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        viewGroup = parent;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cardview_home, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // 使用之前產生的 ViewHolder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewCardView.setText(myDataset_text.get(position).toString());
        Log.d(TAG, "Imgae = "+(int)myDataset_img.get(position));
        holder.relativeLayoutCardView.setBackgroundResource((int)myDataset_img.get(position));
        //bitmap = BitmapFactory.decodeResource(viewGroup.getResources(), R.drawable.img_ad2);
        //holder.relativeLayoutCardView.setBackground(new BitmapDrawable(bitmap));
        //bitmap.recycle();
        //bitmap = null;

        //holder.relativeLayoutCardView.setBackground(ContextCompat.getDrawable(viewGroup.getContext(), R.drawable.img_ad1));
        //holder.relativeLayoutCardView.setBackground(ContextCompat.getDrawable(viewGroup.getContext(), (int)myDataset_img.get(position)));
    }

    // Adapter 才知道有多少資料
    @Override
    public int getItemCount() {
        return myDataset_text.size();
    }


}

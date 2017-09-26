package com.example.f1285.hoteltest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private String[] myDataset_text;
    private int[] myDataset_img;

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
    public RecyclerAdapter (String[] myDataset_text, int[] myDataset_img){
        this.myDataset_text = myDataset_text;
        this.myDataset_img = myDataset_img;
    }

    // 現有 ViewHolder 不夠用，產生新的 ViewHolder
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cardview, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // 使用之前產生的 ViewHolder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewCardView.setText(myDataset_text[position]);
        holder.relativeLayoutCardView.setBackgroundResource(myDataset_img[position]);
    }

    // Adapter 才知道有多少資料
    @Override
    public int getItemCount() {
        return myDataset_text.length;
    }
}

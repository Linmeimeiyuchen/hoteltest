package com.example.f1285.hoteltest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RecyclerAdapterBooking extends RecyclerView.Adapter<RecyclerAdapterBooking.ViewHolder>  {

    private String[] setDataTitle1;
    private String[] setDataTitle2;
    private int[] setDataImg;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewCardViewBookingTitle1;
        public TextView textViewCardViewBookingTitle2;
        public RelativeLayout relativeLayoutCardViewBooking;
        public ViewHolder(View itemView){
            super(itemView);
            textViewCardViewBookingTitle1 = (TextView) itemView.findViewById(R.id.textView_cardView_booking_title1);
            textViewCardViewBookingTitle2 = (TextView) itemView.findViewById(R.id.textView_cardView_booking_title2);
            relativeLayoutCardViewBooking = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_cardView_Booking);
        }
    }

    // 設定預設值
    public RecyclerAdapterBooking (String[] setDataTitle1,String[] setDataTitle2, int[] setDataImg){
        this.setDataTitle1 = setDataTitle1;
        this.setDataTitle2 = setDataTitle2;
        this.setDataImg = setDataImg;
    }

    // 現有 ViewHolder 不夠用，產生新的 ViewHolder
    @Override
    public RecyclerAdapterBooking.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cardview_booking, parent, false);
        // set the view's size, margins, paddings and layout parameters
        RecyclerAdapterBooking.ViewHolder vh = new RecyclerAdapterBooking.ViewHolder(v);
        return vh;
    }

    // 使用之前產生的 ViewHolder
    @Override
    public void onBindViewHolder(RecyclerAdapterBooking.ViewHolder holder, int position) {
        holder.textViewCardViewBookingTitle1.setText(setDataTitle1[position]);
        holder.textViewCardViewBookingTitle2.setText(setDataTitle2[position]);
        holder.relativeLayoutCardViewBooking.setBackgroundResource(setDataImg[position]);
    }

    // Adapter 才知道有多少資料
    @Override
    public int getItemCount() {
        return setDataTitle1.length;
    }

}

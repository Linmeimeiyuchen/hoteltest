package com.example.f1285.hoteltest;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapterBooking extends RecyclerView.Adapter<RecyclerAdapterBooking.ViewHolder> implements ItemSwipeListener {

    private final static String TAG = "RecyclerAdapterBooking";
    //private String[] setDataTitle1;
    private List setDataTitle1;
    private List setDataTitle2;
    private List setDataImg;
    private ViewGroup viewGroup;
    //private String[] setDataTitle2;
    //private int[] setDataImg;

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        //Log.d(TAG, "on Move");
        return false;
    }

    @Override
    public void onItemSwipe(int position) {
        //Log.d(TAG, "on Swipe");
    }

    // 移除第 position 個 cardView
    @Override
    public void onItemRemove(int position) {
        setDataTitle1.remove(position);
        setDataTitle2.remove(position);
        setDataImg.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewCardViewBookingTitle1;
        public TextView textViewCardViewBookingTitle2;
        public RelativeLayout relativeLayoutCardViewBooking;

        public ViewHolder(View itemView){
            super(itemView);
            textViewCardViewBookingTitle1 = (TextView) itemView.findViewById(R.id.textView_cardView_booking_title1);
            textViewCardViewBookingTitle2 = (TextView) itemView.findViewById(R.id.textView_cardView_booking_title2);
            relativeLayoutCardViewBooking = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_cardView_Booking);
        }

        @Override
        public void onClick(View v) {

        }
    }

    // 設定預設值
    public RecyclerAdapterBooking (List setDataTitle1, List setDataTitle2, List setDataImg){
        this.setDataTitle1 = setDataTitle1;
        this.setDataTitle2 = setDataTitle2;
        this.setDataImg = setDataImg;
    }

    // 現有 ViewHolder 不夠用，產生新的 ViewHolder
    @Override
    public RecyclerAdapterBooking.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        viewGroup = parent;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cardview_booking, parent, false);
        // set the view's size, margins, paddings and layout parameters
        RecyclerAdapterBooking.ViewHolder vh = new RecyclerAdapterBooking.ViewHolder(v);
        return vh;
    }

    // 使用之前產生的 ViewHolder
    @Override
    public void onBindViewHolder(RecyclerAdapterBooking.ViewHolder holder, int position) {
        holder.textViewCardViewBookingTitle1.setText(setDataTitle1.get(position).toString());
        holder.textViewCardViewBookingTitle2.setText(setDataTitle2.get(position).toString());
        //holder.relativeLayoutCardViewBooking.setBackground(ContextCompat.getDrawable(viewGroup.getContext(), R.drawable.img_my_booking2));
        holder.relativeLayoutCardViewBooking.setBackgroundResource((int)setDataImg.get(position));
    }

    // Adapter 才知道有多少資料
    @Override
    public int getItemCount() {
        return setDataTitle1.size();
    }

}

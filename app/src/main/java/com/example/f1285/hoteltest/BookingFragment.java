package com.example.f1285.hoteltest;

import android.graphics.Canvas;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookingFragment extends Fragment{

    private final static String TAG = "BookingFragment";
    private Toolbar toolbar_booking = null;
    private RecyclerView mRecyclerView = null;
    private RecyclerAdapterBooking recyclerAdapterBooking = null;
    private RecyclerView.LayoutManager mLayoutManager = null;
    private ItemTouchHelperCallback itemTouchHelperCallback = null;
    private SwipeButtonAction swipeButtonAction = null;
    private List setDataTitle1 = new ArrayList(Arrays.asList("FunHotel", "Sulatania Hotel", "FunHotel", "Sulatania Hotel"));
    private List setDataTitle2 = new ArrayList(Arrays.asList("25 NOV WED - 28 NOV SAT", "28 NOV WED - 30 NOV SAT", "25 NOV WED - 28 NOV SAT", "28 NOV WED - 30 NOV SAT"));
    private List setDataImg = new ArrayList(Arrays.asList(R.drawable.img_my_booking1, R.drawable.img_my_booking2, R.drawable.img_my_booking1, R.drawable.img_my_booking2));
    private boolean scrollState = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        /*---- Set Toolbar ----*/
        toolbar_booking = (Toolbar) view.findViewById(R.id.toolbar_booking);
        toolbar_booking.inflateMenu(R.menu.menu_toolbar_booking);

        setupRecyclerView(view);

        return view;
    }

    /*---------------------*/
    /*---- RecycleView ----*/
    /*---------------------*/
    // Before Declare: RecyclerView.LayoutManager mLayoutManager, RecyclerView mRecyclerView, RecyclerAdapterBooking recyclerAdapterBooking
    // SwipeButtonAction swipeButtonAction, ItemTouchHelperCallback itemTouchHelperCallback
    private void setupRecyclerView( View view){

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleView_booking);
        // 設定 setHasFixedSize(true)在 item 高度固定時，，不用每次加一個 item 就算一次高可增加效率
        mRecyclerView.setHasFixedSize(true);
        // 設定使用哪種 layoutManager，recyclerView 共有三種 layoutManager 可以使用
        // LinearLayoutManager, GridLayoutManager, StaggeredGridLayoutManager
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //設定 RecyclerAdapter，RecyclerAdapterBooking 需要傳三個參數
        recyclerAdapterBooking = new RecyclerAdapterBooking(setDataTitle1, setDataTitle2, setDataImg);
        mRecyclerView.setAdapter(recyclerAdapterBooking);

        // Set Delete Button，如果確認按下後，會呼叫 onRightClicked()
        swipeButtonAction = new SwipeButtonAction() {
            @Override
            public void onRightClicked(int position) {
                Log.d(TAG, "Button on Click!!");
                recyclerAdapterBooking.onItemRemove(position);
            }
        };

        // 傳 swipeButtonAction 以監聽按鈕
        itemTouchHelperCallback = new ItemTouchHelperCallback(swipeButtonAction, getContext());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        // Draw Delete Button
        mRecyclerView.addItemDecoration( new RecyclerView.ItemDecoration(){
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                //Log.d(TAG, "onDraw enter!!");
                itemTouchHelperCallback.onDraw(c);
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent event) {
                if( event.getAction() == MotionEvent.ACTION_UP ){
                    Log.d(TAG, "onInterceptTouchEvent");
                    try{
                        for(int i = 0; i < recyclerAdapterBooking.getItemCount(); i++) {
                            View view1 = mRecyclerView.getChildAt(i);
                            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) mRecyclerView.getChildViewHolder(view1);
                            ImageView imageView = (ImageView) viewHolder.itemView.findViewById(R.id.delete_button);
                            imageView.setVisibility(View.VISIBLE);
                        }
                    }catch (Exception e){
                        Log.d(TAG, "Error");
                    }
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                Log.d(TAG, "onTouchEvent");
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                Log.d(TAG, "onRequestDisallowInterceptTouchEvent");
            }
        });


    }

}
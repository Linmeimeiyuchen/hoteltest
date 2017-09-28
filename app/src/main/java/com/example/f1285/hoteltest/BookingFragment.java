package com.example.f1285.hoteltest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BookingFragment extends Fragment {

    private Toolbar toolbar_booking;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] setDataTitle1 = {"FunHotel", "Sulatania Hotel", "FunHotel", "Sulatania Hotel"};
    private String[] setDataTitle2 = {"25 NOV WED - 28 NOV SAT", "28 NOV WED - 30 NOV SAT", "25 NOV WED - 28 NOV SAT", "28 NOV WED - 30 NOV SAT"};
    private int[] setDataImg = {R.drawable.img_my_booking1, R.drawable.img_my_booking2, R.drawable.img_my_booking1, R.drawable.img_my_booking2};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        /*---- Set Toolbar ----*/
        toolbar_booking = (Toolbar) view.findViewById(R.id.toolbar_booking);
        toolbar_booking.inflateMenu(R.menu.menu_toolbar_booking);

        /*---- RecycleView ----*/
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleView_booking);
        // 設定 setHasFixedSize(true)在 item 高度固定時，，不用每次加一個 item 就算一次高可增加效率
        mRecyclerView.setHasFixedSize(true);
        // 設定使用哪種 layoutManager，recyclerView 共有三種 layoutManager 可以使用
        // LinearLayoutManager, GridLayoutManager, StaggeredGridLayoutManager
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //設定 RecyclerAdapter
        mAdapter = new RecyclerAdapterBooking(setDataTitle1, setDataTitle1, setDataImg);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}

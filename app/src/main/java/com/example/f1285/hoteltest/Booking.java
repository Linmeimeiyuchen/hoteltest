package com.example.f1285.hoteltest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;

public class Booking extends AppCompatActivity {

    private Intent intent;
    private Toolbar toolbar_booking;
    private BottomNavigationBar bottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_booking);

        /*---- BottomNavigationBar ----*/
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        // 未被選中時的顏色
        bottomNavigationBar.setInActiveColor(R.color.bottomNavigationBarInAction);
        // 被選中時的顏色
        bottomNavigationBar.setActiveColor(R.color.bottomNavigationBarAction);
        // 設定右上角未讀訊息數
        TextBadgeItem textBadgeItem = new TextBadgeItem()
                .setBorderWidth(2)
                .setBorderColor(R.color.badgeItem)
                .setText("5")
                .setAnimationDuration(1000)     // 消失動畫速度
                .setHideOnSelect(true);         // 點擊是否消失

        // BottomNavigationItem 表示被選中時的圖示，setInactiveIconResource 表示未被選中時的圖示
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.guest_ic_home_focus, "HOME").setInactiveIconResource(R.drawable.guest_ic_home))
                .addItem(new BottomNavigationItem(R.drawable.guest_ic_booking_focus, "My Booking").setInactiveIconResource(R.drawable.guest_ic_booking))
                .addItem(new BottomNavigationItem(R.drawable.guest_ic_coupon_focus, "Coupon").setInactiveIconResource(R.drawable.guest_ic_coupon))
                .addItem(new BottomNavigationItem(R.drawable.guest_ic_messenger_focus, "Messenger").setInactiveIconResource(R.drawable.guest_ic_messenger).setBadgeItem(textBadgeItem))
                .setFirstSelectedPosition(1)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position){
                    case 0:
                        intent = new Intent();
                        intent.setClass(Booking.this, Home.class);
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

}

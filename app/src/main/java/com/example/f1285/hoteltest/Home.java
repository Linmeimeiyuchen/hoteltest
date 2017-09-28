package com.example.f1285.hoteltest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    private static final String TAG = "homeActivity";
    private HomeFragment homeFragment;
    private BookingFragment bookingFragment;
    private BottomNavigationBar bottomNavigationBar;
    private Bundle bundle;
    private Intent intent;
    private String userName, photoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        setContentView(R.layout.home);

        // 取得 Facebook
        bundle  = this.getIntent().getExtras();
        userName = bundle.getString("name");
        photoUrl = bundle.getString("photo");
        //Log.d(TAG, bundle.getString("photo"));

        //toolbar_home = (Toolbar) findViewById(R.id.toolbar_home);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_home);

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
                .setFirstSelectedPosition(0)
                .initialise();
        // Set BottomNavigationBar Listener
        bottomNavigationBar.setTabSelectedListener(this);
        // 設定 Fragment 初始值
        setDefaultFragment();

    }

    // 設定預設選項和 fragment
    private void setDefaultFragment(){
        // FragmentManager 用來管理 Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        // FragmentTransaction 用來對 Fragment 做處理，add/remove/detach/attach/replace/addToBackStack...
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // HomeFragment 需要 userName and userPhoto 因此需要傳給他
        bundle = new Bundle();
        bundle.putString("userName", userName);
        bundle.putString("photoUrl", photoUrl);
        homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.linearLayout_home, homeFragment);
        fragmentTransaction.commit();
    }

    // BottomNavigationBar Listener
    // onTabSelected 未被選中 → 被選中
    @Override
    public void onTabSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (position){
            case 0:
                Log.d(TAG, "0");
                if(homeFragment == null){
                    bookingFragment = new BookingFragment();
                    fragmentTransaction.add(R.id.linearLayout_home, homeFragment);
                }
                fragmentTransaction.replace(R.id.linearLayout_home, homeFragment);
                break;
            case 1:
                Log.d(TAG, "1");
                if(bookingFragment == null){
                    bookingFragment = new BookingFragment();
                    fragmentTransaction.add(R.id.linearLayout_home, bookingFragment);
                }
                fragmentTransaction.replace(R.id.linearLayout_home, bookingFragment);
                break;
        }
        fragmentTransaction.commit();
    }

    // 被選中 → 未被選中
    @Override
    public void onTabUnselected(int position) {
    }

    // 被選中 → 被選中
    @Override
    public void onTabReselected(int position) {
    }
}

package com.example.f1285.hoteltest;

import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
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

public class Home extends AppCompatActivity {

    private static final String TAG = "homeActivity";
    private TextView textViewUserName;
    private ImageView imageViewUserPhoto;
    private Toolbar toolbar_home;
    private BottomNavigationBar bottomNavigationBar;
    private Bundle bundle;
    private String userName, photoUrl;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] myDataset_text = {"Greece Aegean Sea Romantic1...", "Greece Aegean Sea Romantic2...", "Greece Aegean Sea Romantic3..."
            , "Greece Aegean Sea Romantic4..."};
    private int[] myDataset_img = {R.drawable.img_ad1, R.drawable.img_ad2, R.drawable.img_ad2, R.drawable.img_ad1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bundle  = this.getIntent().getExtras();
        userName = bundle.getString("name");
        photoUrl = bundle.getString("photo");
        //Log.d(TAG, bundle.getString("photo"));

        imageViewUserPhoto = (ImageView) findViewById(R.id.imageView_home_userPhoto);
        toolbar_home = (Toolbar) findViewById(R.id.toolbar_home);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_home);

        if( userName != null ){
            textViewUserName = (TextView) findViewById(R.id.textView_home_userName);
            textViewUserName.setText(userName);
        }
        if( photoUrl != null ) {
            Picasso.with(getApplicationContext()).load(photoUrl).into(imageViewUserPhoto);
        }

        /*---- Toolbar ----*/
        toolbar_home.inflateMenu(R.menu.menu_toolbar_home);

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
                .setAnimationDuration(2000)
                .setHideOnSelect(true);

        // BottomNavigationItem 表示被選中時的圖示，setInactiveIconResource 表示未被選中時的圖示
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.guest_ic_home_focus, "HOME").setInactiveIconResource(R.drawable.guest_ic_home))
                .addItem(new BottomNavigationItem(R.drawable.guest_ic_booking_focus, "My Booking").setInactiveIconResource(R.drawable.guest_ic_booking))
                .addItem(new BottomNavigationItem(R.drawable.guest_ic_coupon_focus, "Coupon").setInactiveIconResource(R.drawable.guest_ic_coupon))
                .addItem(new BottomNavigationItem(R.drawable.guest_ic_messenger_focus, "Messenger").setInactiveIconResource(R.drawable.guest_ic_messenger).setBadgeItem(textBadgeItem))
                .initialise();

        /*---- RecycleView ----*/
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView_home);
        // 設定 setHasFixedSize(true)在 item 高度固定時，，不用每次加一個 item 就算一次高可增加效率
        mRecyclerView.setHasFixedSize(true);
        // 設定使用哪種 layoutManager，recyclerView 共有三種 layoutManager 可以使用
        // LinearLayoutManager, GridLayoutManager, StaggeredGridLayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //設定 RecyclerAdapter
        mAdapter = new RecyclerAdapter(myDataset_text, myDataset_img);
        mRecyclerView.setAdapter(mAdapter);

    }
}

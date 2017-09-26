package com.example.f1285.hoteltest;

import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity {

    private static final String TAG = "homeActivity";
    private TextView textViewUserName;
    private ImageView imageViewUserPhoto;
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
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_home);

        if( userName != null ){
            textViewUserName = (TextView) findViewById(R.id.textView_home_userName);
            textViewUserName.setText(userName);
        }
        if( photoUrl != null ) {
            Picasso.with(getApplicationContext()).load(photoUrl).into(imageViewUserPhoto);
        }

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setInActiveColor(R.color.bottomNavigationBarInAction);
        bottomNavigationBar.setActiveColor(R.color.bottomNavigationBarAction);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.guest_ic_home, "HOME"))
                .addItem(new BottomNavigationItem(R.drawable.guest_ic_booking, "My Booking"))
                .addItem(new BottomNavigationItem(R.drawable.guest_ic_coupon, "Coupon"))
                .addItem(new BottomNavigationItem(R.drawable.guest_ic_messenger, "Messenger"))
                .initialise();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView_home);
        // 設定 setHasFixedSize(true)在 item 高度固定時，，不用每次加一個 item 就算一次高可增加效率
        mRecyclerView.setHasFixedSize(true);
        // 設定使用哪種 layoutManager，recyclerView 共有三種 layoutManager 可以使用
        // LinearLayoutManager, GridLayoutManager, StaggeredGridLayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 設        mAdapter = new RecyclerAdapter(myDataset_text, myDataset_img);
        mRecyclerView.setAdapter(mAdapter);
    }
}

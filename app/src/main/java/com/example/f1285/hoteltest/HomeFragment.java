package com.example.f1285.hoteltest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private Bundle bundle;
    private ImageView imageViewUserPhoto;
    private Toolbar toolbar_home;
    private CircleImageView circleImageViewUserPhoto;
    private TextView textViewUserName;
    private String userName, photoUrl;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] myDataset_text = {"Greece Aegean Sea Romantic1...", "Greece Aegean Sea Romantic2...", "Greece Aegean Sea Romantic3..."
            , "Greece Aegean Sea Romantic4..."};
    private int[] myDataset_img = {R.drawable.img_ad1, R.drawable.img_ad2, R.drawable.img_ad2, R.drawable.img_ad1};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 取得 Home Activity 傳過來值 (userName, photoUrl)
        bundle = getArguments();
        userName = bundle.getString("userName");
        photoUrl = bundle.getString("photoUrl");

        // Set userName and userPhoto
        if( userName != null || userName != "" ){
            textViewUserName = (TextView) view.findViewById(R.id.textView_home_userName);
            textViewUserName.setText(userName);
        }
        if( photoUrl != null || photoUrl != "" ){
            circleImageViewUserPhoto = (CircleImageView) view.findViewById(R.id.imageView_home_userPhoto);
            Picasso.with(getActivity().getApplicationContext()).load(photoUrl).into(circleImageViewUserPhoto);
        }

        /*---- Set Toolbar ----*/
        toolbar_home = (Toolbar) view.findViewById(R.id.toolbar_home);
        toolbar_home.inflateMenu(R.menu.menu_toolbar_home);

        /*---- RecycleView ----*/
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleView_home);
        // 設定 setHasFixedSize(true)在 item 高度固定時，，不用每次加一個 item 就算一次高可增加效率
        mRecyclerView.setHasFixedSize(true);
        // 設定使用哪種 layoutManager，recyclerView 共有三種 layoutManager 可以使用
        // LinearLayoutManager, GridLayoutManager, StaggeredGridLayoutManager
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //設定 RecyclerAdapter
        mAdapter = new RecyclerAdapterHome(myDataset_text, myDataset_img);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}

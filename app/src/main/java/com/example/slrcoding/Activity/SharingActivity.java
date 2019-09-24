package com.example.slrcoding.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.slrcoding.Adapter.SharingAdapter;
import com.example.slrcoding.FeedWriteActivity;
import com.example.slrcoding.R;
import com.example.slrcoding.VO.SharingVO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

public class SharingActivity extends AppCompatActivity {

    private List<SharingVO> mshareList;
    RecyclerView mSharerecyclerView;
    SharingAdapter shareAdapter;
    private ImageView sharinginformation;
    private FloatingActionButton fab_main_sharing,fab_sub1_sharing;
    //에니메이션
    private Animation fab_open, fab_close;
    private boolean isFabOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);

        sharinginformation = findViewById(R.id.shareinformation);

        mSharerecyclerView = findViewById(R.id.sharing_recycler);

        fab_main_sharing = findViewById(R.id.fab_main_sharing);
        fab_sub1_sharing = findViewById(R.id.fab_sub2_sharing);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        mshareList = new ArrayList<>();
        mshareList.add(new SharingVO(true,1234,"미사용","자전거","X","X"));
        mshareList.add(new SharingVO(false,1234,"김연준","전동퀵보드","9월 14일","9월 21일"));
        mshareList.add(new SharingVO(false,1234,"최민철","자전거","9월 14일","9월 21일"));
        mshareList.add(new SharingVO(true,1234,"미사용","전동퀵보드","X","X"));
        mshareList.add(new SharingVO(false,1234,"박영철","자전거","9월 14일","9월 21일"));
        mshareList.add(new SharingVO(false,1234,"바비킴","전동퀵보드","9월 14일","9월 21일"));
        mshareList.add(new SharingVO(true,1234,"미사용","자전거","X","X"));

        shareAdapter = new SharingAdapter(mshareList);
        mSharerecyclerView.setAdapter(shareAdapter);
        mSharerecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        //대여 내역을 볼 수 있는 곳.
        sharinginformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(SharingActivity.this,SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                sweetAlertDialog.setTitleText("나의 대여정보")
                        .setContentText("번호:1234 \n 기간:2019.09.25~2019.09.28")
                        .setCancelText("닫기")
                        .showCancelButton(false).show();
            }
        });

        //플로팅 버튼 작동
        fab_main_sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
            }
        });
        fab_sub1_sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
                Intent intent = new Intent(SharingActivity.this, SharingNumberLockActivity.class);

                startActivity(intent);
            }
        });

    }

    private void toggleFab() {

        if (isFabOpen) {
            fab_main_sharing.setImageResource(R.drawable.ic_bluetooth_connected_black_24dp);
            fab_sub1_sharing.startAnimation(fab_close);
            fab_sub1_sharing.setClickable(false);
            isFabOpen = false;
        } else {
            fab_main_sharing.setImageResource(R.drawable.ic_close);
            fab_sub1_sharing.startAnimation(fab_open);
            fab_sub1_sharing.setClickable(true);
            isFabOpen = true;
        }

    }
}

package com.example.slrcoding.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.slrcoding.Adapter.SharingAdapter;
import com.example.slrcoding.R;
import com.example.slrcoding.VO.SharingVO;

import java.util.ArrayList;
import java.util.List;

public class SharingActivity extends AppCompatActivity {

    private List<SharingVO> mshareList;
    RecyclerView mSharerecyclerView;
    SharingAdapter shareAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);

        mSharerecyclerView = findViewById(R.id.sharing_recycler);

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
    }
}

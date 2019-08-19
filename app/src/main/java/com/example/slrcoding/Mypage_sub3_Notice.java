package com.example.slrcoding;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slrcoding.Adapter.NoticeRecyclerAdapter;
import com.example.slrcoding.VO.noticeVO;

import java.util.Arrays;
import java.util.List;

// 최민철(수정 : 19.08.16)
public class Mypage_sub3_Notice extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoticeRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_sub3__notice);

        init();

        getData();

    }

    private void init(){
        recyclerView = findViewById(R.id.mypage_notice_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new NoticeRecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }

    // 각 리스트에 데이터 추가
    private void getData(){
        List<String> listTitle = Arrays.asList(
                "김연준",
                "박영철",
                "이정찬",
                "최민철"
        );
        List<String> listContent = Arrays.asList(
                "2016",
                "2017",
                "2018",
                "2019"
        );
        List<Integer> listResId = Arrays.asList(
                R.drawable.btn_re,
                R.drawable.btn_re,
                R.drawable.btn_re,
                R.drawable.btn_re
        );

        // 각 List의 값들을 data객체에 세팅
        for(int i = 0; i<listTitle.size();i++){
            noticeVO data = new noticeVO();
            data.setTitle(listTitle.get(i));
            data.setContent(listContent.get(i));
            data.setResId(listResId.get(i));

            // 각 값이 들어간 data를 adapter에 추가
            adapter.addItem(data);
        }

        // adapter의 값이 변경된것을 알려줌
        adapter.notifyDataSetChanged();
    }
}

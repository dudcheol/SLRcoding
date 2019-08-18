package com.example.slrcoding.fragment;


import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.slrcoding.Adapter.MeetingAdapter;
import com.example.slrcoding.R;
import com.example.slrcoding.VO.Meeting_UserVO;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<Meeting_UserVO> Meeting_UserVO_List;
    private MeetingAdapter meetingAdapter;
    private int SPANCOUNT = 3;
    private Spinner spinner_sex, spinner_major, spinner_setting;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_message, container, false);

        setSpinnerItemClick(v);

        showRecyclerViewItem(v);

        return v;
    }

    public void showRecyclerViewItem(View v){
        mRecyclerView = v.findViewById(R.id.meeting_frag_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(v.getContext(),SPANCOUNT);
        mRecyclerView.setLayoutManager(layoutManager);

        Meeting_UserVO_List = new ArrayList<>();
        meetingAdapter = new MeetingAdapter(Meeting_UserVO_List,v.getContext());
        mRecyclerView.setAdapter(meetingAdapter);

        // 임시데이터 추가
        Meeting_UserVO_List.add(new Meeting_UserVO(1,"박보검",false,false));
        Meeting_UserVO_List.add(new Meeting_UserVO(2,"아이린",true,true));
        Meeting_UserVO_List.add(new Meeting_UserVO(1,"박보검",false,true));
        Meeting_UserVO_List.add(new Meeting_UserVO(1,"김덕배",true,true));
        Meeting_UserVO_List.add(new Meeting_UserVO(1,"박보검",true,false));
        Meeting_UserVO_List.add(new Meeting_UserVO(2,"아이린",true,true));
        Meeting_UserVO_List.add(new Meeting_UserVO(1,"아이린",false,true));
        Meeting_UserVO_List.add(new Meeting_UserVO(1,"송창식",false,true));
        Meeting_UserVO_List.add(new Meeting_UserVO(2,"박보검",false,false));
        Meeting_UserVO_List.add(new Meeting_UserVO(2,"아이린",false,true));
        Meeting_UserVO_List.add(new Meeting_UserVO(1,"송창식",false,false));
        Meeting_UserVO_List.add(new Meeting_UserVO(1,"박보검",false,false));
        Meeting_UserVO_List.add(new Meeting_UserVO(2,"박보검",true,false));
        Meeting_UserVO_List.add(new Meeting_UserVO(1,"아이린",true,true));
        Meeting_UserVO_List.add(new Meeting_UserVO(2,"박보검",true,true));


        // 아이템 크기 맞추기
        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = mRecyclerView.getWidth()  / SPANCOUNT;
                int height = mRecyclerView.getWidth()  / SPANCOUNT;
                meetingAdapter.setLength(width, height);
                mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // 리사이클러뷰가 그려진 다음에 setLength가 이루어 지므로
                meetingAdapter.notifyDataSetChanged();
            }
        });
    }

    public void setSpinnerItemClick(View v){
        spinner_sex = v.findViewById(R.id.spinner_sex);
        spinner_major = v.findViewById(R.id.spinner_major);
        //spinner_setting = v.findViewById(R.id.spinner_setting);


    }
}

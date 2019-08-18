package com.example.slrcoding.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_message, container, false);

        mRecyclerView = v.findViewById(R.id.meeting_frag_recyclerview);
        //mRecyclerView.setHasFixedSize(true);

        //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(v.getContext(),3);
        //mRecyclerView.setLayoutManager(layoutManager);

        Meeting_UserVO_List = new ArrayList<>();
        meetingAdapter = new MeetingAdapter(Meeting_UserVO_List,v.getContext());
        mRecyclerView.setAdapter(meetingAdapter);


        // 임시데이터 추가
        for(int i=0,j=0,k=0;i<50;i++){
            if(i%3==0){
                Meeting_UserVO_List.add(new Meeting_UserVO(2,"아이린"+k,true,true));
                k++;
            }else{
                Meeting_UserVO_List.add(new Meeting_UserVO(1,"박보검"+j,true,true));
                j++;
            }
        }
        meetingAdapter.notifyDataSetChanged();

        return v;
    }

}

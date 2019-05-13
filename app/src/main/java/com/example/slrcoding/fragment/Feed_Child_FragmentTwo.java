package com.example.slrcoding.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slrcoding.Board;
import com.example.slrcoding.FeedWriteActivity;
import com.example.slrcoding.MainAdapter;
import com.example.slrcoding.R;

import java.util.ArrayList;
import java.util.List;

//자식 프래그먼트 부모 프래그먼트인 FeedFragment에서 넘어온 것이다.
//이정찬2014154031
public class Feed_Child_FragmentTwo extends Fragment {

    private RecyclerView mMainRecyclerView;
    private MainAdapter mAdapter;
    private List<Board> mBoardList;
    public static final int REQUEST_CODE = 1000;
    public Feed_Child_FragmentTwo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_feed__child__fragment_two, container, false);
        mMainRecyclerView = rootView.findViewById(R.id.main_recycler_view2);

        rootView.findViewById(R.id.main_write_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeedWriteActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        //container.findViewById(R.id.main_write_button).setOnClickListener(this);
        //피드 글 적용시키기
        mBoardList = new ArrayList<>();
        mBoardList.add(new Board(null,"축구","축구 할사람 여러분",null,"android"));
        mBoardList.add(new Board(null,"축구","와~~토트넘 ",null,"server"));
        mBoardList.add(new Board(null,"축구","레알마드리드??",null,"java"));
        mBoardList.add(new Board(null,"축구","히딩크 돌아와라",null,"php"));
        mBoardList.add(new Board(null,"축구","박항서",null,"python"));
        mBoardList.add(new Board(null,"축구","손흥민 잘한다..",null,"server"));
        mBoardList.add(new Board(null,"축구","제 2의 박지성이냐",null,"java"));
        mBoardList.add(new Board(null,"축구","축구는 뭐다?",null,"php"));
        mBoardList.add(new Board(null,"축구","축구다!",null,"python"));
        mAdapter = new MainAdapter(mBoardList);
        mMainRecyclerView.setAdapter(mAdapter);
        return rootView;
    }


}

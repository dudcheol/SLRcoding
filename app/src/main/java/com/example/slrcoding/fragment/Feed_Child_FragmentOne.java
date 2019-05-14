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
//이정찬
public class Feed_Child_FragmentOne extends Fragment {

    private RecyclerView mMainRecyclerView;
    private MainAdapter mAdapter;
    private List<Board> mBoardList;
    public static final int REQUEST_CODE = 1000;

    public Feed_Child_FragmentOne() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_feed__child__fragment_one, container, false);
        mMainRecyclerView = rootView.findViewById(R.id.main_recycler_view);

        //container.findViewById(R.id.main_write_button).setOnClickListener(this);
        rootView.findViewById(R.id.main_write_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeedWriteActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        //피드 글 적용시키기
        mBoardList = new ArrayList<>();
        mBoardList.add(new Board(null,"시흥시모여라","반갑습니다 여러분","와~~~~~~~~~~","익명"));
        mBoardList.add(new Board(null,"시흥시모여라","Hello","와~~~~~~~~~~","익명"));
        mBoardList.add(new Board(null,"시흥시모여라","OK","와~~~~~~~~~~","익명"));
        mBoardList.add(new Board(null,"시흥시모여라","안녕하세요","와~~~~~~~~~~","익명"));
        mBoardList.add(new Board(null,"시흥시모여라","ㅋㅋㅋ","와~~~~~~~~~~","익명"));
        mBoardList.add(new Board(null,"시흥시모여라","Hello","와~~~~~~~~~~","익명"));
        mBoardList.add(new Board(null,"시흥시모여라","OK","와~~~~~~~~~~","익명"));
        mBoardList.add(new Board(null,"시흥시모여라","안녕하세요","와~~~~~~~~~~","익명"));
        mBoardList.add(new Board(null,"시흥시모여라","ㅋㅋㅋ","와~~~~~~~~~~","익명"));
        mAdapter = new MainAdapter(mBoardList);
        mMainRecyclerView.setAdapter(mAdapter);
        return rootView;
    }




}

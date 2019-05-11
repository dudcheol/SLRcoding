package com.example.slrcoding.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slrcoding.Board;
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
        //피드 글 적용시키기
        mBoardList = new ArrayList<>();
        mBoardList.add(new Board(null,"시흥시모여라","반갑습니다 여러분",null,"android"));
        mBoardList.add(new Board(null,"시흥시모여라","Hello",null,"server"));
        mBoardList.add(new Board(null,"시흥시모여라","OK",null,"java"));
        mBoardList.add(new Board(null,"시흥시모여라","안녕하세요",null,"php"));
        mBoardList.add(new Board(null,"시흥시모여라","ㅋㅋㅋ",null,"python"));
        mBoardList.add(new Board(null,"시흥시모여라","Hello",null,"server"));
        mBoardList.add(new Board(null,"시흥시모여라","OK",null,"java"));
        mBoardList.add(new Board(null,"시흥시모여라","안녕하세요",null,"php"));
        mBoardList.add(new Board(null,"시흥시모여라","ㅋㅋㅋ",null,"python"));
        mAdapter = new MainAdapter(mBoardList);
        mMainRecyclerView.setAdapter(mAdapter);
        return rootView;
    }




}

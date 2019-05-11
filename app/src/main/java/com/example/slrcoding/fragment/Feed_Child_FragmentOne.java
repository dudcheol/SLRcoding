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

        //ontainer.findViewById(R.id.main_write_button).setOnClickListener(this);

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

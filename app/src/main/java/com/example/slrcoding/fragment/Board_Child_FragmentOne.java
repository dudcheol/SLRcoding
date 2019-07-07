package com.example.slrcoding.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slrcoding.Board;
import com.example.slrcoding.MainAdapter;
import com.example.slrcoding.R;

import java.util.ArrayList;
import java.util.List;

// 자식 프래그먼트 부모 프래그먼트인 BoardFragment에서 넘어온 것이다.
// 김연준
public class Board_Child_FragmentOne extends Fragment {

    public RecyclerView mMainRecyclerView;
    private MainAdapter mAdapter;
    private List<Board> mBoardList;
    public static final int REQUEST_CODE = 1000;

    public Board_Child_FragmentOne() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_board__child__fragment_one, container, false);
        mMainRecyclerView = rootView.findViewById(R.id.main_recycler_view);


        //이제 파이베 연동 시 writeActivity에서 클릭 시 여기로 이동하는데 파이어베이스로 겟을 통해 각 적용시켜준다.
        //피드 글 적용시키기

        mBoardList = new ArrayList<>();
        mBoardList.add(new Board(null,"책1","화학의 이해(생능출판사)","새삥임 안 사면 후회함","mox","2019년 7월 7일"));
        mBoardList.add(new Board(null,"책2","물리의 이해(생능출판사)","새삥임 안 사면 후회함","fdas","2019년 7월 7일"));
        mBoardList.add(new Board(null,"책3","수학의 이해(생능출판사)","새삥임 안 사면 후회함","dfx","2019년 7월 7일"));
        mBoardList.add(new Board(null,"책4","사랑의 이해(생능출판사)","새삥임 안 사면 후회함","fdas","2019년 7월 7일"));
        mBoardList.add(new Board(null,"책5","시선의 이해(생능출판사)","새삥임 안 사면 후회함","ew","2019년 7월 7일"));
        mBoardList.add(new Board(null,"책6","연준의 이해(생능출판사)","새삥임 안 사면 후회함","xvc","2019년 7월 7일"));
        mBoardList.add(new Board(null,"책7","정찬의 이해(생능출판사)","새삥임 안 사면 후회함","vcx","2019년 7월 7일"));

        mAdapter = new MainAdapter(mBoardList);
        mMainRecyclerView.setAdapter(mAdapter);
        //container.findViewById(R.id.main_write_button).setOnClickListener(this);
        /*rootView.findViewById(R.id.main_write_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeedWriteActivity.class);
                //각 카테고리명 넘겨주기

                startActivityForResult(intent,REQUEST_CODE);
            }
        });*/
        return rootView;
    }




}

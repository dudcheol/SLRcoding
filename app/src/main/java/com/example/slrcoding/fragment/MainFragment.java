package com.example.slrcoding.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slrcoding.Board;
import com.example.slrcoding.famousAdapter;
import com.example.slrcoding.latestAdapter;
import com.example.slrcoding.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private static List<Board> mBoardList;
    private RecyclerView mRecyclerView_latest;
    private RecyclerView mRecyclerView_famous;
    private RecyclerView.Adapter mAdapter_latest;
    private RecyclerView.Adapter mAdapter_famous;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager2;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = (View)inflater.inflate(R.layout.fragment_main, container, false);

        //서버가 없으므로 임시로 추가한 데이터
        mBoardList = new ArrayList<>();
        mBoardList.add(new Board(null,"축구","축구 할사람 여러분","내용입니다","android","10분전"));
        mBoardList.add(new Board(null,"축구","와~~토트넘 ", "내용입니다","server","10분전"));
        mBoardList.add(new Board(null,"축구","레알마드리드??","내용입니다","java","10분전"));
        mBoardList.add(new Board(null,"축구","히딩크 돌아와라","내용입니다","php","10분전"));
        mBoardList.add(new Board(null,"축구","박항서","내용입니다","python","10분전"));
        mBoardList.add(new Board(null,"축구","손흥민 잘한다..","내용입니다","server","10분전"));
        mBoardList.add(new Board(null,"축구","제 2의 박지성이냐","내용입니다","java","10분전"));
        mBoardList.add(new Board(null,"축구","축구는 뭐다?","내용입니다","php","10분전"));
        mBoardList.add(new Board(null,"축구","축구다!","내용입니다","python","10분전"));

        // 리사이클러뷰 생성
        mRecyclerView_latest=(RecyclerView) v.findViewById(R.id.latest_content);
        mRecyclerView_famous=(RecyclerView) v.findViewById(R.id.famous_content);
        mRecyclerView_latest.setHasFixedSize(true);
        mRecyclerView_famous.setHasFixedSize(true);

        // 현재는 보여주기식으로 만들어서 인기글과 최신글이 동일한 내용 사용
        mAdapter_latest = new latestAdapter(mBoardList); // 최신글
        mAdapter_famous = new famousAdapter(mBoardList); // 인기글
        mAdapter_latest.notifyDataSetChanged();
        mAdapter_famous.notifyDataSetChanged();

        // 인기글
        mLayoutManager2=new LinearLayoutManager(v.getContext());
        ((LinearLayoutManager) mLayoutManager2).setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView_famous.setLayoutManager(mLayoutManager2);
        mRecyclerView_famous.setAdapter(mAdapter_famous);

        // 최신글
        mLayoutManager=new LinearLayoutManager(v.getContext());
        mRecyclerView_latest.setLayoutManager(mLayoutManager);
        mRecyclerView_latest.setAdapter(mAdapter_latest);


        // Inflate the layout for this fragment
        return v;
    }

}

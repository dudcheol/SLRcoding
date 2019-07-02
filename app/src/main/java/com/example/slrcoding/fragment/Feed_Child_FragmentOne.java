package com.example.slrcoding.fragment;

import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.slrcoding.Board;
import com.example.slrcoding.MainAdapter;
import com.example.slrcoding.R;

import java.util.ArrayList;
import java.util.List;
//자식 프래그먼트 부모 프래그먼트인 FeedFragment에서 넘어온 것이다.
//이정찬
public class Feed_Child_FragmentOne extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public RecyclerView mMainRecyclerView;
    private MainAdapter mAdapter;
    private List<Board> mBoardList;
    public static final int REQUEST_CODE = 1000;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public Feed_Child_FragmentOne() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_feed__child__fragment_one, container, false);
        mMainRecyclerView = rootView.findViewById(R.id.main_recycler_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swref);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //이제 파이베 연동 시 writeActivity에서 클릭 시 여기로 이동하는데 파이어베이스로 겟을 통해 각 적용시켜준다.
        //피드 글 적용시키기

        mBoardList = new ArrayList<>();
        mBoardList.add(new Board(null,"시흥시모여라","반갑습니다 여러분","와~~~~~~~~~~","익명","2019년 6월 25일"));
        mBoardList.add(new Board(null,"시흥시모여라","Hello","와~~~~~~~~~~","익명","2019년 6월 25일"));
        mBoardList.add(new Board(null,"시흥시모여라","OK","와~~~~~~~~~~","익명","2019년 6월 25일"));
        mBoardList.add(new Board(null,"시흥시모여라","안녕하세요","와~~~~~~~~~~","익명","2019년 6월 25일"));
        mBoardList.add(new Board(null,"시흥시모여라","ㅋㅋㅋ","와~~~~~~~~~~","익명","2019년 6월 25일"));
        mBoardList.add(new Board(null,"시흥시모여라","Hello","와~~~~~~~~~~","익명","2019년 6월 25일"));
        mBoardList.add(new Board(null,"시흥시모여라","OK","와~~~~~~~~~~","익명","2019년 6월 25일"));
        mBoardList.add(new Board(null,"시흥시모여라","안녕하세요","와~~~~~~~~~~","익명","2019년 6월 25일"));
        mBoardList.add(new Board(null,"시흥시모여라","ㅋㅋㅋ","와~~~~~~~~~~","익명","2019년 6월 25일"));
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


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "로딩 완료", Toast.LENGTH_SHORT).show();
            }
        },3000);
    }
}

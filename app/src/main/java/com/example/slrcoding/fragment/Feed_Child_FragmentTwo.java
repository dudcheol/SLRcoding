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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.slrcoding.Board;
import com.example.slrcoding.FeedWriteActivity;
import com.example.slrcoding.MainAdapter;
import com.example.slrcoding.R;

import java.util.ArrayList;
import java.util.List;

//자식 프래그먼트 부모 프래그먼트인 FeedFragment에서 넘어온 것이다.
//이정찬2014154031
public class Feed_Child_FragmentTwo extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public RecyclerView mMainRecyclerView;
    private MainAdapter mAdapter;
    private List<Board> mBoardList;
    public static final int REQUEST_CODE = 1000;
    private SwipeRefreshLayout mSwipeRefreshLayout2;
    public Feed_Child_FragmentTwo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_feed__child__fragment_two, container, false);
        mMainRecyclerView = rootView.findViewById(R.id.main_recycler_view2);
        mSwipeRefreshLayout2 = (SwipeRefreshLayout)rootView.findViewById(R.id.swref2);
        mSwipeRefreshLayout2.setOnRefreshListener(this);
        /*rootView.findViewById(R.id.main_write_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeedWriteActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });*/
        //container.findViewById(R.id.main_write_button).setOnClickListener(this);
        //피드 글 적용시키기
        //이제 파이베 연동 시 writeActivity에서 클릭 시 여기로 이동하는데 파이어베이스로 겟을 통해 각 적용시켜준다.
        mBoardList = new ArrayList<>();
        mBoardList.add(new Board(null,"축구","축구 할사람 여러분","와~~~~~~~~~~","익명","2019년 6월 25일"));
        mBoardList.add(new Board(null,"축구","와~~토트넘 ","와~~~~~~~~~~","익명","2019년 6월 25일"));
        mBoardList.add(new Board(null,"축구","레알마드리드??","와~~~~~~~~~~","익명","2019년 6월 25일"));
        mBoardList.add(new Board(null,"축구","히딩크 돌아와라","와~~~~~~~~~~","익명","2019년 6월 25일"));
        mBoardList.add(new Board(null,"축구","박항서","와~~~~~~~~~~","익명","2019년 6월 25일"));
        mBoardList.add(new Board(null,"축구","손흥민 잘한다..","와~~~~~~~~~~","익명","2019년 6월 25일"));
        mBoardList.add(new Board(null,"축구","제 2의 박지성이냐","와~~~~~~~~~~","익명","2019년 6월 25일"));
        mBoardList.add(new Board(null,"축구","축구는 뭐다?","와~~~~~~~~~~","익명","2019년 6월 25일"));
        mBoardList.add(new Board(null,"축구","축구다!","와~~~~~~~~~~","익명","2019년 6월 25일"));
        mAdapter = new MainAdapter(mBoardList);
        mMainRecyclerView.setAdapter(mAdapter);
        return rootView;
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout2.setRefreshing(false);
                Toast.makeText(getActivity(), "로딩 완료", Toast.LENGTH_SHORT).show();
            }
        },3000);
    }
}

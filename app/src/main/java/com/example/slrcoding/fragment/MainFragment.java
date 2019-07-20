package com.example.slrcoding.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.slrcoding.Adapter.mainListAdapter;
import com.example.slrcoding.Board;
import com.example.slrcoding.famousAdapter;
import com.example.slrcoding.latestAdapter;
import com.example.slrcoding.R;
import com.example.slrcoding.util.MainListViewType;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private String TAG = "MainFragment_TEST";
    private static List<Board> mBoardList;
    FirebaseFirestore firestore;

    private RecyclerView mRecyclerView;

    //리스트뷰 아이템별 뷰타입을 정해주는 리스트
    private List<MainListViewType> mainListViewTypeList;

    /*private RecyclerView mRecyclerView_latest;
    private RecyclerView mRecyclerView_famous;
    private RecyclerView.Adapter mAdapter_latest;
    private RecyclerView.Adapter mAdapter_famous;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager2;*/

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = (View)inflater.inflate(R.layout.fragment_main, container, false);

        // 파이어베이스 설정작업
        firestore = FirebaseFirestore.getInstance();

        //테스트데이터
        mBoardList = new ArrayList<>();

        mBoardList.add(new Board(null,"축구","축구 할사람 여러분","내용입니다","android","10분전",0L,"10분전",0L));
        mBoardList.add(new Board(null,"축구","와~~토트넘 ", "내용입니다","server","10분전",0L,"10분전",0L));
        mBoardList.add(new Board(null,"축구","레알마드리드??","내용입니다","java","10분전",0L,"10분전",0L));
        mBoardList.add(new Board(null,"축구","히딩크 돌아와라","내용입니다","php","10분전",0L,"10분전",0L));
        mBoardList.add(new Board(null,"축구","박항서","내용입니다","python","10분전",0L,"10분전",0L));
        /*mBoardList.add(new Board(null,"축구","손흥민 잘한다..","내용입니다","server","10분전",0L,"10분전",0L));
        mBoardList.add(new Board(null,"축구","제 2의 박지성이냐","내용입니다","java","10분전",0L,"10분전",0L));
        mBoardList.add(new Board(null,"축구","축구는 뭐다?","내용입니다","php","10분전",0L,"10분전",0L));
        mBoardList.add(new Board(null,"축구","축구다!","내용입니다","python","10분전",0L,"10분전",0L));*/


        mRecyclerView = (RecyclerView)v.findViewById(R.id.main_recyclerView_for_mainFrag);
        mRecyclerView.setHasFixedSize(true);

        // mainListViewTypeList를 먼저 만들어준다
        // 뷰 타입 별로 다른 뷰 제공
        // type : flag : subject
        // A : 0 : 인기글
        // B : 1 : test로 그냥 아무거나 넣어봄
        mainListViewTypeList = new ArrayList<>();
        mainListViewTypeList.add(new MainListViewType(0));
        mainListViewTypeList.add(new MainListViewType(1));
        mainListViewTypeList.add(new MainListViewType(0));
        mainListViewTypeList.add(new MainListViewType(1));
        mainListViewTypeList.add(new MainListViewType(0));
        mainListViewTypeList.add(new MainListViewType(0));
        mainListViewTypeList.add(new MainListViewType(1));
        mainListViewTypeList.add(new MainListViewType(0));
        mainListViewTypeList.add(new MainListViewType(1));
        mainListViewTypeList.add(new MainListViewType(0));

        //완료 -- 파이어베이스에서 피드 정보 받아오기
        firestore
                .collection("기숙사와 밥")
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.v(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.v(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        //Todo 1-- 파이어베이스에서 모든 컬렉션에서 가장 최신글 받아오는것 구현
        //Todo 2-- 파이어베이스에서 인기글 받아오는 것 구현 (일단 그냥 좋아요 가장 많은 것부터 가져온다)

        //Todo 3-- 파이어베이스에서 가져온 정보 VO에 저장시킨다음에 어댑터에 넣어줘서 메인으로 만들기

        //Todo 4-- 타이틀봐 활용해서 꾸며보기



        //Todo -- mainListAdapter를 생성할때 생성자로 리사이클러뷰에 담고싶은 정보들을 한꺼번에 보내는 식으로 해야하나..??
        // 내 생각엔 notifyDataSetChanged를 좀 알아봐서 쓰면 될거같다.. 참고:https://alpoxdev.github.io/2018/07/31/Android/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C2/
        // 여기 들어가면 리사이클러뷰 아이템의 특정 Position 위치만 바뀌었을 경우 거기만  동작?하게 할 수 있는듯
        // mainListAdapter의 생성자 안에 들어가는 모든 데이터들을 담고있는 객체 하나 만들어서 처음띄울땐 전부 다 받아오고
        // 실시간 데이터만 getter setter 이용해서 그것만 어댑터에 전달해서 바꾸는식으로 해야하나...?? 고민해봐야할듯..
        RecyclerView.Adapter mainListAdapter = new mainListAdapter(mainListViewTypeList,mBoardList,null);
        mainListAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mainListAdapter);


        //서버가 없으므로 임시로 추가한 데이터
        /*


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
        mRecyclerView_latest.setAdapter(mAdapter_latest);*/


        // Inflate the layout for this fragment
        return v;
    }

}

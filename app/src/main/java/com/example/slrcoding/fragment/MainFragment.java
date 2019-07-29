package com.example.slrcoding.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.slrcoding.Adapter.MainListAdapter;
import com.example.slrcoding.Board;
import com.example.slrcoding.R;
import com.example.slrcoding.util.MainListViewType;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

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

    // 데이터 받은 거 확인하는 플래그
    private int DATA_OK_FLAG=0;

    RecyclerView.Adapter mainListAdapter;
    Board boardDTO;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = (View)inflater.inflate(R.layout.fragment_main, container, false);

        // 파이어베이스 설정작업
        firestore = FirebaseFirestore.getInstance();


        mRecyclerView = (RecyclerView)v.findViewById(R.id.main_recyclerView_for_mainFrag);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mainListViewTypeList=new ArrayList<>();
        RecyclerView.Adapter mainListAdapter = new MainListAdapter(mainListViewTypeList);
        mRecyclerView.setAdapter(mainListAdapter);


        // mainListViewTypeList를 먼저 만들어준다
        // 뷰 타입 별로 다른 뷰 제공
        // type : flag : subject
        // A : 0 : 게시글종류
        // B : 1 : test로 그냥 아무거나 넣어봄
        mainListViewTypeList.add(new MainListViewType(0,"기숙사와 밥"));
        mainListViewTypeList.add(new MainListViewType(0,"스포츠와 게임"));

        // 기숙사와 밥 데이터 로드
        firestore
                .collection("기숙사와 밥")
                .orderBy("regDate",Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        List<Board> boards = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.v(TAG, document.getId() + " => " + document.getData());
                                boardDTO = document.toObject(Board.class);
                                boards.add(boardDTO);
                            }
                            mainListViewTypeList.get(0).setBoards(boards);
                            mainListAdapter.notifyDataSetChanged();
                        } else {
                            Log.v(TAG, "Error getting documents: ", task.getException());
                        }

                        //Todo -- mainListAdapter를 생성할때 생성자로 리사이클러뷰에 담고싶은 정보들을 한꺼번에 보내는 식으로 해야하나..??
                        // 내 생각엔 notifyDataSetChanged를 좀 알아봐서 쓰면 될거같다.. 참고:https://alpoxdev.github.io/2018/07/31/Android/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C2/
                        // 여기 들어가면 리사이클러뷰 아이템의 특정 Position 위치만 바뀌었을 경우 거기만  동작?하게 할 수 있는듯
                        // mainListAdapter의 생성자 안에 들어가는 모든 데이터들을 담고있는 객체 하나 만들어서 처음띄울땐 전부 다 받아오고
                        // 실시간 데이터만 getter setter 이용해서 그것만 어댑터에 전달해서 바꾸는식으로 해야하나...?? 고민해봐야할듯..
                        //MainListAdapter = new MainListAdapter(mainListViewTypeList,boards,null);
                        /*MainListAdapter.notifyDataSetChanged();
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());

                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setAdapter(MainListAdapter);*/
                    }
                })
                .addOnFailureListener(task -> {
                    Toast.makeText(getContext(), "데이터를 받아오는데 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
                });

        // 스포츠와 게임 데이터 로드
        firestore
                .collection("스포츠와 게임")
                .orderBy("regDate",Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        List<Board> boards = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.v(TAG, document.getId() + " => " + document.getData());
                                boardDTO = document.toObject(Board.class);
                                boards.add(boardDTO);
                            }
                            mainListViewTypeList.get(1).setBoards(boards);
                            mainListAdapter.notifyDataSetChanged();
                        } else {
                            Log.v(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(task -> {
                    Toast.makeText(getContext(), "데이터를 받아오는데 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
                });




        //Todo 1-- 파이어베이스에서 모든 컬렉션에서 가장 최신글 받아오는것 구현

        //Todo 2-- 파이어베이스에서 인기글 받아오는 것 구현 (일단 그냥 좋아요 가장 많은 것부터 가져온다)

        //Todo 3-- 파이어베이스에서 가져온 정보 VO에 저장시킨다음에 어댑터에 넣어줘서 메인으로 만들기 << 일단하긴했음
        // DTO만 잘 정의해주면 여러번 써먹을 수 있음

        //Todo 4-- 타이틀바 활용해서 꾸며보기


        return v;
    }

}

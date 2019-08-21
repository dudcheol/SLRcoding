package com.example.slrcoding.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.slrcoding.Adapter.MainListAdapter;
import com.example.slrcoding.Board;
import com.example.slrcoding.R;
import com.example.slrcoding.VO.Main_JunggoVO;
import com.example.slrcoding.util.MainListViewType;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

import es.dmoral.toasty.Toasty;

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

    RecyclerView.Adapter mainListAdapter;
    Board boardDTO;
    ProgressBar board_progressbar, myinfo_progressbar;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 레이아웃 제어를 위한 인플레이터
        View v = (View)inflater.inflate(R.layout.fragment_main, container, false);

        // 실행됐을때 가장 스크롤포지션이 가장 위에 있도록 하기 위한 핸들러
        setScrollPosition_TOP();

        // 파이어베이스를 설정하고
        // 리사이클러뷰, 아이템 리스트를 생성해놓는다.
        initSetting(v);

        // 생성하고싶은 카테고리를 추가한다.
        addCategoryItem();

        // 중고장터 데이터 추가 (임시데이터)
        addJunggoData();

        // 게시판 데이터 추가
        addBoardData();


        //Todo 1-- 파이어베이스에서 모든 컬렉션에서 가장 최신글 받아오는것 구현

        //Todo 2-- 파이어베이스에서 인기글 받아오는 것 구현 (일단 그냥 좋아요 가장 많은 것부터 가져온다)

        //Todo 4-- 타이틀바 활용해서 꾸며보기

        return v;
    }

    void initSetting(View v){
        firestore = FirebaseFirestore.getInstance();

        mRecyclerView = (RecyclerView)v.findViewById(R.id.main_recyclerView_for_mainFrag);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mainListViewTypeList=new ArrayList<>();

        MainFragment fragment=new MainFragment();
        mainListAdapter = new MainListAdapter(mainListViewTypeList,v.getContext(),getActivity());
        mRecyclerView.setAdapter(mainListAdapter);
    }

    void addCategoryItem(){
        // mainListViewTypeList를 먼저 만들어준다
        // 뷰 타입 별로 다른 뷰 제공
        // type : flag : subject
        // A : 0 : 게시글종류
        // B : 1 : 내정보
        // C : 2 : 중고장터
        mainListViewTypeList.add(new MainListViewType(1,"내정보"));
        mainListViewTypeList.add(new MainListViewType(0,"기숙사와 밥"));
        mainListViewTypeList.add(new MainListViewType(0,"스포츠와 게임"));
        mainListViewTypeList.add(new MainListViewType(2,"중고장터"));
    }

    void addJunggoData(){
        // 중고장터 이미지 임시데이터
        List<Main_JunggoVO> test = new ArrayList<>();
        test.add(new Main_JunggoVO(1));
        test.add(new Main_JunggoVO(2));
        test.add(new Main_JunggoVO(3));
        test.add(new Main_JunggoVO(4));
        test.add(new Main_JunggoVO(5));
        test.add(new Main_JunggoVO(6));
        mainListViewTypeList.get(3).setJunggos(test);
        mainListAdapter.notifyDataSetChanged();
    }

    void setScrollPosition_TOP(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.scrollToPosition(0);
            }
        }, 500);
    }

    void addBoardData(){
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
                            mainListViewTypeList.get(1).setBoards(boards);
                            mainListAdapter.notifyDataSetChanged();
                        } else {
                            Log.v(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(task -> {
                    Toasty.error(Objects.requireNonNull(getContext()),"데이터를 받아오는데 문제가 발생했습니다.",Toasty.LENGTH_SHORT).show();
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
                            mainListViewTypeList.get(2).setBoards(boards);
                            mainListAdapter.notifyDataSetChanged();
                        } else {
                            Log.v(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(task -> {
                    Toasty.error(Objects.requireNonNull(getContext()),"데이터를 받아오는데 문제가 발생했습니다.",Toasty.LENGTH_SHORT).show();
                });
    }

    void removeProgressBar(ProgressBar progressBar){
        progressBar.setVisibility(View.GONE);
    }
}

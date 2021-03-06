package com.example.slrcoding.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.slrcoding.Board2;
import com.example.slrcoding.BoardAdapter;
import com.example.slrcoding.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

// 자식 프래그먼트 부모 프래그먼트인 BoardFragment에서 넘어온 것이다.
// 김연준
public class Board_Child_FragmentTwo extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String cate="의류";
    public RecyclerView board_mMainRecyclerView;
    private BoardAdapter board_mAdapter;
    private List<Board2> board_mBoardList2 = null;
    private Board2 data2;

    BoardFragment boardFragment;
    public static final int REQUEST_CODE = 1000;
    private SwipeRefreshLayout mSwipeRefreshLayout2;

    public Board_Child_FragmentTwo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_board__child__fragment_two, container, false);
        board_mMainRecyclerView = rootView.findViewById(R.id.board_recycler_view2);
        mSwipeRefreshLayout2 = (SwipeRefreshLayout)rootView.findViewById(R.id.board_swref2);
        mSwipeRefreshLayout2.setOnRefreshListener(this);

        board_mBoardList2=new ArrayList<>();
        db.collection(cate).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){
                    switch(dc.getType()){
                        case ADDED:
                            String id = (String) dc.getDocument().getData().get("id");
                            String title = (String)dc.getDocument().getData().get("title");
                            String contents=(String)dc.getDocument().getData().get("contents");
                            String name = (String)dc.getDocument().getData().get("name");
                            String userEmail = (String) dc.getDocument().getData().get("userEmail");
                            String category = (String)dc.getDocument().getData().get("category");
                            String regDate = (String)dc.getDocument().getData().get("regDate");
                            Calendar calendar = new GregorianCalendar(Locale.KOREA);
                            //현재 년도일 경우 없애서 보여주고 작년 일 경우 년도 표시하기
                            int nYear = calendar.get(Calendar.YEAR);
                            String year = Integer.toString(nYear);
                            String regYear = regDate.substring(0,4);
                            String regDateModify = null;
                            //현재 년도에 등록했을 때는 월/일 시간 만 보여주기
                            if(year.equals(regYear)){
                                regDateModify = regDate.substring(6,17);
                            }else{ //현재 년도가 아닐 경우 즉 작년에 쓴글이라면 년도까지 표현하기!!
                                regDateModify = regDate.substring(0,17);
                            }
                            Long replyCnt = (Long)dc.getDocument().getData().get("replyCnt");
                            Long likeCnt = (Long)dc.getDocument().getData().get("likeCnt");
                            String image = (String) dc.getDocument().getData().get("image");


                            data2 = new Board2(id, category, title, contents, name, userEmail, regDate, replyCnt,regDateModify,likeCnt, image);
                            board_mBoardList2.add(data2);
                            Log.i("dd","ADDED");

                            break;

                        case MODIFIED:
                            Long likeCnt1 = (Long)dc.getDocument().getData().get("likeCnt");
                            Long replyCnt1 = (Long)dc.getDocument().getData().get("replyCnt");
                            String id1 = (String)dc.getDocument().getData().get("id");
                            String title1 = (String)dc.getDocument().getData().get("title");
                            String contents1=(String)dc.getDocument().getData().get("contents");
                            String name1 = (String) dc.getDocument().getData().get("name");
                            String userEmail1 = (String)dc.getDocument().getData().get("userEmail");
                            String category1 = (String)dc.getDocument().getData().get("category");
                            String regDate1 = (String)dc.getDocument().getData().get("regDate");

                            String image1 = (String) dc.getDocument().getData().get("image1");

                            Calendar calendar1 = new GregorianCalendar(Locale.KOREA);
                            //현재 년도일 경우 없애서 보여주고 작년 일 경우 년도 표시하기
                            int nYear1 = calendar1.get(Calendar.YEAR);
                            String year1 = Integer.toString(nYear1);
                            String regYear1 = regDate1.substring(0,4);
                            String regDateModify1 = null;
                            //현재 년도에 등록했을 때는 월/일 시간 만 보여주기
                            if(year1.equals(regYear1)){
                                regDateModify1 = regDate1.substring(6,17);
                            }else{ //현재 년도가 아닐 경우 즉 작년에 쓴글이라면 년도까지 표현하기!!
                                regDateModify1 = regDate1.substring(0,17);
                            }
                            //수정 된 게시글에 대한 정보를 담은 Board를 백업하여 이를 가지고 리스트에 set으로 수정함
                            Board2 datacopy = new Board2(id1,category1,title1,contents1, name1, userEmail1,regDate1,replyCnt1,regDateModify1,likeCnt1, image1);

                            //리스트에서 해당 수정된 객체를 찾아서 그 리스트에서 수정
                            Board2 temp = new Board2();
                            temp.setId(id1);
                            int index=0;
                            for(int idx=0; idx<board_mBoardList2.size(); idx++){
                                if(compareToId(temp,board_mBoardList2.get(idx))){
                                    index=idx;
                                    Log.i("dd","index: "+index);
                                    board_mBoardList2.set(index,datacopy);
                                }
                            }
                            break;

                        case REMOVED:
                            break;
                    }

                }
                Log.i("boardList: ","boardList2: "+board_mBoardList2);
                Collections.sort(board_mBoardList2,new Board_Child_FragmentTwo.CompareRegDateDesc());
                board_mAdapter = new BoardAdapter(board_mBoardList2);

                //  board_mAdapter.notifyDataSetChanged();
                board_mMainRecyclerView.setAdapter(board_mAdapter);
            }
        });
        boardFragment = new BoardFragment();
        Log.i("searchVIew","searchView"+boardFragment.mSearchView);
        boardFragment.mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                board_mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return rootView;
    }

    //ID 비교 전용 메소드
    private boolean compareToId(Board2 b1, Board2 b2) {
        //대소문자 구분 하는 상태
        return b1.getId().equals(b2.getId());

        //대소문자 구분 하지 않는 상태
        //return o1.getId().toLowerCase().equals(o2.getId().toLowerCase());
    }
    static class CompareRegDateDesc implements Comparator<Board2> {

        @Override
        public int compare(Board2 b1, Board2 b2) {
            return b2.getRegDate().compareTo(b1.getRegDate());
        }
    }
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout2.setRefreshing(false);
                Toast.makeText(getActivity(), "로딩 완료", Toast.LENGTH_SHORT).show();
            }
        },1500);
    }
}

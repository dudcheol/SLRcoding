package com.example.slrcoding.fragment;

import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

//자식 프래그먼트 부모 프래그먼트인 FeedFragment에서 넘어온 것이다.
//이정찬2014154031
public class Feed_Child_FragmentTwo extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String cate="soccer";
    public RecyclerView mMainRecyclerView;
    private MainAdapter mAdapter;
    private List<Board> mBoardList2 = null;
    private Board data2;
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
        mBoardList2=new ArrayList<>();
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
                            data2 = new Board(id,category,title,contents,name,regDate,replyCnt,regDateModify,likeCnt);



                            mBoardList2.add(data2);
                            Log.i("dd","ADDED");

                            break;
                        case MODIFIED:
                            Long replyCnt1 = (Long)dc.getDocument().getData().get("replyCnt");
                            Long likeCnt1 = (Long)dc.getDocument().getData().get("likeCnt");
                            String id1 = (String)dc.getDocument().getData().get("id");
                            String title1 = (String)dc.getDocument().getData().get("title");
                            String contents1=(String)dc.getDocument().getData().get("contents");
                            String name1 = (String)dc.getDocument().getData().get("name");
                            String category1 = (String)dc.getDocument().getData().get("category");
                            String regDate1 = (String)dc.getDocument().getData().get("regDate");
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

                            Board datacopy = new Board(id1,category1,title1,contents1,name1,regDate1,replyCnt1,regDateModify1,likeCnt1);



                            //리스트에서 해당 수정된 객체를 찾아서 그 리스트에서 수정
                            Board temp = new Board();
                            temp.setId(id1);
                            int index=0;
                            for(int idx=0; idx<mBoardList2.size(); idx++){
                                if(compareToId(temp,mBoardList2.get(idx))){
                                    index=idx;
                                    Log.i("dd","index: "+index);
                                    mBoardList2.set(index,datacopy);
                                }
                            }

                            break;
                        case REMOVED:
                            break;
                    }

                }
                Log.i("boardList: ","boardList2: "+mBoardList2);
                Collections.sort(mBoardList2,new CompareRegDateDesc());
                mAdapter = new MainAdapter(mBoardList2);

                //  mAdapter.notifyDataSetChanged();
                mMainRecyclerView.setAdapter(mAdapter);


            }
        });
        return rootView;
    }

    //ID 비교 전용 메소드
    private boolean compareToId(Board o1, Board o2) {
        //대소문자 구분 하는 상태
        return o1.getId().equals(o2.getId());

        //대소문자 구분 하지 않는 상태
        //return o1.getId().toLowerCase().equals(o2.getId().toLowerCase());
    }
    static class CompareRegDateDesc implements Comparator<Board> {

        @Override
        public int compare(Board o1, Board o2) {
            // TODO Auto-generated method stub
            return o2.getRegDate().compareTo(o1.getRegDate());
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
        },3000);
    }
}

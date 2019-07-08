package com.example.slrcoding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nullable;

public class BoardDetailActivity extends AppCompatActivity {
    //좋아요 댓글 수 데이터 교환 가능해야함
    private TextView titleTextView;
    private TextView contentTextView;
    private TextView categoryTextView;
    private TextView nameTextView;
    private TextView dateTextView;
    private LikeButton likelyButton;

    private RecyclerView mReplyRecyclerView;
    private List<FeedReplyVO> feedReplyVOList;
    private ReplyAdapter replyAdapter;
    private ImageButton replyButton;

    private String category;
    private String category2;
    private String idfrom;
    //private String id ;
    private String title;
    private String contents;
    private String name ;
    private String regDate;
    String regDateModify = null; //수정된 날짜
    private Long replyCnt;

    private TextView replyName;//닉네임
    private TextView replyContent;//댓글 내용
    private TextView replyRegDate; //댓글 작성 일자

    private TextView replyCntView; //댓글 수
    private EditText replyEditTextView;
    private String replyId;
    private String time1;
    Toolbar toolbar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);

        titleTextView = findViewById(R.id.bard_title);
        contentTextView = findViewById(R.id.board_context);
        categoryTextView =findViewById(R.id.board_detail_category);
        nameTextView = findViewById(R.id.board_fname);
        dateTextView = findViewById(R.id.board_date);
        likelyButton = findViewById(R.id.board_like_button);
        replyButton = findViewById(R.id.board_reply_submit);
        replyCntView = findViewById(R.id.board_reply_cnt); // 게시판 게시글 댓글 수

        replyName = findViewById(R.id.board_reply_id);
        replyContent=findViewById(R.id.board_reply_content);
        replyRegDate = findViewById(R.id.board_reply_date);

        replyEditTextView = findViewById(R.id.board_reply_edit);
        toolbar = (Toolbar)findViewById(R.id.board_toolbar3);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //넘어온 인텐트!!
        //피드에서 넘어온 데이터(카테고리 명,제목,내용,날짜,좋아요수,댓글수 등)
       /* Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String category = intent.getStringExtra("category");
        String date = intent.getStringExtra("date");*/
        category = getIntent().getStringExtra("category");
        idfrom = getIntent().getStringExtra("id");
        Log.i("idfrom: ","idfRom"+idfrom);
        DocumentReference doc =db.collection(category).document(idfrom);
        doc.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            title = (String) documentSnapshot.getData().get("title");
                            contents = (String)documentSnapshot.getData().get("contents");
                            category2 = (String)documentSnapshot.getData().get("category");
                            name = (String)documentSnapshot.getData().get("name");
                            regDate = (String)documentSnapshot.getData().get("regDate");
                            Calendar calendar = new GregorianCalendar(Locale.KOREA);
                            //현재 년도일 경우 없애서 보여주고 작년 일 경우 년도 표시하기
                            int nYear = calendar.get(Calendar.YEAR);
                            String year = Integer.toString(nYear);
                            String regYear = regDate.substring(0,4);

                            //현재 년도에 등록했을 때는 월/일 시간 만 보여주기
                            if(year.equals(regYear)){
                                regDateModify = regDate.substring(6,17);
                            }else{ //현재 년도가 아닐 경우 즉 작년에 쓴글이라면 년도까지 표현하기!!
                                regDateModify = regDate.substring(0,17);
                            }
                            replyCnt =(Long)documentSnapshot.getData().get("replyCnt");
                            Log.i("title","title: "+title);
                            Log.i("title","contents: "+contents);
                            Log.i("title","category2: "+category2);
                            Log.i("title","name: "+name);
                            Log.i("title","replyCnt: "+replyCnt);
                        }else{
                            Log.i("error","get Failed: "+task.getException());
                        }
                        titleTextView.setText(title);
                        contentTextView.setText(contents);
                        categoryTextView.setText(category2);
                        nameTextView.setText(name+"1");
                        dateTextView.setText(regDateModify);
                        replyCntView.setText(String.valueOf(replyCnt));
                    }

                });


        //댓글 등록시 실시간 불러오기로 받아올 곳
        mReplyRecyclerView = findViewById(R.id.feed_reply_recycler);
        feedReplyVOList = new ArrayList<>();
        Query query = db.collection(category).document(idfrom).collection("reply");
        ListenerRegistration registration = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){

                    String replyId = (String) dc.getDocument().getData().get("id");
                    String replyContent=(String)dc.getDocument().getData().get("replyContent");
                    String replyName = (String)dc.getDocument().getData().get("replyName");
                    String replyDate = (String)dc.getDocument().getData().get("replyDate");
                    Calendar calendar = new GregorianCalendar(Locale.KOREA);
                    //현재 년도일 경우 없애서 보여주고 작년 일 경우 년도 표시하기
                    int nYear = calendar.get(Calendar.YEAR);
                    String year = Integer.toString(nYear);
                    String regYear = replyDate.substring(0,4);
                    String replyDateModify = null;
                    //현재 년도에 등록했을 때는 월/일 시간 만 보여주기
                    if(year.equals(regYear)){
                        replyDateModify = replyDate.substring(6,17);
                    }else{ //현재 년도가 아닐 경우 즉 작년에 쓴글이라면 년도까지 표현하기!!
                        replyDateModify = replyDate.substring(0,17);
                    }
                    Log.i("Reply:","replyId: "+replyId);
                    Log.i("Reply:","replyContent: "+replyContent);
                    Log.i("Reply:","replyName: "+replyName);
                    Log.i("Reply:","replyDate: "+replyDate);
                    FeedReplyVO replyVO = new FeedReplyVO(replyId,replyContent,replyName,replyDateModify);
                    //datacopy = data;

                    feedReplyVOList.add(replyVO);


                }

                // Log.i("for","통과2");
                Collections.sort(feedReplyVOList,new CompareRegDateDesc());
                replyAdapter = new ReplyAdapter(feedReplyVOList);
                replyAdapter.notifyDataSetChanged();
                mReplyRecyclerView.setAdapter(replyAdapter);
                mReplyRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            }
        });
        //댓글 등록 시 댓글 수 실시간으로 보여주기
        final DocumentReference docRef = db.collection(category).document(idfrom);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    //Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    //Log.d(TAG, "Current data: " + snapshot.getData());
                    Long replyCnt = (Long)snapshot.getData().get("replyCnt");
                    replyCntView.setText(String.valueOf(replyCnt));

                } else {
                    //Log.d(TAG, "Current data: null");
                }
            }

        });
        //mReplyRecyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        setClickEvent();
        setReplySubmit();
    }
    //댓글 등록 시 파베에 넣기
    //댓글 수도 업데이트하기..
    private void setReplySubmit(){
        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy년 MM/dd HH:mm:ss");
                Date time = new Date();
                time1 = format1.format(time);


                replyId = db.collection(category).document(idfrom).collection("reply").document().getId();
                Map<String,Object> post = new HashMap<>();
                post.put("id",replyId);
                post.put("replyDate",time1);
                post.put("replyContent",replyEditTextView.getText().toString());
                post.put("replyName","익명");


                db.collection(category)
                        .document(idfrom).collection("reply").document(replyId).set(post)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(BoardDetailActivity.this, "업로드 성공!!", Toast.LENGTH_SHORT).show();
                                // finish();
                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Log.w(TAG, "Error adding document", e);
                                Toast.makeText(BoardDetailActivity.this, "업로드 실패!!", Toast.LENGTH_SHORT).show();
                            }
                        });

                db.collection(category).document(idfrom)
                        .update("replyCnt",replyCnt+1L).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(BoardDetailActivity.this, "댓글 수 증가!!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BoardDetailActivity.this, "댓글 수 증가 실패!!", Toast.LENGTH_SHORT).show();

                    }
                });
            }

        });
    }

    private void setClickEvent(){
        likelyButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Context context = likeButton.getContext();

                Toast.makeText(context, "좋아요 버튼 클릭!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Context context = likeButton.getContext();

                Toast.makeText(context, "좋아요 버튼 취소!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //추가된 소스, ToolBar에 menu.xml을 인플레이트함
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.feeddetailmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_delete:
                db.collection(category).document(idfrom)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                Toast.makeText(getApplicationContext(), "피드 삭제성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                intent.putExtra("flag",1);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //Log.w(TAG, "Error deleting document", e);
                            }
                        });

        }
        return super.onOptionsItemSelected(item);
    }

    static class CompareRegDateDesc implements Comparator<FeedReplyVO> {

        @Override
        public int compare(FeedReplyVO o1, FeedReplyVO o2) {
            // TODO Auto-generated method stub
            return o2.getReplyDate().compareTo(o1.getReplyDate());
        }
    }
}

package com.example.slrcoding;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class FeedWriteActivity extends AppCompatActivity {

    Toolbar toolbar;
    private EditText mWriteTitleText;
    private EditText mWriteContentsText;
    private Button bt;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //2019-08-02 로그인 정보 가져오기 위해 선언(이정찬)
    private FirebaseAuth firebaseAuth;           // 파이어베이스 인증 객체 생성
    private FirebaseUser currentUser;

    private String category=null;
    private int code =0;
    private String id;
    private  String time1;
    private Long replyCnt;
    private Long likeCnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_write);
        toolbar = (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mWriteContentsText = (EditText)findViewById(R.id.feed_memo_edit);
        mWriteTitleText = (EditText)findViewById(R.id.feed_title_editText);
        Intent intent = getIntent();
        code = intent.getExtras().getInt("code");
        if(code == 1){
            category = "기숙사와 밥";
        }else if(code == 2){
            category = "스포츠와 게임";
        }
        //Todo: 여기서 로그인 유저를 가져온다. 아이디를 담아둔다.
        //2019-08-02 현재 로그인 정보 가져오기 추가 (이정찬)
        firebaseAuth = FirebaseAuth.getInstance();          // 파이어베이스 인증 객체 선언
        currentUser = firebaseAuth.getCurrentUser();        // 현재 로그인한 사용자 가져오기
        //Toast.makeText(this, "userEmail:"+userEmail, Toast.LENGTH_SHORT).show();

    }
    //추가된 소스, ToolBar에 menu.xml을 인플레이트함
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.feedwritemenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.feed_done:
                Toast.makeText(getApplicationContext(), "등록 버튼 클릭됨", Toast.LENGTH_LONG).show();
                //예외처리
                if(mWriteTitleText.getText().toString().equals("")){
                    Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(mWriteContentsText.getText().toString().equals("")){
                    Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return false;
                }

                //여기서 파이어베이서 데이터에 저장 각 입력 정보들을 넣는다..
                //대신 카테고리 별로 if문을 이용해서 따로 저장을 한다.??
                //현재 년도를 비교하고 올해이면 MM/dd HH:mm 까지
                //년도를 비교해서

                SimpleDateFormat format1 = new SimpleDateFormat("yyyy년 MM/dd HH:mm:ss");
                Date time = new Date();
                time1 = format1.format(time);
                replyCnt=0L;
                likeCnt=0L;
                //이메일 받아오기
                String userEmail = currentUser.getEmail();
                //TOdo: 아이디를 넣어줘야함.
                id = db.collection(category).document().getId();
                Map<String,Object> post = new HashMap<>();
                post.put("id",id);
                post.put("name","익명");
                post.put("title",mWriteTitleText.getText().toString());
                post.put("contents",mWriteContentsText.getText().toString());
                post.put("category",category);
                post.put("regDate",time1);
                post.put("replyCnt",replyCnt);
                post.put("likeCnt",likeCnt);
                post.put("userEmail",userEmail);
                db.collection(category)
                        .document(id).set(post)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(FeedWriteActivity.this, "업로드 성공!!", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Log.w(TAG, "Error adding document", e);
                                Toast.makeText(FeedWriteActivity.this, "업로드 실패!!", Toast.LENGTH_SHORT).show();
                            }
                        });

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

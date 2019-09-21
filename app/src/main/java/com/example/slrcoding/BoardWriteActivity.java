package com.example.slrcoding;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

import static com.example.slrcoding.MainActivity.uservo;


public class BoardWriteActivity extends AppCompatActivity {

    Toolbar toolbar;
    private EditText mWriteTitleText;
    private EditText mWriteContentsText;
    private EditText mWriteKakaoLinkText;
    private Button board_file;
    private ImageView file_preview;
    private Uri filePath;
    private Button bt;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    // 로그인 정보 가져오기 위해 선언
    private FirebaseAuth firebaseAuth;           // 파이어베이스 인증 객체 생성
    private FirebaseUser currentUser;

    private String category = null;
    private int code = 0;
    private String id;
    private String filename;

    private String time1;
    private Long replyCnt;
    private Long likeCnt;

    //유효성 검사
    private String kakaoValid="https://open.kakao.com/o/";

    String userEmail;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);
        toolbar = (Toolbar) findViewById(R.id.bod_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mWriteContentsText = (EditText) findViewById(R.id.board_memo_edit);
        mWriteTitleText = (EditText) findViewById(R.id.board_title_editText);

        // 사진 업로더
        board_file = (Button) findViewById(R.id.board_file);
        file_preview = (ImageView) findViewById(R.id.file_preview);

        //카카오 링크 받아올 곳
        mWriteKakaoLinkText = (EditText)findViewById(R.id.board_kakaolinkedit);

        Intent intent = getIntent();
        code = intent.getExtras().getInt("code");
        if (code == 1) {
            category = "책";
        } else if (code == 2) {
            category = "의류";
        }

        // 버튼 클릭 이벤트
        board_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이미지를 선택
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요."), 0);
            }
        });

    }

    // ToolBar에 menu.xml을 인플레이트함
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.boardwritemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.board_done:
                if (mWriteTitleText.getText().toString().equals("")) {
                    Toasty.warning(this, "제목을 입력해주세요.", Toasty.LENGTH_SHORT, true).show();
                    return false;
                }
                if (mWriteContentsText.getText().toString().equals("")) {
                    Toasty.warning(this, "내용을 입력해주세요.", Toasty.LENGTH_SHORT, true).show();
                    return false;
                }

                //

                //업로드할 파일이 있으면 수행
                if (filePath == null) {
                    Toasty.warning(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT, true).show();
                    return false;
                }
                //링크가 존재한다면 유효성 판단
                if(!mWriteKakaoLinkText.getText().toString().equals("")){
                    //Todo: 카카오링크 유효성 판단
                    if(mWriteKakaoLinkText.getText().toString().contains(kakaoValid)==false){
                        Toasty.error(this,"유효한 카카오링크가 아닙니다. 다시 입력해주세요.",Toasty.LENGTH_SHORT,true).show();

                        mWriteKakaoLinkText.setText("");
                        return false;
                    }
                }

                // 파일 업로드
                FirebaseStorage storage = FirebaseStorage.getInstance();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
                Date now = new Date();

                filename = category+"_"+userEmail+"_"+formatter.format(now);

                //storage 주소와 폴더 파일명을 지정
                StorageReference storageRef = storage.getReferenceFromUrl("gs://slrcoding.appspot.com/").child("Board images/" + filename);

                storageRef.putFile(filePath)
                        //성공시
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        //실패시
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        //진행중
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                @SuppressWarnings("VisibleForTests")
                                double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            }
                        });


                //여기서 파이어베이서 데이터에 저장 각 입력 정보들을 넣는다..
                //대신 카테고리 별로 if문을 이용해서 따로 저장을 한다.
                //현재 년도를 비교하고 올해이면 MM/dd HH:mm 까지
                //년도를 비교해서

                SimpleDateFormat format1 = new SimpleDateFormat("yyyy년 MM/dd HH:mm:ss");
                Date time = new Date();
                time1 = format1.format(time);
                // L은 long 타입
                replyCnt = 0L;
                likeCnt = 0L;

                // 이메일과 이름 받아오기
                userEmail = uservo.getUser_email();
                userName = uservo.getUser_name();

                final SweetAlertDialog progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                progressDialog.setTitleText("글 등록중...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                id = db.collection(category).document().getId();
                Map<String, Object> post = new HashMap<>();

                post.put("image", filename);
                post.put("id", id);
                post.put("title", mWriteTitleText.getText().toString());
                post.put("contents", mWriteContentsText.getText().toString());
                post.put("category", category);
                post.put("regDate", time1);
                post.put("replyCnt", replyCnt);
                post.put("likeCnt", likeCnt);
                post.put("userEmail", userEmail);
                // user의 이름 추가
                post.put("name", userName);
                post.put("kakaolink",mWriteKakaoLinkText.getText().toString());

                db.collection(category)
                        .document(id).set(post)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toasty.success(BoardWriteActivity.this, "게시글이 등록 성공", Toasty.LENGTH_SHORT, true).show();
                                finish();
                                progressDialog.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Log.w(TAG, "Error adding document", e);
                                Toasty.error(BoardWriteActivity.this, "게시글이 등록 실패", Toasty.LENGTH_SHORT, true).show();
                            }
                        });

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //
    //결과 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK) {
            filePath = data.getData();

            try {
                //Uri 파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                file_preview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
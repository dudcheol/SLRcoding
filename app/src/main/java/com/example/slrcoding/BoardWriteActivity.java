package com.example.slrcoding;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.slrcoding.MainActivity.uservo;


public class BoardWriteActivity extends AppCompatActivity {

    Toolbar toolbar;
    private EditText mWriteTitleText;
    private EditText mWriteContentsText;
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
    private String image_id;

    private String time1;
    private Long replyCnt;
    private Long likeCnt;

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

        // activity_board_write.xml에서 가져옴
        mWriteContentsText = (EditText) findViewById(R.id.board_memo_edit);
        mWriteTitleText = (EditText) findViewById(R.id.board_title_editText);
        // 사진 업로더
        board_file = (Button) findViewById(R.id.board_file);
        file_preview = (ImageView) findViewById(R.id.file_preview);

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
                Toast.makeText(getApplicationContext(), "등록 버튼 클릭됨", Toast.LENGTH_LONG).show();
                //예외처리
                if (mWriteTitleText.getText().toString().equals("")) {
                    Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (mWriteContentsText.getText().toString().equals("")) {
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
                // L은 long 타입
                replyCnt = 0L;
                likeCnt = 0L;

                // 이메일과 이름 받아오기
                userEmail = uservo.getUser_email();
                userName = uservo.getUser_name();

                id = db.collection(category).document().getId();
                Map<String, Object> post = new HashMap<>();
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

                // 파일 업로드
                uploadFile();

                db.collection(category)
                        .document(id).set(post)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(BoardWriteActivity.this, "업로드 성공!!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Log.w(TAG, "Error adding document", e);
                                Toast.makeText(BoardWriteActivity.this, "업로드 실패!!", Toast.LENGTH_SHORT).show();
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
        //request코드가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면
        if(requestCode == 0 && resultCode == RESULT_OK){
            filePath = data.getData();
            //Log.d(MainActivity, "uri:" + String.valueOf(filePath));
            try {
                //Uri 파일을 Bitmap으로 만들어서 ImageView에 집어 넣는다.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                file_preview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //upload the file
    private void uploadFile() {
        // image_id = db.collection(category).document().getId();

        //업로드할 파일이 있으면 수행
        if (filePath != null) {
            //업로드 진행 Dialog 보이기
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();

            //storage
            FirebaseStorage storage = FirebaseStorage.getInstance();

            //Unique한 파일명을 만들자.
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            // String filename = formatter.format(now) + ".png";
            String filename = id + ".png";

            //storage 주소와 폴더 파일명을 지정해 준다.
            StorageReference storageRef = storage.getReferenceFromUrl("gs://slrcoding.appspot.com/").child("Board images/" + filename);

            //올라가거라...
            storageRef.putFile(filePath)
                    //성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                            Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests")
                                    double progress = (100 * taskSnapshot.getBytesTransferred()) /  taskSnapshot.getTotalByteCount();
                            //dialog에 진행률을 퍼센트로 출력해 준다
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }
    //
}
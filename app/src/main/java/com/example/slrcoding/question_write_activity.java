package com.example.slrcoding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

// 최민철(수정 : 19.08.19)
public class question_write_activity extends AppCompatActivity {

    Toolbar toolbar;
    private String class_name, user_email;
    private TextView tv1;
    private EditText title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_sub3__question_write);

        // 인터넷 사용을 위한 권한 허용
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        tv1 = (TextView)findViewById(R.id.question_write_class);
        title = (EditText)findViewById(R.id.question_write_title);
        content = (EditText)findViewById(R.id.question_write_content);
        toolbar = (Toolbar)findViewById(R.id.question_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 인텐트로 받아 각 카테고리별 텍스트뷰 세팅
        Intent intent = getIntent();
        class_name = intent.getStringExtra("class_name");
        user_email = intent.getStringExtra("user_email");
        tv1.setText(class_name);

    }

    // Toolbar에 meun.xml 인플레이트
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.questionwritemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:     // 뒤로가기
                finish();
                return true;
            case R.id.question_done:    // 작성완료 버튼
                // 유효성 검사
                if(TextUtils.isEmpty(title.getText())){
                    Toast.makeText(getApplicationContext(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(content.getText())){
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    // sender객체를 통해 관리자 이메일로 문의내용 접수
                    try{
                        GMailSender sender = new GMailSender();
                        sender.sendMail("FunnyCampus 문의사항 :"+class_name,
                                "제목 : "+title.getText().toString()+ "\n내용 : "+content.getText().toString()+"\n문의자 이메일: "+ user_email,
                                "slrcoding1234@gmail.com");
                        Toast.makeText(getApplicationContext(), "등록 되었습니다", Toast.LENGTH_SHORT).show();
                        finish();
                    }catch (SendFailedException e){
                        Toast.makeText(getApplicationContext(), "이메일 형식 잘못됨", Toast.LENGTH_SHORT).show();
                    }catch (MessagingException e){
                        Toast.makeText(getApplicationContext(), "인터넷 연결 확인요망", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.slrcoding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Mypage_sub3_Question extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_sub3__question);

        ViewGroup layout1 = (ViewGroup)findViewById(R.id.QA_Layout_auth);
        ViewGroup layout2 = (ViewGroup)findViewById(R.id.QA_Layout_community);
        ViewGroup layout3 = (ViewGroup)findViewById(R.id.QA_Layout_access);
        ViewGroup layout4 = (ViewGroup)findViewById(R.id.QA_Layout_other);
        ViewGroup layout5 = (ViewGroup)findViewById(R.id.QA_Layout_adv);

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Mypage_sub3_Question.this, "인증 문의", Toast.LENGTH_SHORT).show();
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Mypage_sub3_Question.this, "커뮤니티 문의", Toast.LENGTH_SHORT).show();
            }
        });

        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Mypage_sub3_Question.this, "신고/접근제한 문의", Toast.LENGTH_SHORT).show();
            }
        });

        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Mypage_sub3_Question.this, "기타 문의", Toast.LENGTH_SHORT).show();
            }
        });

        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Mypage_sub3_Question.this, "제휴/광고 문의", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

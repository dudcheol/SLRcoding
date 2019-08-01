package com.example.slrcoding;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// 최민철(수정 : xx.xx.xx)
public class Mypage_sub2_Alarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_sub2__alarm);

        Switch sw1 = (Switch) findViewById(R.id.alarm_sw1);
        Switch sw2 = (Switch) findViewById(R.id.alarm_sw2);
        Switch sw3 = (Switch) findViewById(R.id.alarm_sw3);
        Switch sw4 = (Switch) findViewById(R.id.alarm_sw4);

        // "푸시 알림" 설정
        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    Toast.makeText(Mypage_sub2_Alarm.this, "푸시 알림 활성화", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Mypage_sub2_Alarm.this, "푸시 알림 비 활성화", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // "내 글에 새 댓글" 설정
        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    Toast.makeText(Mypage_sub2_Alarm.this, "내 글에 새 댓글 활성화", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Mypage_sub2_Alarm.this, "내 글에 새 댓글 비 활성화", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // "새 메시지" 설정
        sw3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    Toast.makeText(Mypage_sub2_Alarm.this, "새 메시지 활성화", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Mypage_sub2_Alarm.this, "새 메시지 비 활성화", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // "이벤트 및 정보" 설정
        sw4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    Toast.makeText(Mypage_sub2_Alarm.this, "이벤트 및 정보 활성화", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Mypage_sub2_Alarm.this, "이벤트 및 정보 비 활성화", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

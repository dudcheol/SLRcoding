package com.example.slrcoding;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Mypage_sub2_Message extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_sub2__message);

        Switch sw5 = (Switch) findViewById(R.id.alarm_sw5);

        // "수신 및 발신" 설정
        sw5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    Toast.makeText(Mypage_sub2_Message.this, "수신 및 발신 활성화", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Mypage_sub2_Message.this, "수신 및 발신 비 활성화", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

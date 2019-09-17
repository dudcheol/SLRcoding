package com.example.slrcoding.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.slrcoding.R;

import es.dmoral.toasty.Toasty;

/**
 * 채팅방
 * 여기서 우선 상대방 아이디를 받아온다.
 * 받아오고 나서 내용 쓰고 전송 버튼누르면 리얼타임데이터베이스에 넣어준다. ==> 채팅방이 생긴다.!!
 * faceagree 여부를 받아와 true이면 상대방 얼굴 보이도록 하기.
 * @author 이정찬
 */
public class ChatRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Intent intent = getIntent();
        boolean agree = intent.getBooleanExtra("faceagree",false);
        Toasty.info(getApplicationContext(),"agree:"+agree,Toasty.LENGTH_SHORT,true).show();

    }
}

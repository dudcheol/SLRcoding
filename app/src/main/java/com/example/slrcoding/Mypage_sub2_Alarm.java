package com.example.slrcoding;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

// 최민철(수정 : 19.08.19)
public class Mypage_sub2_Alarm extends AppCompatActivity {

    private boolean push_alarm, event_alarm, comment_alarm;
    private boolean[] alarm_set = new boolean[3];
    private Switch sw1, sw2, sw3;
    private FirebaseFirestore firebasestore = FirebaseFirestore.getInstance();    // 파이어베이스 스토어 객체 생성 및 선언
    private String user_email;
    //private NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    //private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_sub2__alarm);

        /*
        pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                new Intent(getApplicationContext(), MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.app_icon)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle("알림제목")
                .setContentText("알림내용")
                .setTicker("한줄 출력")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        notificationManager.notify(0, builder.build());
        */

        sw1 = (Switch) findViewById(R.id.alarm_sw1);
        sw2 = (Switch) findViewById(R.id.alarm_sw2);
        sw3 = (Switch) findViewById(R.id.alarm_sw3);

        // 각 Alarm의 boolean값을 받음(메인 액티비티로 부터)
        Intent intent = getIntent();
        push_alarm = intent.getBooleanExtra("push_alarm", false);
        event_alarm = intent.getBooleanExtra("event_alarm", false);
        comment_alarm = intent.getBooleanExtra("comment_alarm", false);
        user_email = intent.getStringExtra("user_email");

        sw1.setChecked(push_alarm);
        sw2.setChecked(comment_alarm);
        sw3.setChecked(event_alarm);

        // 각 알람의 이벤트 처리 과정
        // "푸시 알림" 설정
        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    sw2.setChecked(true);
                    sw3.setChecked(true);
                    Set_Alarm("user_push_alarm", b, user_email);
                    Set_Alarm("user_comment_alarm", b, user_email);
                    Set_Alarm("user_event_alarm", b, user_email);
                    Toast.makeText(Mypage_sub2_Alarm.this, "푸시 알림 활성화", Toast.LENGTH_SHORT).show();
                }else{
                    sw2.setChecked(false);
                    sw3.setChecked(false);
                    Set_Alarm("user_push_alarm", b, user_email);
                    Set_Alarm("user_comment_alarm", b, user_email);
                    Set_Alarm("user_event_alarm", b, user_email);
                    Toast.makeText(Mypage_sub2_Alarm.this, "푸시 알림 비 활성화", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // "내 글에 새 댓글" 설정
        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true && sw1.isChecked()==true){
                    Set_Alarm("user_comment_alarm", b, user_email);
                    Toast.makeText(Mypage_sub2_Alarm.this, "내 글에 새 댓글 활성화", Toast.LENGTH_SHORT).show();
                }else if(b==true){
                    sw2.setChecked(false);
                }else{
                    Set_Alarm("user_comment_alarm", b, user_email);
                    Toast.makeText(Mypage_sub2_Alarm.this, "내 글에 새 댓글 비 활성화", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // "댓글 단 글" 설정
        sw3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true && sw1.isChecked()==true){
                    Set_Alarm("user_event_alarm", b, user_email);
                    Toast.makeText(Mypage_sub2_Alarm.this, "새 메시지 활성화", Toast.LENGTH_SHORT).show();
                }else if(b==true){
                    sw3.setChecked(false);
                }else{
                    Set_Alarm("user_event_alarm", b, user_email);
                    Toast.makeText(Mypage_sub2_Alarm.this, "새 메시지 비 활성화", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 뒤로가기 이벤트처리(인텐트로 바뀐 알림 설정 값을 보냄)
    @Override
    public void onBackPressed(){
        resultData();
        super.onBackPressed();
    }

    // firebase에 알람 설정
    private void Set_Alarm(String alarm_name, boolean alarm, String user_email){
        firebasestore.collection("사용자 정보").document(user_email).update(alarm_name, alarm);
        switch (alarm_name){
            case "user_push_alarm":
                push_alarm = alarm;
                break;
            case "user_comment_alarm":
                comment_alarm = alarm;
                break;
            case "user_event_alarm":
                event_alarm = alarm;
                break;
        }
    }

    // 알람 설정 값 uservo에 update시키기 위해 사용
    public void resultData(){
        alarm_set[0]=push_alarm;
        alarm_set[1]=comment_alarm;
        alarm_set[2]=event_alarm;

        Intent intent = new Intent();
        intent.putExtra("alarm_key", alarm_set);
        setResult(RESULT_OK, intent);
    }
}

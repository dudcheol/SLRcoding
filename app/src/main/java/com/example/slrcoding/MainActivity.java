package com.example.slrcoding;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText("메인 화면");
                    setTitle("홈");
                    return true;
                case R.id.navigation_feed:
                    mTextMessage.setText("피드");
                    setTitle("피드");
                    return true;
                case R.id.navigation_comunity:
                    mTextMessage.setText("게시판");
                    setTitle("게시판");
                    return true;
                case R.id.navigation_message:
                    mTextMessage.setText("메시지 알림");
                    setTitle("메시지");
                    return true;
                case R.id.navigation_mypage:
                    mTextMessage.setText("마이페이지");
                    setTitle("마이페이지");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("홈");
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}

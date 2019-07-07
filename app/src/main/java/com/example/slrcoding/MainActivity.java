package com.example.slrcoding;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;

import com.example.slrcoding.fragment.BoardFragment;
import com.example.slrcoding.fragment.FeedFragment;
import com.example.slrcoding.fragment.MainFragment;
import com.example.slrcoding.fragment.MessageFragment;
import com.example.slrcoding.fragment.MypageFragment;

public class MainActivity extends AppCompatActivity {
    int flag=0;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();

            // 프래그먼트!!
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle("홈");
                    fragmentTransaction.replace(R.id.main_content, new MainFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_feed:
                    setTitle("피드");
                    fragmentTransaction.replace(R.id.main_content, new FeedFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_comunity:
                    setTitle("게시판");
                    fragmentTransaction.replace(R.id.main_content, new BoardFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_message:
                    setTitle("메시지");
                    fragmentTransaction.replace(R.id.main_content, new MessageFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_mypage:
                    setTitle("마이페이지");
                    fragmentTransaction.replace(R.id.main_content, new MypageFragment());
                    fragmentTransaction.commit();
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
        Intent intent = getIntent();

        if(intent.getExtras()!=null){
            flag =intent.getExtras().getInt("flag");
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if(flag==1){
            Log.i("first","first");
            fragmentTransaction.add(R.id.main_content, new FeedFragment());
            fragmentTransaction.commit();
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.getMenu().getItem(1).setChecked(true);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }else {
            Log.i("first","two");
            fragmentTransaction.add(R.id.main_content, new MainFragment());
            fragmentTransaction.commit();
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }
    }

}
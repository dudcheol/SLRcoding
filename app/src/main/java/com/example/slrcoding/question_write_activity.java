package com.example.slrcoding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class question_write_activity extends AppCompatActivity {

    String class_name;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_sub3__question_write);

        tv1 = (TextView)findViewById(R.id.question_write_class);

        Intent intent = getIntent();
        class_name = intent.getStringExtra("class_name");

        tv1.setText(class_name);
    }
}

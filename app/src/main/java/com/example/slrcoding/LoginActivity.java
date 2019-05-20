package com.example.slrcoding;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    InputMethodManager editManager;
    EditText EmailInput, PasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        relativeLayout = findViewById(R.id.loginLayout);
        editManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        Button bt_login = (Button)findViewById(R.id.loginButton);
        Button bt_signup = (Button)findViewById(R.id.signupButton);
        EmailInput = (EditText)findViewById(R.id.emailInput);
        PasswordInput = (EditText)findViewById(R.id.passwordInput);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "로그인 성공!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent1);
            }
        });

        // 레이아웃을 누를 시 키보드 숨기기.
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v!=null)
                    hide_Key_Board();
            }
        });
    }

    // EditText에 키보드 바로 띄우지 않기
    @Override
    protected void onResume() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onResume();
    }

    public void hide_Key_Board(){
        // Edit_ID와 같은(EditText)의 뷰들도 포함.
        editManager.hideSoftInputFromWindow(EmailInput.getWindowToken(),0);
    }
}

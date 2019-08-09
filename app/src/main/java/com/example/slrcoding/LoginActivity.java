package com.example.slrcoding;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// 최민철(수정 : 19.08.06)
public class LoginActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    InputMethodManager editManager;
    EditText EmailInput, PasswordInput;
    CheckBox Autologin_check;
    private FirebaseAuth firebaseAuth;  // 파이어베이스 인증 객체 생성
    private FirebaseUser currentUser;   // 현재 로그인 된 정보를 담은 객체 생성

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        firebaseAuth = FirebaseAuth.getInstance();  // 파이어베이스 인증 객체 선언

        relativeLayout = findViewById(R.id.loginLayout);
        editManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        Button bt_login = (Button)findViewById(R.id.loginButton);
        Button bt_signup = (Button)findViewById(R.id.signupButton);
        EmailInput = (EditText)findViewById(R.id.emailInput);
        PasswordInput = (EditText)findViewById(R.id.passwordInput);
        Autologin_check = (CheckBox)findViewById(R.id.autologin_check);

        // 자동 로그인 체크박스 이벤트 처리
        Autologin_check.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v){
                if(((CheckBox)v).isChecked()){
                    Toast.makeText(LoginActivity.this, "자동로그인이 활성화 되었습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "자동로그인이 비 활성화 되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 로그인 버튼 이벤트 처리
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(EmailInput.getText()) || TextUtils.isEmpty(PasswordInput.getText())){
                    hide_Key_Board();
                    Toast.makeText(LoginActivity.this, "이메일 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    EmailInput.setText("");
                    PasswordInput.setText("");
                    return;
                }else if(!Patterns.EMAIL_ADDRESS.matcher(EmailInput.getText().toString()).matches()){
                    hide_Key_Board();
                    Toast.makeText(LoginActivity.this, "이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
                    EmailInput.setText("");
                    PasswordInput.setText("");
                    return;
                }else{
                    loginUser(EmailInput.getText().toString(), PasswordInput.getText().toString());
                }
            }
        });

        // 회원가입 버튼 이벤트 처리
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

    // 로그인
    private void loginUser(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            hide_Key_Board();
                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }else{
                            hide_Key_Board();
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }

    // 로그인 되어있으면 자동으로 메인페이지로 이동
    @Override
    public void onStart(){
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}

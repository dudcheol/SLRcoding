package com.example.slrcoding;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

// 최민철(수정 : 19.08.19)
public class LoginActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    InputMethodManager editManager;
    EditText EmailInput, PasswordInput;
    CheckBox Autologin_check;

    private SharedPreferences appdata;
    private boolean auto_login_flag;
    private FirebaseAuth firebaseAuth;  // 파이어베이스 인증 객체 생성
    private FirebaseUser currentUser;   // 현재 로그인 된 정보를 담은 객체 생성
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                // Editor사용하여 설정 값 저장
                SharedPreferences.Editor editor = appdata.edit();
                if(((CheckBox)v).isChecked()){
                    Toast.makeText(LoginActivity.this, "자동 로그인이 활성화 되었습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "자동 로그인이 비 활성화 되었습니다.", Toast.LENGTH_SHORT).show();
                }
                editor.putBoolean("auto_login_flag", ((CheckBox)v).isChecked());
                editor.apply();     // apply안하면 변경된 내용 저장되지 않음
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

    // 자동 로그인 설정에 따라 시작화면 설정
    @Override
    public void onStart(){
        super.onStart();
        appdata = getSharedPreferences("appdata", MODE_PRIVATE); // 설정 값 불러오기
        auto_login_flag = appdata.getBoolean("auto_login_flag", false);
        if(auto_login_flag){
            currentUser = firebaseAuth.getCurrentUser();
            if(currentUser != null){
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }
    }

}

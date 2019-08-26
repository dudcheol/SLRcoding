package com.example.slrcoding;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

// 최민철(수정 : 19.08.21)
public class SignUpActivity extends AppCompatActivity {

    RelativeLayout relativeLayout1;
    EditText Edit_ID, Edit_Email, Edit_Check, Edit_PW, Edit_Name, Edit_PhoneNum;
    Spinner Spinner_Year, Spinner_Month, Spinner_Day;
    Button Button_Check, Button_email_check;
    RadioGroup Radio_Sex;
    public String Save_ID, Save_Email, Save_PW, Save_Name, Save_Year, Save_Month, Save_Day, Save_PhoneNum, Save_Sex;
    public boolean push_alarm, comment_alarm, event_alarm;
    InputMethodManager editManager;
    private FirebaseAuth firebaseAuth;          // 파이어베이스 인증 객체 생성
    private FirebaseFirestore firebasestore;    // 파이어베이스 스토어 객체 생성
    private String firebase_id;
    private String email_code;
    private String category = "사용자 정보";
    private String schoolEmail = "ac.kr";
    public static boolean overlap_flag, valid_flag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        // 인터넷 사용을 위한 권한 허용
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .permitDiskReads()
        .permitDiskWrites()
        .permitNetwork().build());

        firebaseAuth = FirebaseAuth.getInstance();          // 파이어베이스 인증 객체 선언
        firebasestore = FirebaseFirestore.getInstance();    // 파이어베이스 스토어 객체 선언

        relativeLayout1 = findViewById(R.id.signup_layout);
        editManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        Edit_ID = (EditText)findViewById(R.id.edit_id);
        Edit_Email = (EditText)findViewById(R.id.edit_email);
        Edit_Check  = (EditText)findViewById(R.id.edit_check);
        Edit_PW = (EditText)findViewById(R.id.edit_pw);
        Edit_Name = (EditText)findViewById(R.id.edit_name);
        Spinner_Year = (Spinner)findViewById(R.id.spinner_year);
        Spinner_Month = (Spinner)findViewById(R.id.spinner_month);
        Spinner_Day=(Spinner)findViewById(R.id.spinner_day);
        Edit_PhoneNum = (EditText)findViewById(R.id.edit_phonenum);
        Button_Check = (Button)findViewById(R.id.button_check);
        Button_email_check = (Button)findViewById(R.id.button_email_check);
        Radio_Sex = (RadioGroup)findViewById(R.id.radiogroup_sex);

        // 생년월일을 스피너로 입력 받음.
        Spinner yearSpinner = (Spinner)findViewById(R.id.spinner_year);
        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.date_year, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        Spinner monthSpinner = (Spinner)findViewById(R.id.spinner_month);
        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(this,
                R.array.date_month, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);

        Spinner daySpinner = (Spinner)findViewById(R.id.spinner_day);
        ArrayAdapter dayAdapter = ArrayAdapter.createFromResource(this,
                R.array.date_day, android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);

        // 이메일 인증을 위한 전송 버튼
        Button_email_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(Edit_Email.getText())){
                    Toast.makeText(getApplicationContext(), "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                    valid_flag=false;
                    return;
                } else if(Edit_Email.getText().toString().contains(schoolEmail)==false) {
                    Toast.makeText(getApplicationContext(), "학교 이메일형식이 아닙니다", Toast.LENGTH_SHORT).show();
                    Edit_Email.setText("");
                    valid_flag=false;
                    return;
                }else{
                    try{
                        findViewById(R.id.button_email_check).setEnabled(false);
                        GMailSender sender = new GMailSender();
                        sender.sendMail("이메일 확인인증.", sender.getEmailCode(), Edit_Email.getText().toString());   // sender.getEmailcode와 사용자가 웹 메일에서 확인한 코드와 같으면 인증성공
                        Toast.makeText(getApplicationContext(), "이메일을 성공적으로 보냈습니다.", Toast.LENGTH_SHORT).show();
                        email_code = sender.getEmailCode();
                        findViewById(R.id.button_email_check).setEnabled(true);
                        valid_flag=true;
                    }catch (SendFailedException e){
                        Toast.makeText(getApplicationContext(), "이메일 형식 잘못됨", Toast.LENGTH_SHORT).show();
                    }catch (MessagingException e){
                        Toast.makeText(getApplicationContext(), "인터넷 연결 확인요망", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        // 작성완료 버튼 이벤트 처리
        Button_Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validForm()){
                    return;
                }else if(!valid_flag){
                    Toast.makeText(getApplicationContext(), "이메일 인증이 필요합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(Edit_Check.getText())){
                    Toast.makeText(getApplicationContext(), "이메일 인증코드를 입력해주세요", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "email code : "+email_code+"Edit : "+Edit_Check.getText().toString(), Toast.LENGTH_SHORT).show();
                    return;
                }else if(!email_code.equals(Edit_Check.getText().toString())){
                    Toast.makeText(getApplicationContext(), "이메일 인증코드가 알맞지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    // String값으로 EditText값들 저장.
                    Save_ID = Edit_ID.getText().toString();
                    Save_Email = Edit_Email.getText().toString();
                    Save_PW = Edit_PW.getText().toString();
                    Save_Name = Edit_Name.getText().toString();
                    Save_Year = Spinner_Year.getSelectedItem().toString();
                    Save_Month = Spinner_Month.getSelectedItem().toString();
                    Save_Day = Spinner_Day.getSelectedItem().toString();
                    Save_PhoneNum = Edit_PhoneNum.getText().toString();
                    int id = Radio_Sex.getCheckedRadioButtonId();
                    RadioButton rb1 = (RadioButton)findViewById(id);
                    Save_Sex = rb1.getText().toString();
                    push_alarm = false;
                    comment_alarm = false;
                    event_alarm = false;

                    // 중복되는 아이디 있는지 확인 및 회원 생성
                    readQueryEqualId(Save_ID, new MyCallback() {
                        @Override
                        public void onCallback(boolean value) {
                            if(value){
                                Create_User(Save_ID, Save_Email, Save_PW, Save_Name, Save_Year, Save_Month, Save_Day, Save_PhoneNum, Save_Sex,
                                        push_alarm, comment_alarm, event_alarm); // 회원 생성
                            }else{
                                Toast.makeText(getApplicationContext(), "중복되는 아이디가 존재합니다. value : "+value, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
                }
            }
        });

        // 레이아웃을 누를 시 키보드 숨기기.
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v!=null)
                    hide_Key_Board();
            }
        });

        // 라디오 버튼 누를 시 키보드 숨기기.
        Radio_Sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                hide_Key_Board();
            }
        });

    }

    // 회원가입.(이메일과 비밀번호를 사용하여 파이어베이스에 회원가입 정보 저장 + cloud messaging을 위한 token생성)
    private void Create_User(String id, String email, String password, String name, String year, String month, String day, String phoneNum, String sex,
                             boolean push_alarm, boolean comment_alarm, boolean event_alarm){

        String token = FirebaseInstanceId.getInstance().getToken();
        firebaseAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            hide_Key_Board();
                            firebase_id = email;
                            Map<String, Object> post = new HashMap<>();
                            post.put("user_id", id);
                            post.put("user_email", email);
                            //post.put("user_password", password);
                            post.put("user_name", name);
                            post.put("user_year", year);
                            post.put("user_month", month);
                            post.put("user_day", day);
                            post.put("user_phoneNum", phoneNum);
                            post.put("user_sex", sex);
                            post.put("user_push_alarm", push_alarm);
                            post.put("user_comment_alarm", comment_alarm);
                            post.put("user_event_alarm", event_alarm);
                            post.put("user_token", token);
                            //이정찬 Uid 넣어주기 추가 2019.08.26
                            post.put("unique_id",FirebaseAuth.getInstance().getCurrentUser().getUid());

                            firebasestore.collection(category)
                                    .document(firebase_id).set(post)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                                            finish(); // 다시 로그인 창으로 이동
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SignUpActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    });
                        }else{
                            hide_Key_Board();
                            Toast.makeText(SignUpActivity.this, "회원가입 실패 : 이메일 또는 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }

    // 중복되는 아이디 존재 확인.(파이어베이스의 비동기 처리문제로 인해 외부에서 데이터 접근하기 위해 콜백함수 사용)
    public void readQueryEqualId(String user_ID, MyCallback mycallback){
        firebasestore.collection(category).whereEqualTo("user_id", user_ID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            overlap_flag=task.getResult().isEmpty();
                            mycallback.onCallback(overlap_flag);       // flag값이 수신됐을때 시스템에서 콜백함수 호출
                        }
                    }
                });
    }

    // 비동기 처리 해결하기 위해 생성한 콜백함수
    public interface MyCallback{
        void onCallback(boolean value);
    }

    // 회원가입 양식의 유효성 검증
    private boolean validForm(){

        boolean valid = true;

        // 모든 입력사항들 기재하지 않았을 시 예외처리.
        if(TextUtils.isEmpty(Edit_ID.getText()) || TextUtils.isEmpty(Edit_Email.getText()) || TextUtils.isEmpty(Edit_PW.getText())
                || TextUtils.isEmpty(Edit_Name.getText())|| TextUtils.isEmpty(Spinner_Year.getSelectedItem().toString())
                || TextUtils.isEmpty(Spinner_Month.getSelectedItem().toString())|| TextUtils.isEmpty(Spinner_Day.getSelectedItem().toString())
                || TextUtils.isEmpty(Edit_PhoneNum.getText())
                || (Radio_Sex.getCheckedRadioButtonId()==-1)){
            Toast.makeText(getApplicationContext(), "모든 선택사항을 입력 및 클릭해주세요.", Toast.LENGTH_SHORT).show();
            hide_Key_Board();
            valid = false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(Edit_Email.getText().toString()).matches()){
            Toast.makeText(getApplicationContext(), "이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
            Edit_Email.setText("");
            hide_Key_Board();
            valid = false;
        }

        return valid;
    }

    // EditText에 키보드 바로 띄우지 않기.
    @Override
    protected void onResume() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onResume();
    }

    public void hide_Key_Board(){
        // Edit_ID와 같은(EditText)의 뷰들도 포함.
        editManager.hideSoftInputFromWindow(Edit_ID.getWindowToken(),0);
    }
}

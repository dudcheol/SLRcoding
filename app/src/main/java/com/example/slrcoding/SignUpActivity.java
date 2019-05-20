package com.example.slrcoding;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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


public class SignUpActivity extends AppCompatActivity {

    RelativeLayout relativeLayout1;
    EditText Edit_ID, Edit_PW, Edit_Name, Edit_PhoneNum;
    Spinner Spinner_Year, Spinner_Month, Spinner_Day;
    Button Button_Check;
    RadioGroup Radio_Sex;
    public String Save_ID, Save_PW, Save_Name, Save_Year, Save_Month, Save_Day, Save_PhoneNum, Save_Sex;
    InputMethodManager editManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        relativeLayout1 = findViewById(R.id.signup_layout);
        editManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        Edit_ID = (EditText)findViewById(R.id.edit_id);
        Edit_PW = (EditText)findViewById(R.id.edit_pw);
        Edit_Name = (EditText)findViewById(R.id.edit_name);
        Spinner_Year = (Spinner)findViewById(R.id.spinner_year);
        Spinner_Month = (Spinner)findViewById(R.id.spinner_month);
        Spinner_Day=(Spinner)findViewById(R.id.spinner_day);
        Edit_PhoneNum = (EditText)findViewById(R.id.edit_phonenum);
        Button_Check = (Button)findViewById(R.id.button_check);
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

        // 모든 입력사항들 기재하지 않았을 시 예외처리.
        Button_Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edit_id = Edit_ID.getText().toString();
                int j =0; // 예외처리를 위한 제어변수,

                if(TextUtils.isEmpty(Edit_ID.getText())|| TextUtils.isEmpty(Edit_PW.getText())
                        || TextUtils.isEmpty(Edit_Name.getText())|| TextUtils.isEmpty(Spinner_Year.getSelectedItem().toString())
                        || TextUtils.isEmpty(Spinner_Month.getSelectedItem().toString())|| TextUtils.isEmpty(Spinner_Day.getSelectedItem().toString())
                        || TextUtils.isEmpty(Edit_PhoneNum.getText())
                        || (Radio_Sex.getCheckedRadioButtonId()!=R.id.radio_man&&Radio_Sex.getCheckedRadioButtonId()!=R.id.radio_woman)){
                    Toast.makeText(getApplicationContext(), "모든 선택사항을 입력 및 클릭해주세요."
                            , Toast.LENGTH_SHORT).show();
                    return;
                }

                // 존재하지 않은 아이디를 입력할 경우 db에 저장.
                if(j==0){
                    // String값으로 EditText값들 저장.
                    Save_ID = Edit_ID.getText().toString();
                    Save_PW = Edit_PW.getText().toString();
                    Save_Name = Edit_Name.getText().toString();
                    Save_Year = Spinner_Year.getSelectedItem().toString();
                    Save_Month = Spinner_Month.getSelectedItem().toString();
                    Save_Day = Spinner_Day.getSelectedItem().toString();
                    Save_PhoneNum = Edit_PhoneNum.getText().toString();
                    int id = Radio_Sex.getCheckedRadioButtonId();
                    RadioButton rb1 = (RadioButton)findViewById(id);
                    Save_Sex = rb1.getText().toString();

                    Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                    finish(); // 다시 로그인 창으로 이동
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

    // EditText에 키보드 바로 띄우지 않기
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

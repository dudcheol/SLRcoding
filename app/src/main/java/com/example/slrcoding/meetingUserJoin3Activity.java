package com.example.slrcoding;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

import static com.example.slrcoding.MainActivity.uservo;

public class meetingUserJoin3Activity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;

    private Button ok_btn;
    private TextView textCnt;
    private EditText EditTextMySelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_user_join3);

        init();
        textCounter();
        btnClick();
    }

    private void init() {
        ok_btn = (Button) findViewById(R.id.ok_btn);
        textCnt = (TextView) findViewById(R.id.textCnt);
        EditTextMySelf = (EditText) findViewById(R.id.EditTextMySelf);
        EditTextMySelf.setText(uservo.getUser_intro_string());

        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private void btnClick() {
        ok_btn.setOnClickListener(v -> {
            checkTextExist();
        });
    }

    private void checkTextExist() {
        if (EditTextMySelf.getText().toString().length() == 0) {
            Toasty.error(getApplicationContext(), "한마디 소개를 작성해야 합니다.", Toasty.LENGTH_SHORT, true).show();
        } else {
            uploadUserIntroString();
        }
    }

    private void textCounter() {
        EditTextMySelf.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int currentTextSize = EditTextMySelf.getText().toString().length();
                textCnt.setText(currentTextSize + "");
                if (currentTextSize >= 30)
                    Toasty.warning(getApplicationContext(), "30자를 넘길 수 없습니다.", Toasty.LENGTH_SHORT, true).show();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void uploadUserIntroString() {
        SweetAlertDialog progressDialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
        progressDialog
                .setTitleText("입력한 내용이 맞나요?")
                .setContentText(EditTextMySelf.getText().toString())
                .setConfirmText("맞아요")
                .setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                    sweetAlertDialog
                            .setTitleText("Loading")
                            .showContentText(false)
                            .showCancelButton(false);

                    Map<String, Object> item = new HashMap<>();
                    item.put("user_intro_string", EditTextMySelf.getText().toString());

                    firebaseFirestore.collection("사용자 정보")
                            .document(uservo.getUser_email())
                            .update(item)
                            .addOnSuccessListener(aVoid -> {
                                // 서버에 올리는 것 성공했으므로 메인액티비티로 이동함
                                Intent intent = new Intent(this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("EXIT", true);
                                startActivity(intent);
                                uservo.setUser_intro_string(EditTextMySelf.getText().toString());
                                progressDialog.dismiss();
                            })
                            .addOnFailureListener(runnable -> {
                                Toasty.error(getApplicationContext(), "에러가 발생했습니다. 다시 시도해주세요.", Toasty.LENGTH_SHORT, true).show();
                                progressDialog.dismiss();
                            });
                })
                .setCancelText("다시 쓸래요")
                .setCancelClickListener(sweetAlertDialog -> {
                    progressDialog.dismiss();
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}

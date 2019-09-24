package com.example.slrcoding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.IntentCompat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

import static com.example.slrcoding.MainActivity.uservo;

// 박영철

public class meetingUserJoin2Activity extends AppCompatActivity {
    final int PICK_FACE_PROFILE_FROM_ALBUM = 20;
    private String prof_string_to_face = "_faceImage.png";

    private CardView add_photo;
    private Button ok_btn;
    private RelativeLayout text_layout;
    private ImageView profile_img;
    private ProgressBar progressBar;
    FirebaseDatabase realtimedatabase = FirebaseDatabase.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_user_join2);

        ok_btn = findViewById(R.id.ok_btn);
        add_photo = findViewById(R.id.profile);
        text_layout = findViewById(R.id.text_layout);
        profile_img = findViewById(R.id.profile_img);
        progressBar = findViewById(R.id.progressbar);

        checkSetting();
        initSetting();

        add_photo.setOnClickListener(v -> {
            photoPicker();
        });

        ok_btn.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        });
    }

    void initSetting(){
        text_layout.setVisibility(View.VISIBLE);
        ok_btn.setVisibility(View.GONE);
    }

    void photoPicker(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent,PICK_FACE_PROFILE_FROM_ALBUM);
    }

    // startActivityForResult로 받은 결과
    // 여기서 프로필 이미지를 서버에 저장시킨다!
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_FACE_PROFILE_FROM_ALBUM && resultCode == Activity.RESULT_OK){
            Uri imageUri = data.getData();

            final SweetAlertDialog progressDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
            progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            progressDialog.setTitleText("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();

            FirebaseStorage
                    .getInstance()
                    .getReference()
                    .child("Face Profile Images")
                    .child(uservo.getUnique_id() + prof_string_to_face)
                    .putFile(imageUri)
                    .addOnSuccessListener(Task -> {
                        Task.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(Uri -> {
                            Glide.with(this)
                                    .load(Uri)
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            progressBar.setVisibility(View.GONE);
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            progressBar.setVisibility(View.GONE);
                                            return false;
                                        }
                                    })
                                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(15)))
                                    .into(profile_img);
                            progressBar.setVisibility(View.VISIBLE);
                            text_layout.setVisibility(View.INVISIBLE);
                            ok_btn.setVisibility(View.VISIBLE);
                            // uservo에 사용자 얼굴 이미지 uri 저장하고 서버에도 저장함
                            uservo.setUser_meeting_profile_image_uri(Uri.toString());
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("user_meeting_profile_uri",Uri.toString());
                            FirebaseFirestore
                                    .getInstance()
                                    .collection("사용자 정보")
                                    .document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                                    .update(map);
                            //Todo: 여기에 미팅을 등록하는 사람 즉 현재 userVO에 담긴 사람을 리얼타임 데이터베이스에 users컬렉션으로 넣어주기. by 이정찬
                            realtimedatabase.getReference().child("users").child(firebaseAuth.getCurrentUser().getUid()).setValue(uservo);
                            progressDialog.dismiss();
                        });
                    })
                    .addOnFailureListener(Uri -> {
                        text_layout.setVisibility(View.VISIBLE);
                        ok_btn.setVisibility(View.INVISIBLE);
                        Toasty.error(this,"오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT,true).show();
                    });

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    void checkSetting(){
        final SweetAlertDialog progressDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl("gs://slrcoding.appspot.com/");
        StorageReference pathReference_to_face = storageReference.child("Face Profile Images/"+uservo.getUnique_id() + prof_string_to_face);
        pathReference_to_face.getDownloadUrl().addOnSuccessListener(Uri -> {
            // 얼굴 사진이 있으면 메인화면으로 이동
            progressDialog.changeAlertType(SweetAlertDialog.NORMAL_TYPE);
            progressDialog.setTitleText("미팅용 사진은 이미 있으시군요!");
            progressDialog.setContentText("사진을 변경하시겠습니까?");
            progressDialog.setCancelText("아니요");
            progressDialog.showCancelButton(true);
            progressDialog.setCancelClickListener(sweetAlertDialog -> {
                //Todo: 여기에 미팅을 등록하는 사람 즉 현재 userVO에 담긴 사람을 리얼타임 데이터베이스에 users컬렉션으로 넣어주기. by 이정찬
                uservo.setUser_meeting_profile_image_uri(Uri.toString());
                realtimedatabase.getReference().child("users").child(firebaseAuth.getCurrentUser().getUid()).setValue(uservo);
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("EXIT", true);
                startActivity(intent);
            });
            progressDialog.setConfirmText("네");
        })
                .addOnFailureListener(e -> {
                    // 얼굴 사진이 없으면 그대로 진행
                    progressDialog.dismiss();
                });
    }
}

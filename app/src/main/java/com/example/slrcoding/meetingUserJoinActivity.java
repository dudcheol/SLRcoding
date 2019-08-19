package com.example.slrcoding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.protobuf.Any;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

import static com.example.slrcoding.MainActivity.uservo;

// 박영철

public class meetingUserJoinActivity extends AppCompatActivity {
    final int PICK_PROFILE_FROM_ALBUM = 10;
    private String prof_string = "_profileImage.png";

    private CardView add_photo;
    private Button ok_btn;
    private RelativeLayout text_layout;
    private ImageView profile_img;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_user_join);

        ok_btn = findViewById(R.id.ok_btn);
        add_photo = findViewById(R.id.profile);
        text_layout = findViewById(R.id.text_layout);
        profile_img = findViewById(R.id.profile_img);
        progressBar = findViewById(R.id.progressbar);

        initSetting();

        add_photo.setOnClickListener(v -> {
            photoPicker();
        });

        ok_btn.setOnClickListener(v -> {
            Intent intent = new Intent(this,meetingUserJoin2Activity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        });
    }

    void initSetting(){
        text_layout.setVisibility(View.VISIBLE);
        ok_btn.setVisibility(View.GONE);
    }

    void photoPicker(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent,PICK_PROFILE_FROM_ALBUM);
    }

    // startActivityForResult로 받은 결과
    // 여기서 프로필 이미지를 서버에 저장시킨다!
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_PROFILE_FROM_ALBUM && resultCode == Activity.RESULT_OK){
            Uri imageUri = data.getData();

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("프로필 설정 중 ...");
            progressDialog.show();

            FirebaseStorage
                    .getInstance()
                    .getReference()
                    .child("Profile Images")
                    .child(uservo.getUser_id() + prof_string)
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
}

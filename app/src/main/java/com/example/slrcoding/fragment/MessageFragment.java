package com.example.slrcoding.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.slrcoding.Adapter.MeetingAdapter;
import com.example.slrcoding.MainActivity;
import com.example.slrcoding.R;
import com.example.slrcoding.VO.Meeting_UserVO;
import com.example.slrcoding.meetingUserJoin2Activity;
import com.example.slrcoding.meetingUserJoinActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

// 박영철

public class MessageFragment extends Fragment {

    private String prof_string = "_profileImage.png";
    private String prof_string_to_face = "_faceImage.png";

    private RecyclerView mRecyclerView;
    private List<Meeting_UserVO> Meeting_UserVO_List;
    private MeetingAdapter meetingAdapter;
    private int SPANCOUNT = 3;
    private Spinner spinner_sex, spinner_major, spinner_setting;
    private RelativeLayout warning_message;
    private ImageView my_profile_imag;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_message, container, false);

        warning_message = v.findViewById(R.id.warning_message);
        my_profile_imag = v.findViewById(R.id.my_profile_imag);

        downloadFile(v);

        return v;
    }

    public void showRecyclerViewItem(View v){
        mRecyclerView = v.findViewById(R.id.meeting_frag_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(v.getContext(),SPANCOUNT);
        mRecyclerView.setLayoutManager(layoutManager);

        Meeting_UserVO_List = new ArrayList<>();
        meetingAdapter = new MeetingAdapter(Meeting_UserVO_List,v.getContext());
        mRecyclerView.setAdapter(meetingAdapter);

        // 아이템 크기 맞추기
        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = mRecyclerView.getWidth()  / SPANCOUNT;
                int height = mRecyclerView.getWidth()  / SPANCOUNT;
                meetingAdapter.setLength(width, height);
                mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // 리사이클러뷰가 그려진 다음에 setLength가 이루어 지므로
                meetingAdapter.notifyDataSetChanged();
            }
        });

        // 임시데이터 추가
        Meeting_UserVO_List.add(new Meeting_UserVO(1,"박보검",false,false));
        Meeting_UserVO_List.add(new Meeting_UserVO(2,"아이린",true,true));
        Meeting_UserVO_List.add(new Meeting_UserVO(1,"박보검",false,true));
        Meeting_UserVO_List.add(new Meeting_UserVO(1,"김덕배",true,true));
        Meeting_UserVO_List.add(new Meeting_UserVO(1,"박보검",true,false));
        Meeting_UserVO_List.add(new Meeting_UserVO(2,"아이린",true,true));
        Meeting_UserVO_List.add(new Meeting_UserVO(1,"아이린",false,true));
        Meeting_UserVO_List.add(new Meeting_UserVO(1,"송창식",false,true));
        Meeting_UserVO_List.add(new Meeting_UserVO(2,"박보검",false,false));
        Meeting_UserVO_List.add(new Meeting_UserVO(2,"아이린",false,true));
        Meeting_UserVO_List.add(new Meeting_UserVO(1,"송창식",false,false));
        Meeting_UserVO_List.add(new Meeting_UserVO(1,"박보검",false,false));
        Meeting_UserVO_List.add(new Meeting_UserVO(2,"박보검",true,false));
        Meeting_UserVO_List.add(new Meeting_UserVO(1,"아이린",true,true));
        Meeting_UserVO_List.add(new Meeting_UserVO(2,"박보검",true,true));
    }

    public void setSpinnerItemClick(View v){
        spinner_sex = v.findViewById(R.id.spinner_sex);
        spinner_major = v.findViewById(R.id.spinner_major);
        //spinner_setting = v.findViewById(R.id.spinner_setting);

    }

    // FireStorage에 프로필 다운로드
    public void downloadFile(View v){

        // 업로드 진행 Dialog 보이기
        SweetAlertDialog progressDialog = new SweetAlertDialog(this.getActivity(),SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl("gs://slrcoding.appspot.com/");

        //다운로드할 파일을 가르키는 참조 만들기
        StorageReference pathReference = storageReference.child("Profile Images/"+((MainActivity) getActivity()).uservo.getUser_id() + prof_string);
        StorageReference pathReference_to_face = storageReference.child("Face Profile Images/"+((MainActivity) getActivity()).uservo.getUser_id() + prof_string_to_face);

        // download url을 가져와
        // 사진이 존재하면 내용을 보여주고
        // 없으면 프로필 설정 액티비티로 이동동
       pathReference.getDownloadUrl().addOnSuccessListener(uri -> {
           // 프로필 사진이 있으면
           // 해당 uri 저장해놓는다
           Uri profile_uri = uri;
           // 얼굴 사진이 있는지 확인
           pathReference_to_face.getDownloadUrl().addOnSuccessListener(Uri -> {
               // 얼굴 사진이 있으면 미팅 탭을 보여준다
               warning_message.setVisibility(View.GONE);
               setProfileImg(profile_uri);
               setSpinnerItemClick(v);
               showRecyclerViewItem(v);
               progressDialog.dismiss();
           })
           .addOnFailureListener(e -> {
               // 얼굴 사진이 없으면 얼굴 사진을 올리는 액티비티로 이동
               warning_message.setVisibility(View.VISIBLE);
               Intent intent = new Intent(v.getContext(), meetingUserJoin2Activity.class);
               startActivity(intent);
               progressDialog.dismiss();
               Log.e("face error",e.toString());
           });
       }).addOnFailureListener(e -> {
           // 프로필 사진이 없으면 프로필 사진을 올리는 액티비티로 이동
           warning_message.setVisibility(View.VISIBLE);
           Intent intent = new Intent(v.getContext(),meetingUserJoinActivity.class);
           startActivity(intent);
           progressDialog.dismiss();
           Log.e("profile error",e.toString());
       });
    }

    void setProfileImg(Uri img){
        Glide.with(this)
                .load(img)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(my_profile_imag);
    }
}

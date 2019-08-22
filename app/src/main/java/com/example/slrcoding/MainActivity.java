package com.example.slrcoding;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;

import com.example.slrcoding.fragment.BoardFragment;
import com.example.slrcoding.fragment.FeedFragment;
import com.example.slrcoding.fragment.MainFragment;
import com.example.slrcoding.fragment.MessageFragment;
import com.example.slrcoding.fragment.MypageFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;


import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    int flag=0;
    // (최민철 수정 19.08.06)
    public static UserVO uservo;

    // (최민철 수정 19.07.28)
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();          // 파이어베이스 인증 객체 선언;  // 파이어베이스 인증 객체 생성
    private FirebaseUser currentUser;   // 현재 로그인 된 정보를 담은 객체 생성
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    // (박영철 수정 19.08.20)
    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();

            // 프래그먼트!!
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle("홈");
                    fragmentTransaction.replace(R.id.main_content, new MainFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_feed:
                    setTitle("피드");
                    fragmentTransaction.replace(R.id.main_content, new FeedFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_board:
                    setTitle("게시판");
                    fragmentTransaction.replace(R.id.main_content, new BoardFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_favorite:
                    setTitle("메시지");
                    fragmentTransaction.replace(R.id.main_content, new MessageFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_mypage:
                    setTitle("마이페이지");
                    fragmentTransaction.replace(R.id.main_content, new MypageFragment());
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("홈");
        Intent intent = getIntent();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);

        // uservo객체에 현재 로그인한 회원정보 저장.(최민철 수정 19.08.06)
        // 각 프레그먼트에서 ex) ((MainActivity)getActivity()).uservo.getUser_id() uservo 접근 가능
        uservo = new UserVO();
        currentUser = firebaseAuth.getCurrentUser();
        db.collection("사용자 정보").document(currentUser.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            uservo.setUser_id((String)documentSnapshot.getData().get("user_id"));
                            uservo.setUser_email((String)documentSnapshot.getData().get("user_email"));
                            uservo.setUser_name((String)documentSnapshot.getData().get("user_name"));
                            uservo.setUser_year((String)documentSnapshot.getData().get("user_year"));
                            uservo.setUser_month((String)documentSnapshot.getData().get("user_month"));
                            uservo.setUser_day((String)documentSnapshot.getData().get("user_day"));
                            uservo.setUser_phone_num((String)documentSnapshot.getData().get("user_phoneNum"));
                            uservo.setUser_sex((String)documentSnapshot.getData().get("user_sex"));
                            uservo.setPush_alarm((boolean)documentSnapshot.getData().get("user_push_alarm"));
                            uservo.setComment_alarm((boolean)documentSnapshot.getData().get("user_comment_alarm"));
                            uservo.setEvent_alarm((boolean)documentSnapshot.getData().get("user_event_alarm"));
                        }
                    }
                });
        // 여기까지

        if(intent.getExtras()!=null){
            flag =intent.getExtras().getInt("flag");
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if(flag==1){
            Log.i("first","first");
            fragmentTransaction.add(R.id.main_content, new FeedFragment());
            fragmentTransaction.commit();
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.getMenu().getItem(1).setChecked(true);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }
        else if(flag==2){
            Log.i("first","first");
            fragmentTransaction.add(R.id.main_content, new BoardFragment());
            fragmentTransaction.commit();
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.getMenu().getItem(2).setChecked(true);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }
        else {
            Log.i("first","two");
            fragmentTransaction.add(R.id.main_content, new MainFragment());
            fragmentTransaction.commit();
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }

        // 미팅 탭에서 초기 프로필 설정 끝나고 다시 메인으로 왔을 때
        // 알림메시지 띄움
        // 바로 미팅탭으로 이동시키고 싶으나 프로필사진을 못받아오는 오류가 있음
        if(intent.getBooleanExtra("EXIT",false)){
            //navigation.setSelectedItemId(R.id.navigation_favorite);
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this,SweetAlertDialog.SUCCESS_TYPE);
            sweetAlertDialog
                    .setTitleText("프로필 설정을 완료했습니다!")
                    .setContentText("이제 Meeting에 참여할 수 있습니다.")
                    .show();
        }

//        //메시지 가공
//        JsonObject jsonObj = new JsonObject();
////token
//        Gson gson = new Gson();
//        JsonElement jsonElement = gson.toJsonTree("fEUWEpkns0E:APA91bEhcYTiYX9FVGYGIs4IjK0k4gtwE2xTFgMpDWloOx1-uDzT8BBjCSx-IEUAj7uOdCtqFfOdDEqiGQfRBohoWBVYW5pjxx5LNdaDTSTojYH9MjTAvPPeln_kxXce5MfKdftwkPll");
//        jsonObj.add("to", jsonElement);
////Notification
//        JsonObject notification = new JsonObject();
//        notification.addProperty("title", "\uD83D\uDE03타이틀HTTP");
//        notification.addProperty("body", "\uD83D\uDE03바디HTTP");
//        jsonObj.add("notification", notification);
//        //Todo: 메시지 전송 테스트
//        /*발송*/
//
//        final MediaType mediaType = MediaType.parse("application/json");
//        OkHttpClient httpClient = new OkHttpClient();
//        try {
//            Request request = new Request.Builder().url("https://fcm.googleapis.com/fcm/send")
//                    .header("Content-Type", "application/json; UTF-8")
//                    .addHeader("Authorization", "key=" + "AAAA2DxHK98:APA91bEWD8UHv0MkkzYspQC3iUys8B3WNemkQlLx6mkWbp8Vk8_b9XqTfvJATNCYtisYMBf2kiiHOO1Mq5vJByAJNOAzFqTwgs5U55pN0dJbG203N6_agIAnGWfa0mfvUw22AJc7tZhR")
//                    .post(RequestBody.create(mediaType, jsonObj.toString())).build();
//            Response response = httpClient.newCall(request).execute();
//            String res = response.body().string();
//            Log.i("res","res: "+res);
//        } catch (IOException e) {
//        }
    }

    // 다른 프래그먼트나 어댑터에서 프래그먼트 전환이 필요할때 씀
    public void replaceFragment(Fragment fragment,int item_flag) {

        if(!isFinishing() && !isDestroyed()){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_content, fragment).commit();

            BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);

            // item_flag 는 선택된 아이템을 바꾸기 위한 변수
            // 0 ~ 4 : home, feed, board, message, mypage
            navigationView.getMenu().getItem(item_flag).setChecked(true);
        }
    }

    // 로그인 되지 않은 경우 login페이지로 이동 (최민철 수정 19.07.28)
    @Override
    public void onStart(){
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }
}
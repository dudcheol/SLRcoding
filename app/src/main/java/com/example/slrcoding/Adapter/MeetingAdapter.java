package com.example.slrcoding.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.slrcoding.Activity.ChatRoomActivity;
import com.example.slrcoding.BoardDetailActivity;
import com.example.slrcoding.R;
import com.example.slrcoding.VO.Meeting_UserVO;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.w3c.dom.Text;

import java.net.URISyntaxException;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.example.slrcoding.MainActivity.getInstance;

// 박영철
// 이정찬
// Todo: 영철이가 Meeting_UserVO 안쓰고 UserVO를 쓴다고 함.
public class MeetingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Meeting_UserVO> Meeting_UserVO_List;
    private Context mContext;
    private int width = 0, height = 0;
    public static boolean faceagree;
    // 생성자
    public MeetingAdapter(List<Meeting_UserVO> list, Context context) {
        this.Meeting_UserVO_List = list;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_profile_item,parent,false);


        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        initSetting(holder,position);

        // 클릭 이벤트
        ((ItemViewHolder)holder).click_part.setOnClickListener(v -> {
            // 임시파일
            // defaultimage에 border radius를 줌
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.defaultimage);
            RoundedBitmapDrawable rd = RoundedBitmapDrawableFactory.create(mContext.getResources(),((BitmapDrawable)drawable).getBitmap());
            rd.setCircular(true);

            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(mContext,SweetAlertDialog.CUSTOM_IMAGE_TYPE);

            sweetAlertDialog
                    .setTitleText(Meeting_UserVO_List.get(position).getName()+" / 성별")
                    .setContentText("자기소개 한마디")
                    .setCustomImage(rd)
                    .setCancelText("닫기")
                   .showCancelButton(true)
                    .setConfirmText("채팅하기")
                    //Todo: 이정찬 채팅방 이동시키기 추가
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//                            //Todo: 이정찬 ( 2019.09.18)
//                            //Todo: 여기서 상대방 유니크 아이디 uservo.getUnique_Id(); 을 통해 destinationId를 intent로 ChatRoomActivity로 보내주기.
//                            //Todo: 다이얼로그를 띄어서 자신의 얼굴 공개 여부를 선택하게 하고 공개를 할 경우 상대방의 얼굴도 볼 수 있는 기능 추가.
//                            //Todo: 만약에 얼굴 공개를 안한다면 상대방 얼굴과 내 얼굴은 공개되지 않을 것이다. 즉 신중히 생각하여 공개를 할지 안할지 고민하는 기능 추가 예정.
//                            sweetAlertDialog.setTitleText("얼굴공개")
//                                    .setContentText("얼굴 공개를 하시겠습니까?")
//                                    .setCancelText("아니요")
//                                    .showCancelButton(true)
//                                    .setConfirmText("예")
//                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                        @Override
//                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                            //Todo: 여기서 상대방의 동의여부를 플래그 형태로 넘길 예정(true) 여기서 파베 사용자 정보에 facegree필드에 넣어주기.
//                                            faceagree = true;
//                                            Intent intent = new Intent(v.getContext(), ChatRoomActivity.class);
//                                            intent.putExtra("faceagree",faceagree);
//                                            mContext.startActivity(intent);
//                                            sweetAlertDialog.dismiss();
//                                        }
//                                    }).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                    //Todo: 여기서 상대방의 동의여부를 플래그 형태로 넘길 예정(false)  여기서 파베 사용자 정보에 facegree필드에 넣어주기.
//                                    faceagree = false;
//                                    Intent intent = new Intent(v.getContext(), ChatRoomActivity.class);
//                                    intent.putExtra("faceagree",faceagree);
//                                    mContext.startActivity(intent);
//                                    sweetAlertDialog.dismiss();
//                                }
//                            });
                            KakaoLinkProgressTask task = new KakaoLinkProgressTask();
                            task.execute();
                        }
                    })
                    .show();
        });

    }

    @Override
    public int getItemCount() {
        return Meeting_UserVO_List.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView man;
        ImageView woman;
        CardView connecting, click_part;
        ImageView newbie;
        ImageView profile_img;
        RelativeLayout layout;

        public ItemViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.meeting_profile_item_name);
            man = v.findViewById(R.id.meeting_profile_item_sex_man);
            woman = v.findViewById(R.id.meeting_profile_item_sex_woman);
            connecting = v.findViewById(R.id.meeting_profile_item_connecting);
            newbie = v.findViewById(R.id.meeting_profile_item_newbie_mark);
            profile_img =  v.findViewById(R.id.meeting_profile_item_image);
            layout = v.findViewById(R.id.meeting_profile_item_layout);
            click_part = v.findViewById(R.id.click_part);
        }
    }

    public void setLength(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void initSetting(RecyclerView.ViewHolder holder, int position){
        // 이미지 크기 화면에 맞춤
        if (width != 0 && height != 0) {
            ((ItemViewHolder)holder).layout.getLayoutParams().width = width;
            ((ItemViewHolder)holder).layout.getLayoutParams().height = height;
        }
        // 남녀 구분해서 보여줌
        if(Meeting_UserVO_List.get(position).getSex()==1){
            ((ItemViewHolder)holder).man.setVisibility(View.VISIBLE);
            ((ItemViewHolder)holder).woman.setVisibility(View.GONE);
        }else{
            ((ItemViewHolder)holder).man.setVisibility(View.GONE);
            ((ItemViewHolder)holder).woman.setVisibility(View.VISIBLE);
        }
        // 현재 접속중 여부
        if(Meeting_UserVO_List.get(position).isConnecting()){
            ((ItemViewHolder)holder).connecting.setVisibility(View.VISIBLE);
        }else{
            ((ItemViewHolder)holder).connecting.setVisibility(View.GONE);
        }
        // 신규 유저 여부
        if(Meeting_UserVO_List.get(position).isNewbie()){
            ((ItemViewHolder)holder).newbie.setVisibility(View.VISIBLE);
        }else{
            ((ItemViewHolder)holder).newbie.setVisibility(View.GONE);
        }
        // 닉네임 설정
        ((ItemViewHolder)holder).name.setText(Meeting_UserVO_List.get(position).getName());
    }


    private class KakaoLinkProgressTask extends AsyncTask<Void,Void,Void> {

        //        ProgressDialog asyncDialog = new ProgressDialog(
//                FeedDetailActivity.this);
        final SweetAlertDialog progressDialog = new SweetAlertDialog(getInstance,SweetAlertDialog.PROGRESS_TYPE);
        @Override
        protected void onPreExecute() {
            progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            progressDialog.setTitleText("오픈채팅방 링크 연결중...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... strings) {
            try {
                for (int i = 0; i < 5; i++) {
//                    asyncDialog.setProgress(i * 40);

                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
//            asyncDialog.dismiss();
            progressDialog.dismiss();
            Intent intent = null;
            try {
                intent = Intent.parseUri("https://open.kakao.com/o/sL6AxYG",Intent.URI_INTENT_SCHEME);
            } catch (URISyntaxException e1) {
                Toasty.error(getInstance,"오픈채팅방 연결 에러!!",Toasty.LENGTH_SHORT,true);

                e1.printStackTrace();
            }
            Intent existPackage = getInstance.getPackageManager().getLaunchIntentForPackage(intent.getPackage());
            if(existPackage!=null){
                getInstance.startActivity(intent);
            }
            super.onPostExecute(result);

        }

    }
}

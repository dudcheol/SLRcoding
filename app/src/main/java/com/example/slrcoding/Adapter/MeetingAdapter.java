package com.example.slrcoding.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import com.example.slrcoding.R;
import com.example.slrcoding.VO.Meeting_UserVO;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.w3c.dom.Text;

import java.util.List;

// 박영철

public class MeetingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Meeting_UserVO> Meeting_UserVO_List;
    private Context mContext;
    private int width = 0, height = 0;

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
                            //Todo: 여기서 상대방 아이디destinationId를 intent로 ChatRoomActivity로 보내주기.
                            Intent intent = new Intent(v.getContext(), ChatRoomActivity.class);
                            mContext.startActivity(intent);
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

}

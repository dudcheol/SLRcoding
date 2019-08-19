package com.example.slrcoding.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slrcoding.R;
import com.example.slrcoding.VO.noticeVO;

import java.util.ArrayList;
import java.util.List;

// 최민철(수정 : 19.08.16)
public class NoticeRecyclerAdapter extends RecyclerView.Adapter<NoticeRecyclerAdapter.ItemViewHolder>{

    private ArrayList<noticeVO> listData = new ArrayList<>();
    View view;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 item.xml을 inflate
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_notice_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // item을 한 개씩 보여주는(bind)함수
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수
        return listData.size();
    }

    public void addItem(noticeVO data){
        // 외부에서 item을 추가시키는 함수
        listData.add(data);
    }

    // subView를 세팅해주는 ViewHolder
    class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView title, content;
        private ImageView img;

        public ItemViewHolder(@NonNull View itemView){
            super(itemView);

            img = itemView.findViewById(R.id.notice_item_img);
            title = itemView.findViewById(R.id.notice_item_tv1);
            content = itemView.findViewById(R.id.notice_item_tv2);
        }

        public void onBind(noticeVO data){
            title.setText(data.getTitle());
            content.setText(data.getContent());
            img.setImageResource(data.getResId());
        }
    }
}

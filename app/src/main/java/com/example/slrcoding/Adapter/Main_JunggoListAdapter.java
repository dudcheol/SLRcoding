package com.example.slrcoding.Adapter;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slrcoding.R;
import com.example.slrcoding.VO.Main_JunggoVO;

import java.util.ArrayList;
import java.util.List;

// 박영철

public class Main_JunggoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Main_JunggoVO> testList;
    Activity activity;
    View v;

    public Main_JunggoListAdapter(Activity context, List<Main_JunggoVO> junggos) {
        this.testList = junggos;
        this.activity = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_junggo_item,parent,false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder)holder).title.setText(testList.get(position).getNum()+"");

        /*GradientDrawable drawable=(GradientDrawable)v.getContext().getDrawable(R.drawable.background_rounding_top);
        ((ItemViewHolder) holder).image.setBackground(drawable);
        ((ItemViewHolder) holder).image.setClipToOutline(true);*/
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    // 커스텀 뷰홀더
    class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView image;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.junggo_image);
            title = itemView.findViewById(R.id.junggo_title);
        }
    }
}

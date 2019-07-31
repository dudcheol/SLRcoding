package com.example.slrcoding.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slrcoding.R;
import com.example.slrcoding.VO.Main_JunggoVO;

import java.util.ArrayList;
import java.util.List;

public class Main_JunggoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Main_JunggoVO> testList;
    Activity activity;

    public Main_JunggoListAdapter(Activity context, List<Main_JunggoVO> junggos) {
        this.testList = junggos;
        this.activity = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_junggo_item,parent,false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder)holder).title.setText(testList.get(position).getNum()+"");
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    // 커스텀 뷰홀더
    class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.junggo_title);
        }
    }
}

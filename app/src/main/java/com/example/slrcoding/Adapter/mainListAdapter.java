package com.example.slrcoding.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slrcoding.R;
import com.example.slrcoding.util.MainListViewType;

import java.util.ArrayList;
import java.util.List;

// 박영철
// 리사이클러뷰 어댑터

public class mainListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // 뷰 타입 별로 다른 뷰 제공
    public static final int VIEW_TYPE_A = 0;
    public static final int VIEW_TYPE_B = 1;

    // 순서별로 어떤 뷰를 보여줄지 리스트에 담아서 결정한다
    private List<MainListViewType> mainListViewTypeList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_A){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_cardview_famous,parent,false);
            return new AHolder(v);
        }
        else{
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_cardview_latest,parent,false);
            return new BHolder(v);
        }
    }


    // onCreateViewHolder가 호출되기 전에 getItemViewType이 먼저 호출되어
    // 뷰타입을 어떻게 정의할지 확인함
    @Override
    public int getItemViewType(int position) {
        switch (mainListViewTypeList.get(position).getViewType()){
            case VIEW_TYPE_A:
                return VIEW_TYPE_A;
            case VIEW_TYPE_B:
                return VIEW_TYPE_B;

                // Todo : 디폴트의 경우 에러처리
                default:
                    return -1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MainListViewType list = mainListViewTypeList.get(position);
        if(holder instanceof AHolder){
            // AHolder에서 보여줄 것 구현
        }else if(holder instanceof  BHolder){
            // BHolder에서 보여줄 것 구현
        }
    }

    @Override
    public int getItemCount() {
        return mainListViewTypeList.size();
    }

    public class AHolder extends RecyclerView.ViewHolder{
        public AHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class BHolder extends RecyclerView.ViewHolder{
        public BHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

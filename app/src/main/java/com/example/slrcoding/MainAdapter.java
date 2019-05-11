package com.example.slrcoding;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

//어댑터
//이정찬
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

    private List<Board> mBoardList;

    public MainAdapter(List<Board> mBoardList) {
        this.mBoardList = mBoardList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //item_main 레이아웃을 생성하여 리턴
        return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main,parent,false));
    }
    //Board로 데이터 바인딩 시킴 이로인해 item_main에 내용이 뜨게 됨
    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Board data = mBoardList.get(position);
        holder.mCategoryTextView.setText(data.getCategory());
        holder.mTitleTextView.setText(data.getTitle());
        holder.mNameTextView.setText(data.getName());
    }

    @Override
    public int getItemCount() {
        return mBoardList.size();
    }

    //하나당 세부 내용을 위한 뷰 홀더
    //item_main 아이디를 통해 각 바인딩할 뷰들을 얻어옴
    class MainViewHolder extends RecyclerView.ViewHolder{
        private TextView mCategoryTextView;
        private TextView mTitleTextView;
        private TextView mNameTextView;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            mCategoryTextView = itemView.findViewById(R.id.category_name);
            mTitleTextView = itemView.findViewById(R.id.item_title_text);
            mNameTextView = itemView.findViewById(R.id.item_name_text);


        }
    }
}
package com.example.slrcoding;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class famousAdapter extends RecyclerView.Adapter<famousAdapter.ViewHolder> {

    private List<Board> mDataset;

    public famousAdapter(List<Board> mBoardList) {
        this.mDataset = mBoardList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titleText;
        //public TextView contentText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleText=(TextView) itemView.findViewById(R.id.latest_content_title);
            //contentText=(TextView) itemView.findViewById(R.id.latest_content_content);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_cardview_famous,viewGroup,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Board data = mDataset.get(i);
        // 생성자를 통해 받아온 데이터를 전역변수에 저장시킨 다음
        // 온바인드뷰홀더에서 처리
        // viewHolder.titleText.setTExt()..... 이런식
        viewHolder.titleText.setText(data.getTitle());
        //iewHolder.contentText.setText(data.getContents());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

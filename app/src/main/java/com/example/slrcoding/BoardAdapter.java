package com.example.slrcoding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.like.LikeButton;

import java.util.List;

//피드 리사이클러 어댑터
//이정찬
//어댑터

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.MainViewHolder>{

    private static final int RESULT_OK = 3000;
    private List<Board2> board_mBoardList;

    public BoardAdapter(List<Board2> board_mBoardList) {
        this.board_mBoardList = board_mBoardList;


    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.board_item_test_design,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        final Board2 data = board_mBoardList.get(position);
        holder.board_mCategoryTextView.setText(data.getCategory());
        holder.board_mTitleTextView.setText(data.getTitle());
        holder.board_mNameTextView.setText(data.getName());
        holder.board_mRegDateView.setText(data.getRegDate());
        holder.board_mReplyCntVIew.setText(String.valueOf(data.getReplyCnt()));
        holder.board_mContentTextView.setText(data.getContents());
        //피드 클릭
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                //여기서 각 피드들 클릭시 상세보기로 이동
                //Toast.makeText(context, position +""+data.getTitle(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context,BoardDetailActivity.class);
                if (context instanceof Activity) {
                    intent.putExtra("category",data.getCategory());
                    intent.putExtra("id",data.getId());
                    ((Activity) context).startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return board_mBoardList.size();
    }

    //하나당 세부 내용을 위한 뷰 홀더
    class MainViewHolder extends RecyclerView.ViewHolder{
        public final View mView;

        private TextView board_mCategoryTextView;
        private TextView board_mTitleTextView;
        private TextView board_mNameTextView;
        private TextView board_mContentTextView;
        private TextView board_mRegDateView;
        private TextView board_mReplyCntVIew;
        private LikeButton board_likeButton;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            board_mCategoryTextView = itemView.findViewById(R.id.board_category_name);
            board_mTitleTextView = itemView.findViewById(R.id.board_item_title_text);
            board_mNameTextView = itemView.findViewById(R.id.board_item_name_text);
            board_mRegDateView = itemView.findViewById(R.id.board_regDate);
            board_mReplyCntVIew = itemView.findViewById(R.id.board_reply_cnt);
            board_mContentTextView = itemView.findViewById(R.id.board_item_content);
            mView = itemView;


        }
    }
}
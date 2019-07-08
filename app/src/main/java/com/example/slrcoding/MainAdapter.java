package com.example.slrcoding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

//피드 리사이클러 어댑터
//이정찬
//어댑터
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

    private static final int RESULT_OK = 3000;
    private List<Board> mBoardList;


    public MainAdapter(List<Board> mBoardList) {
        this.mBoardList = mBoardList;


    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_test_design,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        final Board data = mBoardList.get(position);
        holder.mCategoryTextView.setText(data.getCategory());
        holder.mTitleTextView.setText(data.getTitle());
        holder.mNameTextView.setText(data.getName());
        holder.mRegDateView.setText(data.getRegModifyDate());
        holder.mReplyCntVIew.setText(String.valueOf(data.getReplyCnt()));
        holder.mContentTextView.setText(data.getContents());
        //피드 클릭
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                //여기서 각 피드들 클릭시 상세보기로 이동
                //Toast.makeText(context, position +""+data.getTitle(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context,FeedDetailActivity.class);
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
        return mBoardList.size();
    }

    //하나당 세부 내용을 위한 뷰 홀더
    class MainViewHolder extends RecyclerView.ViewHolder{
        public final View mView;

        private TextView mCategoryTextView;
        private TextView mTitleTextView;
        private TextView mNameTextView;
        private TextView mContentTextView;
        private TextView mRegDateView;
        private TextView mReplyCntVIew;
        private LikeButton likeButton;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            mCategoryTextView = itemView.findViewById(R.id.category_name);
            mTitleTextView = itemView.findViewById(R.id.item_title_text);
            mNameTextView = itemView.findViewById(R.id.item_name_text);
            mRegDateView = itemView.findViewById(R.id.regDate);
            mReplyCntVIew = itemView.findViewById(R.id.reply_cnt);
            mContentTextView = itemView.findViewById(R.id.item_content);
            mView = itemView;


        }
    }
}
package com.example.slrcoding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

//피드 리사이클러 어댑터
//이정찬
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

    private List<Board> mBoardList;
    public static final int REQUEST_CODE = 1000;
    public static final String errorMessage ="Context should be an instanceof Activity.";
    public MainAdapter(List<Board> mBoardList) {
        this.mBoardList = mBoardList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //item_main 레이아웃을 생성하여 리턴
        return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item_test_design,parent,false));
    }
    //Board로 데이터 바인딩 시킴 이로인해 item_main에 내용이 뜨게 됨
    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, final int position) {

        final Board data = mBoardList.get(position);

        holder.mCategoryTextView.setText(data.getCategory());
        holder.mTitleTextView.setText(data.getTitle());
        holder.mNameTextView.setText(data.getName());
        holder.mDateTextView.setText(data.getDate());
        holder.mContentTextView.setText(data.getContents());
        //좋아요 버튼 클릭
        holder.mlikeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Context context = likeButton.getContext();

                Toast.makeText(context, "좋아요 버튼 클릭!!"+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Context context = likeButton.getContext();
                Toast.makeText(context, "좋아요 버튼 취소!!"+position, Toast.LENGTH_SHORT).show();
            }
        });
        //피드 클릭
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                //여기서 각 피드들 클릭시 상세보기로 이동
                //Toast.makeText(context, position +""+data.getTitle(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context,FeedDetailActivity.class);
                if (context instanceof Activity) {
                    intent.putExtra("title",data.getTitle());
                    intent.putExtra("content",data.getContents());
                    intent.putExtra("category",data.getCategory());
                    intent.putExtra("date",data.getDate());
                    ((Activity) context).startActivityForResult(intent,REQUEST_CODE);
                } else {
                    Log.e("e",errorMessage);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mBoardList.size();
    }

    //하나당 세부 내용을 위한 뷰 홀더
    //item_main 아이디를 통해 각 바인딩할 뷰들을 얻어옴
    class MainViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        private TextView mCategoryTextView;
        private TextView mTitleTextView;
        private TextView mNameTextView;
        private TextView mDateTextView;
        private LikeButton mlikeButton;
        private TextView mContentTextView;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            mCategoryTextView = itemView.findViewById(R.id.category_name);
            mTitleTextView = itemView.findViewById(R.id.item_title_text);
            mNameTextView = itemView.findViewById(R.id.item_name_text);
            mDateTextView = itemView.findViewById(R.id.feed_write_date);
            mlikeButton = itemView.findViewById(R.id.heart_button);
            mContentTextView =itemView.findViewById(R.id.item_content);
            mView = itemView;

        }
    }
}
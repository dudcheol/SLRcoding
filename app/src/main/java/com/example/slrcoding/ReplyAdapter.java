package com.example.slrcoding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ReplyViewHolder> {
    private List<FeedReplyVO> feedReplyVOList;

    public ReplyAdapter(List<FeedReplyVO> feedReplyVOList) {
        this.feedReplyVOList = feedReplyVOList;
    }

    @NonNull
    @Override
    public ReplyAdapter.ReplyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //feed_reply_item 레이아웃을 생성하여 리턴
        return new ReplyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_reply_item,viewGroup,false));
    }

    //ReplyVO로 데이터 바인딩 시킴 이로인해 feed_reply_item에  내용이 뜨게 됨
    @Override
    public void onBindViewHolder(@NonNull ReplyAdapter.ReplyViewHolder replyViewHolder,final int i) {
        final FeedReplyVO reply = feedReplyVOList.get(i);
        replyViewHolder.mReplyname.setText(reply.getReplyName());
        replyViewHolder.mReplydate.setText(reply.getReplyDate());
        replyViewHolder.mReplycontent.setText(reply.getReplyContent());

    }

    @Override
    public int getItemCount() {
        return feedReplyVOList.size();
    }

    //하나당 세부 내용을 위한 뷰 홀더
    //item_main 아이디를 통해 각 바인딩할 뷰들을 얻어옴
    class ReplyViewHolder extends RecyclerView.ViewHolder{
        public final View mView;

        private TextView mReplydate;
        private TextView mReplycontent;
        private TextView mReplyname;

        public ReplyViewHolder(@NonNull View itemView) {
            super(itemView);

            mReplydate = itemView.findViewById(R.id.feed_reply_date);
            mReplycontent = itemView.findViewById(R.id.feed_reply_content);
            mReplyname = itemView.findViewById(R.id.feed_reply_id);
            mView = itemView;

        }
    }
}

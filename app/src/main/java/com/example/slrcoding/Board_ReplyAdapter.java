package com.example.slrcoding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Board_ReplyAdapter extends RecyclerView.Adapter<Board_ReplyAdapter.ReplyViewHolder> {
    private List<BoardReplyVO> boardReplyVOList;

    public Board_ReplyAdapter(List<BoardReplyVO> boardReplyVOList) {
        this.boardReplyVOList = boardReplyVOList;
    }

    @NonNull
    @Override
    public Board_ReplyAdapter.ReplyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // board_reply_item 레이아웃을 생성하여 리턴
        return new ReplyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.board_reply_item,viewGroup,false));
    }

    //ReplyVO로 데이터 바인딩 시킴 이로인해 board_reply_item에  내용이 뜨게 됨
    @Override
    public void onBindViewHolder(@NonNull Board_ReplyAdapter.ReplyViewHolder replyViewHolder, final int i) {
        final BoardReplyVO reply = boardReplyVOList.get(i);
        replyViewHolder.board_mReplyname.setText(reply.getReplyName());
        replyViewHolder.board_mReplydate.setText(reply.getReplyDate());
        replyViewHolder.board_mReplycontent.setText(reply.getReplyContent());

    }

    @Override
    public int getItemCount() {
        return boardReplyVOList.size();
    }

    //하나당 세부 내용을 위한 뷰 홀더
    //item_main 아이디를 통해 각 바인딩할 뷰들을 얻어옴
    class ReplyViewHolder extends RecyclerView.ViewHolder{
        public final View board_mView;

        private TextView board_mReplydate;
        private TextView board_mReplycontent;
        private TextView board_mReplyname;

        public ReplyViewHolder(@NonNull View itemView) {
            super(itemView);

            board_mReplydate = itemView.findViewById(R.id.board_reply_date);
            board_mReplycontent = itemView.findViewById(R.id.board_reply_content);
            board_mReplyname = itemView.findViewById(R.id.board_reply_name);
            board_mView = itemView;

        }
    }
}

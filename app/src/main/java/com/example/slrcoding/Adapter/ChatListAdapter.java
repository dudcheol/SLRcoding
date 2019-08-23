package com.example.slrcoding.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slrcoding.R;
import com.example.slrcoding.VO.ChatVO;

import java.util.ArrayList;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {

    private List<ChatVO> chatVOList = new ArrayList<>();

    public ChatListAdapter(List<ChatVO> chatVOList) {
        this.chatVOList = chatVOList;
    }

    @NonNull
    @Override
    public ChatListAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.ChatViewHolder holder, int position) {
        final ChatVO chat = chatVOList.get(position);
        holder.mchatTitleTextView.setText(chat.getDestinationUid());

    }

    @Override
    public int getItemCount() {
        return chatVOList.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        public final View chatView;

        private TextView mchatTitleTextView;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            mchatTitleTextView = itemView.findViewById(R.id.chatitem_textview_title);

            chatView = itemView;
        }
    }


}
